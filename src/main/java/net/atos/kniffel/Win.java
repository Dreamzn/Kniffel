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

    //    public void resultSelection(ArrayList<Integer> dices) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        ArrayList<String> availableResults = new ArrayList<>();
//        boolean validUserInput = false;
//        boolean duplicateResultSelection = true;
//
//        for (Map.Entry<WinPatterns, Integer> entry : resultListWithPoints.entrySet()) {                                                                //output which results are still available
//            if (entry.getValue() == null) availableResults.add(String.valueOf(entry.getKey()));
//        }
//
//        System.out.println("Wähle in welches Feld du deinen Wurf eintragen möchtest. Bei der Wahl eines nicht " +
//                "zutreffenden Feldes, wird diese gestrichen.");
//        for (String result : availableResults) {
//            System.out.println("Die " + (availableResults.indexOf(result) + 1) + ". Möglichkeit ist: " + result);
//        }
//
//        while (duplicateResultSelection && !validUserInput) {
//            String selectedResultName = null;  //get UserInput
//            try {
//                selectedResultName = br.readLine();
//            } catch (IOException e) {
//                System.err.println("IO  Exception beim Einlesen: " + e);
//            }
//
//            //translate Number into Resultname if userinput is number
//            if (selectedResultName.matches("[0-9]+")) {
//                int numberUserInput = (Integer.parseInt(selectedResultName) - 1);
//                String numberUserInputAsName = availableResults.get(numberUserInput);
//                selectedResultName = numberUserInputAsName;
//            }
//
//            //Checks if Resultname was already chosen in a previous round
//            for (String eachAvailableResult : availableResults) {
//                if (selectedResultName.equalsIgnoreCase(eachAvailableResult)) {
//                    duplicateResultSelection = false;
//                }
//            }
//
//            //Check if userinput matches pattern name
//            for (WinPatterns pattern : WinPatterns.values()) {
//                if (selectedResultName.equalsIgnoreCase(pattern.getDisplayName()) && !duplicateResultSelection) {
//                    validUserInput = true;
//                    int points = pattern.getPoints(dices);
//                    resultListWithPoints.put(pattern, points);
//                    System.out.println("Es wurden " + points + " Punkte bei '" + selectedResultName + "' eingetragen.");
//                }
//            }
//            if (!validUserInput || duplicateResultSelection) {
//                System.out.println("Dieses Ergebnisfeld ist nicht verfügbar. Wähle erneut.");
//            }
//        }
//    }
    public void resultSelection(ArrayList<Integer> dices) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> availableResults = new ArrayList<>();
        boolean validUserInput = false;
        boolean duplicateResultSelection = true;


        System.out.println("Wähle in welches Feld du deinen Wurf eintragen möchtest:");
        printResultList();

        while (duplicateResultSelection && !validUserInput) {
            String selectedResultName = null;  //get UserInput
            try {
                selectedResultName = br.readLine();
            } catch (IOException e) {
                System.err.println("IO  Exception beim Einlesen: " + e);
            }

            //translate number into Resultname if userinput is number
            if (selectedResultName.matches("[0-9]+")) {
                ArrayList<String> numberedResultNames = new ArrayList<>();

                for (WinPatterns pattern : resultListWithPoints.keySet()) {
                    numberedResultNames.add(String.valueOf(pattern));
                }
                selectedResultName = numberedResultNames.get((Integer.parseInt(selectedResultName) - 1));
            }

            //Checks if Resultname was already chosen in a previous round
            for (Map.Entry<WinPatterns, Integer> entry : resultListWithPoints.entrySet()) {
                if (entry.getValue() == null) availableResults.add(String.valueOf(entry.getKey()));
            }
            for (String eachAvailableResult : availableResults) {
                if (selectedResultName.equalsIgnoreCase(eachAvailableResult)) {
                    duplicateResultSelection = false;
                }
            }

            //Check if userinput matches pattern name
            for (WinPatterns pattern : WinPatterns.values()) {
                if (selectedResultName.equalsIgnoreCase(pattern.getDisplayName()) && !duplicateResultSelection) {
                    validUserInput = true;
                    int points = pattern.getPoints(dices);
                    resultListWithPoints.put(pattern, points);
                    System.out.println("Es wurden " + points + " Punkte bei '" + selectedResultName + "' eingetragen.");
                }
            }
            if (!validUserInput || duplicateResultSelection) {
                System.out.println("Dieses Ergebnisfeld ist nicht verfügbar. Wähle erneut.");
            }
        }
    }

    private void printResultList() {
        System.out.println(" ________________________________________________\n" +
                "|                   | Rechnung      | Punkte\t |\n" +
                "|------------------ | ------------- | ---------- |\n" +
                "| 1. Einser         | Alle 1en      | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.ONE_EYES)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 2. Zweier         | Alle 2en      | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.TWO_EYES)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 3. Dreier         | Alle 3en      | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.THIRD_EYES)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 4. Vierer         | Alle 4en      | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.FOUR_EYES)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 5. Fünfer         | Alle 5en      | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.FIVE_EYES)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 6. Sechser        | Alle 6en      | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.SIX_EYES)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "|                   |               |            |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 7. Dreierpasch    | Alle Augen    | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.THREE_OF_A_KIND)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 8. Viererpasch    | Alle Augen    | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.FOUR_OF_A_KIND)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 9. Full-House     | 25 Punkte     | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.FULL_HOUSE)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 10. Kleine Straße | 30 Punkte     | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.SMALL_STREET)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 11. Große Straße  | 40 Punkte     | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.BIG_STREET)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 12. Kniffel       | 50 Punkte     | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.KNIFFEL)) + "\t\t |\n" +
                "| ----------------- | ------------- | ---------- |\n" +
                "| 13. Chance        | Alle Augen    | \t" + checkIfNull(resultListWithPoints.get(WinPatterns.CHANCE)) + "\t\t |\n" +
                "|___________________|_______________|____________|\n");
    }

    private String checkIfNull(Integer intToCheck) {
        return intToCheck == null ? " " : String.valueOf(intToCheck);
    }

    public void calculateGameResult() {
        printResultList();
        int allPoints = 0;
        int upperResultsPoints = resultListWithPoints.get(WinPatterns.ONE_EYES) + resultListWithPoints.get(WinPatterns.TWO_EYES) +
                resultListWithPoints.get(WinPatterns.THIRD_EYES) + resultListWithPoints.get(WinPatterns.FOUR_EYES) +
                resultListWithPoints.get(WinPatterns.FIVE_EYES) + resultListWithPoints.get(WinPatterns.SIX_EYES);

        if (upperResultsPoints >= 63) {
            System.out.println("Du hast in der oberen Hälfte der Ergebnissliste 63 Punkte erreicht. Dadurch erhältst du 35 Punkte extra.");
            allPoints = 35;
        }

        for (int i : resultListWithPoints.values()) {
            allPoints += i;
        }

        System.out.println("Das macht zusammengezählt " + allPoints + " Punkte.");
    }

    public void printResultSuggestions(ArrayList<Integer> availableDices) {
        for (WinPatterns pattern : WinPatterns.values()) {
            if (pattern.matches(availableDices) && resultListWithPoints.get(pattern) == null) {
                String name = String.format("%-13s",pattern.getDisplayName());
                String points = String.format("%-2d",pattern.getPoints(availableDices));
                System.out.println("Deine gesamten Ergebnisse treffen auf '" + name + "' zu. (" + points + " Punkte)");
            }
        }
    }

}

