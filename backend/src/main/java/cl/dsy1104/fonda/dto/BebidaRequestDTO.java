package cl.dsy1104.fonda.dto;

import cl.dsy1104.fonda.model.TipoBebida;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BebidaRequestDTO {
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotNull(message = "El tipo de bebida es obligatorio.")
    private TipoBebida tipo;

    @NotNull(message = "El volumen en ML es obligatorio.")
    @Min(value = 100, message = "El volumen mínimo es 100 ml.")
    @Max(value = 3000, message = "El volumen máximo es 3000 ml.")
    private Integer volumenML;

    @NotNull(message = "El stock es obligatorio.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer stock;

    private Double gradosAlcohol;

    private Boolean certificada;

    private Integer azucarPorLitro;

    private Boolean ventaRestringida = false;

    public BebidaRequestDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoBebida getTipo() {
        return tipo;
    }

    public void setTipo(TipoBebida tipo) {
        this.tipo = tipo;
    }

    public Integer getVolumenML() {
        return volumenML;
    }

    public void setVolumenML(Integer volumenML) {
        this.volumenML = volumenML;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(Double gradosAlcohol) {
        this.gradosAlcohol = gradosAlcohol;
    }

    public Boolean getCertificada() {
        return certificada;
    }

    public void setCertificada(Boolean certificada) {
        this.certificada = certificada;
    }

    public Integer getAzucarPorLitro() {
        return azucarPorLitro;
    }

    public void setAzucarPorLitro(Integer azucarPorLitro) {
        this.azucarPorLitro = azucarPorLitro;
    }

    public Boolean getVentaRestringida() {
        return ventaRestringida;
    }

    public void setVentaRestringida(Boolean ventaRestringida) {
        this.ventaRestringida = ventaRestringida;
    }
}
