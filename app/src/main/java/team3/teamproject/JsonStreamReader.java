package team3.teamproject;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Steve on 02/03/2018. Modified by Rheyn Scholtz
 * Functions for reading the JSON Stream from the Server  Stephen N
 */

public class JsonStreamReader {

    public static int readHighestIndex(InputStream in) {
        Scanner scan = new Scanner(in);
        String s = scan.nextLine();
        scan.close();
        return Integer.parseInt(s);
    }

    public static List<JsonSensorData> readJsonSensorDataStream(InputStream in, int index) {
        JsonReader reader =  new JsonReader(new InputStreamReader(in));
        try {
            return readJsonSensorDataArray(reader, index);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return null;
    }

    private static List<JsonSensorData> readJsonSensorDataArray(JsonReader reader, int index) throws IOException {
        List<JsonSensorData> messages = new ArrayList<JsonSensorData>();

        reader.beginArray();
        while (reader.hasNext()){
            JsonSensorData nextSensorData = readJsonSensorDataMessage(reader);
            if (nextSensorData.getIndexValue() == index) {
                messages.add(nextSensorData);
            }
        }
        reader.endArray();
        return messages;
    }

    private static JsonSensorData readJsonSensorDataMessage(JsonReader reader) throws IOException {

        int sensorId = -1;
        double value = 0;
        Date date = new Date();
        int indexValue = -1;

        boolean found = false;

        reader.beginObject();
        while(reader.hasNext()){
            String name = "";
            try {
                name = reader.nextName();
            }
            catch (IllegalStateException e) {
                name = reader.nextString();
            }
            if(name.equals("sensorID")){
                sensorId = reader.nextInt();
            }
            else if(name.equals("value")){
                value = reader.nextDouble();
            }
            else if(name.equals("latest")){
                // try to parse string into Date Time
                try {
                    date = ForumMessage.format.parse(reader.nextString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            else if (name.equals("indexValue")) {
                indexValue = reader.nextInt();
            }
        }
        reader.endObject();
        return new JsonSensorData(sensorId, date, value, indexValue);
    }






    public static List<JsonMessage> readJsonStream(InputStream in) {
        JsonReader reader =  new JsonReader(new InputStreamReader(in));
        try {
            return readJsonMsgArray(reader);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return null;
    }

    private static List<JsonMessage> readJsonMsgArray(JsonReader reader) throws IOException {
        List<JsonMessage> messages = new ArrayList<JsonMessage>();

        reader.beginArray();
        while (reader.hasNext()){
            messages.add(readJsonMsg(reader));
        }
        reader.endArray();
        return messages;
    }

    private static JsonMessage readJsonMsg(JsonReader reader) throws IOException {
        String sensorName = "";
        double lat = 0;
        double lon = 0;
        double baseHeight = 0;
        Date date = new Date();
        String type = "";
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("sensorName")){
                sensorName = reader.nextString();
            }
            else if(name.equals("longitude")){
                lon = reader.nextDouble();
            }
            else if(name.equals("latitude")){
                lat = reader.nextDouble();
            }
            else if(name.equals("baseHeight")){
                baseHeight = reader.nextDouble();
            }
            else if(name.equals("latest")){
                // try to parse string into Date Time
                try {
                    date = ForumMessage.format.parse(reader.nextString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else if(name.equals("type")){
                type = reader.nextString();
            }
        }
        reader.endObject();
        return new JsonMessage(sensorName,lat,lon,baseHeight,date);
    }
}
