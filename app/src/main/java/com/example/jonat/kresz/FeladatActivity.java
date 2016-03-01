package com.example.jonat.kresz;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jonat.kresz.Adapter.FeladatListViewAdapter;
import com.example.jonat.kresz.Data.FeladatListViewItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FeladatActivity extends ListActivity {

    private List<FeladatListViewItem> mItems;
    private FeladatListViewAdapter feladatListViewAdapter;
    private JSONObject obj;
    private int temakor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if (b != null){
            temakor = b.getInt("temakor");
        }

        try {
            obj = new JSONObject(loadJSONFromAsset());

            mItems = new ArrayList<>();
            int feladathossz = obj.getJSONArray("groups").getJSONObject(temakor).getJSONArray("questions").length();
            for (int i = 0; i < feladathossz; i++) {
                String feladatokSzama = obj.getJSONArray("groups").getJSONObject(temakor).getJSONArray("questions").getString(i);
                String cim = obj.getJSONObject("questions").getJSONObject(feladatokSzama).getString("question");
                mItems.add(new FeladatListViewItem(cim));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        feladatListViewAdapter = new FeladatListViewAdapter(getApplication(),mItems);
        setListAdapter(feladatListViewAdapter);
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
