package org.libreria.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "libros")
@Data
public class Libro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String titulo;

  private String genero;

  private String autor;

  private int publicacion;

  private double precio;

}