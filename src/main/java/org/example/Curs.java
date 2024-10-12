package org.example;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Math.abs;

public class Curs <studs extends Student>{
    private final String denumire;
    private final int capacitate;
    private int inrolati;
    ArrayList<studs> studenti = new ArrayList<>();
    private double ultimaMedie;


    public Curs(String denumire, int capacitate) {
        this.denumire = denumire;
        this.capacitate = capacitate;
        this.inrolati = 0;
    }

    public void adaugaStudent(studs student){
        studenti.add(student);
        ultimaMedie = student.getMedie();
        inrolati++;
    }


    //sorteaza studentii alfabetic.
    public void sorteazaStudenti(){
        studenti.sort(new comparamNume());
    }

    private static class comparamNume implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getNume().compareTo(s2.getNume());
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(denumire);
        sb.append(" (").append(capacitate).append(")\n");
        for (studs student : studenti) {
            sb.append(student.getNume());
            sb.append(" - ");
            sb.append(student.getMedie());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getDenumire() {
        return denumire;
    }

    public boolean maiSuntLocuri(){
        return inrolati < capacitate;
    }


    //daca mai sunt locuri sau daca media studentului este egala cu ultima medie
    //inca poate sa se inscrie la curs.
    public boolean potInscrie(Student student) {
        return maiSuntLocuri() || (abs(student.getMedie() - ultimaMedie) < 0.01d);
    }

}
