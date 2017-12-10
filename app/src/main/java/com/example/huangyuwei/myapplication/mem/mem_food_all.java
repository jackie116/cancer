package com.example.huangyuwei.myapplication.mem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.FoodTime;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

class day{
    int date_id;
    double calories;
}

public class mem_food_all extends Fragment{
    private EditText fromDateEtxt;
    private ToggleButton Viewtoggle;
    private MonthYearPickerDialog fromDatePickerDialog;
    private DatePickerDialog fromDayPickerDialog;
    private SimpleDateFormat datedbFormatter;
    private List<FoodTime> fooddays;
    private List<day> graphdays;
    private DataPoint[] graphdata;
    private GraphView graph;
    private Date currentDateView;
    private boolean week=false;
    public mem_food_all() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_food_all, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");
        datedbFormatter.setLenient(false);
        fromDateEtxt=(EditText) getView().findViewById(R.id.EditAllDate);
        Viewtoggle=(ToggleButton)getView().findViewById(R.id.toggleWeekButton);
        currentDateView=Calendar.getInstance().getTime();
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        if(!week)
            fromDateEtxt.setText((currentDateView.getMonth()+1)+"月");
        else
            fromDateEtxt.setText((currentDateView.getMonth() + 1)+"/"+(currentDateView.getDate())+"那一週");
        fooddays= CancerDatabase.getInMemoryDatabase(getContext()).foodTimeDao().getAllFoodTime();
        graphdays=new ArrayList<day>();
        graph = (GraphView) getView().findViewById(R.id.food_graph);
        refreshGraph();
        setDateField();
        Viewtoggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    week = true;
                    setDateField();
                }
                else {
                    week = false;
                    setDateField();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDateField() {
        if(!week) {
            fromDateEtxt.setText((currentDateView.getMonth()+1)+"月");
            fromDateEtxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fromDatePickerDialog.show(getActivity().getFragmentManager(), "Choose a month");
                }
            });
            refreshGraph();
            fromDatePickerDialog = new MonthYearPickerDialog();
            fromDatePickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newCl = Calendar.getInstance();
                    newCl.set(year, monthOfYear, newCl.get(Calendar.DAY_OF_MONTH));
                    currentDateView = newCl.getTime();
                    fromDateEtxt.setText((currentDateView.getMonth() + 1) + "月");
                    refreshGraph();
                }
            });
        }
        else{
            fromDateEtxt.setText((currentDateView.getMonth() + 1)+"/"+(currentDateView.getDate())+"那一週");
            fromDateEtxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fromDayPickerDialog.show();
                }
            });

            refreshGraph();
            Calendar newCalendar = Calendar.getInstance();
            fromDayPickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newCl = Calendar.getInstance();
                    newCl.set(year, monthOfYear, dayOfMonth);
                    currentDateView = newCl.getTime();
                    fromDateEtxt.setText((currentDateView.getMonth() + 1)+"/"+(currentDateView.getDate())+"那一週");
                    refreshGraph();
                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        }
        //pd.show(getActivity().getFragmentManager(),"Choose a month");

//        Calendar newCalendar = Calendar.getInstance();
//        fromDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newCl = Calendar.getInstance();
//                newCl.set(year, monthOfYear, dayOfMonth);
//                currentDateView=newCl.getTime();
//                fromDateEtxt.setText((currentDateView.getMonth()+1)+"月");
//                refreshGraph();
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshGraph() {
        int j = 0;
        graphdays.clear();
        if (!week) {
            for (int i = 0; i < fooddays.size(); i++) {
                //Log.d(TAG,"month "+viewmonth.get(Calendar.MONTH));
                if ((fooddays.get(i).date_id / 100) % 100 == (currentDateView.getMonth() + 1)) {
                    boolean dataexists = false;
                    for (j = 0; j < graphdays.size(); j++) {
                        if (graphdays.get(j).date_id == fooddays.get(i).date_id) {
                            dataexists = true;
                            break;
                        }
                    }
                    if (dataexists)
                        graphdays.get(j).calories += fooddays.get(i).calories;
                    else {
                        day temp = new day();
                        temp.calories = fooddays.get(i).calories;
                        temp.date_id = fooddays.get(i).date_id;
                        graphdays.add(temp);
                    }
                }
            }
            graphdata = new DataPoint[graphdays.size()];
            for (int i = 0; i < graphdays.size(); i++) {
                graphdata[i] = new DataPoint(graphdays.get(i).date_id % 100, graphdays.get(i).calories);
            }
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter());
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphdata);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(1);
            graph.getViewport().setMaxX(31);
            graph.removeAllSeries();
            graph.addSeries(series);
        }
        else{
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(currentDateView);
            cal2.setTime(currentDateView);

            int date=Integer.parseInt(datedbFormatter.format(cal.getTime()));
            cal2.add(Calendar.DATE, -6);
            int dateBefore6Days=Integer.parseInt(datedbFormatter.format(cal2.getTime()));
            for (int i = 0; i < fooddays.size(); i++) {
                //Log.d(TAG,"month "+viewmonth.get(Calendar.MONTH));
                if (fooddays.get(i).date_id <= date
                        && fooddays.get(i).date_id >= dateBefore6Days) {
                    boolean dataexists = false;
                    for (j = 0; j < graphdays.size(); j++) {
                        if (graphdays.get(j).date_id == fooddays.get(i).date_id) {
                            dataexists = true;
                            break;
                        }
                    }
                    if (dataexists)
                        graphdays.get(j).calories += fooddays.get(i).calories;
                    else {
                        day temp = new day();
                        temp.calories = fooddays.get(i).calories;
                        temp.date_id = fooddays.get(i).date_id;
                        graphdays.add(temp);
                    }
                }
            }
            graphdata = new DataPoint[7];
            int di=0;
            for(int k=dateBefore6Days;k<=date;k++) {
                boolean isdate=false;
                Date datedb = Calendar.getInstance().getTime(); //initialize

                try {
                    datedb = datedbFormatter.parse(Integer.toString(k));

                    isdate=true;
                } catch (ParseException e) {

                }
                if(isdate) {
                    boolean exist = false;
                    int i = 0;
                    for (i = 0; i < graphdays.size(); i++) {
                        if (k == graphdays.get(i).date_id) {
                            exist = true;
                            break;
                        }
                        //Log.d(TAG, "output " + graphdays.get(i).date_id);
                    }

                    if (exist)
                        graphdata[di] = new DataPoint(datedb, graphdays.get(i).calories);
                    else
                        graphdata[di] = new DataPoint(datedb, 0);
                    di++;
                }
            }
            for(int i=0;i<7;i++){
                Log.d(TAG, "Test " + graphdata[i].getX()+" "+graphdata[i].getY());
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphdata);
            graph.removeAllSeries();
            graph.addSeries(series);
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            //graph.getGridLabelRenderer().setNumHorizontalLabels(7);
            cal2.add(Calendar.DATE,-1);
            graph.getViewport().setMinX(cal2.getTime().getTime());
            cal.add(Calendar.DATE,1);
            graph.getViewport().setMaxX(cal.getTime().getTime());
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);

           // graph.getGridLabelRenderer().setHumanRounding(false);
        }
    }



}