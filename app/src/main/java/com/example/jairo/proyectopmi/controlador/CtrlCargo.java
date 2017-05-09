package com.example.jairo.proyectopmi.controlador;

import android.app.Activity;

import com.example.jairo.proyectopmi.dao.CargoDAO;
import com.example.jairo.proyectopmi.modelo.Cargo;
import com.example.jairo.proyectopmi.modelo.Proyecto;

import java.util.List;

/**
 * Created by vanegas on 08/05/2017.
 */

public class CtrlCargo {

    /**
     * dao de la clase cargo
     */
    private CargoDAO dao;

    /**
     * controlador de la clase
     *
     * @param activity
     */
    public CtrlCargo(Activity activity) {
        dao = new CargoDAO(activity);
    }

    /**
     * guarda un cargo
     *
     * @param id          id del cargo
     * @param nombre      nombre del cargo
     * @param descripcion descripcion del cargo
     * @param salario     salario del cargo
     * @param horario     horario del cargo
     * @param proyecto    proyecto al que pertenece el cargo
     * @return true si lo puede registrar, de lo contrario false
     */
    public boolean guardarCargo(int id, String nombre, String descripcion, double salario, String horario, Proyecto proyecto) {
        Cargo cargo = new Cargo(id, nombre, descripcion, salario, horario, proyecto);
        return dao.guardar(cargo);
    }

    /**
     * busca un cargo por nombre y proyecto
     *
     * @param nombre el nombre del cargo a buscar*
     * @param proyecto el proyecto al que pertenece el cargo a buscar
     * @return el cargo si lo encuentra, de lo contrario null
     */
    public Cargo buscarCargo(String nombre, Proyecto proyecto) {
        return dao.buscar(nombre, proyecto);
    }

    /**
     * elimina un cargo
     *
     * @param cargo el cargo a eliminar
     * @return true si lo elimina, de lo contrrio false
     */
    public boolean eliminarCargo(Cargo cargo) {
        return dao.eliminar(cargo);
    }

    /**
     * modifica un cargo
     *
     * @param id          id del cargo
     * @param nombre      nombre del cargo
     * @param descripcion descripcion del cargo
     * @param salario     salario del cargo
     * @param horario     horario del cargo
     * @param proyecto    proyecto al que pertenece el cargo
     * @return true si lo puede registrar, de lo contrario false
     */
    public boolean modificarCargo(int id, String nombre, String descripcion, double salario, String horario, Proyecto proyecto) {
        Cargo cargo = new Cargo(id, nombre, descripcion, salario, horario, proyecto);
        return dao.modificar(cargo);
    }

    /**
     * lista todos los cargos registrados
     *
     * @return una lista con todos los cargos
     */
    public List<Cargo> listarCargos() {
        return dao.listar();
    }

}
