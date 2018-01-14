package com.example.huangyuwei.myapplication.move;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huangyuwei.myapplication.R;

public class move_main extends AppCompatActivity {
    Button btn_exercise;
    Button btn_rehab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_main);


        btn_exercise= (Button) findViewById(R.id.btn_exercise);
        btn_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(move_main.this  , move_exercise.class);
                startActivity(intent);
            }
        });

        btn_rehab= (Button) findViewById(R.id.btn_rehab);
        btn_rehab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(move_main.this  , move_rehab.class);
                startActivity(intent);
            }
        });
    }


}
