package com.example.huangyuwei.myapplication.mem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.ChemCure;
import com.example.huangyuwei.myapplication.database.PutCure;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.cb;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mem_cure_fragment_2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mem_cure_fragment_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mem_cure_fragment_2 extends Fragment {
    private static final String TAG = "mem_cure_fragment_2";
    private mem_cure_fragment_2 instance;
    private Context context;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat timedbFormatter;

    private TableLayout c_p_table;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    //private mem_cure_fragment_2.OnListFragmentInteractionListener mListener;
    private Button add_c_p;

    private Button btn_select_date, btn_confirm;
    private TextView dateText_s;
    private TextView dateText_e;
    private int nowYear, nowMonth, nowDay;
    private int mYear, mMonth, mDay;
    private Date currentDateView;

    private List<PutCure> PutCures;


    private OnFragmentInteractionListener mListener;

    public mem_cure_fragment_2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mem_cure_fragment_2.
     */
    // TODO: Rename and change types and number of parameters
    public static mem_cure_fragment_2 newInstance(String param1, String param2) {
        mem_cure_fragment_2 fragment = new mem_cure_fragment_2();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_cure_fragment_2, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.TAIWAN);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");
        timedbFormatter = new SimpleDateFormat("HHmmSS",Locale.TAIWAN);
        add_c_p=(Button)getView().findViewById(R.id.add_c_p);
        c_p_table = (TableLayout) getActivity().findViewById(R.id.c_p_daytable);
        currentDateView= Calendar.getInstance().getTime();
        PutCures= CancerDatabase.getInMemoryDatabase(getContext()).putCureDao().getAllPutCure();
        for (int i = 0; i <PutCures.size(); i++) {
//            if(Integer.parseInt(datedbFormatter.format(currentDateView)) == PutCures.get(i).date_id) {
                Log.i(TAG,  "1231   "+PutCures.get(i).time+" " +PutCures.get(i).start+" "+ PutCures.get(i).end+" "+PutCures.get(i).place);
                addTableRow(c_p_table, PutCures.get(i));
            //}
        }

        add_c_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.fragment_mem_cure_fragment_2_edit,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                dateText_s = (EditText) dialoglayout.findViewById(R.id.edt_start);
                dateText_s.setInputType(InputType.TYPE_NULL);
                dateText_s.requestFocus();
                dateText_s.setText(dateFormatter.format(Calendar.getInstance().getTime()));
                dateText_e = (EditText) dialoglayout.findViewById(R.id.edt_end);
                dateText_e.setInputType(InputType.TYPE_NULL);
                dateText_e.requestFocus();
                dateText_e.setText(dateFormatter.format(Calendar.getInstance().getTime()));
                setTimeField();

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText dateStart = (EditText) dialoglayout.findViewById(R.id.edt_start);
                        final EditText dateEnd = (EditText) dialoglayout.findViewById(R.id.edt_end);
                        String date_s = dateStart.getText().toString();
                        String date_e = dateEnd.getText().toString();
                        Date datedb_s = Calendar.getInstance().getTime(); //initialize
                        Date datedb_e = Calendar.getInstance().getTime(); //initialize

                        try {
                            datedb_s = dateFormatter.parse(date_s);
                            datedb_e = dateFormatter.parse(date_e);
                        } catch (ParseException e){

                        }

                        String dateindb_s=datedbFormatter.format(datedb_s);
                        String dateindb_e=datedbFormatter.format(datedb_e);

                        Date timedb = Calendar.getInstance().getTime();
                        String timeindb=timedbFormatter.format(timedb);
                        Log.i(TAG,  "1231   "+timeindb);


                        final CheckBox cb1 = (CheckBox) dialoglayout.findViewById(R.id.cb1);
                        final CheckBox cb2 = (CheckBox) dialoglayout.findViewById(R.id.cb2);
                        final CheckBox cb3 = (CheckBox) dialoglayout.findViewById(R.id.cb3);
                        final CheckBox cb4 = (CheckBox) dialoglayout.findViewById(R.id.cb4);
                        final CheckBox cb5 = (CheckBox) dialoglayout.findViewById(R.id.cb5);
                        final CheckBox cb6 = (CheckBox) dialoglayout.findViewById(R.id.cb6);
                        final CheckBox cb7 = (CheckBox) dialoglayout.findViewById(R.id.cb7);
                        final EditText edt1 = (EditText) dialoglayout.findViewById(R.id.edt1);

                        String data = "";
                        if(cb1.isChecked()){
                            data+=cb1.getText().toString();
                        }
                        if(cb2.isChecked()){
                            data+=cb2.getText().toString();
                        }
                        if(cb3.isChecked()){
                            data+=cb3.getText().toString();
                        }
                        if(cb4.isChecked()){
                            data+=cb4.getText().toString();
                        }
                        if(cb5.isChecked()){
                            data+=cb5.getText().toString();
                        }
                        if(cb6.isChecked()){
                            data+=cb6.getText().toString();
                        }
                        if(cb7.isChecked()){
                            data+=edt1.getText().toString();
                        }

                        PutCure putCure = new PutCure();

                        putCure.start = Integer.parseInt(dateindb_s);
                        putCure.end = Integer.parseInt(dateindb_e);
                        putCure.time = Integer.parseInt(timeindb);
                        putCure.place = data;
                        Log.i(TAG,  "1231 save  "+putCure.time+" " +putCure.start+" "+ putCure.end+" "+putCure.place);
                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                        addPutCure(cb, putCure);
                        refreshTable();


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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private PutCure addPutCure(final CancerDatabase db, PutCure cure) {
        db.beginTransaction();
        try {
            db.putCureDao().insertPutCure(cure);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return cure;
    }

    private PutCure deletePutCure(final CancerDatabase db, PutCure cure) {
        db.beginTransaction();
        try {
            db.putCureDao().delete(cure);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return cure;
    }

    private PutCure updatePutCure(final CancerDatabase db, PutCure cure) {
        db.beginTransaction();
        try {
            db.putCureDao().updatePutCure(cure);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return cure;
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "."
                + String.valueOf(monthOfYear + 1) + "."
                + String.valueOf(dayOfMonth);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshTable(){
        int count = c_p_table.getChildCount();
        for (int i = 2; i < count; i++) {
            View child = c_p_table.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        PutCures.clear();
        PutCures=CancerDatabase.getInMemoryDatabase(getContext()).putCureDao().getAllPutCure();
        for (int i = 0; i <PutCures.size(); i++) {
            //if(Integer.parseInt(datedbFormatter.format(currentDateView)) == PutCures.get(i).date_id) {
                Log.i(TAG,  "1231   "+PutCures.get(i).time+" " +PutCures.get(i).start+" "+ PutCures.get(i).end+" "+PutCures.get(i).place);
                addTableRow(c_p_table, PutCures.get(i));
            }
        //}
    }

    private void setTimeField() {
        dateText_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                nowYear = c.get(Calendar.YEAR);
                nowMonth = c.get(Calendar.MONTH);
                nowDay = c.get(Calendar.DAY_OF_MONTH);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mYear = year;
                            mMonth = month;
                            mDay = dayOfMonth;
                            String format = setDateFormat(year,month,dayOfMonth);
                            dateText_s.setText(format);
                        }
                    }, nowYear,nowMonth, nowDay).show();
                }
            }
        });
        dateText_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                nowYear = c.get(Calendar.YEAR);
                nowMonth = c.get(Calendar.MONTH);
                nowDay = c.get(Calendar.DAY_OF_MONTH);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mYear = year;
                            mMonth = month;
                            mDay = dayOfMonth;
                            String format = setDateFormat(year,month,dayOfMonth);
                            dateText_e.setText(format);
                        }
                    }, nowYear,nowMonth, nowDay).show();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addTableRow(TableLayout tl, final PutCure cdata){
        int date_s = cdata.start;
        int date_e = cdata.end;
        String place = cdata.place;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row, tl, false);

        // Add First Column
        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Calendar dcal= Calendar.getInstance();
        dcal.set(dcal.get(Calendar.YEAR), dcal.get(Calendar.MONTH), dcal.get(Calendar.DAY_OF_MONTH));
        Name.setText(place);

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(String.valueOf(date_s));

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(String.valueOf(date_e));


        //final int dtime= time;
        //final String s = "確定刪除"+dtime/100+":"+ dtime%100+"的"+food+"嗎？";
        //********************
