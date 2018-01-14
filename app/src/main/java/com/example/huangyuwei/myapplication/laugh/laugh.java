package com.example.huangyuwei.myapplication.laugh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.huangyuwei.myapplication.R;

public class laugh extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laugh);

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
                    fragment = new laugh_soul();
                    break;
                case 1:
                    fragment = new laugh_music();
                    break;
                case 2:
                    fragment = new laugh_night();
                    break;

                case 3:
                    fragment = new laugh_fashion();
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
                    return "心靈補給";
                case 1:
                    return "音樂饗宴";
                case 2:
                    return "一夜好眠";

                case 3:
                    return "時尚自信";
                default:
                    return null;
            }
        }
    }
}
