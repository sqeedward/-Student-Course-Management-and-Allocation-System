package org.example;

import java.io.*;
import java.util.ArrayList;


public class Engine {

    private String director;
    private Secretariat secretariat;
    private String fisier;



    /* Constructorul clasei Engine, este esential sa avem fisierele si directorul
     * pentru ca aici se fac toate operatiile cu fisiere.
     */
    public Engine(Secretariat secretariat, String director, String fisier) {
        this.secretariat = secretariat;
        this.director = director;
        this.fisier = fisier;
    }

    /* Creierul programului, aici comenzile devin actiuni in cod,
     * se folosesc in special metodele de la Secretariat.
     */
    public void prelucreazaInput(String comanda) {
        String[] comenzi = comanda.split(" - ");

        switch (comenzi[0]) {
            case "adauga_student":
                adaugaStudent(comenzi[2], comenzi[1]);
                break;
            case "citeste_mediile":
                citesteMedii();
                break;
            case "posteaza_mediile":
                posteazaMediile();
                break;
            case "contestatie":
                adaugContestatie(comenzi[1], comenzi[2]);
                break;
            case "adauga_curs":
                secretariat.adaugaCurs(comenzi[1], comenzi[2], comenzi[3]);
                break;
            case "adauga_preferinte":
                adaugPreferinte(comenzi);
                break;
            case "repartizeaza":
                secretariat.repartizeaza();
                break;
            case "posteaza_curs":
                PosteazaCurs(comenzi[1]);
                break;
            case "posteaza_student":
                posteazaStudent(comenzi[1]);
                break;
            default:
                System.out.println("Nu am inplementat: " + comenzi[0]);
        }

    }

    //Adaugam studentul in baza de date a secretariatului
    private void adaugaStudent(String nume, String tip) {
        Student student = new Student(nume);
        try {
            this.secretariat.adaugaStudent(student, tip);
        } catch (ExistaStudentul ex) {
            mesajFisier("Student duplicat: " + nume + "\n");
        }
    }

    //citim din fisier mediile studentiilor, pana dam de o eroare, astfel
    //stim sa ne oprim.
    private void citesteMedii() {
        String fisier = director + "note_";
        String fisierNota, line;
        int nrFisier = 1;
        while (true) {
            fisierNota = fisier + nrFisier + ".txt";
            try (BufferedReader br = new BufferedReader(new FileReader(fisierNota))) {
                while ((line = br.readLine()) != null) {
                    adaugaNote(line);
                }
            } catch (FileNotFoundException e) {
                break;
            } catch (IOException e) {
                break;
            }
            nrFisier += 1;
        }

    }

    //Adaugam notele studentilor in baza de date a secretariatului
    private void adaugaNote(String note) {
        String[] date = note.split(" - ");
        String nume = date[0];
        double medie = Double.parseDouble(date[1]);
        secretariat.adaugaNota(nume, medie);
    }

    //Afisam mediile studentiilor
    private void posteazaMediile() {
        secretariat.sortareStudenti();
        mesajFisier(secretariat.toString());
    }

    //Modificam mediile dupa contestatii
    private void adaugContestatie(String nume, String nouaMedie) {
        Student stud = secretariat.gastesteStudentul(nume);
        double medie = Double.parseDouble(nouaMedie);

        if (stud == null) {
            throw new RuntimeException("Nu exista studentul: " + nume);
        }
        stud.setMedie(medie);
    }

    //Adaugam preferintele studentilor, in ordinea dorintei lor
    private void adaugPreferinte(String[] argumente) {
        String nume = argumente[1];
        ArrayList<String> preferinte = new ArrayList<>();
        for (int i = 2; i < argumente.length; i++) {
            preferinte.add(argumente[i]);
        }
        secretariat.adaugaPreferinta(nume, preferinte);
    }

    //Afisam detalli despre curs, anume numele capacitatea si studentii
    public void PosteazaCurs(String numeCurs) {

        secretariat.sortareStudenti();
        Curs curs = secretariat.getCursLicenta(numeCurs);
        if (curs == null) {
            curs = secretariat.getCursMaster(numeCurs);

        }
        curs.sorteazaStudenti();
        mesajFisier(curs.toString());
    }

    //Afisam detalii despre student, anume numele, mediile si cursul
    // la care a fost repartizat
    private void posteazaStudent(String nume) {
        Student studentul = secretariat.gastesteStudentul(nume);
        mesajFisier(studentul.toString());
    }

    //Aici scriem in fisier, pasam un string si scriem in fisier
    //de fiecare data punem *** ca sa marcam o scriere
    private void mesajFisier(String mesaj) {
        String fisier = this.director + this.fisier + ".out";
        try (FileWriter fw = new FileWriter(fisier, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("***");
            out.print(mesaj);
        } catch (IOException e) {
            throw new RuntimeException("Ceva nu a mers la crearea fisierului");
        }
    }


}
