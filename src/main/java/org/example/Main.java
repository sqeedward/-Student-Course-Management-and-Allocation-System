package org.example;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String director = "./src/main/resources/"+args[0]+"/";
        String fisier = director + args[0]+".in";
        System.out.println(fisier);
        Secretariat secretariat = new Secretariat();
        Engine engine = new Engine(secretariat, director, args[0]);

        //aici citim din fisier fiecare linie de input
        try (BufferedReader br = new BufferedReader(new FileReader(fisier))) {
            String line;
            while ((line = br.readLine()) != null) {
                //toata magia se intampla in engine, acolo prelucram inputul
                engine.prelucreazaInput(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
