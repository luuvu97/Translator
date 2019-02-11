package user.com.translator.model.db;

import user.com.translator.DefineVar;

import static user.com.translator.DefineVar.DETECT;

public class LanguageDbo {

    private String mCode;
    private String mEnglishName;

    public LanguageDbo(String code, String englishName) {
        this.mCode = code;
        this.mEnglishName = englishName;
    }

    public LanguageDbo(String mCode) {
        this.mCode = mCode;
        mEnglishName = String.format(DefineVar.DETECT, getCode());
    }

    public static LanguageDbo getDefault() {
        return new LanguageDbo(DefineVar.AUTO_DETECT_LANG_CODE, DefineVar.AUTO_DETECT_LANG_TITLE);
    }

    public String getCode() {
        return mCode;
    }

    public void setEnglishName(String mEnglishName) {
        this.mEnglishName = mEnglishName;
    }

    public String getEnglishName() {
        return mEnglishName;
    }

    public boolean isAutoDetect() {
        return mCode.equalsIgnoreCase(DefineVar.AUTO_DETECT_LANG_CODE);
    }

}
