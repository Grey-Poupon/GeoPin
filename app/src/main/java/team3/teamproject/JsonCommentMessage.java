package team3.teamproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve on 18/04/2018.
 */

public class JsonCommentMessage {
    String ID, userID, comment;
    Date date;

    public JsonCommentMessage(String ID, String userID, String comment, Date date) {
        this.ID = ID;
        this.userID = userID;
        this.comment = comment;
        this.date = date;
    }

    public String getID() {
        return ID;
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

    public static ForumMessage toForumMessage( JsonCommentMessage msg,String postID){
        return new ForumMessage(msg.getComment(),msg.getUserID(),postID,msg.getID(),msg.getDate());
    }
    public static List<ForumMessage> toListForumMessages(List<JsonCommentMessage> msgs, String postID){
        List<ForumMessage> forumMsgs = new ArrayList<ForumMessage>();
        for(JsonCommentMessage msg: msgs){
            forumMsgs.add(toForumMessage(msg,postID));
        }
        return forumMsgs;
    }

}
