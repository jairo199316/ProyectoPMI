package com.example.jairo.proyectopmi.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.jairo.proyectopmi.infraestructura.Conexion;
import com.example.jairo.proyectopmi.modelo.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Laura on 27/04/2017.
 */

public class UsuarioDAO {

    /**
     * clase conexion
     */
    private Conexion conex;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public UsuarioDAO(Activity activity) {
        conex = new Conexion(activity);
    }

    /**
     * guarda un usuario en la bd
     *
     * @param usuario el usuario a guardar
     * @return true si lo guarda, de lo contrario false
     */
    public boolean guardar(Usuario usuario) {
        ContentValues registro = new ContentValues();
        registro.put("documento", usuario.getDocumento());
        registro.put("tipoDocumento", usuario.getTipoDocumento());
        registro.put("tipoUsuario", usuario.getTipoUsuario());
        registro.put("nombre", usuario.getNombre());
        registro.put("apellido", usuario.getApellido());
        registro.put("correo", usuario.getCorreo());
        registro.put("password", usuario.getPassword());
        return conex.ejecutarInsert("Usuario", registro);
    }

    /**
     * busca un usario por numero de documento
     *
     * @param documento el numero de documento del usuario a buscar
     * @return el usuario si lo encuentra, de lo contrario null
     */
    public Usuario buscar(int documento) {
        Usuario usuario = null;
        String consulta = "select tipoDocumento, documento, nombre, apellido, password, correo, tipoUsuario from Usuario where "
                + " documento = " + documento + "";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            usuario = new Usuario(temp.getString(0), Integer.parseInt(temp.getString(1)), temp.getString(2), temp.getString(3), temp.getString(4), temp.getString(5), temp.getString(6));
        }
        conex.cerrarConexion();
        return usuario;
    }

    /**
     * valida el login de un usuario
     *
     * @param correo el correo a validar
     * @param pass   la contraseña a validar
     * @return el usuario si lo valida, de lo contrario false
     */
    public Usuario validadLogin(String correo, String pass) {
        Usuario usuario = null;
        String consulta = "select tipoDocumento, documento, nombre, apellido, password, correo, tipoUsuario from Usuario where"
                + " correo= '" + correo + "' and password= '" + pass + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            usuario = new Usuario(temp.getString(0), temp.getInt(1), temp.getString(2), temp.getString(3), temp.getString(4), temp.getString(5), temp.getString(6));
        }
        conex.cerrarConexion();
        return usuario;
    }

    /**
     * elimina un usuario
     *
     * @param usuario el usuario a eliminar
     * @return true si lo elimina de lo contrario false
     */
    public boolean eliminar(Usuario usuario) {
        String tabla = "Usuario";
        String condicion = "documento=" + usuario.getDocumento() + "";
        return conex.ejecutarDelete(tabla, condicion);
    }

    /**
     * modifica un usuario
     *
     * @param usuario el usuario a modificar
     * @return true si lo modifica, de lo contrario false
     */
    public boolean modificar(Usuario usuario) {
        String tabla = "Usuario";
        String condicion = "documento = " + usuario.getDocumento();
        ContentValues registro = new ContentValues();
        registro.put("documento", usuario.getDocumento());
        registro.put("tipoDocumento", usuario.getTipoDocumento());
        registro.put("tipoUsuario", usuario.getTipoUsuario());
        registro.put("nombre", usuario.getNombre());
        registro.put("apellido", usuario.getApellido());
        registro.put("correo", usuario.getCorreo());
        registro.put("password", usuario.getPassword());
        return conex.ejecutarUpdate(tabla, condicion, registro);
    }

    /**
     * lista los usuarios registrados
     *
     * @return una lista coon usuarios registrados
     */
    public List<Usuario> listar() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String consulta = "select tipoDocumento, documento, nombre, apellido, password, correo, tipoUsuario from Usuario";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.moveToFirst()) {
            do {
                Usuario usuario = new Usuario(temp.getString(0), Integer.parseInt(temp.getString(1)), temp.getString(2), temp.getString(3), temp.getString(4), temp.getString(5), temp.getString(6));
                listaUsuarios.add(usuario);
            } while (temp.moveToNext());
        }
        return listaUsuarios;
    }


}
