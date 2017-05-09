package com.example.jairo.proyectopmi.modelo;

import java.io.Serializable;

/**
 * Created by Laura on 27/04/2017.
 */

public class TareaRecurso implements Serializable {

    private int id;
    private Recurso recurso;
    private Tarea tarea;


    public TareaRecurso() {
    }

    public TareaRecurso(int id, Recurso recurso, Tarea tarea) {
        this.id = id;
        this.recurso = recurso;
        this.tarea = tarea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }
}
