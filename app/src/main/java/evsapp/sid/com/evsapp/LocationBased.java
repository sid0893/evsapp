
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
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.nispok.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by manan on 17-04-2015.
 */

public class LocationBased extends ActionBarActivity implements LocationListener {

    public static String TAG = "LOCATIONBASED";
    Toolbar mToolbar;
    private GoogleMap map; // Might be null if Google Play services APK is not available.
    Jsontodata mJsontodata;
    Location mLocation=null;

    public class Compare implements Comparator<Location> {

        @Override
        public int compare(Location lhs, Location rhs) {
            float a = mLocation.distanceTo(lhs);
            float b = mLocation.distanceTo(rhs);
            return (int)(a-b);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_map);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(LocationBased.this);
            }
        });
        mJsontodata = new Jsontodata(getApplicationContext());
        mJsontodata.startDataDownload();

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView))
                .getMap();



    }
    @Override
    public void onLocationChanged(final Location location) {
        map.clear();
        mLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Your Current Position")
                .draggable(false)).showInfoWindow();

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 11));


        Collections.sort(mJsontodata.mLocations,new Compare());
        ArrayList<Marker> markers = new ArrayList<>();
        for(int i=0;i<3&&i<mJsontodata.mLocations.size();i++){
            latLng = new LatLng(mJsontodata.mLocations.get(i).getLatitude(), mJsontodata.mLocations.get(i).getLongitude());
            //Marker  marker =  new Marker();
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Nearest Location #" + (i + 1) + ": " + mJsontodata.latLngMap.get(mJsontodata.mLocations.get(i)) + ": Distance: "
                            + location.distanceTo(mJsontodata.mLocations.get(i)))
                    .draggable(false));
            //markers.add(marker);
        }
        Snackbar.with(getApplicationContext())
                .duration(Snackbar.SnackbarDuration.LENGTH_LONG)// context
                .text("Select one from the nearest 3 locations") // text to display
                .show(this); // activity where it is displayed


    }
//    private  HashMap sortByValues(HashMap map) {
//        List list = new LinkedList(map.entrySet());
//        // Defined Custom Comparator here
//        Collections.sort(list, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                return ((Comparable) ((Location) (((Map.Entry) (o1)).getValue())).distanceTo(mLocation))
//                        .compareTo(((Location) (((Map.Entry) (o2)).getValue())).distanceTo(mLocation));
//            }
//        });
//        HashMap sortedHashMap = new LinkedHashMap();
//        for (Iterator it = list.iterator(); it.hasNext(); ) {
//            Map.Entry entry = (Map.Entry) it.next();
//            sortedHashMap.put(entry.getKey(), entry.getValue());
//        }
//        return sortedHashMap;
//
//    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    protected void onStart(){
        super.onStart();
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String T = marker.getTitle();
                String[] t = T.split(": ");
                //String[] t1 = t[1].split("Distance:");
                Log.d("centre",t[1]);
                Intent intent = new Intent(getApplicationContext(),AQIDisplay.class);
                intent.putExtra(MainActivity.STATE_AND_CITY,t[1]);
                startActivity(intent);


            }
        });
    }


    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


