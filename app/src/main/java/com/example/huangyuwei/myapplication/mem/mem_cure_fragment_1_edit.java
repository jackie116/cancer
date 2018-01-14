package com.example.huangyuwei.myapplication.mem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mem_cure_fragment_1_edit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mem_cure_fragment_1_edit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mem_cure_fragment_1_edit extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btn_select_date;
    private TextView dateText;

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;

    private int nowYear, nowMonth, nowDay;
    private int mYear, mMonth, mDay;

    private OnFragmentInteractionListener mListener;

    public mem_cure_fragment_1_edit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mem_cure_fragment_1_edit.
     */
    // TODO: Rename and change types and number of parameters
    public static mem_cure_fragment_1_edit newInstance(String param1, String param2) {
        mem_cure_fragment_1_edit fragment = new mem_cure_fragment_1_edit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mem_cure_fragment_1_edit, container, false);
        dateText = (TextView) view.findViewById(R.id.dateText);
        checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) view.findViewById(R.id.checkBox4);
        //btn_select_date = (Button) view.findViewById(R.id.btn_cure_1_date);
        btn_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                nowYear = c.get(Calendar.YEAR);
                nowMonth = c.get(Calendar.MONTH);
                nowDay = c.get(Calendar.DAY_OF_MONTH);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "."
                + String.valueOf(monthOfYear + 1) + "."
                + String.valueOf(dayOfMonth);
    }

}
