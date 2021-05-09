package gu_android_1089.mynotes.logic;

import android.graphics.Color;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Notes implements Parcelable {
    private final  double id;
    private String title;
    private String note;
    private Date date;
    private String category;
    private Color color;



    public Notes(Double id, String title, String note, String category) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.category = category;
        this.date = new Date();

    }

    protected Notes(Parcel in) {
        id = in.readDouble();
        title = in.readString();
        note = in.readString();
        category = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public double getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(id);
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(category);
    }
}
