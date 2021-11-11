package py.com.frontendtp2.ui.fichaClinica;

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
import py.com.frontendtp2.MainActivity;
import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.Reserva;
import py.com.frontendtp2.ui.paciente.Paciente_ind;
import py.com.frontendtp2.ui.reserva.SelectedReserva;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedFichaClinica extends AppCompatActivity {

    FichaClinica ficha;
    TextView textView ;
    TextView empleadoView;
    TextView nombreView;
    TextView diagnostico;
    EditText obs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_ficha_clinica);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            Gson gS = new Gson();
            ficha = gS.fromJson(bundle.getString("ficha"), FichaClinica.class);

            textView = findViewById(R.id.fichaId);
            empleadoView = findViewById(R.id.empleadoApellido);
            nombreView = findViewById(R.id.clienteNombreCompleto);
            diagnostico = findViewById(R.id.diagnostivoView);
            obs = findViewById(R.id.obs);
            TextView mtvoConsultaView = findViewById(R.id.motivo_consultaView);
            mtvoConsultaView.setText(ficha.getMotivoConsulta());
            textView.setText(ficha.getIdFichaClinica().toString());
            empleadoView.setText(ficha.getIdEmpleado().getApellido().toUpperCase());
            nombreView.setText(ficha.getIdCliente().getNombre());
            diagnostico.setText(ficha.getDiagnostico());
            obs.setText(ficha.getObservacion());
        }

        Button button3= findViewById(R.id.button11);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Eliminar(view);
                Bundle b = new Bundle();
                b.putString("origen", "ficha");
                Intent intent = new Intent(SelectedFichaClinica.this, MainActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }

        });


        Button button2= findViewById(R.id.button9);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!obs.getText().toString().equals("")){
                    update(view);
                    Bundle b = new Bundle();
                    b.putString("origen", "ficha");
                    Intent intent = new Intent(SelectedFichaClinica.this, MainActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }else{
                    Toast.makeText(SelectedFichaClinica.this, "Ingrese una observacion", Toast.LENGTH_LONG).show();
                }

            }

        });

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }
    public void update(View view) {
        FichaClinica ficha = new FichaClinica();

        textView = findViewById(R.id.fichaId);
        obs = findViewById(R.id.obs);

        ficha.setIdFichaClinica(Integer.valueOf(textView.getText().toString()));
        ficha.setObservacion(obs.getText().toString());

        Call<FichaClinica> callReserva = null;
        callReserva = Servicios.getFichaClinicaService().actualizarFichaClinica(ficha);
        callReserva.enqueue(new Callback<FichaClinica>() {
            @Override
            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
                if(response.code()==500){
                    Toast.makeText(SelectedFichaClinica.this,"No se puede  Modificar ERROR= " + response.code(),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SelectedFichaClinica.this, "Se actualizo correctamente ", Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<FichaClinica> call, Throwable t) {
                //Log.w("warning", t.getCause().toString());
                Toast.makeText(SelectedFichaClinica.this, "Se actualizo correctamente ", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void Eliminar(View view) {

        textView= findViewById(R.id.fichaId);
        Integer id = Integer.valueOf(textView.getText().toString());
        Call<FichaClinica> callPersona = null;
        callPersona= Servicios.getFichaClinicaService().borrarFichaClinica(id);
        callPersona.enqueue(new Callback<FichaClinica>() {
            @Override
            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
                Toast.makeText(SelectedFichaClinica.this,"Ficha clinica eliminada exitosamente",Toast.LENGTH_LONG).show();
                //finish();
            }

            @Override
            public void onFailure(Call<FichaClinica> call, Throwable t) {
                //Log.w("warning", t.getLocalizedMessage());
                Toast.makeText(SelectedFichaClinica.this,"Ficha clinica eliminada exitosamente",Toast.LENGTH_LONG).show();
            }
        });
    }
}
