package com.example.huangyuwei.myapplication.mem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.WeightTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.cb;


public class mem_body_weight extends Fragment {

    private EditText fromDateEtxt;


    private TableLayout weighttable;
    private Button addWeight;

    private DatePickerDialog fromDatePickerDialog;
    private Date currentDateView;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;

    private List<WeightTime> weightdays;

    public mem_body_weight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_body_weight, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");

        findViewsById();

        weightdays= CancerDatabase.getInMemoryDatabase(getContext()).weightTimeDao().getAllWeightTime();
        currentDateView= Calendar.getInstance().getTime();
        for (int i = 0; i <weightdays.size(); i++) {
            addTableRow(weighttable, weightdays.get(i));
        }

        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.weightday_dialog,null);


                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                fromDateEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
                fromDateEtxt.setInputType(InputType.TYPE_NULL);
                fromDateEtxt.requestFocus();
                setDateField();
                fromDateEtxt.setText(dateFormatter.format(Calendar.getInstance().getTime()));

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

                        final EditText weightkg = (EditText) dialoglayout.findViewById(R.id.EditTextKg);
                        String kg = weightkg.getText().toString();

                        final EditText weightbmi = (EditText) dialoglayout.findViewById(R.id.EditTextBmi);;
                        String bmi = weightbmi.getText().toString();

                        final EditText weightresult = (EditText) dialoglayout.findViewById(R.id.EditTextResult);;
                        String result = weightresult.getText().toString();

                        WeightTime wtime = new WeightTime();

                        wtime.date_id=Integer.parseInt(dateindb);
                        wtime.kg=Double.parseDouble(kg);
                        wtime.bmi=Double.parseDouble(bmi);
                        wtime.result=result;
                        Log.d("TAG",dateindb+" "+kg+" "+bmi+" "+result);
                        /*/add
                        addMoodTime(cb, mtime);
                        refreshTable();
                        moodtext.setText("");
                        moodDiary.setText("");
                        dialog.dismiss();
                        */
                        boolean unique=true;
                        for(int i=0;i<weightdays.size();i++){
                            if(weightdays.get(i).date_id==Integer.parseInt(dateindb))
                                unique=false;
                        }
                        if(unique) {
                            addWeightTime(cb, wtime);
                            refreshTable();
                            weightkg.setText("");
                            weightbmi.setText("");
                            weightresult.setText("");
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
                                    .setMessage( "同一天只能輸一次喔" )
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
    private void addTableRow(TableLayout tl, final WeightTime weightdata){
        final Integer date = weightdata.date_id;
        String kg = weightdata.kg.toString();
        String bmi = weightdata.bmi.toString();
        String result=weightdata.result;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row_4, tl, false);

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
        Phone.setText(kg);

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(bmi);

        TextView birth = (TextView)tr.findViewById(R.id.birth);
        birth.setText(result);

        final int dtime= date;
        final String s = "確定刪除"+date+"的心情嗎？";
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.weightday_dialog,null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                fromDateEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
                fromDateEtxt.setInputType(InputType.TYPE_NULL);
                fromDateEtxt.requestFocus();
                setDateField();
                //Calendar cl=Calendar.getInstance();
                //cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH));
                fromDateEtxt.setText(String.valueOf(date));
                EditText oldkg = (EditText) dialoglayout.findViewById(R.id.EditTextKg);
                EditText oldbmi = (EditText) dialoglayout.findViewById(R.id.EditTextBmi);
                EditText oldresult = (EditText) dialoglayout.findViewById(R.id.EditTextResult);
                oldkg.setText(String.valueOf(weightdata.kg));
                oldbmi.setText(String.valueOf(weightdata.bmi));
                oldresult.setText(weightdata.result);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
                        String date = datetext.getText().toString();
                        //Log.d("TAG",date.toString());
                        //Date datedb = Calendar.getInstance().getTime(); //initialize

                        /*
                        try {
                            datedb = dateFormatter.parse(date);
                        } catch (ParseException e){

                        }

                        */
                        //datedb=dateFormatter.parse(date);
                        //Log.d("TAG",datedb.toString());
                        // String dateindb=datedbFormatter.format(datedb);

                        //Log.d("TAG",dateindb.toString());

                        final EditText weightkg = (EditText) dialoglayout.findViewById(R.id.EditTextKg);
                        String kg = weightkg.getText().toString();

                        final EditText weightbmi = (EditText) dialoglayout.findViewById(R.id.EditTextBmi);;
                        String bmi = weightbmi.getText().toString();

                        final EditText weightresult = (EditText) dialoglayout.findViewById(R.id.EditTextResult);;
                        String result = weightresult.getText().toString();

                        WeightTime wtime = new WeightTime();

                        wtime.date_id=Integer.parseInt(date);
                        wtime.kg=Double.parseDouble(kg);
                        wtime.bmi=Double.parseDouble(bmi);
                        wtime.result=result;


                        //Log.d("TAG",date+" "+mood+" "+diary);
                        /*/update
                        updateMoodTime(cb, mtime);
                        refreshTable();
                        moodscore.setText("");
                        mooddiary.setText("");

                        */
                        boolean unique=true;
                        for(int i=0;i<weightdays.size();i++){
                            if(weightdays.get(i) != weightdata) {
                                if (weightdays.get(i).date_id == wtime.date_id )
                                    unique = false;
                            }
                        }
                        if(unique) {
                            updateWeightTime(cb, wtime);
                            refreshTable();
                            weightkg.setText("");
                            weightbmi.setText("");
                            weightresult.setText("");
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
                                deleteWeightTime(cb, weightdata);
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


    private WeightTime addWeightTime(final CancerDatabase db, WeightTime time) {
        db.beginTransaction();
        try {
            db.weightTimeDao().insertWeightTime(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private WeightTime deleteWeightTime(final CancerDatabase db, WeightTime time) {
        db.beginTransaction();
        try {
            db.weightTimeDao().delete(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private WeightTime updateWeightTime(final CancerDatabase db, WeightTime time) {
        db.beginTransaction();
        try {
            db.weightTimeDao().updateWeightTime(time);
            db.setTransactionSuccessful();
            Log.d("TAG","success123");
        } finally {
            db.endTransaction();
        }
        return time;
    }


    private void findViewsById() {
        weighttable = (TableLayout) getActivity().findViewById(R.id.weight_daytable);
        addWeight=(Button)getView().findViewById(R.id.addWeightDay);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshTable(){
        int count = weighttable.getChildCount();
        //Log.d("TAG",String.valueOf(count));
        for (int i = 1; i < count; i++) {
            View child = weighttable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        weightdays.clear();
        weightdays=CancerDatabase.getInMemoryDatabase(getContext()).weightTimeDao().getAllWeightTime();
        for (int i = 0; i <weightdays.size(); i++) {
            addTableRow(weighttable, weightdays.get(i));
        }
    }

}
