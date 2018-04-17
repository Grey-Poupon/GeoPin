package team3.teamproject;

import android.app.Application;

/**
 * Created by Steve on 22/03/2018.
 */

public class User extends Application {
    private String UserID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

}
