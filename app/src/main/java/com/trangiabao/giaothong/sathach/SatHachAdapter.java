package com.trangiabao.giaothong.sathach;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SatHachAdapter extends AbstractItem<SatHachAdapter, SatHachAdapter.ViewHolder> {

    private String title;
    private Drawable icon;

    public SatHachAdapter(String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }

    @Override
    public int getType() {
        return R.id.item_layout_sat_hach;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_sat_hach;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.txtTitle.setText(getTitle());
        viewHolder.imgIcon.setImageDrawable(getIcon());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtTitle;
        protected ImageView imgIcon;

        public ViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        }
    }
}
