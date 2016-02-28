package com.example.jonat.kresz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jonat.kresz.Data.TemakorListViewItem;
import com.example.jonat.kresz.R;

import java.util.List;

/**
 * Created by jonat on 2016. 02. 27..
 */
public class TemakorListViewAdapter extends BaseAdapter {

    private final List<TemakorListViewItem> listViewItems;

    public TemakorListViewAdapter(Context applicationContext, List<TemakorListViewItem> listViewItems) {
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
        final TemakorListViewItem temakorListViewItem = listViewItems.get(position);

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View iteView = inflater.inflate(R.layout.temakor_listview_item,null);

        TextView textView = (TextView) iteView.findViewById(R.id.temakorCim);
        textView.setText(temakorListViewItem.temakor);

        return iteView;
    }
}
