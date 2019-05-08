package user.com.translator.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.text.TextBlock;

import user.com.translator.ApplicationUtil;
import user.com.translator.DefineVar;
import user.com.translator.repo.TranslateLangRepo;
import user.com.translator.repo.TranslateRepo;

public class MainVM extends AndroidViewModel {

    private TranslateLangRepo mLangRepo;
    private Bitmap mOriginBitmap;
    private BitmapLiveData mOverlayBitmap;
    private TranslateRepo mTranslateRepo;
    private MutableLiveData<Boolean> mLangChanged;

    public MainVM(@NonNull Application application) {
        super(application);
        mLangRepo = TranslateLangRepo.getInstance(application);
        mOverlayBitmap = new BitmapLiveData();
        mTranslateRepo = TranslateRepo.getInstance();
        mLangChanged = mLangRepo.getLangChanged();
    }

    public MutableLiveData<Bitmap> getOverlayBitmap() {
        return mOverlayBitmap;
    }

    public void detect() {
        mOverlayBitmap.detect(mOriginBitmap);
    }

    public void detect(final Bitmap originBitmap) {
        mOriginBitmap = originBitmap;
        mOverlayBitmap.detect(originBitmap);
    }

    public MutableLiveData<Boolean> getLangChanged() {
        return mLangChanged;
    }

    private class BitmapLiveData extends MutableLiveData<Bitmap> {

        private void detect(final Bitmap originBitmap) {
            mTranslateRepo.postRunable(new Runnable() {
                @Override
                public void run() {
                    try {

                        String langFrom = mLangRepo.getLangFrom().getCode();
                        String langTo = mLangRepo.getLangTo().getCode();

                        Log.i("MainVM", "Detecting");
                        SparseArray<TextBlock> items = mTranslateRepo.detect(getApplication(), originBitmap);

                        Log.i("MainVM", "Translating");
                        postValue(mTranslateRepo.getOcrBitmap(getApplication(), originBitmap, items));
                        postValue(mTranslateRepo.translateImage(getApplication(), originBitmap, items, langFrom, langTo));

                        if (langFrom.equalsIgnoreCase(DefineVar.AUTO_DETECT_LANG_CODE)) {
                            mLangRepo.setAutoDetectLang(mTranslateRepo.getLangFrom());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mTranslateRepo.destroy();
    }
}
