package net.atos.kniffel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    protected static ArrayList<Integer> currentRoll = new ArrayList<Integer>();
    protected static ArrayList<Integer> storedDice = new ArrayList<Integer>();
    static Random rand = new Random();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] choose = new String[0];
        int roll = 5;
        int result = 0;
        int dice = 6;


        for (int reroll = 0; reroll < 3; reroll++) {
            roll = roll - choose.length;
            for (int count = 0; count < roll; count++) {                                                                    //rolling dice
                result = rand.nextInt(dice) + 1;
                System.out.println("Dein " + (count + 1) + ". Ergebnis ist: " + result);
                currentRoll.add(result);                                                                                   //ArrayList adds result after each execution
            }

            if (reroll < 2) {
                System.out.println("Welche Würfel möchtest du behalten? Trenne deine Eingabe durch Kommas.");              //Input kept rolls
                String answer = br.readLine();
                if (!answer.equals("")) {
                    choose = answer.split(",");
                    System.out.println("Das sind " + choose.length + " von " + roll + " Würfeln.");

                    for (int count = 0; count < choose.length; count++) {
                        storedDice.add(Integer.valueOf(choose[count]));                                                    //Add kept rolls to storedDice
                    }
                    System.out.println("\nDeine aktuellen Würfel zeigen: " + storedDice + "\n");

                } else {                                                                                                   //zero rolls are kept
                    if (storedDice.size() > 0) {
                        System.out.println("\nEs wurde keines der neuen Ergebnisse behalten. Deine vorherigen Ergebnisse sind: " + storedDice + "\n");
                    } else {
                        System.out.println("\nEs wurde keines der Ergebnisse behalten.\n");
                    }
                }
            } else {                                                                                                       //Case: 3. round, need to choose enough rolls
                System.out.println("\nDies ist die letzte Würfelrunde. Um auf 5 Ergebnisse zu kommen, werden alle Würfel übernommen.");
                for (int getLast = 1; storedDice.size() < 5; getLast++) {
                    storedDice.add(currentRoll.get(currentRoll.size() - getLast));
                }
                System.out.println("\nDeine endgültigen Würfel zeigen: " + storedDice + "\n");

            }
        }
    }

}
