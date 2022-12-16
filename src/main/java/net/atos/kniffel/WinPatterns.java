package net.atos.kniffel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public enum WinPatterns {

    ONE_EYES("Einser",
            (diceValues) -> diceValues.contains(1),
            (diceValues) -> Collections.frequency(diceValues, 1)),
    TWO_EYES("Zweier",
            (diceValues) -> diceValues.contains(2),
            (diceValues) -> Collections.frequency(diceValues, 2) * 2),
    THIRD_EYES("Dreier",
            (diceValues) -> diceValues.contains(3),
            (diceValues) -> Collections.frequency(diceValues, 3) * 3),
    FOUR_EYES("Vierer",
            (diceValues) -> diceValues.contains(4),
            (diceValues) -> Collections.frequency(diceValues, 4) * 4),
    FIVE_EYES("Fünfer",
            (diceValues) -> diceValues.contains(5),
            (diceValues) -> Collections.frequency(diceValues, 5) * 5),
    SIX_EYES("Sechser",
            (diceValues) -> diceValues.contains(6),
            (diceValues) -> Collections.frequency(diceValues, 6) * 6),

    THREE_OF_A_KIND("Dreierpasch",
            (diceValues) -> {
                int countOne = Collections.frequency(diceValues, 1);
                int countTwo = Collections.frequency(diceValues, 2);
                int countThree = Collections.frequency(diceValues, 3);
                int countFour = Collections.frequency(diceValues, 4);
                int countFive = Collections.frequency(diceValues, 5);
                int countSix = Collections.frequency(diceValues, 6);

                return (countOne > 2 || countTwo > 2 || countThree > 2 || countFour > 2 || countFive > 2 || countSix > 2);
            },
            (diceValues) -> {
                int points = 0;
                int countOne = Collections.frequency(diceValues, 1);
                int countTwo = Collections.frequency(diceValues, 2);
                int countThree = Collections.frequency(diceValues, 3);
                int countFour = Collections.frequency(diceValues, 4);
                int countFive = Collections.frequency(diceValues, 5);
                int countSix = Collections.frequency(diceValues, 6);

                if (countOne > 2 || countTwo > 2 || countThree > 2 || countFour > 2 || countFive > 2 || countSix > 2) {
                    points = diceValues.stream().mapToInt(Integer::intValue).sum();
                }
                return points;
            }),
    FOUR_OF_A_KIND("Viererpasch",
            (diceValues) -> {
                int countOne = Collections.frequency(diceValues, 1);
                int countTwo = Collections.frequency(diceValues, 2);
                int countThree = Collections.frequency(diceValues, 3);
                int countFour = Collections.frequency(diceValues, 4);
                int countFive = Collections.frequency(diceValues, 5);
                int countSix = Collections.frequency(diceValues, 6);

                return (countOne > 3 || countTwo > 3 || countThree > 3 || countFour > 3 || countFive > 3 || countSix > 3);
            },
            (diceValues) -> {
                int points = 0;
                int countOne = Collections.frequency(diceValues, 1);
                int countTwo = Collections.frequency(diceValues, 2);
                int countThree = Collections.frequency(diceValues, 3);
                int countFour = Collections.frequency(diceValues, 4);
                int countFive = Collections.frequency(diceValues, 5);
                int countSix = Collections.frequency(diceValues, 6);

                if (countOne > 3 || countTwo > 3 || countThree > 3 || countFour > 3 || countFive > 3 || countSix > 3) {
                points = diceValues.stream().mapToInt(Integer::intValue).sum();}
                return points;
            }),
    KNIFFEL("Kniffel",
            (diceValues) -> {
                int countOne = Collections.frequency(diceValues, 1);
                int countTwo = Collections.frequency(diceValues, 2);
                int countThree = Collections.frequency(diceValues, 3);
                int countFour = Collections.frequency(diceValues, 4);
                int countFive = Collections.frequency(diceValues, 5);
                int countSix = Collections.frequency(diceValues, 6);
                return (countOne > 4 || countTwo > 4 || countThree > 4 || countFour > 4 || countFive > 4 || countSix > 4);
            },
            (diceValues) -> {
                int points = 0;
                int countOne = Collections.frequency(diceValues, 1);
                int countTwo = Collections.frequency(diceValues, 2);
                int countThree = Collections.frequency(diceValues, 3);
                int countFour = Collections.frequency(diceValues, 4);
                int countFive = Collections.frequency(diceValues, 5);
                int countSix = Collections.frequency(diceValues, 6);
                if (countOne > 4 || countTwo > 4 || countThree > 4 || countFour > 4 || countFive > 4 || countSix > 4) {
                    points = 50;
                }
                return points;
            }
    ),
    FULL_HOUSE("Full-House",
            (diceValues) -> {
                int countOne = Collections.frequency(diceValues, 1);
                int countTwo = Collections.frequency(diceValues, 2);
                int countThree = Collections.frequency(diceValues, 3);
                int countFour = Collections.frequency(diceValues, 4);
                int countFive = Collections.frequency(diceValues, 5);
                int countSix = Collections.frequency(diceValues, 6);
                return ((countOne == 3 || countTwo == 3 || countThree == 3 || countFour == 3 || countFive == 3 || countSix == 3) &&
                        (countOne == 2 || countTwo == 2 || countThree == 2 || countFour == 2 || countFive == 2 || countSix == 2));
            },
            (diceValues) -> {
                int countOne = Collections.frequency(diceValues, 1);
                int countTwo = Collections.frequency(diceValues, 2);
                int countThree = Collections.frequency(diceValues, 3);
                int countFour = Collections.frequency(diceValues, 4);
                int countFive = Collections.frequency(diceValues, 5);
                int countSix = Collections.frequency(diceValues, 6);
                int points = 0;
                if ((countOne == 3 || countTwo == 3 || countThree == 3 || countFour == 3 || countFive == 3 || countSix == 3) &&
                        (countOne == 2 || countTwo == 2 || countThree == 2 || countFour == 2 || countFive == 2 || countSix == 2)) {
                    points = 25;
                }
                return points;
            }),
    SMALL_STREET("Kleine Straße",
            (diceValues) -> {
                return diceValues.contains(3) && diceValues.contains(4) && ((diceValues.contains(1) && diceValues.contains(2)) ||
                        (diceValues.contains(2) && diceValues.contains(5)) || (diceValues.contains(5) && diceValues.contains(6)));
            },
            (diceValues) -> {
                int points = 0;
                if (diceValues.contains(3) && diceValues.contains(4) && ((diceValues.contains(1) && diceValues.contains(2)) ||
                        (diceValues.contains(2) && diceValues.contains(5)) || (diceValues.contains(5) && diceValues.contains(6)))) {
                    points = 30;
                }
                return points;
            }),
    BIG_STREET("Große Straße",
            (diceValues) -> {
                return diceValues.contains(2) && diceValues.contains(3) && diceValues.contains(4) && diceValues.contains(5) &&
                        (diceValues.contains(1) || diceValues.contains(6));
            },
            (diceValues) -> {
                int points = 0;
                if (diceValues.contains(2) && diceValues.contains(3) && diceValues.contains(4) && diceValues.contains(5) &&
                        (diceValues.contains(1) || diceValues.contains(6))) {
                    points = 40;
                }
                return points;
            }),
    CHANCE("Chance",
            (diceValues) -> {
                return true;
            },
            (diceValues) -> {
                int points = diceValues.stream().mapToInt(Integer::intValue).sum();
                return points;
            }),
    ;


    private Function<ArrayList<Integer>, Boolean> matcherFunction;
    private Function<ArrayList<Integer>, Integer> pointFunction;
    private String displayName;

    WinPatterns(String displayName, Function<ArrayList<Integer>, Boolean> matcherFunction, Function<ArrayList<Integer>, Integer> pointFunction) {
        this.displayName = displayName;
        this.matcherFunction = matcherFunction;
        this.pointFunction = pointFunction;
    }

    public boolean matches(ArrayList<Integer> diceValues) {
        boolean matcherResult = matcherFunction.apply(diceValues);
        return matcherResult;
    }

    public Integer getPoints(ArrayList<Integer> diceValues) {
        Integer pointResult = pointFunction.apply(diceValues);
        return pointResult;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static void printResultSuggestions(ArrayList<Integer> availableDices) {
        for (WinPatterns pattern : WinPatterns.values()) {
            if (pattern.matches(availableDices)) {
                System.out.println("Deine gesamten Ergebnisse treffen auf '" + pattern.getDisplayName() + "' zu. (" + pattern.getPoints(availableDices) + " Punkte)");
            }
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}





