package com.example.huangyuwei.myapplication.mem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huangyuwei.myapplication.R;

public class self_main extends AppCompatActivity {
    Button btn_mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_main);

        btn_mood= (Button) findViewById(R.id.btn_mood);
        btn_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(self_main.this  , mood_main.class);
                startActivity(intent);
            }
        });
    }
}
