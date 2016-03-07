package com.example.jonat.kresz;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonat.kresz.Adapter.ListViewAdapter;
import com.example.jonat.kresz.Data.ListViewItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KerdesActivity extends Activity {

    private List<ListViewItem> mItems;
    private ImageView imageView;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private TextView feladat;
    private JSONObject obj;
    private int fszam, szam, temakor, helyes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerdes);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            temakor = b.getInt("temakor");
            szam = b.getInt("szam");
        }
        try {
            obj = new JSONObject(loadJSONFromAsset());
            fszam = getFeladatSzam(szam);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findView();

        feladat(fszam);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.ch);

                checkBox.setChecked(!checkBox.isChecked());
                if (position == helyes) {
                    changeColor(helyes, Color.GREEN);
                    Toast.makeText(KerdesActivity.this, "helyes", Toast.LENGTH_LONG).show();

                } else {
                    changeColor(position, Color.RED);
                    changeColor(helyes, Color.GREEN);
                    Toast.makeText(KerdesActivity.this, "rossz", Toast.LENGTH_LONG).show();
                }
                listEnableDisable(false);
            }
        });
    }

    public void listEnableDisable(boolean enableDisable) {
        for (int i = 0; i <= listView.getLastVisiblePosition() - listView.getFirstVisiblePosition(); i++) {
            CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.ch);
            checkBox.setEnabled(enableDisable);
        }
    }

    public void changeColor(int x, int color) {
        if (listView == null) {
            listView = (ListView) findViewById(R.id.list);
        }
        TextView tv = (TextView) listView.getChildAt(x).findViewById(R.id.tv);
        tv.setTextColor(color);
    }

    public void nextOnClick(View v) throws JSONException {
        if (v.getId() == R.id.next1) {
            int db = obj.getJSONArray("groups").getJSONObject(temakor).
                    getJSONArray("questions").length();
            if (szam < db - 1) {
                szam++;
                mItems = null;
                fszam = getFeladatSzam(szam);
            }
            feladat(fszam);
        }
    }

    public void backOnClick(View v) throws JSONException {
        if (v.getId() == R.id.back1) {
            if (szam > 0) {
                szam--;
                mItems = null;
                fszam = getFeladatSzam(szam);
            }
            feladat(fszam);
        }
    }

    public int getFeladatSzam(int szam) throws JSONException {
        int x = Integer.parseInt(obj.getJSONArray("groups").getJSONObject(temakor).
                getJSONArray("questions").getString(szam));
        return x;
    }

    public void feladat(int szam) {
        try {
            //feladat lekérdezése
            String tmp = obj.getJSONObject("questions").getJSONObject(String.valueOf(szam)).
                    getString("question");
            feladat.setText(tmp);

            //kép cseréje
            if (obj.getJSONObject("questions").getJSONObject(String.valueOf(szam)).getJSONArray("assets").length() != 0) {
                tmp = obj.getJSONObject("questions").getJSONObject(String.valueOf(szam)).getJSONArray("assets").getString(0);
                String tmp2 = "kepatmeretezes_hu_" + tmp;
                tmp2 = tmp2.substring(0, tmp2.length() - 4);
                int i = getResources().getIdentifier(tmp2, "drawable", getPackageName());

                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                imageView.setBackgroundResource(R.drawable.image);

                imageView.setImageResource(i);

            } else {
                imageView.setImageResource(0);
                imageView.setBackgroundResource(0);
            }

            //Válasz lehetőségek
            if (mItems == null) {
                mItems = new ArrayList<ListViewItem>();

                int valaszokSzama = obj.getJSONObject("questions").getJSONObject(String.valueOf(szam)).
                        getJSONArray("choices").length();
                ArrayList<Integer> keveres = new ArrayList<>();
                ArrayList<Integer> keveres2 = new ArrayList<>();
                for (int i = 0; i < valaszokSzama; i++) {
                    keveres.add(i);
                }
                Random r = new Random();
                while (keveres.size() > 0) {
                    int i = r.nextInt(keveres.size());
                    keveres2.add(keveres.get(i));
                    keveres.remove(i);
                }
                for (int i = 0; i < valaszokSzama; i++) {
                    mItems.add(new ListViewItem(new CheckBox(KerdesActivity.this),
                            obj.getJSONObject("questions").getJSONObject(String.valueOf(szam)).
                                    getJSONArray("choices").getString(keveres2.get(i))));
                    if (keveres2.get(i) == 0) {
                        helyes = i;
                    }
                }
            }

            listViewAdapter = new ListViewAdapter(KerdesActivity.this, mItems);
            listView.setAdapter(listViewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void findView() {
        imageView = (ImageView) findViewById(R.id.kep1);
        listView = (ListView) findViewById(R.id.list1);
        feladat = (TextView) findViewById(R.id.feladat1);
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
