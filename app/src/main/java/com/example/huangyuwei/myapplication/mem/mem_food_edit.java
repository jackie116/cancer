package com.example.huangyuwei.myapplication.mem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.huangyuwei.myapplication.MainActivity.cb;
import static com.example.huangyuwei.myapplication.MainActivity.getContext;

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
    //private Button saveFood;
    private Button addFood;
    private Date currentDateView;
    private List<FoodTime> fooddays;
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

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.TAIWAN);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");
        timedbFormatter = new SimpleDateFormat("HHmm",Locale.TAIWAN);
        findViewsById();

        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        setDateField();
        fromDateEtxt.setText(dateFormatter.format(Calendar.getInstance().getTime()));

        tablelabel.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        fooddays=CancerDatabase.getInMemoryDatabase(getContext()).foodTimeDao().getAllFoodTime();
        currentDateView=Calendar.getInstance().getTime();
        for (int i = 0; i <fooddays.size(); i++) {
            if(Integer.parseInt(datedbFormatter.format(currentDateView)) == fooddays.get(i).date_id) {
                Log.d("TAG", fooddays.get(i).time + " " + fooddays.get(i).FoodName + " " + fooddays.get(i).calories);
                addTableRow(foodtable, fooddays.get(i));
            }
        }




        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.foodday_dialog,null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                fromTimeEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                fromTimeEtxt.setInputType(InputType.TYPE_NULL);
                fromTimeEtxt.requestFocus();
                setTimeField();
                fromTimeEtxt.setText(timeFormatter.format(Calendar.getInstance().getTime()));
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) getView().findViewById(R.id.EditTextDate);
                        String date = datetext.getText().toString();
                        Date datedb = Calendar.getInstance().getTime(); //initialize
                        try {
                            datedb = dateFormatter.parse(date);
                        } catch (ParseException e){

                        }

                        String dateindb=datedbFormatter.format(datedb);

                        final EditText timetext = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                        String time = timetext.getText().toString();
                        Date timedb = Calendar.getInstance().getTime(); //initialize
                        try {
                            timedb = timeFormatter.parse(time);
                        } catch (ParseException e){ }


                        String timeindb=timedbFormatter.format(timedb);


                        final EditText foodtext = (EditText) dialoglayout.findViewById(R.id.EditTextFood);
                        String food = foodtext.getText().toString();

                        final EditText foodCalories = (EditText) dialoglayout.findViewById(R.id.EditTextFoodCalories);;
                        String calories = foodCalories.getText().toString();

                        FoodTime ftime = new FoodTime();

                        ftime.date_id=Integer.parseInt(dateindb);
                        ftime.time=Integer.parseInt(timeindb);
                        ftime.FoodName=food;
                        ftime.calories=Double.parseDouble(calories);
                        Log.d("TAG",dateindb+" "+timeindb+ " "+food+" "+calories);
                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                        boolean unique=true;
                        for(int i=0;i<fooddays.size();i++){
                            if(fooddays.get(i).time==Integer.parseInt(timeindb) && fooddays.get(i).date_id==Integer.parseInt(dateindb))
                                unique=false;
                        }
                        if(unique) {
                            addFoodTime(cb, ftime);
                            refreshTable();
                            foodtext.setText("");
                            foodCalories.setText("");
                            dialog.dismiss();
                        }
                        else{
                            final AlertDialog d = new AlertDialog.Builder(getContext())
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setMessage( "同一個時間不能一直輸入食物哦" )
                                    .setTitle("吃太多了吧")
                                    .create();
                            d.show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.setView(dialoglayout).create();
                dialog.show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addTableRow(TableLayout tl, final FoodTime fooddata){
        int time = fooddata.time;
        String food = fooddata.FoodName;
        Double calories = fooddata.calories;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row, tl, false);

        // Add First Column
        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Calendar dcal= Calendar.getInstance();
        dcal.set(dcal.get(Calendar.YEAR), dcal.get(Calendar.MONTH), dcal.get(Calendar.DAY_OF_MONTH),time/100,time%100);
        Name.setText(timeFormatter.format(dcal.getTime()));

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(food);

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(calories.toString());


        final int dtime= time;
        final String s = "確定刪除"+dtime/100+":"+ dtime%100+"的"+food+"嗎？";
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.foodday_dialog,null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                fromTimeEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                fromTimeEtxt.setInputType(InputType.TYPE_NULL);
                fromTimeEtxt.requestFocus();
                setTimeField();
                Calendar cl=Calendar.getInstance();
                cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH),fooddata.time/100,fooddata.time%100);
                fromTimeEtxt.setText(timeFormatter.format(cl.getTime()));
                EditText oldfoodtext = (EditText) dialoglayout.findViewById(R.id.EditTextFood);
                EditText oldfoodCalories = (EditText) dialoglayout.findViewById(R.id.EditTextFoodCalories);
                oldfoodtext.setText(fooddata.FoodName);
                oldfoodCalories.setText(fooddata.calories.toString());

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) getView().findViewById(R.id.EditTextDate);
                        String date = datetext.getText().toString();
                        Date datedb = Calendar.getInstance().getTime(); //initialize
                        try {
                            datedb = dateFormatter.parse(date);
                        } catch (ParseException e){

                        }

                        String dateindb=datedbFormatter.format(datedb);

                        final EditText timetext = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                        String time = timetext.getText().toString();
                        Date timedb = Calendar.getInstance().getTime(); //initialize
                        //Log.d("TAG",time.toString());
                        Log.d("TAG",timedb.toString());
                        try {
                            timedb = timeFormatter.parse(time);
                        } catch (ParseException e){

                        }

                        Log.d("TAG",timedb.toString());

                        String timeindb=timedbFormatter.format(timedb);


                        final EditText foodtext = (EditText) dialoglayout.findViewById(R.id.EditTextFood);
                        String food = foodtext.getText().toString();

                        final EditText foodCalories = (EditText) dialoglayout.findViewById(R.id.EditTextFoodCalories);;
                        String calories = foodCalories.getText().toString();

                        FoodTime ftime = new FoodTime();

                        ftime.date_id=Integer.parseInt(dateindb);
                        ftime.time=Integer.parseInt(timeindb);
                        ftime.FoodName=food;
                        ftime.calories=Double.parseDouble(calories);
                        Log.d("TAG",dateindb+" "+timeindb+ " "+food+" "+calories);
                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                        boolean unique=true;
                        for(int i=0;i<fooddays.size();i++){
                            if(fooddays.get(i) != fooddata) {
                                if (fooddays.get(i).date_id == ftime.date_id && fooddays.get(i).time == ftime.time)
                                    unique = false;
                            }
                        }
                        if(unique) {
                            updateFoodTime(cb, ftime);
                            refreshTable();
                            foodtext.setText("");
                            foodCalories.setText("");
                        }
                        else{
                            final AlertDialog d = new AlertDialog.Builder(getContext())
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setMessage( "你這時間已經有紀錄過東西哦！" )
                                    .setTitle("重複了哦！")
                                    .create();
                            d.show();
                        }
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog d = builder.setView(dialoglayout).create();
                d.show();

            }

        });

        tr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog d = new AlertDialog.Builder(getContext())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteFoodTime(cb, fooddata);
                                refreshTable();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setMessage( s )
                        .setTitle("刪除" + dtime/100+":"+ dtime%100 )
                        .create();
                d.show();
                return false;
            }
        });


        tl.addView(tr);
    }



    private FoodTime addFoodTime(final CancerDatabase db, FoodTime time) {
        db.beginTransaction();
        try {
            db.foodTimeDao().insertFoodTime(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private FoodTime deleteFoodTime(final CancerDatabase db, FoodTime time) {
        db.beginTransaction();
        try {
            db.foodTimeDao().delete(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private FoodTime updateFoodTime(final CancerDatabase db, FoodTime time) {
        db.beginTransaction();
        try {
            db.foodTimeDao().updateFoodTime(time);
            db.setTransactionSuccessful();
            Log.d("TAG","success123");
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private void findViewsById() {
        tablelabel = (TextView) getActivity().findViewById(R.id.food_day_label);
        foodtable = (TableLayout) getActivity().findViewById(R.id.food_daytable);

        addFood=(Button)getView().findViewById(R.id.addFoodDay);
        fromDateEtxt = (EditText) getView().findViewById(R.id.EditTextDate);
    }

    private void setTimeField() {

        fromTimeEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromTimePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();

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

    private void setDateField() {
        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
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
                refreshTable();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshTable(){
        int count = foodtable.getChildCount();
        for (int i = 2; i < count; i++) {
            View child = foodtable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        fooddays.clear();
        fooddays=CancerDatabase.getInMemoryDatabase(getContext()).foodTimeDao().getAllFoodTime();
        for (int i = 0; i <fooddays.size(); i++) {
            if(Integer.parseInt(datedbFormatter.format(currentDateView)) == fooddays.get(i).date_id) {
                Log.d("TAG", fooddays.get(i).time + " " + fooddays.get(i).FoodName + " " + fooddays.get(i).calories);
                addTableRow(foodtable, fooddays.get(i));
            }
        }
    }

}