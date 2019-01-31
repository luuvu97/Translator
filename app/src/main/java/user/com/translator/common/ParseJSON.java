package user.com.translator.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import user.com.translator.model.logic.Language;

public class ParseJSON {

    private static final String LANG_NAME = "name";
    private static final String LANG_NATIVE_NAME = "nativeName";
    private static final String TEXT = "text";
    private static final String LANG = "lang";
    public static final int RESPONE_LANG_SIZE = 2;

    /*
    * "ab":{
        "name":"Abkhaz",
        "nativeName":"аҧсуа"
    }
    * */
    public static ArrayList<Language> parseLanguage(String content){
        ArrayList<Language> list = new ArrayList<Language>();
        list.add(Language.getDefault());
        try {
            JSONObject obj = null;
            obj = new JSONObject(content);
            Iterator itr = obj.keys();
            String key = null;
            String code, englishName, nativeName;
            while(itr.hasNext()) {
                key = (String)itr.next();
                if(key != null){
                    JSONObject property = new JSONObject(obj.getString(key));
                    code = key;
                    englishName = property.getString(LANG_NAME);
                    nativeName = property.getString(LANG_NATIVE_NAME);
                    list.add(new Language(code.toUpperCase(), englishName, nativeName));
                }
            }
        } finally {
            return list;
        }
    }


    /*
    * {"code":200,"lang":"en-vi","text":["xin chào việt nam","xin chào"]}
    * first element is detect language, second element is translate result
    * */
    public static ArrayList<String> parseTranslate(String response){
        ArrayList<String> ret = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray arr = obj.getJSONArray(TEXT);
            String str = obj.getString(LANG);
            String[] langArr = str.split("-");
            ret.add(langArr[0]);
            for(int i = 0; i < arr.length(); i++){
                ret.add(arr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }
}
