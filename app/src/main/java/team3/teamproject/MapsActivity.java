package team3.teamproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.facebook.FacebookSdk;
import com.facebook.HttpMethod;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

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

    private Spinner heatmapTypeSpinner;

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

        // Pollution selection
        heatmapTypeSpinner = (Spinner)findViewById(R.id.heatmapType);

        ArrayAdapter<CharSequence> heatmapTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.polutionTypes, android.R.layout.simple_spinner_item);
        heatmapTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        heatmapTypeSpinner.setAdapter(heatmapTypeAdapter);
        heatmapTypeSpinner.setOnItemSelectedListener(this);
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
        try {
            // make true to test comment creation
           if(false){ PostStreamReader.createComment("Test1","Comment","");}
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    // Rheyn Scholtz

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedPollution = heatmapTypeSpinner.getSelectedItem().toString();
        if (selectedPollution.equals(OverlayState.CO.toString()))  {
            UpdateHeatMap(OverlayState.CO);
        }
        else if (selectedPollution.equals(OverlayState.Humidity.toString())) {
            UpdateHeatMap(OverlayState.Humidity);
        }
        else if (selectedPollution.equals(OverlayState.NO.toString())) {
            UpdateHeatMap(OverlayState.NO);
        }
        else if (selectedPollution.equals(OverlayState.NO2.toString())) {
            UpdateHeatMap(OverlayState.NO2);
        }
        else if (selectedPollution.equals(OverlayState.Sound.toString())) {
            UpdateHeatMap(OverlayState.Sound);
        }
        else {
            UpdateHeatMap(OverlayState.Temperature);
        }
    }

    public void onMapAirClick(View view) {
        if (overlayState == OverlayState.Air) {
            return;
        }

        overlayState = OverlayState.Air;
        //UpdateHeatMap();

        //addHeatMap(getAirPoints());
        /*debug to test getting the right data from the server
            * Stephen N*/
        //for(JsonSensorMessage msg : getAllSensorData()){
        //    Log.d("app","Name:"+msg.getSensorName()+"Lat"+msg.getLatLng().latitude+"Long:"+msg.getLatLng().longitude+"Height:"+msg.getBaseHeight()+"Date:"+msg.getDate());
        //}
    }
    public void onNothingSelected(AdapterView<?> adapterView) {
        return;
    }

    private void UpdateHeatMap (OverlayState pollutionType) {
        // Get all sensor data to place on the heatmap
        List<JsonSensorData> allRelivantSensorData = getSensorsFromType(pollutionType);

        if (allRelivantSensorData == null) {
            Log.e("SENSOR ERROR", "No data is found");
            return;
        }

        Log.e("PROGRESS", "Got all sensor data");

        // Apply the server to get the datas locations
        List<JsonSensorMessage> allSensorData = getAllSensorData();

        Log.e("PROGRESS", "Got all sensor locations");

        // Find the location of the sensor
        for (int i = 1; i <= allRelivantSensorData.size(); i ++) {
            int id = allRelivantSensorData.get(i - 1).getSensorId();
            for (JsonSensorMessage sensorItem : allSensorData) {
                if (sensorItem.getSensorName().equals(Integer.toString(id))) {
                    LatLng location = sensorItem.getLatLng();
                    allRelivantSensorData.get(i - 1).applyLocation(sensorItem.getLatLng());
                    break;
                }
            }
        }

        Log.e("PROGRESS", "All data processed");

        // Find the smallest and largest value
        double min = allRelivantSensorData.get(0).value;
        double max = allRelivantSensorData.get(0).value;

        for (JsonSensorData sensorData : allRelivantSensorData) {
            if (sensorData.value < min) {
                min = sensorData.value;
            }
            if (sensorData.value > max) {
                max = sensorData.value;
            }
        }

        List<WeightedLatLng> mapData = new ArrayList<>();


        for (JsonSensorData sensorData : allRelivantSensorData) {
            LatLng location = sensorData.getLatLng();
            if (location != null) {
                mapData.add(new WeightedLatLng(location, (sensorData.getValue() - min) / (max - min)));
            }
            else {
                Log.e("NO LOCATION FOUND", "No location found for sensor:" + sensorData.getSensorId());
            }
        }

        placeDataOnMap(mapData);
    }

    // Rheyn Scholtz
    private void placeDataOnMap (List<WeightedLatLng> heatmapData) {

        mProvider = new HeatmapTileProvider.Builder().weightedData(heatmapData).radius(radiusBlur).gradient(new Gradient(colours,startPoints)).build();
        mProvider.setRadius(50);

        if (mOverlay != null) {
            mOverlay.remove();
        }
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    public List<JsonSensorData> getSensorsFromType(OverlayState type){
        String urlPath = "https://duffin.co/uo/getData.php?property=";
        if (type == OverlayState.Air) {
            urlPath += "Air%20Quality";
        }
        else if (type == OverlayState.Temperature) {
            urlPath += "Temperature";
        }
        else if (type == OverlayState.Humidity) {
            urlPath += "Humidity";
        }
        else if (type == OverlayState.NO2) {
            urlPath += "NO2";
        }
        else if (type == OverlayState.NO) {
            urlPath += "NO";
        }
        else if (type == OverlayState.CO) {
            urlPath += "CO";
        }
        else {
            urlPath += "Sound";
        }

        //Will be replaced with graph functionality later

        int newestIndex = getNewestIndex();
        if (newestIndex == -1) {
            return null;
        }

        List<JsonSensorData> listOfSensorData = getSensorDataFromDatabase(urlPath, newestIndex);

        if ((listOfSensorData == null) || (listOfSensorData.size() == 0)) {
            Log.e("PROGRESS","listOfSensorData null");
            return null;
        }

        return listOfSensorData;
    }

    private List<JsonSensorData> getSensorDataFromDatabase (String urlPath, int sensorIndex) {
        if (sensorIndex == -1) {
            return null;
        }

        List<JsonSensorData> listOfSensorData = new ArrayList<JsonSensorData>();
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();

            return listOfSensorData;
        }
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return listOfSensorData;
        }
        if(urlConnection!=null) {

            try {
                return JsonStreamReader.readJsonSensorDataStream(urlConnection.getInputStream(), sensorIndex);
            }
            catch (IOException e) {
                e.printStackTrace();
                return listOfSensorData;
            } finally {
                urlConnection.disconnect();
            }
        }
        return listOfSensorData;
    }

    private int getNewestIndex () {
        String urlPath = "https://duffin.co/uo/getIndex.php";

        List<JsonSensorMessage> listOfSensors = new ArrayList<JsonSensorMessage>();
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();

            return -1;
        }
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        if(urlConnection!=null) {

            try {
                return JsonStreamReader.readHighestIndex(urlConnection.getInputStream()) - 10;
            }
            catch (IOException e) {
                e.printStackTrace();
                return -1;
            } finally {
                urlConnection.disconnect();
            }
        }
        return -1;
    }

    private List<JsonSensorMessage> getDataFromDatabase (String urlPath) {
        List<JsonSensorMessage> listOfSensors = new ArrayList<JsonSensorMessage>();
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
        if (urlConnection != null) {
            try {
                return JsonStreamReader.readSensorJsonStream(urlConnection.getInputStream());
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
    public List<JsonSensorMessage> getAllSensorData(){
        List<JsonSensorMessage> empty = new ArrayList<JsonSensorMessage>();
        String message = "";
        URL url = null;
        try {
            url = new URL("https://duffin.co/uo/getSensors.php");
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
                Log.e("PROGRESS", "Calling");
                return JsonStreamReader.readSensorJsonStream(urlConnection.getInputStream());
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
    List<JsonSensorMessage> sensorsToBePlaced;

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
        for (JsonSensorMessage sensor : sensorsToBePlaced) {
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
