package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ContainerClue implements Clue {

    private final List<String> keyWords;
    private Thesaurus thesaurus;

    public ContainerClue() {
        keyWords = asList("CLOTHING", "ABOUT", "ADMIT", "ADMITS", "ADMITTING", "AROUND", "BESIEGE", "BESIEGES", "BESIEGING", "BOX", "BOXES", "BOXING", "BRIDGE", "BRIDGES", "BRIDGING", "CAPTURE", "CAPTURED", "CAPTURES", "CAPTURING", "CATCH", "CATCHES", "CATCHING", "CIRCLE", "CIRCLES", "CIRCLING", "CLUTCH", "CLUTCHES", "CLUTCHING", "CONTAIN", "CONTAINING", "CONTAINS", "COVER", "COVERING", "COVERS", "EMBRACE", "EMBRACES", "EMBRACING", "ENCIRCLE", "ENCIRCLES", "ENCIRCLING", "ENFOLD", "ENFOLDING", "ENFOLDS", "ENVELOP", "ENVELOPING", "ENVELOPS", "EXTERNAL", "FLANK", "FLANKING", "FLANKS", "FRAME", "FRAMED", "FRAMING", "FRAMES", "GRASP", "GRASPING", "GRASPS", "HARBOUR", "HARBOURS", "HARBOURING", "HOLD", "HOLDING", "HOLDS", "HOUSE", "HOUSES", "HOUSING", "OUTSIDE", "RING", "RINGING", "RINGS", "ROUND", "SHELTER", "SHELTERING", "SHELTERS", "SURROUND", "SURROUNDING", "SURROUNDS", "SWALLOW", "SWALLOWING", "SWALLOWS", "TAKE IN", "TAKES IN", "TAKING IN", "WITHOUT", "WRAP", "WRAPPING", "WRAPS");
        thesaurus = new Thesaurus();
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
        String key = getKeyword(phrase);

        // Get synonyms list for first and last words in phrase, set all to lowercase or future comparison
        ArrayList<String> firstLastList = getSideWords(phrase);
        ArrayList<String> firstWordSynonyms = (ArrayList<String>) thesaurus.getAllSynonyms(firstLastList.get(0));
        ArrayList<String> lastWordSynonyms = (ArrayList<String>) thesaurus.getAllSynonyms(firstLastList.get(1));
        firstWordSynonyms.addAll(lastWordSynonyms);

        for(int i=0; i < firstWordSynonyms.size(); i++) {
            firstWordSynonyms.set(i, firstWordSynonyms.get(i).toLowerCase());
        }

        // Get left and right half of phrase (removing the keyword)
        String leftHalf = splitPhrase(phrase, key).get(0);
        String rightHalf = splitPhrase(phrase, key).get(1);

        // Get list of potential cryptic crossword solutions (assuming the first word is the answer, remove the first word)
        ArrayList<String> solutions = getSolutions(phrase, leftHalf, rightHalf);

        // Compare against list of synonyms, and return the result (null if no match has been found)
        return compareLists(firstWordSynonyms, solutions);
    }

    // Compare the list of solutions to the list of synonyms. Return a match, else return null.
    public String compareLists(ArrayList<String> synonyms, ArrayList<String> solutions) {

        //
        for (String word : solutions) {
            for (String synonym : synonyms) {
                if (word.equals(synonym)) {
                    return synonym.toUpperCase();
                }
            }
        }
        return null;
    }

    // Return the keyword used in the phrase (uppercase)
    public String getKeyword(String phrase) {

        for (String keyWord : keyWords) {
            if (phrase.toUpperCase().contains(keyWord)) {
                return keyWord;
            }
        }
        return null;
    }

    // Return a list with the first and last words of the phrase
    public ArrayList<String> getSideWords(String phrase) {

        //
        ArrayList<String> words = new ArrayList<String>();

        if (phrase.contains(" ")) {
            words.add(phrase.substring(0, phrase.indexOf(" ")));
            words.add(phrase.substring(phrase.lastIndexOf(" ") + 1));
        }
        return words;
    }

    // Return a list containing the left and right half of the phrase, split on the keyword
    public ArrayList<String> splitPhrase(String phrase, String keyword) {

        //
        phrase = phrase.toUpperCase();
        ArrayList<String> list = new ArrayList<String>();
        list.add(phrase.substring(0, phrase.indexOf(keyword)).trim());
        list.add(phrase.substring(phrase.lastIndexOf(keyword) + keyword.length()).trim());
        return list;
    }

    // Return a list of potential solutions given the left and right half of the phrase
    public ArrayList<String> getSolutions(String phrase, String leftHalf, String rightHalf) {

        // List of solutions
        ArrayList<String> solutions = new ArrayList<String>();
        String leftWord, rightWord = null;

        // Get solutions
        ArrayList<String> l = getLeftRightWords(leftHalf, rightHalf);
        leftWord = l.get(0);
        rightWord = l.get(1);
        System.out.println(leftWord); //delete
        System.out.println(rightWord); //delete

        // Get lists of synonyms for left and right words
        ArrayList<String> leftSynonyms = null;
        ArrayList<String> rightSynonyms = null;
        if (leftWord != null) {
            leftSynonyms = (ArrayList<String>) thesaurus.getAllSynonyms(leftWord);
        }
        if (rightWord != null) {
            rightSynonyms = (ArrayList<String>) thesaurus.getAllSynonyms(rightWord);
        }
        leftSynonyms.add(leftWord);
        rightSynonyms.add(rightWord);

        return returnContainedWords(leftSynonyms, rightSynonyms);
    }

    // Return a list containing the words to the left and right of the keyword
    public ArrayList<String> getLeftRightWords(String leftHalf, String rightHalf) {

        //
        ArrayList<String> list = new ArrayList<String>();
        String leftWord, rightWord = null;

        leftWord = leftHalf.substring(leftHalf.lastIndexOf(" ")+1).trim();
        try {
            rightWord = rightHalf.substring(0, rightHalf.indexOf(" ")).trim();
        } catch (Exception e) {
            rightWord = rightHalf.substring(0).trim();
        }
        list.add(leftWord);
        list.add(rightWord);
        return list;

    }

    // Return a list of all possible containment of words
    public ArrayList<String> returnContainedWords(ArrayList<String> leftSynonyms, ArrayList<String> rightSynonyms) {

        // List of possible words
        ArrayList<String> possibleWords = new ArrayList<String>();

        //
        for (String leftWord : leftSynonyms) {
            for (String rightWord : rightSynonyms) {

                // Place leftWord and rightWord next to each other
                possibleWords.add((leftWord + rightWord).toLowerCase());
                possibleWords.add((rightWord + leftWord).toLowerCase());

                // Place leftWord in every possible position in rightWord
                for (int i = 1; i < rightWord.length(); i++) {
                    String possibleWord = rightWord.substring(0, i).trim() + leftWord.trim() + rightWord.substring(i);

                    if (isWord(possibleWord)) {
                        possibleWords.add(possibleWord.toLowerCase());
                    }
                }

                // Place rightWord in every possible position in leftWord
                for (int i = 1; i < leftWord.length(); i++) {
                    String possibleWord = leftWord.substring(0, i).trim() + rightWord.trim() + leftWord.substring(i);

                    if (isWord(possibleWord)) {
                        possibleWords.add(possibleWord.toLowerCase());
                    }
                }
            }
        }
        return possibleWords;
    }

    // Check if the given string is an actual word
    public boolean isWord(String word) {
        return true;
    }
}