
package br.com.qrcred;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    // teste xxxxxxxxx
    // william
    public GoogleMap mMap;
    public String nome_c;
    public String categoria_c;
    public String latitude_c;
    public String longitude_c;
    public double latitude_d;
    public double longitude_d;
    public Button mTypeBtn, mTypeBtn2, mBtnir;

    private FusedLocationProviderClient client;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    MarkerOptions place1, place2;
    Polyline currentPolyline;
    private Location location;
    private LocationManager locationManager;
    private Address endereco;
    double latitude;
    double longitude;
    private final List<Marker> originMarkers = new ArrayList<>( );
    private final List<Marker> destinationMarkers = new ArrayList<>( );
    private final List<Polyline> polylinePaths = new ArrayList<>( );
    LatLng origem;
    LatLng destino;
    String origem_x;
    String destino_x;
    TextView distancia;
    TextView tempo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //  key=AIzaSyApW3SFbrgRnC9tG0VJiToLr9v--SNicls
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapLocal);
        mapFragment.getMapAsync(this);


        mTypeBtn = findViewById(R.id.btnSatelite);
        mTypeBtn2 = findViewById(R.id.btnHibrido);
        mBtnir = findViewById(R.id.btnIr);
        distancia = findViewById(R.id.tvDistance);
        tempo = findViewById(R.id.tvDuration);

        Intent intent = getIntent( );

        nome_c = intent.getStringExtra("nome_c");
        categoria_c = intent.getStringExtra("categoria_c");
        latitude_c = intent.getStringExtra("latitude_c");
        longitude_c = intent.getStringExtra("longitude_c");

        client = LocationServices.getFusedLocationProviderClient(this);

        fethLocation( );

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{
                            ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
        if (!latitude_c.equals("") && !longitude_c.equals("")) {
            latitude_d = Double.parseDouble(latitude_c);
            longitude_d = Double.parseDouble(longitude_c);

            mTypeBtn.setOnClickListener(v -> mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE));
            mTypeBtn2.setOnClickListener(v -> mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN));
        } else {
            Toast.makeText(this, "Não tem coordenada.", Toast.LENGTH_LONG).show( );
            finish( );
        }
        mBtnir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getDirectionsUrl(origem, destino);
                RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("MapsActivity", "Response: " + response);
                                drawPath(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                queue.add(stringRequest);
            }
        });


    }
    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origem e destino são definidos aqui para simplificar
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //String sensor = "sensor=false";
        String mode = "mode=" + "driving";
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.setIndoorEnabled(true);
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(true)
                .rotateGesturesEnabled(true)
                .tiltGesturesEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {// If request is cancelled, the result arrays are empty.
        }
    }

    private void fethLocation() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Requer permissão de localização")
                        .setMessage("Voce permite acesso de localização futura")
                        .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(MapsActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss( ))
                        .create( )
                        .show( );
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            client.getLastLocation( ).addOnSuccessListener(MapsActivity.this, location -> {
                if (location != null) {
                    origem = new LatLng(location.getLatitude( ), location.getLongitude( ));
                    origem_x = origem.toString();
                    //Toast.makeText(MapsActivity.this, "Latitude:" + location.getLatitude( ) + "Longitude:" + location.getLongitude( ), Toast.LENGTH_LONG).show( );
                    destino = new LatLng(latitude_d, longitude_d);
                    destino_x = destino.toString();

                    place1 = new MarkerOptions( ).position(origem).title("Estou aqui.").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    place2 = new MarkerOptions( ).position(destino).snippet(categoria_c).title(nome_c);

                    mMap.addMarker(place1);
                    mMap.addMarker(place2);


                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destino, 15.2f));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destino, 15.2f));
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);

                    UiSettings mapUiSettings = mMap.getUiSettings();

                    mapUiSettings.setZoomControlsEnabled(true);
                    mapUiSettings.setMapToolbarEnabled(true);
                    mapUiSettings.setTiltGesturesEnabled(true);
                    mapUiSettings.setCompassEnabled(true);
                    mapUiSettings.setRotateGesturesEnabled(true);



                    float[] result = new float[1];
                    NumberFormat format = NumberFormat.getInstance();
                    format.setMaximumFractionDigits(3);
                    Location.distanceBetween(location.getLatitude( ),location.getLongitude( ),latitude_d,longitude_d,result);
                    distancia.setText(format.format(result[0]/1000)+" Km");




                }
            });

        }

    }

    private void drawPath(String result) {
        try {
            // Converte a resposta JSON em um objeto
            final JSONObject json = new JSONObject(result);
            JSONArray routes = json.getJSONArray("routes");
            JSONObject route = routes.getJSONObject(0);
            JSONObject poly = route.getJSONObject("overview_polyline");
            String polyline = poly.getString("points");
            List<LatLng> list = decodePoly(polyline);

            JSONArray legs = route.getJSONArray("legs");
            JSONObject leg = legs.getJSONObject(0);
            JSONObject duration = leg.getJSONObject("duration");
            String durationText = duration.getString("text");
            long durationValue = duration.getLong("value");

            // Exemplo de atualização da UI
            runOnUiThread(() -> tempo.setText(durationText));

            // Log para depuração
            //Log.d("Duration", "Duration: " + durationText);

            // Desenha a polilinha no mapa
            mMap.addPolyline(new PolylineOptions().addAll(list).width(12).color(Color.BLUE).geodesic(true));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para decodificar polilinhas de uma String para uma lista de LatLng.
     */
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}

