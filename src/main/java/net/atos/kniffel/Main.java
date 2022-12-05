package net.atos.kniffel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    protected static ArrayList<Integer> currentRoll = new ArrayList<Integer>();
    protected static ArrayList<Integer> storedDice = new ArrayList<Integer>();
    static Random rand = new Random();


    private static List<Integer> chooseResult(String[] roll) {
        return null;
    }

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
                    choose = answer.split("\\D");

                    // regular for loop - Java Basic
                    // for (int count = 0; count < choose.length; count++) {
                    //     storedDice.add(Integer.valueOf(choose[count]));                                                    //Add kept rolls to storedDice
                    // }

                    // for each - Java 5
                    for (String s : choose) {
                        storedDice.add(Integer.valueOf(s));                                                                 //Add kept rolls to storedDice
                    }

                    // Streaming API - Java 8
                    // Arrays.stream(choose).forEach( s -> storedDice.add(Integer.valueOf(s)));


                    System.out.println("Das sind " + choose.length + " von " + roll + " Würfeln.");
                    System.out.println("\nDeine aktuellen Würfel zeigen: " + storedDice + "\n");

                } else if (storedDice.size() > 0) {                                                                        //zero rolls are kept
                    System.out.println("\nEs wurde keines der neuen Ergebnisse behalten." +
                            " Deine vorherigen Ergebnisse sind: " + storedDice + "\n");
                } else {
                    System.out.println("\nEs wurde keines der Ergebnisse behalten.\n");
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
