package team3.teamproject;

import java.util.Date;

/**
 * Created by Steve on 18/04/2018.
 */

public class JsonPostMessage {
    String selectID;
    String title;
    String description;
    Date datePosted;

    public JsonPostMessage(String selectID, String title, String description, Date datePosted) {
        this.selectID = selectID;
        this.title = title;
        this.description = description;
        this.datePosted = datePosted;

    }

    public String getSelectID() {
        return selectID;
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


}
