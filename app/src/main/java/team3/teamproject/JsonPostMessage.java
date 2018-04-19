package team3.teamproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve on 18/04/2018.
 */

public class JsonPostMessage {
    String ID;
    String title;
    String description;
    String userID;
    Date datePosted;

    public JsonPostMessage(String ID, String userID, String title, String description, Date datePosted) {
        this.ID = ID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.datePosted = datePosted;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public String getUserID() {
        return userID;
    }

    public static ForumPost toForumPost(JsonPostMessage msg,String boardID){
        return new ForumPost(msg.getTitle(),msg.getDescription(),msg.getUserID(),boardID,msg.getID(),msg.getDatePosted());
    }

    public static List<ForumPost> toListForumPost(List<JsonPostMessage> msgs, String postID){
        if(msgs == null){return new ArrayList<ForumPost>();}
        List<ForumPost> forumMsgs = new ArrayList<ForumPost>();
        for(JsonPostMessage msg: msgs){
            forumMsgs.add(toForumPost(msg,postID));
        }
        return forumMsgs;
    }
}
