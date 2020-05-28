package com.example.employeeofthemonth.Models;

/**
 * @Auteur  Bart de Graaf
 * @Date 27-05-2020
 * @Leerlijn Software Development Praktijk 1
 */

public class Employee extends Person{
    public boolean nameIsValid(){
        return this.getFirstName().matches("[a-zA-Z]+");
    }
}
