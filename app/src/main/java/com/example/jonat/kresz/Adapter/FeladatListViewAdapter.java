package com.example.jonat.kresz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jonat.kresz.Data.FeladatListViewItem;
import com.example.jonat.kresz.Data.TemakorListViewItem;
import com.example.jonat.kresz.R;

import java.util.List;

/**
 * Created by jonat on 2016. 03. 01..
 */
public class FeladatListViewAdapter extends ArrayAdapter<FeladatListViewItem> {

    public FeladatListViewAdapter(Context context, List<FeladatListViewItem> listViewItems) {
        super(context, R.layout.feladat_listview_item, listViewItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.feladat_listview_item,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.feladatCim);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FeladatListViewItem item = getItem(position);
        viewHolder.textView.setText(item.feladatCim);

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}
