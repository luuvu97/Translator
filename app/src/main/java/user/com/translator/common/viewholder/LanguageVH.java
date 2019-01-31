package user.com.translator.common.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import user.com.translator.R;
import user.com.translator.interf.IListItem;

public class LanguageVH extends BaseViewHolder {

    TextView mTvTitle;

    public LanguageVH(@NonNull View itemView) {
        super(itemView);
        mTvTitle = itemView.findViewById(R.id.tv_text);
    }

    @Override
    public void onBindViewHolder(IListItem language) {
        super.onBindViewHolder(language);
        mTvTitle.setText(language.getTitle());
    }
}
