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


    //    BONUS_AT_63_POINTS,
    //meaning: 63 points were aquired solely by adding the results of ONE-SIX_EYES rolls (upper Part of Win-Sheet). Will add 35 Points to final result.

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
                int eyes = 1;
                while (eyes < 7) {
                    if (Collections.frequency(diceValues, eyes) >= 3) {
                        points = eyes * 3;
                        break;
                    } else {
                        eyes++;
                    }
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
                int eyes = 1;
                while (eyes < 7) {
                    if (Collections.frequency(diceValues, eyes) >= 4) {
                        points = eyes * 4;
                        break;
                    } else {
                        eyes++;
                    }
                }
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
            (diceValue) -> 50),
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
            (diceValue) -> 25),
    SMALL_STREET("kleine Straße",
            (diceValues) -> {
                return diceValues.contains(3) && diceValues.contains(4) && ((diceValues.contains(1) && diceValues.contains(2)) ||
                        (diceValues.contains(2) && diceValues.contains(5)) || (diceValues.contains(5) && diceValues.contains(6)));
            },
            (diceValue) -> 30),
    BIG_STREET("große Straße",
            (diceValues) -> {
                return diceValues.contains(2) && diceValues.contains(3) && diceValues.contains(4) && diceValues.contains(5) &&
                        (diceValues.contains(1) || diceValues.contains(6));
            },
            (diceValue) -> 40),
    //    KNIFFEL,
    CHANCE("Chance",
            (diceValues) -> {
                return true;
            },
            (diceValue) -> {
                int points = diceValue.stream().mapToInt(Integer::intValue).sum();
                return points;
            }),;


//    public final static WinPatterns[] WIN_PATTERNS = new WinPatterns[]{
//            ONE_EYES,
//            TWO_EYES,
//            THIRD_EYES,
//            FOUR_EYES,
//            FIVE_EYES,
//            SIX_EYES,
//            BONUS_AT_63_POINTS,
//            THREE_OF_A_KIND,
//            FOUR_OF_A_KIND,
//            FULL_HOUSE,
//            SMALL_STREET,
//            BIG_STREET,
//            KNIFFEL,
//            CHANCE,
//
//    };

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


    };





