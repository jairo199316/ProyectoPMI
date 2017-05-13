package com.example.jairo.proyectopmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jairo.proyectopmi.controlador.CtrlRecurso;
import com.example.jairo.proyectopmi.modelo.Recurso;
import com.example.jairo.proyectopmi.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GestionRecursoActivity extends AppCompatActivity {

    private EditText nombreProyecto, nombreRecurso, cantidadRecurso, descripcionRecurso, ubicacionRecurso;
    private CtrlRecurso ctrlRecurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_recurso);
        nombreProyecto = (EditText) findViewById(R.id.nombreProyectoGestRec);
        nombreRecurso = (EditText) findViewById(R.id.nombreGestRec);
        cantidadRecurso = (EditText) findViewById(R.id.cantidadGestRec);
        descripcionRecurso = (EditText) findViewById(R.id.descripcionGestRec);
        ubicacionRecurso = (EditText) findViewById(R.id.ubicacionGestRec);
        ctrlRecurso = new CtrlRecurso(this);
        if (Util.getProyecto() != null) {
            nombreProyecto.setHint(Util.getProyecto().getNombre());
        } else {
            nombreProyecto.setHint("Sin proyectos registrados");
        }
    }

    /**
     * busca un recurso
     *
     * @param view
     */
    public void buscarRecurso(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreRecurso.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            } else {
                String nombre = nombreRecurso.getText().toString();
                Recurso recursoB = ctrlRecurso.buscarRecurso(nombre, Util.getProyecto());
                if (recursoB != null) {
                    descripcionRecurso.setText(recursoB.getDescripcion());
                    cantidadRecurso.setText(recursoB.getCantidad() + "");
                    ubicacionRecurso.setText(recursoB.getUbicacion());
                } else {
                    Toast.makeText(getApplicationContext(), "No existe un recurso registrado con este nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * agrega un recurso
     *
     * @param view
     */
    public void agregarRecurso(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreRecurso.getText().toString().equals("") || cantidadRecurso.getText().toString().equals("") ||
                    descripcionRecurso.getText().toString().equals("") || ubicacionRecurso.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                String nombre = nombreRecurso.getText().toString();
                Recurso recursoB = ctrlRecurso.buscarRecurso(nombre, Util.getProyecto());
                if (recursoB == null) {
                    String descripcion = descripcionRecurso.getText().toString();
                    int cantidad = Integer.parseInt(cantidadRecurso.getText().toString());
                    List<Recurso> recursos = ctrlRecurso.listarRecurso();
                    int idRecurso = 0;
                    if (!recursos.isEmpty()) {
                        idRecurso = recursos.get(recursos.size() - 1).getId() + 1;
                    }
                    String ubicacion = ubicacionRecurso.getText().toString();
                    Recurso recurso = new Recurso(idRecurso, nombre, cantidad, descripcion, ubicacion, Util.getProyecto());
                    if (ctrlRecurso.guardarRecurso(recurso)) {
                        Toast.makeText(getApplicationContext(), "El recurso ha sido registrado", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible registrar el recurso", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ya existe un recurso registrado con este nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * edita un recurso
     *
     * @param view
     */
    public void editarRecurso(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreRecurso.getText().toString().equals("") || cantidadRecurso.getText().toString().equals("") ||
                    descripcionRecurso.getText().toString().equals("") || ubicacionRecurso.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                String nombre = nombreRecurso.getText().toString();
                Recurso recursoB = ctrlRecurso.buscarRecurso(nombre, Util.getProyecto());
                if (recursoB != null) {
                    String descripcion = descripcionRecurso.getText().toString();
                    int cantidad = Integer.parseInt(cantidadRecurso.getText().toString());
                    String ubicacion = ubicacionRecurso.getText().toString();
                    Recurso recurso = new Recurso(recursoB.getId(), nombre, cantidad, descripcion, ubicacion, Util.getProyecto());
                    if (ctrlRecurso.modificarRecurso(recurso)) {
                        Toast.makeText(getApplicationContext(), "El recurso ha sido modificado", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible modificado el recurso", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No existe un recurso registrado con este nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * elimina un recurso
     *
     * @param view
     */
    public void eliminarRecurso(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreRecurso.getText().toString().equals("") || cantidadRecurso.getText().toString().equals("") ||
                    descripcionRecurso.getText().toString().equals("") || ubicacionRecurso.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                String nombre = nombreRecurso.getText().toString();
                Recurso recurso = ctrlRecurso.buscarRecurso(nombre, Util.getProyecto());
                if (recurso != null) {
                    if (ctrlRecurso.eliminarRecurso(recurso)) {
                        Toast.makeText(getApplicationContext(), "El recurso ha sido eliminado", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible eliminar el recurso", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No existe un recurso registrado con este nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * limpia los campos de la ventana
     */
    public void limpiarCampos() {
        nombreProyecto.setText("");
        nombreRecurso.setText("");
        cantidadRecurso.setText("");
        descripcionRecurso.setText("");
        ubicacionRecurso.setText("");
    }
}
