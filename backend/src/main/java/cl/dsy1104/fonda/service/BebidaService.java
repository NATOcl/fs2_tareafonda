package cl.dsy1104.fonda.service;

import cl.dsy1104.fonda.model.Bebida;
import cl.dsy1104.fonda.repository.BebidaRepository;

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
}
