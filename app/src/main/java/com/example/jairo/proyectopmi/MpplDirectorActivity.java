package com.example.jairo.proyectopmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jairo.proyectopmi.util.Util;

public class MpplDirectorActivity extends AppCompatActivity {

    private ListView opcionesMenu;
    private TextView msjUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mppl_director);
        opcionesMenu = (ListView) findViewById(R.id.menuDirector);
        msjUsuario = (TextView) findViewById(R.id.msjUsuario);
        msjUsuario.setText("Bienvenido: " + Util.getUsuario().getNombre() + " " + Util.getUsuario().getApellido());
        configurarLista();
    }

    /**
     * configura la lista de opciones en el menu del director
     */
    public void configurarLista() {
        String[] opciones = new String[]{"Gestion de proyectos", "Gestion de cargos", "Gestion de integrantes", "Gestion de actividades",
                "Gestion de tareas", "Gestion de recursos", "Gestion de reuniones"};
        ArrayAdapter<String> listaOpciones = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones);
        opcionesMenu.setAdapter(listaOpciones);
        opcionesMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abrirVentana(position);
            }
        });
    }

    /**
     * abre las ventanas dependiendo de las seleccion
     *
     * @param seleccion la seleccion de una opcion
     */
    public void abrirVentana(int seleccion) {
        Intent intent = null;
        if (seleccion == 0) {
            intent = new Intent(this, GestionProyectoActivity.class);
        } else if (seleccion == 1) {
            intent = new Intent(this, GestionCargoActivity.class);
        } else if (seleccion == 2) {
            intent = new Intent(this, GestionIntegranteActivity.class);
        } else if (seleccion == 3) {
            intent = new Intent(this, GestionActividadActivity.class);
        } else if (seleccion == 4) {
            intent = new Intent(this, GestionTareaActivity.class);
        } else if (seleccion == 5) {
            intent = new Intent(this, GestionRecursoActivity.class);
        }
        startActivity(intent);
    }
}
