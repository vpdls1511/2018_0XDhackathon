package com.keen.android.happckathon.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.keen.android.happckathon.R;
import com.keen.android.happckathon.ui.activities.ContentActivity;

import java.net.URL;

/**
 * Created by Kimhyeongmin on 2018. 1. 20..
 */

public class MapDialog extends Dialog {

    private ImageView mapImage;
    private EditText titleText;
    private EditText contentText;

    private Button leftButton, rightButton;

    private String mapDialogImage;

    private View.OnClickListener leftClickEvent;
    private View.OnClickListener rightClickEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.map_dialog_layout);

        leftButton = findViewById(R.id.mapDialogCancel);
        rightButton = findViewById(R.id.mapDialogSuccess);

        mapImage = findViewById(R.id.mapimageView);
        titleText = findViewById(R.id.editTextTitle);
        contentText = findViewById(R.id.editTextContent);

        mapImage.setImageURI(Uri.parse(mapDialogImage));

        if (leftButton != null && rightButton != null){
            leftButton.setOnClickListener(leftClickEvent);
            rightButton.setOnClickListener(rightClickEvent);
        }else{

        }


    }

    public MapDialog(Context context, String imageUri, View.OnClickListener mLeftClickEvent){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mapDialogImage = imageUri;
        this.leftClickEvent = mLeftClickEvent;
    }
}
