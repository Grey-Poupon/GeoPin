package team3.teamproject;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve on 03/03/2018.
 */

public class ForumMessage implements Comparable, Parcelable {

    /** This is the format for a Date it should used globally*/
    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String text;
    private String userID;
    private String ID;
    private String parentID;
    private Date date;
    private List<ForumMessage> replies = new ArrayList<>();

    public ForumMessage(String text, String uID, String parentID,String ID,Date date){
        this.text = text;
        this.userID = uID;
        this.parentID = parentID;
        this.ID = ID;
        this.date = date;
    }



    public void setReplies(List<ForumMessage> msgs){
        replies = msgs;
    }
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

    public List<ForumMessage> getReplies() {
        return replies;
    }

    public String getDateString() {
        return format.format(date);
    }
    public Date getDate(){
        return date;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return date.compareTo(((ForumMessage)o).getDate());
    }


    protected ForumMessage(Parcel in) {
        text = in.readString();
        userID = in.readString();
        ID = in.readString();
        parentID = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        if (in.readByte() == 0x01) {
            replies = new ArrayList<ForumMessage>();
            in.readList(replies, ForumMessage.class.getClassLoader());
        } else {
            replies = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(userID);
        dest.writeString(ID);
        dest.writeString(parentID);
        dest.writeLong(date != null ? date.getTime() : -1L);
        if (replies == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(replies);
        }
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
}