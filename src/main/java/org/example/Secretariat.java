package org.example;

import java.util.Comparator;
import java.util.ArrayList;

class ExistaStudentul extends Exception {
    public ExistaStudentul(String message) {
        super(message);
    }
}

public class Secretariat {

    private final ArrayList<Preferinta> preferinte = new ArrayList<>();
    private final ArrayList<Student> totiStudentii = new ArrayList<>();

    private final ArrayList<StudentMaster> stdentiiMaster = new ArrayList<>();
    private final ArrayList<StudentLicenta> studentiiLicenta = new ArrayList<>();

    private final ArrayList<Curs<StudentLicenta>> cursuriLicenta = new ArrayList<>();
    private final ArrayList<Curs<StudentMaster>> cursuriMaster = new ArrayList<>();



    //adaugam studenti in baza de date, adaugam aceasi referinta in toti studentii
    //atributul ajuta ca sa gasim studenti indiferent daca sunt de la licenta sau master.
    //Daca exista deja studentul aruncam o exceptie facuta de noi sa atentionam ca exista.
    public void adaugaStudent(Student student, String tip) throws ExistaStudentul {
        if (existaStudentul(student)) {
            throw new ExistaStudentul("Studentul exista deja");
        }
        StudentMaster master;
        StudentLicenta licenta;

        if (tip.equals("master")) {
            master = new StudentMaster(student.getNume());
            stdentiiMaster.add(master);
            totiStudentii.add(master);
        } else if (tip.equals("licenta")){
            licenta = new StudentLicenta(student.getNume());
            studentiiLicenta.add(licenta);
            totiStudentii.add(licenta);
        } else {
            throw new RuntimeException("Nu exista tipul: " + tip);
        }
    }



    public void adaugaNota(String nume, double medie) {
        Student student = this.gastesteStudentul(nume);
        if (student == null) {
            throw new RuntimeException("Nu exista studentul: " + nume);
        }
        student.setMedie(medie);
    }

    public void adaugaCurs(String tip, String nume, String capacitate) {
        int cap = Integer.parseInt(capacitate);

        if (tip.equals("licenta")) {
            Curs<StudentLicenta> curs = new Curs<>(nume, cap);
            cursuriLicenta.add(curs);
        } else if (tip.equals("master")) {
            Curs<StudentMaster> curs = new Curs<>(nume, cap);
            cursuriMaster.add(curs);
        } else {
            throw new RuntimeException("Tipul de curs nu este licenta sau master");
        }
    }

    public void adaugaPreferinta(String student, ArrayList<String> preferinte) {
        Preferinta preferinta = new Preferinta(student);
        for (String prefer: preferinte) {
            preferinta.adaugaPreferinta(prefer);
        }

        this.preferinte.add(preferinta);
    }


    //repartizam studentii in functie de preferinte
    //verificam daca sunt de la licenta sau master, ca sa ii repartizam in
    //baza de date corecta.
    public void repartizeaza() {
        sortareStudenti();

        Preferinta pref;
        StudentLicenta studLic;
        StudentMaster studMas;

        for (Student stud : totiStudentii) {
            //daca foloseam static ramaneau de la celelate teste
            pref = Preferinta.getStudent(preferinte, stud.getNume());
            if (esteLicenta(stud)) {
                studLic = getLicenta(stud);
                if (pref != null)
                    repartLicenta(studLic, pref);
            }
            else {
                studMas = getCursMaster(stud);
                if (pref != null)
                    repartMaster(studMas, pref);
            }
        }
    }

    private void repartLicenta(StudentLicenta stud, Preferinta pref) {
        for (String preferinta : pref.getPreferinte()) {
            Curs curs = getCursLicenta(preferinta);
            if (curs == null) {
                throw new RuntimeException("Nu exista cursul la licenta: " + preferinta);
            } else if (curs.potInscrie(stud)) {
                curs.adaugaStudent(stud);
                stud.setCurs(preferinta);
                return;
            }
        }

        sortamCursuri();
        for (Curs curs : cursuriLicenta) {
            if (curs.maiSuntLocuri()) {
                curs.adaugaStudent(stud);
                stud.setCurs(curs.getDenumire());
                return;
            }
        }

    }

    public Curs getCursLicenta(String nume) {
        for (Curs curs : cursuriLicenta) {
            if (curs.getDenumire().equals(nume)) {
                return curs;
            }
        }
        return null;
    }

    private void repartMaster(StudentMaster stud, Preferinta pref) {
        for (String preferinta : pref.getPreferinte()) {
            Curs curs = getCursMaster(preferinta);
            if (curs == null) {
                throw new RuntimeException("Nu exista cursul la master: " + preferinta);
            } else if (curs.potInscrie(stud)) {
                curs.adaugaStudent(stud);
                stud.setCurs(preferinta);
                return;
            }
        }

        sortamCursuri();
        for (Curs curs : cursuriMaster) {
            if (curs.maiSuntLocuri()) {
                curs.adaugaStudent(stud);
                stud.setCurs(curs.getDenumire());
                return;
            }
        }
    }

    public Curs getCursMaster(String nume) {
        for (Curs curs : cursuriMaster) {
            if (curs.getDenumire().equals(nume)) {
                return curs;
            }
        }
        return null;
    }

    public void sortareStudenti() {
        totiStudentii.sort(new comparamStudenti());
    }

    private Boolean esteLicenta(Student stud) {
        for (StudentLicenta studentLicenta : studentiiLicenta) {
            if (studentLicenta.equals(stud)) {
                return true;
            }
        }
        return false;
    }


    //Functie de comparare pentru sortarea studentilor, dupa medie si nume
    private static class comparamStudenti implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            if (s1.getMedie() < s2.getMedie()) {
                return 1;
            } else if (s1.getMedie() > s2.getMedie()) {
                return -1;
            } else {
                return s1.getNume().compareTo(s2.getNume());
            }
        }
    }

    public boolean existaStudentul(Student student) {
        for (Student stud : totiStudentii) {
            if (stud.equals(student)) {
                return true;
            }
        }
        return false;
    }

    public Student gastesteStudentul(String nume) {
        for (Student stud : totiStudentii) {
            if (stud.getNume().equals(nume)) {
                return stud;
            }
        }
        return null;
    }

    private StudentLicenta getLicenta(Student stud) {
        for (StudentLicenta studentLicenta : studentiiLicenta) {
            if (studentLicenta.equals(stud)) {
                return studentLicenta;
            }
        }
        return null;
    }

    public StudentMaster getCursMaster(Student stud) {
        for (StudentMaster studentMaster : stdentiiMaster) {
            if (studentMaster.equals(stud)) {
                return studentMaster;
            }
        }
        return null;
    }

    public void sortamCursuri() {
        cursuriLicenta.sort(new Comparator<Curs<StudentLicenta>>() {
            @Override
            public int compare(Curs<StudentLicenta> c1, Curs<StudentLicenta> c2) {
                return c1.getDenumire().compareTo(c2.getDenumire());
            }
        });

        cursuriMaster.sort(new Comparator<Curs<StudentMaster>>() {
            @Override
            public int compare(Curs<StudentMaster> c1, Curs<StudentMaster> c2) {
                return c1.getDenumire().compareTo(c2.getDenumire());
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder rezultat = new StringBuilder();
        for (Student stud : totiStudentii) {
            rezultat.append(stud.getNume());
            rezultat.append(" - ");
            rezultat.append(stud.getMedie());
            rezultat.append("\n");
        }
        return rezultat.toString();
    }

}
