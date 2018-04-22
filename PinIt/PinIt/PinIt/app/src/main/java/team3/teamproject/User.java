package team3.teamproject;

import android.app.Application;

/**
 * Created by Steve on 22/03/2018.
 */

public class User extends Application {
    private String userID;
    private String userName;
    private String userImageURL;

    public String getUserID() {
        return userID;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserImageURL() {
        return userImageURL;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserImageURL(String userEmail) {
        this.userImageURL = userImageURL;
    }

}
