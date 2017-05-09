package com.example.jairo.proyectopmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jairo.proyectopmi.controlador.CtrlUsuario;
import com.example.jairo.proyectopmi.modelo.Usuario;

import java.util.List;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private EditText numeroDoc, nombre, apellido, contrasenia, confContrasenia, correo;
    private Spinner tipoDoc, tipoUsu;
    private CtrlUsuario ctrlUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        numeroDoc = (EditText) findViewById(R.id.numeroDocReg);
        nombre = (EditText) findViewById(R.id.nombreReg);
        apellido = (EditText) findViewById(R.id.apellidoReg);
        contrasenia = (EditText) findViewById(R.id.passReg);
        confContrasenia = (EditText) findViewById(R.id.confPassReg);
        correo = (EditText) findViewById(R.id.correoReg);
        tipoDoc = (Spinner) findViewById(R.id.tipoDocumentoReg);
        tipoUsu = (Spinner) findViewById(R.id.tipoUsuReg);
        ctrlUsuario = new CtrlUsuario(this);
        cargarCombos();
    }

    /**
     * registra un usuario en la base de datos
     *
     * @param view
     */
    public void registrarUsuario(View view) {
        if (numeroDoc.getText().toString().equals("") || nombre.getText().toString().equals("") ||
                correo.getText().toString().equals("") || contrasenia.getText().toString().equals("") ||
                confContrasenia.getText().toString().equals("") || tipoDoc.getSelectedItemPosition() == 0 ||
                tipoUsu.getSelectedItemPosition() == 0 || apellido.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Todos los campos y selecciones son obligatorios", Toast.LENGTH_SHORT).show();
        } else {
            String contraseniaUsu = contrasenia.getText().toString();
            String confContraseniaUsu = confContrasenia.getText().toString();
            if (contraseniaUsu.equals(confContraseniaUsu)) {
                int numeroDocUsu = Integer.parseInt(numeroDoc.getText().toString());
                String nombreUsu = nombre.getText().toString();
                String apellidoUsu = apellido.getText().toString();
                String correoUsu = correo.getText().toString();
                String tipoDocUsu = tipoDoc.getSelectedItem().toString();
                String tipoUs = tipoUsu.getSelectedItem().toString();
                List<Usuario> usuarios = ctrlUsuario.listarUsuario();
                boolean verificarCorreo = true;
                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getCorreo().equals(correoUsu)) {
                        verificarCorreo = false;
                    }
                }
                if (verificarCorreo) {
                    if (ctrlUsuario.guardarUsuario(tipoDocUsu, numeroDocUsu, nombreUsu, apellidoUsu, contraseniaUsu, correoUsu, tipoUs)) {
                        Toast.makeText(getApplicationContext(), "El usuario ha sido registrado", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible crear el usuario", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ya se encuentra un usuario con este correo", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * limpia los campos de la ventana
     */
    public void limpiarCampos() {
        numeroDoc.setText("");
        nombre.setText("");
        apellido.setText("");
        contrasenia.setText("");
        confContrasenia.setText("");
        correo.setText("");
        tipoDoc.setSelection(0);
        tipoUsu.setSelection(0);
    }

    /**
     * carga los combos de tipo de documento y tipo de usuario
     */
    public void cargarCombos() {
        String[] tipoDocumento = new String[]{"Seleccione una opcion", "Cedula", "Cedula extrangera"};
        String[] tipoUsuario = new String[]{"Seleccione una opcion", "Director", "Integrante"};
        ArrayAdapter<String> opcionesDocumento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoDocumento);
        ArrayAdapter<String> opcionesTipoUsu = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoUsuario);
        tipoDoc.setAdapter(opcionesDocumento);
        tipoUsu.setAdapter(opcionesTipoUsu);
    }

}
