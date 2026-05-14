package org.libreria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Maneja el error 404 cuando no se encuentra un Libro
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> manejarResourceNotFound(ResourceNotFoundException ex) {
    Map<String, String> respuesta = new HashMap<>();
    respuesta.put("error", ex.getMessage());
    return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
  }

  // Maneja el error 400 cuando fallan las validaciones (@NotBlank, @Positive, etc.)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
    Map<String, String> errores = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
            errores.put(error.getField(), error.getDefaultMessage())
    );
    return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
  }
}