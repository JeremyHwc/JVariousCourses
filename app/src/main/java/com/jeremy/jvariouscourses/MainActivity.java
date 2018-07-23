package com.jeremy.jvariouscourses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jeremy.jvariouscourses.musicplaywave.MusicPlayWaveActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void MusicPlayWaveView(View view) {
        startActivity(new Intent(this, MusicPlayWaveActivity.class));
    }
}
