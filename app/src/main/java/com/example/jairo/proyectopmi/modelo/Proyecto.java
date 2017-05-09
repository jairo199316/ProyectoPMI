package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Laura on 27/04/2017.
 */

public class Proyecto implements Serializable {


    private int id;
    private int usuario;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private double duracion;
    private int etapa;

    public Proyecto() {
    }

    public Proyecto(int id, String nombre, String fechaInicio, String fechaFin, double duracion, int etapa, int usuario) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracion = duracion;
        this.etapa = etapa;
        this.usuario = usuario;
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

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
}
