package team3.teamproject;

import android.app.Application;

/**
 * Created by Steve on 22/03/2018.
 */

public class User extends Application {
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
    }


    public void setUserID(String userID) {
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
    }

}
