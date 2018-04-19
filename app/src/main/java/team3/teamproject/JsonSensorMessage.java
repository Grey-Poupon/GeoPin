package team3.teamproject;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Steve on 02/03/2018.
 */

public class JsonSensorMessage {
    String sensorName;
    LatLng latLng;
    double baseHeight;
    Date date;

    public JsonSensorMessage(String sensorName, double lat, double lon, double baseHeight, Date date){
        this.sensorName = sensorName;
        this.latLng = new LatLng(lat,lon);
        this.baseHeight = baseHeight;
        this.date = date;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public double getBaseHeight() {
        return baseHeight;
    }

    public Date getDate() {
        return date;
    }

    public String getSensorName() {

        return sensorName;
    }
}
