package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class fragment_amnhac_huongdan extends Fragment {
    public fragment_amnhac_huongdan() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_amnhac_huongdan,container,false );
        return view;
    }
}