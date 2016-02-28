package com.example.jonat.kresz.Data;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.CheckBox;

/**
 * Created by jonat on 2016. 02. 18..
 */
public class ListViewItem implements Parcelable {

    public final CheckBox checkBox;
    public final String answer;

    public ListViewItem(CheckBox checkBox, String answer) {
        this.checkBox = checkBox;
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(checkBox);
        dest.writeString(answer);
    }

    private ListViewItem(Parcel in){
        this.checkBox = (CheckBox) in.readValue(ListViewItem.class.getClassLoader());
        this.answer = in.readString();
    }

    public static final Creator<ListViewItem> CREATOR = new Creator<ListViewItem>() {
        @Override
        public ListViewItem createFromParcel(Parcel source) {
            return new ListViewItem(source);
        }

        @Override
        public ListViewItem[] newArray(int size) {
            return new ListViewItem[size];
        }
    };
}
