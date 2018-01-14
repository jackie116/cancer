package com.example.huangyuwei.myapplication.mem;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;

public class mood_qa extends AppCompatActivity {


    private RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5;
    private RadioButton ans_1,ans_2,ans_3,ans_4,ans_5;
    Button submit;
    private int selected;
    private int answer;
    TextView score,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_qa);

        addListenerRadioButton ();

    }
    private void addListenerRadioButton (){

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
        radioGroup5 = (RadioGroup) findViewById(R.id.radioGroup5);


        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroupWebsite
                selected = radioGroup1.getCheckedRadioButtonId();
                ans_1 = (RadioButton) findViewById(selected);

                selected = radioGroup2.getCheckedRadioButtonId();
                ans_2 = (RadioButton) findViewById(selected);

                selected = radioGroup3.getCheckedRadioButtonId();
                ans_3 = (RadioButton) findViewById(selected);

                selected = radioGroup4.getCheckedRadioButtonId();
                ans_4 = (RadioButton) findViewById(selected);

                selected = radioGroup5.getCheckedRadioButtonId();
                ans_5 = (RadioButton) findViewById(selected);

                answer=Integer.parseInt(ans_1.getText().toString())
                        +Integer.parseInt(ans_2.getText().toString())
                        +Integer.parseInt(ans_3.getText().toString())
                        +Integer.parseInt(ans_4.getText().toString())
                        +Integer.parseInt(ans_5.getText().toString());
                score = (TextView)findViewById(R.id.score);
                result = (TextView)findViewById(R.id.result);
                score.setText("得分："+answer+"分");
                if(answer<=5){
                    score.setTextColor(Color.parseColor("#00FF00"));
                    result.setText("為一般正常範圍，表示身心適應狀況良好");

                }else if(5<answer&&answer<=9){
                    score.setTextColor(Color.parseColor("#FFD700"));
                    result.setText("輕度情緒困擾，建議找家人或朋友談談，抒發情緒");

                }else if(10<=answer&&answer<15){
                    score.setTextColor(Color.parseColor("#FF8C00"));
                    result.setText("中度情緒困擾，建議尋求紓壓管道或接受心理專業諮詢");

                }else if(answer>=15){
                    score.setTextColor(Color.parseColor("#FF0000"));
                    result.setText("重度情緒困擾，建議咨詢精神科醫生接受進一步評估");

                }


            }
        });

    }

}
