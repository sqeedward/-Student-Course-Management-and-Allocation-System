# tema-2-poo-2023
[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/yw5p-AaX)

## **NUME: BRUMA GEORGE-SEBASTIAN**
## **GRUPA: 322CB**
## **TITLU: TEMA2-POO**

## **Introducere**
Tema are ca scop implementarea unui sistem informatic pentru gestiunea si repartizarea
studentilor la materiile optionale din facultate. Astfel, sistemul va fi pus la dispozitie
secretariatului pentru gestionarea mediilor studentilor, preferintelor de repartizare si 
specificului cursurilor.

## **Implementare**
Tema este proiectata astfel incat, toate informatiile sa fie tinute in clasa secretariat,
aceasta clasa fiind baza de date a intregului sistem informatic, cu permisiuni intregi asupra
acestora. Pentru a face operatii se transmite prin args numele fisierului de input si se citesc
de acolo toate instructiunile cerute. Pentru prelucrarea inputului am creat o clasa numita
"ENGINE", care desparte inputul, il transforma si il executa. Tot in ENGINE se fac si scrierile
in fisier.
    Datele si prelucrarile datelor sunt tinute in clasa Secretariat. Aceasta contine un vector
 cu toti studentii din facultate, pentru a fi mai usor de identificat, trebuind sa parcurgem 
doar un vector. Un vector special pentru studentii de la master si cei care sunt la licenta. 
Vectorii apartin Collections din Java, fiind ArrayList-uri care detin clase copii ale unui 
student, anume studentLicenta si studentMaster. Aceste array-uri au fost create pentru a identifica
mai usor ce student apartine carui tip de invatamant. De asemenea, avem si cursuri pentru fiecare tip
de educatie, structurate intr-un ArrayList general pentru fiecare tip, folosind conceptul de "Generics",
astfel sa nu cream cate o clasa noua pentru fiecare tip de student, avand nevoie doar de una.
Totodata, avem si un vector de preferinte, care tine minte pentru fiecare student ce cursuri
ar dori in principal sa urmeze. Pentru acesti vectori, avem metode de prelucrare, care fac
cautari, specifica existenta elementelor, adauga elemente, le sorteaza. Pentru aceasta clasa
a fost creata si o exceptie speciala, anume "ExistaStudentul" care atentioneaza ca un student
cu acelasi nume a incercat sa fie inscris, aruncand o exceptie care este apoi prelucrata de engine.
    Clasa student reprezinta omul care participa la cursuri. Acesta are un nume unic, o medie anuala
cu care nu este initializat de la inceput si un curs la care este asignat, care apare abia dupa inrolare.
Acesta se extinde in 2 subtipuri, studentLicenta si studentMaster, clasele copil tinand minte tipul
de invatamant al studentului.
    Am ales colectiile de tipul ArrayList<>, deoarece sunt ceva mai familiare, avand
metode de prelucrare si identificare foarte bune, dimensiune dinamica si cam tot ce trebuia.
Puteam sa aleg si alte colectii, insa pentru simplitate, am decis sa nu fac codul mai greu de citit si 
totodata, sa nu reinventez roata.

## **BONUS:**

Bonusul se afla in folderul bonus.
Pentru repartizare am luat cursurile ramase in ordine alfabetica si asignarea unui student
la acestea se ia pe rand in functie de medie si nume.
    Ex: daca sunt cursurile: AA, POO si SO care inca au locuri si studentii: Alin: 6.30, Brumarul: 6.30,
Marian: 6.15, Zilean: 3.40, iar la AA mai avem: 1 loc, la POO: 1 loc si la SO: 2 locuri, vom avea:
AA(ALIN), POO(Brumarul), SO(Marian, Zilean).
