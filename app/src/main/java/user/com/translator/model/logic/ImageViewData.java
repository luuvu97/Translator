package user.com.translator.model.logic;

import android.graphics.drawable.Drawable;

public class ImageViewData {

    private Drawable mOriginDrawable;
    private Drawable mOverlayDrawable;

    public ImageViewData(Drawable mOriginDrawable, Drawable mOverlayDrawable) {
        this.mOriginDrawable = mOriginDrawable;
        this.mOverlayDrawable = mOverlayDrawable;
    }

    public Drawable getOriginDrawable() {
        return mOriginDrawable;
    }

    public Drawable getOverlayDrawable() {
        return mOverlayDrawable;
    }
}
