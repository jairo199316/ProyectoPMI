package com.example.jairo.proyectopmi.modelo;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Laura on 27/04/2017.
 */

public class Tarea implements Serializable {

    private int id;
    private String nombre;
    private double porcentaje;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private Actividad actividad;


    public Tarea() {
    }

    public Tarea(int id, String nombre, double porcentaje, String fechaInicio, String fechaFin, String estado, Actividad actividad) {
        this.id = id;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.actividad = actividad;
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

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
}
