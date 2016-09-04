package com.trangiabao.giaothong.gioithieu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trangiabao.giaothong.R;

public class GioiThieuFragment extends Fragment {


    public GioiThieuFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gioi_thieu, container, false);

        return view;
    }

}
