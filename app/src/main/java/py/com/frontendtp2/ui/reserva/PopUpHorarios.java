package py.com.frontendtp2.ui.reserva;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import py.com.frontendtp2.MainActivity;
import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.Agenda;
import py.com.frontendtp2.data.model.Categoria;
import py.com.frontendtp2.data.model.Ejemplo;
import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.TipoProducto;
import py.com.frontendtp2.ui.fichaClinica.DatePickerFragment;
import py.com.frontendtp2.ui.fichaClinica.FichaClinicaAdapter;
import py.com.frontendtp2.ui.fichaClinica.FichaClinicaFragment;
import py.com.frontendtp2.ui.fichaClinica.PopUp;
import py.com.frontendtp2.ui.fichaClinica.PopUpCliente;
import py.com.frontendtp2.ui.paciente.EmpleadoPopUp;
import py.com.frontendtp2.ui.paciente.PacienteAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopUpHorarios extends AppCompatDialogFragment implements
PopUp.PopUpListener, DatePickerDialog.OnDateSetListener {
    private TextView empleadoSeleccionado;
    private TextView fechaSeleccionada;
    private TextView editSeleccionado;
    public PopUpHorariosListener listener;
    private Agenda[] agendas;
    private Persona empleadoSelec;
    private Agenda agendaSelec;
    AgendaAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_popup_horarios, null);

        //listener = (PopUpListener) getChildFragmentManager();
        builder.setView(view)
                .setTitle("Seleccione un Item de la lista:")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(agendaSelec != null) {
                            Agenda agenda = agendaSelec;
                            listener.applyTextsHorario(agenda, empleadoSelec);
                        }else{
                            Toast.makeText(getContext(),"Es necesario marcar un elemento.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now));

        editSeleccionado = view.findViewById(R.id.editSeleccion);
        empleadoSeleccionado = view.findViewById(R.id.empleadoSeleccionado);
        Persona empleadoAux = new Persona();
        empleadoAux.setIdPersona(2);
        empleadoAux.setNombre("Proveedor");
        empleadoAux.setApellido("Generico");
        String text = empleadoAux.getNombre()+' '+empleadoAux.getApellido();
        empleadoSeleccionado.setText(text);
        empleadoSelec = empleadoAux;

        fechaSeleccionada = view.findViewById(R.id.fechaSeleccionada);
        fechaSeleccionada.setText(dtf.format(now));


        ListView listView = view.findViewById(R.id.horariosPopUp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Agenda item = adapter.getItemAtPosition(i);
                if(item.getIdCliente() == null){
                    agendaSelec = item;
                    String text = item.getHoraInicioCadena()+" a "+item.getHoraFinCadena();
                    editSeleccionado.setText(text);
                }else{
                    Toast.makeText(getContext(),"El horario ya esta reservado a un cliente", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //BOTON LLAMADA A POPUP EMPLEADO
        Button buttonBuscarEmpleado = view.findViewById(R.id.buttonBuscarEmpleado);
        buttonBuscarEmpleado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUp exampleDialog = new PopUp();
                exampleDialog.listener = (PopUp.PopUpListener) PopUpHorarios.this;
                exampleDialog.show(getFragmentManager(), "example dialog");
            }

        });

        //BOTON FECHA DESDE
        Button buttonSeleccionarFechaDesde = (Button) view.findViewById(R.id.buttonSeleccionarFecha);
        buttonSeleccionarFechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(PopUpHorarios.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        //LLAMADA


        Call<Agenda[]> callPersona= Servicios.getAgendaService().obtenerAgendas("2",
                dtf.format(now));
        callPersona.enqueue(new Callback<Agenda[]>() {
            @Override
            public void onResponse(Call<Agenda[]> call, Response<Agenda[]> response) {
                if(response.isSuccessful()) {
                    agendas = response.body();

                    if(agendas.length == 0){
                        Toast.makeText(getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                    }else {

                        int i = 0;
                        i++;

                        ListView listView = (ListView) view.findViewById(R.id.horariosPopUp);
                        adapter = new AgendaAdapter(getContext(), agendas);
                        listView.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Agenda[]> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });

        //FILTRADO EN BUSCAR
        Button botonBuscar= view.findViewById(R.id.btnEventoBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view2) {

                Call<Agenda[]> callFicha = Servicios.getAgendaService().obtenerAgendas(
                        String.valueOf(empleadoSelec.getIdPersona()),
                        (String) fechaSeleccionada.getText());
                callFicha.enqueue(new Callback<Agenda[]>() {
                    @Override
                    public void onResponse(Call<Agenda[]> call, Response<Agenda[]> response) {
                        if(response.isSuccessful()) {
                            agendas = response.body();

                            if(agendas.length == 0){
                                Toast.makeText(getView().getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                                ListView listView = (ListView) view.findViewById(R.id.horariosPopUp);
                                adapter = new AgendaAdapter(getContext(), agendas);
                                listView.setAdapter(adapter);
                            }else {

                                int i = 0;
                                i++;

                                ListView listView = (ListView) view.findViewById(R.id.horariosPopUp);
                                adapter = new AgendaAdapter(getContext(), agendas);
                                listView.setAdapter(adapter);
                            }
                        }else {
                            Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Agenda[]> call, Throwable t) {
                        Log.w("warning",t.getCause().toString());
                    }
                });
            }

        });

        return builder.create();
    }


    public interface PopUpHorariosListener {
        void applyTextsHorario(Agenda agenda, Persona empleado);
    }

    @Override
    public void applyTexts(Persona empleado) {
        empleadoSelec = empleado;
        String text = empleadoSelec.getNombre()+' '+empleadoSelec.getApellido();
        empleadoSeleccionado.setText(text);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dayOfMonthString = String.valueOf(dayOfMonth);
        if(dayOfMonth<10){
            dayOfMonthString = '0'+String.valueOf(dayOfMonth);
        }
        month = month+1;
        String fecha = String.valueOf(year)+ month + dayOfMonthString;
        fechaSeleccionada.setText(fecha);
    }
}

