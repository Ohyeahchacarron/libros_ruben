package org.utl.idgs702.AppService;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.utl.idgs702.ViewModel.LibroPublic;

import java.util.Arrays;
import java.util.List;

@Service
public class LibrosExternosAppService {

    private final String URL_API_EXTERNA = "http://192.168.188.114:8081/api/libros/obtenerLibros";
    private final RestTemplate restTemplate; 

    public LibrosExternosAppService() {
        this.restTemplate = new RestTemplate(); 
    }

    public List<LibroPublic> obtenerLibrosExternos() {
      
        LibroPublic[] librosArray = restTemplate.getForObject(URL_API_EXTERNA, LibroPublic[].class);
    
        System.out.println("Respuesta JSON:");
        System.out.println(Arrays.toString(librosArray));
    
        // Asegúrate de manejar el caso en que la respuesta sea null
        if (librosArray == null) {
            return Arrays.asList(); 
        }
    
        // Convierte el array a una lista y la retorna
        List<LibroPublic> librosList = Arrays.asList(librosArray);
        
        // Imprimir los datos para verificar
        System.out.println("Lista de Libros:");
        for (LibroPublic libro : librosList) {
            System.out.println("ID: " + libro.getId_libro() +
                               ", Nombre: " + libro.getNombre_libro() +
                               ", Autor: " + libro.getAutor_libro() +
                               ", Género: " + libro.getGenero_libro() +
                               ", Ruta PDF: " + libro.getRuta_pdf()); 
        }
    
        return librosList;
    }

    public static void main(String[] args) {
        LibrosExternosAppService servicio = new LibrosExternosAppService();
        List<LibroPublic> librosExternos = servicio.obtenerLibrosExternos();
        System.out.println("Libros Externos Obtenidos: " + librosExternos.size());
    }
}
