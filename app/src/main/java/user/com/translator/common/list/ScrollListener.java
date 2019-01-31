package user.com.translator.common.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class ScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {

            try {
                int scrollY = 0;

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();

                View firstView = recyclerView.findViewHolderForLayoutPosition(position).itemView;

                int itemHeight = firstView.getHeight();
                int heightOffset = (recyclerView.getTop() - firstView.getTop());
                heightOffset %= itemHeight;
                if (heightOffset < 0) {
                    heightOffset += itemHeight;
                }

                if (heightOffset > itemHeight / 2) {
                    scrollY = itemHeight - heightOffset;
                } else {
                    scrollY = -heightOffset;
                }

                recyclerView.smoothScrollBy(0, scrollY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
