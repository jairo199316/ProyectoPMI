package com.example.jairo.proyectopmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jairo.proyectopmi.controlador.CtrlProyecto;
import com.example.jairo.proyectopmi.controlador.CtrlUsuario;
import com.example.jairo.proyectopmi.modelo.Usuario;
import com.example.jairo.proyectopmi.util.Util;

public class MainActivity extends AppCompatActivity {

    private EditText correo, contrasenia;
    private CtrlUsuario ctrlUsuario;
    private CtrlProyecto ctrlProyecto;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correo = (EditText) findViewById(R.id.correoLog);
        contrasenia = (EditText) findViewById(R.id.passLog);
        ctrlUsuario = new CtrlUsuario(this);
        ctrlProyecto = new CtrlProyecto(this);
        util = new Util();
        for (int i = 0; i < ctrlProyecto.listarProyectos().size(); i++) {
            Log.i("usuario", ctrlProyecto.listarProyectos().get(i).getUsuario() + "");
        }
    }

    /**
     * abre la ventana de registro de usuario
     *
     * @param view
     */
    public void abrirRegistroUsuario(View view) {
        Intent intent = new Intent(this, RegistroUsuarioActivity.class);
        startActivity(intent);
    }

    /**
     * valida el login del usuario
     *
     * @param view
     */
    public void validarLogin(View view) {
        String correoUsu = correo.getText().toString();
        String pass = contrasenia.getText().toString();
        Usuario usuario = ctrlUsuario.validarLog(correoUsu, pass);
        if (usuario != null) {
            Util.setUsuario(usuario);
            Util.setProyecto(ctrlProyecto.buscarProyectoUsuario(usuario.getDocumento()));
            Intent intent = null;
            if (usuario.getTipoUsuario().equals("Director")) {
                intent = new Intent(this, MpplDirectorActivity.class);
            }
            limpiarCampos();
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * limpia los campos de la ventana
     */
    public void limpiarCampos() {
        correo.setText("");
        contrasenia.setText("");
    }


}
