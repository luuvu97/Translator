package user.com.translator.common.list;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import user.com.translator.R;
import user.com.translator.interf.ICommonListClickListener;

public class CommonList extends ConstraintLayout {

    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ScrollListener mScrollListener;
    private RecyclerView.ItemDecoration mItemDecoration;

    public CommonList(Context context) {
        this(context, null);
    }

    public CommonList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = LayoutInflater.from(context).inflate(R.layout.common_list, this);
        mRecyclerView = v.findViewById(R.id.recycler_view);
//        mScrollListener = new ScrollListener();
//        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    public void setData(List mDatas, ICommonListClickListener mListener) {
        mAdapter = new CommonAdapter(mDatas, mListener);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mItemDecoration = new DividerItemDecoration(getContext());
        mRecyclerView.addItemDecoration(mItemDecoration);
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public void scrollTo(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    public void smoothScrollTo(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }
}
