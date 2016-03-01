package com.example.jonat.kresz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jonat.kresz.Data.FeladatListViewItem;
import com.example.jonat.kresz.R;

import java.util.List;

/**
 * Created by jonat on 2016. 03. 01..
 */
public class FeladatListViewAdapter extends BaseAdapter {

    private final List<FeladatListViewItem> listViewItems;

    public FeladatListViewAdapter(Context applicationContext, List<FeladatListViewItem> listViewItems) {
        this.listViewItems = listViewItems;
    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FeladatListViewItem feladatListViewItem = listViewItems.get(position);

        LayoutInflater inflater = (LayoutInflater) parent.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.feladat_listview_item,null);

        TextView textView = (TextView) itemView.findViewById(R.id.feladatCim);
        textView.setText(feladatListViewItem.feladatCim);

        return itemView;
    }
}
