package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Laura on 27/04/2017.
 */

public class Usuario implements Serializable {

    private String tipoDocumento;
    private int documento;
    private String nombre;
    private String apellido;
    private String password;
    private String correo;
    private String tipoUsuario;

    public Usuario() {
    }

    public Usuario(String tipoDocumento, int documento, String nombre, String apellido, String password, String correo, String tipoUsuario) {
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
