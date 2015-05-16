package evsapp.sid.com.evsapp;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import evsapp.sid.com.evsapp.Utils.VolleySingleton;

/**
 * Created by manan on 29-04-2015.
 */
public class Jsontodata {

    private final static String TAG = "JSON_TO_DATABASE";
    private static final String url = "https://api.myjson.com/bins/12uj1";
    private Context context;
    HashMap<String, String> queries;
    HashMap<Location, String> latLngMap;
    ArrayList<Location> mLocations;

    public Jsontodata(Context context) {
        this.context = context;


    }

    public void startDataDownload() {
        fetchPlaces(url);
    }


    private void fetchTracks(String url) {
/*
        RequestQueue queue = VolleySingleton.getReqQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = removePaddingFromString(response);
                //  Log.d(TAG, jsonArray.toString());
                String trackName;
                String trackInformation;

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        trackName = jsonArray.getJSONObject(i).getJSONArray("c").getJSONObject(0)
                                .getString("v");
                        trackInformation = jsonArray.getJSONObject(i).getJSONArray("c").getJSONObject(1)
                                .getString("v");
                        String query = "INSERT INTO %s VALUES (%d, '%s', '%s');";
                        query = String.format(query, DatabaseHelper.TABLE_NAME_TRACK, i, StringUtils.replaceUnicode(trackName), StringUtils.replaceUnicode(trackInformation));
                        // Log.d(TAG, query);
                        queries.add(query);
                    } catch (JSONException e) {
                        //  Log.e(TAG, "JSON Error: " + e.getMessage() + "\nResponse" + response);
                    }

                }
                tracks = true;
            }
        }

                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                tracks = true;

            }
        }

        );
        // Add the request to the RequestQueue.
        queue.add(stringRequest);*/
    }

    private void fetchPlaces(String url) {
        RequestQueue queue = VolleySingleton.getReqQueue(context);

        final JsonArrayRequest jsonarrRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                try {
                    queries = new HashMap<String, String>();
                    latLngMap = new HashMap<Location, String>();
                    mLocations = new ArrayList<>();

                    for (int i = 0; i <= response.length() + 1; i++) {

                        JSONObject place = (JSONObject) response.get(i);

                        // String state_name = place.getString("state_name");
                        // String city_name = place.getString("city_name");
                        String centre_name = place.getString("centre_name");
                        String query = place.getString("query");
                        JSONObject latlng = place.getJSONObject("latlng");
                        Double latitude = latlng.getDouble("latitude");
                        Double longitude = latlng.getDouble("longitude");
                        //LatLng latLng1 = new LatLng(latitude,longitude);
                        Location latlng1 = new Location("");
                        latlng1.setLatitude(latitude);
                        latlng1.setLongitude(longitude);
                        Log.d(TAG, " centre: " + centre_name + " query: " + query + " latlng :" + latitude + "," + longitude);
                        queries.put(centre_name, query);
                        latLngMap.put(latlng1, centre_name);
                        mLocations.add(latlng1);
                        // Place temp = new Place(state_name,city_name,centre_name,query,latitude,longitude);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonarrRequest);

    }

    private JSONArray removePaddingFromString(String response) {
        response = response.replaceAll("\"v\":null", "\"v\":\"\"");
        response = response.replaceAll("null", "{\"v\": \"\"}");
        response = response.substring(response.indexOf("(") + 1, response.length() - 2);
        try {
            JSONObject jObj = new JSONObject(response);
            jObj = jObj.getJSONObject("table");
            JSONArray jArray = jObj.getJSONArray("rows");
//            Log.d(TAG, jArray.toString());
            return jArray;
        } catch (JSONException e) {
            // Log.e(TAG, "JSON Error: " + e.getMessage() + "\nResponse" + response);

        }

        return null;

    }
}