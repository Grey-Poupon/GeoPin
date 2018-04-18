package team3.teamproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.net.ssl.HttpsURLConnection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TileOverlay mOverlay;
    private HeatmapTileProvider mProvider;
    private OverlayState overlayState;

    private LatLng startlocation = new LatLng(54.973701,-1.624397);
    private final int maxZoom = 15;
    private final int minZoom = 13;
    private final int radiusBlur = 35;
    private final LatLngBounds mapBounds = new LatLngBounds(
            new LatLng(54.85,-1.7), new LatLng(55.07,-1.52));

    private int[] colours = {
            Color.rgb(152,236,220),
            Color.rgb(75,205,179),
            Color.rgb(30,148,126),
            Color.rgb(0,91,73)};

    private float[] startPoints = {0.1f,0.4f,0.7f,1f};

    /**
     * List to manage the forum markers, add location and title to add a new Marker
     * */
    private List<Marker>  forumMarkers = new ArrayList<Marker>();
    private final LatLng[] forumMarkerLocation = {new LatLng(54.973701,-1.624397)};
    private final String[] forumMarkerTitle = {"PageListActivity"};

    public MapsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        startLocation(startlocation, 15);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
       // mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setMaxZoomPreference(maxZoom);
        mMap.setMinZoomPreference(minZoom);
        mMap.setLatLngBoundsForCameraTarget(mapBounds);
        setupForumMarkers(mMap);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent forum = new Intent(MapsActivity.this,PageListActivity.class);
                forum.putExtra("PinID",marker.getTitle());
                startActivity(forum);
                return true;
            }
        });

        // Uncomment to view all sensors
        //SensorPlacement();
    }

    private void setupForumMarkers(GoogleMap map){
        for(int i = 0;i<forumMarkerLocation.length;i++) {
            forumMarkers.add(map.addMarker(new MarkerOptions()
                    .position(forumMarkerLocation[i])
                    .title(forumMarkerTitle[i])));
        }
    }



    /**
     * move the camera when screen launched
     * Created by Petr Makarov modified by Rheyn Scholtz
     */
    private void startLocation(LatLng lat, int zoom) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(lat, zoom);
        mMap.moveCamera(update);
    }

    public void onMapEnvironmentClick(View view) {
        if(overlayState == OverlayState.Environmental){return;}

        overlayState = OverlayState.Environmental;
        UpdateHeatMap();
    }

    public void onMapAirClick(View view) {
        if(overlayState == OverlayState.Air){return;}

        overlayState = OverlayState.Air;
        UpdateHeatMap();

        //addHeatMap(getAirPoints());
        /*debug to test getting the right data from the server
            * Stephen N*/
        //for(JsonMessage msg : getAllSensorData()){
        //    Log.d("app","Name:"+msg.getSensorName()+"Lat"+msg.getLatLng().latitude+"Long:"+msg.getLatLng().longitude+"Height:"+msg.getBaseHeight()+"Date:"+msg.getDate());
        //}
    }

    public void onMapTrafficClick(View view) {
        if(overlayState == OverlayState.Traffic){return;}

        overlayState = OverlayState.Traffic;
        UpdateHeatMap();
    }

    public void onMapWeatherClick(View view) {
        if(overlayState == OverlayState.Weather){return;}

        overlayState = OverlayState.Weather;
        UpdateHeatMap();
    }



    // Rheyn Scholtz
    private void UpdateHeatMap () {
        // Get all sensors to place on the heatmap
        List<JsonMessage> sensors = getSensorsFromType(overlayState);

        List<WeightedLatLng> sensorData = new ArrayList<>();

        for (JsonMessage sensor : sensors) {
            sensorData.add(new WeightedLatLng(sensor.getLatLng(), 10.0));
        }

        mProvider = new HeatmapTileProvider.Builder().weightedData(sensorData).radius(radiusBlur).gradient(new Gradient(colours,startPoints)).build();

        if (mOverlay != null) {
            mOverlay.remove();
        }
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    public List<JsonMessage> getSensorsFromType(OverlayState type){
        String urlPath = "https://duffin.co/uo/retreiveSensors.php?type=";
        if (type == OverlayState.Air) {
            urlPath += "Air%20Quality";
        }
        else if (type == OverlayState.Weather) {
            urlPath += "Weather";
        }
        else if (type == OverlayState.Environmental) {
            urlPath += "Environmental";
        }
        else {
            urlPath += "Traffic";
        }

        List<JsonMessage> listOfSensors = new ArrayList<JsonMessage>();
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();

            return listOfSensors;
        }
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return listOfSensors;
        }
        if(urlConnection!=null) {
            try {
                return JsonStreamReader.readJsonStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return listOfSensors;
            } finally {
                urlConnection.disconnect();
            }
        }
        return listOfSensors;
    }

    /**
     * back button listener, returns to home screen
     *
     * Created by Petr Makarov
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * adds a heatmap overlay, takes Weighted points
     * @Stephen Northrop
     */
    private void addHeatMap(List<WeightedLatLng> points) {
        if (mOverlay != null) {
            mOverlay.remove();
        }
        mProvider = new HeatmapTileProvider.Builder().weightedData(points).radius(radiusBlur).gradient(new Gradient(colours,startPoints)).build();
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    /**
     * Just test points for debugging
     * @Stephen Northrop
     */
    private List<WeightedLatLng> getTestPoints() {
        List<WeightedLatLng> list = new ArrayList<>();
        double lat = startlocation.latitude;
        double lon = startlocation.longitude;

        for (double y = 0; y < 0.005; y+=0.001)
            for (double x = 0; x < 0.005; x+=0.001) {
                list.add(new WeightedLatLng(new LatLng(lat+x, lon+y), 10));
            }
        return  list;
    }
    /**
     * gets all sensor data from server
     * Stephen N
     * */
    public List<JsonMessage> getAllSensorData(){
        List<JsonMessage> empty = new ArrayList<JsonMessage>();
        String message = "";
        URL url = null;
        try {
            url = new URL("https://duffin.co/uo/retreiveSensors.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();

            return empty;
        }
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return empty;
        }
        if(urlConnection!=null) {
            try {
                return JsonStreamReader.readJsonStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return empty;
            } finally {
                urlConnection.disconnect();
            }
        }
        return empty;
    }

    // Rheyn Scholtz, place sensors on the map (viewing temp)
    List<JsonMessage> sensorsToBePlaced;

    private void SensorPlacement() {
        sensorsToBePlaced = getAllSensorData();

        if (sensorsToBePlaced != null) {
            PlaceSensorsOnMap();
        }
        else {
            Log.e("STATE", "else");
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(54.973701,-1.626498))
                    .title("test"));
            Log.e("STATE", "marker created");
        }
    }

    private void PlaceSensorsOnMap () {
        for (JsonMessage sensor : sensorsToBePlaced) {
            mMap.addMarker(new MarkerOptions()
                    .position(sensor.getLatLng())
                    .title(sensor.getSensorName()));
        }
    }

    // log out button click listener
    public void onLogOutClick(View view){
        LoginManager.getInstance().logOut();

        Intent logOutScreen = new Intent(this, LoginActivity.class);
        startActivity(logOutScreen);
    }
}
