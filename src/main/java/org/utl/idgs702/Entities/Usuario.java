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
    
    @Column(name= "apellido_p", nullable = false)
    private String apellidoP;
    
    @Column(name= "apellido_m")
    private String apellidoM;
    
    @Column(nullable = false)
    private String contrasenia;

	public Usuario() {
		super();
	}

	public Usuario(int idUsuario, String usuario, String nombre, String apellidoP, String apellidoM,
			String contrasenia) {
		super();
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		this.nombre = nombre;
		this.apellidoP = apellidoP;
		this.apellidoM = apellidoM;
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

	public String getApellidoP() {
		return apellidoP;
	}

	public void setApellidoP(String apellidoP) {
		this.apellidoP = apellidoP;
	}

	public String getApellidoM() {
		return apellidoM;
	}

	public void setApellidoM(String apellidoM) {
		this.apellidoM = apellidoM;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public String toString() {
		return "Usuarios [idUsuario=" + idUsuario + ", usuario=" + usuario + ", nombre=" + nombre + ", apellidoP="
				+ apellidoP + ", apellidoM=" + apellidoM + ", contrasenia=" + contrasenia + "]";
	}

	
}

