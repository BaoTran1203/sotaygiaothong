package com.trangiabao.giaothong.tracuu;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.util.List;

class TraCuuAdapter extends AbstractItem<TraCuuAdapter, TraCuuAdapter.ViewHolder> {

    private String title;
    private Drawable icon;

    TraCuuAdapter(String title, Drawable icon) {
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
        return R.id.item_layout_tra_cuu;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_tra_cuu;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.txtTitle.setText(getTitle());
        viewHolder.imgIcon.setImageDrawable(getIcon());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private ImageView imgIcon;

        public ViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        }
    }
}
