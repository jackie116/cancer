package com.example.huangyuwei.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;


public class mem_calendar extends Fragment {



    public mem_calendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_calendar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CalendarView cv = (CalendarView) getView().findViewById(R.id.calendarView);
        //为CalendarView组件的日期改变事件添加事件监听器

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                //使用Toast显示用户选择的日期
                Toast.makeText(getActivity(),"你生日是" + year + "年" + (month+1) + "月" + dayOfMonth + "日",Toast.LENGTH_SHORT).show();

            }
        });

    }



}