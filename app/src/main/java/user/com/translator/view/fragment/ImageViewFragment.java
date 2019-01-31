package user.com.translator.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import user.com.translator.R;
import user.com.translator.common.TouchImageView;
import user.com.translator.interf.IImageView;

public class ImageViewFragment extends BaseFragment implements IImageView {

    private TouchImageView mImage;
    private Switch mSwitch;

    private Bitmap mOriginBitmap;
    private Bitmap mOverlayBitmap;

    @Override
    public void bindLayout(View view) {
        mImage = view.findViewById(R.id.image);
        mSwitch = view.findViewById(R.id.sw_image);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_image_view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setOverlayDrawable(Bitmap overlay){
        mOverlayBitmap = overlay;
        if(overlay == null){
            mSwitch.setVisibility(View.GONE);
            mImage.setImageBitmap(mOriginBitmap);
        }else{
            mImage.setImageBitmap(mOverlayBitmap);
            mSwitch.setVisibility(View.VISIBLE);
            mSwitch.setChecked(true);
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        mImage.setImageBitmap(mOverlayBitmap);
                    }else{
                        mImage.setImageBitmap(mOriginBitmap);
                    }
                }
            });
        }
    }

    @Override
    public void onNewImage(Bitmap origin) {
        mOriginBitmap = origin;
        setOverlayDrawable(null);
    }

    @Override
    public void onOverlayImage(Bitmap overlay) {
        setOverlayDrawable(overlay);
    }
}
