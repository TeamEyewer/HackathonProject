package hackathon.eyewer.com.hackathonproject.MainViews;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import hackathon.eyewer.com.hackathonproject.GCM.NotiHandler;
import hackathon.eyewer.com.hackathonproject.R;

public class Dashboard extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public int ready = 0;
    private Marker pmarker = null;

    public static final String SENDER_ID = "954642047450";
    public static MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        NotificationsManager.handleNotifications(this, SENDER_ID, NotiHandler.class);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        ready = 1;


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (pmarker == null) {
                    pmarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .draggable(false));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                } else {
                    pmarker.remove();

                    pmarker = mMap.addMarker(new MarkerOptions()
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

}
