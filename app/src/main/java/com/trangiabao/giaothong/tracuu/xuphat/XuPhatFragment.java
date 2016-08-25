package com.trangiabao.giaothong.tracuu.xuphat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trangiabao.giaothong.R;

public class XuPhatFragment extends Fragment {

    public XuPhatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xu_phat, container, false);
        return view;
    }
}
