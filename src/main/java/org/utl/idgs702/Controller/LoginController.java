package org.utl.idgs702.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.utl.idgs702.DAO.UsuarioDAO;
import org.utl.idgs702.Entities.Usuario;

import com.google.gson.Gson;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UsuarioDAO usuarioService; 

    private Gson gson = new Gson();

    // Endpoint para login
    @PostMapping("/ingresar")
    public ResponseEntity<String> login(@RequestBody String loginJson) {
        try {
            Usuario usuarioLogin = gson.fromJson(loginJson, Usuario.class);
            Usuario usuario = usuarioService.login(usuarioLogin.getUsuario(), usuarioLogin.getContrasenia());
            if (usuario != null) {
                return new ResponseEntity<>(gson.toJson(usuario), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(gson.toJson("Usuario o contraseña incorrectos"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Error en el proceso de login"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
