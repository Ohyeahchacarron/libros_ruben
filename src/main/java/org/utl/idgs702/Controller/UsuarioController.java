package org.utl.idgs702.Controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utl.idgs702.CQRS.UsuarioCQRS;
import org.utl.idgs702.DAO.UsuarioDAO;
import org.utl.idgs702.Entities.Usuario;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioCQRS usuarioCQRS; 

    @Autowired
    private UsuarioDAO usuarioService; 

    private Gson gson = new Gson();

    // Endpoint para crear un nuevo usuario
    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(
            @RequestBody Usuario usuario) {
        try {
            String mensaje = usuarioCQRS.agregarUsuario(usuario);
            return new ResponseEntity<>(gson.toJson(mensaje), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Error al crear usuario: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para actualizar un usuario existente
    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<String> actualizarUsuario(
            @PathVariable int idUsuario,
            @RequestBody Usuario usuario) {
        try {
            usuario.setIdUsuario(idUsuario); // Asigna el ID del usuario a actualizar
            // Llama a la lógica de actualización en UsuarioCQRS
            String mensaje = usuarioCQRS.actualizarUsuario(usuario);
            return new ResponseEntity<>(gson.toJson(mensaje), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Error al actualizar usuario: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para eliminar un usuario por su ID
    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int idUsuario) {
        try {
            usuarioService.eliminarUsuario(idUsuario);
            return new ResponseEntity<>(gson.toJson("Usuario eliminado con éxito"), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Error al eliminar usuario"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para listar todos los usuarios
    @GetMapping("/listar")
    public ResponseEntity<String> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.mostrarTodos();
        return new ResponseEntity<>(gson.toJson(usuarios), HttpStatus.OK);
    }

}
