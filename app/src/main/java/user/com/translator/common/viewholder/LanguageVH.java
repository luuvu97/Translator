package user.com.translator.common.viewholder;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import user.com.translator.R;
import user.com.translator.model.logic.Language;

public class LanguageVH extends BaseViewHolder<Language> {

    TextView mTvTitle;

    public LanguageVH(@NonNull View itemView) {
        super(itemView);
        mTvTitle = itemView.findViewById(R.id.tv_text);
    }

    @Override
    public void onBindViewHolder(Language language) {
        super.onBindViewHolder(language);
        mTvTitle.setText(language.getTitle());
        if (language.isSelected()) {
            mTvTitle.setTypeface(null, Typeface.BOLD);
            mTvTitle.setBackgroundResource(R.color.colorGreen);
        } else {
            mTvTitle.setTypeface(null, Typeface.NORMAL);
            mTvTitle.setBackgroundResource(android.R.color.transparent);
        }
    }
}
