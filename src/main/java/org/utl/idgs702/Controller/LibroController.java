package org.utl.idgs702.Controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.utl.idgs702.Entities.Libro;
import org.utl.idgs702.CQRS.LibrosCQRS;
import org.utl.idgs702.DAOLibros.LibroDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibrosCQRS librosCQRS;

    
    @Autowired
    private LibroDAO libroService;

    // Instancia de Gson para la conversión a JSON
    private Gson gson = new Gson();

    // Endpoint para crear un nuevo libro con archivo PDF
    @PostMapping("/crear")
    public ResponseEntity<String> crearLibro(
            @RequestPart("libro") Libro libro, 
            @RequestPart(value = "archivo", required = false) MultipartFile archivo) {
  
            librosCQRS.registrarLibro(libro, archivo);
            return new ResponseEntity<>(gson.toJson("Libro registrado con éxito"), HttpStatus.CREATED);
       
    }
    
    // Endpoint para obtener un libro por su ID
    @GetMapping("/{idLibro}")
    public ResponseEntity<String> obtenerLibroPorId(@PathVariable int idLibro) {
        try {
            Libro libro = libroService.obtenerLibroPorId(idLibro);
            String json = gson.toJson(libro);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Libro no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para actualizar un libro existente
   // Endpoint para actualizar un libro existente
   @PutMapping("/actualizar/{idLibro}")
   public ResponseEntity<String> actualizarLibro(
           @PathVariable int idLibro,
           @RequestPart("libro") Libro libro,  
           @RequestPart(value = "archivo", required = false) MultipartFile archivo) {
       
           libro.setIdLibro(idLibro);  // Asegurarse de establecer el ID del libro a actualizar
           librosCQRS.actualizarLibro(libro, archivo);
           return new ResponseEntity<>(gson.toJson("Libro actualizado con éxito"), HttpStatus.OK);
   }

    // Endpoint para eliminar un libro por su ID
    @DeleteMapping("/eliminar/{idLibro}")
    public ResponseEntity<String> eliminarLibro(@PathVariable int idLibro) {
        try {
            libroService.eliminarLibro(idLibro);
            return new ResponseEntity<>(gson.toJson("Libro eliminado con éxito"), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Error al eliminar el libro"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para listar todos los libros
   @GetMapping("/listar")
public ResponseEntity<String> listarLibros() {
    List<Libro> libros = libroService.listarLibros(); // Asegúrate de que esto devuelve una lista
    if (libros == null || libros.isEmpty()) {
        return new ResponseEntity<>(gson.toJson(new ArrayList<>()), HttpStatus.OK); // Devolver un array vacío
    }
    String json = gson.toJson(libros);
    return new ResponseEntity<>(json, HttpStatus.OK);
}


    // Endpoint para obtener el archivo PDF de un libro por su ID
    @GetMapping("/pdf/{idLibro}")
    public ResponseEntity<byte[]> obtenerPdfPorId(@PathVariable int idLibro) {
        try {
            return libroService.obtenerPdfPorId(idLibro);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
