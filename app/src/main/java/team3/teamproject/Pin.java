package team3.teamproject;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 19/04/2018.
 */


//Defins a pin object
public class Pin {

    public static List<Pin> allPins = new ArrayList<Pin>();

    private String ID;
    private LatLng longLat;
    private String name;

    public Pin(String ID, LatLng longLat, String name) {
        this.ID = ID;
        this.longLat = longLat;
        this.name = name;
    }


    //Getters and setters
    public String getID() {
        return ID;
    }

    public LatLng getLongLat() {
        return longLat;
    }

    public String getName() {
        return name;
    }

    public static void addPin(Pin pin){
        allPins.add(pin);
    }

    public static void addPins(List<Pin> pins){
        allPins.addAll(pins);
    }
}
