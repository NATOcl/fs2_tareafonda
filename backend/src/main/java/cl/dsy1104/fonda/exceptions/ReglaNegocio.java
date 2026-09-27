package cl.dsy1104.fonda.exceptions;

public class ReglaNegocio extends RuntimeException{
    
    private final String codigoError;

    public ReglaNegocio(String codigoError, String mensaje) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    public String getCodigoError() {
        return codigoError;
    }
}

