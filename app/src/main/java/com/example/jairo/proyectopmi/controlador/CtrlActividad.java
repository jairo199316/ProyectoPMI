package com.example.jairo.proyectopmi.controlador;

import android.app.Activity;

import com.example.jairo.proyectopmi.dao.ActividadDAO;
import com.example.jairo.proyectopmi.modelo.Actividad;
import com.example.jairo.proyectopmi.modelo.Proyecto;

import java.util.List;

/**
 * Created by JAIRO on 10/05/2017.
 */
public class CtrlActividad {


    /**
     * dao de la clase actividad
     */
    private ActividadDAO dao;

    /**
     * controlador de la clase
     *
     * @param activity
     */
    public CtrlActividad(Activity activity) {
        dao = new ActividadDAO(activity);
    }

    /**
     * guarda una acividad
     *
     * @param actividad la actividad a guardar
     * @return true si lo guarda, de lo contrario false
     */
    public boolean guardarActividad(Actividad actividad) {
        return dao.guardar(actividad);
    }

    /**
     * busca una actividad por nombre y proyecto
     *
     * @param nombre   nombre de la actividad
     * @param proyecto proyecto de la actividad
     * @return una actividad si lo encuentra, de lo contrario null
     */
    public Actividad buscarActividad(String nombre, Proyecto proyecto) {
        return dao.buscar(nombre, proyecto);
    }

    /**
     * busca una actividad por codigo
     *
     * @param codigo el codigo de la actividad a buscar
     * @return la actividad si la encuentra, de lo contrario null
     */
    public Actividad buscarActividad(int codigo) {
        return dao.buscar(codigo);
    }

    /**
     * elimina una actividad
     *
     * @param actividad la actividad a eliminar
     * @return true si lo elimina, de lo contrrio false
     */
    public boolean eliminarActividad(Actividad actividad) {
        return dao.eliminar(actividad);
    }

    /**
     * modifica una actividad
     *
     * @param actividad la actividad a modificar
     * @return true si lo modifica, de lo contrario false
     */
    public boolean modificarActividad(Actividad actividad) {
        return dao.modificar(actividad);
    }

    /**
     * lista todos las actividades registrados
     *
     * @return una lista con todas las actividades
     */
    public List<Actividad> listarActividades() {
        return dao.listar();
    }

    /**
     * lista todos las actividades registrados de un proyecto
     *
     * @param proyecto el proyecto de la actividad
     * @return una lista con todas las actividades
     */
    public List<Actividad> listarActividades(Proyecto proyecto) {
        return dao.listar(proyecto);
    }

}
