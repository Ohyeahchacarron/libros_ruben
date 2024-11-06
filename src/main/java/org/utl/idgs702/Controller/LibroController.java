package org.utl.idgs702.Controller;

import com.google.gson.Gson;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.utl.idgs702.Entities.Libro;
import org.utl.idgs702.ViewModel.LibroPublic;
import org.utl.idgs702.AppService.LibrosExternosAppService;
import org.utl.idgs702.CQRS.LibroCQRS;
import org.utl.idgs702.DAO.LibroDAO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroCQRS librosCQRS;

    
    @Autowired
    private LibroDAO libroService;

   @Autowired
    private LibrosExternosAppService librosExternosService;

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
   @PutMapping("/actualizar/{idLibro}")
   public ResponseEntity<String> actualizarLibro(
           @PathVariable int idLibro,
           @RequestPart("libro") Libro libro,  
           @RequestPart(value = "archivo", required = false) MultipartFile archivo) {
           libro.setIdLibro(idLibro);  
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
    List<Libro> libros = libroService.listarLibros(); 
    if (libros == null || libros.isEmpty()) {
        return new ResponseEntity<>(gson.toJson(new ArrayList<>()), HttpStatus.OK); 
    }
    String json = gson.toJson(libros);
    return new ResponseEntity<>(json, HttpStatus.OK);
}

 // Endpoint para obtener el archivo PDF de un libro por su ID
@GetMapping("/pdf/{idLibro}")
public ResponseEntity<byte[]> obtenerPdfPorId(@PathVariable int idLibro) {
    try {
        Libro libro = libroService.obtenerLibroPorId(idLibro);
        if (libro.getArchivoPdf() == null) {
            throw new EntityNotFoundException("Archivo PDF no encontrado para este libro.");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=libro_" + libro.getIdLibro() + ".pdf") // Cambia a 'inline'
                .contentType(MediaType.APPLICATION_PDF)
                .body(libro.getArchivoPdf());
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}


@GetMapping("/listar-todos")
public ResponseEntity<List<LibroPublic>> getAllPublicTodo() {
    // Obtener libros externos
    List<LibroPublic> librosExternos = librosExternosService.obtenerLibrosExternos();
    
    // Obtener libros locales
    List<Libro> librosLocales = libroService.listarLibros();
    
    List<LibroPublic> todosLosLibros = new ArrayList<>(librosExternos);
    
    // Agregar los libros locales a la lista
    for (Libro libro : librosLocales) {
        LibroPublic libroPublic = new LibroPublic();
        libroPublic.setId_libro(libro.getIdLibro());
        libroPublic.setNombre_libro(libro.getNombreLibro());
        libroPublic.setAutor_libro(libro.getAutor());
        libroPublic.setGenero_libro(libro.getGenero());
        libroPublic.setArchivo_pdf(libro.getArchivoPdf()); 
        
        // Agregar el libro local a la lista
        todosLosLibros.add(libroPublic);
    }

    return new ResponseEntity<>(todosLosLibros, HttpStatus.OK);
}


// Endpoint para buscar libros por nombre
@GetMapping("/buscar")
public ResponseEntity<String> buscarLibrosPorNombre(@RequestParam("nombre") String nombre) {
    List<Libro> librosEncontrados = libroService.buscarLibrosPorNombre(nombre);
    String json = gson.toJson(librosEncontrados);
    return new ResponseEntity<>(json, HttpStatus.OK);
}

}
