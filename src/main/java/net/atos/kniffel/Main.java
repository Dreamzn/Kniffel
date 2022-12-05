package net.atos.kniffel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    private static ArrayList<Integer> currentRoll = new ArrayList<Integer>();
    private static ArrayList<Integer> storedDice = new ArrayList<Integer>();
    private static Random rand = new Random();

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @param rolledDiced number of dice thrown
     * @throws IOException in case the read operation from the command line fails
     */
    private static void chooseResult(int rolledDiced) throws IOException {
        ArrayList<Integer> validateDice = new ArrayList<Integer>();

        System.out.println("Welche Würfel möchtest du behalten? Trenne deine Eingabe durch Kommas.");                       //Input kept rolls
        String answer = br.readLine();

        if (!answer.equals("")) {
            String[] choose = answer.split("\\D");
            boolean validInput = false;
            boolean validSelection = false;

            String answerCheck = answer.replaceAll("\\W", "");                                              //Checks if Input is valid
            char[] checkInput = answerCheck.toCharArray();
            for (char c : checkInput) {
                if (Character.isDigit(c)) {
                    validInput = true;
                } else {
                    System.out.println("Das ist kein gültiges Zahlenformat.");
                    validInput = false;
                    break;
                }
            }

            for (String s : choose) {
                validateDice.add(Integer.valueOf(s));                                                                   //Adds chosen rolls to validation-list
            }

            if (currentRoll.containsAll(validateDice) && validInput) {                                                  //Checks if chosen rolls were actually rolled (anti-cheat)
                validSelection = true;
            } else if (!currentRoll.containsAll(validateDice) && validInput) {
                System.out.println("Dieses Würfelergebnis wurde nicht gewürfelt. Bitte wähle erneut.");
            }


            // TODO: validate selection
            // convert String array into list of integer values -> if none valid characters are detected validSelection has to be false
            // check if the selected integers are part of the currentRoll list -> set valid selection to true


            if (validSelection) {
                for (String s : choose) {
                    storedDice.add(Integer.valueOf(s));                                                                 //Add kept rolls to storedDice
                }
                currentRoll.clear();                                                                                    //Prevents cheating by limiting the selectable rolls to the current round of rolls
            } else {
                chooseResult(rolledDiced);
                return;
            }

            System.out.println("Das sind " + choose.length + " von " + rolledDiced + " Würfeln.");
            System.out.println("\nDeine aktuellen Würfel zeigen: " + storedDice + "\n");

        } else if (storedDice.size() > 0) {                                                                             //zero rolls are kept
            System.out.println("\nEs wurde keines der neuen Ergebnisse behalten." +
                    " Deine vorherigen Ergebnisse sind: " + storedDice + "\n");
        } else {
            System.out.println("\nEs wurde keines der Ergebnisse behalten.\n");
        }
    }

    public static void main(String[] args) throws IOException {

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
                chooseResult(roll);
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
