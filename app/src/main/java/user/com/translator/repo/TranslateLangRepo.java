package user.com.translator.repo;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import user.com.translator.ApplicationUtil;
import user.com.translator.DefineVar;
import user.com.translator.interf.ITranslateLanguage;
import user.com.translator.model.db.LanguageDbo;
import user.com.translator.model.db.TranslateLangSP;

public class TranslateLangRepo implements ITranslateLanguage {

    /*Singleton*/
    private static TranslateLangRepo mInstance;

    private final TranslateLangSP mSp;

    private String mDetectLangCode;
    private LanguageDbo mLangFrom;
    private LanguageDbo mLangTo;

    private MutableLiveData<Boolean> mLangChanged;
    private List<LanguageDbo> mAllLang;

    public static synchronized TranslateLangRepo getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new TranslateLangRepo(application);
        }
        return mInstance;
    }

    private TranslateLangRepo(Application application) {
        mSp = new TranslateLangSP(application);
        mLangChanged = new MutableLiveData<>();
        mLangChanged.postValue(true);
        mAllLang = ApplicationUtil.getAllLang(application);
        mLangFrom = getLangByCode(mSp.getLangFrom());
        mLangTo = getLangByCode(mSp.getLangTo());
    }

    public MutableLiveData<Boolean> getLangChanged() {
        return mLangChanged;
    }

    @Override
    public LanguageDbo getLangFrom() {
        return mLangFrom;
    }

    @Override
    public LanguageDbo getLangTo() {
        return mLangTo;
    }

    @Override
    public void setAutoDetectLang(String langCode) {
        mSp.setLangFrom(DefineVar.AUTO_DETECT_LANG_CODE);
        mLangFrom = LanguageDbo.getDefault();
        mDetectLangCode = langCode;
        mLangFrom.setEnglishName("Detect: " + getLangByCode(langCode).getEnglishName());
    }

    @Override
    public void setLangFrom(String langCode) {
        if (mSp.setLangFrom(langCode)) {
            mLangFrom = getLangByCode(langCode);
            mLangChanged.postValue(true);
        }
    }

    @Override
    public void setLangTo(String langCode) {
        if (mSp.setLangTo(langCode)) {
            mLangTo = getLangByCode(langCode);
            mLangChanged.postValue(true);
        }
    }

    @Override
    public void revertLang() {
        String fromLang = mLangFrom.getCode();
        String toLang = mLangTo.getCode();
        if (mLangFrom.isAutoDetect()) {
            fromLang = mDetectLangCode;
        }
        mSp.revertLang(fromLang, toLang);
        mLangFrom = getLangByCode(mSp.getLangFrom());
        mLangTo = getLangByCode(mSp.getLangTo());
        mLangChanged.postValue(true);
    }

    @Override
    public List<LanguageDbo> getAllLang() {
        return mAllLang;
    }

    private LanguageDbo getLangByCode(String langCode) {
        for (LanguageDbo language : mAllLang) {
            if (language.getCode().equalsIgnoreCase(langCode)) {
                return language;
            }
        }
        return null;
    }
}
