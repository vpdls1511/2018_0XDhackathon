package com.keen.android.happckathon.ui.dialogs;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.keen.android.happckathon.R;
import com.keen.android.happckathon.ui.activities.ContentActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Kimhyeongmin on 2018. 1. 20..
 */

public class MapDialog extends Dialog {

    private ImageView mapImage;
    private EditText titleText;
    private EditText contentText;

    private Button leftButton, rightButton;

    private Uri mapDialogImage;
    private Bitmap bmp;

    private View.OnClickListener leftClickEvent;
    private View.OnClickListener rightClickEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.1f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.map_dialog_layout);

        leftButton = findViewById(R.id.mapDialogCancel);
        rightButton = findViewById(R.id.mapDialogSuccess);

        mapImage = findViewById(R.id.mapimageView);
        titleText = findViewById(R.id.editTextTitle);
        contentText = findViewById(R.id.editTextContent);

        Glide.with(getContext())
                .load(mapDialogImage)
                .into(mapImage);

            leftButton.setOnClickListener(leftClickEvent);
            rightButton.setOnClickListener((View view) -> {
                if (titleText.getText().length() <= 0 || contentText.getText().length() <=0){
                    Toast.makeText(getContext(), "입력칸이 비어있습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    rightButton.setOnClickListener(rightClickEvent);
                }
            });


    }

    public MapDialog(Context context, Uri imageUri, View.OnClickListener mLeftClickEvent, View.OnClickListener mRightClickLister){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mapDialogImage = imageUri;
        this.leftClickEvent = mLeftClickEvent;
        this.rightClickEvent = mRightClickLister;
    }
}
