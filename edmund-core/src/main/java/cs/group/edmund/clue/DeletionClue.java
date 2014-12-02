package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public class DeletionClue implements Clue {

    private final List<String> keyWordsHead, keyWordsTail, keyWordsBoth, keyWordsMiddle, keyWords;
    private Thesaurus thesaurus;

    public DeletionClue() {
        keyWordsHead = asList("AFTER COMMENCEMENT", "BEGINNING TO GO", "BEHEADED", "BEHEADING", "DECAPITATED", "FIRST OFF", "HEADLESS", "HEAD OFF", "INITIALLY LACKING", "LEADERLESS", "LOSING OPENER", "MISSING THE FIRST", "NEEDING NO INTRODUCTION", "NOT BEGINNING", "NOT COMMENCING", "NOT STARTING", "START OFF", "START TO GO", "SCRATCH THE HEAD", "STRIKE THE HEAD", "UNINITIATED", "UNSTARTED", "WITHOUT ORIGIN");
        keyWordsTail = asList("ABRIDGED", "ALMOST", "BACK OFF", "CLIPPED", "CURTAILED", "CUT SHORT", "DETAILED", "EARLY CLOSING", "ENDLESS", "FALLING SHORT", "FINISH OFF", "FOR THE MOST PART", "INCOMPLETE", "INTERMINABLE", "LACKING FINISH", "MISSING THE LAST", "MOST", "MOSTLY", "NEARLY", "NOT COMPLETELY", "NOT FULLY", "NOT QUITE", "SHORT", "SHORTENING", "TAILLESS", "UNENDING", "UNFINISHED", "WITHOUT END");
        keyWordsBoth = asList("EDGES AWAY", "LACKING WINGS", "LIMITLESS", "LOSING MARGINS", "SHELLED", "SIDES SPLITTING", "TRIMMED", "UNLIMITED", "WINGLESS", "WITHOUT LIMITS");
        keyWordsMiddle = asList("CORED", "DISHEARTENED", "EMPTIED", "EMPTY", "EVACUATED", "FILLETED", "GUTTED", "HEARTLESS", "HOLLOW", "LOSING HEART", "UNCENTERED");
        keyWords = asList("AFTER COMMENCEMENT", "BEGINNING TO GO", "BEHEADED", "BEHEADING", "DECAPITATED", "FIRST OFF", "HEADLESS", "HEAD OFF", "INITIALLY LACKING", "LEADERLESS", "LOSING OPENER", "MISSING THE FIRST", "NEEDING NO INTRODUCTION", "NOT BEGINNING", "NOT COMMENCING", "NOT STARTING", "START OFF", "START TO GO", "SCRATCH THE HEAD", "STRIKE THE HEAD", "UNINITIATED", "UNSTARTED", "WITHOUT ORIGIN", "ABRIDGED", "ALMOST", "BACK OFF", "CLIPPED", "CURTAILED", "CUT SHORT", "DETAILED", "EARLY CLOSING", "ENDLESS", "FALLING SHORT", "FINISH OFF", "FOR THE MOST PART", "INCOMPLETE", "INTERMINABLE", "LACKING FINISH", "MISSING THE LAST", "MOST", "MOSTLY", "NEARLY", "NOT COMPLETELY", "NOT FULLY", "NOT QUITE", "SHORT", "SHORTENING", "TAILLESS", "UNENDING", "UNFINISHED", "WITHOUT END", "EDGES AWAY", "LACKING WINGS", "LIMITLESS", "LOSING MARGINS", "SHELLED", "SIDES SPLITTING", "TRIMMED", "UNLIMITED", "WINGLESS", "WITHOUT LIMITS", "CORED", "DISHEARTENED", "EMPTIED", "EMPTY", "EVACUATED", "FILLETED", "GUTTED", "HEARTLESS", "HOLLOW", "LOSING HEART", "UNCENTERED");
        thesaurus = new Thesaurus();
    }

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase)
    {
        for (String keyWord : keyWords) {
            if (phrase.contains(keyWord.toLowerCase()))
                return true;
        }
        return false;
    }

    @Override
    public String solve(String phrase, String hint, int... answerLength) {

        // Check if any known keyword is in phrase
        phrase = phrase.toLowerCase();
        String key = getKeyword(phrase);

        if(isRelevant(phrase)) {
            // Deduce what type of deletion clue it is

        }
        return null;
    }

    // Return the keyword used in the phrase, else return null.
    public String getKeyword(String phrase)
    {
        for (String keyWord : keyWords) {
            if (phrase.contains(keyWord.toLowerCase()))
                return keyWord.toLowerCase();
        }
        return null;
    }
}