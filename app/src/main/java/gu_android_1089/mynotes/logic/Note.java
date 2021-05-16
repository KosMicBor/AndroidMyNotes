package gu_android_1089.mynotes.logic;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

public class Note implements Parcelable {
    private final String id;
    private String title;
    private String note;
    private Date date;
    private String category;
    private Color color;


    public Note(String id, String title, String note, String category, Date date) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.category = category;
        this.date = date;

    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        note = in.readString();
        category = in.readString();
        date = (Date) in.readSerializable();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
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


    public String getId() {
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
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(category);
        dest.writeSerializable(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return Objects.equals(id, note1.id) &&
                Objects.equals(title, note1.title) &&
                Objects.equals(note, note1.note) &&
                Objects.equals(date, note1.date) &&
                Objects.equals(category, note1.category) &&
                Objects.equals(color, note1.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, note, date, category, color);
    }
}
