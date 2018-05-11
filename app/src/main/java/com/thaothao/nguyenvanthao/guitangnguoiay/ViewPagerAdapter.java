package com.thaothao.nguyenvanthao.guitangnguoiay;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment>  listFragments=new ArrayList<>(  );
    private final ArrayList<String> listTile=new ArrayList<>(  );
    public ViewPagerAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int i) {
        return listFragments.get( i );
    }

    @Override
    public int getCount() {
        return listTile.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTile.get( position );
    }
    public void addFagment(Fragment fragment, String tile ){
        listFragments.add( fragment );
        listTile.add( tile );
    }
}
