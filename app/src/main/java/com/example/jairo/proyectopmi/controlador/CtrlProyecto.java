package com.example.jairo.proyectopmi.controlador;

import android.app.Activity;

import com.example.jairo.proyectopmi.dao.ProyectoDAO;
import com.example.jairo.proyectopmi.modelo.Proyecto;
import com.example.jairo.proyectopmi.modelo.Usuario;

import java.util.List;

/**
 * Created by JAIRO on 7/05/2017.
 */
public class CtrlProyecto {

    /**
     * dao de la clase proyecto
     */
    private ProyectoDAO dao;

    /**
     * controlador de la clase
     *
     * @param activity
     */
    public CtrlProyecto(Activity activity) {
        dao = new ProyectoDAO(activity);
    }

    /**
     * guarda un proyecto
     *
     * @param id          id del proyecto
     * @param nombre      nombre del proyecto
     * @param fechaInicio fecha inicio del proyecto
     * @param fechaFin    fecha fin del proyecto
     * @param duracion    duracion del proyecto
     * @param etapa       etapa del proyecto
     * @return true si lo puede registrar, de lo contrario false
     */
    public boolean guardarProyecto(int id, String nombre, String fechaInicio, String fechaFin, double duracion, int etapa, int usuario) {
        Proyecto proyecto = new Proyecto(id, nombre, fechaInicio, fechaFin, duracion, etapa, usuario);
        return dao.guardar(proyecto);
    }

    /**
     * busca un proyecto por nombre
     *
     * @param nombre el nombre del proyecto a buscar
     * @return el proyecto si lo encuentra, de lo contrario null
     */
    public Proyecto buscarProyecto(String nombre) {
        return dao.buscar(nombre);
    }

    /**
     * busca un proyecto por codigo
     *
     * @param codigo el codigo del proyecto a buscar
     * @return el proyecto si lo encuentra, de lo contrario null
     */
    public Proyecto buscarProyecto(int codigo) {
        return dao.buscar(codigo);
    }


    public Proyecto buscarProyectoUsuario(int usuario, String nombre){
        return dao.buscarProyectoUsuario(usuario, nombre);
    }

    public Proyecto buscarProyectoUsuario(int usuario){
        return dao.buscarProyectoUsuario(usuario);
    }

    /**
     * elimina un proyecto por nombre
     *
     * @param nombre el nombre del proyecto
     * @return true si lo elimina, de lo contrrio false
     */
    public boolean eliminarProyecto(String nombre) {
        Proyecto proyecto = new Proyecto(0, nombre, "", "", 0, 0, 0);
        return dao.eliminar(proyecto);
    }

    /**
     * modifica un proyecto
     *
     * @param id          id del proyecto
     * @param nombre      nombre del proyecto
     * @param fechaInicio fecha inicio del proyecto
     * @param fechaFin    fecha fin del proyecto
     * @param duracion    duracion del proyecto
     * @param etapa       etapa del proyecto
     * @return true si lo puede registrar, de lo contrario false
     */
    public boolean modificarProyecto(int id, String nombre, String fechaInicio, String fechaFin, double duracion, int etapa, int usuario) {
        Proyecto proyecto = new Proyecto(id, nombre, fechaInicio, fechaFin, duracion, etapa, usuario);
        return dao.modificar(proyecto);
    }

    /**
     * lista todos los proyectos registrados
     *
     * @return una lista con todos los proyectos
     */
    public List<Proyecto> listarProyectos() {
        return dao.listar();
    }
}
