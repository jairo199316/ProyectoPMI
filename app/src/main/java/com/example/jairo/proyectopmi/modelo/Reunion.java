package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;

/**
 * Created by Laura on 27/04/2017.
 */

public class Reunion implements Serializable {

    private int id;
    private Ubicacion ubicacion;
    private String tematica;

    public Reunion() {

    }

    public Reunion(int id, Ubicacion ubicacion, String tematica) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.tematica = tematica;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }
}
