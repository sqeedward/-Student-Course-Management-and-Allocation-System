package org.example;

import java.time.Period;
import java.util.ArrayList;

public class Preferinta {
    private ArrayList<String> preferinte = new ArrayList<>();
    private final String student;

    public Preferinta(String student) {
        this.student = student;
    }

    public boolean existaPreferinta(String preferinta){
        for (String preferinta1 : preferinte) {
            if (preferinta1.equals(preferinta)) {
                return true;
            }
        }
        return false;
    }

    public static Preferinta getStudent(ArrayList<Preferinta> toatePref, String student){
        for (Preferinta preferinta : toatePref) {
            if (preferinta.student.equals(student)) {
                return preferinta;
            }
        }
        return null;
    }
    public String getStudent() {
        return student;
    }

    public void adaugaPreferinta(String preferinta){
        preferinte.add(preferinta);
    }

    public ArrayList<String> getPreferinte() {
        return preferinte;
    }

}
