package com.techtask.android.picscramble.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.techtask.android.picscramble.R;

/**
 * Helper class for Glide library.
 */
public class GlideUtils {
    /**
     * Load the image from the url.
     *
     * @param context   App context.
     * @param imageUrl  url of the image.
     * @param imageView ImageView into which has to image loaded.
     */
    public static void displayImageFromUrl(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_image).into(imageView);
    }
}
