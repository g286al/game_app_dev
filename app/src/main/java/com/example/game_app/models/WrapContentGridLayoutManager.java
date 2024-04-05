package com.example.game_app.models;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

public class WrapContentGridLayoutManager extends GridLayoutManager {
    public WrapContentGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WrapContentGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public WrapContentGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            //Log.e("TAG", "meet a IOOBE in RecyclerView");
        }
    }
}



