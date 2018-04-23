package team3.teamproject;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve on 03/03/2018.
 */

//Controlls forum message

public class ForumMessage implements Comparable, Parcelable {

    /** This is the format for a Date it should used globally*/
    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //User data required for the post
    private String text;
    private String userID;
    private String ID;
    private String parentID;
    private Date date;
    private String username;
    private URL url;

    //Constructor
    public ForumMessage(String text,String username, String uID, String parentID,String ID,Date date){
        this.text = text;
        this.userID = uID;
        this.username = username;
        this.parentID = parentID;
        this.ID = ID;
        this.date = date;
        this.url = PostStreamReader.getUserImage(username);

    }

    //Getters and setters
    public String getText() {
        return text;
    }

    public String getUserID() {
        return userID;
    }

    public String getParentID() {
        return parentID;
    }

    public String getID() {
        return ID;
    }

    public String getDateString() {
        return format.format(date);
    }
    public Date getDate(){
        return date;
    }

    //Overidden comparitor
    @Override
    public int compareTo(@NonNull Object o) {
        return date.compareTo(((ForumMessage)o).getDate());
    }


    public String getUsername() {
        return username;
    }

    public URL getUrl() {
        return url;
    }


    //Forum message reader
    protected ForumMessage(Parcel in) {
        text = in.readString();
        userID = in.readString();
        ID = in.readString();
        parentID = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        username = in.readString();
        url = (URL) in.readValue(URL.class.getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Write post to database
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(userID);
        dest.writeString(ID);
        dest.writeString(parentID);
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeString(username);
        dest.writeValue(url);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ForumMessage> CREATOR = new Parcelable.Creator<ForumMessage>() {
        @Override
        public ForumMessage createFromParcel(Parcel in) {
            return new ForumMessage(in);
        }

        @Override
        public ForumMessage[] newArray(int size) {
            return new ForumMessage[size];
        }
    };

    public void setID(String ID) {
        this.ID = ID;
    }
}