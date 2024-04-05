package com.example.game_app.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game_app.models.WrapContentGridLayoutManager;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 6; // The minimum amount of items to have below your current scroll position before loading more.
    private int lastVisibleItem, totalItemCount;
    private boolean loading; // True if we are still waiting for the last set of data to load.

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessScrollListener(WrapContentGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItemCount = mLayoutManager.getItemCount();
        lastVisibleItem = ((WrapContentGridLayoutManager) mLayoutManager).findLastVisibleItemPosition();

        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            // End has been reached, Do something
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();
}