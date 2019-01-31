package user.com.translator.interf;

import android.graphics.Bitmap;

public interface IImageView {

    public void onNewImage(Bitmap origin);

    public void onOverlayImage(Bitmap overlay);
}
