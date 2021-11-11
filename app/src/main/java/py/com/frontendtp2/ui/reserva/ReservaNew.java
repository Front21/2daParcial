package py.com.frontendtp2.ui.reserva;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.R;
import org.w3c.dom.Text;

import py.com.frontendtp2.MainActivity;
import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.PersonaService;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.Agenda;
import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.Reserva;
import py.com.frontendtp2.ui.fichaClinica.DatePickerFragment;
import py.com.frontendtp2.ui.fichaClinica.FichaClinicaFragment;
import py.com.frontendtp2.ui.fichaClinica.PopUp;
import py.com.frontendtp2.ui.fichaClinica.PopUpCliente;
import py.com.frontendtp2.ui.fichaClinica.PopUpCliente.PopUpClienteListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaNew extends AppCompatActivity implements PopUpHorarios.PopUpHorariosListener,
        PopUpCliente.PopUpClienteListener {
    TextView empleadoSeleccionado;
    Persona empleadoSelec;
    TextView fechaSeleccionada;
    TextView horarioSeleccionado;
    TextView clienteSeleccionado;
    Persona clienteSelec;
    EditText observacion;
    Agenda agendaSelec;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_new);

        observacion=findViewById(R.id.observacion);
        horarioSeleccionado=findViewById(R.id.horarioSeleccionado);
        clienteSeleccionado=findViewById(R.id.clienteSeleccionado);
        observacion=findViewById(R.id.observacion);

        //BOTON LLAMADA A POPUP CLIENTE
        Button buttonBuscarCliente = findViewById(R.id.buttonBuscarCliente);
        buttonBuscarCliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUpCliente exampleDialog = new PopUpCliente();
                exampleDialog.listener = (PopUpClienteListener) ReservaNew.this;
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
            }

        });

        //BOTON LLAMADA A POPUP HORARIO
        Button buttonSeleccionarHorario = findViewById(R.id.buttonSeleccionarHorario);
        buttonSeleccionarHorario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUpHorarios exampleDialog = new PopUpHorarios();
                exampleDialog.listener = (PopUpHorarios.PopUpHorariosListener) ReservaNew.this;
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
            }

        });

        //BOTON GUARDAR
        Button buttoGuardar = findViewById(R.id.btnEventoGuardar);
        buttoGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Reserva r = new Reserva();
                r.setFechaCadena(agendaSelec.getFechaCadena());
                r.setHoraInicioCadena(agendaSelec.getHoraInicioCadena());
                r.setHoraFinCadena(agendaSelec.getHoraFinCadena());
                Persona personaAux = new Persona();
                personaAux.setIdPersona(empleadoSelec.getIdPersona());
                r.setIdEmpleado(personaAux);
                Persona personaAux2 = new Persona();
                personaAux2.setIdPersona(clienteSelec.getIdPersona());
                r.setIdCliente(personaAux2);
                r.setObservacion(observacion.getText().toString());

                Call<Reserva> callReserva;
                callReserva= Servicios.getReservaService().agregarReserva(r);
                callReserva.enqueue(new Callback<Reserva>() {
                    @Override
                    public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                        Reserva reserva = response.body();
                        Toast.makeText(ReservaNew.this,"Reserva agregada exitosamente",Toast.LENGTH_LONG).show();
                        Bundle b = new Bundle();
                        b.putString("origen", "reserva");
                        Intent intent = new Intent(ReservaNew.this, MainActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Reserva> call, Throwable t) {
                        Log.w("warning", t.getCause().toString());
                    }
                });

            }

        });


    }

    @Override
    public void applyTextsCliente(Persona cliente) {
        clienteSelec = cliente;
        String text = clienteSelec.getNombre()+' '+clienteSelec.getApellido();
        clienteSeleccionado.setText(text);
    }

    @Override
    public void applyTextsHorario(Agenda agenda, Persona empleado) {
        agendaSelec = agenda;
        String text = agendaSelec.getHoraInicioCadena()+" a "+agendaSelec.getHoraFinCadena();
        horarioSeleccionado.setText(text);
        empleadoSelec = empleado;
    }
   /* //Método para volver a la lista de pacientes
    public void NewPaciente(View view)
    {
        Intent Pac= new Intent(this,PacienteFragment.class);
        startActivity(Pac);
    }*/

    public void guardar(View view) {
        FichaClinica c=new FichaClinica();
        //c.setMotivoConsulta(motivo.getText().toString());
        //c.setDiagnostico(diagnostico.getText().toString());
        c.setObservacion(observacion.getText().toString());
        //c.setIdCliente(paciente.getText().toString());
        //c.setIdEmpleado(fisioterapeuta.getText().toString());


        Call<FichaClinica> callPersona = null;


        callPersona= Servicios.getFichaClinicaService().agregarFichaClinica(c);


        callPersona.enqueue(new Callback<FichaClinica>() {
            @Override
            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
                //Toast.makeText(py.com.frontendtp2.ui.fichaClinica.FichaClinicaNew.this,"Paciente agregado exitosamente",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<FichaClinica> call, Throwable t) {
                Log.w("warning", t.getCause().toString());
            }
        });
    }

}
