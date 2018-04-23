package team3.teamproject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Steve on 19/04/2018.
 * Functions for sendsing Post Requests to the server
 */

//Controlls the flow of posts between the app and the database

public class PostStreamReader {


    public static List<JsonPostMessage> getPosts(String pinID) throws Exception {
        String URLend = "getPost.php?";
        String URLParameters = "pinID="+pinID;
        return getPosts(URLend, URLParameters);
    }
    public static List<JsonCommentMessage> getComments(String postID) throws Exception {
        String URLend = "getComment.php?";
        String URLParameters = "postID="+postID;
        return getComments(URLend,URLParameters);
    }
    public static String createPost(String username, String title, String description,String pinID) throws Exception {
        String URLend = "createPost.php?";
        if(username==null){username = "test";}
        String URLParameters = String.format("username=%s&title=%s&description=%s&pin=%s", username, title, description,pinID);
        return sendCreate(URLend,URLParameters);
    }

    public static String createComment(String username, String comment, String postID) throws Exception {
        String URLend = "createComment.php?";
        if(username==null){username="test";}
        String URLParameters = String.format("username=%s&comment=%s&postID=%s", username,comment,postID);
        return sendCreate(URLend,URLParameters);
    }

    // Steve N
    private static String sendCreate(String url, String urlParameters) throws Exception {

        URL obj = new URL("https://duffin.co/uo/"+url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", System.getProperty("http.agent"));
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        InputStream in = con.getInputStream();
        String inputLine;
        StringBuffer response = new StringBuffer();


        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        while ((inputLine = bin.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    // used for getting response from server Mantas S
    public static String sendCreateString(String url, String urlParameters) throws Exception {


        URL obj = new URL("https://duffin.co/uo/"+url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", System.getProperty("http.agent"));
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();


        InputStream in = con.getInputStream();
        String inputLine;
        StringBuffer response = new StringBuffer();


        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        while ((inputLine = bin.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        return response.toString();

    }

    // Steve N
    private static List<JsonPostMessage> getPosts(String url, String urlParameters) throws Exception {

        URL obj = new URL("https://duffin.co/uo/"+url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", System.getProperty("http.agent"));
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        InputStream in = con.getInputStream();
        String inputLine;
        StringBuffer response = new StringBuffer();

        return JsonStreamReader.readPostJsonStream(in);
    }

    // Steve N
    private static List<JsonCommentMessage> getComments(String url, String urlParameters) throws Exception {

        URL obj = new URL("https://duffin.co/uo/"+url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", System.getProperty("http.agent"));
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();


        InputStream in = con.getInputStream();
        String inputLine;
        StringBuffer response = new StringBuffer();

        return JsonStreamReader.readCommentJsonStream(in);
    }

    public static URL getUserImage(String username){
        URL obj = null;
        try {
            obj = new URL("https://duffin.co/uo/getUserImage.php?name=");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpsURLConnection con = null;
        try {
            con = (HttpsURLConnection) obj.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //add reuqest header
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("User-Agent", System.getProperty("http.agent"));
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        con.setDoOutput(true);
        DataOutputStream wr = null;
        String inputLine;
        StringBuffer response = new StringBuffer();

        try {
            // Send post request
            wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(username);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            InputStream in = con.getInputStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));

            while ((inputLine = bin.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            return new URL(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
