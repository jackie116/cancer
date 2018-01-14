package com.example.huangyuwei.myapplication.laugh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.huangyuwei.myapplication.R;


public class laugh_fashion extends Fragment {
    ImageButton btn_bra;
    ImageButton btn_hair;



    public laugh_fashion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laugh_fashion, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_bra= (ImageButton) getView().findViewById(R.id.Btn_bra);
        btn_bra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity() , laugh_fashion_bra.class);
                startActivity(intent);
            }
        });

        btn_hair= (ImageButton) getView().findViewById(R.id.Btn_hair);
        btn_hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity() , laugh_fashion_hair.class);
                startActivity(intent);
            }
        });


    }



}