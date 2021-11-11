package py.com.frontendtp2.ui.reserva;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.Categoria;
import py.com.frontendtp2.data.model.Ejemplo;
import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.Reserva;
import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.TipoProducto;
import py.com.frontendtp2.ui.fichaClinica.DatePickerFragment;
import py.com.frontendtp2.ui.fichaClinica.FichaClinicaAdapter;
import py.com.frontendtp2.ui.fichaClinica.FichaClinicaFragment;
import py.com.frontendtp2.ui.fichaClinica.FichaClinicaNew;
import py.com.frontendtp2.ui.fichaClinica.PopUp;
import py.com.frontendtp2.ui.fichaClinica.PopUpCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReservaFragment extends Fragment implements DatePickerDialog.OnDateSetListener,
        PopUp.PopUpListener, PopUpCliente.PopUpClienteListener {

    private ReservaViewModel reservaViewModel;
    private Reserva[] reservas;
    ReservaAdapter adapter;
    TextView empleadoSeleccionado;
    TextView clienteSeleccionado;
    TextView fechaDesdeSeleccionada;
    TextView fechaHastaSeleccionada;
    Persona empleadoSelec;
    Persona clienteSelec;
    Boolean fechaDesde;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reservaViewModel =
                ViewModelProviders.of(this).get(ReservaViewModel.class);

        View root = inflater.inflate(R.layout.fragment_reserva, container, false);

        empleadoSeleccionado = root.findViewById(R.id.empleadoSeleccionado);
        clienteSeleccionado = root.findViewById(R.id.clienteSeleccionado);
        fechaDesdeSeleccionada = root.findViewById(R.id.fechaDesdeSeleccionada);
        fechaHastaSeleccionada = root.findViewById(R.id.fechaHastaSeleccionada);



        //BOTON LLAMADA A POPUP EMPLEADO
        Button buttonBuscarEmpleado= root.findViewById(R.id.buttonBuscarEmpleado);
        buttonBuscarEmpleado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUp exampleDialog = new PopUp();
                exampleDialog.listener = (PopUp.PopUpListener) ReservaFragment.this;
                exampleDialog.show(getFragmentManager(), "example dialog");
            }

        });

        //BOTON LLAMADA A POPUP CLIENTE
        Button buttonBuscarCliente= root.findViewById(R.id.buttonBuscarCliente);
        buttonBuscarCliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUpCliente exampleDialog = new PopUpCliente();
                exampleDialog.listener = (PopUpCliente.PopUpClienteListener) ReservaFragment.this;
                exampleDialog.show(getFragmentManager(), "example dialog");
            }

        });

        //BOTON FECHA DESDE
        Button buttonSeleccionarFechaDesde = (Button) root.findViewById(R.id.buttonSeleccionarFechaDesde);
        buttonSeleccionarFechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaDesde = true;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(ReservaFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        //BOTON FECHA HASTA
        Button buttonSeleccionarFechaHasta = (Button) root.findViewById(R.id.buttonSeleccionarFechaHasta);
        buttonSeleccionarFechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaDesde = false;
                DialogFragment datePicker = new DatePickerFragment();
                //datePicker.setTargetFragment(ReservaFragment.this, 0);
                datePicker.show(getFragmentManager().beginTransaction(), "date picker");
            }
        });

        //IR AL SELECTED
        ListView listView = root.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Reserva item = adapter.getItemAtPosition(i);

                Intent intent = new Intent(getContext(), SelectedReserva.class);
                Gson gS = new Gson();
                String reserva = gS.toJson(item);
                Bundle b = new Bundle();
                b.putString("reserva",reserva);

                intent.putExtras(b);

                startActivity(intent);
            }


        });



        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now));
        Ejemplo ejemplo = new Ejemplo();
        Gson gson = new Gson();
        ejemplo.setFechaDesdeCadena(dtf.format(now));
        ejemplo.setFechaHastaCadena(dtf.format(now));
        String json = gson.toJson(ejemplo);

        fechaDesdeSeleccionada.setText(dtf.format(now));
        fechaHastaSeleccionada.setText(dtf.format(now));

        Call<Lista<Reserva>> callReserva= Servicios.getReservaService().obtenerReservas(
                "idReserva", "asc", "S", json
        );
        callReserva.enqueue(new Callback<Lista<Reserva>>() {

            @Override
            public void onResponse(Call<Lista<Reserva>> call, Response<Lista<Reserva>> response) {

                if(response.isSuccessful()) {
                    reservas = response.body().getLista();
                    
                    if(reservas.length == 0){
                        Toast.makeText(getView().getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                    }else{
                        ListView listView = (ListView)getView().findViewById(R.id.list_view);
                        adapter = new ReservaAdapter(getContext(), reservas);
                        listView.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Lista<Reserva>> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });

        //FILTRADO EN BUSCAR
        Button botonBuscar= root.findViewById(R.id.btnEventoBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Ejemplo ejemplo = new Ejemplo();
                Gson gson = new Gson();
                String json = "";

                String fechaDesdeSelec = (String) fechaDesdeSeleccionada.getText();
                String fechaHastaSelec = (String) fechaHastaSeleccionada.getText();

                if(empleadoSelec != null){
                    Persona personaAux = new Persona();
                    personaAux.setIdPersona(empleadoSelec.getIdPersona());
                    ejemplo.setIdEmpleado(personaAux);
                }
                if(clienteSelec != null){
                    Persona personaAux = new Persona();
                    personaAux.setIdPersona(clienteSelec.getIdPersona());
                    ejemplo.setIdCliente(personaAux);
                }

                if(!fechaDesdeSelec.equals("No Seleccionado")){
                    ejemplo.setFechaDesdeCadena(fechaDesdeSelec);
                }
                if(!fechaHastaSelec.equals("No Seleccionado")){
                    ejemplo.setFechaHastaCadena(fechaHastaSelec);
                }

                if (empleadoSelec == null && clienteSelec == null
                        && fechaDesdeSelec.equals("No Seleccionado") && fechaHastaSelec.equals("No Seleccionado")){
                    Toast.makeText(getView().getContext(), "Seleccione opciones de busqueda", Toast.LENGTH_SHORT).show();
                }else {
                    json = gson.toJson(ejemplo);
                }

                Call<Lista<Reserva>> callReserva = Servicios.getReservaService().obtenerReservas(
                        "idReserva",
                        "asc", "S", json);
                callReserva.enqueue(new Callback<Lista<Reserva>>() {
                    @Override
                    public void onResponse(Call<Lista<Reserva>> call, Response<Lista<Reserva>> response) {
                        if(response.isSuccessful()) {
                            reservas = response.body().getLista();

                            if(reservas.length == 0){
                                Toast.makeText(getView().getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                                ListView listView = (ListView) getView().findViewById(R.id.list_view);
                                adapter = new ReservaAdapter(getContext(), reservas);
                                listView.setAdapter(adapter);
                            }else {

                                int i = 0;
                                i++;

                                ListView listView = (ListView) getView().findViewById(R.id.list_view);
                                adapter = new ReservaAdapter(getContext(), reservas);
                                listView.setAdapter(adapter);
                            }
                        }else {
                            Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Lista<Reserva>> call, Throwable t) {
                        Log.w("warning",t.getCause().toString());
                    }
                });
            }

        });


        //BOTON LIMPIAR
        Button botonLimpiar= root.findViewById(R.id.btnEventoLimpiar);
        botonLimpiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Call<Lista<Reserva>> callReserva = Servicios.getReservaService().obtenerReservas(
                        "idReserva",
                        "asc", "S", "");
                callReserva.enqueue(new Callback<Lista<Reserva>>() {
                    @Override
                    public void onResponse(Call<Lista<Reserva>> call, Response<Lista<Reserva>> response) {
                        if(response.isSuccessful()) {
                            reservas = response.body().getLista();

                            if(reservas.length == 0){
                                Toast.makeText(getView().getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                                ListView listView = (ListView) getView().findViewById(R.id.list_view);
                                adapter = new ReservaAdapter(getContext(), reservas);
                                listView.setAdapter(adapter);
                            }else {

                                empleadoSelec = null;
                                clienteSelec = null;
                                String text = "No Seleccionado";
                                empleadoSeleccionado.setText(text);
                                clienteSeleccionado.setText(text);
                                fechaDesdeSeleccionada.setText(text);
                                fechaHastaSeleccionada.setText(text);

                                int i = 0;
                                i++;

                                ListView listView = (ListView) getView().findViewById(R.id.list_view);
                                adapter = new ReservaAdapter(getContext(), reservas);
                                listView.setAdapter(adapter);

                            }
                        }else {
                            Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Lista<Reserva>> call, Throwable t) {
                        Log.w("warning",t.getCause().toString());
                    }
                });
            }

        });

        FloatingActionButton fabNuevoUsuario = root.findViewById(R.id.fabNuevaReserva);
        fabNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReservaNew.class);
                startActivity(intent);
            }
        });
        
        return root;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dayOfMonthString = String.valueOf(dayOfMonth);
        if(dayOfMonth<10){
            dayOfMonthString = '0'+String.valueOf(dayOfMonth);
        }
        month = month+1;
        String fecha = String.valueOf(year)+ month + dayOfMonthString;
        if(fechaDesde){
            fechaDesdeSeleccionada.setText(fecha);
        }else{
            fechaHastaSeleccionada.setText(fecha);
        }

    }


    @Override
    public void applyTexts(Persona empleado) {
        empleadoSelec = empleado;
        String text = empleadoSelec.getNombre()+' '+empleadoSelec.getApellido();
        empleadoSeleccionado.setText(text);
    }

    @Override
    public void applyTextsCliente(Persona cliente) {
        clienteSelec = cliente;
        String text = clienteSelec.getNombre()+' '+clienteSelec.getApellido();
        clienteSeleccionado.setText(text);
    }
}