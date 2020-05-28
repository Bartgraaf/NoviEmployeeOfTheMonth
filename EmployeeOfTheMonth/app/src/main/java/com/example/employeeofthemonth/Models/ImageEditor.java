package com.example.employeeofthemonth.Models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class ImageEditor {

    public static Bitmap writeTextOnDrawable(Bitmap src, String string, int size, boolean underline, int width , int height) {
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        int xPos = (canvas.getWidth() / 2);
        int yPos = canvas.getHeight() - 20 ;
        double relation = Math.sqrt(canvas.getWidth() * canvas.getHeight()) / 250;
        canvas.drawBitmap(src, 0, 0, null);
        Paint paint = new Paint();
        Paint.FontMetrics fm = new Paint.FontMetrics();
        paint.setColor(Color.WHITE);
        paint.setTextSize((float) (size * relation));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getFontMetrics(fm);
        int margin = 5;
        canvas.drawRect(0, yPos + fm.top - margin,
                canvas.getWidth(), yPos + 1005 + fm.bottom
                        + margin, paint);

        paint.setColor(Color.BLACK);
        canvas.drawText(string, xPos, yPos, paint);
        return result;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public static Bitmap putOverlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp2.getWidth(), bmp2.getHeight(), bmp1.getConfig());
        float left =(bmp2.getWidth() - (bmp1.getWidth()*((float)bmp2.getHeight()/(float)bmp1.getHeight())))/(float)2.0;
        float bmp1newW = bmp1.getWidth()*((float)bmp2.getHeight()/(float)bmp1.getHeight());
        Bitmap bmp1new = getResizedBitmap(bmp1, bmp2.getHeight(), (int)bmp1newW);
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1new, left ,0 , null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }

}
