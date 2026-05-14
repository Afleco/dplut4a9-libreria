package org.libreria.services;

import org.libreria.models.Libro;
import org.libreria.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

  @Autowired
    private LibroRepository libroRepository;

  // Crear un libro en base de datos
  public Libro guardarLibro(Libro libro) {
    return libroRepository.save(libro);
  }

  // Obtener todos los libros de la base de datos
  public List<Libro> obtenerTodos() {
    return libroRepository.findAll();
  }
}

