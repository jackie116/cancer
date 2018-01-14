package com.example.huangyuwei.myapplication.mem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.ChemCure;
import com.example.huangyuwei.myapplication.mem.dummy.DummyContent;

public class mem_cure_main extends AppCompatActivity implements mem_cure_fragment_1.OnListFragmentInteractionListener,
        mem_cure_fragment_2.OnFragmentInteractionListener, mem_cure_fragment_3.OnFragmentInteractionListener, mem_cure_fragment_4.OnFragmentInteractionListener,
        mem_cure_fragment_1_edit.OnFragmentInteractionListener, mem_cure_fragment_2_edit.OnFragmentInteractionListener, mem_cure_fragment_3_edit.OnFragmentInteractionListener, mem_cure_fragment_4_edit.OnFragmentInteractionListener{
    private final String TAG = "MEM_CURE";
    private mem_cure_main instance;
    private Context context;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    Fragment fragment;
    int selectPos;

    //private mem_cure_fragment_1_dialog dialog_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_cure_main);
        instance=this;
        context = this;
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

        // 設定 ViewPager 和 Pager Adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(ChemCure item) {

    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            selectPos = position;
            fragment = null;

            // 根據目前tab標籤頁的位置，傳回對應的fragment物件
            switch (position) {
                case 0:
                    fragment = new mem_cure_fragment_1();
                    break;
                case 1:
                    fragment = new mem_cure_fragment_2();
                    break;
                case 2:
                    fragment = new mem_cure_fragment_3();
                    break;
                case 3:
                    fragment = new mem_cure_fragment_4();
                    break;

            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "化療";
                case 1:
                    return "放療";
                case 2:
                    return "標靶";
                case 3:
                    return "賀爾蒙";

                default:
                    return null;
            }
        }
    }
}
