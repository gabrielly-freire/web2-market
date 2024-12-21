package br.ufrn.imd.market.util;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.ufrn.imd.market.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder mensagem = new StringBuilder("Validação falhou: ");
        ex.getConstraintViolations().forEach(violation -> {
            mensagem.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append(", ");
        });

        if (mensagem.length() > 0) {
            mensagem.setLength(mensagem.length() - 2);
        }

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(mensagem.toString(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Erro de integridade de dados", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
