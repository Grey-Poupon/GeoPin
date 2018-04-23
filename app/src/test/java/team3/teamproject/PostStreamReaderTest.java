package team3.teamproject;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Steve on 22/04/2018.
 */
public class PostStreamReaderTest {
    @Test
    public void getPosts_invalidPinID_returnsEmptyList() throws Exception {
        assertEquals(PostStreamReader.getPosts("XXX").size(),0);
    }
    @Test
    public void getPosts_valid_returnsList() throws Exception {
        assertNotEquals(PostStreamReader.getPosts("2").size(),0);
    }

    @Test
    public void getComments_invalidPinID_returnsEmptyList() throws Exception {
        assertEquals(PostStreamReader.getComments("XXX").size(),0);
    }
    @Test
    public void getComments_valid_returnsList() throws Exception {
        assertNotEquals(PostStreamReader.getComments("2").size(),0);
    }


    @Test
    public void createPost_valid_returnsID() throws Exception {
        assertNotEquals(PostStreamReader.createPost("test","XXX","XXX","1"),"-1");
    }
    @Test
    public void createPost_invalidUsername_returnsErr() throws Exception {
        assertEquals(PostStreamReader.createPost("XXX","XXX","XXX","1"),"-1");
    }
    @Test
    public void createPost_invalidPinID_returnsErr() throws Exception {
        assertEquals(PostStreamReader.createPost("test","XXX","XXX","-1"),"-1");
    }

    @Test
    public void createComment_valid_returnsID() throws Exception {
        assertNotEquals(PostStreamReader.createComment("test","XXX","1"),"-1");
    }
    @Test
    public void createComment_invalidUsername_returnsErr() throws Exception {
        assertEquals(PostStreamReader.createComment("XXX","XXX","1"),"-1");
    }
    @Test
    public void createComment_invalidPostID_returnsErr() throws Exception {
        assertEquals(PostStreamReader.createComment("test","XXX","-1"),"-1");
    }


}