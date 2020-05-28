package com.example.employeeofthemonth.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.employeeofthemonth.Models.Gadget;
import com.example.employeeofthemonth.Models.ImageEditor;
import com.example.employeeofthemonth.Models.ImageRequester;
import com.example.employeeofthemonth.Models.Month;
import com.example.employeeofthemonth.R;

import java.io.IOException;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class ImageEditActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap curr_image_bitmap;
    private Bitmap result;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Novi medewerker van de Maand");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);
        imageView = findViewById(R.id.my_avatar);

        Uri selectedImgUri = getIntent().getData();
        String employeeName = getIntent().getStringExtra("employeeName");
        String imagePath = getIntent().getStringExtra("imagePath");
        String rotateCounter = getIntent().getStringExtra("rotateCounter");
        int rotateCounterInt = Integer.parseInt(rotateCounter);

        if (selectedImgUri != null) {
            Log.e("Gallery ImageURI", "" + selectedImgUri);
            String[] selectedImgPath = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImgUri,
                    selectedImgPath, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            try {
                curr_image_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selectedImgUri == null) {
            curr_image_bitmap = BitmapFactory.decodeFile(imagePath);
        }

        if (curr_image_bitmap != null) {
            Month currentMonth = new Month();
            currentMonth.setCurrentMonth();
            String imageText = "Novi medewerker van " + currentMonth.getMonth() + " is " + employeeName;

            int rotateDegree = rotateCounterInt*90;
            Matrix matrix = new Matrix();
            matrix.postRotate(-rotateDegree);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(curr_image_bitmap, curr_image_bitmap.getWidth(), curr_image_bitmap.getHeight(), true);
            curr_image_bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            System.out.println(rotateCounterInt);

            Gadget blankGadget = new Gadget();
            blankGadget.generateBlank();
            curr_image_bitmap = ImageEditor.putOverlay(curr_image_bitmap, blankGadget.getCurrResult());
            result = ImageEditor.writeTextOnDrawable(curr_image_bitmap, imageText, 8, false, curr_image_bitmap.getWidth() , curr_image_bitmap.getHeight());

            imageView.setImageBitmap(result);
        }
        final Gadget gadget = new Gadget();

        final Button applyGadget = findViewById(R.id.applyGadget);
        applyGadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = ImageEditor.putOverlay(result, gadget.getCurrResult());
            }
        });


        final Button prevGadget = findViewById(R.id.prevGadget);
        prevGadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gadget.prevGadget();
                if(gadget.getCurrGadget() != 0){
                    gadget.generateResult();
                    Bitmap resultPrev = ImageEditor.putOverlay(result, gadget.getCurrResult());
                    imageView.setImageBitmap(resultPrev);
                    applyGadget.setVisibility(View.VISIBLE);
                }else{
                    applyGadget.setVisibility(View.GONE);
                    imageView.setImageBitmap(result);
                }
            }
        });

        final Button nextGadget = findViewById(R.id.nextGadget);
        nextGadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gadget.nextGadget();
                if(gadget.getCurrGadget() != 0){
                    gadget.generateResult();
                    Bitmap resultPrev = ImageEditor.putOverlay(result, gadget.getCurrResult());
                    imageView.setImageBitmap(resultPrev);
                    applyGadget.setVisibility(View.VISIBLE);
                }else{
                    applyGadget.setVisibility(View.GONE);
                    imageView.setImageBitmap(result);
                }
            }
        });

        final Button nextStepSend = findViewById(R.id.nextStepSend);
        nextStepSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequester ImageRequester = new ImageRequester();
                if(ImageRequester.storeImage(result, v.getContext())){
                    currentPhotoPath = ImageRequester.getCurrentPhotoPath();
                    Intent IntentCamera = new Intent(v.getContext(), ImageSaveShareActivity.class);
                    IntentCamera.putExtra("imagePath", currentPhotoPath);
                    startActivity(IntentCamera);
                }
            }
        });

    }
}
