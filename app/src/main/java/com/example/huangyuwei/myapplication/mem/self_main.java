package com.example.huangyuwei.myapplication.mem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huangyuwei.myapplication.R;

public class self_main extends AppCompatActivity {
    private static self_main mInstance = null;
    Button btn_mood;
    Button btn_food;
    Button btn_body;
    Button btn_cure;
    Button btn_mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_main);
        mInstance=this;
        btn_mine = (Button) findViewById(R.id.mem_me_button);
        btn_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(self_main.this  , mem_mine.class);
                startActivity(intent);
            }
        });

        btn_mood= (Button) findViewById(R.id.mem_mood_button);
        btn_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(self_main.this  , mem_mood_main.class);
                startActivity(intent);
            }
        });

        btn_food= (Button)findViewById(R.id.mem_food_button);
        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mem_food_main.class);
                startActivity(intent);
            }
        });
        btn_cure = (Button)findViewById(R.id.mem_cure_button);
        btn_cure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mem_cure_main.class);
                startActivity(intent);
            }
        });

        btn_body= (Button)findViewById(R.id.mem_body_button);
        btn_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mem_body_main.class);
                startActivity(intent);
            }
        });

    }
    public static self_main getInstance(){
        return mInstance;
    }
}
