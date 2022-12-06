package net.atos.kniffel;

import java.util.ArrayList;
import java.util.function.Function;

public enum WinPatterns {

    ONE_EYES( (diceValues) -> {
        return diceValues.contains(1);
    }),
    TWO_EYES( (diceValues) -> {
        return diceValues.contains(2);
    }),
    THIRD_EYES( (diceValues) -> {
        return diceValues.contains(3);
    }),
    FOUR_EYES( (diceValues) -> {
        return diceValues.contains(4);
    }),
    FIVE_EYES( (diceValues) -> {
        return diceValues.contains(5);
    }),
    SIX_EYES( (diceValues) -> {
        return diceValues.contains(6);
    }),
//    BONUS_AT_63_POINTS,
//    THREE_OF_A_KIND,
//    FOUR_OF_A_KIND,
//    FULL_HOUSE,
//    SMALL_STREET,
//    BIG_STREET,
//    KNIFFEL,
    CHANCE( (diceValues) -> {
        return true;
    });


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
    WinPatterns(Function<ArrayList<Integer>, Boolean> matcherFunction) {
        this.matcherFunction = matcherFunction;
    }

    boolean matches(ArrayList<Integer> diceValues) {
        boolean matcherResult = matcherFunction.apply(diceValues);
        return matcherResult;
    }

};





