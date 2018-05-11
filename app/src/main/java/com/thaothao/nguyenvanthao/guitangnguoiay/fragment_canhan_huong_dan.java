package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class fragment_canhan_huong_dan extends Fragment {
    public fragment_canhan_huong_dan() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_canhan_huongdan,container,false );
        return view;
    }
}