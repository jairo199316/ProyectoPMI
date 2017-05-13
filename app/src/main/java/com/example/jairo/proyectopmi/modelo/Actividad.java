package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Laura on 27/04/2017.
 */

public class Actividad implements Serializable {

    private int id;
    private String nombre;
    private Integrante responsable;
    private Proyecto proyecto;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;

    public Actividad() {
    }

    public Actividad(int id, String nombre, Integrante responsable, Proyecto proyecto, String fechaInicio, String fechaFin, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.responsable = responsable;
        this.proyecto = proyecto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integrante getResponsable() {
        return responsable;
    }

    public void setResponsable(Integrante responsable) {
        this.responsable = responsable;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
