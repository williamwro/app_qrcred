package br.com.qrcred;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    public String latitude_c;
    public String longitude_c;
    public String message;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        latitude_c = bundle.getString("latitude_c");
        longitude_c = bundle.getString("longitude_c");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);

                        markerOptions.title(latLng.latitude+" KG "+latLng.longitude);
                        googleMap.clear();
                        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
                        googleMap.setIndoorEnabled(true);
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return view;
    }
    protected void displayReceivedData(String message)
    {
        latitude_c = message;

    }
}
