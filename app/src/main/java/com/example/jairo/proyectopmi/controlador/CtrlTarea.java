package com.example.jairo.proyectopmi.controlador;

import android.app.Activity;


import com.example.jairo.proyectopmi.dao.TareaDAO;
import com.example.jairo.proyectopmi.modelo.Actividad;
import com.example.jairo.proyectopmi.modelo.Tarea;

import java.util.List;

/**
 * Created by JAIRO on 11/05/2017.
 */
public class CtrlTarea {

    /**
     * dao de la clase tarea
     */
    private TareaDAO dao;

    /**
     * controlador de la clase
     *
     * @param activity
     */
    public CtrlTarea(Activity activity) {
        dao = new TareaDAO(activity);
    }

    /**
     * guarda una acividad
     *
     * @param tarea la tarea a guardar
     * @return true si lo guarda, de lo contrario false
     */
    public boolean guardarTarea(Tarea tarea) {
        return dao.guardar(tarea);
    }

    /**
     * busca una tarea por nombre y actividad
     *
     * @param nombre    nombre de la tarea
     * @param actividad actividad de la tarea
     * @return una Tarea si la encuentra, de lo contrario null
     */
    public Tarea buscarTarea(String nombre, Actividad actividad) {
        return dao.buscar(nombre, actividad);
    }

    /**
     * busca una tarea por codigo
     *
     * @param codigo el codigo de la tarea a buscar
     * @return la tarea si la encuentra, de lo contrario null
     */
    public Tarea buscarTarea(int codigo) {
        return dao.buscar(codigo);
    }

    /**
     * elimina una tarea
     *
     * @param tarea la tarea a eliminar
     * @return true si lo elimina, de lo contrrio false
     */
    public boolean eliminarTarea(Tarea tarea) {
        return dao.eliminar(tarea);
    }

    /**
     * modifica una tarea
     *
     * @param tarea la tarea a modificar
     * @return true si lo modifica, de lo contrario false
     */
    public boolean modificarTarea(Tarea tarea) {
        return dao.modificar(tarea);
    }

    /**
     * lista todos las tarea registrados
     *
     * @return una lista con todas las tareas
     */
    public List<Tarea> listarTareas() {
        return dao.listar();
    }

    /**
     * lista todos las tareas registrados de una actividad
     *
     * @param actividadId el actividadId de la Tarea
     * @return una lista con todas las tareas
     */
    public List<Tarea> listarTareas(int actividadId) {
        return dao.listar(actividadId);
    }


}
