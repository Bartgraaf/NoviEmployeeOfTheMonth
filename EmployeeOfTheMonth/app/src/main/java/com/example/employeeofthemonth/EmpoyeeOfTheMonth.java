package com.example.employeeofthemonth;

import android.app.Application;
import android.content.Context;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class EmpoyeeOfTheMonth extends Application {
    private static EmpoyeeOfTheMonth instance;

    public EmpoyeeOfTheMonth() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
