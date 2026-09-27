package cl.dsy1104.fonda.controller;

import cl.dsy1104.fonda.model.Bebida;
import cl.dsy1104.fonda.service.BebidaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/api/bebidas")
public class BebidaController {
    
    @Autowired
    private BebidaService bebidaService;

    @GetMapping
    public ResponseEntity<List<Bebida>> listarBebidas(@RequestParam(required = false) String nombre) {
        List<Bebida> bebidas = bebidaService.obtenerBebidas(nombre);
        return ResponseEntity.ok(bebidas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bebida> obtenerPorId(@PathVariable Long id) {
        Bebida bebida = bebidaService.obtenerBebidaPorId(id);
        if (bebida == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bebida);
    }

    @PostMapping
    public ResponseEntity<Bebida> crearBebida(@RequestBody Bebida bebida) {
        if (bebida.getNombre() == null || bebida.getNombre().trim().isEmpty() || bebida.getTipo() == null) {
            return ResponseEntity.badRequest().build();
        }

        Bebida nuevaBebida = bebidaService.guardarBebida(bebida);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevaBebida.getId())
                .toUri();

        return ResponseEntity.created(location).body(nuevaBebida);
    }

    public ResponseEntity<Bebida> actualizarBebida(@PathVariable Long id, @RequestBody Bebida bebida) {
        if (bebida == null) {
            return ResponseEntity.badRequest().build();
        }

        Bebida exist = bebidaService.obtenerBebidaPorId(id);
        if (exist == null) {
            return ResponseEntity.notFound().build();
        }

        bebida.setId(id);
        Bebida actualizada = bebidaService.actualizarBebida(bebida);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bebida> eliminarBebida(@PathVariable Long id) {
        boolean eliminada = bebidaService.eliminarBebida(id);
        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/restriccion")
    public ResponseEntity<Bebida> marcarRestriccion(@PathVariable Long id) {
        Bebida actualizada = bebidaService.marcarRestriccion(id, true);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }
}
