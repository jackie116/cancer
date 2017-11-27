package com.example.huangyuwei.myapplication.mem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.FoodTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class mem_food_edit extends Fragment{
    private EditText fromDateEtxt;
    private EditText fromTimeEtxt;
    private TextView tablelabel;
    private TableLayout foodtable;
    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog fromTimePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat timedbFormatter;
    Button saveFood;
    private Date currentDateView;
    private List<FoodTime> fooddata;
    public mem_food_edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_food_edit, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        saveFood=(Button)getView().findViewById(R.id.saveFoodDay);
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.TAIWAN);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");
        timedbFormatter = new SimpleDateFormat("HHmm");
        findViewsById();

        setDateTimeField();

        fromDateEtxt.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        fromTimeEtxt.setText(timeFormatter.format(Calendar.getInstance().getTime()));
        tablelabel.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        fooddata=CancerDatabase.getInMemoryDatabase(getContext()).foodTimeDao().getAllFoodTime();
        currentDateView=Calendar.getInstance().getTime();
        for (int i = 0; i <fooddata.size(); i++) {
            if(Integer.parseInt(datedbFormatter.format(currentDateView)) == fooddata.get(i).date_id) {
                Log.d("TAG", fooddata.get(i).time + " " + fooddata.get(i).FoodName + " " + fooddata.get(i).calories);
                addTableRow(foodtable, fooddata.get(i).time, fooddata.get(i).FoodName, fooddata.get(i).calories);
            }
        }

        saveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText datetext = (EditText) getView().findViewById(R.id.EditTextDate);
                String date = datetext.getText().toString();
                Date datedb = Calendar.getInstance().getTime(); //initialize
                try {
                    datedb = dateFormatter.parse(date);
                } catch (ParseException e){

                }

                String dateindb=datedbFormatter.format(datedb);

                final EditText timetext = (EditText) getView().findViewById(R.id.EditTextTime);
                String time = timetext.getText().toString();
                Date timedb = Calendar.getInstance().getTime(); //initialize
                try {
                    timedb = timeFormatter.parse(time);
                } catch (ParseException e){

                }


                String timeindb=timedbFormatter.format(timedb);


                final EditText foodtext = (EditText) getView().findViewById(R.id.EditTextFood);
                String food = foodtext.getText().toString();

                final EditText foodCalories = (EditText) getView().findViewById(R.id.EditTextFoodCalories);;
                String calories = foodCalories.getText().toString();

                FoodTime ftime = new FoodTime();

                ftime.date_id=Integer.parseInt(dateindb);
                ftime.time=Integer.parseInt(timeindb);
                ftime.FoodName=food;
                ftime.calories=Double.parseDouble(calories);
                Log.d("TAG",dateindb+" "+timeindb+ " "+food+" "+calories);
                //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                boolean unique=true;
                for(int i=0;i<fooddata.size();i++){
                    if(fooddata.get(i).time==Integer.parseInt(timeindb) && fooddata.get(i).date_id==Integer.parseInt(dateindb))
                        unique=false;
                }
                if(unique)
                    addFoodTime(CancerDatabase.getInMemoryDatabase(getContext()),ftime);
                int count = foodtable.getChildCount();
                for (int i = 2; i < count; i++) {
                    View child = foodtable.getChildAt(i);
                    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                }

                fooddata.clear();
                fooddata=CancerDatabase.getInMemoryDatabase(getContext()).foodTimeDao().getAllFoodTime();
                for (int i = 0; i <fooddata.size(); i++) {
                    if(Integer.parseInt(datedbFormatter.format(currentDateView)) == fooddata.get(i).date_id) {
                        Log.d("TAG", fooddata.get(i).time + " " + fooddata.get(i).FoodName + " " + fooddata.get(i).calories);
                        addTableRow(foodtable, fooddata.get(i).time, fooddata.get(i).FoodName, fooddata.get(i).calories);
                    }
                }
                foodtext.setText("");
                foodCalories.setText("");
            }
        });


    }

    private void addTableRow(TableLayout tl, int time, String food, Double calories){

        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row, tl, false);

        // Add First Column
        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Name.setText(Integer.toString(time));

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(food);

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(calories.toString());

        tl.addView(tr);
    }



    private FoodTime addFoodTime(final CancerDatabase db, FoodTime time) {
        db.foodTimeDao().insertFoodTime(time);
        return time;
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) getActivity().findViewById(R.id.EditTextDate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        fromTimeEtxt = (EditText) getActivity().findViewById(R.id.EditTextTime);
        fromTimeEtxt.setInputType(InputType.TYPE_NULL);
        fromTimeEtxt.requestFocus();
        tablelabel = (TextView) getActivity().findViewById(R.id.food_day_label);
        foodtable = (TableLayout) getActivity().findViewById(R.id.food_daytable);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
        fromTimeEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromTimePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newCl = Calendar.getInstance();
                newCl.set(year, monthOfYear, dayOfMonth);
                currentDateView=newCl.getTime();
                fromDateEtxt.setText(dateFormatter.format(currentDateView.getTime()));
                tablelabel.setText(dateFormatter.format(currentDateView.getTime()));
                int count = foodtable.getChildCount();
                for (int i = 2; i < count; i++) {
                    View child = foodtable.getChildAt(i);
                    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                }
                fooddata.clear();
                fooddata=CancerDatabase.getInMemoryDatabase(getContext()).foodTimeDao().getAllFoodTime();
                for (int i = 0; i <fooddata.size(); i++) {
                    if(Integer.parseInt(datedbFormatter.format(currentDateView)) == fooddata.get(i).date_id) {
                        Log.d("TAG", fooddata.get(i).time + " " + fooddata.get(i).FoodName + " " + fooddata.get(i).calories);
                        addTableRow(foodtable, fooddata.get(i).time, fooddata.get(i).FoodName, fooddata.get(i).calories);
                    }
                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        fromTimePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                fromTimeEtxt.setText(timeFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH),true);

    }



}