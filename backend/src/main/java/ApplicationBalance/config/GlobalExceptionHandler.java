package ApplicationBalance.config;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura qualquer RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Aqui você pode personalizar a mensagem ou retornar um JSON com detalhes sobre o erro
        String errorMessage = ex.getMessage();  // Retorna a mensagem de erro
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);  // Retorna 400 (Bad Request) e a mensagem
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidUUID(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Invalid UUID format for parameter: " + ex.getName());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<String> handleMissingPathVariable(MissingPathVariableException ex) {
        return ResponseEntity.badRequest().body("Missing required path variable: " + ex.getVariableName());
    }

}
