package com.example.employeeofthemonth.Models;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import com.example.employeeofthemonth.EmpoyeeOfTheMonth;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1Ga
 */

public class Gadget {
    private int gadgetCount = 5;
    private int currGadget = 0;
    private Bitmap currResult;

    public Bitmap getCurrResult() {
        return currResult;
    }

    public int getCurrGadget() {
        return currGadget;
    }

    public void setCurrGadget(int currGadget) {
        this.currGadget = currGadget;
    }

    public int getGadgetCount() {
        return gadgetCount;
    }

    public void nextGadget(){
        if(this.getCurrGadget() == this.getGadgetCount()){
            this.setCurrGadget(0);
        }else{
            int newGadgetCount = this.getCurrGadget();
            newGadgetCount++;
            this.setCurrGadget(newGadgetCount);
        }
    }

    public void prevGadget(){
        if(this.getCurrGadget() == 0){
            this.setCurrGadget(this.getGadgetCount());
        }else{
            int newGadgetCount = this.getCurrGadget();
            newGadgetCount--;
            this.setCurrGadget(newGadgetCount);
        }
    }

    public void generateResult(){
        String gName = "g" + getCurrGadget();
        Context context = EmpoyeeOfTheMonth.getContext();
        Resources res = context.getResources();
        Drawable g = ResourcesCompat.getDrawable(res, context.getApplicationContext().getResources().getIdentifier(gName, "drawable",  context.getPackageName()), null);

        Bitmap anImage = ((BitmapDrawable) g).getBitmap();
        this.currResult = anImage;
    }

    public void generateBlank(){
        String gName = "g10";
        Context context = EmpoyeeOfTheMonth.getContext();
        Resources res = context.getResources();
        Drawable g = ResourcesCompat.getDrawable(res, context.getApplicationContext().getResources().getIdentifier(gName, "drawable",  context.getPackageName()), null);

        Bitmap anImage = ((BitmapDrawable) g).getBitmap();
        this.currResult = anImage;
    }
}
