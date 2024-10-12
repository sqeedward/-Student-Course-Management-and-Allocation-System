package org.example;

public class StudentLicenta extends Student{

    public StudentLicenta(String nume) {
        super(nume);
    }

    public String toString(){
        return "Student Licenta: "+this.getNume()+" - "+this.getMedie()+" - "+this.getCurs()+"\n";
    }

}
