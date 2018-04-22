package team3.teamproject;

import com.google.maps.android.heatmaps.WeightedLatLng;

import org.junit.Test;

import java.util.ArrayList;

import static android.test.MoreAsserts.assertNotEqual;
import static org.junit.Assert.*;

/**
 * Created by Steve on 21/04/2018.
 */
public class MapsActivityTest {

    /*getSensorDataFromDatabase*/
    @Test
    public void getSensorDataFromDatabase_invalidIndex_returnsEmptyList(){
        MapsActivity maps = new MapsActivity();
        String validPath = "https://duffin.co/uo/getData.php?property=sound";
        int invalidIndex  = -1;
        assertEquals(maps.getSensorDataFromDatabase(validPath,invalidIndex).size(),0);
    }

    @Test
    public void getSensorDataFromDatabase_invalidURL_returnsEmptyList(){
        MapsActivity maps = new MapsActivity();
        String validPath = "XXXXXXXXXXXXX";
        int invalidIndex  = -1;
        assertEquals(maps.getSensorDataFromDatabase(validPath,invalidIndex).size(),0);
    }

    @Test
    public void getSensorDataFromDatabase_valid_returnsList(){
        MapsActivity maps = new MapsActivity();
        String validPath = "https://duffin.co/uo/getData.php?property=sound";
        int invalidIndex  = 123;
        assertNotEquals(maps.getSensorDataFromDatabase(validPath,invalidIndex).size(),0);
    }

    /*getSensorFromType*/
    @Test
    public void getSensorFromType_validTypeSound_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState sound = OverlayState.Sound;
        assertNotEquals(maps.getSensorsFromType(sound),0);
    }

    @Test
    public void getSensorFromType_validTypeAir_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState sound = OverlayState.Air;
        assertNotEquals(maps.getSensorsFromType(sound),0);
    }

    @Test
    public void getSensorFromType_validTypeCO_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState sound = OverlayState.CO;
        assertNotEquals(maps.getSensorsFromType(sound),0);
    }

    @Test
    public void getSensorFromType_validTypeHumidity_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState sound = OverlayState.Humidity;
        assertNotEquals(maps.getSensorsFromType(sound),0);
    }

    @Test
    public void getSensorFromType_validTypeNO_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState sound = OverlayState.NO;
        assertNotEquals(maps.getSensorsFromType(sound),0);
    }

    @Test
    public void getSensorFromType_validTypeNO2_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState sound = OverlayState.NO2;
        assertNotEquals(maps.getSensorsFromType(sound),0);
    }

    @Test
    public void getSensorFromType_validTypeTemperature_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState sound = OverlayState.Temperature;
        assertNotEquals(maps.getSensorsFromType(sound),0);
    }

    @Test
    public void getAllSensorData_returnList(){
        MapsActivity maps = new MapsActivity();
        assertNotEquals(maps.getAllSensorData().size(),0);
    }

    @Test
    public void createSensorMap_returnList(){
        MapsActivity maps = new MapsActivity();
        assertNotEquals(maps.createSensorMap().size(),0);
    }

    @Test
    public void getAllPins_returnList(){
        MapsActivity maps = new MapsActivity();
        assertNotEquals(maps.getAllPins().size(),0);
    }


}