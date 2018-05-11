package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HuongDanActivity extends AppCompatActivity {
private TabLayout tabLayout;
private ViewPager viewPager;
private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_huong_dan );
        tabLayout=(TabLayout) findViewById( R.id.tablayuot_id );
        viewPager=(ViewPager) findViewById( R.id.viewPager_id );
        adapter=new ViewPagerAdapter( getSupportFragmentManager() );
        //add fragment o day
        adapter.addFagment( new fragment_tinnhan_huong_dan(),"" );
        adapter.addFagment( new fragment_banbe_huongdan(),"" );
        adapter.addFagment( new fragment_bantin_huongdan(),"" );
        adapter.addFagment( new fragment_amnhac_huongdan(),"" );
        adapter.addFagment( new fragment_canhan_huong_dan(),"" );

        viewPager.setAdapter( adapter );
        tabLayout.setupWithViewPager( viewPager );

        tabLayout.getTabAt( 0 ).setIcon( R.drawable.tinh_nhan1);
        tabLayout.getTabAt( 1 ).setIcon( R.drawable.ban_be1 );
        tabLayout.getTabAt( 2 ).setIcon( R.drawable.ban_tin1 );
        tabLayout.getTabAt( 3 ).setIcon( R.drawable.am_nhac1 );
        tabLayout.getTabAt( 4 ).setIcon( R.drawable.ca_nhan1 );


    }
}
