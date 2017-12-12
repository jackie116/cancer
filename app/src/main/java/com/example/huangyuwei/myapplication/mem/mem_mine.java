package com.example.huangyuwei.myapplication.mem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.huangyuwei.myapplication.R;

public class mem_mine extends AppCompatActivity {
    private Context context;
    private Button btn_edit;
    private mem_mine_edit_dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_mine);
        context = this;

        btn_edit = (Button) findViewById(R.id.btn_edit_mem_mine);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new mem_mine_edit_dialog(context);
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
}
