package user.com.translator.common.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import user.com.translator.common.viewholder.BaseViewHolder;
import user.com.translator.interf.ICommonListClickListener;
import user.com.translator.interf.IListItem;

public class CommonAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List mDatas;
    private ICommonListClickListener mListener;

    public CommonAdapter(List mDatas, ICommonListClickListener mListener) {
        this.mDatas = mDatas;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(viewType, viewGroup, false);
        return ViewHolderProvider.getInstance(viewType, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, final int position) {
        IListItem item = (IListItem) mDatas.get(position);
        baseViewHolder.onBindViewHolder(item);
        if (mListener != null) {
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListItemClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ((IListItem)mDatas.get(position)).getLayoutId();
    }
}
