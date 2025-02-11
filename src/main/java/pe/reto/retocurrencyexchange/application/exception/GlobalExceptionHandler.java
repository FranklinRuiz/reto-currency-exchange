package pe.reto.retocurrencyexchange.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String STATUS = "status";
    private static final String STATUS_CODE = "error";
    private static final String MESSAGE = "message";

    @ExceptionHandler(ExchangeRateException.class)
    public ResponseEntity<Map<String, String>> handleExchangeRateException(ExchangeRateException ex) {
        Map<String, String> errorResponse = Map.of(
                STATUS, STATUS_CODE,
                MESSAGE, ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<Map<String, String>> handleWebInputException(ServerWebInputException ex) {
        Map<String, String> errorResponse = Map.of(
                STATUS, STATUS_CODE,
                MESSAGE, "Invalid input: " + ex.getReason()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = Map.of(
                STATUS, STATUS_CODE,
                MESSAGE, "Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
