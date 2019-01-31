package user.com.translator.view.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.io.IOException;
import java.util.List;

import user.com.translator.ApplicationUtil;
import user.com.translator.R;
import user.com.translator.common.Translator;
import user.com.translator.view.fragment.ImageViewFragment;
import user.com.translator.view.fragment.TranslateLangFragment;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_CODE_GALLERY = 1234;

    private ImageView mIvGallery;
    private ImageViewFragment mImageViewFragment;
    private TranslateLangFragment mTransLangFragment;

    private final String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
    };

    @Override
    public void bindLayout(View view) {
        mIvGallery = findViewById(R.id.iv_photo);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(permissions, 12344);
        mImageViewFragment = new ImageViewFragment();
        mTransLangFragment = new TranslateLangFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mImageViewFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_fragment, mTransLangFragment).commit();
        mIvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Translator translator = new Translator("Hello Vietnam\nHi Japan", null, "vi");
                translator.translate();
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            try {
                Uri imageUri = Uri.parse(data.getDataString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                mImageViewFragment.onNewImage(bitmap);
                SparseArray<TextBlock> items = ApplicationUtil.detect(getApplicationContext(), bitmap);
                Bitmap overlay = ApplicationUtil.getOverlayBitmap(items, bitmap);
                if (items.size() > 0) {
                    String lang = items.valueAt(0).getLanguage();
                    mTransLangFragment.setAutoDetectLang(lang);
                }
                mImageViewFragment.onOverlayImage(overlay);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
