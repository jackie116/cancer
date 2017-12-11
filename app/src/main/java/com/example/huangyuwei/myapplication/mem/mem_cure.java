//package com.example.huangyuwei.myapplication.mem;
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.app.FragmentTransaction;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.Toast;
//
//import com.example.huangyuwei.myapplication.R;
//import com.example.huangyuwei.myapplication.mem.dummy.DummyContent;
//
//public class mem_cure extends AppCompatActivity implements mem_cure_fragment_1.OnListFragmentInteractionListener,
//        mem_cure_fragment_2.OnFragmentInteractionListener, mem_cure_fragment_3.OnFragmentInteractionListener, mem_cure_fragment_4.OnFragmentInteractionListener,
//        mem_cure_fragment_1_edit.OnFragmentInteractionListener, mem_cure_fragment_2_edit.OnFragmentInteractionListener, mem_cure_fragment_3_edit.OnFragmentInteractionListener, mem_cure_fragment_4_edit.OnFragmentInteractionListener{
//    private FrameLayout frame_fragment;
//    private Fragment oldFragment;
//    private Fragment newFragment;
//    private Button btn_1;
//    private Button btn_2;
//    private Button btn_3;
//    private Button btn_4;
//    private Button btn_5;
//    private int now_at;
//    private boolean now_edit;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mem_cure);
//        frame_fragment = (FrameLayout)findViewById(R.id.frame_fragment);
//        btn_1 = (Button)findViewById(R.id.btn_men_cure_1);
//        btn_2 = (Button)findViewById(R.id.btn_men_cure_2);
//        btn_3 = (Button)findViewById(R.id.btn_men_cure_3);
//        btn_4 = (Button)findViewById(R.id.btn_men_cure_4);
//        btn_5 = (Button)findViewById(R.id.btn_men_cure_5);
//
//        btn_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                oldFragment = newFragment;
//                newFragment = new mem_cure_fragment_1();
//                newFragment.setArguments(getIntent().getExtras());
//                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction().remove(oldFragment).add(R.id.frame_fragment, newFragment, "TAG_Fragment_1").addToBackStack(null)
//                        .commit();
//                now_at = 1;
//                now_edit=false;
//            }
//        });
//        btn_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                oldFragment = newFragment;
//                newFragment = new mem_cure_fragment_2();
//                newFragment.setArguments(getIntent().getExtras());
//                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction().remove(oldFragment).add(R.id.frame_fragment, newFragment, "TAG_Fragment_2").addToBackStack(null)
//                        .commit();
//                now_at = 2;
//                now_edit=false;
//            }
//        });
//        btn_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                oldFragment = newFragment;
//                newFragment = new mem_cure_fragment_3();
//                newFragment.setArguments(getIntent().getExtras());
//                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction().remove(oldFragment).add(R.id.frame_fragment, newFragment, "TAG_Fragment_3").addToBackStack(null)
//                        .commit();
//                now_at = 3;
//                now_edit=false;
//            }
//        });
//        btn_4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                oldFragment = newFragment;
//                newFragment = new mem_cure_fragment_4();
//                newFragment.setArguments(getIntent().getExtras());
//                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction().remove(oldFragment).add(R.id.frame_fragment, newFragment, "TAG_Fragment_4").addToBackStack(null)
//                        .commit();
//                now_at = 4;
//                now_edit=false;
//            }
//        });
//        btn_5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                go_edit_fragment();
//            }
//        });
//
//
//        newFragment = new mem_cure_fragment_1();
//        oldFragment = newFragment;
//        newFragment.setArguments(getIntent().getExtras());
//        getFragmentManager().beginTransaction().remove(oldFragment).add(R.id.frame_fragment, newFragment, "TAG_Fragment_1").addToBackStack(null)
//                .commit();
//        now_at = 1;
//        now_edit=false;
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
////        if(!now_edit){
////            self_main.getInstance().finish();
////            Intent intent = new Intent();
////            intent.setClass( mem_cure.this , self_main.class);
////            startActivity(intent);
////            mem_cure.this.finish();
////        }
//    }
//
//    @Override
//    public void onFragmentInteraction(Uri uri) {
//        Toast.makeText(getApplicationContext(),"123 : "+uri,Toast.LENGTH_SHORT).show();
//    }
//
//
//    @Override
//    public void onListFragmentInteraction(DummyContent.DummyItem item) {
//        Toast.makeText(getApplicationContext(),"123 : "+item.content,Toast.LENGTH_SHORT).show();
//    }
//
//    public void go_edit_fragment(){
//        switch(now_at){
//            case 1:
//                oldFragment = newFragment;
//                newFragment = new mem_cure_fragment_1_edit();
//                newFragment.setArguments(getIntent().getExtras());
//                newFragment.setArguments(getIntent().getExtras());
//                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction().replace(R.id.frame_fragment, newFragment, "TAG_Fragment_edit_1").addToBackStack(null)
//                        .commit();
//                now_edit=true;
//                break;
//            case 2:
//                oldFragment = newFragment;
//                newFragment = new mem_cure_fragment_2_edit();
//                newFragment.setArguments(getIntent().getExtras());
//                newFragment.setArguments(getIntent().getExtras());
//                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction().replace(R.id.frame_fragment, newFragment, "TAG_Fragment_edit_2").addToBackStack(null)
//                        .commit();
//                now_edit=true;
//                break;
//            case 3:
//                oldFragment = newFragment;
//                newFragment = new mem_cure_fragment_3_edit();
//                newFragment.setArguments(getIntent().getExtras());
//                newFragment.setArguments(getIntent().getExtras());
//                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction().replace(R.id.frame_fragment, newFragment, "TAG_Fragment_edit_3").addToBackStack(null)
//                        .commit();
//                now_edit=true;
//                break;
//            case 4:
//                oldFragment = newFragment;
//                newFragment = new mem_cure_fragment_4_edit();
//                newFragment.setArguments(getIntent().getExtras());
//                newFragment.setArguments(getIntent().getExtras());
//                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction().replace(R.id.frame_fragment, newFragment, "TAG_Fragment_edit_4").addToBackStack(null)
//                        .commit();
//                now_edit=true;
//                break;
//
//        }
//    }
//}
