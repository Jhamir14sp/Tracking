package saenz.jhamir.packagelocatorapp.Fragments;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import saenz.jhamir.packagelocatorapp.LoginActivity;
import saenz.jhamir.packagelocatorapp.R;
import saenz.jhamir.packagelocatorapp.mysql.locationW;

import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.android.PolyUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class LocalizarFragment extends SupportMapFragment implements OnMapReadyCallback,Response.Listener<JSONObject>, Response.ErrorListener {
    View vista;
    private GoogleMap mMap;
    private Marker marcador;
    public static String x,y;
    RequestQueue rq;
    JsonRequest jrq;
    JSONObject jso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    public LocalizarFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) {
                //sin señal en gps
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mMap.setMyLocationEnabled(true);
        Location(googleMap);
        float zoom = 16;


        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {


                double lt = Double.valueOf(ClienteFragment.latW).doubleValue();
                double ln = Double.valueOf(ClienteFragment.lngW).doubleValue();
                final LatLng punto1 = new LatLng(ln, lt);
                double latW = Double.valueOf(ClienteFragment.lng).doubleValue();
                double lngW = Double.valueOf(ClienteFragment.lat).doubleValue();
                final LatLng punto2 = new LatLng(latW, lngW);
                String url2 = "https://maps.googleapis.com/maps/api/directions/json?origin=" + lt + "," + ln + "&destination=" + latW + "," + lngW + "&key=AIzaSyBibI3_dZFiuPuHn-MiV8iX-RTFDyETJNA";
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jso = new JSONObject(response);
                            trazarRuta(jso);
                            //Log.i("jsonRuta",""+response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    public void ubicarPaquete(){
        String correo;
        correo= ClienteFragment.worker;
        String url ="https://diars2018upn.000webhostapp.com/login/asklocation.php?email="+ClienteFragment.worker;
        jrq=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Error inesperado",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        locationW location= new locationW();
        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;
        try{
            jsonObject=jsonArray.getJSONObject(0);
            location.setLat(jsonObject.optString("lat"));
            location.setLng(jsonObject.optString("lng"));
            x=location.getLat();
            y=location.getLng();

        } catch (Exception e){
        e.printStackTrace();
        }
    }


    public void Location(GoogleMap googleMap){
        mMap=googleMap;

        double lt=Double.valueOf(ClienteFragment.latW).doubleValue();
        double ln=Double.valueOf(ClienteFragment.lngW).doubleValue();
        final LatLng punto1=new LatLng(ln,lt);
        double latW=Double.valueOf(ClienteFragment.lng).doubleValue();
        double lngW=Double.valueOf(ClienteFragment.lat).doubleValue();
        final LatLng punto2=new LatLng(latW,lngW);
        mMap.addMarker(new MarkerOptions().position(punto1).title("Su direccion"));

        mMap.addMarker(new MarkerOptions().position(punto2).title("Dirección de entrega"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(punto2));

        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);

    }
    private void trazarRuta(JSONObject jso){

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){

                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++){

                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k<jSteps.length();k++){


                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(5));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
