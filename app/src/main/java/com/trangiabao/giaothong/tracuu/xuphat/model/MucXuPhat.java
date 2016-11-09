package com.trangiabao.giaothong.tracuu.xuphat.model;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.util.List;

public class MucXuPhat extends AbstractItem<MucXuPhat, MucXuPhat.ViewHolder> {

    private int id;
    private String doiTuong;
    private String hanhVi;
    private String mucPhat;
    private String boSung;
    private String khacPhuc;

    public MucXuPhat(int id, String doiTuong, String hanhVi, String mucPhat, String boSung, String khacPhuc) {
        this.id = id;
        this.doiTuong = doiTuong;
        this.hanhVi = hanhVi;
        this.mucPhat = mucPhat;
        this.boSung = boSung;
        this.khacPhuc = khacPhuc;
    }

    public int getId() {
        return id;
    }

    public String getHanhVi() {
        return hanhVi;
    }

    public String getMucPhat() {
        return mucPhat;
    }

    public String getBoSung() {
        return boSung;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public String getKhacPhuc() {
        return khacPhuc;
    }

    @Override
    public int getType() {
        return R.id.item_layout_xu_phat;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_xu_phat;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.txtHanhVi.setText(Html.fromHtml(getHanhVi()));
        viewHolder.txtMucGia.setText(Html.fromHtml(getMucPhat()));
        viewHolder.txtBoSung.setText(Html.fromHtml(getBoSung()));
        viewHolder.txtKhacPhuc.setText(Html.fromHtml(getKhacPhuc()));
        viewHolder.txtDoiTuong.setText(Html.fromHtml(getDoiTuong()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtHanhVi, txtMucGia, txtBoSung, txtKhacPhuc, txtDoiTuong;

        public ViewHolder(View view) {
            super(view);
            this.txtHanhVi = (TextView) itemView.findViewById(R.id.txtHanhVi);
            this.txtMucGia = (TextView) itemView.findViewById(R.id.txtMucGia);
            this.txtBoSung = (TextView) itemView.findViewById(R.id.txtBoSung);
            this.txtKhacPhuc = (TextView) itemView.findViewById(R.id.txtKhacPhuc);
            this.txtDoiTuong = (TextView) itemView.findViewById(R.id.txtDoiTuong);
        }
    }
}
