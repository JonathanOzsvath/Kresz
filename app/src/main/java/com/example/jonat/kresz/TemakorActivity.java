package com.example.jonat.kresz;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jonat.kresz.Adapter.TemakorListViewAdapter;
import com.example.jonat.kresz.Data.TemakorListViewItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TemakorActivity extends Activity {

    private List<TemakorListViewItem> mItems;
    private TemakorListViewAdapter temakorListViewAdapter;
    private ListView listView;
    private JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temakor);

        try {
            obj = new JSONObject(loadJSONFromAsset());

            mItems = new ArrayList<TemakorListViewItem>();
            int temakorokSzama = obj.getJSONArray("groups").length();
            for (int i = 0; i < temakorokSzama; i++) {
                String cim = obj.getJSONArray("groups").getJSONObject(i).getString("title");
                mItems.add(new TemakorListViewItem(cim));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.temakorList);
        temakorListViewAdapter = new TemakorListViewAdapter(TemakorActivity.this,mItems);
        listView.setAdapter(temakorListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putInt("temakor", position);
                Intent i = new Intent();
                i.setClass(TemakorActivity.this, FeladatActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("166852.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
