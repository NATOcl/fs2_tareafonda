package cl.dsy1104.fonda.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class VentaRequestDTO {

    @NotNull(message = "El id de la bebida es obligatorio.")
    private Long bebidaId;

    @NotNull(message = "Las unidades son obligatorias.")
    @Min(value = 1, message = "Debe vender al menos 1 unidad.")
    private Integer unidades;

    public VentaRequestDTO() {
    }

    public Long getBebidaId() {
        return bebidaId;
    }

    public void setBebidaId(Long bebidaId) {
        this.bebidaId = bebidaId;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }
}
