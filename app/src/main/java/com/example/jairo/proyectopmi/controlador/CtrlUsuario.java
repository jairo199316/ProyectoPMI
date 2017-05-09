package com.example.jairo.proyectopmi.controlador;

import android.app.Activity;

import com.example.jairo.proyectopmi.dao.UsuarioDAO;
import com.example.jairo.proyectopmi.modelo.Usuario;

import java.util.Date;
import java.util.List;


/**
 * Created by Laura on 27/04/2017.
 */

public class CtrlUsuario {

    /**
     * dao de la clase usuario
     */
    private UsuarioDAO dao;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public CtrlUsuario(Activity activity) {
        dao = new UsuarioDAO(activity);
    }

    /**
     * registra un usuario
     *
     * @param tipoDocumento tipo de documento del usuario
     * @param documento     documento del usuario
     * @param nombre        nombre del usuario
     * @param apellido      apellido del usuario
     * @param password      contraseña del usuario
     * @param correo        correo del usuario
     * @param tipoUsuario   tipo de usuario del usuario
     * @return true si lo registra de lo contrario false
     */
    public boolean guardarUsuario(String tipoDocumento, int documento, String nombre, String apellido, String password, String correo, String tipoUsuario) {
        Usuario usuario = new Usuario(tipoDocumento, documento, nombre, apellido, password, correo, tipoUsuario);
        return dao.guardar(usuario);
    }

    /**
     * busca un usuario por numero de docuemto
     *
     * @param documento el documento del usuario
     * @return un susario si lo encuentra, de lo contrarionull
     */
    public Usuario buscarUsuario(int documento) {
        return dao.buscar(documento);
    }

    /**
     * valida el login de un usuario
     *
     * @param correo   el correo del usuario
     * @param password la contraseña de usuario
     * @return el usuario si lo valida, de lo contrario null
     */
    public Usuario validarLog(String correo, String password) {
        return dao.validadLogin(correo, password);
    }

    /**
     * elimina un usuario por numero de documento
     *
     * @param documento el numero de documento del usuario
     * @return true si lo elimina, de lo contrario false
     */
    public boolean eliminarUsuario(int documento) {
        Usuario usuario = new Usuario("", documento, "", "", "", "", "");
        return dao.eliminar(usuario);
    }

    /**
     * modifica un usuario por numero de documento
     *
     * @param tipoDocumento tipo de documento del usuario
     * @param documento     documento del usuario
     * @param nombre        nombre del usuario
     * @param apellido      apellido del usuario
     * @param password      contraseña del usuario
     * @param correo        correo del usuario
     * @param tipoUsuario   tipo de usuario del usuario
     * @return true si lo registra de lo contrario false
     */
    public boolean modificarUsuario(String tipoDocumento, int documento, String nombre, String apellido, String password, String correo, String tipoUsuario) {
        Usuario usuario = new Usuario(tipoDocumento, documento, nombre, apellido, password, correo, tipoUsuario);
        return dao.modificar(usuario);
    }

    /**
     * lista los usuario registrados
     *
     * @return una lista de usuarios
     */
    public List<Usuario> listarUsuario() {
        return dao.listar();
    }


}