//        tr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater inflater = LayoutInflater.from(getActivity());
//                final View dialoglayout = inflater.inflate(R.layout.foodday_dialog,null);
////layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
//
//                //Building dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//
//                fromTimeEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
//                fromTimeEtxt.setInputType(InputType.TYPE_NULL);
//                fromTimeEtxt.requestFocus();
//                setTimeField();
//                Calendar cl=Calendar.getInstance();
//                cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH),fooddata.time/100,fooddata.time%100);
//                fromTimeEtxt.setText(timeFormatter.format(cl.getTime()));
//                EditText oldfoodtext = (EditText) dialoglayout.findViewById(R.id.EditTextFood);
//                EditText oldfoodCalories = (EditText) dialoglayout.findViewById(R.id.EditTextFoodCalories);
//                oldfoodtext.setText(fooddata.FoodName);
//                oldfoodCalories.setText(fooddata.calories.toString());
//
//                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        final EditText datetext = (EditText) getView().findViewById(R.id.EditTextDate);
//                        String date = datetext.getText().toString();
//                        Date datedb = Calendar.getInstance().getTime(); //initialize
//                        try {
//                            datedb = dateFormatter.parse(date);
//                        } catch (ParseException e){
//
//                        }
//
//                        String dateindb=datedbFormatter.format(datedb);
//
//                        final EditText timetext = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
//                        String time = timetext.getText().toString();
//                        Date timedb = Calendar.getInstance().getTime(); //initialize
//                        try {
//                            timedb = timeFormatter.parse(time);
//                        } catch (ParseException e){
//
//                        }
//
//
//                        String timeindb=timedbFormatter.format(timedb);
//
//
//                        final EditText foodtext = (EditText) dialoglayout.findViewById(R.id.EditTextFood);
//                        String food = foodtext.getText().toString();
//
//                        final EditText foodCalories = (EditText) dialoglayout.findViewById(R.id.EditTextFoodCalories);;
//                        String calories = foodCalories.getText().toString();
//
//                        FoodTime ftime = new FoodTime();
//
//                        ftime.date_id=Integer.parseInt(dateindb);
//                        ftime.time=Integer.parseInt(timeindb);
//                        ftime.FoodName=food;
//                        ftime.calories=Double.parseDouble(calories);
//                        Log.d("TAG",dateindb+" "+timeindb+ " "+food+" "+calories);
//                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
//                        boolean unique=true;
//                        for(int i=0;i<fooddays.size();i++){
//                            if(fooddays.get(i) != fooddata) {
//                                if (fooddays.get(i).date_id == ftime.date_id && fooddays.get(i).time == ftime.time)
//                                    unique = false;
//                            }
//                        }
//                        if(unique) {
//                            updateFoodTime(cb, ftime);
//                            refreshTable();
//                            foodtext.setText("");
//                            foodCalories.setText("");
//                        }
//                        else{
//                            final AlertDialog d = new AlertDialog.Builder(getContext())
//                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .setMessage( "你這時間已經有紀錄過東西哦！" )
//                                    .setTitle("重複了哦！")
//                                    .create();
//                            d.show();
//                        }
//                        dialog.dismiss();
//
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog d = builder.setView(dialoglayout).create();
//                d.show();
//
//            }
//
//        });
//
//        tr.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                final AlertDialog d = new AlertDialog.Builder(getContext())
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                deleteFoodTime(cb, fooddata);
//                                refreshTable();
//                            }
//                        })
//                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .setMessage( s )
//                        .setTitle("刪除" + dtime/100+":"+ dtime%100 )
//                        .create();
//                d.show();
//                return false;
//            }
//        });


        tl.addView(tr);
    }
}
