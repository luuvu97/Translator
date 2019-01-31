package user.com.translator.common.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import user.com.translator.interf.IListItem;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        bindLayout();
    }

    public void bindLayout() {

    }

    public void onBindViewHolder(IListItem item) {

    }
}
