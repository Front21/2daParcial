package py.com.frontendtp2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import py.com.frontendtp2.data.model.Persona;
import py.com.frontendtp2.ui.fichaClinica.PopUp;
import py.com.frontendtp2.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Bundle bundle;
    Persona per;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recibir argumentos
        bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        Gson gS = new Gson();

        //String origen = bundle.getString("origen");
        if(bundle.getString("origen") != null){
            if(bundle.getString("origen").equals("reserva")){

                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.nav_home
                        );
            }
            if(bundle.getString("origen").equals("ficha")){

                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.nav_ficha_clinica
                        );
            }
            if(bundle.getString("origen").equals("paciente")){

                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.nav_gallery
                        );
            }
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_ficha_clinica)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void goToSettins(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void logout(MenuItem item) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void changeContentView(int contentView){
        setContentView(contentView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){

            case R.id.nav_gallery:{

                //Bundle bundle = new Bundle();
                //bundle.putString("id", "1");

                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.nav_gallery
                        );
                break;
            }

            case R.id.nav_home:{
                //Bundle bundle = new Bundle();
                //bundle.putString("id", "1");

                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.nav_home
                        );
                break;
            }

            case R.id.nav_ficha_clinica:{
                //Bundle bundle = new Bundle();
                //bundle.putString("id", "1");

                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.nav_ficha_clinica
                        );
                break;
            }
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}
