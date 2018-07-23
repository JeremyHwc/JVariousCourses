package com.jeremy.jvariouscourses.musicplaywave;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jeremy.jvariouscourses.R;

public class MusicPlayWaveActivity extends AppCompatActivity {
    private MusicPlayWaveView mPlayWaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play_wave);
        mPlayWaveView=findViewById(R.id.music_wave_view);
    }

    public void start(View view) {
        mPlayWaveView.start();
    }

    public void stop(View view) {
        mPlayWaveView.stop();
    }
}
