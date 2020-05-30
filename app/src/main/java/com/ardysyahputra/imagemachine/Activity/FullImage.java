package com.ardysyahputra.imagemachine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.ardysyahputra.imagemachine.R;

import java.io.File;

public class FullImage extends AppCompatActivity {

    ImageView fullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        fullImage = findViewById(R.id.imageFull);

        Intent intent = getIntent();
        String path =  intent.getStringExtra("path");

        File imgFile = new File(path);
        if (imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            fullImage.setImageBitmap(myBitmap);



        }
    }
}
