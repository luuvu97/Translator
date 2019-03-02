package user.com.translator.view.activity;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import user.com.translator.R;
import user.com.translator.view.fragment.CameraFragment;

public class TestActivity extends BaseActivity<ViewModel> {

    ImageView mIvPhoto;
    CameraFragment mCameraFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCameraFragment = new CameraFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mCameraFragment).commit();
        mIvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraFragment.takePicture();
            }
        });
    }

    @Override
    public void bindLayout(View view) {
        mIvPhoto = findViewById(R.id.iv_photo);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public ViewModel getViewModel() {
        return null;
    }
}
