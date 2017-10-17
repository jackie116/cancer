package com.example.huangyuwei.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class link extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

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
                    fragment = new link_foundation();
                    break;
                case 1:
                    fragment = new link_center();
                    break;
                case 2:
                    fragment = new link_resource();
                case 3:
                    fragment = new link_economic();
                    break;
                case 4:
                    fragment = new link_sister();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "基金會";
                case 1:
                    return "癌症中心";
                case 2:
                    return "輔具資源";
                case 3:
                    return "經濟補助";
                case 4:
                    return "志工姐妹";
                default:
                    return null;
            }
        }
    }
}
