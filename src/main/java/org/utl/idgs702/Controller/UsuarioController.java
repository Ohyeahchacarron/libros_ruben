package org.utl.idgs702.Controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utl.idgs702.Entities.Usuario;
import org.utl.idgs702.DAOLibros.UsuarioDAO;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioService;
    
    // Gson para manejar la conversión de objetos a JSON
    private Gson gson = new Gson();

    // Endpoint para insertar un nuevo usuario
    @PostMapping("/insertar")
    public ResponseEntity<String> insertarUsuario(@RequestBody String usuarioJson) {
        try {
            // Convertir JSON a objeto Usuario
            Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);
            Usuario nuevoUsuario = usuarioService.insertarUsuario(usuario);
            return new ResponseEntity<>(gson.toJson(nuevoUsuario), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Error al insertar usuario"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para eliminar un usuario por su ID
    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int idUsuario) {
        boolean eliminado = usuarioService.eliminarUsuario(idUsuario);
        if (eliminado) {
            return new ResponseEntity<>(gson.toJson("Usuario eliminado"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(gson.toJson("Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para mostrar todos los usuarios
    @GetMapping("/listar")
    public ResponseEntity<String> mostrarTodos() {
        List<Usuario> usuarios = usuarioService.mostrarTodos();
        return new ResponseEntity<>(gson.toJson(usuarios), HttpStatus.OK);
    }

    // Endpoint para actualizar un usuario existente
    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable int idUsuario, @RequestBody String usuarioJson) {
        try {
            // Convertir JSON a objeto Usuario
            Usuario usuarioActualizado = gson.fromJson(usuarioJson, Usuario.class);
            usuarioActualizado.setIdUsuario(idUsuario);  // Asignar ID del usuario a actualizar
            Usuario usuario = usuarioService.actualizarUsuario(usuarioActualizado);
            return new ResponseEntity<>(gson.toJson(usuario), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Error al actualizar usuario"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// Endpoint para login
@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody String loginJson) {
    try {
        // Descomponer el JSON en usuario y contrasenia directamente
        Usuario usuarioLogin = gson.fromJson(loginJson, Usuario.class);
        
        // Llamar al servicio para verificar si las credenciales son correctas
        Usuario usuario = usuarioService.login(usuarioLogin.getUsuario(), usuarioLogin.getContrasenia());
        
        // Verificar si se encontró el usuario
        if (usuario != null) {
            // Si el usuario y contraseña coinciden, devolver los datos del usuario
            return new ResponseEntity<>(gson.toJson(usuario), HttpStatus.OK);
        } else {
            // Si las credenciales no coinciden, devolver un mensaje de error
            System.out.println("Credenciales incorrectas");  // Imprimir mensaje en el servidor
            return new ResponseEntity<>(gson.toJson("Usuario o contraseña incorrectos"), HttpStatus.UNAUTHORIZED);
        }
    } catch (Exception e) {
        // Manejar cualquier otro error en el proceso de login
        return new ResponseEntity<>(gson.toJson("Error en el proceso de login"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
