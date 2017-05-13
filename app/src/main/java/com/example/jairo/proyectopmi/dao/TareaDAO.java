package com.example.jairo.proyectopmi.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.jairo.proyectopmi.controlador.CtrlActividad;
import com.example.jairo.proyectopmi.infraestructura.Conexion;
import com.example.jairo.proyectopmi.modelo.Actividad;
import com.example.jairo.proyectopmi.modelo.Tarea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAIRO on 11/05/2017.
 */
public class TareaDAO {

    /**
     * clase conexion
     */
    private Conexion conex;

    /**
     * controlador de la clase actividad
     */
    private CtrlActividad ctrlActividad;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public TareaDAO(Activity activity) {
        conex = new Conexion(activity);
        ctrlActividad = new CtrlActividad(activity);
    }

    /**
     * guarda una tarea en la bd
     *
     * @param tarea la tarea a guardar
     * @return true si lo puede guardar, de lo contrario false
     */
    public boolean guardar(Tarea tarea) {
        ContentValues registro = new ContentValues();
        registro.put("id", tarea.getId());
        registro.put("nombre", tarea.getNombre());
        registro.put("porcentaje", tarea.getPorcentaje());
        registro.put("fechaInicio", tarea.getFechaInicio());
        registro.put("fechaFin", tarea.getFechaFin());
        registro.put("estado", tarea.getEstado());
        registro.put("actividad_id", tarea.getActividad().getId());
        return conex.ejecutarInsert("Tarea", registro);
    }


    /**
     * busca una tarea por nombre y actividad
     *
     * @param nombre    el nombre de la tarea a buscar
     * @param actividad la actividad de la tarea a buscar
     * @return la tarea si la encuentra de lo contrario null
     */
    public Tarea buscar(String nombre, Actividad actividad) {
        Tarea tarea = null;
        String consulta = "select id, nombre, porcentaje, fechaInicio, fechaFin, estado, actividad_id from Tarea where "
                + "nombre = '" + nombre + "' and actividad_id = " + actividad.getId() + "";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            tarea = new Tarea(temp.getInt(0), temp.getString(1), temp.getDouble(2), temp.getString(3), temp.getString(4), temp.getString(5), actividad);
        }
        conex.cerrarConexion();
        return tarea;
    }

    /**
     * busca una tarea por nombre
     *
     * @param codigo el codigo de la tarea a buscar
     * @return la tarea si la encuentra de lo contrario null
     */
    public Tarea buscar(int codigo) {
        Tarea tarea = null;
        String consulta = "select id, nombre, porcentaje, fechaInicio, fechaFin, estado, actividad_id from Tarea where "
                + "id = '" + codigo + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            Actividad actividad = ctrlActividad.buscarActividad(temp.getInt(6));
            tarea = new Tarea(temp.getInt(0), temp.getString(1), temp.getDouble(2), temp.getString(3), temp.getString(4), temp.getString(5), actividad);
        }
        conex.cerrarConexion();
        return tarea;
    }


    /**
     * elimina una tarea en la bd
     *
     * @param tarea la tearea a eliminar
     * @return true si lo elimina, de lo contrario false
     */
    public boolean eliminar(Tarea tarea) {
        String tabla = "Tarea";
        String condicion = "nombre='" + tarea.getNombre() + "' and actividad_id='" + tarea.getActividad().getId() + "'";
        return conex.ejecutarDelete(tabla, condicion);
    }

    /**
     * modifica un tarea
     *
     * @param tarea el tarea a modificar
     * @return true si lo modifica, de lo contrario false
     */
    public boolean modificar(Tarea tarea) {
        String tabla = "Tarea";
        String condicion = "nombre='" + tarea.getNombre() + "' and actividad_id='" + tarea.getActividad().getId() + "'";
        ContentValues registro = new ContentValues();
        registro.put("nombre", tarea.getNombre());
        registro.put("porcentaje", tarea.getPorcentaje());
        registro.put("fechaInicio", tarea.getFechaInicio());
        registro.put("fechaFin", tarea.getFechaFin());
        registro.put("estado", tarea.getEstado());
        registro.put("actividad_id", tarea.getActividad().getId());
        return conex.ejecutarUpdate(tabla, condicion, registro);

    }

    /**
     * lista las tareas registrados
     *
     * @return una lista con las tareas registrados
     */
    public List<Tarea> listar() {
        List<Tarea> listaTareas = new ArrayList<>();
        String consulta = "select id, nombre, porcentaje, fechaInicio, fechaFin, estado, actividad_id from Tarea";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp != null) {
            if (temp.moveToFirst()) {
                do {
                    Actividad actividad = ctrlActividad.buscarActividad(temp.getInt(6));
                    Tarea tarea = new Tarea(temp.getInt(0), temp.getString(1), temp.getDouble(2), temp.getString(3), temp.getString(4), temp.getString(5), actividad);
                    listaTareas.add(tarea);
                } while (temp.moveToNext());
            }
        }
        return listaTareas;
    }

    /**
     * lista las tareas registrados
     *
     * @return una lista con las tareas registrados
     */
    public List<Tarea> listar(int actividadId) {
        List<Tarea> listaTareas = new ArrayList<>();
        String consulta = "select id, nombre, porcentaje, fechaInicio, fechaFin, estado, actividad_id from Tarea where actividad_id=" + actividadId;
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp != null) {
            if (temp.moveToFirst()) {
                do {
                    Actividad actividad = ctrlActividad.buscarActividad(temp.getInt(6));
                    Tarea tarea = new Tarea(temp.getInt(0), temp.getString(1), temp.getDouble(2), temp.getString(3), temp.getString(4), temp.getString(5), actividad);
                    listaTareas.add(tarea);
                } while (temp.moveToNext());
            }
        }
        return listaTareas;
    }


}
