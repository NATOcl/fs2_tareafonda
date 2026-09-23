package cl.dsy1104.fonda.service;

import cl.dsy1104.fonda.model.Venta;
import cl.dsy1104.fonda.repository.VentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 
public class VentaService {
    
    @Autowired 
    private VentaRepository ventaRepository;

    public List<Venta> obtenerBebidas(){
        return ventaRepository.findAll();
    }

    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Venta obtenerVentaPorId(Long id){
        return ventaRepository.findById(id).orElse(null);
    }

    public Venta actualizarVenta(Venta venta) {
        if(!ventaRepository.existsById(venta.getId())){
            return null;
        }

        return ventaRepository.save(venta);
    }

    public boolean eliminarVenta(Long id){
        if (ventaRepository.existsById(id)){
            ventaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
