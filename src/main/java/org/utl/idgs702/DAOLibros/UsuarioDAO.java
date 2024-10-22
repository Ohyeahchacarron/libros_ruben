package org.utl.idgs702.DAOLibros;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utl.idgs702.Entities.Usuario;
import org.utl.idgs702.Repository.UsuarioRepository;

@Service
public class UsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para insertar un nuevo usuario
    public Usuario insertarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para eliminar un usuario por su ID
    public boolean eliminarUsuario(int idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return true; 
        }
        return false;
    }

    // Método para mostrar todos los usuarios
    public List<Usuario> mostrarTodos() {
        return usuarioRepository.findAll();
    }

    // Método para actualizar un usuario existente
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para login que devuelve un objeto simplificado (solo los datos necesarios)
    public Usuario login(String usuario, String contrasenia) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsuarioAndContrasenia(usuario, contrasenia);
        return usuarioEncontrado.orElse(null); // Retorna el usuario si existe, o null si no
    }
}
