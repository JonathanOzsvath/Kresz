package com.example.jonat.kresz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jonat.kresz.Data.ListViewItem;
import com.example.jonat.kresz.Data.TemakorListViewItem;
import com.example.jonat.kresz.R;

import java.util.List;

/**
 * Created by jonat on 2016. 02. 27..
 */
public class TemakorListViewAdapter extends ArrayAdapter<TemakorListViewItem> {

    public TemakorListViewAdapter(Context context, List<TemakorListViewItem> listViewItems) {
        super(context, R.layout.temakor_listview_item, listViewItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.temakor_listview_item,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.temakorCim);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TemakorListViewItem item = getItem(position);
        viewHolder.textView.setText(item.temakor);

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}
