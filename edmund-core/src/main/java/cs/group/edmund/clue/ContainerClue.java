package cs.group.edmund.clue;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ContainerClue implements Clue {

    private final List<String> keyWords;

    public ContainerClue() {
        keyWords = asList("ABOUT", "ADMIT", "ADMITS", "ADMITTING", "AROUND", "BESIEGE", "BESIEGES", "BESIEGING", "BOX", "BOXES", "BOXING", "BRIDGE", "BRIDGES", "BRIDGING", "CAPTURE", "CAPTURED", "CAPTURES", "CAPTURING", "CATCH", "CATCHES", "CATCHING", "CIRCLE", "CIRCLES", "CIRCLING", "CLUTCH", "CLUTCHES", "CLUTCHING", "CONTAIN", "CONTAINING", "CONTAINS", "COVER", "COVERING", "COVERS", "EMBRACE", "EMBRACES", "EMBRACING", "ENCIRCLE", "ENCIRCLES", "ENCIRCLING", "ENFOLD", "ENFOLDING", "ENFOLDS", "ENVELOP", "ENVELOPING", "ENVELOPS", "EXTERNAL", "FLANK", "FLANKING", "FLANKS", "FRAME", "FRAMED", "FRAMING", "FRAMES", "GRASP", "GRASPING", "GRASPS", "HARBOUR", "HARBOURS", "HARBOURING", "HOLD", "HOLDING", "HOLDS", "HOUSE", "HOUSES", "HOUSING", "OUTSIDE", "RING", "RINGING", "RINGS", "ROUND", "SHELTER", "SHELTERING", "SHELTERS", "SURROUND", "SURROUNDING", "SURROUNDS", "SWALLOW", "SWALLOWING", "SWALLOWS", "TAKE IN", "TAKES IN", "TAKING IN", "WITHOUT", "WRAP", "WRAPPING", "WRAPS");
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
    public String solve(String phrase, int... answerLength) {

        // Get container clue keyword
        String key = null;
        for (String keyWord : keyWords) {
            if (phrase.toUpperCase().contains(keyWord)) {
                key = keyWord;
            }
        }

        // Get synonyms list for first and last words in phrase
        String firstWord = null;
        if (phrase.contains(" ")) {
            firstWord = phrase.substring(0, phrase.indexOf(" "));
        }
        String lastWord = phrase.substring(phrase.lastIndexOf(" ") + 1);

        ArrayList<String> firstWordSynonyms = getSynonyms(firstWord);
        ArrayList<String> lastWordSynonyms = getSynonyms(lastWord);

        // Get left and right half of phrase (removing the keyword)
        String leftHalf = phrase.toUpperCase().substring(0, phrase.indexOf(key));
        String rightHalf = phrase.toUpperCase().substring(phrase.lastIndexOf(key) + 1);

        // Get list of potential cryptic crossword solutions (assuming the first word is the answer, remove the first word)
        leftHalf = leftHalf.substring(leftHalf.indexOf(" "));
        ArrayList<String> firstList = returnContainedWords(leftHalf.substring(leftHalf.lastIndexOf(" ") + 1), rightHalf.substring(0, rightHalf.indexOf(" ")));

        // Get list of potential cryptic crossword solutions (assuming the last word is the answer, remove the last word)
        rightHalf = rightHalf.substring(rightHalf.lastIndexOf(" "));
        ArrayList<String> lastList = returnContainedWords(leftHalf.substring(leftHalf.lastIndexOf(" ") + 1), rightHalf.substring(0, rightHalf.indexOf(" ")));

        // Combine first and last lists, and compare against list of synonyms
        firstList.addAll(lastList);
        firstWordSynonyms.addAll(lastWordSynonyms);

        for (String word : firstList) {
            for (String synonym : firstWordSynonyms) {
                if (word == synonym) {
                    return synonym;
                }
            }
        }

        return null;
    }

    // Returns a list of synonyms based on the given word
    public ArrayList getSynonyms(String word) {
        return null;
    }

    // Return a list of all possible containment of words, and check if they are real words
    public ArrayList returnContainedWords(String wordA, String wordB) {

        // List of possible words
        ArrayList possibleWords = null;

        // Place wordA in every possible position in wordB
        for (int i = 0; i < wordB.length(); i++) {
            String possibleWord = wordB.substring(i, i) + wordA + wordB.substring(i + 1);

            if (isWord(possibleWord)) {
                possibleWords.add(possibleWord);
            }
        }

        // Place wordB in every possible position in wordA
        for (int i = 0; i < wordA.length(); i++) {
            String possibleWord = wordA.substring(i, i) + wordB + wordA.substring(i + 1);

            if (isWord(possibleWord)) {
                possibleWords.add(possibleWord);
            }
        }

        return possibleWords;
    }

    // Check if the given string is an actual word
    public boolean isWord(String word) {
        return true;
    }
}
