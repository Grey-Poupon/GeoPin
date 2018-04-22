package team3.teamproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Steve on 22/04/2018.
 */
public class ImageHandler {
    private static final int PIC_WIDTH = 50;
    private static final int PIC_HEIGHT = 50;

    public static Drawable LoadImageFromURL(Resources resource) {
        try {
            URL url = ((User) LoginActivity.getLoginActivity().getApplication()).getUserImageURL();
            InputStream is = (InputStream) url.getContent();
            Drawable pic = Drawable.createFromStream(is, "url");
            Bitmap bit = ((BitmapDrawable)pic).getBitmap();
            Bitmap newBit = Bitmap.createScaledBitmap(bit, PIC_WIDTH, PIC_HEIGHT, false);

            return new BitmapDrawable(resource, newBit);
        } catch (Exception e) {
            return null;
        }
    }
}
