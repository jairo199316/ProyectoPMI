package com.example.jairo.proyectopmi.controlador;

import android.app.Activity;

import com.example.jairo.proyectopmi.dao.IntegranteDAO;
import com.example.jairo.proyectopmi.modelo.Integrante;
import com.example.jairo.proyectopmi.modelo.Proyecto;

import java.util.List;

/**
 * Created by JAIRO on 9/05/2017.
 */
public class CtrlIntegrante {


    /**
     * dao de la clase integrante
     */
    private IntegranteDAO integranteDAO;

    /**
     * controlador de la clase
     *
     * @param activity
     */
    public CtrlIntegrante(Activity activity) {
        integranteDAO = new IntegranteDAO(activity);
    }

    /**
     * guarda un integrante en la bd
     *
     * @param integrante el integrante a guardar
     * @return true si lo guarda, de lo contrario false
     */
    public boolean guardaIntegrante(Integrante integrante) {
        return integranteDAO.guardar(integrante);
    }

    /**
     * @param documento
     * @param proyectoCod
     * @return
     */
    public Integrante buscarIntegrante(int documento, int proyectoCod) {
        return integranteDAO.buscar(documento, proyectoCod);
    }

    /**
     * @param documento
     * @return
     */
    public Integrante buscarIntegrante(int documento) {
        return integranteDAO.buscar(documento);
    }




    /**
     * elimina un integrante
     *
     * @param integrante el integrante a eliminar
     * @return true si lo elimina, de lo contrrio false
     */
    public boolean eliminarIntegrante(Integrante integrante) {
        return integranteDAO.eliminar(integrante);
    }

    public List<Integrante>listarIntegrantes(Proyecto proyecto){
        return integranteDAO.listarIntegrantes(proyecto);
    }

}
