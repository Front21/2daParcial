package py.com.frontendtp2.ui.fichaClinica;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import py.com.frontendtp2.MainActivity;
import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.PersonaService;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.Categoria;
import py.com.frontendtp2.data.model.Ejemplo;
import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.Reserva;
import py.com.frontendtp2.data.model.TipoProducto;
import py.com.frontendtp2.ui.reserva.ReservaNew;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaClinicaNew extends AppCompatActivity implements
        PopUp.PopUpListener, PopUpCliente.PopUpClienteListener{

    EditText motivo;
    EditText diagnostico;
    EditText observacion;
    TextView empleadoSeleccionado;
    TextView clienteSeleccionado;
    Persona empleadoSelec;
    Persona clienteSelec;
    Boolean fechaDesde;
    Spinner spinnerCategoria;
    Spinner spinnerSubCategoria;
    private Categoria[] categorias;
    private TipoProducto[] subCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_new);

        motivo=findViewById(R.id.motivo_consulta);
        diagnostico=findViewById(R.id.diagnostico);
        observacion=findViewById(R.id.observacion);
        empleadoSeleccionado = findViewById(R.id.empleadoSeleccionado);
        clienteSeleccionado = findViewById(R.id.clienteSeleccionado);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerSubCategoria = findViewById(R.id.spinnerSubCategoria);

        //BOTON LLAMADA A POPUP EMPLEADO
        Button buttonBuscarEmpleado = findViewById(R.id.buttonBuscarEmpleado);
        buttonBuscarEmpleado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUp exampleDialog = new PopUp();
                exampleDialog.listener = (PopUp.PopUpListener) FichaClinicaNew.this;
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
            }

        });

        //BOTON LLAMADA A POPUP CLIENTE
        Button buttonBuscarCliente = findViewById(R.id.buttonBuscarCliente);
        buttonBuscarCliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUpCliente exampleDialog = new PopUpCliente();
                exampleDialog.listener = (PopUpCliente.PopUpClienteListener) FichaClinicaNew.this;
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
            }

        });

        //LLAMADA PARA LLENAR SPINER DE CATEGORIAS

        Call<Lista<Categoria>> callCategoria = Servicios.getCategoriaService().obtenerCategorias("idCategoria",
                "asc", "S", "");
        callCategoria.enqueue(new Callback<Lista<Categoria>>() {
            @Override
            public void onResponse(Call<Lista<Categoria>> call, Response<Lista<Categoria>> response) {
                for (Categoria c: response.body().getLista()) {
                    Log.d("w", "Persona de id "+ c.getIdCategoria());
                }
                if(response.isSuccessful()) {
                    categorias = response.body().getLista();

                    ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();
                    if(categorias.length == 0){
                        Categoria categoria = new Categoria();
                        categoria.setIdCategoria(0);
                        categoria.setDescripcion("No Existen Categorias");
                        ArrayAdapter<Categoria> adapterSpinnerCategoria = new ArrayAdapter(FichaClinicaNew.this,
                                android.R.layout.simple_spinner_item, listaCategorias);
                        adapterSpinnerCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategoria.setAdapter(adapterSpinnerCategoria);
                    }else {

                        //CARGAR SPINNER
                        Categoria categoria = new Categoria();
                        categoria.setIdCategoria(0);
                        categoria.setDescripcion("Seleccione");
                        listaCategorias.add(categoria);
                        for (int j = 0; j < categorias.length; j++) {
                            listaCategorias.add(categorias[j]);
                        }

                        ArrayAdapter<Categoria> adapterSpinnerCategoria = new ArrayAdapter(FichaClinicaNew.this,
                                android.R.layout.simple_spinner_item, listaCategorias);
                        adapterSpinnerCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategoria.setAdapter(adapterSpinnerCategoria);
                        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                Categoria categoriaSeleccionada = (Categoria) spinnerCategoria.getSelectedItem();
                                Ejemplo ejemplo = new Ejemplo();
                                Gson gson = new Gson();
                                ejemplo.setIdCategoria(categoriaSeleccionada);
                                String json = gson.toJson(ejemplo);

                                Call<Lista<TipoProducto>> callCategoria = Servicios.getSubCategoriaService().obtenerTipoProductos("idTipoProducto",
                                        "asc", "S", json);
                                callCategoria.enqueue(new Callback<Lista<TipoProducto>>() {
                                    @Override
                                    public void onResponse(Call<Lista<TipoProducto>> call, Response<Lista<TipoProducto>> response) {
                                        if(response.isSuccessful()) {
                                            subCategorias = response.body().getLista();

                                            ArrayList<TipoProducto> listaSubCategorias = new ArrayList<TipoProducto>();
                                            if(subCategorias.length == 0){
                                                TipoProducto categoria = new TipoProducto();
                                                categoria.setIdTipoProducto(0);
                                                categoria.setDescripcion("No Existen Tipos de Producto");
                                                listaSubCategorias.add(categoria);

                                                ArrayAdapter<TipoProducto> adapterSpinnerTipoProducto = new ArrayAdapter(FichaClinicaNew.this,
                                                        android.R.layout.simple_spinner_item, listaSubCategorias);
                                                adapterSpinnerTipoProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinnerSubCategoria.setAdapter(adapterSpinnerTipoProducto);
                                            }else {

                                                //CARGAR SPINNER
                                                TipoProducto subCategoria = new TipoProducto();
                                                subCategoria.setIdTipoProducto(0);
                                                subCategoria.setDescripcion("Seleccione");
                                                listaSubCategorias.add(subCategoria);
                                                for (int j = 0; j < subCategorias.length; j++) {
                                                    listaSubCategorias.add(subCategorias[j]);
                                                }

                                                ArrayAdapter<Categoria> adapterSpinnerTipoProducto = new ArrayAdapter(FichaClinicaNew.this,
                                                        android.R.layout.simple_spinner_item, listaSubCategorias);
                                                adapterSpinnerTipoProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinnerSubCategoria.setAdapter(adapterSpinnerTipoProducto);

                                                int i = 0;
                                                i++;

                                            }
                                        }else {
                                            Toast.makeText(FichaClinicaNew.this, response.message(), Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Lista<TipoProducto>> call, Throwable t) {
                                        Log.w("warning",t.getCause().toString());
                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        int i = 0;
                        i++;

                    }
                }else {
                    Toast.makeText(FichaClinicaNew.this, response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Lista<Categoria>> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });

        //BOTON GUARDAR
        Button buttoGuardar = findViewById(R.id.button_newPaciente);
        buttoGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TipoProducto subCategoriaSelec = (TipoProducto) spinnerSubCategoria.getSelectedItem();
                if(!motivo.getText().toString().equals("") && !diagnostico.getText().toString().equals("")
                        && !observacion.getText().toString().equals("")
                && empleadoSelec != null && clienteSelec != null
                && subCategoriaSelec.getIdTipoProducto() != 0){
                    guardar(view);
                }else{
                    Toast.makeText(FichaClinicaNew.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }


    public void guardar(View view) {
        FichaClinica f = new FichaClinica();
        f.setMotivoConsulta(motivo.getText().toString());
        f.setDiagnostico(diagnostico.getText().toString());
        f.setObservacion(observacion.getText().toString());
        Persona empleadoAux = new Persona();
        empleadoAux.setIdPersona(empleadoSelec.getIdPersona());
        f.setIdEmpleado(empleadoAux);
        Persona clienteAux = new Persona();
        clienteAux.setIdPersona(clienteSelec.getIdPersona());
        f.setIdCliente(clienteAux);
        TipoProducto subCategoriaSelec = (TipoProducto) spinnerSubCategoria.getSelectedItem();
        TipoProducto subCategoriaAux = new TipoProducto();
        subCategoriaAux.setIdTipoProducto(subCategoriaSelec.getIdTipoProducto());
        f.setIdTipoProducto(subCategoriaAux);


        Call<FichaClinica> callPersona = null;
        callPersona= Servicios.getFichaClinicaService().agregarFichaClinica(f);


        callPersona.enqueue(new Callback<FichaClinica>() {
            @Override
            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
                FichaClinica reserva = response.body();
                Toast.makeText(FichaClinicaNew.this,"Ficha agregada exitosamente",Toast.LENGTH_LONG).show();
                Bundle b = new Bundle();
                b.putString("origen", "ficha");
                Intent intent = new Intent(FichaClinicaNew.this, MainActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                //finish();
            }

            @Override
            public void onFailure(Call<FichaClinica> call, Throwable t) {
                Log.w("warning", t.getCause().toString());
                //Toast.makeText(FichaClinicaNew.this,"Ficha agregada exitosamente",Toast.LENGTH_LONG).show();
            }
        });
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
