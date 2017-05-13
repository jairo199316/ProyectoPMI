package com.example.jairo.proyectopmi.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.jairo.proyectopmi.controlador.CtrlIntegrante;
import com.example.jairo.proyectopmi.controlador.CtrlProyecto;
import com.example.jairo.proyectopmi.infraestructura.Conexion;
import com.example.jairo.proyectopmi.modelo.Actividad;
import com.example.jairo.proyectopmi.modelo.Integrante;
import com.example.jairo.proyectopmi.modelo.Proyecto;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JAIRO on 10/05/2017.
 */
public class ActividadDAO {

    /**
     * clase conexion
     */
    private Conexion conex;

    /**
     * controlador del proyecto
     */
    private CtrlProyecto proye;

    /**
     * controlador del usuario
     */
    private CtrlIntegrante ctrlIntegrante;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public ActividadDAO(Activity activity) {
        conex = new Conexion(activity);
        proye = new CtrlProyecto(activity);
        ctrlIntegrante = new CtrlIntegrante(activity);
    }

    /**
     * guarda un actividad en la bd
     *
     * @param actividad la actividad a guardar
     * @return true si lo puede guardar, de lo contrario false
     */
    public boolean guardar(Actividad actividad) {
        ContentValues registro = new ContentValues();
        registro.put("id", actividad.getId());
        registro.put("nombre", actividad.getNombre());
        registro.put("responsable_id", actividad.getResponsable().getDocumento());
        registro.put("proyecto_id", actividad.getProyecto().getId());
        registro.put("fechaInicio", actividad.getFechaInicio());
        registro.put("fechaFin", actividad.getFechaFin());
        registro.put("descripcion", actividad.getDescripcion());
        return conex.ejecutarInsert("Actividad", registro);
    }


    /**
     * busca un actividad por nombre
     *
     * @param nombre el nombre de la actividad a buscar
     * @return la actividad si la encuentra de lo contrario null
     */
    public Actividad buscar(String nombre, Proyecto proyecto) {
        Actividad actividad = null;
        String consulta = "select id, nombre, responsable_id, proyecto_id, fechaInicio, fechaFin, descripcion from Actividad where "
                + " nombre = '" + nombre + "' and proyecto_id = " + proyecto.getId() + "";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            Integrante integrante = ctrlIntegrante.buscarIntegrante(temp.getInt(2));
            actividad = new Actividad(temp.getInt(0), temp.getString(1), integrante, proyecto, temp.getString(4), temp.getString(5), temp.getString(6));
        }
        conex.cerrarConexion();
        return actividad;
    }

    /**
     * busca una actividad por codigo
     *
     * @param codigo el codigo de la actividad a buscar
     * @return la actividad si la encuentra, de lo contrario null
     */
    public Actividad buscar(int codigo) {
        Actividad actividad = null;
        String consulta = "select id, nombre, responsable_id, proyecto_id, fechaInicio, fechaFin, descripcion from Actividad where "
                + " id = '" + codigo + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            Integrante integrante = ctrlIntegrante.buscarIntegrante(temp.getInt(2));
            Proyecto proyecto = proye.buscarProyecto(temp.getInt(3));
            actividad = new Actividad(temp.getInt(0), temp.getString(1), integrante, proyecto, temp.getString(4), temp.getString(5), temp.getString(6));
        }
        conex.cerrarConexion();
        return actividad;
    }

    /**
     * elimina una actividad en la bd
     *
     * @param actividad la actividad a eliminar
     * @return true si lo elimina, de lo contrario false
     */
    public boolean eliminar(Actividad actividad) {
        String tabla = "Actividad";
        String condicion = "nombre='" + actividad.getNombre() + "' and proyecto_id='" + actividad.getProyecto().getId() + "'";
        return conex.ejecutarDelete(tabla, condicion);
    }

    /**
     * modifica una actividad
     *
     * @param actividad la actividad a modificar
     * @return true si lo modifica, de lo contrario false
     */
    public boolean modificar(Actividad actividad) {
        String tabla = "Actividad";
        String condicion = "nombre='" + actividad.getNombre() + "' and proyecto_id='" + actividad.getProyecto().getId() + "'";
        ContentValues registro = new ContentValues();
        registro.put("nombre", actividad.getNombre());
        registro.put("responsable_id", actividad.getResponsable().getDocumento());
        registro.put("proyecto_id", actividad.getProyecto().getId());
        registro.put("fechaInicio", actividad.getFechaInicio());
        registro.put("fechaFin", actividad.getFechaFin());
        registro.put("descripcion", actividad.getDescripcion());
        return conex.ejecutarUpdate(tabla, condicion, registro);
    }


    /**
     * lista los proyectos registrados
     *
     * @return una lista con los ptoyectos registrados
     */
    public List<Actividad> listar() {
        List<Actividad> listaActividads = new ArrayList<>();
        String consulta = "select id, nombre, responsable_id, proyecto_id, fechaInicio, fechaFin, descripcion from Actividad";
        Cursor temp = conex.ejecutarSearch(consulta);

        if (temp != null) {
            if (temp.moveToFirst()) {
                do {
                    Proyecto proyecto = proye.buscarProyecto(temp.getInt(3));
                    Integrante integrante = ctrlIntegrante.buscarIntegrante(temp.getInt(2));
                    Actividad actividad = new Actividad(temp.getInt(0), temp.getString(1), integrante, proyecto, temp.getString(4), temp.getString(5), temp.getString(6));
                    listaActividads.add(actividad);
                } while (temp.moveToNext());
            }
        }
        return listaActividads;
    }


    /**
     * lista todos las actividades registrados de un proyecto
     *
     * @param proyecto el proyecto de la actividad
     * @return una lista con todas las actividades
     */
    public List<Actividad> listar(Proyecto proyecto) {
        List<Actividad> listaActividads = new ArrayList<>();
        String consulta = "select id, nombre, responsable_id, proyecto_id, fechaInicio, fechaFin, descripcion from Actividad where proyecto_id=" + proyecto.getId();
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp != null) {
            if (temp.moveToFirst()) {
                do {
                    Integrante integrante = ctrlIntegrante.buscarIntegrante(temp.getInt(2));
                    Actividad actividad = new Actividad(temp.getInt(0), temp.getString(1), integrante, proyecto, temp.getString(4), temp.getString(5), temp.getString(6));
                    listaActividads.add(actividad);
                } while (temp.moveToNext());
            }
        }
        return listaActividads;
    }
}
