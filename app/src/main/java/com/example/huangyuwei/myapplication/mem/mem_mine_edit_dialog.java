package com.example.huangyuwei.myapplication.mem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.UserData;

import static android.content.ContentValues.TAG;

/**
 * Created by goodweather on 2017/12/11.
 */

public class mem_mine_edit_dialog extends Dialog {
    private Context context;
    private Button btn_confirm;
    private mem_mine instance;

    private String noStr, yesStr;
    private mem_mine_edit_dialog.onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private mem_mine_edit_dialog.onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    private EditText edt_year, edt_height, edt_menstruation, edt_surgery, edt_celltypem, edt_hermonetype, edt_her2, edt_fish;
    private CheckBox cb_chemical, cb_radio, cb_biaoba, cb_hermone;

    private String year, height, menstruation, surgery, celltype, hermonetype, her2, fish;
    boolean chemical, radio, biaoba, hermone;

    public mem_mine_edit_dialog(@NonNull Context context, mem_mine instance) {
        super(context);
        this.instance = instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mem_mine_edit);
        context = getContext();
        init();
        btn_confirm = (Button) findViewById(R.id.btn_confirm_mem_mine);
        btn_confirm.setText(yesStr);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();

                    getData();
                    saveToSharedPreference();

                    Intent refresh = new Intent(context, mem_mine.class);
                    context.startActivity(refresh);
                    instance.finish();
                }
            }
        });
    }



    public void setYesOnclickListener(String str, mem_mine_edit_dialog.onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }

    void init(){
        edt_year = (EditText) findViewById(R.id.edt_year);
        edt_height = (EditText) findViewById(R.id.edt_height);
        edt_menstruation = (EditText) findViewById(R.id.edt_menstruation);
        edt_surgery = (EditText) findViewById(R.id.edt_surgery);
        edt_celltypem = (EditText) findViewById(R.id.edt_celltype);
        edt_hermonetype = (EditText) findViewById(R.id.edt_hermone);
        edt_her2 = (EditText) findViewById(R.id.edt_her2);
        edt_fish = (EditText) findViewById(R.id.edt_fish);

        cb_chemical = (CheckBox)findViewById(R.id.cb_chemical);
        cb_radio = (CheckBox)findViewById(R.id.cb_radio);
        cb_biaoba = (CheckBox)findViewById(R.id.cb_biaoba);
        cb_hermone = (CheckBox)findViewById(R.id.cb_hermone);
    }
    void getData(){
        year = edt_year.getText().toString();
        height = edt_height.getText().toString();
        menstruation = edt_menstruation.getText().toString();
        surgery = edt_surgery.getText().toString();
        celltype = edt_celltypem.getText().toString();
        hermonetype = edt_hermonetype.getText().toString();
        her2 = edt_her2.getText().toString();
        fish = edt_fish.getText().toString();

        chemical = cb_chemical.isChecked();
        radio = cb_radio.isChecked();
        biaoba = cb_biaoba.isChecked();
        hermone = cb_hermone.isChecked();

        Log.i(TAG, "1231:  "+" 1 "+year+" 2 "+height+" 3 "+menstruation+" 4 "+surgery+" 5 "+celltype+" 6 "+hermonetype+" 7 "+her2+" 8 "+fish);
        Log.i(TAG, "1231: "+" 1 "+chemical+" 2 "+radio+" 3 "+biaoba+" 4 "+hermone);
    }
    void saveToSharedPreference(){
        SharedPreferences sharedPreferences = UserData.getSharedPreferences(context);
        sharedPreferences.edit()
                .putString("YEAR", year)
                .putString("HEIGHT", height)
                .putString("MENSTRUATION", menstruation)
                .putString("SURGERY", surgery)
                .putString("CELLTYPE", celltype)
                .putString("HERMONETYPE", hermonetype)
                .putString("HER2", her2)
                .putString("FISH", fish)
                .putBoolean("CHEMICAL", chemical)
                .putBoolean("RADIO", radio)
                .putBoolean("BIAOBA", biaoba)
                .putBoolean("HERMONE", hermone)
                .apply();
    }
}
