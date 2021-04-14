package com.example.exercise;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScroll extends RecyclerView.OnScrollListener {
    LinearLayoutManager layoutManager;

    public PaginationScroll(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                loadMoreItems();
            }
        }
    }
        protected abstract void loadMoreItems();

        public abstract int getTotalPageCount();

        public abstract boolean isLastPage();

        public abstract boolean isLoading();



}
//&& firstVisibleItemPosition >= 0
//        && totalItemCount >= getTotalPageCount()