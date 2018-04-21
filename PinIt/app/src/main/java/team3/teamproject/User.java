package team3.teamproject;

import android.app.Application;

/**
 * Created by Steve on 22/03/2018.
 */

public class User extends Application {
<<<<<<< HEAD
    private String UserID;

    //Added fields for user name, last name, and email
    private String UserName;
    private String UserLastName;
    private String UserEmail;

    public String getUserID() {
        return UserID;
    }
    public String getUserName() {
        return UserName;
    }
    public String getUserLastName() {
        return UserLastName;
    }
    public String getUserEmail() {
        return UserEmail;
=======
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
>>>>>>> PinItmaster/master
    }


    public void setUserID(String userID) {
<<<<<<< HEAD
        this.UserID = userID;
    }
    public void setUserName(String userName) {
        this.UserName = userName;
    }
    public void setUserLastName(String userLastName) {
        this.UserLastName = userLastName;
    }
    public void setUserEmail(String userEmail) {
        this.UserEmail = userEmail;
=======
        this.userID = userID;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserImageURL(String userEmail) {
        this.userImageURL = userImageURL;
>>>>>>> PinItmaster/master
    }

}
