package com.example.jonat.kresz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jonat.kresz.Data.ListViewItem;
import com.example.jonat.kresz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 2016. 02. 18..
 */
public class ListViewAdapter extends ArrayAdapter<ListViewItem> {
    public ListViewAdapter(Context context, List<ListViewItem> items) {
        super(context, R.layout.listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.ch);
            viewHolder.answer = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // update the item view
        ListViewItem item = getItem(position);
        viewHolder.checkBox.setChecked(item.checkBox.isChecked());
        viewHolder.answer.setText(item.answer);

        return convertView;
    }

    private static class ViewHolder {
        CheckBox checkBox;
        TextView answer;
    }
}
