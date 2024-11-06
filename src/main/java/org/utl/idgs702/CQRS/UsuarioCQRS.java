package org.utl.idgs702.CQRS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utl.idgs702.DAO.UsuarioDAO;
import org.utl.idgs702.Entities.Usuario;

@Service
public class UsuarioCQRS {

    @Autowired
    private UsuarioDAO usuarioDAO;

    // Validación para el nombre del usuario
    public boolean validarNombre(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.length() < 2 || nombreUsuario.length() > 100) {
            System.out.println("Error: El nombre del usuario debe tener entre 2 y 100 caracteres.");
            return false;
        }
        return true;
    }

    // Validación para el apellido del usuario
    public boolean validarApellido(String apellidoUsuario) {
        if (apellidoUsuario == null || apellidoUsuario.length() < 2 || apellidoUsuario.length() > 100) {
            System.out.println("Error: El apellido del usuario debe tener entre 2 y 100 caracteres.");
            return false;
        }
        return true;
    }

    // Validación para el nombre de usuario
    public boolean validarUsuario(String usuario) {
        if (usuario == null || usuario.length() < 5 || usuario.length() > 30) {
            System.out.println("Error: El nombre de usuario debe tener entre 5 y 30 caracteres.");
            return false;
        }
        return true;
    }

    // Método para agregar un usuario
    public String agregarUsuario(Usuario usuario) {
        if (validarNombre(usuario.getNombre()) && 
            validarApellido(usuario.getApellidos()) && 
            validarUsuario(usuario.getUsuario())) {
            try {
                usuarioDAO.insertarUsuario(usuario);
                System.out.println("Usuario agregado exitosamente.");
                return "Usuario agregado exitosamente.";
            } catch (Exception e) {
                System.out.println("Error al agregar usuario: " + e.getMessage());
                return "Error al agregar usuario.";
            }
        } else {
            System.out.println("Error: Datos inválidos para agregar el usuario.");
            return "Error: Datos inválidos para agregar el usuario.";
        }
    }

    // Método para actualizar un usuario
    public String actualizarUsuario(Usuario usuario) {
        if (validarNombre(usuario.getNombre()) && 
            validarApellido(usuario.getApellidos()) && 
            validarUsuario(usuario.getUsuario())) {
            try {
                usuarioDAO.actualizarUsuario(usuario);
                System.out.println("Usuario actualizado exitosamente.");
                return "Usuario actualizado exitosamente.";
            } catch (Exception e) {
                System.out.println("Error al actualizar usuario: " + e.getMessage());
                return "Error al actualizar usuario.";
            }
        } else {
            System.out.println("Error: Datos inválidos para actualizar el usuario.");
            return "Error: Datos inválidos para actualizar el usuario.";
        }
    }
}
