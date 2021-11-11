package py.com.frontendtp2.ui.login;

import android.app.Activity;

import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import py.com.frontendtp2.MainActivity;
import py.com.frontendtp2.R;
import py.com.frontendtp2.Services.Servicios;
import py.com.frontendtp2.data.model.Ejemplo;
import py.com.frontendtp2.data.model.Lista;
import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.data.model.Reserva;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private static int theme;

    Persona[] array;
    Reserva[] array2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getApplicationContext().getSharedPreferences(
                getString(R.string.settings), Context.MODE_PRIVATE);
        int defaultTheme = getResources().getInteger(R.integer.default_theme);
        theme = settings.getInt(getString(R.string.settings_theme), defaultTheme);
        switch (theme){
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case -1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;

        }

        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);



        ((EditText)findViewById(R.id.username)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            loadingProgressBar.setVisibility(View.VISIBLE);
                            login(usernameEditText.getText().toString());
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                login(usernameEditText.getText().toString());
            }
        });

    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void changeTheme(View view) {
        if(theme == 0){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            theme = 1;
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.settings), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.settings_theme), 1);
            editor.apply();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            theme = 0;
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.settings), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.settings_theme), 0);
            editor.apply();
        }
        Activity activity = this;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    private void login(final String username){
        try {
            Ejemplo ejemplo = new Ejemplo();
            Gson gson = new Gson();
            ejemplo.setSoloUsuariosDelSistema("true");
            String json = gson.toJson(ejemplo);

            Call<Lista<Persona>> callPersona= Servicios.getPersonaService().obtenerPersonas("idPersona",
                    "asc", "S", json);

            callPersona.enqueue(new Callback<Lista<Persona>>() {
                @Override
                public void onResponse(Call<Lista<Persona>> call, Response<Lista<Persona>> response) {

                    if (response.isSuccessful()) {
                        array = response.body().getLista();

                        for (Persona persona : array) {
                            if (persona.getUsuarioLogin() != null && persona.getUsuarioLogin().equals(username)) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(EXTRA_MESSAGE, username);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Bienvenido: " + username + "!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        ProgressBar loadingProgressBar = findViewById(R.id.loading);
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Intento fallido, usuario no encontrado", Toast.LENGTH_SHORT).show();

                    } else {
                        ProgressBar loadingProgressBar = findViewById(R.id.loading);
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<Lista<Persona>> call, Throwable t) {

                    ProgressBar loadingProgressBar = findViewById(R.id.loading);
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                    Log.w("warning", t.getCause().toString());
                }
            });

        } catch (Exception ex){
            ProgressBar loadingProgressBar = findViewById(R.id.loading);
            loadingProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

}
