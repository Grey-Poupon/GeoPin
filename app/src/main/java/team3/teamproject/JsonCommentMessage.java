package team3.teamproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve on 18/04/2018.
 */


//Controls the comment functionality on the comments
public class JsonCommentMessage {
    String ID, userID, comment, username;
    Date date;


    //Conctructor
    public JsonCommentMessage(String ID,String username, String userID, String comment, Date date) {
        this.ID = ID;
        this.username = username;
        this.userID = userID;
        this.comment = comment;
        this.date = date;
    }


    //Getters and setters
    public String getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }


    //Convert to a forum message type
    public static ForumMessage toForumMessage( JsonCommentMessage msg,String postID){
        return new ForumMessage(msg.getComment(),msg.getUsername(),msg.getUserID(),postID,msg.getID(),msg.getDate());

    }

    //Returns a list of the forum messages
    public static List<ForumMessage> toListForumMessages(List<JsonCommentMessage> msgs, String postID){
        if(msgs == null){return new ArrayList<ForumMessage>();}
        List<ForumMessage> forumMsgs = new ArrayList<ForumMessage>();
        for(JsonCommentMessage msg: msgs){
            forumMsgs.add(toForumMessage(msg,postID));
        }
        return forumMsgs;
    }

}
