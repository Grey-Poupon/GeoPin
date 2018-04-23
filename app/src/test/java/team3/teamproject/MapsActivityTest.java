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
    public void getSensorFromType_validTypeTemperature_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState temperature = OverlayState.Temperature;
        assertNotEquals(maps.getSensorsFromType(temperature),0);
    }

    @Test
    public void getSensorFromType_validTypeSound_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState sound = OverlayState.Sound;
        assertNotEquals(maps.getSensorsFromType(sound),0);
    }

    @Test
    public void getSensorFromType_validTypeHumidity_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState humidity = OverlayState.Humidity;
        assertNotEquals(maps.getSensorsFromType(humidity),0);
    }
    @Test
    public void getSensorFromType_validTypeNO2_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState NO2 = OverlayState.NO2;
        assertNotEquals(maps.getSensorsFromType(NO2),0);
    }
    @Test
    public void getSensorFromType_validTypeNO_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState NO = OverlayState.NO;
        assertNotEquals(maps.getSensorsFromType(NO),0);
    }

    @Test
    public void getSensorFromType_validTypeCO_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState CO = OverlayState.CO;
        assertNotEquals(maps.getSensorsFromType(CO),0);
    }

    @Test
    public void getSensorFromType_validTypeWindSpeed_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState wind = OverlayState.Wind_speed;
        assertNotEquals(maps.getSensorsFromType(wind),0);
    }
    @Test
    public void getSensorFromType_validTypeNOX_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState NOX = OverlayState.NOX;
        assertNotEquals(maps.getSensorsFromType(NOX),0);
    }
    @Test
    public void getSensorFromType_validTypeO3_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState O3 = OverlayState.O3;
        assertNotEquals(maps.getSensorsFromType(O3),0);
    }
    @Test
    public void getSensorFromType_validTypePressure_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState Pressure = OverlayState.Pressure;
        assertNotEquals(maps.getSensorsFromType(Pressure),0);
    }
    @Test
    public void getSensorFromType_validTypeRainAccumulation_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState Rain_Accumulation = OverlayState.Rain_Accumulation;
        assertNotEquals(maps.getSensorsFromType(Rain_Accumulation),0);
    }
    @Test
    public void getSensorFromType_validTypeRainFall_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState Rain_Fall = OverlayState.Rain_Fall;
        assertNotEquals(maps.getSensorsFromType(Rain_Fall),0);
    }
    @Test
    public void getSensorFromType_validTypeSewageLevel_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState SewageLevel = OverlayState.Sewage_Level;
        assertNotEquals(maps.getSensorsFromType(SewageLevel),0);
    }
    @Test
    public void getSensorFromType_validTypeSolarDiffuseRadiation_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState Solar = OverlayState.Solar_Diffuseradiation;
        assertNotEquals(maps.getSensorsFromType(Solar),0);
    }
    @Test
    public void getSensorFromType_validTypeVisiblity_returnsList(){
        MapsActivity maps = new MapsActivity();
        OverlayState Visiblity = OverlayState.Visiblity;
        assertNotEquals(maps.getSensorsFromType(Visiblity),0);
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