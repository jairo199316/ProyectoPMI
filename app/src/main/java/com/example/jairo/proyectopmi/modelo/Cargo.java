package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;

/**
 * Created by Laura on 27/04/2017.
 */

public class Cargo implements Serializable {

    private int id;
    private String nombre;
    private String descripcion;
    private double salario;
    private String horario;
    private Proyecto proyecto;

    public Cargo() {
    }

    public Cargo(int id, String nombre, String descripcion, double salario, String horario, Proyecto proyecto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.salario = salario;
        this.horario = horario;
        this.proyecto = proyecto;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
