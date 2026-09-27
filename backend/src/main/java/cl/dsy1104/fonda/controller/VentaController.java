package cl.dsy1104.fonda.controller;

import cl.dsy1104.fonda.model.Venta;
import cl.dsy1104.fonda.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/api/ventas")
public class VentaController {
    
    @Autowired 
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> obtenerHistorialVentas() {
        return ResponseEntity.ok(ventaService.obtenerVentas());
    }

    @PostMapping
    public ResponseEntity registrarVenta(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.registrarVenta(venta);
            if (nuevaVenta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bebida no encontrada.");
            }

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(nuevaVenta.getId())
                    .toUri();

            return ResponseEntity.created(location).body(nuevaVenta);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
