package com.trangiabao.giaothong.tracuu.biensoxe.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.biensoxe.db.DiaPhuongDB;

import java.util.List;

public class Tinh extends AbstractItem<Tinh, Tinh.ViewHolder> {

    private int id;
    private String kiHieu;
    private String tinh;
    private List<DiaPhuong> lstdiaPhuong;

    public Tinh(Context context, int id, String kiHieu, String tinh) {
        this.id = id;
        this.kiHieu = kiHieu;
        this.tinh = tinh;
        this.lstdiaPhuong = new DiaPhuongDB(context).getByIdTinh(this.id);
    }

    public int getId() {
        return id;
    }

    public String getKiHieu() {
        return kiHieu;
    }

    public String getTinh() {
        return tinh;
    }

    @Override
    public int getType() {
        return R.id.layout_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_dan_su;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.txtTinh.setText(getTinh());
        viewHolder.txtKiHieu.setText(getKiHieu());
        String seri = "";
        for (DiaPhuong temp : this.lstdiaPhuong) {
            seri += temp.getDiaPhuong() + ": " + temp.getSeri() + "\n";
        }
        viewHolder.txtSeri.setText(seri.substring(0, seri.length() - 2));

        /*Drawable drawable = null;
        try {
            InputStream is = this.context.getAssets().open(getHinh());
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.imgHinh.setImageDrawable(drawable);*/
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imgHinh;
        protected TextView txtSeri;
        protected TextView txtTinh;
        protected TextView txtKiHieu;

        public ViewHolder(View view) {
            super(view);
            this.txtSeri = (TextView) itemView.findViewById(R.id.txtSeri);
            this.txtTinh = (TextView) itemView.findViewById(R.id.txtTinh);
            this.txtKiHieu = (TextView) itemView.findViewById(R.id.txtKiHieu);
            this.imgHinh = (ImageView) itemView.findViewById(R.id.imgHinh);
        }
    }
}
