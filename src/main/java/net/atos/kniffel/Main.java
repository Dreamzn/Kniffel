package net.atos.kniffel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int roll = 0;
        int result = 0;
        ArrayList<Integer> resultCollection = new ArrayList<Integer>();

        System.out.println("Wie viele Würfel möchtest du würfeln?");                                                    //dice count
        try {
            roll = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Ungültiges Zahlenformat!");
            return;
        }

        System.out.println("Wie viele Seiten haben deine Würfel?");                                                     //Dice type
        int dice = 0;

        try {
            dice = Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException nfe) {
            System.err.println("Ungültiges Zahlenformat!");
            return;
        }

        for (int count = 0; count < roll; count++) {                                                                    //rolling dice
            Random rand = new Random();
            result = rand.nextInt(dice) + 1;
            System.out.println("Dein " + (count + 1) + ". Ergebnis ist: " + result);
            resultCollection.add(result);                                                                               //ArrayList adds result after each execution
        }

        System.out.println("Wie viele der niedrigsten Ergebnisse möchtest du entfernen?");                              //Removing more than one result
        int deleteResults = 0;
        int smallest = resultCollection.get(0);

        try {
            deleteResults = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Ungültiges Zahlenformat!");
            return;
        }

        if (deleteResults < 1) {                                                                                         //0 & - numbers
            System.out.println("Wir haben keinen Würfel entfernt.");
        }

        for (int count = 0; count < deleteResults; count++) {
            smallest = Collections.min(resultCollection);
            int location = resultCollection.indexOf(smallest);
            resultCollection.remove(location);
        }

        System.out.println("Die übrigen Würfelwürfe sind die folgenden: " + resultCollection);

        int diceSum = 0;

        for (int number : resultCollection) {
            diceSum += number;
        }
        System.out.println("Diese Werte ergeben zusammen: " + diceSum);



        /*System.out.println("Möchtest du die niedrigsten Ergebnisse entfernen? Antworte mit 'Ja' oder 'Nein'.");
        boolean answer = false;
        int smallest = resultCollection.get(0);
        String decision;
        decision = br.readLine();

        switch (decision) {
            case "Ja":
                answer = true;
                break;
            case "Nein":
                answer = false;
                break;
            default:
                System.out.println("Bitte erneut eingeben.");
        }

        if (answer == true) {
            smallest = Collections.min(resultCollection);
            int location = resultCollection.indexOf(smallest);
            resultCollection.remove(location);
            System.out.println("Der niedrigste Wurf wurde entfernt: " + smallest);
            System.out.println("Die übrigen Würfelwürfe sind die folgenden: " + resultCollection);

            int diceSum = 0;

            for (int number : resultCollection) {
                diceSum += number;
            }
            System.out.println("Diese Werte ergeben zusammen: " + diceSum );

        } */

    }
}