package com.example.employeeofthemonth.Controllers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.employeeofthemonth.Models.Dialog;
import com.example.employeeofthemonth.Models.Employee;
import com.example.employeeofthemonth.Models.ImageRequester;
import com.example.employeeofthemonth.R;
import java.io.File;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText employee;
    private int requestCodeFinal;
    private Uri selectedImageU;
    private String currentPhotoPath;
    private int rotateCounter = 0;

    public Uri getSelectedImageU() {
        return selectedImageU;
    }

    public void setSelectedImageU(Uri selectedImageU) {
        this.selectedImageU = selectedImageU;
    }

    public int getRequestCode() {
        return requestCodeFinal;
    }

    public void setRequestCode(int requestCode) {
        this.requestCodeFinal = requestCode;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Novi medewerker van de Maand");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.my_avatar);

        if(!MainActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            findViewById(R.id.takePhotoButton).setVisibility(View.GONE);
        }


        final ImageRequester ImageRequester = new ImageRequester();

        final Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequester.selectImageButton();
                setRequestCode(0);
                rotateCounter = 0;
                startActivityForResult(ImageRequester.getRequestIntent(), getRequestCode());
            }
        });


        final Button takePhotoButton = findViewById(R.id.takePhotoButton);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setRequestCode(1);

                String fileName = "photo";
                File storeDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                if(ImageRequester.takeImageButton(fileName, storeDirectory, MainActivity.this)){
                    currentPhotoPath = ImageRequester.getCurrentPhotoPath();
                }
                rotateCounter = 0;
                startActivityForResult(ImageRequester.getRequestIntent(), getRequestCode());
            }
        });

        final Button rotateImage = findViewById(R.id.rotateImage);
        rotateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                rotateCounter++;
                imageView.setImageBitmap(rotatedBitmap);
            }
        });

        final Button nextStep = findViewById(R.id.nextStep);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee = findViewById(R.id.employee);
                Employee EmployeeOfTheMonth = new Employee();
                EmployeeOfTheMonth.setFirstName(employee.getText().toString());
                if(EmployeeOfTheMonth.nameIsValid()){
                    if(getRequestCode() == 0){
                        /* Passing ImageURI to the Second Activity */
                        Intent IntentGallery = new Intent(v.getContext(), ImageEditActivity.class);
                        IntentGallery.setData(getSelectedImageU());
                        IntentGallery.putExtra("employeeName", EmployeeOfTheMonth.getFirstName());
                        IntentGallery.putExtra("rotateCounter", String.valueOf(rotateCounter));
                        startActivity(IntentGallery);

                    }else if(getRequestCode() == 1){
                        /* Passing BITMAP to the Second Activity */
                        Intent IntentCamera = new Intent(v.getContext(), ImageEditActivity.class);
                        IntentCamera.putExtra("imagePath", currentPhotoPath);
                        IntentCamera.putExtra("rotateCounter", String.valueOf(rotateCounter));
                        IntentCamera.putExtra("employeeName", EmployeeOfTheMonth.getFirstName());
                        startActivity(IntentCamera);
                    }
                }else{
                    openDialog();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setRequestCode(requestCode);
        if (resultCode != RESULT_CANCELED) {
            final LinearLayout nextStepContainer = findViewById(R.id.nextStepContainer);
            switch (requestCode) {
                case 0:
                    //Gallery
                    if (resultCode == RESULT_OK && data != null) {
                        setSelectedImageU(data.getData());
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (getSelectedImageU() != null) {
                            Cursor cursor = getContentResolver().query(getSelectedImageU(),
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                imageView.setImageURI(getSelectedImageU());
                                cursor.close();

                            }
                        }
                    }
                    break;
                case 1:
                    //Photo
                    if (resultCode == RESULT_OK) {
                        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
            }
            nextStepContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog() {
        Dialog employeeDialog = new Dialog();
        employeeDialog.setDialogTitle("Informatie");
        employeeDialog.setDialogText("De naam die je hebt ingevuld is niet toegestaan, gebruik een andere naam.");

        employeeDialog.show(getSupportFragmentManager(), "wrong_employee_name");
    }
}