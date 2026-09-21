package cl.dsy1104.fonda.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bebida")

public class Bebida {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoBebida tipo;

    @Column(name = "volumen_ml",nullable = false)
    private Integer volumenML;

    @Column(nullable = false)
    private  Integer stock;

    @Column(name = "grados_alcohol")
    private  Double gradosAlcohol;

    private boolean certificada;

    @Column(name = "azucar_por_litro")
    private Integer azucarPorLitro;

    @Column(name = "venta_restringida", nullable = false)
    private Boolean ventaRestringida = false;

    public Bebida(){

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public TipoBebida getTipo(){
        return tipo;
    }

    public Integer getVolumenML(){
        return volumenML;
    }

    public void setVolumenML(Integer volumenML){
        this.volumenML = volumenML;
    }

    public Integer getStock(){
        return stock;
    }

    public void setStock( Integer  stock){
        this.stock = stock;
    }

    public Double getGradosAlcohol(){
        return gradosAlcohol;
    }

    public void setGradosAlcohol(Double gradosAlcohol){
        this.gradosAlcohol = gradosAlcohol;
    }

    public Boolean getCertificada(){
        return certificada;
    }

    public void setCertificada(Boolean certificada){
        this.certificada = certificada;
    }

    public Integer getAzucarPorLitro(){
        return azucarPorLitro;
    }

    public void setAzucarPorLitro(Integer azucarPorLitro){
        this.azucarPorLitro = azucarPorLitro;
    }

    public Boolean getVentaRestringida(){
        return ventaRestringida;
    }

    public void setVentaRestringida(Boolean  ventaRestringida){
        this.ventaRestringida = ventaRestringida;
    }






    
}