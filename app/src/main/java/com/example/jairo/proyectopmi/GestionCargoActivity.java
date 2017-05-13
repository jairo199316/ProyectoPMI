package com.example.jairo.proyectopmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jairo.proyectopmi.controlador.CtrlCargo;
import com.example.jairo.proyectopmi.modelo.Cargo;
import com.example.jairo.proyectopmi.util.Util;

import java.util.List;

public class GestionCargoActivity extends AppCompatActivity {

    private EditText nombreProyecto, nombreCargo, descripcionCargo, salarioCargo;
    private Spinner horarioCargo;
    private CtrlCargo controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cargo);
        nombreProyecto = (EditText) findViewById(R.id.nombreProyecto);
        nombreCargo = (EditText) findViewById(R.id.nombreCargo);
        descripcionCargo = (EditText) findViewById(R.id.descripcion);
        salarioCargo = (EditText) findViewById(R.id.salarioCargo);
        horarioCargo = (Spinner) findViewById(R.id.horarioCargo);
        cargarSpinnereHorarios();
        controlador = new CtrlCargo(this);
        if (Util.getProyecto() != null) {
            nombreProyecto.setHint(Util.getProyecto().getNombre());
        } else {
            nombreProyecto.setHint("Sin proyectos registrados");
        }
    }

    /**
     * carga el spinner con horarios
     */
    public void cargarSpinnereHorarios() {
        String[] opciones = new String[]{"8am - 10am y 2pm - 4pm", "10am - 12pm y 4pm - 6pm"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        horarioCargo.setAdapter(adapter);
    }

    /**
     * registra un cargo en la bd
     *
     * @param view
     */
    public void registrarCargo(View view) {
        if (Util.proyecto == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreCargo.getText().toString().equals("") ||
                    descripcionCargo.getText().toString().equals("") || salarioCargo.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                String des = descripcionCargo.getText().toString();
                String nombre = nombreCargo.getText().toString();
                double salario = Double.parseDouble(salarioCargo.getText().toString());
                String horario = horarioCargo.getSelectedItem().toString();
                List<Cargo> cargos = controlador.listarCargos();
                int idCargo = 0;
                if (!cargos.isEmpty()) {
                    idCargo = cargos.get(cargos.size() - 1).getId() + 1;
                }
                boolean validarNombre = true;
                for (int i = 0; i < cargos.size(); i++) {
                    if (cargos.get(i).getNombre().equals(nombre) &&
                            cargos.get(i).getProyecto().getNombre().equals(Util.getProyecto().getNombre())) {
                        validarNombre = false;
                    }
                }
                if (validarNombre) {
                    if (controlador.guardarCargo(idCargo, nombre, des, salario, horario, Util.getProyecto())) {
                        Toast.makeText(getApplicationContext(), "El cargo ha sido registrado", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible registrar el cargo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ya se encuentra un cargo con este nombre", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * busca un cargo
     *
     * @param view
     */
    public void buscarCargo(View view) {
        if (Util.proyecto == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreCargo.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            } else {
                String nombreC = nombreCargo.getText().toString();
                Cargo cargo = controlador.buscarCargo(nombreC, Util.getProyecto());
                if (cargo != null) {
                    salarioCargo.setText(String.valueOf(cargo.getSalario()));
                    descripcionCargo.setText(cargo.getDescripcion());
                    for (int i = 0; i < horarioCargo.getCount(); i++) {
                        if (horarioCargo.getItemAtPosition(i).toString().equals(cargo.getHorario())) {
                            horarioCargo.setSelection(i);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha encontrado un cargo con el nombre ingresado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * edita un cargo
     *
     * @param view
     */
    public void editarCargo(View view) {
        if (Util.proyecto == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreCargo.getText().toString().equals("") || descripcionCargo.getText().toString().equals("") ||
                    salarioCargo.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                String des = descripcionCargo.getText().toString();
                String nombre = nombreCargo.getText().toString();
                double salario = Double.parseDouble(salarioCargo.getText().toString());
                String horario = horarioCargo.getSelectedItem().toString();
                Cargo car = controlador.buscarCargo(nombre, Util.getProyecto());
                if (car != null) {
                    if (controlador.modificarCargo(car.getId(), nombre, des, salario, horario, car.getProyecto())) {
                        Toast.makeText(getApplicationContext(), "El cargo ha sido modificado", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    } else {
                        Toast.makeText(getApplicationContext(), "No se ha encontrado un cargo con el nombre ingresado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "El cargo a modificar no existe", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * elimina un cargo
     *
     * @param view
     */
    public void eliminarCargo(View view) {
        if (Util.proyecto == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreCargo.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            } else {
                String nombreC = nombreCargo.getText().toString();
                Cargo car = controlador.buscarCargo(nombreC, Util.getProyecto());
                if (car != null) {
                    if (controlador.eliminarCargo(car)) {
                        limpiarCampos();
                        Toast.makeText(getApplicationContext(), "El cargo ha sido eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible eliminar el cargo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "El cargo a eliminar no existe", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * limpia los campos de la ventana
     */
    public void limpiarCampos() {
        nombreCargo.setText("");
        descripcionCargo.setText("");
        salarioCargo.setText("");
        horarioCargo.setSelection(0);
    }

}

