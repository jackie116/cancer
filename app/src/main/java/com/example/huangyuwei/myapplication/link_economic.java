package com.example.huangyuwei.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class link_economic extends Fragment {



    public link_economic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_link_economic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        TableLayout ll = (TableLayout) getView().findViewById(R.id.WigTable);
//        TableLayout bl = (TableLayout) getView().findViewById(R.id.BraTable);
//        for (int i = 0; i <2; i++) {
//            addTableRow(ll,"C.Y.S假髮","免費服務專線：0800-450-450","有台北、新北、桃園、新竹、台中、台南、高雄等多家分店");
//        }
//        for (int i = 0; i <2; i++) {
//            addTableRow(bl,"C.Y.S假髮","免費服務專線：0800-450-450","有台北、新北、桃園、新竹、台中、台南、高雄等多家分店");
//        }

    }

    private void addTableRow(TableLayout tl, String name, String phone, String address){

        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row, tl, false);

        // Add First Column
        TextView Name = (TextView)tr.findViewById(R.id.Name);
        Name.setText(name);

        // Add the 3rd Column
        TextView Phone = (TextView)tr.findViewById(R.id.Phone);
        Phone.setText(phone);

        TextView Address = (TextView)tr.findViewById(R.id.Address);
        Address.setText(address);

        tl.addView(tr);
    }



}