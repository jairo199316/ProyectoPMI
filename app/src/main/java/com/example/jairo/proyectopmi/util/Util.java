package com.example.jairo.proyectopmi.util;

import com.example.jairo.proyectopmi.modelo.Proyecto;
import com.example.jairo.proyectopmi.modelo.Usuario;

/**
 * Created by JAIRO on 7/05/2017.
 */
public class Util {

    public static Usuario usuario;
    public static Proyecto proyecto;

    public Util() {
        this.usuario = null;
        this.proyecto = null;
    }

    public static Proyecto getProyecto() {
        return proyecto;
    }

    public static void setProyecto(Proyecto proyecto) {
        Util.proyecto = proyecto;
    }

    public static void setUsuario(Usuario usuario) {
        Util.usuario = usuario;
    }

    public static Usuario getUsuario() {
        return usuario;
    }
}
