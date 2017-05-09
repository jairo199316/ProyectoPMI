package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;

/**
 * Created by Laura on 27/04/2017.
 */

public class Ubicacion implements Serializable{

    private int id;
    private double latitud;
    private double longitud;

    public Ubicacion() {
    }

    public Ubicacion(int id, double latitud, double longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
