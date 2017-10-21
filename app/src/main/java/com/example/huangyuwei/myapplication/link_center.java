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


public class link_center extends Fragment {



    public link_center() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_link_center, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TableLayout ll = (TableLayout) getView().findViewById(R.id.NorthTable);
        TableLayout cl = (TableLayout) getView().findViewById(R.id.CenterTable);
        TableLayout sl = (TableLayout) getView().findViewById(R.id.SouthTable);
        TableLayout el = (TableLayout) getView().findViewById(R.id.EastTable);
        for (int i = 0; i <2; i++) {
            addTableRow(ll,"三軍總醫院附設民眾診療服務處","(02)8792-3321 分機 10721","臺北市內湖區成功路二段325號 放射腫瘤部 ");
        }
        for (int i = 0; i <2; i++) {
            addTableRow(cl,"三軍總醫院附設民眾診療服務處","免費服務專線：0800-450-450","臺北市內湖區成功路二段325號 放射腫瘤部 ");
        }
        for (int i = 0; i <2; i++) {
            addTableRow(sl,"三軍總醫院附設民眾診療服務處","免費服務專線：0800-450-450","臺北市內湖區成功路二段325號 放射腫瘤部 ");
        }
        for (int i = 0; i <2; i++) {
            addTableRow(el,"三軍總醫院附設民眾診療服務處","免費服務專線：0800-450-450","臺北市內湖區成功路二段325號 放射腫瘤部 ");
        }
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