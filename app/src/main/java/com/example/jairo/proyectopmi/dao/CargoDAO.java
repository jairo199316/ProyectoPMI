package com.example.jairo.proyectopmi.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.jairo.proyectopmi.controlador.CtrlProyecto;
import com.example.jairo.proyectopmi.infraestructura.Conexion;
import com.example.jairo.proyectopmi.modelo.Cargo;
import com.example.jairo.proyectopmi.modelo.Proyecto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanegas on 08/05/2017.
 */

public class CargoDAO {

    /**
     * clase conexion
     */
    private Conexion conex;

    /**
     * controlador del proyecto
     */
    private CtrlProyecto proye;



    /**
     * constructor de la clase
     *
     * @param activity
     */
    public CargoDAO(Activity activity) {
        conex = new Conexion(activity);
        proye=new CtrlProyecto(activity);
    }

    /**
     * guarda un cargo en la bd
     *
     * @param cargo el cargo a guardar
     * @return true si lo puede guardar, de lo contrario false
     */
    public boolean guardar(Cargo cargo) {
        ContentValues registro = new ContentValues();
        registro.put("id", cargo.getId());
        registro.put("nombre", cargo.getNombre());
        registro.put("descripcion", cargo.getDescripcion());
        registro.put("salario", cargo.getSalario());
        registro.put("horario", cargo.getHorario());
        registro.put("proyecto_id", cargo.getProyecto().getId());
        return conex.ejecutarInsert("Cargo", registro);
    }


    /**
     * busca un cargo por nombre
     *
     * @param nombre el nombre del cargo a buscar
     * @return el cargo si lo encuentra de lo contrario null
     */
    public Cargo buscar(String nombre, Proyecto proyecto) {
        Cargo cargo = null;
        String consulta = "select id, nombre, descripcion, salario, horario, proyecto_id from Cargo where "
                + " nombre = '" + nombre + "' and proyecto_id = '"  + proyecto.getId() + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            cargo = new Cargo(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getDouble(3), temp.getString(4), proyecto);
        }
        conex.cerrarConexion();
        return cargo;
    }

    /**
     * elimina un cargo en la bd
     *
     * @param cargo el cargo a eliminar
     * @return true si lo elimina, de lo contrario false
     */
    public boolean eliminar(Cargo cargo) {
        String tabla = "Cargo";
        String condicion = "nombre='" + cargo.getNombre() + "' and proyecto_id='" + cargo.getProyecto().getId() + "'";
        return conex.ejecutarDelete(tabla, condicion);
    }

    /**
     * modifica un cargo
     *
     * @param cargo el cargo a modificar
     * @return true si lo modifica, de lo contrario false
     */
    public boolean modificar(Cargo cargo) {
        String tabla = "Cargo";
        String condicion = "nombre='" + cargo.getNombre() + "' and proyecto_id='" + cargo.getProyecto().getId() + "'";
        ContentValues registro = new ContentValues();
        registro.put("nombre", cargo.getNombre());
        registro.put("descripcion", cargo.getDescripcion());
        registro.put("salario", cargo.getSalario());
        registro.put("horario", cargo.getHorario());
        registro.put("proyecto_id", cargo.getProyecto().getId());
        return conex.ejecutarUpdate(tabla, condicion, registro);

    }

    /**
     * lista los cargos registrados
     *
     * @return una lista con los cargos registrados
     */
    public List<Cargo> listar() {
        List<Cargo> listaCargos = new ArrayList<>();
        String consulta = "select id, nombre, descripcion, salario, horario, proyecto_id from Cargo";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp != null) {
            if (temp.moveToFirst()) {
                do {
                    Proyecto proyecto=proye.buscarProyecto(temp.getInt(5));
                    Cargo cargo = new Cargo(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getDouble(3), temp.getString(4), proyecto);
                    listaCargos.add(cargo);
                } while (temp.moveToNext());
            }
        }
        return listaCargos;
    }
}
