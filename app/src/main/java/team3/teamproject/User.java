package team3.teamproject;

import android.app.Application;

import java.net.URL;

/**
 * Created by Steve on 22/03/2018.
 */

//Holds the data regarding the user

public class User extends Application {
    private String userID;
    private String userName;
    private URL userImageURL;

    public String getUserID() {
        return userID;
    }
    public String getUserName() {
        return userName;
    }
    public URL getUserImageURL() {return userImageURL;}


    public void setUserID(String userID) {this.userID = userID;}
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserImageURL(URL userImageURL) {
        this.userImageURL = userImageURL;
    }

}
