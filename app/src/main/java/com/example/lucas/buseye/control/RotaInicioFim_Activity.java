package com.example.lucas.buseye.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lucas.buseye.R;

public class RotaInicioFim_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout hamb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telarotas_inicioefim);


        ///////////////HAMBURGUER
        Toolbar toolbar = findViewById(R.id.toolbar3);
        hamb = findViewById(R.id.drawer_layout3);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, hamb, toolbar, R.string.nav_open, R.string.nav_close);
        hamb.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view3);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.hamb_home){
            Intent intent  = new Intent (this, TelaInicial_Activity.class);
            startActivity(intent);
        }
        else if(menuItem.getItemId()==R.id.hamb_favori){
            Intent intent  = new Intent (this, RotasActivity.class);
            startActivity(intent);
        }
        else if(menuItem.getItemId()==R.id.hamb_conf){
            Intent intent  = new Intent (this, RotaInicioFim_Activity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (hamb.isDrawerOpen(GravityCompat.START)) {
            hamb.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}