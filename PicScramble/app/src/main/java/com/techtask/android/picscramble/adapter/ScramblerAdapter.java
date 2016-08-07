package com.techtask.android.picscramble.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.techtask.android.picscramble.R;
import com.techtask.android.picscramble.model.ScrambleItem;
import com.techtask.android.picscramble.util.GlideUtils;

import java.util.List;

public class ScramblerAdapter extends RecyclerView.Adapter<ScramblerAdapter.ViewHolder> {
    private List<ScrambleItem> picList;
    private SparseBooleanArray flipState = new SparseBooleanArray();
    private OnItemClickListener clickListener;

    public ScramblerAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScrambleItem item = picList.get(position);
        boolean canShowImage = flipState.get(item.getId(), false);
        holder.blankView.setTag(item);
        flipView(holder, canShowImage, item);
    }

    private void flipView(ViewHolder holder, boolean canShowImage, ScrambleItem item) {
        if (canShowImage) {
            holder.blankView.setVisibility(View.GONE);
            holder.picImageView.setVisibility(View.VISIBLE);
            GlideUtils.displayImageFromUrl(holder.itemView.getContext(), item.getImageUrl(), holder.picImageView);
        } else {
            holder.picImageView.setVisibility(View.GONE);
            holder.blankView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return picList != null ? picList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView picImageView;
        private Button blankView;

        public ViewHolder(View itemView) {
            super(itemView);
            picImageView = (ImageView) itemView.findViewById(R.id.pic_imageview);
            blankView = (Button) itemView.findViewById(R.id.blank_view);
            blankView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.blank_view) {
                clickListener.onItemClicked(((ScrambleItem) view.getTag()));
            }
        }
    }

    public void setPicList(List<ScrambleItem> picList) {
        this.picList = picList;
    }

    public void enableAllFlipStates() {
        for (ScrambleItem item : picList) {
            enableFlipStateForId(item.getId());
        }
    }

    public void enableFlipStateForId(int id) {
        if (flipState != null) {
            flipState.put(id, true);
        }
    }

    public void resetFlipState() {
        if (flipState != null) {
            flipState.clear();
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(ScrambleItem item);
    }
}
