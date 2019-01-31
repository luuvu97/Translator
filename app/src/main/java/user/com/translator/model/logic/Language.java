package user.com.translator.model.logic;

import user.com.translator.R;
import user.com.translator.interf.IListItem;

public class Language extends IListItem {
    public String code;
    public String englishName;
    public String nativeName;

    public Language(String code, String englishName, String nativeName) {
        this.code = code;
        this.englishName = englishName;
        this.nativeName = nativeName;
    }

    public static Language getDefault() {
        return new Language("", "Auto detect", "");
    }

    public void setDetectLang(String lang) {
        code = lang.toUpperCase();
        englishName = "Detect(" + code + ")";
    }

    @Override
    public String toString() {
       return getTitle();
    }

    @Override
    public String getTitle() {
        if (code == null || code.isEmpty()) {
            return englishName;
        }
        return "" + nativeName + "(" + code + ")" + " - " + englishName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.simple_list_item;
    }

}
