package com.example.jairo.proyectopmi;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jairo.proyectopmi.controlador.CtrlActividad;
import com.example.jairo.proyectopmi.controlador.CtrlIntegrante;
import com.example.jairo.proyectopmi.modelo.Actividad;
import com.example.jairo.proyectopmi.modelo.Integrante;
import com.example.jairo.proyectopmi.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GestionActividadActivity extends AppCompatActivity {

    private EditText nombreProyecto, nombreActividad, descripcionActividad, fechaInicioActividad, fechaFinActividad;
    private Spinner responsableActividad;
    private CtrlIntegrante ctrlIntegrante;
    private CtrlActividad ctrlActividad;
    private int[] cedulas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_actividad);
        nombreProyecto = (EditText) findViewById(R.id.nombreProyGesAct);
        nombreActividad = (EditText) findViewById(R.id.nombreGesAct);
        descripcionActividad = (EditText) findViewById(R.id.descripcionGesAct);
        fechaInicioActividad = (EditText) findViewById(R.id.fechaInicioGesAct);
        fechaFinActividad = (EditText) findViewById(R.id.fechaFinGesAct);
        responsableActividad = (Spinner) findViewById(R.id.responsableGesAct);
        if (Util.getProyecto() != null) {
            nombreProyecto.setHint(Util.getProyecto().getNombre());
        } else {
            nombreProyecto.setHint("Sin proyectos registrados");
        }
        ctrlIntegrante = new CtrlIntegrante(this);
        ctrlActividad = new CtrlActividad(this);
        cargarComboResponsables();
        configurarFechasEdittext();
    }

    /**
     * configura un los edittext de fecha para un calendario
     */
    public void configurarFechasEdittext() {
        fechaInicioActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                DatePickerDialog mDatePicker = new DatePickerDialog(GestionActividadActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        String dia = String.valueOf(selectedday);
                        String mes = String.valueOf(selectedmonth);
                        if (selectedday < 10) {
                            dia = 0 + "" + dia;
                        }
                        if (selectedmonth < 10) {
                            mes = 0 + "" + mes;
                        }
                        fechaInicioActividad.setText(dia + "/" + mes + "/" + selectedyear);
                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        fechaFinActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                DatePickerDialog mDatePicker = new DatePickerDialog(GestionActividadActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        String dia = String.valueOf(selectedday);
                        String mes = String.valueOf(selectedmonth);
                        if (selectedday < 10) {
                            dia = 0 + "" + dia;
                        }
                        if (selectedmonth < 10) {
                            mes = 0 + "" + mes;
                        }
                        fechaFinActividad.setText(dia + "/" + mes + "/" + selectedyear);
                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
    }


    /**
     * carga un combo con los integrantes del proyecto
     */
    public void cargarComboResponsables() {
        if (Util.getProyecto() != null) {
            List<Integrante> integrantes = ctrlIntegrante.listarIntegrantes(Util.getProyecto());
            String[] nombres = new String[integrantes.size() + 1];
            cedulas = new int[integrantes.size()];
            nombres[0] = "Seleccione una opcion";
            for (int i = 0; i < integrantes.size(); i++) {
                nombres[i + 1] = integrantes.get(i).getNombre() + " " + integrantes.get(i).getApellido();
                cedulas[i] = integrantes.get(i).getDocumento();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombres);
            responsableActividad.setAdapter(adapter);
        }
    }

    /**
     * registra una actividad
     *
     * @param view
     */
    public void registrarActividad(View view) {
        if (Util.getProyecto() != null) {
            try {
                if (fechaInicioActividad.getText().toString().equals("") || fechaFinActividad.getText().toString().equals("") ||
                        nombreActividad.getText().toString().equals("") || descripcionActividad.getText().toString().equals("")
                        || responsableActividad.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios, debe seleccionar un responsabe", Toast.LENGTH_SHORT).show();
                } else {
                    String fechaInicioAct = fechaInicioActividad.getText().toString();
                    String fechaFinAct = fechaFinActividad.getText().toString();
                    if (verificarFecha(fechaInicioAct) && verificarFecha(fechaFinAct)) {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date inicio = df.parse(fechaInicioAct);
                        Date fin = df.parse(fechaFinAct);
                        if (inicio.compareTo(fin) != 1) {
                            String nombreAct = nombreActividad.getText().toString();
                            String descripcion = descripcionActividad.getText().toString();
                            List<Actividad> actividades = ctrlActividad.listarActividades();
                            int idActividad = 0;
                            if (!actividades.isEmpty()) {
                                idActividad = actividades.get(actividades.size() - 1).getId() + 1;
                            }
                            Actividad actividadValidar = ctrlActividad.buscarActividad(nombreAct, Util.getProyecto());
                            if (actividadValidar == null) {
                                Integrante integrante = ctrlIntegrante.buscarIntegrante(cedulas[responsableActividad.getSelectedItemPosition() - 1]);
                                Actividad actividad = new Actividad(idActividad, nombreAct, integrante, Util.getProyecto(), fechaInicioAct, fechaFinAct, descripcion);
                                if (ctrlActividad.guardarActividad(actividad)) {
                                    Toast.makeText(getApplicationContext(), "La actividad ha sido registrada", Toast.LENGTH_SHORT).show();
                                    limpiarCampos();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No ha sido posible registrar la actividad", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Ya se encuentra una actividad con este nombre", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "La fecha de inicio es menor a la final", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Debe registrar un proyecto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * busca una actividad
     *
     * @param view
     */
    public void buscarActividad(View view) {
        if (Util.getProyecto() != null) {
            String nombreAct = nombreActividad.getText().toString();
            if (nombreAct.equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            } else {
                Actividad actividad = ctrlActividad.buscarActividad(nombreAct, Util.getProyecto());
                if (actividad != null) {
                    descripcionActividad.setText(actividad.getDescripcion());
                    fechaInicioActividad.setText(actividad.getFechaInicio());
                    fechaFinActividad.setText(actividad.getFechaFin());
                    for (int i = 0; i < responsableActividad.getCount(); i++) {
                        if (responsableActividad.getItemAtPosition(i).toString().equals(actividad.getResponsable().getNombre() + " " + actividad.getResponsable().getApellido())) {
                            responsableActividad.setSelection(i);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se encontro una actividad con el nombre ingresado", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Debe registrar un proyecto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * modifica una actividad
     *
     * @param view
     */
    public void modificarActividad(View view) {
        if (Util.getProyecto() != null) {
            try {
                if (fechaInicioActividad.getText().toString().equals("") || fechaFinActividad.getText().toString().equals("") ||
                        nombreActividad.getText().toString().equals("") || descripcionActividad.getText().toString().equals("")
                        || responsableActividad.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios, debe seleccionar un responsabe", Toast.LENGTH_SHORT).show();
                } else {
                    String fechaInicioAct = fechaInicioActividad.getText().toString();
                    String fechaFinAct = fechaFinActividad.getText().toString();
                    if (verificarFecha(fechaInicioAct) && verificarFecha(fechaFinAct)) {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date inicio = df.parse(fechaInicioAct);
                        Date fin = df.parse(fechaFinAct);
                        if (inicio.compareTo(fin) != 1) {
                            String nombreAct = nombreActividad.getText().toString();
                            String descripcion = descripcionActividad.getText().toString();
                            Actividad actividad = ctrlActividad.buscarActividad(nombreAct, Util.getProyecto());
                            if (actividad != null) {
                                Integrante integrante = ctrlIntegrante.buscarIntegrante(cedulas[responsableActividad.getSelectedItemPosition() - 1]);
                                Actividad actividadM = new Actividad(0, nombreAct, integrante, Util.getProyecto(), fechaInicioAct, fechaFinAct, descripcion);
                                if (ctrlActividad.modificarActividad(actividadM)) {
                                    Toast.makeText(getApplicationContext(), "La actividad ha sido modificada", Toast.LENGTH_SHORT).show();
                                    limpiarCampos();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No ha sido posible modificar la actividad", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No se encontro una actividad con este nombre", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "La fecha de inicio es menor a la final", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Debe registrar un proyecto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * elimina una actividad
     *
     * @param view
     */
    public void eliminarActividad(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreActividad.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            } else {
                String nombre = nombreActividad.getText().toString();
                Actividad actividad = ctrlActividad.buscarActividad(nombre, Util.getProyecto());
                if (actividad != null) {
                    if (ctrlActividad.eliminarActividad(actividad)) {
                        limpiarCampos();
                        Toast.makeText(getApplicationContext(), "La actividad ha sido eliminada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible eliminar la actividad", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha encontrado una actividad con el nombre ingresado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /**
     * verifica la fecha
     *
     * @return true si la fecha es correcta, de lo contrario false
     */
    public boolean verificarFecha(String fecha) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date testDate = null;
            testDate = df.parse(fecha);
            if (!df.format(testDate).equals(fecha)) {
                Toast.makeText(getApplicationContext(), "Fecha invalida!!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Formato invalido!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * limpia los campos de la ventana
     */
    public void limpiarCampos() {
        nombreActividad.setText("");
        descripcionActividad.setText("");
        fechaInicioActividad.setText("");
        fechaFinActividad.setText("");
        responsableActividad.setSelection(0);
    }
}
