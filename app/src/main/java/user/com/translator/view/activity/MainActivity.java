package user.com.translator.view.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import user.com.translator.R;
import user.com.translator.view.fragment.CameraFragment;
import user.com.translator.view.fragment.CameraVideoFragment;
import user.com.translator.view.fragment.ImageViewFragment;
import user.com.translator.view.fragment.LangFragment;
import user.com.translator.viewmodel.MainVM;

public class MainActivity extends BaseActivity<MainVM> {

    private static final int REQUEST_CODE_GALLERY = 1234;

    private ImageView mIvGallery;
    private ImageViewFragment mImageViewFragment;
    private ImageView mIvCamera;

    private LangFragment mTransLangFragment;
    private CameraFragment mCameraFragment;
    private CameraFragment.OnTakeImageListener mCameraListener = new CameraVideoFragment.OnTakeImageListener() {
        @Override
        public void onImageAvailable(final Bitmap bitmap) {
            showCamera(false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mImageViewFragment.onNewImage(bitmap);
                    getViewModel().detect(bitmap);
                }
            }, 200);
        }
    };

    private boolean mIsCameraShow;

    private final String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
    };

    @Override
    public void bindLayout(View view) {
        mIvGallery = findViewById(R.id.iv_photo);
        mIvCamera = findViewById(R.id.iv_camera);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainVM getViewModel() {
        return ViewModelProviders.of(this).get(MainVM.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(permissions, REQUEST_CODE_GALLERY);
        mTransLangFragment = new LangFragment();

        showCamera(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_fragment, mTransLangFragment).commit();
        mCameraFragment.setImageListener(mCameraListener);
        mIvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                showCamera(false);
            }
        });
        mIvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsCameraShow) {
                    mCameraFragment.takePicture();
                } else {
                    showCamera(true);
                }
            }
        });

        getViewModel().getOverlayBitmap().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap overlayBitmap) {
                if (mImageViewFragment != null) {
                    mImageViewFragment.onOverlayImage(overlayBitmap);
                }
            }
        });
        getViewModel().getLangChanged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                getViewModel().detect();
            }
        });
    }

    void showCamera(boolean isShowCam) {
        mIsCameraShow = isShowCam;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isShowCam) {
            mCameraFragment = new CameraFragment();
            transaction.replace(R.id.main_content, mCameraFragment);
        } else {
            mImageViewFragment = new ImageViewFragment();
            transaction.replace(R.id.main_content, mImageViewFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            try {
                Uri imageUri = Uri.parse(data.getDataString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                mImageViewFragment.onNewImage(bitmap);
                getViewModel().detect(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showCamera(true);
        }
    }
}
