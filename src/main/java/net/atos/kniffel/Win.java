package net.atos.kniffel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Win {
    private TreeMap<WinPatterns, Integer> resultListWithPoints = new TreeMap<>();

    public Win() {
        for (WinPatterns p : WinPatterns.values()) {  //Fills "resultListWithPoints" with all WinPatterns
            resultListWithPoints.put(p, null);
        }
    }

    public void resultSelection(ArrayList<Integer> dices) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean validUserInput = false;

        ArrayList<WinPatterns> availableResults = new ArrayList<>();
        for (Map.Entry<WinPatterns, Integer> entry : resultListWithPoints.entrySet()) {                                                                //output which results are still available
            if (entry.getValue() == null) availableResults.add(entry.getKey());
        }

        System.out.println("Wähle in welches Feld du deinen Wurf eintragen möchtest. Bei der Wahl eines nicht " +
                "zutreffenden Feldes, wird diese gestrichen.");
        System.out.println("Zur Auswahl stehen die folgenden Felder: " + availableResults);

        while (!validUserInput) {
            String selectedResultName = null;  //get UserInput
            try {
                selectedResultName = br.readLine();
            } catch (IOException e) {
                System.err.println("IO  Exception beim Einlesen: " + e);
            }

            for (WinPatterns pattern : WinPatterns.values()) {
                if (selectedResultName.equalsIgnoreCase(pattern.getDisplayName())) { //Check if userinput matches pattern name
                    validUserInput = true;
                    int points = pattern.getPoints(dices);
                    resultListWithPoints.put(pattern, points);
                    System.out.println("Es wurden " + points + " Punkte bei '" + selectedResultName + "' eingetragen.");
                }}
                if (!validUserInput) {
                    System.out.println("Dieses Ergebnisfeld ist nicht verfügbar. Wähle erneut.");
                }
                //TODO: Auswahl via Zahleingabe
                //TODO: Bei Ergebnissen mit fester Punktzahl muss geprüft werden ob die Würfel auf das Ergebnis zutreffen, sonst 0 Punkte
                //TODO: Ergebnissfelder sollen nicht mehrmals auswählbar sein
        }
    }

        public void calculateGameResult () {

            int allPoints = 0;
            int Einser = resultListWithPoints.get(WinPatterns.ONE_EYES);
            int Zweier = resultListWithPoints.get(WinPatterns.TWO_EYES);
            int Dreier = resultListWithPoints.get(WinPatterns.THIRD_EYES);
            int Vierer = resultListWithPoints.get(WinPatterns.FOUR_EYES);
            int Fuenfer = resultListWithPoints.get(WinPatterns.FIVE_EYES);
            int Sechser = resultListWithPoints.get(WinPatterns.SIX_EYES);
            int upperResultsPoints = Einser+Zweier+Dreier+Vierer+Fuenfer+Sechser; //Testing for Bonus at 63 Points

            if (upperResultsPoints >= 63) {
                System.out.println("Du hast in der ersten Hälfte der Ergebnissliste 63 Punkte erreicht. Dadurch erhältst du 35 Punkte extra.");
                allPoints = 35;
            }

            for (int i : resultListWithPoints.values()) {
                allPoints += i;
            }

            System.out.println("Das macht zusammengezählt " + allPoints + " Punkte.");
        }

    public void printResults() {
        for (Map.Entry<WinPatterns, Integer> result : resultListWithPoints.entrySet()) {
            System.out.println("* " + result.getKey().getDisplayName() + " - " + result.getValue() + " Punkte");
        }
    }
}

