package com.example.huangyuwei.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class user_profile extends AppCompatActivity {
    private final String TAG = "user_profile";
    private static Context context;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        context=this;
        btn_logout = (Button)findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.getSharedPreferences(context).edit().clear().apply();
                Intent intent = new Intent(user_profile.this,MainActivity.class);
                startActivity(intent);
                center.getInstance().finish();
                user_profile.this.finish();
            }
        });
    }
}
