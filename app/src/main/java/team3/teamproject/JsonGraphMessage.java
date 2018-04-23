package team3.teamproject;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

/**
 * Created by Tyler Duffin on 18/04/2018.
 */

//Controls a data type for a graph point

public class JsonGraphMessage {
    String date;
    double value;
    int indexValue;

    public JsonGraphMessage(String date, double value, int indexValue){
        this.date = date;
        this.value = value;
        this.indexValue = indexValue;
    }

    public String getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public int getIndexValue () {return indexValue;}
}
