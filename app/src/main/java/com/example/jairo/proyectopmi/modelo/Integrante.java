package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Laura on 27/04/2017.
 */

public class Integrante extends Usuario implements Serializable {

    private Cargo cargo;
    private Proyecto proyecto;

    public Integrante() {
    }

    public Integrante(Cargo cargo, Proyecto proyecto) {
        this.cargo = cargo;
        this.proyecto = proyecto;
    }

    public Integrante(String tipoDocumento, int documento, String nombre, String apellido, String password, String correo, String tipoUsuario, Cargo cargo, Proyecto proyecto) {
        super(tipoDocumento,documento, nombre, apellido, password, correo,tipoUsuario);
        this.cargo = cargo;
        this.proyecto = proyecto;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
