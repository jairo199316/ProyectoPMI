package com.example.jairo.proyectopmi.controlador;

import android.app.Activity;

import com.example.jairo.proyectopmi.dao.RecursoDAO;
import com.example.jairo.proyectopmi.modelo.Proyecto;
import com.example.jairo.proyectopmi.modelo.Recurso;

import java.util.List;

/**
 * Created by JAIRO on 13/05/2017.
 */
public class CtrlRecurso {


    /**
     * dao de la clase recurso
     */
    private RecursoDAO dao;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public CtrlRecurso(Activity activity) {
        dao = new RecursoDAO(activity);
    }

    /**
     * registra un recurso
     *
     * @param recurso el recurso a registrar
     * @return true si lo registra, de lo ocntrario false
     */
    public boolean guardarRecurso(Recurso recurso) {
        return dao.guardar(recurso);
    }

    /**
     * busca un recurso por nombre y proyecto
     *
     * @param nombre   el nombre del recurso
     * @param proyecto el proyecto del recurso
     * @return el recurso si lo encuentra, de lo contrario null
     */
    public Recurso buscarRecurso(String nombre, Proyecto proyecto) {
        return dao.buscar(nombre, proyecto);
    }

    /**
     * elimina un recurso
     *
     * @param recurso el recurso a eliminar
     * @return true si lo elimina, de lo contrario false
     */
    public boolean eliminarRecurso(Recurso recurso) {
        return dao.eliminar(recurso);
    }

    /**
     * modifica un recurso
     *
     * @param recurso el recurso a modificar
     * @return rue si lo modifica, de lo contrario false
     */
    public boolean modificarRecurso(Recurso recurso) {
        return dao.modificar(recurso);
    }

    /**
     * lista los recursos registrados
     *
     * @return una lista con los recursos registrados
     */
    public List<Recurso> listarRecurso() {
        return dao.listar();
    }


}
