package team3.teamproject;

import android.os.Debug;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
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
                sensorId = Integer.parseInt(reader.nextString());
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

    public static List<Pin> readJsonPinStream(InputStream in){
        JsonReader reader =  new JsonReader(new InputStreamReader(in));
        try {
            return readJsonPinArray(reader);
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

    private static List<Pin> readJsonPinArray(JsonReader reader) throws IOException {
        List<Pin> messages = new ArrayList<Pin>();

        reader.beginArray();
        while (reader.hasNext()){
            messages.add(readJsonPinMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    private static Pin readJsonPinMessage(JsonReader reader) throws IOException{

        String ID = "";
        double longitude = 0d;
        double latitude = 0d;
        String name = "";

        reader.beginObject();
        while(reader.hasNext()){
            String msg = reader.nextName();
            if(msg.equals("ID")){
                ID = reader.nextString();
            }
            else if(msg.equals("longitude")){
                longitude = reader.nextDouble();
            }
            else if(msg.equals("latitude")){
                latitude = reader.nextDouble();
            }
            else if(msg.equals("name")){
                name = reader.nextString();
            }
        }
        reader.endObject();
        return new Pin(ID,new LatLng(latitude,longitude),name);
    }


    public static List<JsonSensorMessage> readSensorJsonStream(InputStream in) {
        JsonReader reader =  new JsonReader(new InputStreamReader(in));
        try {
            return readSensorJsonMsgArray(reader);

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

    public static List<JsonPostMessage> readPostJsonStream(InputStream in) {
        JsonReader reader =  new JsonReader(new InputStreamReader(in));
        try {
            return readPostJsonMsgArray(reader);

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

    public static List<JsonCommentMessage> readCommentJsonStream(InputStream in) {
        JsonReader reader =  new JsonReader(new InputStreamReader(in));
        try {
            return readCommentJsonMsgArray(reader);

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

    private static List<JsonSensorMessage> readSensorJsonMsgArray(JsonReader reader) throws IOException {
        List<JsonSensorMessage> messages = new ArrayList<JsonSensorMessage>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readSensorJsonMsg(reader));
        }

        reader.endArray();
        return messages;
    }

    private static List<JsonPostMessage> readPostJsonMsgArray(JsonReader reader) throws IOException {
        List<JsonPostMessage> messages = new ArrayList<JsonPostMessage>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readPostJsonMsg(reader));
        }

        reader.endArray();
        return messages;
    }

    private static List<JsonCommentMessage> readCommentJsonMsgArray(JsonReader reader) throws IOException {
        List<JsonCommentMessage> messages = new ArrayList<JsonCommentMessage>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readCommentJsonMsg(reader));
        }

        reader.endArray();
        return messages;
    }



    private static JsonSensorMessage readSensorJsonMsg(JsonReader reader) throws IOException {
        String ID = "";
        String sensorName = "";
        double lat = 0;
        double lon = 0;
        double baseHeight = 0;
        Date date = new Date();
        String type = "";

        reader.beginObject();
        while(reader.hasNext()){
            String name = "";
            try {
                name = reader.nextName();
            }
            catch (IllegalStateException e) {
                name = reader.nextString();
            }

            if(name.equals("ID")){
                ID = reader.nextString();
            }
            else if(name.equals("sensorName")){
                sensorName = reader.nextString();
            }
            else if(name.equals("longitude")){
                lon = Double.parseDouble(reader.nextString());
            }
            else if(name.equals("latitude")){
                lat = Double.parseDouble(reader.nextString());
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
        return new JsonSensorMessage(ID,sensorName,lat,lon,baseHeight,date);
    }

    private static JsonPostMessage readPostJsonMsg(JsonReader reader) throws IOException {
        String ID = "";
        String ownerID = "";
        String title = "";
        String description = "";
        Date datePosted = null;

        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("ID")){
                ID = reader.nextString();
            }
            else if(name.equals("ownerID")){
                ownerID = reader.nextString();
            }
            else if(name.equals("title")){
                title = reader.nextString();
            }
            else if(name.equals("description")){
                description = reader.nextString();
            }
            else if(name.equals("datePosted")){
                // try to parse string into Date Time
                try {
                    datePosted = ForumMessage.format.parse(reader.nextString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        reader.endObject();
        return new JsonPostMessage(ID,ownerID,title,description,datePosted);
    }

    private static JsonCommentMessage readCommentJsonMsg(JsonReader reader) throws IOException {
        String selectID = "";
        String userID = "";
        String comment = "";
        Date date = new Date();

        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("ID")){
                selectID = reader.nextString();
            }
            else if(name.equals("userID")){
                userID = reader.nextString();
            }
            else if(name.equals("comment")){
                comment = reader.nextString();
            }
            else if(name.equals("date")){
                // try to parse string into Date Time
                try {
                    date = ForumMessage.format.parse(reader.nextString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        reader.endObject();
        return new JsonCommentMessage(selectID,userID,comment,date);
    }

}
