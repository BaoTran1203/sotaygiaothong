package com.trangiabao.giaothong.tracuu.hotline.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.util.List;

public class HotLine extends AbstractItem<HotLine, HotLine.ViewHolder> {

    private int id;
    private int idNhomHotLine;
    private String ten;
    private String phone;

    public HotLine(int id, int idNhomHotLine, String ten, String phone) {
        this.id = id;
        this.idNhomHotLine = idNhomHotLine;
        this.ten = ten;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public int getIdNhomHotLine() {
        return idNhomHotLine;
    }

    private String getTen() {
        return ten;
    }

    private String getPhone() {
        return phone;
    }

    @Override
    public int getType() {
        return R.id.item_layout_hotline;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_hotline;
    }

    @Override
    public void bindView(HotLine.ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.txtTen.setText(getTen());
        viewHolder.txtPhone.setText(getPhone());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTen;
        private TextView txtPhone;

        public ViewHolder(View view) {
            super(view);
            this.txtTen = (TextView) itemView.findViewById(R.id.txtTen);
            this.txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
        }
    }
}
