package org.libreria.controllers;

import jakarta.validation.Valid;
import org.libreria.models.Libro;
import org.libreria.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

  @Autowired
  private LibroService libroService;

  // Añadimos @Valid para activar las validaciones
  @PostMapping
  public Libro crear(@Valid @RequestBody Libro libro) {
    return libroService.guardarLibro(libro);
  }

  @GetMapping
  public List<Libro> listarTodos() {
    return libroService.obtenerTodos();
  }

  // Endpoint para actualizar (PUT)
  @PutMapping("/{id}")
  public ResponseEntity<Libro> actualizar(@PathVariable Long id, @Valid @RequestBody Libro libro) {
    Libro libroActualizado = libroService.actualizarLibro(id, libro);
    return ResponseEntity.ok(libroActualizado);
  }

  // Endpoint para borrar (DELETE)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    libroService.eliminarLibro(id);
    return ResponseEntity.noContent().build();
  }
}