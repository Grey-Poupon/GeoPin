package team3.teamproject;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

/**
 * Created by Rheyn Scholtz on 17/04/2018.
 */

public class JsonSensorData {
    int sensorID;
    Date date;
    double value;
    LatLng latLng;

    String databaseUrlPath = "https://duffin.co/uo/retreiveSensors.php";

    public JsonSensorData(int sensorID, Date date, double value){
        this.sensorID = sensorID;
        this.date = date;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public int getSensorId() {
        return sensorID;
    }

    public double getValue() {
        return value;
    }

    public void applyLatLong (LatLng location) {
        latLng = location;
    }

    public LatLng getLatLng () {return latLng;}
}
