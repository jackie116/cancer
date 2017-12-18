package com.example.huangyuwei.myapplication.mem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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
import com.example.huangyuwei.myapplication.database.TmpTime;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.cb;


public class mem_body_tmp extends Fragment {
    /*

    private EditText fromDateEtxt;
    private EditText fromTimeEtxt;


    private TableLayout tmptable;
    private Button addTmp;

    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog fromTimePickerDialog;
    private Date currentDateView;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;
    private android.icu.text.SimpleDateFormat timeFormatter;
    private android.icu.text.SimpleDateFormat timedbFormatter;

    private List<TmpTime> tmpdays;

    public mem_body_tmp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_mood_all, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        timedbFormatter = new SimpleDateFormat("HHmm",Locale.TAIWAN);

        findViewsById();

        //fromDateEtxt.setInputType(InputType.TYPE_NULL);
        //fromDateEtxt.requestFocus();
        //setDateField();
        //fromDateEtxt.setText(dateFormatter.format(Calendar.getInstance().getTime()));

        tmpdays=CancerDatabase.getInMemoryDatabase(getContext()).tmpTimeDao().getAllTmpTime();
        currentDateView=Calendar.getInstance().getTime();
        for (int i = 0; i <tmpdays.size(); i++) {
            //if(Integer.parseInt(datedbFormatter.format(currentDateView)) == mooddays.get(i).date_id) {
            //    Log.d("TAG", mooddays.get(i).date_id + " " + mooddays.get(i).score + " " + mooddays.get(i).diary);
            addTableRow(tmptable, tmpdays.get(i));
            //}
        }

        addTmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.tmpday_dialog,null);


                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                fromDateEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
                fromDateEtxt.setInputType(InputType.TYPE_NULL);
                fromDateEtxt.requestFocus();
                setDateField();
                fromDateEtxt.setText(dateFormatter.format(Calendar.getInstance().getTime()));

                fromTimeEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                fromTimeEtxt.setInputType(InputType.TYPE_NULL);
                fromTimeEtxt.requestFocus();
                setTimeField();
                fromTimeEtxt.setText(timeFormatter.format(Calendar.getInstance().getTime()));

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
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
                        } catch (ParseException e){

                        }
                        String timeindb=timedbFormatter.format(timedb);


                        final EditText tmpdegree = (EditText) dialoglayout.findViewById(R.id.EditTextDegree);
                        String degree = tmpdegree.getText().toString();

                        TmpTime ttime = new TmpTime();

                        ttime.date_id=Integer.parseInt(dateindb);
                        ttime.time=Integer.parseInt(timeindb);
                        ttime.degree=Double.parseDouble(degree);
                        Log.d("TAG",dateindb+" "+timeindb+" "+degree);

                        boolean unique=true;
                        for(int i=0;i<tmpdays.size();i++){
                            if(tmpdays.get(i).time==Integer.parseInt(timeindb) && tmpdays.get(i).date_id==Integer.parseInt(dateindb))
                                unique=false;
                        }
                        if(unique) {
                            addTmpTime(cb, ttime);
                            refreshTable();
                            tmpdegree.setText("");

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
                                    .setMessage( "同一個時間不能一直輸溫度喔" )
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
    private void addTableRow(TableLayout tl, final TmpTime tmpdata){
        final Integer date = tmpdata.date_id;
        final Integer time =tmpdata.time;
        String degree = tmpdata.degree.toString();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row, tl, false);

        // Add First Column

        //TextView Name = (TextView)tr.findViewById(R.id.Name);
        //Calendar dcal= Calendar.getInstance();
        //dcal.set(dcal.get(Calendar.YEAR), dca
        // l.get(Calendar.MONTH), dcal.get(Calendar.DAY_OF_MONTH));
        //Name.setText(dateFormatter.format(dcal.getTime()));


        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Name.setText(date.toString());

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(time.toString());

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(degree);


        final int ttime= time;
        final String s = "確定刪除"+ttime/100+":"+ ttime%100+"的"+degree+"嗎？";
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.tmpday_dialog,null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                fromDateEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
                fromDateEtxt.setInputType(InputType.TYPE_NULL);
                fromDateEtxt.requestFocus();
                setDateField();
                //Calendar cl=Calendar.getInstance();
                //cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH));
                fromDateEtxt.setText(date.toString());
                EditText oldtmpdegree = (EditText) dialoglayout.findViewById(R.id.EditTextDegree);

                oldmoodscore.setText(mooddata.score.toString());
                oldmooddiary.setText(mooddata.diary);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
                        String date = datetext.getText().toString();
                        //Log.d("TAG",date.toString());
                        //Date datedb = Calendar.getInstance().getTime(); //initialize


                        //datedb=dateFormatter.parse(date);
                        //Log.d("TAG",datedb.toString());
                        // String dateindb=datedbFormatter.format(datedb);

                        //Log.d("TAG",dateindb.toString());

                        final EditText moodscore = (EditText) dialoglayout.findViewById(R.id.EditTextMood);
                        String mood = moodscore.getText().toString();

                        final EditText mooddiary = (EditText) dialoglayout.findViewById(R.id.EditTextDiary);;
                        String diary = mooddiary.getText().toString();

                        MoodTime mtime = new MoodTime();

                        mtime.date_id=Integer.parseInt(date);
                        mtime.score=Integer.parseInt(mood);
                        mtime.diary=diary;
                        Log.d("TAG",date+" "+mood+" "+diary);

                        boolean unique=true;
                        for(int i=0;i<mooddays.size();i++){
                            if(mooddays.get(i) != mooddata) {
                                if (mooddays.get(i).date_id == mtime.date_id )
                                    unique = false;
                            }
                        }
                        if(unique) {
                            updateTmpTime(cb, mtime);
                            refreshTable();
                            .setText("");
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
                                deleteTmpTime(cb, tmpdata);
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
                        .setTitle("刪除" )
                        .create();
                d.show();
                return false;
            }
        });


        tl.addView(tr);
    }


    private TmpTime addTmpTime(final CancerDatabase db, TmpTime time) {
        db.beginTransaction();
        try {
            db.tmpTimeDao().insertTmpTime(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private TmpTime deleteTmpTime(final CancerDatabase db, TmpTime time) {
        db.beginTransaction();
        try {
            db.tmpTimeDao().delete(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private TmpTime updateTmpTime(final CancerDatabase db, TmpTime time) {
        db.beginTransaction();
        try {
            db.tmpTimeDao().updateTmpTime(time);
            db.setTransactionSuccessful();
            Log.d("TAG","success123");
        } finally {
            db.endTransaction();
        }
        return time;
    }


    private void findViewsById() {
        tmptable = (TableLayout) getActivity().findViewById(R.id.tmp_daytable);
        addTmp=(Button)getView().findViewById(R.id.addTmpDay);

        //fromDateEtxt = (EditText) getView().findViewById(R.id.EditTextDate);
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
                refreshTable();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshTable(){
        int count = tmptable.getChildCount();
        //Log.d("TAG",String.valueOf(count));
        for (int i = 1; i < count; i++) {
            View child = tmptable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        tmpdays.clear();
        tmpdays= CancerDatabase.getInMemoryDatabase(getContext()).tmpTimeDao().getAllTmpTime();
        for (int i = 0; i <tmpdays.size(); i++) {
            //if(Integer.parseInt(datedbFormatter.format(currentDateView)) == mooddays.get(i).date_id) {
            //    Log.d("TAG", mooddays.get(i).date_id + " " + mooddays.get(i).score + " " + mooddays.get(i).diary);
            addTableRow(tmptable, tmpdays.get(i));
            //}
        }
    }

}

*/