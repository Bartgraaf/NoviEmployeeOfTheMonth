package com.example.employeeofthemonth.Models;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class Dialog extends AppCompatDialogFragment {
    private String dialogTitle;
    private String dialogText;

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public void setDialogText(String dialogText) {
        this.dialogText = dialogText;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public String getDialogText() {
        return dialogText;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(this.getDialogTitle())
            .setMessage(this.getDialogText())
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        return builder.create();

    }
}
