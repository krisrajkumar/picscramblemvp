package com.techtask.android.picscramble.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ScramblerView extends RecyclerView {
    private static final int COL = 3;

    public ScramblerView(Context context) {
        super(context);
        setHasFixedSize(true);
        setLayoutManager(new GridLayoutManager(context, COL));
    }

    public ScramblerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutManager(new GridLayoutManager(context, COL));
    }

    public ScramblerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new GridLayoutManager(context, COL));
    }
}