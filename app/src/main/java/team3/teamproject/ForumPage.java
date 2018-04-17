package team3.teamproject;

import android.os.Parcel;

import java.util.Date;

public class ForumPage extends ForumMessage  {

    public String getTitle() {
        return title;
    }

    private String title;

    public ForumPage(String title, String text, String uID, String boardID, String ID, Date date) {
        super(text, uID, boardID, ID, date);
        this.title = title;
    }

    protected ForumPage(Parcel in) {
        super(in);
        this.title = in.readString();
    }

    public static final Creator<ForumPage> CREATOR = new Creator<ForumPage>() {
        @Override
        public ForumPage createFromParcel(Parcel in) {
            return new ForumPage(in);
        }

        @Override
        public ForumPage[] newArray(int size) {
            return new ForumPage[size];
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
    public String getBoardID(){
        return getParentID();
    }
}
