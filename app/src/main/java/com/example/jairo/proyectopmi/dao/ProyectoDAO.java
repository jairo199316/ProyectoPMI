package com.example.jairo.proyectopmi.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.jairo.proyectopmi.infraestructura.Conexion;
import com.example.jairo.proyectopmi.modelo.Proyecto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAIRO on 7/05/2017.
 */
public class ProyectoDAO {
    /**
     * clase conexion
     */
    private Conexion conex;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public ProyectoDAO(Activity activity) {
        conex = new Conexion(activity);
    }

    /**
     * guarda un proyecto en la bd
     *
     * @param proyecto el proyecto a guardar
     * @return true si lo puede guardar, de lo contrario false
     */
    public boolean guardar(Proyecto proyecto) {
        Log.i("Cedula-----", proyecto.getUsuario() + "");
        ContentValues registro = new ContentValues();
        registro.put("id", proyecto.getId());
        registro.put("nombre", proyecto.getNombre());
        registro.put("duracion", proyecto.getDuracion());
        registro.put("etapa", proyecto.getEtapa());
        registro.put("fechaInicio", proyecto.getFechaInicio());
        registro.put("fechaFin", proyecto.getFechaFin());
        registro.put("cedula_usuario", proyecto.getUsuario());
        return conex.ejecutarInsert("Proyecto", registro);
    }

    /**
     * busca un proyecto por nombre
     *
     * @param nombre el nombre del proyecto a buscar
     * @return el proyecto si lo encuentram de lo contrario null
     */
    public Proyecto buscar(String nombre) {
        Proyecto proyecto = null;
        String consulta = "select id, nombre, fechaInicio, fechaFin, duracion, etapa, cedula_usuario from Proyecto where "
                + " nombre = '" + nombre + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            proyecto = new Proyecto(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3), temp.getDouble(4), temp.getInt(5), temp.getInt(6));
        }
        conex.cerrarConexion();
        return proyecto;
    }

    /**
     * busca un proyecto por nombre
     *
     * @param codigo el nombre del proyecto a buscar
     * @return el proyecto si lo encuentram de lo contrario null
     */
    public Proyecto buscar(int codigo) {
        Proyecto proyecto = null;
        String consulta = "select id, nombre, fechaInicio, fechaFin, duracion, etapa, cedula_usuario from Proyecto where "
                + " id = '" + codigo + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            proyecto = new Proyecto(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3), temp.getDouble(4), temp.getInt(5), temp.getInt(6));
        }
        conex.cerrarConexion();
        return proyecto;
    }

    /**
     * busca un proyecto de un usuario
     *
     * @param usuario el usuario dueño del proyecto
     * @return el proyecto si lo encuentra, de lo contrario null
     */
    public Proyecto buscarProyectoUsuario(int usuario, String nombre) {
        Proyecto proyecto = null;
        Log.i("cedulaDao", usuario + "");
        String consulta = "select id, nombre, fechaInicio, fechaFin, duracion, etapa, cedula_usuario from Proyecto where cedula_usuario = '" + usuario + "' and nombre='" + nombre + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            proyecto = new Proyecto(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3), temp.getDouble(4), temp.getInt(5), temp.getInt(6));
        }
        conex.cerrarConexion();
        return proyecto;
    }

    /**
     * busca un proyecto de un usuario
     *
     * @param usuario el usuario dueño del proyecto
     * @return el proyecto si lo encuentra, de lo contrario null
     */
    public Proyecto buscarProyectoUsuario(int usuario) {
        Proyecto proyecto = null;
        Log.i("cedulaDao", usuario + "");
        String consulta = "select id, nombre, fechaInicio, fechaFin, duracion, etapa, cedula_usuario from Proyecto where cedula_usuario = '" + usuario + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            proyecto = new Proyecto(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3), temp.getDouble(4), temp.getInt(5), temp.getInt(6));
        }
        conex.cerrarConexion();
        return proyecto;
    }


    /**
     * elimina un proyecto en la bd
     *
     * @param proyecto el proyecto a eliminar
     * @return true si lo elimina, de lo contrario false
     */
    public boolean eliminar(Proyecto proyecto) {
        String tabla = "Proyecto";
        String condicion = "nombre='" + proyecto.getNombre() + "'";
        return conex.ejecutarDelete(tabla, condicion);
    }

    /**
     * modifica un proyecto
     *
     * @param proyecto el proyecto a modificar
     * @return true si lo modifica, de lo contrario false
     */
    public boolean modificar(Proyecto proyecto) {
        String tabla = "Proyecto";
        String condicion = "nombre='" + proyecto.getNombre() + "'";
        ContentValues registro = new ContentValues();
        registro.put("nombre", proyecto.getNombre());
        registro.put("duracion", proyecto.getDuracion());
        registro.put("etapa", proyecto.getEtapa());
        registro.put("fechaInicio", proyecto.getFechaInicio());
        registro.put("fechaFin", proyecto.getFechaFin());
        registro.put("cedula_usuario", proyecto.getUsuario());
        return conex.ejecutarUpdate(tabla, condicion, registro);
    }

    /**
     * lista los proyectos registrados
     *
     * @return una lista con los ptoyectos registrados
     */
    public List<Proyecto> listar() {
        List<Proyecto> listaProyectos = new ArrayList<>();
        String consulta = "select id, nombre, fechaInicio, fechaFin, duracion, etapa, cedula_usuario from Proyecto";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp != null) {
            if (temp.moveToFirst()) {
                do {
                    Proyecto proyecto = new Proyecto(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3), Double.valueOf(temp.getInt(4)), temp.getInt(5), temp.getInt(6));
                    listaProyectos.add(proyecto);
                } while (temp.moveToNext());
            }
        }
        return listaProyectos;
    }
}
