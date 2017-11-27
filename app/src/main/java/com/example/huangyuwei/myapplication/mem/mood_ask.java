package com.example.huangyuwei.myapplication.mem;

import android.icu.text.SimpleDateFormat;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.huangyuwei.myapplication.R;

import java.util.Date;

public class mood_ask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_ask);
        //先行定義時間格式

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

//取得現在時間

        Date dt=new Date();

//透過SimpleDateFormat的format方法將Date轉為字串

        String dts=sdf.format(dt);
    }
}
