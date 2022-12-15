package net.atos.kniffel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Rolls {
    private Random rand = new Random();
    private ArrayList<Integer> currentRoll = new ArrayList<>();


    public void rollDices(int diceCount) {
        int result = 0;
        for (int count = 0; count < diceCount; count++) {                                                                    //rolling dice --> wird irgendwie bei 5 behalten trotzdem gemacht
            result = rand.nextInt(6) + 1;               //rolling 6sided dice
            System.out.println("Dein " + (count + 1) + ". Ergebnis ist: " + result);
            currentRoll.add(result);
        }
    }

    public ArrayList<Integer> chooseDices() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean validSelection = false;
        ArrayList<Integer> chosenDices = new ArrayList<>();

        validSelectionLoop:
        while (!validSelection) {
            chosenDices.clear();
            ArrayList<Integer> currentRollTemp = new ArrayList<>(currentRoll); // create deep copy

            System.out.println("\nWelche Würfel möchtest du behalten? Trenne deine Eingabe durch Kommas.");
            System.out.println("Verfügbar sind die folgenden: " + currentRoll);
            String answer = br.readLine(); //Input rolls to keep

            if (!answer.equals("")) { //if UserInput isn't empty
                String[] choose = answer.replaceAll(" ", "").split(",");

                for (String s : choose) {
                    try {
                        chosenDices.add(Integer.valueOf(s)); //Adds chosen rolls to validation-list
                    } catch (NumberFormatException nfe) {
                        System.out.println("Das ist kein gültiges Zahlenformat.");
                        continue validSelectionLoop;
                    }
                }

                //Checks if chosen rolls were actually rolled (anti-cheat)
                for (int chosenDice : chosenDices) {
                    int idx = currentRollTemp.indexOf(chosenDice);
                    if (idx != -1) {
                        currentRollTemp.remove(idx); //remove found rolls to prevent selection of the same number more often than it was rolled
                        validSelection = true;
                    } else {
                        System.out.println("Dieses Würfelergebnis wurde nicht gewürfelt. Bitte wähle erneut.");
                        continue validSelectionLoop;
                    }
                }

            } else {
                validSelection = true;
                System.out.println("\nEs wurde keines der Ergebnisse behalten.\n");
            }
        }
        return chosenDices;
    }

    public Collection<Integer> getCurrentRoll() {
        return currentRoll;
    }
}
