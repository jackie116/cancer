package com.example.huangyuwei.myapplication.mem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.huangyuwei.myapplication.R;

public class mood_main extends AppCompatActivity {
    ImageButton btn_cry;
    ImageButton btn_happy;
    ImageButton btn_vomit;
    ImageButton btn_despise;
    ImageButton btn_love;
    ImageButton btn_sigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_main);

        btn_cry= (ImageButton) findViewById(R.id.btn_cry);
        btn_cry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mood_ask.class);
                startActivity(intent);
            }
        });

        btn_happy= (ImageButton) findViewById(R.id.btn_happy);
        btn_happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mood_ask.class);
                startActivity(intent);
            }
        });

        btn_vomit= (ImageButton) findViewById(R.id.btn_vomit);
        btn_vomit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mood_ask.class);
                startActivity(intent);
            }
        });

        btn_despise= (ImageButton) findViewById(R.id.btn_despise);
        btn_despise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mood_ask.class);
                startActivity(intent);
            }
        });

        btn_love= (ImageButton) findViewById(R.id.btn_love);
        btn_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mood_ask.class);
                startActivity(intent);
            }
        });
        btn_sigh= (ImageButton) findViewById(R.id.btn_sigh);
        btn_sigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mood_ask.class);
                startActivity(intent);
            }
        });
    }
}
