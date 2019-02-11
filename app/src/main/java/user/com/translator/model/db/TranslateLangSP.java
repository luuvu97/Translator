package user.com.translator.model.db;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import user.com.translator.DefineVar;

public class TranslateLangSP {

    private static final String NAME = "TranslateLanguage";
    private static final String KEY_LANG_FROM = "LangFrom";
    private static final String KEY_LANG_TO = "LangTo";

    private SharedPreferences mSp;
    private String mLangFrom;
    private String mLangTo;

    public TranslateLangSP(Application application) {
        mSp = application.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        mLangFrom = mSp.getString(KEY_LANG_FROM, DefineVar.AUTO_DETECT_LANG_CODE);
        mLangTo = mSp.getString(KEY_LANG_TO, DefineVar.DEFAULT_TRANS_TO_LANG);
    }

    public boolean setLangFrom(String langFrom) {
        if (!mLangFrom.equalsIgnoreCase(langFrom)) {
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString(KEY_LANG_FROM, langFrom);
            editor.commit();
            mLangFrom = langFrom;
            return true;
        }
        return false;
    }

    public boolean setLangTo(String langTo) {
        if (!mLangTo.equalsIgnoreCase(langTo)) {
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString(KEY_LANG_FROM, langTo);
            editor.commit();
            mLangTo = langTo;
            return true;
        }
        return false;
    }

    public String getLangFrom() {
        return mLangFrom;
    }

    public String getLangTo() {
        return mLangTo;
    }

    public void revertLang(String langFrom, String langTo) {
        setLangFrom(langTo);
        setLangTo(langFrom);
    }
}
