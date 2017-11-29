package com.example.huangyuwei.myapplication.mem;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.huangyuwei.myapplication.R;

import java.util.Date;

public class mood_ask extends AppCompatActivity {

    ImageButton btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_ask);

        btn_start= (ImageButton) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext() , mood_qa.class);
                startActivity(intent);
            }
        });
    }
}
