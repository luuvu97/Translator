package user.com.translator.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import user.com.translator.DefineVar;
import user.com.translator.interf.ITranslateLanguage;
import user.com.translator.model.db.LanguageDbo;
import user.com.translator.model.logic.Language;
import user.com.translator.repo.TranslateLangRepo;

public class TranslateLangVM extends AndroidViewModel implements ITranslateLanguage {

    private TranslateLangRepo mRepo;
    private MutableLiveData<Boolean> mLangChanged;
    private int mSelectLangPos;

    public TranslateLangVM(@NonNull Application application) {
        super(application);
        mRepo = TranslateLangRepo.getInstance(application);
        mLangChanged = mRepo.getLangChanged();
    }

    private List<Language> mirgate(String langCode, boolean isWithAutoDetectOption) {
        List<LanguageDbo> allLang = mRepo.getAllLang();
        List<Language> list = new ArrayList<>();
        for (LanguageDbo lang : allLang) {
            boolean mark = lang.getCode().equalsIgnoreCase(langCode);
            list.add(new Language(lang, mark));
            if (mark) {
                mSelectLangPos = allLang.indexOf(lang);
                if (!isWithAutoDetectOption) {
                    --mSelectLangPos;
                }
            }
        }
        if (!isWithAutoDetectOption) {
            list.remove(0);
        }
        return list;
    }

    public MutableLiveData<Boolean> getLangChanged() {
        return mLangChanged;
    }

    public List<Language> getList() {
        String langCode = mRepo.getLangFrom().getCode();
        return mirgate(langCode, true);
    }

    public List<Language> getListWithoutAutoDetectOption() {
        String langCode = mRepo.getLangTo().getCode();
        return mirgate(langCode, false);
    }

    public int getSelectLangPos() {
        return mSelectLangPos;
    }

    @Override
    public LanguageDbo getLangFrom() {
        return mRepo.getLangFrom();
    }

    @Override
    public LanguageDbo getLangTo() {
        return mRepo.getLangTo();
    }

    @Override
    public void setAutoDetectLang(String langCode) {
//        mRepo.setAutoDetectLang(langCode);
    }

    @Override
    public void setLangFrom(String langCode) {
        mRepo.setLangFrom(langCode);
    }

    @Override
    public void setLangTo(String langCode) {
        mRepo.setLangTo(langCode);
    }

    @Override
    public void revertLang() {
        mRepo.revertLang();
    }

    @Override
    public List<LanguageDbo> getAllLang() {
        return null;
    }
}
