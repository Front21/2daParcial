package py.com.frontendtp2.ui.fichaClinica;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.gson.Gson;

import py.com.frontendtp2.MainActivity;
import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.Ejemplo;
import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.ui.paciente.EmpleadoPopUp;
import py.com.frontendtp2.ui.paciente.PacienteAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopUp extends AppCompatDialogFragment {
    private TextView editSeleccionado;
    public PopUpListener listener;
    private Persona[] empleados;
    private Persona empleadoSelec;
    PacienteAdapter adapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_popup_empleado, null);

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
                        Persona empleado = empleadoSelec;
                        listener.applyTexts(empleado);
                    }
                });

        editSeleccionado = view.findViewById(R.id.editSeleccion);


        ListView listView = view.findViewById(R.id.empleadoPopUp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Persona item = adapter.getItemAtPosition(i);
                String text = item.getNombre()+' '+item.getApellido();
                editSeleccionado.setText(text);
                empleadoSelec = item;
            }
        });

        //LLAMADA
        Ejemplo ejemplo = new Ejemplo();
        Gson gson = new Gson();
        ejemplo.setSoloUsuariosDelSistema("true");
        String json = gson.toJson(ejemplo);
        Call<Lista<Persona>> callPersona= Servicios.getPersonaService().obtenerPersonas("idPersona",
                "asc", "S", json);
        callPersona.enqueue(new Callback<Lista<Persona>>() {
            @Override
            public void onResponse(Call<Lista<Persona>> call, Response<Lista<Persona>> response) {
                for (Persona c: response.body().getLista()) {
                    Log.d("w", "Persona de id "+ c.getIdPersona());
                }
                if(response.isSuccessful()) {
                    empleados = response.body().getLista();

                    if(empleados.length == 0){
                        Toast.makeText(getContext(), R.string.noExistenElementos, Toast.LENGTH_SHORT).show();
                    }else {

                        int i = 0;
                        i++;

                        ListView listView = (ListView) view.findViewById(R.id.empleadoPopUp);
                        adapter = new PacienteAdapter(getContext(), empleados);
                        listView.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Lista<Persona>> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });


        return builder.create();
    }


    public interface PopUpListener {
        void applyTexts(Persona empleado);
    }
}
