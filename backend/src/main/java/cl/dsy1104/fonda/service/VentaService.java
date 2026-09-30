package cl.dsy1104.fonda.service;

import cl.dsy1104.fonda.model.Venta;
import cl.dsy1104.fonda.model.EstadoVenta;
import cl.dsy1104.fonda.model.Bebida;
import cl.dsy1104.fonda.model.TipoBebida;
import cl.dsy1104.fonda.repository.VentaRepository;
import cl.dsy1104.fonda.repository.BebidaRepository;
import cl.dsy1104.fonda.exceptions.ReglaNegocio;
import cl.dsy1104.fonda.exceptions.RecursoNoEncontrado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service 
public class VentaService {
    
    @Autowired 
    private VentaRepository ventaRepository;

    @Autowired 
    private BebidaRepository bebidaRepository;

    @Autowired 
    private BebidaService bebidaService;

    @Value("${fonda.limite-unidades-por-cliente:3}")
    private Integer limiteUnidadesPorCliente;
    
    public List<Venta> obtenerVentas() {
    return ventaRepository.findAll();
    }
    public Venta registrarVenta(Venta ventaR) {
        if (ventaR.getBebida() == null || ventaR.getBebida().getId() == null) {
            throw new ReglaNegocio("VALIDACION", "La bebida es obligatoria.");
        }

        Bebida bebida = bebidaRepository.findById(ventaR.getBebida().getId())
                .orElseThrow(() -> new RecursoNoEncontrado("No se encontró la bebida con ID: " + ventaR.getBebida().getId()));

        int precioUnitario = bebidaService.calcularPrecio(bebida);
        int totalCalculado = precioUnitario * ventaR.getUnidades();

        if (Boolean.TRUE.equals(bebida.getVentaRestringida())) {
            guardarVentaRechazada(ventaR, bebida, totalCalculado, "VENTA_RESTRINGIDA");
            throw new ReglaNegocio("VENTA_RESTRINGIDA", "La bebida se encuentra restringida para la venta.");
        }

        if (bebida.getTipo() == TipoBebida.ALCOHOLICA && ventaR.getUnidades() > limiteUnidadesPorCliente) {
            guardarVentaRechazada(ventaR, bebida, totalCalculado, "LIMITE_EXCEDIDO");
            throw new ReglaNegocio("LIMITE_EXCEDIDO", "Supera el límite máximo de " + limiteUnidadesPorCliente + " unidades por cliente.");
        }

        if (bebida.getStock() < ventaR.getUnidades()) {
            guardarVentaRechazada(ventaR, bebida, totalCalculado, "STOCK_INSUFFICIENTES");
            throw new ReglaNegocio("STOCK_INSUFFICIENTES", "Stock insuficiente para realizar la venta.");
        }

        bebida.setStock(bebida.getStock() - ventaR.getUnidades());
        bebidaRepository.save(bebida);

        ventaR.setBebida(bebida);
        ventaR.setTotal(totalCalculado);
        ventaR.setEstado(EstadoVenta.AUTORIZADA);
        ventaR.setMotivo(null);
        ventaR.setFecha(LocalDateTime.now());

        return ventaRepository.save(ventaR);
    }

    private void guardarVentaRechazada(Venta ventaR, Bebida bebida, Integer total, String motivo) {
        ventaR.setBebida(bebida);
        ventaR.setTotal(total);
        ventaR.setEstado(EstadoVenta.RECHAZADA);
        ventaR.setMotivo(motivo);
        ventaR.setFecha(LocalDateTime.now());
        ventaRepository.save(ventaR);
    }
}
