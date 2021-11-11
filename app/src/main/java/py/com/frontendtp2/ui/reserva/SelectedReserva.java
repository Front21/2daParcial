package py.com.frontendtp2.ui.reserva;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import py.com.frontendtp2.MainActivity;
import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.Reserva;
import py.com.frontendtp2.ui.paciente.Paciente_ind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static py.com.frontendtp2.Services.Servicios.getReservaService;

public class SelectedReserva extends AppCompatActivity {

    Reserva reserva;
    TextView idView ;
    TextView empleadoView;
    TextView nombreView;
    TextView fechaView;
    TextView estadoView;
    EditText obs;
    EditText asistencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_reserva);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            Gson gS = new Gson();
            reserva = gS.fromJson(bundle.getString("reserva"), Reserva.class);

            idView = findViewById(R.id.reservaId);
            empleadoView = findViewById(R.id.empleadoApellido);
            nombreView = findViewById(R.id.clienteNombreCompleto);
            fechaView = findViewById(R.id.fechaReserva);
            estadoView = findViewById(R.id.estadoReserva);
            obs = findViewById(R.id.obs);
            asistencia = findViewById(R.id.asistencia);

            idView.setText(reserva.getIdReserva().toString());
            empleadoView.setText(reserva.getIdEmpleado().getApellido().toUpperCase());
            nombreView.setText(reserva.getIdCliente().getNombreCompleto());
            estadoView.setText(reserva.getFlagEstado());
            obs.setText(reserva.getObservacion());
            asistencia.setText(reserva.getFlagAsistio());
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
            try {
                Date date = parseador.parse(reserva.getFecha());
                fechaView.setText(formateador.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Button button= findViewById(R.id.guardar);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean flagAsistencia = false;
                boolean flagEstado = false;

                if(reserva.getFlagAsistio() != null){
                    if(!reserva.getFlagAsistio().equals("S") && !reserva.getFlagAsistio().equals("N")){
                        flagAsistencia = true;
                    }else{
                        Toast.makeText(SelectedReserva.this, "Ya se marco asistencia. No es posible modificar.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    flagAsistencia = true;
                }


                if(!reserva.getFlagEstado().equals("C")){
                        flagEstado = true;
                }else{
                    Toast.makeText(SelectedReserva.this, "Reserva Cancelada. No es posible modificar.", Toast.LENGTH_LONG).show();
                }

                if(flagAsistencia && flagEstado){
                    if(asistencia.getText() != null){
                        if(asistencia.getText().toString().equals("S") || asistencia.getText().toString().equals("N")){
                            update(view);
                            Bundle b = new Bundle();
                            b.putString("origen", "reserva");
                            Intent intent = new Intent(SelectedReserva.this, MainActivity.class);
                            intent.putExtras(b);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SelectedReserva.this, "Escriba S o N para la asistencia.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        update(view);
                        Bundle b = new Bundle();
                        b.putString("origen", "reserva");
                        Intent intent = new Intent(SelectedReserva.this, MainActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }


            }

        });

        Button buttonEliminar= findViewById(R.id.eliminar);
        buttonEliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean flagAsistencia = false;
                boolean flagEstado = false;

                if(reserva.getFlagAsistio() != null){
                    if(!reserva.getFlagAsistio().equals("S") && !reserva.getFlagAsistio().equals("N")){
                        flagAsistencia = true;
                    }else{
                        Toast.makeText(SelectedReserva.this, "Ya se marco asistencia. No es posible cancelar la reserva.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    flagAsistencia = true;
                }


                if(!reserva.getFlagEstado().equals("C")){
                    flagEstado = true;
                }else{
                    Toast.makeText(SelectedReserva.this, "Reserva Cancelada. No es posible volver a cancelarla.", Toast.LENGTH_LONG).show();
                }

                if(flagAsistencia && flagEstado){
                    delete(view);
                    Bundle b = new Bundle();
                    b.putString("origen", "reserva");
                    Intent intent = new Intent(SelectedReserva.this, MainActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }


            }

        });


    }

    public void update(View view) {
        Reserva reserva = new Reserva();

        idView = findViewById(R.id.reservaId);
        asistencia = findViewById(R.id.asistencia);
        obs = findViewById(R.id.obs);

        reserva.setIdReserva(Integer.valueOf(idView.getText().toString()));
        reserva.setObservacion(obs.getText().toString());
        reserva.setFlagAsistio(asistencia.getText().toString());

        Call<Reserva> callReserva = null;


        callReserva = Servicios.getReservaService().actualizarReserva(reserva);


        callReserva.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if(response.code()==500){
                    Toast.makeText(SelectedReserva.this,"No se puede  Modificar ERROR= " + response.code(),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SelectedReserva.this, "Se actualizo correctamente " + response.code(), Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                //Log.w("warning", Objects.requireNonNull(t.getCause()).toString());
                Toast.makeText(SelectedReserva.this, "Se actualizo correctamente ", Toast.LENGTH_LONG).show();
                //finish();
            }
        });
    }

    public void delete(View view) {

        Integer id = reserva.getIdReserva();

        Call<Reserva> callPersona = null;

        callPersona= Servicios.getReservaService().borrarReserva(id);


        callPersona.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                Toast.makeText(SelectedReserva.this,"Reserva cancelada exitosamente",Toast.LENGTH_LONG).show();
                //finish();
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                //Log.w("warning", t.getLocalizedMessage());
                Toast.makeText(SelectedReserva.this,"Reserva cancelada exitosamente",Toast.LENGTH_LONG).show();
                //finish();
            }
        });
    }

}
