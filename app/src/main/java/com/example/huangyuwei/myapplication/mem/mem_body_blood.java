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
import com.example.huangyuwei.myapplication.database.BloodTime;
import com.example.huangyuwei.myapplication.database.CancerDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.cb;

public class mem_body_blood extends Fragment {

    private EditText fromDateEtxt;


    private TableLayout bloodtable;
    private Button addBlood;

    private DatePickerDialog fromDatePickerDialog;
    private Date currentDateView;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;

    private List<BloodTime> blooddays;


    public mem_body_blood() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        return inflater.inflate(R.layout.fragment_mem_body_blood, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");

        findViewsById();

        blooddays= CancerDatabase.getInMemoryDatabase(getContext()).bloodTimeDao().getAllBloodTime();
        currentDateView= Calendar.getInstance().getTime();
        for (int i = 0; i <blooddays.size(); i++) {
            addTableRow(bloodtable, blooddays.get(i));
        }

        addBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.bloodday_dialog,null);


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

                        final EditText bloodWBC = (EditText) dialoglayout.findViewById(R.id.EditTextWBC);
                        String WBC = bloodWBC.getText().toString();

                        final EditText bloodHB = (EditText) dialoglayout.findViewById(R.id.EditTextHB);;
                        String HB = bloodHB.getText().toString();

                        final EditText bloodPLT = (EditText) dialoglayout.findViewById(R.id.EditTextPLT);;
                        String PLT = bloodPLT.getText().toString();

                        BloodTime btime = new BloodTime();

                        btime.date_id=Integer.parseInt(dateindb);
                        btime.WBC=Double.parseDouble(WBC);
                        btime.HB=Double.parseDouble(HB);
                        btime.PLT=PLT;
                        Log.d("TAG",dateindb+" "+WBC+" "+HB+" "+PLT);

                        boolean unique=true;
                        for(int i=0;i<blooddays.size();i++){
                            if(blooddays.get(i).date_id==Integer.parseInt(dateindb))
                                unique=false;
                        }
                        if(unique) {
                            addBloodTime(cb, btime);
                            refreshTable();
                            bloodWBC.setText("");
                            bloodHB.setText("");
                            bloodPLT.setText("");
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
    private void addTableRow(TableLayout tl, final BloodTime blooddata){
        final Integer date = blooddata.date_id;
        String WBC = blooddata.WBC.toString();
        String HB = blooddata.HB.toString();
        String PLT=blooddata.PLT;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row_4, tl, false);


        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Name.setText(date.toString());

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(WBC);

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(HB);

        TextView birth = (TextView)tr.findViewById(R.id.birth);
        birth.setText(PLT);

        final int dtime= date;
        final String s = "確定刪除"+date+"的心情嗎？";
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.bloodday_dialog,null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                fromDateEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
                fromDateEtxt.setInputType(InputType.TYPE_NULL);
                fromDateEtxt.requestFocus();
                setDateField();

                fromDateEtxt.setText(String.valueOf(date));
                EditText oldWBC = (EditText) dialoglayout.findViewById(R.id.EditTextWBC);
                EditText oldHB = (EditText) dialoglayout.findViewById(R.id.EditTextHB);
                EditText oldPLT = (EditText) dialoglayout.findViewById(R.id.EditTextPLT);
                oldWBC.setText(String.valueOf(blooddata.WBC));
                oldHB.setText(String.valueOf(blooddata.HB));
                oldPLT.setText(blooddata.PLT);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) dialoglayout.findViewById(R.id.EditTextDate);
                        String date = datetext.getText().toString();

                        final EditText bloodWBC = (EditText) dialoglayout.findViewById(R.id.EditTextWBC);
                        String WBC = bloodWBC.getText().toString();

                        final EditText bloodHB = (EditText) dialoglayout.findViewById(R.id.EditTextHB);;
                        String HB = bloodHB.getText().toString();

                        final EditText bloodPLT = (EditText) dialoglayout.findViewById(R.id.EditTextPLT);;
                        String PLT = bloodPLT.getText().toString();

                        BloodTime btime = new BloodTime();

                        btime.date_id=Integer.parseInt(date);
                        btime.WBC=Double.parseDouble(WBC);
                        btime.HB=Double.parseDouble(HB);
                        btime.PLT=PLT;


                        boolean unique=true;
                        for(int i=0;i<blooddays.size();i++){
                            if(blooddays.get(i) != blooddata) {
                                if (blooddays.get(i).date_id == btime.date_id )
                                    unique = false;
                            }
                        }
                        if(unique) {
                            updateBloodTime(cb, btime);
                            refreshTable();
                            bloodWBC.setText("");
                            bloodHB.setText("");
                            bloodPLT.setText("");
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
                                deleteBloodTime(cb, blooddata);
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


    private BloodTime addBloodTime(final CancerDatabase db, BloodTime time) {
        db.beginTransaction();
        try {
            db.bloodTimeDao().insertBloodTime(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private BloodTime deleteBloodTime(final CancerDatabase db, BloodTime time) {
        db.beginTransaction();
        try {
            db.bloodTimeDao().delete(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private BloodTime updateBloodTime(final CancerDatabase db, BloodTime time) {
        db.beginTransaction();
        try {
            db.bloodTimeDao().updateBloodTime(time);
            db.setTransactionSuccessful();
            Log.d("TAG","success123");
        } finally {
            db.endTransaction();
        }
        return time;
    }


    private void findViewsById() {
        bloodtable = (TableLayout) getActivity().findViewById(R.id.blood_daytable);
        addBlood=(Button)getView().findViewById(R.id.addBloodDay);

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
        int count = bloodtable.getChildCount();
        //Log.d("TAG",String.valueOf(count));
        for (int i = 1; i < count; i++) {
            View child = bloodtable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        blooddays.clear();
        blooddays=CancerDatabase.getInMemoryDatabase(getContext()).bloodTimeDao().getAllBloodTime();
        for (int i = 0; i <blooddays.size(); i++) {
            addTableRow(bloodtable, blooddays.get(i));
        }
    }

}
