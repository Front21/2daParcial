package py.com.frontendtp2.ui.fichaClinica;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.Categoria;
import py.com.frontendtp2.data.model.Ejemplo;
import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.TipoProducto;
import py.com.frontendtp2.ui.paciente.EmpleadoPopUp;
import py.com.frontendtp2.ui.paciente.PacienteAdapter;
import py.com.frontendtp2.ui.paciente.Paciente_New;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaClinicaFragment extends Fragment implements DatePickerDialog.OnDateSetListener,
        PopUp.PopUpListener, PopUpCliente.PopUpClienteListener{
    private py.com.frontendtp2.ui.fichaClinica.FichaClinicaViewModel fichaClinicaViewModel;
    TextView empleadoSeleccionado;
    TextView clienteSeleccionado;
    TextView fechaDesdeSeleccionada;
    TextView fechaHastaSeleccionada;
    Persona empleadoSelec;
    Persona clienteSelec;
    Boolean fechaDesde;
    Spinner spinnerCategoria;
    Spinner spinnerSubCategoria;

    private FichaClinica[] fichas;
    private Categoria[] categorias;
    private TipoProducto[] subCategorias;
    FichaClinicaAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fichaClinicaViewModel =
                ViewModelProviders.of(this).get(py.com.frontendtp2.ui.fichaClinica.FichaClinicaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ficha_clinica, container, false);


        //PRUEBA
        empleadoSeleccionado = root.findViewById(R.id.empleadoSeleccionado);
        clienteSeleccionado = root.findViewById(R.id.clienteSeleccionado);
        fechaDesdeSeleccionada = root.findViewById(R.id.fechaDesdeSeleccionada);
        fechaHastaSeleccionada = root.findViewById(R.id.fechaHastaSeleccionada);
        spinnerCategoria = root.findViewById(R.id.spinnerCategoria);
        spinnerSubCategoria = root.findViewById(R.id.spinnerSubCategoria);


        //BOTON LLAMADA A POPUP EMPLEADO
        Button buttonBuscarEmpleado= root.findViewById(R.id.buttonBuscarEmpleado);
        buttonBuscarEmpleado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUp exampleDialog = new PopUp();
                exampleDialog.listener = (PopUp.PopUpListener) FichaClinicaFragment.this;
                exampleDialog.show(getFragmentManager(), "example dialog");
            }

        });

        //BOTON LLAMADA A POPUP CLIENTE
        Button buttonBuscarCliente= root.findViewById(R.id.buttonBuscarCliente);
        buttonBuscarCliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopUpCliente exampleDialog = new PopUpCliente();
                exampleDialog.listener = (PopUpCliente.PopUpClienteListener) FichaClinicaFragment.this;
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
                datePicker.setTargetFragment(FichaClinicaFragment.this, 0);
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
                datePicker.setTargetFragment(FichaClinicaFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });


        ListView listView = root.findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FichaClinica item = adapter.getItemAtPosition(i);

                Intent intent = new Intent(getContext(), SelectedFichaClinica.class);
                Gson gS = new Gson();
                String ficha = gS.toJson(item);
                Bundle b = new Bundle();
                b.putString("ficha", ficha);

                intent.putExtras(b);

                startActivity(intent);
            }


        });

        Call<Lista<FichaClinica>> callPersona = Servicios.getFichaClinicaService().obtenerFichaClinica(
                "idFichaClinica",
                "asc", "S", "");

        callPersona.enqueue(new Callback<Lista<FichaClinica>>() {
            @Override
            public void onResponse(Call<Lista<FichaClinica>> call, Response<Lista<FichaClinica>> response) {
                if (response.isSuccessful()) {
                    fichas = response.body().getLista();

                    if (fichas.length == 0){
                        Toast.makeText(getView().getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                    } else {
                        int i = 0;
                        i++;

                        ListView listView = (ListView) getView().findViewById(R.id.list_view);
                        adapter = new FichaClinicaAdapter(getContext(), fichas);
                        listView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<Lista<FichaClinica>> call, Throwable t) {
                Log.w("warning", t.getCause().toString());
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
                        ArrayAdapter<Categoria> adapterSpinnerCategoria = new ArrayAdapter(getView().getContext(),
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

                        ArrayAdapter<Categoria> adapterSpinnerCategoria = new ArrayAdapter(getView().getContext(),
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

                                                ArrayAdapter<TipoProducto> adapterSpinnerTipoProducto = new ArrayAdapter(getView().getContext(),
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

                                                ArrayAdapter<Categoria> adapterSpinnerTipoProducto = new ArrayAdapter(getView().getContext(),
                                                        android.R.layout.simple_spinner_item, listaSubCategorias);
                                                adapterSpinnerTipoProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinnerSubCategoria.setAdapter(adapterSpinnerTipoProducto);

                                                int i = 0;
                                                i++;

                                            }
                                        }else {
                                            Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Lista<Categoria>> call, Throwable t) {
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

                TipoProducto subCategoriaSelec = (TipoProducto) spinnerSubCategoria.getSelectedItem();
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
                if(subCategoriaSelec.getIdTipoProducto() != 0){
                    TipoProducto tipoProductoAux = new TipoProducto();
                    tipoProductoAux.setIdTipoProducto(subCategoriaSelec.getIdTipoProducto());
                    ejemplo.setIdTipoProducto(tipoProductoAux);
                }
                if(!fechaDesdeSelec.equals("No Seleccionado")){
                    ejemplo.setFechaDesdeCadena(fechaDesdeSelec);
                }
                if(!fechaHastaSelec.equals("No Seleccionado")){
                    ejemplo.setFechaHastaCadena(fechaHastaSelec);
                }

                if (empleadoSelec == null && clienteSelec == null && subCategoriaSelec.getIdTipoProducto() == 0
                && fechaDesdeSelec.equals("No Seleccionado") && fechaHastaSelec.equals("No Seleccionado")){
                    Toast.makeText(getView().getContext(), "Seleccione opciones de busqueda", Toast.LENGTH_SHORT).show();
                }else {
                    json = gson.toJson(ejemplo);
                }


                Call<Lista<FichaClinica>> callFicha = Servicios.getFichaClinicaService().obtenerFichaClinica(
                        "idFichaClinica",
                        "asc", "S", json);
                callFicha.enqueue(new Callback<Lista<FichaClinica>>() {
                    @Override
                    public void onResponse(Call<Lista<FichaClinica>> call, Response<Lista<FichaClinica>> response) {
                        if(response.isSuccessful()) {
                            fichas = response.body().getLista();

                            if(fichas.length == 0){
                                Toast.makeText(getView().getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                                ListView listView = (ListView) getView().findViewById(R.id.list_view);
                                adapter = new FichaClinicaAdapter(getContext(), fichas);
                                listView.setAdapter(adapter);
                            }else {

                                int i = 0;
                                i++;

                                ListView listView = (ListView) getView().findViewById(R.id.list_view);
                                adapter = new FichaClinicaAdapter(getContext(), fichas);
                                listView.setAdapter(adapter);
                            }
                        }else {
                            Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Lista<FichaClinica>> call, Throwable t) {
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

                Call<Lista<FichaClinica>> callFicha= Servicios.getFichaClinicaService().obtenerFichaClinica(
                        "idFichaClinica",
                        "asc", "S", "");
                callFicha.enqueue(new Callback<Lista<FichaClinica>>() {
                    @Override
                    public void onResponse(Call<Lista<FichaClinica>> call, Response<Lista<FichaClinica>> response) {
                        for (FichaClinica c: response.body().getLista()) {
                            Log.d("w", "Ficha de id "+ c.getIdFichaClinica());
                        }
                        if(response.isSuccessful()) {
                            fichas = response.body().getLista();

                            if(fichas.length == 0){
                                Toast.makeText(getView().getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                                ListView listView = (ListView) getView().findViewById(R.id.list_view);
                                adapter = new FichaClinicaAdapter(getContext(), fichas);
                                listView.setAdapter(adapter);
                            }else {

                                empleadoSelec = null;
                                clienteSelec = null;
                                String text = "No Seleccionado";
                                empleadoSeleccionado.setText(text);
                                clienteSeleccionado.setText(text);
                                fechaDesdeSeleccionada.setText(text);
                                fechaHastaSeleccionada.setText(text);

                                //CARGAR SPINNER
                                ArrayList<TipoProducto> listaSubCategorias = new ArrayList<>();
                                TipoProducto subCategoria = new TipoProducto();
                                subCategoria.setIdTipoProducto(0);
                                subCategoria.setDescripcion("Seleccione");
                                listaSubCategorias.add(subCategoria);


                                ArrayAdapter<TipoProducto> adapterSpinnerTipoProducto = new ArrayAdapter(getView().getContext(),
                                        android.R.layout.simple_spinner_item, listaSubCategorias);
                                adapterSpinnerTipoProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerSubCategoria.setAdapter(adapterSpinnerTipoProducto);

                                //CARGAR SPINNER
                                ArrayList<Categoria> listaCategorias = new ArrayList<>();
                                Categoria categoria = new Categoria();
                                categoria.setIdCategoria(0);
                                categoria.setDescripcion("Seleccione");
                                listaCategorias.add(categoria);
                                for (int j = 0; j < categorias.length; j++) {
                                    listaCategorias.add(categorias[j]);
                                }


                                ArrayAdapter<Categoria> adapterSpinnerCategoria = new ArrayAdapter(getView().getContext(),
                                        android.R.layout.simple_spinner_item, listaCategorias);
                                adapterSpinnerCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCategoria.setAdapter(adapterSpinnerCategoria);

                                int i = 0;
                                i++;

                                ListView listView = (ListView) getView().findViewById(R.id.list_view);
                                adapter = new FichaClinicaAdapter(getContext(), fichas);
                                listView.setAdapter(adapter);

                            }
                        }else {
                            Toast.makeText(getView().getContext(), response.message(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Lista<FichaClinica>> call, Throwable t) {
                        Log.w("warning",t.getCause().toString());
                    }
                });
            }

        });

        FloatingActionButton fabNuevoUsuario = root.findViewById(R.id.fabNuevaFicha);
        fabNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FichaClinicaNew.class);
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