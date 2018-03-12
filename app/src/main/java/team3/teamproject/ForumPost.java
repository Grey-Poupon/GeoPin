package team3.teamproject;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

public class ForumPost extends ForumMessage  {

    public String getTitle() {
        return title;
    }

    private String title;

    public ForumPost(String title, String text, String uID, String parentID, String ID, Date date) {
        super(text, uID, parentID, ID, date);
        this.title = title;
    }

    protected ForumPost(Parcel in) {
        super(in);
        this.title = in.readString();
    }

    public static final Creator<ForumPost> CREATOR = new Creator<ForumPost>() {
        @Override
        public ForumPost createFromParcel(Parcel in) {
            return new ForumPost(in);
        }

        @Override
        public ForumPost[] newArray(int size) {
            return new ForumPost[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(title);
    }
}
