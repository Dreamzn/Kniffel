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
        ArrayList<String> availableResults = new ArrayList<>();
        boolean validUserInput = false;
        boolean duplicateResultSelection = true;

        for (Map.Entry<WinPatterns, Integer> entry : resultListWithPoints.entrySet()) {                                                                //output which results are still available
            if (entry.getValue() == null) availableResults.add(String.valueOf(entry.getKey()));
        }

        System.out.println("Wähle in welches Feld du deinen Wurf eintragen möchtest. Bei der Wahl eines nicht " +
                "zutreffenden Feldes, wird diese gestrichen.");
        for (String result : availableResults) {
            System.out.println("Die " + (availableResults.indexOf(result) + 1) + ". Möglichkeit ist: " + result);
        }

        while (duplicateResultSelection && !validUserInput) {
            String selectedResultName = null;  //get UserInput
            try {
                selectedResultName = br.readLine();
            } catch (IOException e) {
                System.err.println("IO  Exception beim Einlesen: " + e);
            }

            //translate Number into Resultname if userinput is number
            if (selectedResultName.matches("[0-9]+")) {
                int numberUserInput = (Integer.parseInt(selectedResultName) - 1);
                String numberUserInputAsName = availableResults.get(numberUserInput);
                selectedResultName = numberUserInputAsName;
            }

            //Checks if Resultname was already chosen in a previous round
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

        //TODO: (Graphische Anzeige des Ergebnisblocks)
    }

//    public void printResultList(){
//        System.out.println(" _______________________________________________\n" +
//                "|                |               |              |\n" +
//                "|                | Rechnung      | Punkte       |\n" +
//                "|--------------- | ------------- | -------------|\n" +
//                "| Einser         | Alle 1en      |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Zweier         | Alle 2en      |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Dreier         | Alle 3en      |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Vierer         | Alle 4en      |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Fünfer         | Alle 5en      |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Sechser        | Alle 6en      |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "|                |               |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Dreierpasch    | Alle Augen    |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Viererpasch    | Alle Augen    |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Full-House     | 25 Punkte     |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Kleine Straße  | 30 Punkte     |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Große Straße   | 40 Punkte     |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Kniffel        | 50 Punkte     |              |\n" +
//                "| -------------- | ------------- | -------------|\n" +
//                "| Chance         | Alle Augen    |              |\n" +
//                "|________________|_______________|______________|");
//    }

    public void calculateGameResult() {

        int allPoints = 0;
        int upperResultsPoints = resultListWithPoints.get(WinPatterns.ONE_EYES) + resultListWithPoints.get(WinPatterns.TWO_EYES) +
                resultListWithPoints.get(WinPatterns.THIRD_EYES) + resultListWithPoints.get(WinPatterns.FOUR_EYES) +
                resultListWithPoints.get(WinPatterns.FIVE_EYES) + resultListWithPoints.get(WinPatterns.SIX_EYES);

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

