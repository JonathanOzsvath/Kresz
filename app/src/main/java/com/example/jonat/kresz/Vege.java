package com.example.jonat.kresz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Vege extends Activity {

    int pont;
    int osszpont = 75;
    int szazalek;
    ArrayList<Integer> rossz;

    ProgressBar progressBar;
    TextView progressBarTextView, eredmenyUzenet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vege);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            pont = b.getInt("pont");
            rossz = b.getIntegerArrayList("rossz");
        } else pont = 0;

        szazalek = (int) (pont / ((double) osszpont / 100.0));

        progressBar = (ProgressBar) findViewById(R.id.circularProgressBar);
        progressBarTextView = (TextView) findViewById(R.id.progressBarTextView);
        eredmenyUzenet = (TextView) findViewById(R.id.eredmenyUzenet);
        eredmenyUzenet.setText("Nem sikerült! Próbáld meg újra!");

        progressBarTextView.setText(String.valueOf(szazalek) + "%");
        if (pont >= 65) {
            progressBar.setProgressDrawable(getResources()
                    .getDrawable(R.drawable.circular_progress_bar_green));
            progressBarTextView.setTextColor(getResources().getColor(R.color.pBZold));
            eredmenyUzenet.setText("Gratulálunk! Sikeres volt a teszt!");
            eredmenyUzenet.setTextColor(getResources().getColor(R.color.pBZold));
        }
        progressBar.setProgress(szazalek);
    }

    public void onClickRosszak (View v){
        if (v.getId() == R.id.rosszak){
            Bundle b = new Bundle();
            b.putIntegerArrayList("rossz", rossz);
            Intent i = new Intent();
            i.setClass(Vege.this, KerdesActivity.class);
            i.putExtras(b);
            startActivity(i);
        }
    }

    public void onClickUj (View v){
        if (v.getId() == R.id.ujTeszt){
            Intent i = new Intent();
            i.setClass(this, TesztActivity.class);
            startActivity(i);
        }
    }

}
