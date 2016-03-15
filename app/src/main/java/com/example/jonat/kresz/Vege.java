package com.example.jonat.kresz;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Vege extends Activity {

    int pont;
    int osszpont = 75;
    int szazalek;

    ProgressBar progressBar;
    TextView progressBarTextView, eredmenyUzenet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vege);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            pont = b.getInt("pont");
        } else pont = 0;

        szazalek = (int) (pont / ((double) osszpont / 100.0));

        progressBar = (ProgressBar) findViewById(R.id.circularProgressBar);
        progressBarTextView = (TextView) findViewById(R.id.progressBarTextView);
        eredmenyUzenet = (TextView) findViewById(R.id.eredmenyUzenet);

        progressBarTextView.setText(String.valueOf(szazalek) + "%");
        if (pont >= 65) {
            progressBar.setProgressDrawable(getResources()
                    .getDrawable(R.drawable.circular_progress_bar_green));
            progressBarTextView.setTextColor(getResources().getColor(R.color.pBZold));
            eredmenyUzenet.setTextColor(getResources().getColor(R.color.pBZold));
        }
        progressBar.setProgress(szazalek);
    }


}
