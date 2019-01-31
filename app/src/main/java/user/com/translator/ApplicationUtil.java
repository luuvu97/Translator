package user.com.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import user.com.translator.common.ParseJSON;
import user.com.translator.model.logic.Language;

public class ApplicationUtil {

    public static List<Language> getAllLang(Context context) {
        List<Language> list = null;
        try {
            InputStream is = context.getAssets().open("language.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            list = ParseJSON.parseLanguage(new String(buffer, "UTF-8"));
            Collections.sort(list, new Comparator<Language>() {
                @Override
                public int compare(Language o1, Language o2) {
                    return o1.nativeName.compareToIgnoreCase(o2.nativeName);
                }
            });
        } finally {
            return list;
        }
    }

    public static SparseArray<TextBlock> detect(Context context, Bitmap bitmap) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        return textRecognizer.detect(frame);
    }

    public static Bitmap getOverlayBitmap(SparseArray<TextBlock> items, Bitmap origin) {
        Bitmap bitmap = Bitmap.createBitmap(origin.getWidth(), origin.getHeight(), origin.getConfig());
        Canvas canvas = new Canvas(bitmap);

        canvas.drawBitmap(origin, new Matrix(), null);

        Paint paint = new Paint();
        paint.setColor(0xCC444444);
        canvas.drawRect(new Rect(0, 0, origin.getWidth(), origin.getHeight()), paint);

        drawText(canvas, items, null);

        return bitmap;
    };

    private static void drawText(Canvas canvas, SparseArray<TextBlock> items, List<String> translateText) {
        Paint paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        for(int i = 0; i< items.size(); i++){
            TextBlock item = (TextBlock) items.valueAt(i);
            for (Text line : item.getComponents()) {
                drawLine(paint, canvas, line, line.getValue());
            }
        }
    }

    private static void drawLine(Paint paint, Canvas canvas, Text line, String translateText) {
        Rect rect = line.getBoundingBox();
        int length = line.getValue().length();

        int calHeight = rect.bottom - rect.top;
        int calWidth =  (rect.right - rect.left) / length;

        int textSize = Math.max(calHeight, calWidth);
        paint.setTextSize(textSize);

        canvas.drawText(line.getValue(), rect.left + textSize / 3, rect.bottom, paint);
    }
}
