package com.example.employeeofthemonth.Models;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import android.content.Context;
import java.io.OutputStream;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class ImageHandler {

    public void saveImage(Bitmap bitmap, Context v, Activity activity){
        ContentResolver cr = v.getContentResolver();
        String title = "Employee_of_the_month_novi";
        String description = "My bitmap created by Android-er";
        String savedURL = MediaStore.Images.Media
                .insertImage(cr, bitmap, title, description);

        Toast.makeText(activity,
                savedURL,
                Toast.LENGTH_LONG).show();
    }

    public void shareImage(Bitmap bitmap, Context v, Activity activity){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = v.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);


        OutputStream outstream;
        try {
            outstream = v.getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        share.putExtra(Intent.EXTRA_STREAM, uri);
        v.startActivity(Intent.createChooser(share, "Share Image"));
    }
}
