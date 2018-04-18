package team3.teamproject;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve on 02/03/2018.
 * Functions for reading the JSON Stream from the Server  Stephen N
 */

public class JsonStreamReader {

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
        return new JsonSensorMessage(sensorName,lat,lon,baseHeight,date);
    }

    private static JsonPostMessage readPostJsonMsg(JsonReader reader) throws IOException {
        String selectID = "";
        String title = "";
        String description = "";
        Date datePosted = null;

        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("SELECT ID")){
                selectID = reader.nextString();
            }
            else if(name.equals("userID")){
                title = reader.nextString();
            }
            else if(name.equals("comment")){
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
        return new JsonPostMessage(selectID,title,description,datePosted);
    }

    private static JsonCommentMessage readCommentJsonMsg(JsonReader reader) throws IOException {
        String selectID = "";
        String userID = "";
        String comment = "";
        Date date = new Date();

        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("selectID")){
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
