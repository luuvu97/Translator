package user.com.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import user.com.translator.common.ParseJSON;
import user.com.translator.model.db.LanguageDbo;

public class ApplicationUtil {

    private static final String TAG = "ApplicationUtil";

    public static List<LanguageDbo> getAllLang(Context context) {
        List<LanguageDbo> list = null;
        try {
            InputStream is = context.getAssets().open("languagev2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            list = ParseJSON.parseLanguage(new String(buffer, "UTF-8"));
            Collections.sort(list, new Comparator<LanguageDbo>() {
                @Override
                public int compare(LanguageDbo o1, LanguageDbo o2) {
                    return o1.getEnglishName().compareToIgnoreCase(o2.getEnglishName());
                }
            });
            list.add(0, LanguageDbo.getDefault());
        } finally {
            return list;
        }
    }

    public static SparseArray<TextBlock> detect(Context context, Bitmap bitmap) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        return textRecognizer.detect(frame);
    }

    public static Bitmap getOverlayBitmap(SparseArray<TextBlock> items, List<String> translateItems, Bitmap origin) {
        Bitmap bitmap = Bitmap.createBitmap(origin.getWidth(), origin.getHeight(), origin.getConfig());
        Canvas canvas = new Canvas(bitmap);

        canvas.drawBitmap(origin, new Matrix(), null);

        Paint paint = new Paint();
        paint.setColor(DefineVar.COLOR_OVERLAY_BACKGROUND);
        canvas.drawRect(new Rect(0, 0, origin.getWidth(), origin.getHeight()), paint);

        drawText(canvas, mirgate(items), translateItems);
        /*Todo: remove it*/
//        drawBoundingBox(canvas, items);
        return bitmap;
    };

    private static void drawBoundingBox(Canvas canvas, SparseArray<TextBlock> items) {
        Paint paint = new Paint();
        paint.setColor(DefineVar.COLOR_OVERLAY_BACKGROUND);
        for(int i= 0; i < items.size(); i++) {
            TextBlock block = items.valueAt(i);
            canvas.drawRect(block.getBoundingBox(), paint);
        }
    }

    private static void drawText(Canvas canvas, List<Text> items, List<String> translateText) {
        Paint paint = new Paint();
        paint.setColor(DefineVar.COLOR_OVERLAY_TEXT_COLOR);
        paint.setTypeface(Typeface.DEFAULT);

        for(int i = 0; i < items.size(); ++i) {
            if (translateText != null) {
                drawLine(paint, canvas, items.get(i), translateText.get(i));
            } else {
                drawLine(paint, canvas, items.get(i), items.get(i).getValue());
            }
        }
    }

    private static void drawLine(Paint paint, Canvas canvas, Text line, String translateText) {
        Rect rect = line.getBoundingBox();
        int length = line.getValue().length();

        int calHeight = rect.height() * 3 / 4;
//        int calWidth =  rect.width() * 2 / length;

        int textSize = calHeight;
        paint.setTextSize(textSize);

        int baseLineYAxis = (rect.bottom + rect.top) / 2 + textSize / 2;

        Log.i(TAG, "Draw: " + translateText + " at: " + rect.width() + " - " + rect.height() + " - " + textSize + " - " + rect.left + " - " + rect.top + " - " + rect.bottom);
        canvas.drawText(" " + translateText, rect.left, baseLineYAxis, paint);
    }

    public static String collect(SparseArray<TextBlock> items) {
        StringBuilder builder = new StringBuilder();
        for(int i= 0; i < items.size(); i++) {
            TextBlock block = items.valueAt(i);
            for (Text line : block.getComponents()) {
                builder.append(line.getValue()).append("\n");
            }
        }
        return builder.toString();
    }

    private static List<Text> mirgate(SparseArray<TextBlock> items) {
        List<Text> lines = new ArrayList<>();
        for(int i = 0; i< items.size(); i++){
            TextBlock item = (TextBlock) items.valueAt(i);
            for (Text line : item.getComponents()) {
                lines.add(line);
            }
        }
        return lines;
    }
}
