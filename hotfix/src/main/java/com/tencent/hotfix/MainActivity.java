package com.tencent.hotfix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResult = findViewById(R.id.result);

    }

    public void produceBug(View view) {
        int i=2;
        mResult.setText(""+(100/i));
    }

    public void fixBug(View view) {

    }
}
