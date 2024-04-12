package com.example.game_app.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game_app.models.WrapContentGridLayoutManager;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 4; // The minimum amount of items to have below your current scroll position before loading more.
    private int lastVisibleItem, totalItemCount;

    private boolean loading = true; // Initially assume we are loading until the first set of data is set

    private WrapContentGridLayoutManager mLayoutManager;

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
        lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

        if (!loading && (lastVisibleItem + visibleThreshold) >= totalItemCount) {
            // End has been reached and we are not currently loading, time to load more data
            onLoadMore();
            loading = true; // Set loading to true until confirmed data is fetched and adapter is updated
        }
    }

    public void resetState() {
        // This method can be called to reset the loading state when necessary
        loading = false;
    }

    public abstract void onLoadMore();
}