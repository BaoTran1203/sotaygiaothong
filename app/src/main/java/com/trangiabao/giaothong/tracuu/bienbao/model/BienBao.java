package com.trangiabao.giaothong.tracuu.bienbao.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BienBao extends AbstractItem<BienBao, BienBao.ViewHolder> {

    private int id;
    private int idNhomBienBao;
    private String maBienBao;
    private String tenBienBao;
    private String noiDungBienBao;
    private String hinh;
    private Context context;

    public BienBao(Context context, int id, int idNhomBienBao, String maBienBao, String tenBienBao, String noiDungBienBao, String hinh) {
        this.context = context;
        this.id = id;
        this.idNhomBienBao = idNhomBienBao;
        this.maBienBao = maBienBao;
        this.tenBienBao = tenBienBao;
        this.noiDungBienBao = noiDungBienBao;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public String getMaBienBao() {
        return maBienBao;
    }

    public String getTenBienBao() {
        return tenBienBao;
    }

    public String getNoiDungBienBao() {
        return noiDungBienBao;
    }

    public String getHinh() {
        return hinh;
    }

    @Override
    public int getType() {
        return R.id.item_layout_bien_bao;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_bien_bao;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.txtMaBienBao.setText(Html.fromHtml("Mã: " + getMaBienBao()));
        viewHolder.txtTenBienBao.setText(Html.fromHtml(getTenBienBao()));
        viewHolder.txtNoiDungBienBao.setText(Html.fromHtml(getNoiDungBienBao()));
        Drawable drawable = null;
        try {
            InputStream is = this.context.getAssets().open(getHinh());
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.imgHinh.setImageDrawable(drawable);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imgHinh;
        protected TextView txtMaBienBao;
        protected TextView txtTenBienBao;
        protected TextView txtNoiDungBienBao;

        public ViewHolder(View view) {
            super(view);
            this.txtMaBienBao = (TextView) itemView.findViewById(R.id.txtMaBienBao);
            this.txtTenBienBao = (TextView) itemView.findViewById(R.id.txtTenBienBao);
            this.txtNoiDungBienBao = (TextView) itemView.findViewById(R.id.txtNoiDungBienBao);
            this.imgHinh = (ImageView) itemView.findViewById(R.id.imgHinh);
        }
    }
}
