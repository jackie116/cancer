package com.example.huangyuwei.myapplication.mem;

import android.support.v4.app.Fragment;


public class mem_body_tmp extends Fragment {
/*
    private EditText fromDateEtxt;
    private EditText fromTimeEtxt;
    private TextView tablelabel;
    private TableLayout tmptable;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat timedbFormatter;

    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog fromTimePickerDialog;

    private Date currentDateView;
    private Button addTmp;

    private List<TmpTime> tmpdays;

    public mem_body_tmp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_body_tmp, container, false);
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
        tmpdays= CancerDatabase.getInMemoryDatabase(getContext()).tmpTimeDao().getAllTmpTime();
        currentDateView=Calendar.getInstance().getTime();
        for (int i = 0; i <tmpdays.size(); i++) {
            if(Integer.parseInt(datedbFormatter.format(currentDateView)) == tmpdays.get(i).date_id) {
                Log.d("TAG", tmpdays.get(i).date + " " + tmpdays.get(i).time + " " + tmpdays.get(i).degree );
                addTableRow(tmptable, tmpdays.get(i));
            }
        }




        addTmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.tmpday_dialog,null);
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
                        } catch (ParseException e){

                        }


                        String timeindb=timedbFormatter.format(timedb);

                        final EditText tmpdegree = (EditText) dialoglayout.findViewById(R.id.EditTextDegree);;
                        String degree = tmpdegree.getText().toString();

                        TmpTime ftime = new TmpTime();

                        ftime.date_id=Integer.parseInt(dateindb);
                        ftime.date=Integer.parseInt(timeindb);
                        ftime.time=Integer.parseInt(timeindb);
                        ftime.degree=Double.parseDouble(degree);
                        Log.d("TAG",dateindb+" "+timeindb+ " " +degree);
                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                        boolean unique=true;
                        for(int i=0;i<tmpdays.size();i++){
                            if(tmpdays.get(i).time==Integer.parseInt(timeindb) && tmpdays.get(i).date_id==Integer.parseInt(dateindb))
                                unique=false;
                        }
                        if(unique) {
                            addTmpTime(cb, ftime);
                            refreshTable();
                            timetext.setText("");
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
    private void addTableRow(TableLayout tl, final TmpTime tmpdata){
        int date = tmpdata.date;
        int time = tmpdata.time;
        Double degree = tmpdata.degree;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row, tl, false);

        // Add First Column
        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Calendar dcal= Calendar.getInstance();
        dcal.set(dcal.get(Calendar.YEAR), dcal.get(Calendar.MONTH), dcal.get(Calendar.DAY_OF_MONTH),time/100,time%100);
        Name.setText(timeFormatter.format(dcal.getTime()));

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(time);

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(degree.toString());


        final int dtime= time;
        final String s = "確定刪除"+dtime/100+":"+ dtime%100+"的"+degree+"嗎？";
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.tmpday_dialog,null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                fromTimeEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                fromTimeEtxt.setInputType(InputType.TYPE_NULL);
                fromTimeEtxt.requestFocus();
                setTimeField();
                Calendar cl=Calendar.getInstance();
                cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH),tmpdata.time/100,tmpdata.time%100);
                fromTimeEtxt.setText(timeFormatter.format(cl.getTime()));
                EditText oldtmptext = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                EditText oldtmpdegree = (EditText) dialoglayout.findViewById(R.id.EditTextDegree);
                oldtmptext.setText(tmpdata.time);

                oldtmpdegree.setText(tmpdata.degree.toString());

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
                        } catch (ParseException e){

                        }


                        String timeindb=timedbFormatter.format(timedb);


                        final EditText tmptext = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                       // String tmptime= tmptext.getText().toString();

                        final EditText tmpdegree= (EditText) dialoglayout.findViewById(R.id.EditTextDegree);;
                        String degree = tmpdegree.getText().toString();

                        TmpTime ftime = new TmpTime();

                        ftime.date_id=Integer.parseInt(dateindb);
                        ftime.time=Integer.parseInt(timeindb);
                        ftime.date=Integer.parseInt(dateindb);
                        ftime.degree=Double.parseDouble(degree);
                        Log.d("TAG",dateindb+" "+timeindb+" "+degree);
                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                        boolean unique=true;
                        for(int i=0;i<tmpdays.size();i++){
                            if(tmpdays.get(i) != tmpdata) {
                                if (tmpdays.get(i).date_id == ftime.date_id && tmpdays.get(i).time == ftime.time)
                                    unique = false;
                            }
                        }
                        if(unique) {
                            updateFoodTime(cb, ftime);
                            refreshTable();
                            tmptext.setText("");
                            tmpdegree.setText("");
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
                        .setTitle("刪除" + dtime/100+":"+ dtime%100 )
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

    private TmpTime updateFoodTime(final CancerDatabase db, TmpTime time) {
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
        tablelabel = (TextView) getActivity().findViewById(R.id.tmp_date_label);
        tmptable = (TableLayout) getActivity().findViewById(R.id.tmp_daytable);
        // saveFood=(Button)getView().findViewById(R.id.saveFoodDay);
        addTmp=(Button)getView().findViewById(R.id.addTmpDay);
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
        int count = tmptable.getChildCount();
        for (int i = 2; i < count; i++) {
            View child = tmptable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        tmpdays.clear();
        tmpdays=CancerDatabase.getInMemoryDatabase(getContext()).tmpTimeDao().getAllTmpTime();
        for (int i = 0; i <tmpdays.size(); i++) {
            if(Integer.parseInt(datedbFormatter.format(currentDateView)) == tmpdays.get(i).date_id) {
                Log.d("TAG", tmpdays.get(i).date + " " + tmpdays.get(i).time + " " + tmpdays.get(i).degree);
                addTableRow(tmptable, tmpdays.get(i));
            }
        }
    }

*/
}