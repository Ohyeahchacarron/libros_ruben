package org.utl.idgs702.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    
    @Column(name = "usuario")
    private String usuario;
    
    @Column
    private String nombre;
    
    @Column(name= "apellidos", nullable = false)
    private String apellidos;
    
    @Column(name= "perfil")
    private String perfil;
    
    @Column(nullable = false)
    private String contrasenia;

	public Usuario() {
		super();
	}

	public Usuario(int idUsuario, String usuario, String nombre, String apellidos, String perfil,
			String contrasenia) {
		super();
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.perfil = perfil;
		this.contrasenia = contrasenia;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", usuario=" + usuario + ", nombre=" + nombre + ", apellidos="
				+ apellidos + ", perfil=" + perfil + ", contrasenia=" + contrasenia + "]";
	}

	
}

