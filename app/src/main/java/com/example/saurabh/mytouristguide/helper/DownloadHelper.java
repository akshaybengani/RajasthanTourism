package com.example.saurabh.mytouristguide.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.saurabh.mytouristguide.R;

/**
 * Created by Saurabh on 29-Jun-17.
 */

public class DownloadHelper {

    public static void loadImagesWithGlide(Context context, int imageRes, ImageView imageView)
    {
        Glide.with(context)
                .load(imageRes)
                .placeholder(R.drawable.clone)
                .crossFade()
                .into(imageView);
    }

    public static void loadImageWithGlideURL(Context context, String url, ImageView imageView)
    {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.clone)
                .crossFade()
                .into(imageView);
    }
}
