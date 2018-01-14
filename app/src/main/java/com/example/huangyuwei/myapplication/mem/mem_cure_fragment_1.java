package com.example.huangyuwei.myapplication.mem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TimePicker;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.ChemCure;
import com.example.huangyuwei.myapplication.database.FoodTime;
import com.example.huangyuwei.myapplication.mem.dummy.DummyContent;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.cb;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class mem_cure_fragment_1 extends Fragment {
    private static final String TAG = "mem_cure_fragment_1";
    private mem_cure_fragment_1 instance;
    private Context context;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat timedbFormatter;
    private TableLayout c_c_table;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    private OnListFragmentInteractionListener mListener;
    private Button add_c_c;

    private Button btn_select_date, btn_confirm;
    private TextView dateText;
    private int nowYear, nowMonth, nowDay;
    private int mYear, mMonth, mDay;
    private Date currentDateView;

    private List<ChemCure> ChemCures;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public mem_cure_fragment_1() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static mem_cure_fragment_1 newInstance(int columnCount) {
        mem_cure_fragment_1 fragment = new mem_cure_fragment_1();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mem_cure_fragment_1, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.TAIWAN);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");
        timedbFormatter = new SimpleDateFormat("HHmmSS",Locale.TAIWAN);
        add_c_c=(Button)getView().findViewById(R.id.addFoodDay);
        c_c_table = (TableLayout) getActivity().findViewById(R.id.c_c_daytable);
        currentDateView=Calendar.getInstance().getTime();
        ChemCures=CancerDatabase.getInMemoryDatabase(getContext()).chemCureDao().getAllChemCure();
        for (int i = 0; i <ChemCures.size(); i++) {
            //if(Integer.parseInt(datedbFormatter.format(currentDateView)) == ChemCures.get(i).date_id) {
                Log.d("TAG", ChemCures.get(i).date_id + " " + ChemCures.get(i).cure);
                addTableRow(c_c_table, ChemCures.get(i));
            //}
        }

        add_c_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.fragment_mem_cure_fragment_1_edit,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                dateText = (EditText) dialoglayout.findViewById(R.id.dateText);
                dateText.setInputType(InputType.TYPE_NULL);
                dateText.requestFocus();
                setTimeField();
                dateText.setText(dateFormatter.format(Calendar.getInstance().getTime()));

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) dialoglayout.findViewById(R.id.dateText);
                        String date = datetext.getText().toString();
                        Log.i(TAG,  "1231 dateTExt  "+date);
                        Date datedb = Calendar.getInstance().getTime(); //initialize
                        try {
                            datedb = dateFormatter.parse(date);
                            Log.i(TAG,  "1231 dateTdb  "+datedb);

                        } catch (ParseException e){
                            e.getErrorOffset();
                        }
                        Log.i(TAG,  "1231 dateTdb  "+datedb);

                        String dateindb=datedbFormatter.format(datedb);
                        Log.i(TAG,  "1231 dateTindb  "+dateindb);

                        Date timedb = Calendar.getInstance().getTime();
                        String timeindb=timedbFormatter.format(timedb);
                        Log.i(TAG,  "1231   "+timeindb);
                        final CheckBox cb1 = (CheckBox) dialoglayout.findViewById(R.id.checkBox1);
                        final CheckBox cb2 = (CheckBox) dialoglayout.findViewById(R.id.checkBox2);
                        final CheckBox cb3 = (CheckBox) dialoglayout.findViewById(R.id.checkBox3);
                        final CheckBox cb4 = (CheckBox) dialoglayout.findViewById(R.id.checkBox4);

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

                        ChemCure chemCure = new ChemCure();

                        chemCure.date_id = Integer.parseInt(dateindb);
                        chemCure.time = Integer.parseInt(timeindb);
                        chemCure.cure = data;
                        Log.i(TAG,  "1231 save   "+chemCure.date_id+" " +chemCure.time+" "+ chemCure.cure);
                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                        addChemCure(cb, chemCure);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addTableRow(TableLayout tl, final ChemCure cdata){
        int date_id = cdata.date_id;
        String cure = cdata.cure;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row2, tl, false);

        // Add First Column
        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Calendar dcal= Calendar.getInstance();
        dcal.set(dcal.get(Calendar.YEAR), dcal.get(Calendar.MONTH), dcal.get(Calendar.DAY_OF_MONTH));
        Name.setText(dateFormatter.format(dcal.getTime()));

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(cure);


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

    private void setTimeField() {
        dateText.setOnClickListener(new View.OnClickListener() {
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
                            dateText.setText(format);
                        }
                    }, nowYear,nowMonth, nowDay).show();
                }
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ChemCure item);
    }

    public void refreshList(){
        List<ChemCure> list = CancerDatabase.getInMemoryDatabase(context).chemCureDao().getAllChemCure();

    }
    private ChemCure addChemCure(final CancerDatabase db, ChemCure cure) {
        db.beginTransaction();
        try {
            db.chemCureDao().insertChemCure(cure);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return cure;
    }

    private ChemCure deleteChemCure(final CancerDatabase db, ChemCure cure) {
        db.beginTransaction();
        try {
            db.chemCureDao().delete(cure);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return cure;
    }

    private ChemCure updateChemCure(final CancerDatabase db, ChemCure cure) {
        db.beginTransaction();
        try {
            db.chemCureDao().updateChemCure(cure);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return cure;
    }
    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshTable(){
        int count = c_c_table.getChildCount();
        for (int i = 2; i < count; i++) {
            View child = c_c_table.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        ChemCures.clear();
        ChemCures=CancerDatabase.getInMemoryDatabase(getContext()).chemCureDao().getAllChemCure();
        for (int i = 0; i <ChemCures.size(); i++) {
            //if(Integer.parseInt(datedbFormatter.format(currentDateView)) == ChemCures.get(i).date_id) {
                Log.d("TAG", ChemCures.get(i).date_id + " " + ChemCures.get(i).cure);
                addTableRow(c_c_table, ChemCures.get(i));
            //}
        }
    }
}
