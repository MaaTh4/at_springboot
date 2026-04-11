package guilda.aventureiros.controle;

import guilda.aventureiros.dto.ErroPadraoResposta;
import guilda.aventureiros.excecoes.AventureiroNaoEncontradoException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadraoResposta> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        
        ErroPadraoResposta resposta = new ErroPadraoResposta("Solicitação inválida", erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroPadraoResposta> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        List<String> erros = ex.getConstraintViolations().stream()
                .map(violation -> {
                    String path = violation.getPropertyPath().toString();
                    String field = path.substring(path.lastIndexOf('.') + 1);
                    return field + ": " + violation.getMessage();
                })
                .collect(Collectors.toList());
        
        ErroPadraoResposta resposta = new ErroPadraoResposta("Solicitação inválida", erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
    
    @ExceptionHandler(AventureiroNaoEncontradoException.class)
    public ResponseEntity<ErroPadraoResposta> handleAventureiroNaoEncontrado(AventureiroNaoEncontradoException ex) {
        ErroPadraoResposta resposta = new ErroPadraoResposta(ex.getMessage(), Collections.emptyList());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }
}
