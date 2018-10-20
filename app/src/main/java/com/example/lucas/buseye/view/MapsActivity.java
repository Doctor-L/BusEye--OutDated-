package com.example.lucas.buseye.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lucas.buseye.R;
import com.example.lucas.buseye.control.LinhaControle;
import com.example.lucas.buseye.control.PontoControle;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.Ponto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText txtBuscar;
    private Button btBuscar;
    private ListView lvRetornoLinha;
    final List<String> linhaString = new ArrayList<>();
    List<Linha> linhaRetorno = new ArrayList<>();
    List<Ponto> listaPonto = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btBuscar = (Button) findViewById(R.id.realizaBusca);
        EditText txtBuscar = (EditText) findViewById(R.id.buscaLinha);
        ListView lvRetornoLinha = (ListView) findViewById(R.id.retornoLinhas);

            adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, linhaString);

            lvRetornoLinha.setAdapter(adapter);

            lvRetornoLinha.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id)
                {
                    final String item = (String) parent.getItemAtPosition(position);
                    view.animate().setDuration(2000).alpha(0)
                            .withEndAction(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        mostrarPontos(parent.getItemIdAtPosition(position));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                                            .clickable(true)
                                            .add(
                                                    new LatLng(-35.016, 143.321),
                                                    new LatLng(-34.747, 145.592),
                                                    new LatLng(-34.364, 147.891),
                                                    new LatLng(-33.501, 150.217),
                                                    new LatLng(-32.306, 149.248),
                                                    new LatLng(-32.491, 147.309)));
                                    linhaString.remove(item);
                                    adapter.notifyDataSetChanged();
                                    view.setAlpha(1);
                                }
                            });
                }
            });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-11, 11);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void Api(View view) throws JSONException
    {
        new LinhaControle.buscarLinha("Pery").execute();
        linhaRetorno = LinhaControle.getLinhaRetorno();
        Log.d("++RETORNO",LinhaControle.getLinhaRetorno().toString());
       if(linhaRetorno.size() > 0) {
           for (Linha l : linhaRetorno) {
               linhaString.add(l.getNumLinha() + " " + l.getNomeTP() + " / " + l.getNomeTS());
           }
           adapter.addAll(linhaString);
           //adapter.notifyDataSetChanged();
       }
    }

    public void mostrarPontos(Long position) throws JSONException
    {
        int pos = position.intValue();
        Log.d("++POSITION++",Long.toString(position));
        do {
            new PontoControle.buscarPontosPorLinha(linhaRetorno.get(pos)).execute();
            listaPonto = PontoControle.getPontos();
            Log.i("++LINHARETORNO",listaPonto.get(pos).toString());
        }while(listaPonto.size() == 0);
            for (Ponto p : listaPonto){
                LatLng pontos = new LatLng(Double.parseDouble(p.getPosY()), Double.parseDouble(p.getPosX()));
                mMap.addMarker(new MarkerOptions().position(pontos).title(p.getNome()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pontos));
                adapter.notifyDataSetChanged();
            }
    }
}