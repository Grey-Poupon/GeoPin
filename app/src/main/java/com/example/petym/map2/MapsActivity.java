package com.example.petym.map2;
/* @autor Peter Makarov
 * @date 22/11/2017
  * A prototype for the Dragon's Den with bad implementation
  * which will not be used in future project.
  * Relative layout must be changed in the future*/

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    /* local variables */
    private boolean mPermissionDenied = false;

    /* boolean var to change between maps(social and urban obs)
     * poor use */
    private boolean isSocial;

    /* constants */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final LatLng RIVER = new LatLng(54.920511, -1.612379);
    private static final LatLng STRAIN = new LatLng(54.972425, -1.623186);
    private static final LatLng TIDE = new LatLng(55.007379, -1.440368);
    private static final LatLng TRAFFIC = new LatLng(54.989564, -1.625078);
    private static final LatLng WEATHER = new LatLng(54.972570, -1.623747);
    private static final LatLng TEST = new LatLng(54.973627, -1.617512);
    private static final LatLng AQ = new LatLng(54.973853, -1.624552);
    private static final LatLng ELEC = new LatLng(54.972153, -1.622894);
    private static final LatLng ENV = new LatLng(54.973681, -1.624917);


    private static final LatLng PER = new LatLng(54.983778, -1.620015);
    private static final LatLng PER1 = new LatLng(54.979905, -1.613527);
    private static final LatLng PER2 = new LatLng(54.974566, -1.610523);
    private static final LatLng PER3 = new LatLng(54.972620, -1.610705);
    private static final LatLng PER4 = new LatLng(54.969576, -1.619189);
    private static final LatLng PER5 = new LatLng(54.971092, -1.624954);
    private static final LatLng PER6 = new LatLng(54.975533, -1.631682);
    private static final LatLng PER7 = new LatLng(54.975769, -1.580583);
    private static final LatLng PER8 = new LatLng(54.972153, -1.622894);
    private static final LatLng PER9 = new LatLng(54.973681, -1.624917);

    /* vars for the good style */
    private Marker mEnv, mElec, mAq, mTest, mWeather, mTraffic, mTide, mStrain, mRiver, mAdded;

    private Marker mPer, mPer1, mPer2, mPer3, mPer4, mPer5, mPer6, mPer7, mPer8, mPer9;


    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        startLocation(54.973701, -1.624397, 12);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnMarkerClickListener(this);
        /* add marker when you click the map*/
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (isSocial) {
                    if (null != mAdded) {
                        mAdded.remove();
                    }
                    mAdded = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latLng.latitude, latLng.longitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_parking))
                            .title("Added marker"));
                }

            }
        });

        enableMyLocation();
        addUrbanObsPins();
    }

    /* move the camera when app launched */
    private void startLocation(double lat, double lng, float zoom) {
        LatLng latl = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latl, zoom);
        mMap.moveCamera(update);
    }

    /* add urban observatory markers  */
    private void addUrbanObsPins() {

        mRiver = mMap.addMarker(new MarkerOptions()
                .position(RIVER)
                .title("River Level")
                .snippet("River Level  0.26 m")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_river)));


        mStrain = mMap.addMarker(new MarkerOptions()
                .position(STRAIN)
                .title("Strain Guage")
                .snippet("Live readings: Strain 2  63.04 strain")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_strain)));


        mTide = mMap.addMarker(new MarkerOptions()
                .position(TIDE)
                .title("Tidal Level")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_tide))
                .snippet("Live readings: Relative Tidal Level 0.29 mAOD"));


        mTraffic = mMap.addMarker(new MarkerOptions()
                .position(TRAFFIC)
                .title("Traffic")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_traffic))
                .snippet("Live readings: Journey Time  82.00 seconds"));


        mWeather = mMap.addMarker(new MarkerOptions()
                .position(WEATHER)
                .title("Weather")
                .snippet("Live readings:  Temperature  11.30 Celsius")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_weather)));


        mTest = mMap.addMarker(new MarkerOptions()
                .position(TEST)
                .title("High Precision Air Monitor")
                .snippet("Live readings:  CO  299.33 ugm -3")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker)));


        mAq = mMap.addMarker(new MarkerOptions()
                .position(AQ)
                .title("Air Quality")
                .snippet("Live readings:  Particle Count  42.49 Kgm -3")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_aq)));


        mElec = mMap.addMarker(new MarkerOptions()
                .position(ELEC)
                .title("Electrical")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_elec))
                .snippet("Live readings:  NOXasNO2 Concentration  31.94 ugm "));


        mEnv = mMap.addMarker(new MarkerOptions()
                .position(ENV)
                .title("Eviromental")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test_marker_env))
                .snippet("Live readings:  Real Power  57.35 +kW"));


        isSocial = true;
    }

    /* add social markers  */
    private void addSocialPins() {

        mPer = mMap.addMarker(new MarkerOptions()
                .position(PER)
                .title("Koen Olde Rikkert")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_male)));


        mPer1 = mMap.addMarker(new MarkerOptions()
                .position(PER1)
                .title("Fergus Tatlow")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_male)));

        mPer2 = mMap.addMarker(new MarkerOptions()
                .position(PER2)
                .title("Ariel Schultz")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_female)));

        mPer3 = mMap.addMarker(new MarkerOptions()
                .position(PER3)
                .title("Robbie Anker")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_male)));


        mPer4 = mMap.addMarker(new MarkerOptions()
                .position(PER4)
                .title("Salma Morris")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_female)));


        mPer5 = mMap.addMarker(new MarkerOptions()
                .position(PER5)
                .title("Jack Golding")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_male)));


        mPer6 = mMap.addMarker(new MarkerOptions()
                .position(PER6)
                .title("Litzy Herman")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_female)));


        mPer7 = mMap.addMarker(new MarkerOptions()
                .position(PER7)
                .title("Krista Roth")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_female)));


        mPer8 = mMap.addMarker(new MarkerOptions()
                .position(PER8)
                .title("Stephen Northrop")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_male)));


        mPer9 = mMap.addMarker(new MarkerOptions()
                .position(PER9)
                .title("Aiyana Rhodes")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_female)));


        isSocial = false;
    }


    private boolean checkReady() {
        return mMap != null;
    }

    /* socialButton listener */
    public void onMapSocial(View view) {
        if (!checkReady()) {
            return;
        }
        if (!isSocial) {
            // Clear the map because we don't want duplicates of the markers.
            mMap.clear();
            addUrbanObsPins();
        } else {
            mMap.clear();
            addSocialPins();
        }
    }

    /* zoomInButton listener */
    public void onMapZoomIn(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    /* zoomOutButton listener */
    public void onMapZoomOut(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted.
            mPermissionDenied = false;
        }
    }

    /* method to get location */
    private void enableMyLocation() {
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            // Access to the location has been granted to the app.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
            // Enable the my location layer if the permission has been granted.

        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    public void onMenuClick(View view){
        Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show();
    }

    /* onMyLocationButton listener(poor implementation) */
    public void onMyLocationButtonClick(View view) {
        Location location = mMap.getMyLocation();

        if (location != null) {

            LatLng target = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition.Builder builder = new CameraPosition.Builder();
            builder.zoom(15);
            builder.target(target);

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
        }
        else{
            Toast.makeText(this, "Turn on your location services", Toast.LENGTH_LONG).show();
        }


    }


}
