package com.example.jairo.proyectopmi.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.jairo.proyectopmi.controlador.CtrlProyecto;
import com.example.jairo.proyectopmi.infraestructura.Conexion;
import com.example.jairo.proyectopmi.modelo.Proyecto;
import com.example.jairo.proyectopmi.modelo.Recurso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAIRO on 13/05/2017.
 */
public class RecursoDAO {


    /**
     * clase conexion
     */
    private Conexion conex;

    private CtrlProyecto ctrlProyecto;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public RecursoDAO(Activity activity) {
        conex = new Conexion(activity);
        ctrlProyecto = new CtrlProyecto(activity);
    }

    /**
     * guarda un recurso en la bd
     *
     * @param recurso el recurso a guardar
     * @return true si lo guarda, de lo contrario false
     */
    public boolean guardar(Recurso recurso) {
        ContentValues registro = new ContentValues();
        registro.put("id", recurso.getId());
        registro.put("nombre", recurso.getNombre());
        registro.put("cantidad", recurso.getCantidad());
        registro.put("descripcion", recurso.getDescripcion());
        registro.put("ubicacion", recurso.getUbicacion());
        registro.put("proyecto_id", recurso.getProyecto().getId());
        return conex.ejecutarInsert("Recurso", registro);
    }

    /**
     * busc un recurso por nombre y proyecto
     *
     * @param nombre   el nombre del proyecto a buscar
     * @param proyecto el proyecto del recurso
     * @return el recurso si lo encuentra, de lo contrario null
     */
    public Recurso buscar(String nombre, Proyecto proyecto) {
        Recurso recurso = null;
        String consulta = "select id, nombre, cantidad, descripcion, ubicacion, proyecto_id from Recurso where "
                + " nombre = '" + nombre + "' and proyecto_id = " + proyecto.getId();
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            recurso = new Recurso(temp.getInt(0), temp.getString(1), temp.getInt(2), temp.getString(3), temp.getString(4), proyecto);
        }
        conex.cerrarConexion();
        return recurso;
    }

    /**
     * elimina un recurso
     *
     * @param recurso el recurso a eliminar
     * @return true si lo elimina de lo contrario false
     */
    public boolean eliminar(Recurso recurso) {
        String tabla = "Recurso";
        String condicion = "id=" + recurso.getId() + "";
        return conex.ejecutarDelete(tabla, condicion);
    }

    /**
     * modifica un recurso
     *
     * @param recurso el recurso a modificar
     * @return true si lo modifica, de lo contrario false
     */
    public boolean modificar(Recurso recurso) {
        String tabla = "Recurso";
        String condicion = "id = " + recurso.getId();
        ContentValues registro = new ContentValues();
        registro.put("nombre", recurso.getNombre());
        registro.put("cantidad", recurso.getCantidad());
        registro.put("descripcion", recurso.getDescripcion());
        registro.put("ubicacion", recurso.getUbicacion());
        registro.put("proyecto_id", recurso.getProyecto().getId());
        return conex.ejecutarUpdate(tabla, condicion, registro);
    }

    /**
     * lista los recursos registrados
     *
     * @return una lista con recursos registrados
     */
    public List<Recurso> listar() {
        List<Recurso> listaRecursos = new ArrayList<>();
        String consulta = "select id, nombre, cantidad, descripcion, ubicacion, proyecto_id from Recurso";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.moveToFirst()) {
            do {
                Proyecto proyecto = ctrlProyecto.buscarProyecto(temp.getInt(5));
                Recurso recurso = new Recurso(temp.getInt(0), temp.getString(1), temp.getInt(2), temp.getString(3), temp.getString(4), proyecto);
                listaRecursos.add(recurso);
            } while (temp.moveToNext());
        }
        return listaRecursos;
    }

}
