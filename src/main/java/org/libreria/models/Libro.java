package org.libreria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Table(name = "libros")
@Data
public class Libro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El título es obligatorio")
  private String titulo;

  @NotBlank(message = "El género es obligatorio")
  private String genero;

  @NotBlank(message = "El autor es obligatorio")
  private String autor;

  @Min(value = 1000, message = "El año de publicación debe ser válido")
  private int publicacion;

  @Positive(message = "El precio debe ser mayor a 0")
  private double precio;
}