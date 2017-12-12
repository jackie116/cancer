package com.example.huangyuwei.myapplication.mem;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.huangyuwei.myapplication.R;

/**
 * Created by goodweather on 2017/12/11.
 */

public class mem_mine_edit_dialog extends Dialog {
    private Button btn_confirm;

    private String noStr, yesStr;
    private mem_mine_edit_dialog.onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private mem_mine_edit_dialog.onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public mem_mine_edit_dialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn_confirm = (Button) findViewById(R.id.btn_confirm_mem_mine);
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
}
