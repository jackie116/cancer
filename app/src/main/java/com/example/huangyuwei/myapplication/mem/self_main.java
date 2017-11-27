package com.example.huangyuwei.myapplication.mem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.move.move_exercise_movie;

public class self_main extends AppCompatActivity {
    Button btn_mood;
    Button btn_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_main);

        btn_mood= (Button) findViewById(R.id.mem_mood_button);
        btn_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(self_main.this  , mood_main.class);
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

    }
}
