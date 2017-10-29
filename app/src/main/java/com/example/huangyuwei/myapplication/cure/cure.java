package com.example.huangyuwei.myapplication.cure;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.huangyuwei.myapplication.R;

public class cure extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cure);

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
                    fragment = new cure_stage();
                    break;
                case 1:
                    fragment = new cure_way();
                    break;
                case 2:
                    fragment = new cure_china();
                    break;
                case 3:
                    fragment = new cure_transfer();
                    break;
                case 4:
                    fragment = new cure_relapse();
                    break;
                case 5:
                    fragment = new cure_prognosis();
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
                    return "分期";
                case 1:
                    return "方式";
                case 2:
                    return "中醫輔助";
                case 3:
                    return "轉移";
                case 4:
                    return "復發";
                case 5:
                    return "預後";
                default:
                    return null;
            }
        }
    }
}