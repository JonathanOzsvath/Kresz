package com.example.jonat.kresz;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Vege extends Activity {

    int pont;
    int osszpont = 75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vege);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            pont = b.getInt("pont");
        } else pont = 0;

        int szazalek = (int) (pont / ((double)osszpont/100.0));
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.circularProgressBar);

        progressBar.setProgress(szazalek);

        TextView textView = (TextView) findViewById(R.id.progressBarTextView);

        textView.setText(String.valueOf(szazalek) + "%");
    }


}
