package cl.dsy1104.fonda.dto;

import cl.dsy1104.fonda.model.TipoBebida;

public class BebidaDTO {

    private Long id;
    private String nombre;
    private TipoBebida tipo;
    private Integer volumenML;
    private Integer stock;
    private Double gradosAlcohol;
    private Boolean certificada;
    private Integer azucarPorLitro;
    private Boolean ventaRestringida;
    private Integer precio;

    public BebidaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}
