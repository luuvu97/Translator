package user.com.translator.interf;

import java.util.List;

import user.com.translator.model.db.LanguageDbo;

public interface ITranslateLanguage {

    public LanguageDbo getLangFrom();

    public LanguageDbo getLangTo();

    public void setAutoDetectLang(String langCode);

    public void setLangFrom(String langCode);

    public void setLangTo(String langCode);

    public void revertLang();

    public List<LanguageDbo> getAllLang();

}
