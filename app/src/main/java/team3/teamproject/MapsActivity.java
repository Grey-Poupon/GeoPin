package team3.teamproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
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
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart mChart;
    private DialogFragment logoutDialog;


    //Google map feature variables
    private GoogleMap mMap;
    private TileOverlay mOverlay;
    private HeatmapTileProvider mProvider;
    //Holds which pollution is to be displayed
    private OverlayState overlayState = OverlayState.CO;
    //Holds the sensor information
    private HashMap<String,JsonSensorMessage> sensors;
    private static final String TAG = MapsActivity.class.getSimpleName();

    // Holds the index value which the heatmap will be base from (data value)
    private int currentIndex = -1;
    private OverlayState currentOverlayState = OverlayState.Sound;
    private Map<String, Integer> dateToIndex = new HashMap<String, Integer>();
    private Map indexToDate = new HashMap();
    private DateValueFormatter dateValueFormatter = new DateValueFormatter();


    // Define start point of the map (local 0,0)
    private LatLng startlocation = new LatLng(54.973701, -1.624397);
    // Other map properties
    private final int maxZoom = 15;
    private final int minZoom = 13;
    // Heatmap properties
    private final int radiusBlur = 35;
    // Define the bounds of the map
    private final LatLngBounds mapBounds = new LatLngBounds(
            new LatLng(54.85, -1.7), new LatLng(55.07, -1.52));

    // Colours that will affect how the heatmap looks
    private int[] colours;

    //Heatmap colours for weather typed pollution
    private int[] weatherColours = {
            Color.rgb(152, 236, 220),
            Color.rgb(75, 205, 179),
            Color.rgb(30, 148, 126),
            Color.rgb(0, 91, 73)};


    //Heatmap colours for air typed pollution
    private int[] airColours = {
            Color.rgb(234, 98, 96),
            Color.rgb(204, 49, 46),
            Color.rgb(195, 14, 23),
            Color.rgb(174, 0, 7)};


    //Heatmap colours for enviroment typed pollution
    private int[] enviromantColours = {
            Color.rgb(108, 255, 35),
            Color.rgb(116, 226, 72),
            Color.rgb(111, 181, 76),
            Color.rgb(111, 153, 78)};

    private float[] startPoints = {0.1f, 0.4f, 0.7f, 1f};

    /**
     * List to manage the forum markers, add location and title to add a new Marker
     */
    private List<Marker> forumMarkers = new ArrayList<Marker>();


    //UI elements
    private Toolbar appToolbar;
    private Spinner heatmapTypeSpinner;

    public MapsActivity() {
    }


    //Run when the page loads
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

        // get sensors from server
        sensors = createSensorMap();

        logoutDialog = new DialogLogout();
        appToolbar = (Toolbar) findViewById(R.id.toolbar_maps);
        // Pollution selection
        heatmapTypeSpinner = (Spinner) findViewById(R.id.heatmapType);

        ArrayAdapter<CharSequence> heatmapTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.polutionTypes, R.layout.custom_spinner_item);
        heatmapTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        heatmapTypeSpinner.setAdapter(heatmapTypeAdapter);
        heatmapTypeSpinner.setOnItemSelectedListener(this);

        Pin.addPins(getAllPins());
        //Tyler chart stuff
        mChart = (LineChart) findViewById(R.id.lineChart);
        mChart.setOnChartGestureListener(MapsActivity.this);
        mChart.setOnChartValueSelectedListener(MapsActivity.this);

        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setScaleYEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setNoDataText("Select a category using the top left menu!");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(dateValueFormatter);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-15f);
        updateGraph(currentOverlayState);
        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setDrawLabels(false);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    //Load when the map is created with all its properties are defined
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        startLocation(startlocation, 15);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        // mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.setMaxZoomPreference(maxZoom);
        mMap.setMinZoomPreference(minZoom);
        mMap.setLatLngBoundsForCameraTarget(mapBounds);
        setupForumMarkers(mMap);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle() != null && !User.getIsGuest()) {
                    Intent forum = new Intent(MapsActivity.this, PostListActivity.class);
                    forum.putExtra("PinID", (String) marker.getTag());
                    forum.putExtra("title", marker.getTitle());
                    startActivity(forum);
                }
                else{
                    Toast denied = Toast.makeText(getApplicationContext(),"Guest users cannot access the forums",Toast.LENGTH_SHORT);
                    denied.show();
                }
                return true;
            }
        });


        // Uncomment to view all sensors
        //SensorPlacement();
    }


    //Called when either the polluttion type or index value changes
    private void updateGraph(OverlayState pollutionType) {
        String property = pollutionType.toString().toLowerCase();

        List<JsonGraphMessage> x = getGraphValues("https://duffin.co/uo/getAverages.php?property=", property);
        if(x==null || x.size()<1){return;}
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<String>();

        for (JsonGraphMessage graphValue : x) {
            dateToIndex.put(graphValue.getDate(), graphValue.getIndexValue());
            dates.add(graphValue.getDate());
            values.add(new Entry( graphValue.getIndexValue(), (float)graphValue.getValue()));
        }
        dateValueFormatter.setDates(dates);
        setGraphValues(values, property);
    }


    //Place data on the graph component
    private void setGraphValues(ArrayList<Entry> values, String property) {
        LineDataSet set = new LineDataSet(values, "");
        set.setHighlightLineWidth(3);
        if (colours != null) {
            set.setColors(colours[0]);
        }
        set.setFillAlpha(110);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        LineData data = new LineData(dataSets);

        mChart.getDescription().setText("Showing average " + property + " over time");
        mChart.invalidate();
        mChart.clear();

        mChart.setData(data);
    }


    //Returns the graph data obtained by the database
    private List<JsonGraphMessage> getGraphValues(String urlPath, String property) {
        if (property == null || property == "") {
            return null;
        }
        urlPath += property;
        List<JsonGraphMessage> listOfGraphData = new ArrayList<JsonGraphMessage>();
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch(MalformedURLException e) {
            Log.e("ERROR", "error with URL creation");
            return listOfGraphData;
        }
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            Log.e("ERROR", "error with https url connection");
            return listOfGraphData;
        }
        if(urlConnection!=null) {
            try {
                return JsonStreamReader.readJsonGraphStream(urlConnection.getInputStream(), property);
            }
            catch (IOException e) {
                Log.e("ERROR", "error with urlConnection json stream reader");
                return listOfGraphData;
            } finally {
                urlConnection.disconnect();
            }
        }
        return listOfGraphData;
    }


    //Place landmark forum markers on the map
    private void setupForumMarkers(GoogleMap map) {
        for (Pin pin : Pin.allPins) {
            Marker text = map.addMarker(
                    new MarkerOptions()
                            .position(pin.getLongLat())
                            .title(pin.getName())
                            .icon(createText(pin.getName())));
            text.setAnchor(0.5f, 0f);

            Marker m = map.addMarker(
                    new MarkerOptions()
                            .position(pin.getLongLat())
                            .title(pin.getName()));
            m.setTag(pin.getID());
            forumMarkers.add(m);
        }
    }

    /**
     * move the camera when screen launched
     * Created by Petr Makarov modified by Rheyn Scholtz
     */

    // Move camera to starting position on loadup
    private void startLocation(LatLng lat, int zoom) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(lat, zoom);
        mMap.moveCamera(update);
    }

    // Rheyn Scholtz

    //Called when the pollution spinner changes value
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedPollution = heatmapTypeSpinner.getSelectedItem().toString();

        if (selectedPollution.equals(OverlayState.CO.toString())) {
            colours = airColours;
            currentOverlayState = OverlayState.CO;
        }
        else if (selectedPollution.equals(OverlayState.Humidity.toString())) {
            colours = weatherColours;
            currentOverlayState = OverlayState.Humidity;
        }
        else if (selectedPollution.equals(OverlayState.NO.toString())) {
            colours = airColours;
            currentOverlayState = OverlayState.NO;
        }
        else if (selectedPollution.equals(OverlayState.NO2.toString())) {
            colours = airColours;
            currentOverlayState = OverlayState.NO2;
        }
        else if (selectedPollution.equals(OverlayState.Sound.toString())) {
            colours = enviromantColours;
            currentOverlayState = OverlayState.Sound;
        }
        else if (selectedPollution.equals(OverlayState.Temperature.toString())) {
            colours = weatherColours;
            UpdateHeatMap(OverlayState.Temperature);
        }
        else if (selectedPollution.equals(OverlayState.Wind_speed.toString())) {
            colours = weatherColours;
            currentOverlayState = OverlayState.Wind_speed;
        }
        else if (selectedPollution.equals(OverlayState.NOX.toString())) {
            colours = airColours;
            currentOverlayState = OverlayState.NOX;
        }
        else if (selectedPollution.equals(OverlayState.O3.toString())) {
            colours = airColours;
            currentOverlayState = OverlayState.O3;
        }
        else if (selectedPollution.equals(OverlayState.Pressure.toString())) {
            colours = weatherColours;
            currentOverlayState = OverlayState.Pressure;
        }
        else if (selectedPollution.equals(OverlayState.Rain_Accumulation.toString())) {
            colours = weatherColours;
            currentOverlayState = OverlayState.Rain_Accumulation;
        }
        else if (selectedPollution.equals(OverlayState.Rain_Fall.toString())) {
            colours = weatherColours;
            currentOverlayState = OverlayState.Rain_Fall;
        }
        else if (selectedPollution.equals(OverlayState.Sewage_Level.toString())) {
            colours = enviromantColours;
            currentOverlayState = OverlayState.Sewage_Level;
        }
        else if (selectedPollution.equals(OverlayState.Solar_Diffuseradiation.toString())) {
            colours = weatherColours;
            currentOverlayState = OverlayState.Solar_Diffuseradiation;
        }
        else if (selectedPollution.equals(OverlayState.Visiblity.toString())) {
            colours = enviromantColours;
            currentOverlayState = OverlayState.Visiblity;
        }
        else {
            Log.e("UNKNOWN POLLUTION TYPE", "" + selectedPollution.toString());
            return;
        }

        if (currentOverlayState != null) {
            updateGraph(currentOverlayState);
            currentIndex = -1;
            UpdateHeatMap(currentOverlayState);
        }
        else {

        }
    }


    //If the user selects nothing / aborts the selection
    public void onNothingSelected(AdapterView<?> adapterView) {
        return;
    }


    //Updates the heatmap with new data
    private void UpdateHeatMap(OverlayState pollutionType) {
        // Get all sensor data to place on the heatmap
        List<JsonSensorData> allRelivantSensorData = getSensorsFromType(pollutionType);

        if (allRelivantSensorData == null || allRelivantSensorData.size() == 0) {
            return;
        }

        LatLng latLng = null;
        for(int i =0;i<allRelivantSensorData.size();i++){

            String id = Integer.toString(allRelivantSensorData.get(i).getSensorId());
            JsonSensorMessage sensor = sensors.get(id);
            // any sensor data that doesnt match the sensor we have dont use
            if(sensor!=null) {
                latLng = sensor.getLatLng();
                allRelivantSensorData.get(i).applyLocation(latLng);
            }
        }


        // Find the smallest and largest value
        double min = allRelivantSensorData.get(0).value;
        double max = allRelivantSensorData.get(allRelivantSensorData.size()-1).value;

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
        }
        placeDataOnMap(mapData);
    }

    // Rheyn Scholtz

    // Takes in heatmap data and places it on the heatmap component
    public void placeDataOnMap(List<WeightedLatLng> heatmapData) {

        if (heatmapData == null || heatmapData.size() < 1) {
            return;
        }

        mProvider = new HeatmapTileProvider.Builder().weightedData(heatmapData).radius(radiusBlur).gradient(new Gradient(colours, startPoints)).build();
        mProvider.setRadius(50);

        if (mOverlay != null) {
            mOverlay.remove();
        }
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }


    //Get the sensor information from the database
    public List<JsonSensorData> getSensorsFromType(OverlayState type){
        String urlPath = "https://duffin.co/uo/getData.php?property=";
        if (type == OverlayState.Sound) {
            urlPath += "Sound";
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
        else if (type == OverlayState.Wind_speed) {
            urlPath += "Windspeed";
        }
        else if (type == OverlayState.NOX) {
            urlPath += "NOX";
        }
        else if (type == OverlayState.O3) {
            urlPath += "O3";
        }
        else if (type == OverlayState.Pressure) {
            urlPath += "Pressure";
        }
        else if (type == OverlayState.Rain_Accumulation) {
            urlPath += "RainAccumulation";
        }
        else if (type == OverlayState.Rain_Fall) {
            urlPath += "RainFall";
        }
        else if (type == OverlayState.Sewage_Level) {
            urlPath += "SewageLevel";
        }
        else if (type == OverlayState.Solar_Diffuseradiation) {
            urlPath += "SolarDiffuseradiation";
        }
        else if (type == OverlayState.Visiblity) {
            urlPath += "Visiblity";
        }
        else {
            Log.e("Overlay state error", "" + overlayState.toString() + " Not recognised");
        }

        //Will be replaced with graph functionality later
        int newestIndex = getNewestIndex();

        if (currentIndex != -1) {
            newestIndex = currentIndex;
        }

        if (newestIndex == -1) {
            return null;
        }

        List<JsonSensorData> listOfSensorData = getSensorDataFromDatabase(urlPath, newestIndex);

        if (listOfSensorData == null)  {
            return new ArrayList<JsonSensorData>();

        }

        return listOfSensorData;
    }


    //Returns the sensor loction data from the database
    public List<JsonSensorData> getSensorDataFromDatabase(String urlPath, int sensorIndex) {
        List<JsonSensorData> listOfSensorData = new ArrayList<JsonSensorData>();

        if (sensorIndex < 0) {
            return listOfSensorData;
        }

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
        if (urlConnection != null) {

            try {
                return JsonStreamReader.readJsonSensorDataStream(urlConnection.getInputStream(), sensorIndex);
            } catch (IOException e) {
                e.printStackTrace();
                return listOfSensorData;
            } finally {
                urlConnection.disconnect();
            }
        }
        return listOfSensorData;
    }


    //Returns the most recent index value (newest data source)
    private int getNewestIndex() {
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
        if (urlConnection != null) {

            try {
                return JsonStreamReader.readHighestIndex(urlConnection.getInputStream()) - 10;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            } finally {
                urlConnection.disconnect();
            }
        }
        return -1;
    }

    /**
     * Back button listener.
     * Returns to home screen
     *
     * Created by Petr Makarov
     */
    @Override

    //Back button listener
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * gets all sensor data from server
     * Stephen N
     */

    //Returns all data regarding a sensor
    public List<JsonSensorMessage> getAllSensorData() {
        List<JsonSensorMessage> empty = new ArrayList<JsonSensorMessage>();
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
        if (urlConnection != null) {
            try {
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


    //Creates a sensor map
    public HashMap<String,JsonSensorMessage> createSensorMap(){
        HashMap<String,JsonSensorMessage> map = new HashMap<>();
        List<JsonSensorMessage> sensors = getAllSensorData();
        for(JsonSensorMessage sensor: sensors){
            map.put(sensor.getID(),sensor);
        }
        return map;
    }


    //Returns all the landmark pins stored on the database
    public List<Pin> getAllPins(){
        List<Pin> empty = new ArrayList<Pin>();
        String message = "";
        URL url = null;
        try {
            url = new URL("https://duffin.co/uo/getPins.php");
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
        if (urlConnection != null) {
            try {
                return JsonStreamReader.readJsonPinStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return empty;
            } finally {
                urlConnection.disconnect();
            }
        }
        return empty;
    }


    //Creates a text item
    public BitmapDescriptor createText(String text) {

        Paint textPaint = new Paint();
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setTextSize(30);

        float textWidth = textPaint.measureText(text);
        float textHeight = textPaint.getTextSize();

        int width = (int) textWidth;
        int height = (int) textHeight;

        Bitmap image = Bitmap.createBitmap(width, height + 10, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);

        canvas.translate(0, height);
        canvas.drawText(text, 0, 0, textPaint);
        BitmapDescriptor textBitmap = BitmapDescriptorFactory.fromBitmap(image);
        return textBitmap;
    }


    //Chart gesture listener
    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        return;
    }

    //Chart value selected listener
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        final String x = mChart.getXAxis().getValueFormatter().getFormattedValue(e.getX(), mChart.getXAxis());


        Log.e("TTESTING", dateValueFormatter.test());
        Log.e("TTESTING", x);

        currentIndex = Integer.parseInt(dateToIndex.get(x).toString());
        UpdateHeatMap(currentOverlayState);
    }


    //Chart hold listener
    @Override
    public void onChartLongPressed(MotionEvent me) {
        return;
    }

    //Chart zoom listener
    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        return;
    }

    //Chart tap listener
    @Override
    public void onChartSingleTapped(MotionEvent me) {
        return;
    }

    //Chart nothing selected listener
    @Override
    public void onNothingSelected() {
        return;
    }

    //Chart gesture listener
    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        return;
    }


    //Chart gesture listener
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        return;
    }

    //Chart double tap listener
    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        return;
    }

    //Chart listener
    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        return;
    }

    //On pin click listener
    public void onPMenuClick(final View view){
        if(!User.getIsGuest()){
        PopupMenu popup = new PopupMenu(MapsActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contact:
                        Intent contactUsScreen = new Intent(MapsActivity.this, ContactFormActivity.class);
                        startActivity(contactUsScreen);
                        return true;
                    case R.id.acc:
                        Intent accountScreen = new Intent(MapsActivity.this, AccountActivity.class);
                        startActivity(accountScreen);
                        return true;
                    case R.id.logout:
                        logoutDialog.show(getFragmentManager(), "logoutDialog");
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }else{
        PopupMenu popupLo = new PopupMenu(MapsActivity.this, view);
        popupLo.getMenuInflater().inflate(R.menu.popup_menu_lo, popupLo.getMenu());
        popupLo.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.login:
                        Intent loginScreen = new Intent(MapsActivity.this, LoginActivity.class);
                        startActivity(loginScreen);
                        return true;
                    case R.id.contact:
                        Intent contactUsScreen = new Intent(MapsActivity.this, ContactFormActivity.class);
                        startActivity(contactUsScreen);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupLo.show();
    }
    }


    //Refresh button listener
    public void onRefreshClick(View view) {
        currentIndex = -1;
        UpdateHeatMap(overlayState);
    }




}
