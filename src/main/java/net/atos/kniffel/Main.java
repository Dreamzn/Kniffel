package net.atos.kniffel;

import javax.xml.transform.Result;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static ArrayList<Integer> currentRoll = new ArrayList<Integer>();
    private static ArrayList<Integer> storedDices = new ArrayList<Integer>();
    private static Random rand = new Random();
    private static final int DICECOUNT = 5;
    private static boolean helpAnswerOn = true;

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static void resultSelection() throws IOException {
        System.out.println("Wähle in welches Feld du deinen Wurf eintragen möchtest. Bei der Wahl eines nicht " +
                "zutreffenden Feldes, wird diese gestrichen.");

        HashMap<String, Integer> results = new HashMap<String, Integer>();
        ArrayList<String> availableResults = new ArrayList<>();
        int points = 1;
        results.put("oneEyes", 0);
        results.put("twoEyes", 0);
        results.put("threeEyes", 0);
        results.put("fourEyes", 0);
        results.put("fiveEyes", 0);
        results.put("sixEyes", 0);
        results.put("threeOfAKind", 0);
        results.put("fourOfAKind", 0);
        results.put("Kniffel", 0);
        results.put("fullHouse", 0);
        results.put("smallStreet", 0);
        results.put("bigStreet", 0);
        results.put("Chance", 0);

        for (String i : results.keySet()) {
            if (results.get(i) < 1) availableResults.add(i);
        }
        System.out.println("Zur Auswahl stehen die folgenden Felder: " + availableResults);

        String selectedResultName = br.readLine();
        for (String i : results.keySet()) {
        try {
                if (selectedResultName.equals(i)) {
                    results.replace(i, points);
                }
        } catch (NumberFormatException nfe) {
            System.out.println("Das ist kein gültiges Ergebnisfeld.");
            resultSelection();
        }}
        System.out.println("Es wurden deine Punkte bei '" + selectedResultName + "' eingetragen.");
        storedDices.clear();
        currentRoll.clear();
    }

    private static void resultSuggestion() {

        ArrayList<Integer> availableDices = new ArrayList<>();
        availableDices.addAll(currentRoll);
        availableDices.addAll(storedDices);
        for (WinPatterns pattern : WinPatterns.values()) {

            if (pattern.matches(availableDices)) {
                System.out.println("Deine gesamten Ergebnisse treffen auf '" + pattern.getDisplayName() + "' zu. (" + pattern.getPoints(availableDices) + " Punkte)");
            }
        }
    }

    /**
     * @param rolledDiced number of dice thrown
     * @throws IOException in case the read operation from the command line fails
     */

    private static void chooseResult(int rolledDiced) throws IOException {
        ArrayList<Integer> chosenDices = new ArrayList<>();
        ArrayList<Integer> currentRollTemp = new ArrayList<>(currentRoll); // create deep copy

        System.out.println("\nWelche Würfel möchtest du behalten? Trenne deine Eingabe durch Kommas.");                      //Input kept rolls
        System.out.println("Verfügbar sind die folgenden: " + currentRoll);
        String answer = br.readLine();

        if (!answer.equals("")) {
            String[] choose = answer.replaceAll(" ", "").split(",");
            boolean validSelection = false;

            for (String s : choose) {
                try {
                    chosenDices.add(Integer.valueOf(s));                                                                   //Adds chosen rolls to validation-list
                } catch (NumberFormatException nfe) {
                    System.out.println("Das ist kein gültiges Zahlenformat.");
                    chooseResult(rolledDiced);
                    return;
                }
            }

            //Checks if chosen rolls were actually rolled (anti-cheat)

            for (int chosenDice : chosenDices) {
                int idx = currentRollTemp.indexOf(chosenDice);
                if (idx != -1) {
                    currentRollTemp.remove(idx);
                    validSelection = true;
                } else {
                    System.out.println("Dieses Würfelergebnis wurde nicht gewürfelt. Bitte wähle erneut.");
                    validSelection = false;
                    break;
                }
            }

            if (validSelection) {
                storedDices.addAll(chosenDices);                                                                 //Add kept rolls to storedDice
                currentRoll.clear();                                                                                    //Prevents cheating by limiting the selectable rolls to the current round of rolls
            } else {
                chooseResult(rolledDiced);
                return;
            }

            System.out.println("Das sind " + storedDices.size() + " von " + rolledDiced + " Würfeln.");
            System.out.println("\nDeine aktuellen Würfel zeigen: " + storedDices + "\n");

        } else if (storedDices.size() > 0) {                                                                             //zero rolls are kept
            System.out.println("\nEs wurde keines der neuen Ergebnisse behalten." +
                    " Deine vorherigen Ergebnisse sind: " + storedDices + "\n");
        } else {
            System.out.println("\nEs wurde keines der Ergebnisse behalten.\n");
            currentRoll.clear();
        }
    }
<<<<<<< HEAD

    public static void main(String[] args) throws IOException {

        System.out.println("Wenn du mit Spielunterstützung spielen möchtest reagiere mit 'j/N'.");
        String helpAnswer = br.readLine();
        if (!helpAnswer.equalsIgnoreCase("j")) {
            helpAnswerOn = false;
        }

        for (int roundCount = 0; roundCount < 13; roundCount++) {

            int result = 0;

            for (int reroll = 0; reroll < 3; reroll++) {

                int roll = DICECOUNT - storedDices.size();

                for (int count = 0; count < roll; count++) {                                                                    //rolling dice
                    result = rand.nextInt(6) + 1;               //rolling 6sided dice
                    System.out.println("Dein " + (count + 1) + ". Ergebnis ist: " + result);
                    currentRoll.add(result);
                }

                System.out.println("-----------------------------------------------------------------");

                if (reroll < 2) {
                    if (helpAnswerOn) {
                        resultSuggestion();
                    }

                    chooseResult(roll);

                    if (storedDices.size() == 5) { //in Case user keeps 5 rolls first/second round
                        System.out.println("Du hast 5 Würfel behalten. Hiermit ist diese Runde beendet.");
                        reroll = 3;
                    }
                } else {                                                                                                       //Case: 3. round, need to choose enough rolls
                    System.out.println("\nDies ist die letzte Würfelrunde. Um auf 5 Ergebnisse zu kommen, werden alle Würfel übernommen.");
                    for (int getLast = 1; storedDices.size() < DICECOUNT; getLast++) {
                        storedDices.add(currentRoll.get(currentRoll.size() - getLast));
                    }
                    System.out.println("\nDeine endgültigen Würfel zeigen: " + storedDices + "\n");
                    if (helpAnswerOn) {
                        resultSuggestion();
                    }
                    resultSelection();
                }
            }
        }
    }
=======
>>>>>>> c2bdcca (Multiserver)
}
