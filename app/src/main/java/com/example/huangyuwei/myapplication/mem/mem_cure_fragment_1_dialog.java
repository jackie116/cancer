package com.example.huangyuwei.myapplication.mem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;

import java.util.Calendar;

/**
 * Created by user-pc on 2017/12/10.
 */

public class mem_cure_fragment_1_dialog extends Dialog {
    private Context context;
    private Button btn_select_date, btn_confirm;
    private TextView dateText;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;

    private int nowYear, nowMonth, nowDay;
    private int mYear, mMonth, mDay;

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    private String noStr, yesStr;


    public mem_cure_fragment_1_dialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mem_cure_fragment_1_edit);
        dateText = (TextView) findViewById(R.id.dateText);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        btn_select_date = (Button) findViewById(R.id.btn_cure_1_date);
        btn_select_date.setOnClickListener(new View.OnClickListener() {
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
        btn_confirm = (Button) findViewById(R.id.btn_cure_1_confirm);
        btn_confirm.setText(yesStr);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
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


    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "."
                + String.valueOf(monthOfYear + 1) + "."
                + String.valueOf(dayOfMonth);
    }
}
