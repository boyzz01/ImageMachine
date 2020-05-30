package com.ardysyahputra.imagemachine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ardysyahputra.imagemachine.Model.ImageModel;

import java.io.File;
import java.util.ArrayList;

public class GridItemView extends FrameLayout {

    private ImageView imageView;
    private ConstraintLayout overlay;
    ArrayList<ImageModel> arrayList;

    public GridItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.single_image_layout, this);
        imageView = getRootView().findViewById(R.id.image);
        overlay = getRootView().findViewById(R.id.overlay);
        arrayList = new ArrayList<>();
    }

    public void display(String path, boolean isSelected) {

            File imgFile = new File(path);
            if (imgFile.exists())
            {
                final int THUMBSIZE = 512;
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imgFile.getAbsolutePath()),
                        THUMBSIZE, THUMBSIZE);


                imageView.setImageBitmap(ThumbImage);

            }
        display(isSelected);
    }

    public void display(boolean isSelected) {
        overlay.setVisibility(isSelected? View.VISIBLE: View.GONE);
    }
}