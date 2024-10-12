package org.example;

public class Student {
    //final pt ca nu se schimba numele studentului
    private final String nume;
    private double medie;

    private String curs;

    public Student(String nume) {
        this.nume = nume;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public double getMedie() {
        return medie;
    }

    public String getNume() {
        return nume;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }

    public String getCurs() {
        return curs;
    }

    public String toString() {
        return nume + " - " + medie + " - " + curs + "\n";
    }

    @Override
    public boolean equals(Object stud) {
        Student student2 = (Student) stud;
        return student2.getNume().equals(this.getNume());
    }
}
