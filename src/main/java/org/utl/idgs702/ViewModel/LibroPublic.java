package org.utl.idgs702.ViewModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibroPublic {

    @JsonProperty("id_libro")
    private int id_libro;

    @JsonProperty("nombre")
    private String nombre_libro;

    @JsonProperty("autor")
    private String autor_libro;

    @JsonProperty("genero")
    private String genero_libro;

    @JsonProperty("ruta")
    private String ruta_pdf; // Nuevo atributo para la ruta del PDF

    private byte[] archivo_pdf;

    // Getters y Setters

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public String getNombre_libro() {
        return nombre_libro;
    }

    public void setNombre_libro(String nombre_libro) {
        this.nombre_libro = nombre_libro;
    }

    public String getAutor_libro() {
        return autor_libro;
    }

    public void setAutor_libro(String autor_libro) {
        this.autor_libro = autor_libro;
    }

    public String getGenero_libro() {
        return genero_libro;
    }

    public void setGenero_libro(String genero_libro) {
        this.genero_libro = genero_libro;
    }

    public String getRuta_pdf() { // Getter para la ruta del PDF
        return ruta_pdf;
    }

    public void setRuta_pdf(String ruta_pdf) { // Setter para la ruta del PDF
        this.ruta_pdf = ruta_pdf;
    }

    public byte[] getArchivo_pdf() {
        return archivo_pdf;
    }

    public void setArchivo_pdf(byte[] archivo_pdf) {
        this.archivo_pdf = archivo_pdf;
    }

    
}
