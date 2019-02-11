package user.com.translator.repo;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.text.TextBlock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import user.com.translator.ApplicationUtil;
import user.com.translator.DefineVar;
import user.com.translator.common.ParseJSON;

public class TranslateRepo {
    private static final String PREURL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
    private static final String KEY = "trnsl.1.1.20180520T102816Z.aab97a77c0e5adbf.103692e0595910855f714d988c22b4dc43296eb6";
    private static final String FORMAT = "&format=plain"; //for return json; format = html for return http format
    private static final String LANG = "&lang=";

    private static TranslateRepo mInstance;

    private String mTextToTranslate;
    private String mResult;
    private String mLangFrom;
    private String mLangTo;

    public static synchronized TranslateRepo getInstance() {
        if (mInstance == null) {
            mInstance = new TranslateRepo();
        }
        return mInstance;
    }

    private String getUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(PREURL).append(KEY);
        if (mLangFrom == null || mLangFrom.isEmpty()) {
            builder.append(LANG).append(mLangTo);
        } else {
            builder.append(LANG).append(mLangFrom).append("-").append(mLangTo);
        }
        builder.append(FORMAT);
        Log.i("TranslateRepo", "Url: " + builder.toString());
        return builder.toString();
    }

    private String getPostData() throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        builder.append("&").append(URLEncoder.encode("text", "UTF-8"));
        builder.append("=");
        builder.append(URLEncoder.encode(mTextToTranslate, "UTF-8"));
        return builder.toString();
    }

    private String connect() {
        try {
            URL urlObj = new URL(getUrl());

            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            wr.write(getPostData());
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            reader.close();
            conn.disconnect();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String translate(String mTextToTranslate, String mLangFrom, String mLangTo) {
        reset(mTextToTranslate, mLangFrom, mLangTo);
        String response = connect();
        Log.i("TranslateRepo", "Translate: " + mTextToTranslate + "\nFrom: " + mLangFrom + " - " + mLangTo);
        Log.i("TranslateRepo", "Response: " + response);
        ArrayList<String> parsed = ParseJSON.parseTranslate(response);
        if (parsed != null && parsed.size() == DefineVar.RESPONE_LANG_SIZE) {
            this.mLangFrom = parsed.get(0);
            mResult = parsed.get(1);
        } else {
            mResult = null;
        }
        return mResult;
    }

    public Bitmap translateImage(Application application, Bitmap originBitmap, String mLangFrom, String mLangTo) {
        SparseArray<TextBlock> items = ApplicationUtil.detect(application, originBitmap);
        String textToTranslate = ApplicationUtil.collect(items);

        String result = translate(textToTranslate, mLangFrom, mLangTo);
        List<String> parseResult = Arrays.asList(result.split("\n"));
        Bitmap overlay = ApplicationUtil.getOverlayBitmap(items, parseResult, originBitmap);
        return overlay;
    }

    public String getLangFrom() {
        return mLangFrom;
    }

    private void reset(String mTextToTranslate, String mLangFrom, String mLangTo) {
        this.mTextToTranslate = mTextToTranslate;
        if (mLangFrom != null && mLangFrom.equalsIgnoreCase(DefineVar.AUTO_DETECT_LANG_CODE)) {
            this.mLangFrom = null;
        } else {
            this.mLangFrom = mLangFrom.toLowerCase();
        }
        this.mLangTo = mLangTo.toLowerCase();
        this.mResult = "";
    }
}
