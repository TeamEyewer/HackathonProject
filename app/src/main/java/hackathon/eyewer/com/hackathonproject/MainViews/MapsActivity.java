package hackathon.eyewer.com.hackathonproject.MainViews;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public int ready  =  0 ;
private EditText lat ;
    private EditText lng ;
    private Marker pmarker = null ;
    private Button addmarkerbutton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parentlayout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);






        addmarkerbutton = (Button) findViewById(R.id.addm);

        addmarkerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmarkerfrombutton();
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        ready = 1 ;


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
           if(pmarker == null) {
               pmarker =    mMap.addMarker(new MarkerOptions()
                       .position(latLng)
                       .draggable(false));
               mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
           }

                else {
               pmarker.remove();

               pmarker =    mMap.addMarker(new MarkerOptions()
                       .position(latLng)
                       .draggable(false));

               mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
           }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng dragPosition = marker.getPosition();
                double dragLat = dragPosition.latitude;
                double dragLong = dragPosition.longitude;
            }
        });

    }


    public void addMarker(LatLng latlong , String description ) {

        mMap.addMarker(new MarkerOptions().position(latlong).title(description));



    }

    public void addmarkerfrombutton() {


        lat = (EditText)findViewById(R.id.lat);
        lng = (EditText)findViewById(R.id.lng);

        String l = lat.getText().toString();
        String lo =  lng.getText().toString();
        LatLng latlong = new LatLng(Integer.parseInt(l),Integer.parseInt(lo));

        addMarker(latlong,"Test Marker");




    }
}
