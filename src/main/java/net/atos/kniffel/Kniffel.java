package net.atos.kniffel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Kniffel {

//    private HashMap<WinPatterns, Integer> resultListWithPoints = new HashMap<>();

    Win win = new Win();
    private boolean enableSuggestions = true;
    private static final int DICECOUNT = 5;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void run() throws IOException {
        Win win = new Win();

        //PRE-Game Userinit
        System.out.println("Spielunterstützung gewünscht? 'j/n'");
        String helpAnswer = br.readLine();
        if (!helpAnswer.equalsIgnoreCase("j")) {
            enableSuggestions = false;
        }

        for (int roundCount = 1; roundCount < 14; roundCount++) { //roundCounter until 13 rounds
            ArrayList<Integer> storedDices = new ArrayList<Integer>();

            for (int reroll = 1; reroll < 4; reroll++) { //inner reRollCounter until 3 Rounds
                System.out.println(reroll +"/3 Würfen der " + roundCount + ". Runde:");
                Rolls roll = new Rolls();
                roll.rollDices(DICECOUNT - storedDices.size());

                System.out.println("-----------------------------------------------------------------");

                if (reroll < 3) { //Usual roll, choosing Dice for 1.  & 2. Round of rolling
                    if (enableSuggestions) { //initialises arraylist only if suggestions are enabled
                        ArrayList<Integer> availableDices = new ArrayList<>();
                        availableDices.addAll(roll.getCurrentRoll());
                        availableDices.addAll(storedDices);
                        WinPatterns.printResultSuggestions(availableDices);
                    }
                    storedDices.addAll(roll.chooseDices());

                    if (storedDices.size() == 5) { //in Case user keeps 5 rolls first/second round
                        System.out.println("Du hast 5 Würfel behalten.");
                        break;
                    }
                } else {  //Case: 3. round, need to choose enough rolls
                    System.out.println("\nDies ist die letzte Würfelrunde. Alle Würfel werden übernommen.");
                    storedDices.addAll(roll.getCurrentRoll());
                    System.out.println("\nDeine endgültigen Würfel zeigen: " + storedDices + "\n");
                }

            }
            if (enableSuggestions) {
                WinPatterns.printResultSuggestions(storedDices);
            }
            win.resultSelection(storedDices); //select resultname to document points
        }
        win.printResults(); //print all results with documented points
        win.calculateGameResult(); //calculate overall game result
    }
}
