package com.example.huangyuwei.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;




public class mem_body extends Fragment {


    EditText edtcm,edtkg;
    TextView edtresult;
    Button btncount;
    private LinearLayout linear_tab;

    public mem_body() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_body, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edtcm=(EditText)getView().findViewById(R.id.input_cm);
        edtkg=(EditText)getView().findViewById(R.id.input_kg);
        edtresult=(TextView)getView().findViewById(R.id.result) ;
        btncount=(Button)getView().findViewById(R.id.count);
        linear_tab = (LinearLayout)getView().findViewById(R.id.linear_tab_signup);

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
                    Toast.makeText(getActivity(), "輸入不可為空", Toast.LENGTH_SHORT).show();
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