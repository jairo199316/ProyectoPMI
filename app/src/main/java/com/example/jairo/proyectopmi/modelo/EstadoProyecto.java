package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;

/**
 * Created by Laura on 27/04/2017.
 */

public class EstadoProyecto implements Serializable {

    private Proyecto proyecto;
    private double porcentaje;
    private String descripcion;

    public EstadoProyecto() {
    }

    public EstadoProyecto(Proyecto proyecto, double porcentaje, String descripcion) {
        this.proyecto = proyecto;
        this.porcentaje = porcentaje;
        this.descripcion = descripcion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
