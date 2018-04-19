package team3.teamproject;

import java.util.Date;

/**
 * Created by Steve on 18/04/2018.
 */

public class JsonCommentMessage {
    String selectID, userID, comment;
    Date date;

    public JsonCommentMessage(String selectID, String userID, String comment, Date date) {
        this.selectID = selectID;
        this.userID = userID;
        this.comment = comment;
        this.date = date;
    }

    public String getSelectID() {
        return selectID;
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
}
