package com.example.jairo.proyectopmi.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.jairo.proyectopmi.controlador.CtrlCargo;
import com.example.jairo.proyectopmi.controlador.CtrlProyecto;
import com.example.jairo.proyectopmi.controlador.CtrlUsuario;
import com.example.jairo.proyectopmi.infraestructura.Conexion;
import com.example.jairo.proyectopmi.modelo.Cargo;
import com.example.jairo.proyectopmi.modelo.Integrante;
import com.example.jairo.proyectopmi.modelo.Proyecto;
import com.example.jairo.proyectopmi.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAIRO on 9/05/2017.
 */
public class IntegranteDAO {

    /**
     * clase conexion
     */
    private Conexion conex;

    private CtrlUsuario ctrlUsuario;

    private CtrlProyecto ctrlProyecto;

    private CtrlCargo ctrlCargo;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public IntegranteDAO(Activity activity) {
        conex = new Conexion(activity);
        ctrlUsuario = new CtrlUsuario(activity);
        ctrlProyecto = new CtrlProyecto(activity);
        ctrlCargo = new CtrlCargo(activity);
    }

    /**
     * guarda un integrante en la bd
     *
     * @param integrante el integrante a guardar
     * @return true si lo guarda, de lo contrario false
     */
    public boolean guardar(Integrante integrante) {
        ContentValues registro = new ContentValues();
        registro.put("usuario_id", integrante.getDocumento());
        registro.put("cargo_id", integrante.getCargo().getId());
        registro.put("proyecto_id", integrante.getProyecto().getId());
        return conex.ejecutarInsert("Integrante", registro);
    }

    /**
     * busca un usario por numero de documento
     *
     * @param documento el numero de documento del integrante a buscar
     * @return el integrante si lo encuentra, de lo contrario null
     */
    public Integrante buscar(int documento, int proyectoCod) {
        Integrante integrante = null;
        String consulta = "select usuario_id, cargo_id, proyecto_id from Integrante where "
                + " usuario_id = " + documento + " and proyecto_id = " + proyectoCod;
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            Usuario usuario = ctrlUsuario.buscarUsuario(temp.getInt(0));
            Cargo cargo = ctrlCargo.buscarCargo(temp.getInt(1));
            Proyecto proyecto = ctrlProyecto.buscarProyecto(temp.getInt(2));
            integrante = new Integrante(usuario.getTipoDocumento(), usuario.getDocumento(), usuario.getNombre(), usuario.getApellido()
                    , usuario.getPassword(), usuario.getCorreo(), usuario.getTipoUsuario(), cargo, proyecto);
        }
        conex.cerrarConexion();
        return integrante;
    }

    /**
     * busca un usario por numero de documento
     *
     * @param documento el numero de documento del integrante a buscar
     * @return el integrante si lo encuentra, de lo contrario null
     */
    public Integrante buscar(int documento) {
        Integrante integrante = null;
        String consulta = "select usuario_id, cargo_id, proyecto_id from Integrante where "
                + " usuario_id = " + documento + "";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            Usuario usuario = ctrlUsuario.buscarUsuario(temp.getInt(0));
            Cargo cargo = ctrlCargo.buscarCargo(temp.getInt(1));
            Proyecto proyecto = ctrlProyecto.buscarProyecto(temp.getInt(2));
            integrante = new Integrante(usuario.getTipoDocumento(), usuario.getDocumento(), usuario.getNombre(), usuario.getApellido()
                    , usuario.getPassword(), usuario.getCorreo(), usuario.getTipoUsuario(), cargo, proyecto);
        }
        conex.cerrarConexion();
        return integrante;
    }

    /**
     * elimina un integrante
     *
     * @param integrante el integrante a eliminar
     * @return true si lo elimina de lo contrario false
     */
    public boolean eliminar(Integrante integrante) {
        String tabla = "Integrante";
        String condicion = "usuario_id=" + integrante.getDocumento() + " and proyecto_id=" + integrante.getProyecto().getId();
        return conex.ejecutarDelete(tabla, condicion);
    }

    /**
     * lista los integrantes de un proyecto
     *
     * @param proyecto el proyecto de los integrantes
     * @return una lista de integrantes de un proyecto
     */
    public List<Integrante> listarIntegrantes(Proyecto proyecto) {
        List<Integrante> listaIntegrantes = new ArrayList<>();
        String consulta = "select usuario_id, cargo_id, proyecto_id from Integrante where "
                + " proyecto_id = " + proyecto.getId() + "";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp != null) {
            if (temp.moveToFirst()) {
                do {
                    Usuario usuario = ctrlUsuario.buscarUsuario(temp.getInt(0));
                    Cargo cargo = ctrlCargo.buscarCargo(temp.getInt(1));
                    Integrante integrante = new Integrante(usuario.getTipoDocumento(), usuario.getDocumento(), usuario.getNombre(), usuario.getApellido()
                            , usuario.getPassword(), usuario.getCorreo(), usuario.getTipoUsuario(), cargo, proyecto);
                    listaIntegrantes.add(integrante);
                } while (temp.moveToNext());
            }
        }
        return listaIntegrantes;
    }
}
