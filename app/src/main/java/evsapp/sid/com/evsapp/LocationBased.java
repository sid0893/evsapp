package evsapp.sid.com.evsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manan on 17-04-2015.
 */

public class LocationBased extends ActionBarActivity implements LocationListener {

    private String TAG = "LOCATIONBASED";

    private GoogleMap map; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_map);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView))
                .getMap();

    }
    @Override
    public void onLocationChanged(Location location) {
        map.clear();
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("current position")
                        .draggable(true));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 16));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                              @Override
                                              public void onInfoWindowClick(Marker marker) {

                                                  if (marker.getTitle().equals("current position")) {
                                                      Toast.makeText(LocationBased.this, marker.getTitle(), Toast.LENGTH_LONG).show();
                                                      Intent student_ctr = new Intent(LocationBased.this, AQI.class);
                                                      startActivity(student_ctr);

                                                  }

                                              }
                                          }

        );
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


