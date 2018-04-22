package team3.teamproject;

import org.junit.Test;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.Assert.*;

/**
 * Created by Steve N.
 * Test Major functionality of all stream readers in JsonStreamReader
 */
public class JsonStreamReaderTest {
    @Test
    public void readJsonSensorDataStream_valid_returnsList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getData.php?property=sound");
        assertNotEquals(JsonStreamReader.readJsonSensorDataStream(((HttpsURLConnection)(urlPath.openConnection())).getInputStream(),257),0);
    }

    @Test
    public void readJsonSensorDataStream_invalidStream_returnsList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getData.php?property=sound");
        InputStream stream = ((HttpsURLConnection)(urlPath.openConnection())).getInputStream();
        stream.close();
        assertEquals(JsonStreamReader.readJsonSensorDataStream(stream,257),0);
    }

    @Test
    public void readJsonSensorDataStream_invalid_returnsList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getData.php?property=sound");
        assertNotEquals(JsonStreamReader.readJsonSensorDataStream(((HttpsURLConnection)(urlPath.openConnection())).getInputStream(),257),-1);
    }




    @Test
    public void readJsonPinStream_invalidStream_returnsEmptyList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getPins.php");
        InputStream stream = ((HttpsURLConnection)(urlPath.openConnection())).getInputStream();
        stream.close();
        assertEquals(JsonStreamReader.readJsonPinStream(stream),0);
    }


    @Test
    public void readJsonPinDataStream_valid_returnsList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getPins.php");
        assertNotEquals(JsonStreamReader.readJsonPinStream(((HttpsURLConnection)(urlPath.openConnection())).getInputStream()),0);
    }


    @Test
    public void readSensorJsonStream_invalidStream_returnsEmptyList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getData.php?property=sound");
        InputStream stream = ((HttpsURLConnection)(urlPath.openConnection())).getInputStream();
        stream.close();
        assertEquals(JsonStreamReader.readSensorJsonStream(stream),0);
    }



    @Test
    public void readSensorJsonStream_valid_returnsList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getData.php?property=sound");
        assertNotEquals(JsonStreamReader.readSensorJsonStream(((HttpsURLConnection)(urlPath.openConnection())).getInputStream()),0);
    }


    @Test
    public void readPostJsonStream_invalidStream_returnsEmptyList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getPost.php?PinID=1");
        InputStream stream = ((HttpsURLConnection)(urlPath.openConnection())).getInputStream();
        stream.close();
        assertEquals(JsonStreamReader.readPostJsonStream(stream),0);
    }



    @Test
    public void readPostJsonStream_valid_returnsList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getPost.php?PinID=1");
        assertNotEquals(JsonStreamReader.readPostJsonStream(((HttpsURLConnection)(urlPath.openConnection())).getInputStream()),0);
    }


    @Test
    public void readCommentJsonStream_invalidStream_returnsEmptyList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getComment.php?PostID=1");
        InputStream stream = ((HttpsURLConnection)(urlPath.openConnection())).getInputStream();
        stream.close();
        assertEquals(JsonStreamReader.readCommentJsonStream(stream),0);
    }


    @Test
    public void readCommentJsonStream_valid_returnsList() throws Exception {
        URL urlPath = new URL("https://duffin.co/uo/getComment.php?PostID=1");
        assertNotEquals(JsonStreamReader.readCommentJsonStream(((HttpsURLConnection)(urlPath.openConnection())).getInputStream()),0);
    }

}