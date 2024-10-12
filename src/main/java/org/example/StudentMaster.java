package org.example;

public class StudentMaster extends Student{

    public StudentMaster(String nume) {
        super(nume);
    }

    public String toString(){
        return "Student Master: "+this.getNume()+" - "+this.getMedie()+" - "+this.getCurs()+"\n";
    }

}
