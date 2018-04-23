package team3.teamproject;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

/**
 * Created by Rheyn Scholtz on 17/04/2018.
 */

//Holds the data regarding a sensor

public class JsonSensorData {
    int sensorID;
    Date date;
    double value;
    LatLng latLng;
    int indexValue;

    //Constructor
    public JsonSensorData(int sensorID, Date date, double value, int indexValue){
        this.sensorID = sensorID;
        this.date = date;
        this.value = value;
        this.indexValue = indexValue;
    }

    //Getters and setters
    public Date getDate() {
        return date;
    }

    public int getSensorId() {
        return sensorID;
    }

    public double getValue() {
        return value;
    }

    public LatLng getLatLng () {return latLng;}

    public int getIndexValue () {return indexValue;}

    public void applyLocation (LatLng location) {
        latLng = location;
    }

    //Overidden toString method
    public String toString () {
        if (latLng == null) {
            return "null";
        }
        else {
            return (latLng.toString());
        }
    }
}
