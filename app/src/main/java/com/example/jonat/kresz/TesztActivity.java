package com.example.jonat.kresz;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
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
import java.util.concurrent.TimeUnit;

public class TesztActivity extends Activity {

    private List<ListViewItem> mItems;
    private ImageView imageView;
    private ListView listView;
    public ListViewAdapter listViewAdapter;
    private TextView feladat, textViewTime;
    private JSONObject obj;
    private int szam;
    private int temakor;
    int helyes;
    int aktfeladat, aktpontszam;
    private TextView feladatSzam, pontSzam;
    private CounterClass timer;
    int time;
    long millis;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teszt);

        try {
            obj = new JSONObject(loadJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (savedInstanceState != null) {
            szam = savedInstanceState.getInt("feladatSzam");
            temakor = savedInstanceState.getInt("temakor");
            aktfeladat = savedInstanceState.getInt("aktFeladat");
            aktpontszam = savedInstanceState.getInt("aktPontszam");
            time = (int) savedInstanceState.getLong("millis");
            savedInstanceState.clear();
        } else {
            temakor = 0;
            aktfeladat = 1;
            aktpontszam = 0;
            time = 60000;

            try {
                Random r = new Random();
                szam = Integer.parseInt(obj.getJSONArray("groups").getJSONObject(temakor).
                        getJSONArray("questions").getString(r.nextInt(obj.getJSONArray("groups").
                        getJSONObject(temakor).
                        getJSONArray("questions").length())));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        timer = new CounterClass(time, 1000);
        timer.start();
        findView();

        //feladat(721079);
        //feladat(703566);
        //feladat(703478);
        feladat(szam);

        progressBar.setMax(60);
        feladatSzam.setText(aktfeladat + "/55");
        pontSzam.setText(aktpontszam + "/75");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.ch);

                if (listCheck()) {
                    listEnableDisable(false);
                } else {
                    checkBox.setChecked(!checkBox.isChecked());
                    if (position == helyes) {
                        changeColor(helyes, Color.GREEN);
                        Toast.makeText(TesztActivity.this, "helyes", Toast.LENGTH_LONG).show();
                        try {
                            aktpontszam += Integer.parseInt(obj.getJSONArray("groups")
                                    .getJSONObject(temakor).getString("score"));
                            pontSzam.setText(aktpontszam + "/75");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        changeColor(position, Color.RED);
                        changeColor(helyes, Color.GREEN);
                        Toast.makeText(TesztActivity.this, "rossz", Toast.LENGTH_LONG).show();
                    }

                    listEnableDisable(false);
                    timer.cancel();
                }
            }
        });
    }

    public boolean listCheck() {
        for (int i = 0; i <= listView.getLastVisiblePosition()-listView.getFirstVisiblePosition(); i++) {
            CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.ch);
            if (checkBox.isChecked()) {
                return true;
            }
        }
        return false;
    }

    public void listEnableDisable(boolean enableDisable) {
        for (int i = 0; i <= listView.getLastVisiblePosition()-listView.getFirstVisiblePosition(); i++) {
            CheckBox checkBox = (CheckBox) listView.getChildAt(i).findViewById(R.id.ch);
            checkBox.setEnabled(enableDisable);
        }
    }

    public void changeColor(int x, int color) {
        TextView tv = (TextView) listView.getChildAt(x).findViewById(R.id.tv);
        tv.setTextColor(color);
    }

    public void nextOnClick(View v) {
        if (v.getId() == R.id.next) {
            try {
                temakor++;
                Random r = new Random();
                szam = Integer.parseInt(obj.getJSONArray("groups").getJSONObject(temakor).
                        getJSONArray("questions").getString(r.nextInt(obj.getJSONArray("groups").
                        getJSONObject(temakor).
                        getJSONArray("questions").length())));
                feladat(szam);
                aktfeladat++;
                feladatSzam.setText(aktfeladat + "/55");

                time = 60000;
                timer.cancel();
                timer = new CounterClass(time, 1000);
                timer.start();
                textViewTime.setTextColor(Color.parseColor("#fffb64"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void feladat(int szam) {
        try {
            mItems = new ArrayList<ListViewItem>();

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
                mItems.add(new ListViewItem(new CheckBox(TesztActivity.this),
                        obj.getJSONObject("questions").getJSONObject(String.valueOf(szam)).
                                getJSONArray("choices").getString(keveres2.get(i))));
                if (keveres2.get(i) == 0) {
                    helyes = i;
                }
            }

            listViewAdapter = new ListViewAdapter(TesztActivity.this, mItems);
            listView.setAdapter(listViewAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = TesztActivity.this.getAssets().open("166852.txt");
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

    public void findView() {
        imageView = (ImageView) findViewById(R.id.kep);
        listView = (ListView) findViewById(R.id.list);
        feladat = (TextView) findViewById(R.id.feladat);
        feladatSzam = (TextView) findViewById(R.id.feladatszam);
        pontSzam = (TextView) findViewById(R.id.pontszam);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("feladatSzam", szam);
        outState.putInt("temakor", temakor);
        outState.putInt("aktFeladat", aktfeladat);
        outState.putInt("aktPontszam", aktpontszam);
        outState.putLong("millis", millis);
    }


    public class CounterClass extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            System.out.println(hms);
            textViewTime.setText(hms);
            progressBar.setProgress((int) TimeUnit.MILLISECONDS.toSeconds(millis));
        }

        @Override
        public void onFinish() {
            millis = 1;
            textViewTime.setText("Az idő lejárt.");
            textViewTime.setTextColor(Color.RED);
            listEnableDisable(false);
            changeColor(helyes, Color.GREEN);
        }
    }
}