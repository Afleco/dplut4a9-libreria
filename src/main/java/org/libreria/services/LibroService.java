package org.libreria.services;

import org.libreria.exceptions.ResourceNotFoundException;
import org.libreria.models.Libro;
import org.libreria.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

  @Autowired
  private LibroRepository libroRepository;

  // Guardar libro
  public Libro guardarLibro(Libro libro) {
    return libroRepository.save(libro);
  }

  // Devuelve todos los libros de la base de datos
  public List<Libro> obtenerTodos() {
    return libroRepository.findAll();
  }

  // Actualizar un libro existente
  public Libro actualizarLibro(Long id, Libro detallesLibro) {
    Libro libroExistente = libroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el libro con ID: " + id));

    libroExistente.setTitulo(detallesLibro.getTitulo());
    libroExistente.setAutor(detallesLibro.getAutor());
    libroExistente.setGenero(detallesLibro.getGenero());
    libroExistente.setPrecio(detallesLibro.getPrecio());
    libroExistente.setPublicacion(detallesLibro.getPublicacion());

    return libroRepository.save(libroExistente);
  }

  // Eliminar un libro
  public void eliminarLibro(Long id) {
    Libro libroExistente = libroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el libro con ID: " + id));

    libroRepository.delete(libroExistente);
  }
}