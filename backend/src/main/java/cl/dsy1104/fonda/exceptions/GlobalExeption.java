package cl.dsy1104.fonda.exceptions;

import cl.dsy1104.fonda.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice 
public class GlobalExeption {

    //error400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity manejarValidaciones(MethodArgumentNotValidException ex) {
        Map campos = new HashMap<>();
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            campos.put(error.getField(), error.getDefaultMessage());
        }

        ErrorDTO response = new ErrorDTO(
                "VALIDACION",
                "Existen errores en los campos enviados",
                campos
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    //error 404 
    @ExceptionHandler(RecursoNoEncontrado.class)
    public ResponseEntity manejarNoEncontrado(RecursoNoEncontrado ex) {
        ErrorDTO response = new ErrorDTO(
                "NO_ENCONTRADO",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //error 500 
    @ExceptionHandler(Exception.class)
    public ResponseEntity manejarErrorGeneral(Exception ex) {
        ErrorDTO response = new ErrorDTO(
                "ERROR_INTERNO",
                "Ocurrió un error inesperado en el servidor: " + ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
