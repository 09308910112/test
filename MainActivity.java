package com.itxiaohou.myapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itxiaohou.myapplication.R;
import com.itxiaohou.myapplication.fragment.FragmentTable01;
import com.itxiaohou.myapplication.fragment.FragmentTable02;
import com.itxiaohou.myapplication.fragment.FragmentTable03;

public class MainActivity extends FragmentActivity {

    //固定写法，表示我们要加载的资源文件为libhello.so
    static {
        System.loadLibrary("hello");
    }

    private ViewPager viewPager;
    private TextView tvChat;
    private TextView tvDiscovery;
    private TextView tvContact;
    private ImageView ivTab;


    //声明一个本地方法，用native关键字修饰
    public native String getStringFromNative();

    private static  int mTabWidth = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        initTab();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                position = position%3;
                if(position == 0){
                    return new FragmentTable01();
                }else if(position == 1){
                    return new FragmentTable02();
                }else if(position == 2){
                    return new FragmentTable03();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    public void initTab(){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivTab.getLayoutParams();
        params.width = 100;
        Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        mTabWidth = screenWidth/3;
        params.width = screenWidth/3;
        ivTab.setLayoutParams(params);
    }

    public int flag = 0;
    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("lpc222",position+",  "+positionOffset+",  "+positionOffsetPixels);
                int value;
                if(flag >positionOffsetPixels){ //往右边滑动
                     value = (int)(position*mTabWidth -(1-mTabWidth)*positionOffset);
                }else{//往左边滑动
                     value = (int)( position*mTabWidth +mTabWidth*positionOffset);
                }
                flag = positionOffsetPixels;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivTab.getLayoutParams();
                params.leftMargin = value;
                ivTab.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                setTabTextColorDefault();
                if(position == 0){
                    tvChat.setTextColor(Color.RED);

                }else if(position == 1){
                    tvDiscovery.setTextColor(Color.RED);

                }else if(position == 2){
                    tvContact.setTextColor(Color.RED);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTabTextColorDefault() {
        tvChat.setTextColor(Color.BLACK);
        tvDiscovery.setTextColor(Color.BLACK);
        tvContact.setTextColor(Color.BLACK);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tvChat = (TextView) findViewById(R.id.tv_chat);
        tvDiscovery = (TextView) findViewById(R.id.tv_discovery);
        tvContact = (TextView) findViewById(R.id.tv_contact);
        ivTab = (ImageView) findViewById(R.id.imageview_tab);
    }
}