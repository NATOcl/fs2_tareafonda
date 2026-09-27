package cl.dsy1104.fonda.dto;

import cl.dsy1104.fonda.model.EstadoVenta;
import java.time.LocalDateTime;

public class VentaResponseDTO {

    private Long id;
    private Long bebidaId;
    private String nombre; 
    private Integer unidades;
    private Integer total; 
    private EstadoVenta estado;
    private String motivo; 
    private LocalDateTime fecha;

    public VentaResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBebidaId() {
        return bebidaId;
    }

    public void setBebidaId(Long bebidaId) {
        this.bebidaId = bebidaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
