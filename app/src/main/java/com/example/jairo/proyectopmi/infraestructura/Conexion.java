package com.example.jairo.proyectopmi.infraestructura;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Laura on 27/04/2017.
 */

public class Conexion extends SQLiteOpenHelper {

    private static final String database = "ProyectoPMI.db";
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int version = 6;
    SQLiteDatabase bd;
    private String sql;


    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Conexion(Context context) {
        super(context, database, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
            //db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Usuario ( " +
                "documento NUMERIC NOT NULL, " +
                "tipoUsuario TEXT NOT NULL,  " +
                "tipoDocumento TEXT NOT NULL,  " +
                "nombre TEXT NOT NULL,  " +
                "apellido TEXT NOT NULL, " +
                "correo TEXT NOT NULL,  " +
                "password TEXT NOT NULL, " +
                "PRIMARY KEY(documento)  " +
                ")");

        db.execSQL("CREATE TABLE Ubicacion ( " +
                "id NUMERIC NOT NULL PRIMARY KEY, " +
                "latitud NUMERIC NOT NULL,  " +
                "longitud NUMERIC NOT NULL  " +
                ")");

        db.execSQL("CREATE TABLE Reunion ( " +
                "id NUMERIC NOT NULL, " +
                "ubicacion_id NUMERIC NOT NULL, " +
                "tematica TEXT NOT NULL,  " +
                "PRIMARY KEY(id),  " +
                "FOREIGN KEY(ubicacion_id) REFERENCES Ubicacion(id) " +
                ")");

        db.execSQL("CREATE TABLE Recurso ( " +
                "id NUMERIC NOT NULL,  " +
                "nombre TEXT NOT NULL, " +
                "cantidad NUMERIC NOT NULL,  " +
                "descripcion TEXT NOT NULL,  " +
                "ubicacion TEXT NOT NULL,  " +
                "proyecto_id NUMERIC NOT NULL,  " +
                "PRIMARY KEY(id), " +
                "FOREIGN KEY(proyecto_id) REFERENCES Proyecto(id) " +
                ")");

        db.execSQL("CREATE TABLE Proyecto ( " +
                "id NUMERIC NOT NULL,  " +
                "nombre TEXT NOT NULL, " +
                "fechaInicio TEXT NOT NULL,  " +
                "fechaFin TEXT NOT NULL,  " +
                "duracion NUMERIC NOT NULL,  " +
                "etapa NUMERIC NOT NULL,  " +
                "cedula_usuario NUMERIC NOT NULL, " +
                "PRIMARY KEY(id), " +
                "FOREIGN KEY(cedula_usuario) REFERENCES Usuario(documento) " +
                ")");

        db.execSQL("CREATE TABLE Cargo ( " +
                "id NUMERIC NOT NULL,  " +
                "nombre TEXT NOT NULL,  " +
                "descripcion TEXT NOT NULL,  " +
                "salario NUMERIC NOT NULL,  " +
                "horario TEXT NOT NULL,  " +
                "proyecto_id NUMERIC NOT NULL,  " +
                "PRIMARY KEY(id),  " +
                "FOREIGN KEY(proyecto_id) REFERENCES Proyecto(id) " +
                ")");

        db.execSQL("CREATE TABLE Integrante ( " +
                "usuario_id NUMERIC NOT NULL,  " +
                "cargo_id NUMERIC NOT NULL,  " +
                "proyecto_id NUMERIC NOT NULL,  " +
                "FOREIGN KEY(cargo_id) REFERENCES Cargo(id), " +
                "FOREIGN KEY(usuario_id) REFERENCES Usuario(documento), " +
                "FOREIGN KEY(proyecto_id) REFERENCES Proyecto(id), " +
                "PRIMARY KEY(usuario_id) " +
                ")");

        db.execSQL("CREATE TABLE EstadoProyecto ( " +
                "proyecto_id NUMERIC NOT NULL,  " +
                "porcentaje NUMERIC NOT NULL,  " +
                "descripcion_avance TEXT NOT NULL,  " +
                "FOREIGN KEY(proyecto_id) REFERENCES Proyecto(id),  " +
                "PRIMARY KEY(proyecto_id) " +
                ")");

        db.execSQL("CREATE TABLE Actividad ( " +
                "id NUMERIC NOT NULL,  " +
                "nombre TEXT NOT NULL, " +
                "responsable_id NUMERIC NOT NULL,  " +
                "proyecto_id NUMERIC NOT NULL,  " +
                "fechaInicio TEXT NOT NULL,  " +
                "fechaFin TEXT NOT NULL,  " +
                "descripcion TEXT NOT NULL,  " +
                "PRIMARY KEY(id),  " +
                "FOREIGN KEY(responsable_id) REFERENCES Integrante(usuario_id), " +
                "FOREIGN KEY(proyecto_id) REFERENCES Proyecto(id) " +
                ")");

        db.execSQL("CREATE TABLE Tarea ( " +
                "id NUMERIC NOT NULL,  " +
                "nombre TEXT NOT NULL, " +
                "porcentaje  NUMERIC NOT NULL, " +
                "fechaInicio TEXT NOT NULL, " +
                "fechaFin TEXT NOT NULL, " +
                "estado TEXT NOT NULL, " +
                "actividad_id NUMERIC NOT NULL, " +
                "FOREIGN KEY(actividad_id) REFERENCES Actividad(id), " +
                "PRIMARY KEY(id) " +
                ")");

        db.execSQL("CREATE TABLE TareaRecurso ( " +
                "id NUMERIC NOT NULL,  " +
                "recurso_id NUMERIC NOT NULL,  " +
                "tarea_id NUMERIC NOT NULL,  " +
                "PRIMARY KEY(id), " +
                "FOREIGN KEY(recurso_id) REFERENCES Recurso(id), " +
                "FOREIGN KEY(tarea_id) REFERENCES Tarea(id) " +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Usuario");
        db.execSQL("drop table if exists Ubicacion");
        db.execSQL("drop table if exists TareaRecurso");
        db.execSQL("drop table if exists Tarea");
        db.execSQL("drop table if exists Reunion");
        db.execSQL("drop table if exists Recurso");
        db.execSQL("drop table if exists Proyecto");
        db.execSQL("drop table if exists Integrante");
        db.execSQL("drop table if exists EstadoProyecto");
        db.execSQL("drop table if exists Cargo");
        db.execSQL("drop table if exists Actividad");
        onCreate(db);
    }

    public void cerrarConexion() {
        bd.close();
    }

    /**
     * ejecuta un insert
     *
     * @param tabla    la tabla en la que se va a insertar
     * @param registro los datos a insertar
     * @return true si inserta, de lo contrario false
     */
    public boolean ejecutarInsert(String tabla, ContentValues registro) {
        try {
            bd = this.getWritableDatabase();
            int res = (int) bd.insert(tabla, null, registro);
            if (res != -1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ejecuta el modificar
     *
     * @param tabla     la taba a modificar
     * @param condicion la condicion en la que se va a modificar
     * @param registro  los datos a modificar
     * @return true si modifica, de lo contrario false
     */
    public boolean ejecutarUpdate(String tabla, String condicion, ContentValues registro) {
        try {
            bd = this.getWritableDatabase();
            int cant = bd.update(tabla, registro, condicion, null);
            cerrarConexion();
            if (cant == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ejecuta las busquedas
     *
     * @param consulta el dato a buscar
     * @return el objeto si lo encuentra, de lo contrario false
     */
    public Cursor ejecutarSearch(String consulta) {
        try {
            bd = this.getWritableDatabase();
            Cursor fila = bd.rawQuery(consulta, null);
            return fila;
        } catch (Exception e) {
            cerrarConexion();
            return null;
        }
    }

    /**
     * elimina en la bd
     *
     * @param tabla     la tabla en la que se eliminara
     * @param condicion el dato a eliminar
     * @return true si lo elimina, de lo contrario false
     */
    public boolean ejecutarDelete(String tabla, String condicion) {
        try {
            bd = this.getWritableDatabase();
            int cant = bd.delete(tabla, condicion, null);
            if (cant == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
