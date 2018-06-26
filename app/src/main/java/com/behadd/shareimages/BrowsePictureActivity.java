package com.behadd.shareimages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BrowsePictureActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private Uri selectedImageUri;
    private ImageView selectedImage;
    private int check=0;

    @Override
    protected void onResume() {
        super.onResume();
        if(check==1) {
            try {
                Bitmap captureBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                selectedImage.setImageBitmap(captureBmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            check=0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_picture);

        selectedImage= findViewById(R.id.ivSelectedImage);



        findViewById(R.id.btnSelect).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                    }
                });

        findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri bmpUri= selectedImageUri ;
                if (bmpUri != null) {

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share_message)));       //NOT SHOWING share_message
                }
                else {
                    Toast.makeText(BrowsePictureActivity.this, "Select an image first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                check=1;
                LinearLayout.LayoutParams myparams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,7);
                findViewById(R.id.ivSelectedImage).setLayoutParams(myparams);
                selectedImageUri = data.getData();
//                Log.d("CHECK IT OUT", "onActivityResult: Uri "+selectedImageUri+" );
            }
        }
    }
}
