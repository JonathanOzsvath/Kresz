package com.example.jonat.kresz.Data;

import android.widget.CheckBox;

/**
 * Created by jonat on 2016. 02. 18..
 */
public class ListViewItem {

    public final CheckBox checkBox;
    public final String answer;

    public ListViewItem(CheckBox checkBox, String answer) {
        this.checkBox = checkBox;
        this.answer = answer;
    }
}
