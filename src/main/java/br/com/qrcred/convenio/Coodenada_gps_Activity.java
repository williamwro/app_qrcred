package br.com.qrcred.convenio;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Map;

import br.com.qrcred.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Coodenada_gps_Activity extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public Double latitude;
    public Double longitude;
    private Map<String, String> params;
    private FusedLocationProviderClient client;
    RequestQueue requestQueue;
    private String cod_convenio;
    public Button botaoDefinir;
    private TextView Tvlatitude;
    private TextView Tvlongitude;


    public Coodenada_gps_Activity() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_coodenada_gps, container, false);

        botaoDefinir = v.findViewById(R.id.btnDefinirGps);
        Tvlatitude   = v.findViewById(R.id.tvLatitude);
        Tvlongitude  = v.findViewById(R.id.tvLongitude);
        client = LocationServices.getFusedLocationProviderClient(requireContext());
        Intent intent = getActivity().getIntent( );

        cod_convenio = intent.getStringExtra("cod_convenio");
        Tvlatitude.setText(intent.getStringExtra("latitude"));
        Tvlongitude.setText(intent.getStringExtra("longitude"));

        /*Toolbar toolbar = findViewById(R.id.toolbar_coord_gps);
        setSupportActionBar(toolbar);
        if (getSupportActionBar( ) != null) {
            getSupportActionBar( ).setDisplayHomeAsUpEnabled(true);
            ActionBar ab = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            ab.setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        getSupportActionBar( ).setTitle("Definir coordenada gps");*/
        botaoDefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fethLocation( );
            }
        });
        if (ActivityCompat.checkSelfPermission( requireContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        return v;
    }
 /*       @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/
    private void fethLocation() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Requer permissão de localização")
                        .setMessage("Voce permite acesso de localização futura")
                        .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss( ))
                        .create( )
                        .show( );
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            client.getLastLocation( ).addOnSuccessListener(getActivity(), location -> {
                if (location != null) {
                    //origem = new LatLng(location.getLatitude( ), location.getLongitude( ));
                    latitude = location.getLatitude( );
                    longitude = location.getLongitude( );
                    atualiza_gps();
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                }
            });
        }
    }
    public void atualiza_gps() {
        String urlm = getResources( ).getString(R.string.HOST) + "atualiza_gps_convenio.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlm,
                response -> {
                    Tvlatitude.setText(latitude.toString());
                    Tvlongitude.setText(longitude.toString());
                    Toast.makeText(getContext(), "GPS definido com sucesso !!! ",Toast.LENGTH_LONG).show();
                }, error -> {
                    Toast.makeText(getContext(), "Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.GONE);
                }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("codigo", cod_convenio);
                params.put("latitude",  latitude.toString());
                params.put("longitude", longitude.toString());
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
