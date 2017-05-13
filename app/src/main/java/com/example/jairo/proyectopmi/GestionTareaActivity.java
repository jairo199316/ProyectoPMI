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
import com.example.jairo.proyectopmi.controlador.CtrlTarea;
import com.example.jairo.proyectopmi.modelo.Actividad;
import com.example.jairo.proyectopmi.modelo.Proyecto;
import com.example.jairo.proyectopmi.modelo.Tarea;
import com.example.jairo.proyectopmi.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GestionTareaActivity extends AppCompatActivity {

    private EditText nombreProyecto, nombreTarea, porcentaje, fechaInicio, fechaFin, estado;
    private Spinner actividades;
    private CtrlActividad ctrlActividad;
    private CtrlTarea ctrlTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_tarea);
        nombreProyecto = (EditText) findViewById(R.id.nombreProyectoGesTar);
        nombreTarea = (EditText) findViewById(R.id.nombreGesTar);
        porcentaje = (EditText) findViewById(R.id.porcentajeGesTar);
        fechaInicio = (EditText) findViewById(R.id.fechaInicioGesTar);
        fechaFin = (EditText) findViewById(R.id.fechaFinGesTar);
        estado = (EditText) findViewById(R.id.estadoGesTar);
        actividades = (Spinner) findViewById(R.id.actividadesGesTar);
        ctrlActividad = new CtrlActividad(this);
        ctrlTarea = new CtrlTarea(this);
        if (Util.getProyecto() != null) {
            nombreProyecto.setHint(Util.getProyecto().getNombre());
        } else {
            nombreProyecto.setHint("Sin proyectos registrados");
        }
        configurarFechasEdittext();
        cargarActividades();
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
                DatePickerDialog mDatePicker = new DatePickerDialog(GestionTareaActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog mDatePicker = new DatePickerDialog(GestionTareaActivity.this, new DatePickerDialog.OnDateSetListener() {
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
     * carga el combo con las actividades registradas
     */
    public void cargarActividades() {
        List<Actividad> actividadesLista = new ArrayList<>();
        if (Util.getProyecto() != null) {
            actividadesLista = ctrlActividad.listarActividades(Util.getProyecto());
        }
        String[] nombresActividades = new String[actividadesLista.size() + 1];
        nombresActividades[0] = "Seleccione una opcion";
        for (int i = 0; i < actividadesLista.size(); i++) {
            nombresActividades[i + 1] = actividadesLista.get(i).getNombre();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombresActividades);
        actividades.setAdapter(adapter);
    }

    /**
     * busca una tarea
     *
     * @param view
     */
    public void buscarTarea(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreTarea.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            } else {
                String nombre = nombreTarea.getText().toString();
                List<Tarea> tareas = ctrlTarea.listarTareas();
                Tarea tarea = null;
                for (int i = 0; i < tareas.size(); i++) {
                    if (tareas.get(i).getNombre().equals(nombre) &&
                            tareas.get(i).getActividad().getProyecto().getNombre().equals(Util.getProyecto().getNombre())) {
                        tarea = tareas.get(i);
                    }
                }
                if (tarea != null) {
                    porcentaje.setText(tarea.getPorcentaje() + "");
                    fechaInicio.setText(tarea.getFechaInicio());
                    fechaFin.setText(tarea.getFechaFin());
                    estado.setText(tarea.getEstado());
                    for (int i = 0; i < actividades.getCount(); i++) {
                        if (actividades.getItemAtPosition(i).toString().equals(tarea.getActividad().getNombre())) {
                            actividades.setSelection(i);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha encontrado una tarea con el nombre ingresado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * agraga una tarea a la bd
     *
     * @param view
     */
    public void agregarTarea(View view) {
        try {
            if (Util.getProyecto() == null) {
                Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
            } else {
                if (fechaInicio.getText().toString().equals("") || fechaFin.getText().toString().equals("") ||
                        nombreTarea.getText().toString().equals("") || porcentaje.getText().toString().equals("")
                        || estado.getText().toString().equals("") || actividades.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    String fechaInicioTarea = fechaInicio.getText().toString();
                    String fechaFinTarea = fechaFin.getText().toString();
                    if (verificarFecha(fechaInicioTarea) && verificarFecha(fechaFinTarea)) {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date inicio = df.parse(fechaInicioTarea);
                        Date fin = df.parse(fechaFinTarea);
                        if (inicio.compareTo(fin) != 1) {
                            List<Tarea> tareas = ctrlTarea.listarTareas();
                            int idTarea = 0;
                            if (!tareas.isEmpty()) {
                                idTarea = tareas.get(tareas.size() - 1).getId() + 1;
                            }
                            boolean validarNombre = true;
                            String nombre = nombreTarea.getText().toString();
                            for (int i = 0; i < tareas.size(); i++) {
                                if (tareas.get(i).getNombre().equals(nombre) &&
                                        tareas.get(i).getActividad().getProyecto().getNombre().equals(Util.getProyecto().getNombre())) {
                                    validarNombre = false;
                                }
                            }
                            if (validarNombre) {
                                double porcentajeTarea = Double.parseDouble(porcentaje.getText().toString());
                                if (porcentajeTarea > 100) {
                                    Toast.makeText(getApplicationContext(), "El porcentaje no debe ser mayor a cien", Toast.LENGTH_SHORT).show();
                                } else {
                                    String estadoTarea = estado.getText().toString();
                                    Actividad actividad = ctrlActividad.buscarActividad(actividades.getSelectedItem().toString(), Util.getProyecto());
                                    Tarea tarea = new Tarea(idTarea, nombre, porcentajeTarea, fechaInicioTarea, fechaFinTarea, estadoTarea, actividad);
                                    if (ctrlTarea.guardarTarea(tarea)) {
                                        Toast.makeText(getApplicationContext(), "La tarea ha sido registrada", Toast.LENGTH_SHORT).show();
                                        limpiarCampos();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No ha sido posible registrar la tarea", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Ya existe una tarea con este nombre", Toast.LENGTH_SHORT).show();
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
     * modifica una tarea
     *
     * @param view
     */
    public void modificarTarea(View view) {
        try {
            if (Util.getProyecto() == null) {
                Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
            } else {
                if (fechaInicio.getText().toString().equals("") || fechaFin.getText().toString().equals("") ||
                        nombreTarea.getText().toString().equals("") || porcentaje.getText().toString().equals("")
                        || estado.getText().toString().equals("") || actividades.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    String fechaInicioTarea = fechaInicio.getText().toString();
                    String fechaFinTarea = fechaFin.getText().toString();
                    if (verificarFecha(fechaInicioTarea) && verificarFecha(fechaFinTarea)) {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date inicio = df.parse(fechaInicioTarea);
                        Date fin = df.parse(fechaFinTarea);
                        if (inicio.compareTo(fin) != 1) {
                            boolean validarNombre = true;
                            String nombre = nombreTarea.getText().toString();
                            double porcentajeTarea = Double.parseDouble(porcentaje.getText().toString());
                            if (porcentajeTarea > 100) {
                                Toast.makeText(getApplicationContext(), "El porcentaje no debe ser mayor a cien", Toast.LENGTH_SHORT).show();
                            } else {
                                String estadoTarea = estado.getText().toString();
                                Actividad actividad = ctrlActividad.buscarActividad(actividades.getSelectedItem().toString(), Util.getProyecto());
                                Tarea tarea = new Tarea(0, nombre, porcentajeTarea, fechaInicioTarea, fechaFinTarea, estadoTarea, actividad);
                                if (ctrlTarea.modificarTarea(tarea)) {
                                    Toast.makeText(getApplicationContext(), "La tarea ha sido modificada", Toast.LENGTH_SHORT).show();
                                    limpiarCampos();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No se ha encontrado una tarea con el nombre y actividad ingresados", Toast.LENGTH_SHORT).show();
                                }
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
     * elimina una tarea
     *
     * @param view
     */
    public void eliminarTarea(View view) {
        if (Util.getProyecto() == null) {
            Toast.makeText(getApplicationContext(), "Usted no tiene proyectos registrados", Toast.LENGTH_SHORT).show();
        } else {
            if (nombreTarea.getText().toString().equals("") && actividades.getSelectedItemPosition() == 0) {
                Toast.makeText(getApplicationContext(), "Debe ingresar un nombre y seleccionar una actividad", Toast.LENGTH_SHORT).show();
            } else {
                String nombre = nombreTarea.getText().toString();
                Actividad actividad = ctrlActividad.buscarActividad(actividades.getSelectedItem().toString(), Util.getProyecto());
                Tarea tarea = ctrlTarea.buscarTarea(nombre, actividad);
                if (tarea != null) {
                    if (ctrlTarea.eliminarTarea(tarea)) {
                        limpiarCampos();
                        Toast.makeText(getApplicationContext(), "La tarea ha sido eliminada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No ha sido posible eliminar la tarea", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Debe buscar la tarea antes de eliminarla", Toast.LENGTH_SHORT).show();
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
        nombreTarea.setText("");
        porcentaje.setText("");
        fechaInicio.setText("");
        fechaFin.setText("");
        estado.setText("");
        actividades.setSelection(0);
    }
}
