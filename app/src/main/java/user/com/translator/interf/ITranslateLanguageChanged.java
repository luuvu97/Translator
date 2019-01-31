package user.com.translator.interf;

public interface ITranslateLanguageChanged {

    public void onLangFromChanged(String langFrom);
    public void onLangToChanged(String langTo);
    public void revertTranslate(String langFrom, String langTo);

}
