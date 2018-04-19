package team3.teamproject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Steve on 19/04/2018.
 * Functions for sendsing Post Requests to the server
 */

public class PostStreamReader {


    public static List<JsonPostMessage> getPost(String postID, String postTitle, String ownerID, String ownerName, String pinID, String pinName) throws Exception {
        String URLend = "getPost.php?";
        String URLParameters = String.format("selectID=%s&title=%s&description=%s&datePosted=%s&pinID=%s&pinName=s%", postID, postTitle, ownerID, ownerName, pinID, pinName);
        return getPost(URLend, URLParameters);
    }
    public static List<JsonCommentMessage> getComment(String postID, String ownerID, String ownerName) throws Exception {
        String URLend = "getComment.php?";
        String URLParameters = String.format("selectID=%s&description=%s&datePosted=%s&", postID, ownerID, ownerName);
        return getComment(URLend,URLParameters);
    }
    public static void createPost(String username, String title, String description,String pinID) throws Exception {
        String URLend = "createPost.php?";
        String URLParameters = String.format("username=%s&title=%s&description=%s&pinID=%s", username, title, description,pinID);
        sendCreate(URLend,URLParameters);
    }

    public static void createComment(String username, String comment, String postID) throws Exception {
        String URLend = "createComment.php?";
        String URLParameters = String.format("username=%s&comment=%s&selectID=%s", username,comment,postID);
        sendCreate(URLend,URLParameters);
    }

    // Steve N
    private static void sendCreate(String url, String urlParameters) throws Exception {

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
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        InputStream in = con.getInputStream();
        String inputLine;
        StringBuffer response = new StringBuffer();


        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        while ((inputLine = bin.readLine()) != null) {
            response.append(inputLine);

            in.close();

            //print result
            System.out.println(response.toString());

        }}

    // Steve N
    private static List<JsonPostMessage> getPost(String url, String urlParameters) throws Exception {

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
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        InputStream in = con.getInputStream();
        String inputLine;
        StringBuffer response = new StringBuffer();

        return JsonStreamReader.readPostJsonStream(in);
    }

    // Steve N
    private static List<JsonCommentMessage> getComment(String url, String urlParameters) throws Exception {

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
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        InputStream in = con.getInputStream();
        String inputLine;
        StringBuffer response = new StringBuffer();

        return JsonStreamReader.readCommentJsonStream(in);
    }
}
