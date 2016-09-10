package com.trangiabao.giaothong.tracuu.biensoxe.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.io.IOException;
import java.io.InputStream;

public class NuocNgoai extends AbstractItem<NuocNgoai, NuocNgoai.ViewHolder> {

    private int id;
    private String nuoc;
    private String maNuoc;
    private String quocKy;
    private Context context;

    public NuocNgoai(Context context, int id, String nuoc, String maNuoc, String quocKy) {
        this.context = context;
        this.id = id;
        this.nuoc = nuoc;
        this.maNuoc = maNuoc;
        this.quocKy = quocKy;
    }

    public int getId() {
        return id;
    }

    public String getNuoc() {
        return nuoc;
    }

    public String getMaNuoc() {
        return maNuoc;
    }

    public String getQuocKy() {
        return quocKy;
    }

    @Override
    public int getType() {
        return R.id.item_layout;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_nuoc_ngoai;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.txtNuoc.setText(Html.fromHtml(getNuoc()));
        viewHolder.txtMaNuoc.setText(Html.fromHtml(getMaNuoc()));
        Drawable drawable = null;
        try {
            InputStream is = this.context.getAssets().open(getQuocKy());
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.imgQuocKy.setImageDrawable(drawable);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imgQuocKy;
        protected TextView txtNuoc;
        protected TextView txtMaNuoc;

        public ViewHolder(View view) {
            super(view);
            this.txtNuoc = (TextView) itemView.findViewById(R.id.txtNuoc);
            this.txtMaNuoc = (TextView) itemView.findViewById(R.id.txtMaNuoc);
            this.imgQuocKy = (ImageView) itemView.findViewById(R.id.imgQuocKy);
        }
    }
}
