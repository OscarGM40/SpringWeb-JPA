package io.getarrays.server.models;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.SuperBuilder;

// fijate que no es obligatorio crear una clase/modelo para las respuestas,pero si lo hago las respuestas quedan más consistentes
@Data
@SuperBuilder //crea el constructor private con el patrón Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL) // para que no se envie nulos
public class Response {

  protected LocalDateTime timeStamp;
  protected int statusCode;
  protected HttpStatus status;
  protected String reason;
  protected String message;
  protected String developerMessage; // mensaje más técnico  
  protected Map<?,?> data; // mapa con cualquier tipo de dato
  
}
