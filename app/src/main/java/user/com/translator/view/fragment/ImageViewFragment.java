package user.com.translator.view.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.ToggleButton;

import de.hdodenhof.circleimageview.CircleImageView;
import user.com.translator.R;
import user.com.translator.common.TouchImageView;
import user.com.translator.interf.IImageView;

public class ImageViewFragment extends BaseFragment implements IImageView {

    private TouchImageView mImage;
    private CircleImageView mSwitch;
    private boolean mIsVisible = false;

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
    public ViewModel getViewModel() {
        return null;
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
            mSwitch.setImageResource(R.drawable.ic_visible_on);
            mIsVisible = true;
            mImage.setImageBitmap(mOverlayBitmap);
            mSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchShowMode();
                }
            });
        }
    }

    private void switchShowMode() {
        if(mIsVisible){
            mSwitch.setImageResource(R.drawable.ic_visible_off);
            mIsVisible = false;
            mImage.setImageBitmap(mOriginBitmap);
        }else{
            mSwitch.setImageResource(R.drawable.ic_visible_on);
            mIsVisible = true;
            mImage.setImageBitmap(mOverlayBitmap);
        }
    }

    @Override
    public void onNewImage(Bitmap origin) {
        mOriginBitmap = origin;
        mImage.resetZoom();
        setOverlayDrawable(null);
    }

    @Override
    public void onOverlayImage(Bitmap overlay) {
        setOverlayDrawable(overlay);
    }
}
