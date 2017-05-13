package com.example.jairo.proyectopmi;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jairo.proyectopmi.controlador.CtrlProyecto;
import com.example.jairo.proyectopmi.modelo.Proyecto;
import com.example.jairo.proyectopmi.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GestionProyectoActivity extends AppCompatActivity {

    private EditText nombre, fechaInicio, fechaFin, etapa;
    private CtrlProyecto ctrlProyecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_proyecto);
        nombre = (EditText) findViewById(R.id.nombreProyectoReg);
        fechaInicio = (EditText) findViewById(R.id.fechaInicioProyecto);
        fechaFin = (EditText) findViewById(R.id.fechaFinProyecto);
        etapa = (EditText) findViewById(R.id.etapaProyecto);
        ctrlProyecto = new CtrlProyecto(this);
        configurarFechasEdittext();
    }

    /**
     * configura un los edittext de fecha para un calendario
     */
    public void configurarFechasEdittext() {
        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                DatePickerDialog mDatePicker = new DatePickerDialog(GestionProyectoActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        fechaInicio.setText(dia + "/" + mes + "/" + selectedyear);
                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                DatePickerDialog mDatePicker = new DatePickerDialog(GestionProyectoActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        fechaFin.setText(dia + "/" + mes + "/" + selectedyear);
                    }
                }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
    }


    /**
     * registra un proyecto en la bd
     *
     * @param view
     */
    public void registrarProyecto(View view) {
        try {
            if (fechaInicio.getText().toString().equals("") || fechaFin.getText().toString().equals("") ||
                    etapa.getText().toString().equals("") || nombre.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                String fechaInicioProyecto = fechaInicio.getText().toString();
                String fechaFinProyecto = fechaFin.getText().toString();
                if (verificarFecha(fechaInicioProyecto) && verificarFecha(fechaFinProyecto)) {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date inicio = df.parse(fechaInicioProyecto);
                    Date fin = df.parse(fechaFinProyecto);
                    if (inicio.compareTo(fin) != 1) {
                        int etapaProyecto = Integer.parseInt(etapa.getText().toString());
                        List<Proyecto> proyectos = ctrlProyecto.listarProyectos();
                        int idProyecto = 0;
                        if (!proyectos.isEmpty()) {
                            idProyecto = proyectos.get(proyectos.size() - 1).getId() + 1;
                        }
                        String nombreProy = nombre.getText().toString();
                        boolean validarNombre = true;
                        for (int i = 0; i < proyectos.size(); i++) {
                            if (proyectos.get(i).getNombre().equals(nombreProy)) {
                                validarNombre = false;
                            }
                        }
                        if (validarNombre) {
                            long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
                            double duracion = (fin.getTime() - inicio.getTime()) / MILLSECS_PER_DAY;
                            if (Util.proyecto == null) {
                                if (ctrlProyecto.guardarProyecto(idProyecto, nombreProy, fechaInicioProyecto, fechaFinProyecto, duracion, etapaProyecto, Util.getUsuario().getDocumento())) {
                                    Toast.makeText(getApplicationContext(), "El proyecto ha sido registrado", Toast.LENGTH_SHORT).show();
                                    Util.setProyecto(ctrlProyecto.buscarProyectoUsuario(Util.getUsuario().getDocumento(), nombreProy));
                                    limpiarCampos();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No ha sido posible registrar el proyecto", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (ctrlProyecto.buscarProyectoUsuario(Util.getUsuario().getDocumento(), Util.getProyecto().getNombre()) == null) {
                                    if (ctrlProyecto.guardarProyecto(idProyecto, nombreProy, fechaInicioProyecto, fechaFinProyecto, duracion, etapaProyecto, Util.getUsuario().getDocumento())) {
                                        Toast.makeText(getApplicationContext(), "El proyecto ha sido registrado", Toast.LENGTH_SHORT).show();
                                        Util.setProyecto(ctrlProyecto.buscarProyectoUsuario(Util.getUsuario().getDocumento(), nombreProy));
                                        limpiarCampos();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No ha sido posible registrar el proyecto", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Ya existe un proyecto registrado para usted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Ya existe un proyecto con este nombre", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "La fecha de inicio es menor a la final", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * busca un proyecto por nombre
     *
     * @param view
     */
    public void buscarProyecto(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombre.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            } else {
                String nombreProy = nombre.getText().toString();
                Proyecto proyecto = ctrlProyecto.buscarProyecto(nombreProy);
                if (proyecto != null) {
                    if (ctrlProyecto.buscarProyectoUsuario(Util.getUsuario().getDocumento(), proyecto.getNombre()) != null) {
                        etapa.setText(String.valueOf(proyecto.getEtapa()));
                        fechaInicio.setText(proyecto.getFechaInicio());
                        fechaFin.setText(proyecto.getFechaFin());
                    } else {
                        Toast.makeText(getApplicationContext(), "No se ha encontrado un proyecto con el nombre ingresado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha encontrado un proyecto con el nombre ingresado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /**
     * edita un proyecto
     *
     * @param view
     */
    public void editarProyecto(View view) {
        try {
            if (Util.getProyecto() == null) {
                Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
            } else {
                if (fechaInicio.getText().toString().equals("") || fechaFin.getText().toString().equals("") ||
                        etapa.getText().toString().equals("") || nombre.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    String fechaInicioProyecto = fechaInicio.getText().toString();
                    String fechaFinProyecto = fechaFin.getText().toString();
                    if (verificarFecha(fechaInicioProyecto) && verificarFecha(fechaFinProyecto)) {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date inicio = df.parse(fechaInicioProyecto);
                        Date fin = df.parse(fechaFinProyecto);
                        if (inicio.compareTo(fin) != 1) {
                            int etapaProyecto = Integer.parseInt(etapa.getText().toString());
                            String nombreProy = nombre.getText().toString();
                            long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
                            double duracion = (fin.getTime() - inicio.getTime()) / MILLSECS_PER_DAY;
                            if (ctrlProyecto.buscarProyectoUsuario(Util.getUsuario().getDocumento(), nombreProy) != null) {
                                if (ctrlProyecto.modificarProyecto(0, nombreProy, fechaInicioProyecto, fechaFinProyecto, duracion, etapaProyecto, Util.getUsuario().getDocumento())) {
                                    Toast.makeText(getApplicationContext(), "El proyecto ha sido modificado", Toast.LENGTH_SHORT).show();
                                    Util.setProyecto(ctrlProyecto.buscarProyecto(nombreProy));
                                    limpiarCampos();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No se ha encontrado un proyecto con el nombre ingresado", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No se ha encontrado un proyecto con el nombre ingresado", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "La fecha de inicio es menor a la final", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * elimina un proyecto
     *
     * @param view
     */
    public void eliminarProyecto(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombre.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            } else {
                String nombreProy = nombre.getText().toString();
                if (ctrlProyecto.buscarProyectoUsuario(Util.getUsuario().getDocumento(), nombreProy) != null) {
                    if (ctrlProyecto.eliminarProyecto(nombreProy)) {
                        limpiarCampos();
                        Util.setProyecto(null);
                        Toast.makeText(getApplicationContext(), "El proyecto ha sido eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible eliminar el proyecto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha encontrado un proyecto con el nombre ingresado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * limpia los campos de la ventana
     */
    public void limpiarCampos() {
        nombre.setText("");
        fechaInicio.setText("");
        fechaFin.setText("");
        etapa.setText("");
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
}
