package com.example.huangyuwei.myapplication.mem;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.UserData;

public class mem_mine extends AppCompatActivity {
    private Context context;
    private static mem_mine instance;
    private Button btn_edit;
    private mem_mine_edit_dialog dialog;
    private TextView text_year, text_height, text_menstruation, text_surgery_date, text_cell_type, text_hermone, text_her_2, text_fish;
    private CheckBox cb_m_chemical, cb_m_radio, cb_m_biaoba, cb_m_hermone;

    private String year, height, menstruation, surgery, celltype, hermonetype, her2, fish;
    boolean chemical, radio, biaoba, hermone;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_mine);
        context = this;
        instance = this;
        init();
        getSharedData();
        updateData();

        btn_edit = (Button) findViewById(R.id.btn_edit_mem_mine);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new mem_mine_edit_dialog(context, instance);
                dialog.setTitle("新增");
                dialog.setYesOnclickListener("確定", new mem_mine_edit_dialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
    }

    void init(){
        text_year = (TextView)findViewById(R.id.text_year);
        text_height = (TextView)findViewById(R.id.text_height);
        text_menstruation = (TextView)findViewById(R.id.text_menstruation);
        text_surgery_date = (TextView)findViewById(R.id.text_surgery_date);
        text_cell_type = (TextView)findViewById(R.id.text_cell_type);
        text_hermone = (TextView)findViewById(R.id.text_hermone);
        text_her_2 = (TextView)findViewById(R.id.text_her_2);
        text_fish = (TextView)findViewById(R.id.text_fish);

        cb_m_chemical = (CheckBox)findViewById(R.id.cb_m_chemical);
        cb_m_radio = (CheckBox)findViewById(R.id.cb_m_radio);
        cb_m_biaoba = (CheckBox)findViewById(R.id.cb_m_biaoba);
        cb_m_hermone = (CheckBox)findViewById(R.id.cb_m_hermone);
    }

    void getSharedData(){
        sharedPreferences = UserData.getSharedPreferences(context);
        year = sharedPreferences.getString("YEAR", "0");
        height = sharedPreferences.getString("HEIGHT", "0");
        menstruation = sharedPreferences.getString("MENSTRUATION", "0");
        surgery = sharedPreferences.getString("SURGERY", "0");
        celltype = sharedPreferences.getString("CELLTYPE", "0");
        hermonetype = sharedPreferences.getString("HERMONETYPE", "0");
        her2 = sharedPreferences.getString("HER2", "0");
        fish = sharedPreferences.getString("FISH", "0");

        chemical = sharedPreferences.getBoolean("CHEMICAL", false);
        radio = sharedPreferences.getBoolean("RADIO", false);
        biaoba = sharedPreferences.getBoolean("BIAOBA", false);
        hermone = sharedPreferences.getBoolean("HERMONE", false);
    }
    void updateData(){
        text_year.setText(year);
        text_height.setText(height);
        text_menstruation.setText(menstruation);
        text_surgery_date.setText(surgery);
        text_cell_type.setText(celltype);
        text_hermone.setText(hermonetype);
        text_her_2.setText(her2);
        text_fish.setText(fish);

        cb_m_chemical.setClickable(false);
        cb_m_chemical.setChecked(chemical);
        cb_m_radio.setClickable(false);
        cb_m_radio.setChecked(radio);
        cb_m_biaoba.setClickable(false);
        cb_m_biaoba.setChecked(biaoba);
        cb_m_hermone.setClickable(false);
        cb_m_hermone.setChecked(hermone);

    }

}
