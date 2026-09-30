package cl.dsy1104.fonda.service;

import cl.dsy1104.fonda.model.Bebida;
import cl.dsy1104.fonda.model.TipoBebida;
import cl.dsy1104.fonda.repository.BebidaRepository;
import cl.dsy1104.fonda.exceptions.ReglaNegocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  
public class BebidaService {
    
    @Autowired 
    private BebidaRepository bebidaRepository;

    public List<Bebida> obtenerBebidas(String nombre){
        if (nombre != null && !nombre.trim().isEmpty()) {
            return bebidaRepository.findByNombreContainingIgnoreCase(nombre);
        }
        return bebidaRepository.findAll();
    }

    public Bebida guardarBebida(Bebida bebida) {
        return bebidaRepository.save(bebida);
    }

    public Bebida obtenerBebidaPorId(Long id){
        return bebidaRepository.findById(id).orElse(null);
    }

    public Bebida actualizarBebida(Bebida bebida){
        if(!bebidaRepository.existsById(bebida.getId())){
            return null;
        }

        return bebidaRepository.save(bebida);
    }

    public boolean eliminarBebida(Long id){
        if (bebidaRepository.existsById(id)){
            bebidaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Bebida marcarRestriccion(Long id, boolean restringida) {
        Bebida bebida = obtenerBebidaPorId(id);
        if (bebida != null) {
            bebida.setVentaRestringida(restringida);
            return bebidaRepository.save(bebida);
        }
        return null;
    }

    public Integer calcularPrecio(Bebida bebida) {
        if (bebida.getTipo() == TipoBebida.ALCOHOLICA) {
            int base = 3500;
            if (Boolean.FALSE.equals(bebida.getCertificada())) {
                base = (int) (base * 1.20); 
            }
            return base;
        } else {
            int base = 2000;
            if (bebida.getAzucarPorLitro() != null && bebida.getAzucarPorLitro() > 80) {
                base = (int) (base * 1.10);
            }
            return base;
        }
    }

    public void validarReglasBebida(Bebida bebida) {
        if (bebida.getTipo() == TipoBebida.ALCOHOLICA) {
            if (bebida.getGradosAlcohol() == null || bebida.getGradosAlcohol() < 0.5 || bebida.getGradosAlcohol() > 45) {
                throw new ReglaNegocio("VALIDACION", "Grados de alcohol debe estar entre 0.5 y 45 para bebidas alcohólicas");
            }
            bebida.setAzucarPorLitro(null); 
        } else if (bebida.getTipo() == TipoBebida.SIN_ALCOHOL) {
            if (bebida.getAzucarPorLitro() == null || bebida.getAzucarPorLitro() < 0) {
                throw new ReglaNegocio("VALIDACION", "Azúcar por litro debe ser >= 0 para bebidas sin alcohol");
            }
            bebida.setGradosAlcohol(null);
            bebida.setCertificada(null);
        }
    }
}
