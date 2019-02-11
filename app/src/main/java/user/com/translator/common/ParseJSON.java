package user.com.translator.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import user.com.translator.model.db.LanguageDbo;

public class ParseJSON {

    private static final String TEXT = "text";
    private static final String LANG = "lang";

    public static ArrayList<LanguageDbo> parseLanguage(String content){
        ArrayList<LanguageDbo> list = new ArrayList<LanguageDbo>();
        try {
            JSONObject obj = null;
            obj = new JSONObject(content);
            Iterator itr = obj.keys();
            String key = null;
            String code, englishName;
            while(itr.hasNext()) {
                key = (String)itr.next();
                code = key;
                englishName = obj.getString(key);
                list.add(new LanguageDbo(code.toUpperCase(), englishName));
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
