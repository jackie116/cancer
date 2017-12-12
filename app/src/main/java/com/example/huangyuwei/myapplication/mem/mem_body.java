package com.example.huangyuwei.myapplication.mem;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


import com.example.huangyuwei.myapplication.R;


public class mem_body extends AppCompatActivity {


    EditText edtcm,edtkg;
    TextView edtresult;
    Button btncount;
    private LinearLayout linear_tab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_body_main);

        edtcm=(EditText)findViewById(R.id.input_cm);
        edtkg=(EditText)findViewById(R.id.input_kg);
        edtresult=(TextView)findViewById(R.id.result) ;
        btncount=(Button)findViewById(R.id.count);
        linear_tab = (LinearLayout)findViewById(R.id.linear_tab_signup);

        linear_tab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                return false;
            }
        });

        btncount.setOnClickListener(new View.OnClickListener() {
            float cm,m,kg,bmi;
            @Override
            public void onClick(View v) {
                if(edtcm.getText().toString().matches("")||edtkg.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),"輸入不可為空", Toast.LENGTH_SHORT).show();
                }else{
                    cm=Float.parseFloat(edtcm.getText().toString());
                    m=cm/100;
                    kg=Float.parseFloat(edtkg.getText().toString());
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits( 2 );

                    bmi=kg/(m*m);
                    if(18.5<=bmi&&bmi<24){
                        edtresult.setTextColor(Color.GREEN);
                    }else{
                        edtresult.setTextColor(Color.RED);
                    }
                    edtresult.setText("BMI="+nf.format(bmi));
                }

            }
        });


    }
}