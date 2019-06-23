package com.lyx.particle_effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExplosionField explosionField = new ExplosionField(this);
        explosionField.addListener(findViewById(R.id.image));
        explosionField.addListener(findViewById(R.id.image2));



    }
}
