package user.com.translator.model.logic;

import user.com.translator.R;
import user.com.translator.interf.IListItem;
import user.com.translator.model.db.LanguageDbo;

public class Language implements IListItem {

    private LanguageDbo mLanguage;
    private boolean mIsSelected;

    public Language(LanguageDbo language, boolean isSelected) {
        this.mLanguage = language;
        this.mIsSelected = isSelected;
    }

    public String getCode() {
        return mLanguage.getCode();
    }


    public boolean isSelected() {
        return mIsSelected;
    }

    @Override
    public String getTitle() {
        return mLanguage.getEnglishName();
    }

    @Override
    public int getLayoutId() {
        return R.layout.simple_list_item;
    }

}
