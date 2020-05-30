package com.ardysyahputra.imagemachine.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ardysyahputra.imagemachine.GridItemView;
import com.ardysyahputra.imagemachine.Model.ImageModel;
import com.ardysyahputra.imagemachine.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<ImageModel> arrayList;
    ConstraintLayout overlay;
    public List<Integer> selectedPositions = new ArrayList<>();

    public ImageAdapter(Context context, ArrayList<ImageModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        selectedPositions = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public  View getView(int position, View convertView, ViewGroup parent) {
        GridItemView customView = (convertView == null) ? new GridItemView(context) : (GridItemView) convertView;
        if (arrayList.get(position).getPath()!=null) {
            File imgFile = new File(arrayList.get(position).getPath());


            if (imgFile.exists()) {
                String path = arrayList.get(position).getPath();

                customView.display(path, selectedPositions.contains(position));

            }
        }


        return customView;
    }

}
