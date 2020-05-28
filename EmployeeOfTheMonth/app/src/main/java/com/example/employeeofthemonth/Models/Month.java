package com.example.employeeofthemonth.Models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class Month {
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        setMonth(new SimpleDateFormat("MMM").format(cal.getTime()));
    }
}
