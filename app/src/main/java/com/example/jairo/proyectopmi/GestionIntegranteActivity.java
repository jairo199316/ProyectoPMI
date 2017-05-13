package com.example.jairo.proyectopmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jairo.proyectopmi.controlador.CtrlCargo;
import com.example.jairo.proyectopmi.controlador.CtrlIntegrante;
import com.example.jairo.proyectopmi.controlador.CtrlUsuario;
import com.example.jairo.proyectopmi.modelo.Cargo;
import com.example.jairo.proyectopmi.modelo.Integrante;
import com.example.jairo.proyectopmi.modelo.Usuario;
import com.example.jairo.proyectopmi.util.Util;

import java.util.ArrayList;
import java.util.List;

public class GestionIntegranteActivity extends AppCompatActivity {

    private EditText nombreProyecto, cedulaInt, nombreInt, apellidoInt, correoInt;
    private Spinner cargos;
    private CtrlCargo ctrlCargo;
    private CtrlUsuario ctrlUsuario;
    private CtrlIntegrante ctrlIntegrante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_integrante);
        ctrlCargo = new CtrlCargo(this);
        ctrlUsuario = new CtrlUsuario(this);
        ctrlIntegrante = new CtrlIntegrante(this);
        nombreProyecto = (EditText) findViewById(R.id.nombreProyectoGesInt);
        cedulaInt = (EditText) findViewById(R.id.cedulaGesInt);
        nombreInt = (EditText) findViewById(R.id.nombreGesInt);
        apellidoInt = (EditText) findViewById(R.id.apellidoGesInt);
        correoInt = (EditText) findViewById(R.id.correoGesInt);
        cargos = (Spinner) findViewById(R.id.cargosGesInt);
        if (Util.getProyecto() != null) {
            nombreProyecto.setHint(Util.getProyecto().getNombre());
        } else {
            nombreProyecto.setHint("Sin proyectos registrados");
        }
        cargarCargos();
    }

    /**
     * carga los cargos de un proyecto en el spinner
     */
    public void cargarCargos() {
        List<Cargo> listaCargos = new ArrayList<>();
        if (Util.getProyecto() != null) {
            listaCargos = ctrlCargo.listarCargos(Util.getProyecto().getId());
        }
        String[] nombreCargos = new String[listaCargos.size() + 1];
        nombreCargos[0] = "Seleccione una opcion";
        for (int i = 0; i < listaCargos.size(); i++) {
            nombreCargos[i + 1] = listaCargos.get(i).getNombre();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombreCargos);
        cargos.setAdapter(adapter);
    }


    /**
     * busca un integrante
     *
     * @param view
     */
    public void buscarIntegrante(View view) {
        if (Util.proyecto == null) {
            Toast.makeText(getApplicationContext(), "Debe registrar un proyecto", Toast.LENGTH_SHORT).show();
        } else {
            if (cedulaInt.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar una cedula", Toast.LENGTH_SHORT).show();
            } else {
                int cedulaInte = Integer.parseInt(cedulaInt.getText().toString());
                Integrante integrante = ctrlIntegrante.buscarIntegrante(cedulaInte, Util.getProyecto().getId());
                Integrante integranteDisponible = ctrlIntegrante.buscarIntegrante(cedulaInte);
                if (integrante != null) {
                    nombreInt.setText(integrante.getNombre());
                    apellidoInt.setText(integrante.getApellido());
                    correoInt.setText(integrante.getCorreo());
                    for (int i = 0; i < cargos.getCount(); i++) {
                        if(cargos.getItemAtPosition(i).equals(integrante.getCargo().getNombre())){
                            cargos.setSelection(i);
                        }
                    }
                } else if (integranteDisponible == null) {
                    Usuario usuario = ctrlUsuario.buscarUsuario(cedulaInte);
                    if (usuario != null && usuario.getTipoUsuario().equals("Integrante")) {
                        nombreInt.setText(usuario.getNombre());
                        apellidoInt.setText(usuario.getApellido());
                        correoInt.setText(usuario.getCorreo());
                    } else {
                        Toast.makeText(getApplicationContext(), "No se encontro un integrante con la cedula inrgesada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se encontro un integrante disponible con los datos ingresados", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /**
     * Elimina un integrante
     *
     * @param view
     */
    public void eliminarIntegrante(View view) {
        if (Util.proyecto == null) {
            Toast.makeText(getApplicationContext(), "Debe registrar un proyecto", Toast.LENGTH_SHORT).show();
        } else {
            if (cedulaInt.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar una cedula", Toast.LENGTH_SHORT).show();
            } else {
                int cedulaInte = Integer.parseInt(cedulaInt.getText().toString());
                Integrante integrante = ctrlIntegrante.buscarIntegrante(cedulaInte, Util.getProyecto().getId());
                if (integrante != null) {
                    if (ctrlIntegrante.eliminarIntegrante(integrante)) {
                        limpiarCampos();
                        Toast.makeText(getApplicationContext(), "El integrante ha sido eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible eliminar el integrate", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha encontrado el integrante", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * agrega un integrante
     *
     * @param view
     */
    public void agregarIntegrante(View view) {
        if (Util.proyecto == null) {
            Toast.makeText(getApplicationContext(), "Debe registrar un proyecto", Toast.LENGTH_SHORT).show();
        } else {
            if (cedulaInt.getText().toString().equals("") || cargos.getSelectedItemPosition() == 0) {
                Toast.makeText(getApplicationContext(), "Debe ingresar una cedula y seleccionar un cargo", Toast.LENGTH_SHORT).show();
            } else {
                int cedulaInte = Integer.parseInt(cedulaInt.getText().toString());
                Usuario usuario = ctrlUsuario.buscarUsuario(cedulaInte);
                if (usuario != null && usuario.getTipoUsuario().equals("Integrante")) {
                    Integrante integrante = ctrlIntegrante.buscarIntegrante(usuario.getDocumento(), Util.getProyecto().getId());
                    if (integrante == null) {
                        Integrante integranteDisponible = ctrlIntegrante.buscarIntegrante(cedulaInte);
                        if (integranteDisponible == null) {
                            String cargo = cargos.getSelectedItem().toString();
                            Cargo cargoIntg = ctrlCargo.buscarCargo(cargo, Util.getProyecto());
                            integrante = new Integrante(usuario.getTipoDocumento(), usuario.getDocumento(), usuario.getNombre(), usuario.getApellido()
                                    , usuario.getPassword(), usuario.getCorreo(), usuario.getTipoUsuario(), cargoIntg, Util.getProyecto());
                            if (ctrlIntegrante.guardaIntegrante(integrante)) {
                                limpiarCampos();
                                Toast.makeText(getApplicationContext(), "El integrante ha sido registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No ha sido posible registrar el integrante", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "El integrante ya se encuentra registrado en otro proyecto", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "El integrante ya se encuentra registrado en este proyecto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * limpia los campos de la ventana
     */
    public void limpiarCampos() {
        nombreProyecto.setText("");
        cedulaInt.setText("");
        nombreInt.setText("");
        apellidoInt.setText("");
        correoInt.setText("");
        cargos.setSelection(0);
    }
}
