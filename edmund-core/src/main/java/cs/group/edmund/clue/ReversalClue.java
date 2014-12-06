package cs.group.edmund.clue;

import java.util.List;

import static java.util.Arrays.asList;

public class ReversalClue implements Clue {


    private final List<String> keyWords;
    private String answer = "Answer not found";

    public ReversalClue() {
        keyWords = asList("ABOUT", "AROUND", "ASCENDING", "BACK", "BACKED", "BACKING", "BACK-TO-FRONT", "BACKWARD", "BROUGHTABOUT", "BROUGHTUP", "CASTUP", "CLIMBING", "COMINGBACK", "COMINGUP", "COUNTER", "FLIPPED", "FLIPPING", "FROMTHEBOTTOM", "FROMTHEEAST", "FROMTHERIGHT", "FROMTHESOUTH", "GOINGBACK", "GOINGNORTH", "GOINGROUND", "GOINGUP", "GOINGWEST", "LIFTED", "LOOKINGBACK", "LOOKINGUP", "NORTHBOUND", "OVER", "OVERTURNED", "RAISED", "RAISING", "RETREAT", "RETREATING", "RETROGRADE", "RETROSPECTIVE", "REVERSED", "REVERSING", "REVOLUTIONARY", "RISING", "ROUND", "SENTBACK", "SENTUP", "SHOWNUP", "TAKENUP", "TURN", "TURNED", "TURNING", "TURNS", "UP", "UPENDED", "UPSET", "UPWARDLYMOBILE", "WESTBOUND", "WRITTENUP");
    }


    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        for (String keyWord : keyWords) {
            if (phrase.toUpperCase().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String solve(String phrase, String hint, int... answerLength) {
        String[] words = phrase.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[,?!]", "");
        }

        for (String word : words) {
            answer = (word.length() == answerLength[0]) ? new StringBuilder(word).reverse().toString() : answer;
        }

        return answer;
    }


}
