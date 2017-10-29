package com.example.huangyuwei.myapplication.mem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.huangyuwei.myapplication.CameraActivity;
import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.adapter.GridViewAdapter;
import com.example.huangyuwei.myapplication.adapter.ImageItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class mem_food extends Fragment {
    private GridView gridView;
    public GridViewAdapter gridAdapter;
    Button photo_eat;
    public mem_food() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_food, container, false);

    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        // It have to be matched with the directory in SDCard
        String ExternalStorageDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Cancer/";
        String targetPath = ExternalStorageDirectoryPath;
        File targetDirector = new File(targetPath);

        File[] files = targetDirector.listFiles();
        if(targetDirector.listFiles()!=null) {
            for (File file : files) {
                imageItems.add(new ImageItem(file.getAbsolutePath(), file.getAbsolutePath()));
            }
        }

        return imageItems;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView = (GridView) getView().findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick (AdapterView < ? > parent, View v,int position, long id){
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View view = factory.inflate(R.layout.image_dialog, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.dialog_imageview);
                imageView.setImageBitmap(decodeURI(item.getImage()));
                android.app.AlertDialog d = new android.app.AlertDialog.Builder(getActivity())
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, null)
                        .setTitle(item.getTitle())
                        .create();


                d.show();

            }
        });
        photo_eat= (Button)getView().findViewById(R.id.photo_eat);
        photo_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity() , CameraActivity.class);
                startActivity(intent);
            }
        });

    }


    public Bitmap decodeURI(String path){

        Bitmap bm = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
        //BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        //options.inSampleSize = calculateInSampleSize(options, );

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }

        return inSampleSize;
    }

}


