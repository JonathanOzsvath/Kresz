package com.example.jonat.kresz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tesztOnClick (View v){
        if (v.getId() == R.id.teszt){
            Intent i = new Intent();
            i.setClass(this, TesztActivity.class);
            startActivity(i);
        }
    }

    public void osszesOnClick (View v){
        if (v.getId() == R.id.osszes){
            Intent i = new Intent();
            i.setClass(this,TemakorActivity.class);
            startActivity(i);
        }
    }
}
