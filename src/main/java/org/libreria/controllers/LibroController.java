package org.libreria.controllers;

import org.libreria.models.Libro;
import org.libreria.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

  // Volvemos a usar Inyección de Dependencias para traer el Service
  @Autowired
    private LibroService libroService;

  // Endpoint para guardar (POST: http://localhost:8080/api/libros)
  @PostMapping
  public Libro crear(@RequestBody Libro libro) {
    return libroService.guardarLibro(libro);
  }

  // Endpoint para listar (GET: http://localhost:8080/api/libros)
  @GetMapping
  public List<Libro> listarTodos() {
    return libroService.obtenerTodos();
  }

}


