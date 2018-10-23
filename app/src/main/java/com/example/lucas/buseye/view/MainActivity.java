package com.example.lucas.buseye.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lucas.buseye.R;
import com.example.lucas.buseye.control.ConectaAPI;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {


    String aux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnMap = (Button) findViewById(R.id.btMap);
        //buscaOlhoVivo = (EditText) findViewById(R.id.buscaOlhoVIvo);
        //mostraBuscaOlhoVivo = (TextView) findViewById(R.id.mostraBuscaOlhoVIvo);
        aux = "";
        //ConectaAPI.autenticarAPI();
    }

    public void abrirMapa(View view) throws JSONException {
        ConectaAPI.autenticarAPI();
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    // ConectaAPI.autenticarAPI();
    // Linha.buscarLinha("PERI ALTO");
    }

    public void abrirRotas(View view){
        Intent intent = new Intent(this, RotasActivity.class);
        startActivity(intent);
    }
}