package com.example.aplicativomovil;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.aplicativomovil.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1; // Define un código de solicitud
    private FusedLocationProviderClient fusedLocationClient;
    private double textoLatitud, textoLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // Inicializa el cliente

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void obtenerUbicacion() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_LOCATION); // Código de solicitud
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            textoLatitud = location.getLatitude();
                            textoLongitud = location.getLongitude();

                            // Añade el marcador después de obtener la ubicación
                            LatLng currentLocation = new LatLng(textoLatitud, textoLongitud);
                            float zoomLevel = 15.0f;

                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));

                            Toast.makeText(MapsActivity.this, "Latitud: " + textoLatitud, Toast.LENGTH_LONG).show();
                            Toast.makeText(MapsActivity.this, "Longitud: " + textoLongitud, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        obtenerUbicacion(); // Llama a obtenerUbicacion al estar el mapa listo
    }
}
