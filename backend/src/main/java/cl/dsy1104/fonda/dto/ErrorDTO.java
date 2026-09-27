package cl.dsy1104.fonda.dto;

import java.util.Map;

public class ErrorDTO {

    private String error;
    private String mensaje;
    private Map campos;

    public ErrorDTO() {
    }

    public ErrorDTO(String error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
    }
    public ErrorDTO(String error, String mensaje, Map campos) {
        this.error = error;
        this.mensaje = mensaje;
        this.campos = campos;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
