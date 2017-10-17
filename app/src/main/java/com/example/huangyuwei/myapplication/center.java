package com.example.huangyuwei.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;

public class center extends AppCompatActivity {
    ImageButton Btn_ask;
    ImageButton Btn_link;
    ImageButton Btn_mem;
    ImageButton Btn_move;
    ImageButton Btn_talk;
    ImageButton Btn_eat;
    ImageButton Btn_laugh;
    ImageButton Btn_cure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        Btn_laugh= (ImageButton) findViewById(R.id.Btn_laugh);
        Btn_laugh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(center.this  , laugh.class);
                startActivity(intent);
            }
        });

        Btn_cure= (ImageButton) findViewById(R.id.Btn_cure);
        Btn_cure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(center.this  , cure.class);
                startActivity(intent);
            }
        });

        Btn_eat= (ImageButton) findViewById(R.id.Btn_eat);
        Btn_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(center.this  , eat.class);
                startActivity(intent);
            }
        });

        Btn_ask= (ImageButton) findViewById(R.id.Btn_ask);
        Btn_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(center.this  , ask.class);
                startActivity(intent);
            }
        });

        Btn_link= (ImageButton) findViewById(R.id.Btn_link);
        Btn_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(center.this  , link.class);
                startActivity(intent);
            }
        });

        Btn_mem= (ImageButton) findViewById(R.id.Btn_mem);
        Btn_mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(center.this  , mem.class);
                startActivity(intent);
            }
        });

        Btn_move= (ImageButton) findViewById(R.id.Btn_move);
        Btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(center.this  , move.class);
                startActivity(intent);
            }
        });

        Btn_talk= (ImageButton) findViewById(R.id.Btn_talk);
        Btn_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(center.this  ,talk.class);
                startActivity(intent);
            }
        });
    }
}
