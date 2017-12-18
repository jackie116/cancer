package com.example.huangyuwei.myapplication.mem;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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

import com.example.huangyuwei.myapplication.R;

import java.text.NumberFormat;


public class mem_body_weight_test extends Fragment {
    EditText edtcm,edtkg;
    TextView edtresult;
    private Button btncount;
    private LinearLayout linear_tab;


    public mem_body_weight_test() {
        // Required empty public constructor
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        edtcm=(EditText) getActivity().findViewById(R.id.input_cm);
        edtkg=(EditText)getActivity().findViewById(R.id.input_kg);
        edtresult=(TextView)getActivity().findViewById(R.id.result) ;
        btncount=(Button) getActivity().findViewById(R.id.count);

        linear_tab = (LinearLayout)getActivity().findViewById(R.id.linear_tab);

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
                    Toast.makeText(getActivity(),"輸入不可為空", Toast.LENGTH_SHORT).show();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_mem_body_weight_test, container, false);
    }


}
