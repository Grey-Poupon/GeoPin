package team3.teamproject;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Steve on 22/04/2018.
 */
public class ForumPostActivityTest {


    @Test
    public void getComments_valid_returnsList(){
        ForumPostActivity forum = new ForumPostActivity();
        ForumPost post = new ForumPost("XXX","XXX","XXX","XXX","3", new Date());
        assertNotEquals(forum.getComments(post),0);
    }
    @Test
    public void getComments_invalid_returnsEmptyList(){
        ForumPostActivity forum = new ForumPostActivity();
        ForumPost post = new ForumPost("XXX","XXX","XXX","XXX","-1", new Date());
        assertEquals(forum.getComments(post),0);
    }

    @Test
    public void addMsgToServer_valid_returnsEmptyList(){
        ForumPostActivity forum = new ForumPostActivity();
        ForumPost post = new ForumPost("XXX","XXX","test","1","1", new Date());
        assertNotEquals(forum.getComments(post),0);
    }

    @Test
    public void addMsgToServer_invalidpostID_returnsEmptyList(){
        ForumPostActivity forum = new ForumPostActivity();
        ForumPost post = new ForumPost("XXX","XXX","test","1","-1", new Date());
        assertEquals(forum.getComments(post),0);
    }
    @Test
    public void addMsgToServer_invalidUID_returnsEmptyList(){
        ForumPostActivity forum = new ForumPostActivity();
        ForumPost post = new ForumPost("XXX","XXX","XXX","1","1", new Date());
        assertEquals(forum.getComments(post),0);
    }

}