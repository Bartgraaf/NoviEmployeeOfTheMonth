package com.example.employeeofthemonth.Controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.employeeofthemonth.Models.ImageHandler;
import com.example.employeeofthemonth.R;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class ImageSaveShareActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap curr_image_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Novi medewerker van de Maand");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_share);
        String imagePath = getIntent().getStringExtra("imagePath");
        curr_image_bitmap = BitmapFactory.decodeFile(imagePath);
        imageView = findViewById(R.id.my_avatar);
        imageView.setImageBitmap(curr_image_bitmap);

        final ImageHandler imageHandler = new ImageHandler();


        final Button saveImage = findViewById(R.id.saveImage);
            saveImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                imageHandler.saveImage(curr_image_bitmap, v.getContext(), ImageSaveShareActivity.this);
            }
        });

        final Button shareImage = findViewById(R.id.shareImage);
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHandler.shareImage(curr_image_bitmap, v.getContext(), ImageSaveShareActivity.this);
            }
        });
    }
}
