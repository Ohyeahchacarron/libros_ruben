package org.utl.idgs702.DAOLibros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.utl.idgs702.Entities.Libro;
import org.utl.idgs702.Repository.LibroRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

@Service
public class LibroDAO {

    @Autowired
    private LibroRepository libroRepository;

 // Crear un nuevo libro con el archivo PDF como BLOB
    public Libro crearLibro(String nombreLibro, String autor, String genero, char estatus, MultipartFile archivo) throws IOException {
        Libro libro = new Libro();
        libro.setNombreLibro(nombreLibro);
        libro.setAutor(autor);
        libro.setGenero(genero);
        libro.setEstatus(estatus);

        // Guardar el archivo PDF como byte[] si se proporciona
        if (archivo != null && !archivo.isEmpty()) {
            libro.setArchivoPdf(archivo.getBytes());
        } else {
            libro.setArchivoPdf(null); // Asegúrate de establecer a null si no se proporciona
        }

        // Guardamos el libro en la base de datos
        return libroRepository.save(libro);
    }


    // Obtener un libro por su ID
    public Libro obtenerLibroPorId(int idLibro) {
        return libroRepository.findById(idLibro)
                .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
    }

    // Actualizar un libro con un nuevo archivo PDF (BLOB)
    public Libro actualizarLibro(int idLibro, String nombreLibro, String autor, String genero, char estatus, MultipartFile archivo) throws IOException {
        Libro libroExistente = obtenerLibroPorId(idLibro);

        libroExistente.setNombreLibro(nombreLibro);
        libroExistente.setAutor(autor);
        libroExistente.setGenero(genero);
        libroExistente.setEstatus(estatus);

        // Actualizar el archivo PDF si se proporciona uno nuevo
        if (archivo != null && !archivo.isEmpty()) {
            libroExistente.setArchivoPdf(archivo.getBytes());
        }

        return libroRepository.save(libroExistente);
    }

    // Eliminar un libro
    public void eliminarLibro(int idLibro) {
        libroRepository.deleteById(idLibro);
    }

    // Listar todos los libros
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    // Método para mostrar el PDF basado en el id del libro
    public ResponseEntity<byte[]> obtenerPdfPorId(int idLibro) {
        Libro libro = obtenerLibroPorId(idLibro);

        // Verificamos si el libro tiene un archivo PDF almacenado
        if (libro.getArchivoPdf() == null) {
            throw new EntityNotFoundException("Archivo PDF no encontrado para este libro.");
        }

        // Retornar el archivo PDF como respuesta
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=libro_" + libro.getIdLibro() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(libro.getArchivoPdf());
    }
}
