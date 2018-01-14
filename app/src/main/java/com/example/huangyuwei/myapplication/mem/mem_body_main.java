package com.example.huangyuwei.myapplication.mem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.huangyuwei.myapplication.R;

public class mem_body_main extends AppCompatActivity {

    private String TAG="身體";

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_body_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

        // 設定 ViewPager 和 Pager Adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            // 根據目前tab標籤頁的位置，傳回對應的fragment物件
            switch (position) {
                case 0:
                    fragment = new mem_body_weight();
                    break;
                case 1:
                    fragment= new mem_body_weight_test();
                    break;
                case 2:
                    fragment = new mem_body_tmp();
                    break;
                case 3:
                    fragment = new mem_body_blood();
                    break;
                case 4:
                    fragment = new mem_body_arm();
                    break;
                case 5:
                    fragment = new mem_body_symptom();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "體重";
                case 1:
                    return "BMI測量";
                case 2:
                    return "體溫";
                case 3:
                    return "血液";
                case 4:
                    return "手臂";
                case 5:
                    return "症狀";

                default:
                    return null;
            }
        }
    }
}
