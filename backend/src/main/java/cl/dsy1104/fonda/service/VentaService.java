package cl.dsy1104.fonda.service;

import cl.dsy1104.fonda.model.Venta;
import cl.dsy1104.fonda.model.EstadoVenta;
import cl.dsy1104.fonda.model.Bebida;
import cl.dsy1104.fonda.repository.VentaRepository;
import cl.dsy1104.fonda.repository.BebidaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service 
public class VentaService {
    
    @Autowired 
    private VentaRepository ventaRepository;

    @Autowired 
    private BebidaRepository bebidaRepository;

    public List<Venta> obtenerVentas(){
        return ventaRepository.findAll();
    }

    public Venta registrarVenta(Venta ventaR){

        if (ventaR.getBebida() == null || ventaR.getBebida().getId() == null) {
            throw new IllegalArgumentException("La bebida es obligatoria.");
        }
        Bebida bebida = bebidaRepository.findById(ventaR.getBebida().getId()).orElse(null);

        if (bebida == null) {
            return null; 
        }

        if (Boolean.TRUE.equals(bebida.getVentaRestringida())) {
            //Rechazo
            ventaR.setBebida(bebida);
            ventaR.setEstado(EstadoVenta.RECHAZADA);
            ventaR.setMotivo("Bebida con venta restringida");
            ventaR.setFecha(LocalDateTime.now());
            ventaRepository.save(ventaR);
            throw new IllegalStateException("La bebida se encuentra restringida para la venta."); 
        }

        if (bebida.getStock() < ventaR.getUnidades()) {
            ventaR.setBebida(bebida);
            ventaR.setEstado(EstadoVenta.RECHAZADA);
            ventaR.setMotivo("Stock insuficiente");
            ventaR.setFecha(LocalDateTime.now());
            ventaRepository.save(ventaR);
            throw new IllegalStateException("Stock insuficiente para realizar la venta."); 
        }

        // Autorizado y reducir stock
        bebida.setStock(bebida.getStock() - ventaR.getUnidades());
        bebidaRepository.save(bebida);

        ventaR.setBebida(bebida);
        ventaR.setEstado(EstadoVenta.AUTORIZADA);
        ventaR.setFecha(LocalDateTime.now());
        return ventaRepository.save(ventaR);
    }
}
