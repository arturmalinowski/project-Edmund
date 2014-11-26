package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public class ContainerClue implements Clue {

    private final List<String> keyWords;
    private Thesaurus thesaurus;
    private boolean verbose;

    public ContainerClue() {
        keyWords = asList("PUT IN", "CLOTHING", "ABOUT", "ADMIT", "ADMITS", "ADMITTING", "AROUND", "BESIEGE", "BESIEGES", "BESIEGING", "BOX", "BOXES", "BOXING", "BRIDGE", "BRIDGES", "BRIDGING", "CAPTURE", "CAPTURED", "CAPTURES", "CAPTURING", "CATCH", "CATCHES", "CATCHING", "CIRCLE", "CIRCLES", "CIRCLING", "CLUTCH", "CLUTCHES", "CLUTCHING", "CONTAIN", "CONTAINING", "CONTAINS", "COVER", "COVERING", "COVERS", "EMBRACE", "EMBRACES", "EMBRACING", "ENCIRCLE", "ENCIRCLES", "ENCIRCLING", "ENFOLD", "ENFOLDING", "ENFOLDS", "ENVELOP", "ENVELOPING", "ENVELOPS", "EXTERNAL", "FLANK", "FLANKING", "FLANKS", "FRAME", "FRAMED", "FRAMING", "FRAMES", "GRASP", "GRASPING", "GRASPS", "HARBOUR", "HARBOURS", "HARBOURING", "HOLD", "HOLDING", "HOLDS", "HOUSE", "HOUSES", "HOUSING", "OUTSIDE", "RING", "RINGING", "RINGS", "ROUND", "SHELTER", "SHELTERING", "SHELTERS", "SURROUND", "SURROUNDING", "SURROUNDS", "SWALLOW", "SWALLOWING", "SWALLOWS", "TAKE IN", "TAKES IN", "TAKING IN", "WITHOUT", "WRAP", "WRAPPING", "WRAPS");
        thesaurus = new Thesaurus();
        verbose = false;
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
    public String solve(String phrase, String hint, int... answerLength)
    {
        // Check if any known keyword is in phrase
        phrase = phrase.toLowerCase();
        String key = getKeyword(phrase);
        if (isRelevant(phrase)) {
            // Access words to the far left/right of the phrase by using split(), then
            // Insert synonyms/related words of far left/right words into potentialAnswers, then
            // Remove the words not of the appropriate length using an Iterator
            String[] splitPhrase = phrase.split("\\s+");
            ArrayList<String> potentialAnswers = ((ArrayList<String>) thesaurus.getAllSynonymsXML(splitPhrase[0]));
            potentialAnswers.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(splitPhrase[splitPhrase.length - 1]));
            potentialAnswers.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(splitPhrase[0]));
            potentialAnswers.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(splitPhrase[splitPhrase.length - 1]));
            for (Iterator<String> iter = potentialAnswers.listIterator(); iter.hasNext(); ) {
                String a = iter.next();
                if (((answerLength.length == 1) && ((a.length() != answerLength[0]) || (a.contains(" ")))) || ((answerLength.length > 1) && (!a.contains(" "))))
                    iter.remove();
            }

            // Get left and right half of phrase by using splitPhrase(), then
            // Get list of potential cryptic crossword solutions by using getSolutions(), then
            // Compare against list of synonyms, and return the result by using compareLists()
            ArrayList<String> finalList = compareLists(potentialAnswers, getSolutions(phrase, splitPhrase(phrase, key).get(0), splitPhrase(phrase, key).get(1), answerLength));
            if (finalList.size() == 1)
                return finalList.get(0);
            return null;
        }
        else
        {
            // No known keyword is in phrase
            return null;
        }
    }

    // Compare the list of solutions to the list of synonyms.
    // Return a match, else return possible answers
    public ArrayList<String> compareLists(ArrayList<String> synonyms, ArrayList<String> solutions)
    {
        // Loop through solutions and synonyms, looking for a matches
        ArrayList<String> solutionsList = new ArrayList<String>();
        for (String word : solutions) {
            for (String synonym : synonyms) {
                if (word.equals(synonym)) {
                    solutionsList.add(synonym.toLowerCase());
                    return solutionsList;
                }
            }
        }
        // No match found, so return possible results
        return solutions;
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

    // Return a list containing the left and right half of the phrase, split on the keyword
    public ArrayList<String> splitPhrase(String phrase, String keyword)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add(phrase.substring(0, phrase.indexOf(keyword)).trim());
        list.add(phrase.substring(phrase.lastIndexOf(keyword) + keyword.length()).trim());
        return list;
    }

    // Return a list of potential solutions given the left and right half of the phrase
    public ArrayList<String> getSolutions(String phrase, String leftHalf, String rightHalf, int... answerLength)
    {
        ArrayList<String> leftSynonyms = new ArrayList<String>();
        ArrayList<String> rightSynonyms = new ArrayList<String>();
        String[] leftSplit = leftHalf.split("\\s+");
        String[] rightSplit = rightHalf.split("\\s+");
        String leftWord = leftSplit[leftSplit.length - 1];
        String rightWord = rightSplit[0];

        // Add synonyms/related words of left/right most words to arrays (including the word itself), then
        // Add synonyms/related words of 2 left/right most words to arrays (including the word itself)
        leftSynonyms.add(leftWord);
        rightSynonyms.add(rightWord);

        if (leftWord != null) {
            leftSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(leftWord));
            leftSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(leftWord));
        }
        if (rightWord != null) {
            rightSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(rightWord));
            rightSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(rightWord));
        }
        if (leftSplit.length >= 2) {
            leftSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
            leftSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
        }
        if (rightSplit.length >= 2) {
            rightSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(rightSplit[0] + " " + rightSplit[1]));
            rightSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(rightSplit[0] + " " + rightSplit[1]));
        }

        // Calculate list of mixed words by using returnContainedWords(), then
        // Remove the answers that don't adhere to the answerLength
        ArrayList<String> containedWords = returnContainedWords(leftSynonyms, rightSynonyms);
        if (answerLength.length == 1) {
            for (Iterator<String> iter = containedWords.listIterator(); iter.hasNext(); ) {
                String a = iter.next();
                if (a.length() != answerLength[0])
                    iter.remove();
            }
        }
        return containedWords;
    }

    // Return a list of all possible combinations of words given in leftSynonyms/rightSynonyms
    public ArrayList<String> returnContainedWords(ArrayList<String> leftSynonyms, ArrayList<String> rightSynonyms)
    {
        ArrayList<String> possibleWords = new ArrayList<String>();

        // Loop through all words, combining left/right words in every possible way
        for (String leftWord : leftSynonyms) {
            for (String rightWord : rightSynonyms) {
                possibleWords.add((leftWord + rightWord).toLowerCase());
                possibleWords.add((rightWord + leftWord).toLowerCase());

                // Place leftWord in every possible position in rightWord, add to list
                for (int i = 1; i < rightWord.length(); i++) {
                    String possibleWord = rightWord.substring(0, i).trim() + leftWord.trim() + rightWord.substring(i);

                    if (isWord(possibleWord))
                        possibleWords.add(possibleWord.toLowerCase());
                }

                // Place rightWord in every possible position in leftWord, add to list
                for (int i = 1; i < leftWord.length(); i++) {
                    String possibleWord = leftWord.substring(0, i).trim() + rightWord.trim() + leftWord.substring(i);

                    if (isWord(possibleWord))
                        possibleWords.add(possibleWord.toLowerCase());
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