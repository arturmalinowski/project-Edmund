package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import cs.group.edmund.utils.Helper;

import static java.util.Arrays.asList;

public class ContainerClue implements Clue {

    private final List<String> keyWords;
    private Thesaurus thesaurus;
    private int searchIntensity;

    public ContainerClue() {
        keyWords = asList("CAPTURING", "SURROUNDED", "PUT IN", "KEEPS", "INSIDE", " IN ", "CLOTHING", "ABOUT", "ADMIT", "ADMITS", "ADMITTING", "AROUND", "BESIEGE", "BESIEGES", "BESIEGING", "BOX", "BOXES", "BOXING", "BRIDGE", "BRIDGES", "BRIDGING", "CAPTURE", "CAPTURED", "CAPTURES", "CAPTURING", "CATCH", "CATCHES", "CATCHING", "CIRCLE", "CIRCLES", "CIRCLING", "CLUTCH", "CLUTCHES", "CLUTCHING", "CONTAIN", "CONTAINING", "CONTAINS", "COVER", "COVERING", "COVERS", "EMBRACE", "EMBRACES", "EMBRACING", "ENCIRCLE", "ENCIRCLES", "ENCIRCLING", "ENFOLD", "ENFOLDING", "ENFOLDS", "ENVELOP", "ENVELOPING", "ENVELOPS", "EXTERNAL", "FLANK", "FLANKING", "FLANKS", "FRAME", "FRAMED", "FRAMING", "FRAMES", "GRASP", "GRASPING", "GRASPS", "HARBOUR", "HARBOURS", "HARBOURING", "HOLD", "HOLDING", "HOLDS", "HOUSE", "HOUSES", "HOUSING", "OUTSIDE", "ENTERING", "RINGS", "ROUND", "SHELTER", "SHELTERING", "SHELTERS", "SURROUND", "SURROUNDING", "SURROUNDS", "SWALLOW", "SWALLOWING", "SWALLOWS", "TAKE IN", "TAKES IN", "TAKING IN", "WITHOUT", "WRAP", "WRAPPING", "WRAPS");
        thesaurus = new Thesaurus();
        searchIntensity = 2;
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
            String[] splitPhrase = phrase.split("\\s+");

            ArrayList<String> answers = new ArrayList<>();
            answers.add(solveFor(splitPhrase[0], phrase.substring(phrase.indexOf(" ")+1), key, hint, answerLength));
            answers.add(solveFor(splitPhrase[splitPhrase.length - 1], phrase.substring(0, phrase.lastIndexOf(" ")), key, hint, answerLength));

            for (String answer : answers) {
                if ((answer != null) && (!answer.contains(","))) {
                    return answer;
                }
            }
            if (searchIntensity != 3) {
                // no answer found, last resort
                searchIntensity = 3;
                return solve(phrase, hint, answerLength);
            }
            else {
                for (String answer : answers) {
                    if ((answer != null) && (answer.contains(","))) {
                        return answer;
                    }
                }
            }
        }
        return "Answer not found!";
    }

    public String solveFor(String assumedClue, String phrase, String keyword, String hint, int... answerLength)
    {
        ArrayList<String> potentialAnswers = new ArrayList<>();
        potentialAnswers.addAll(thesaurus.getAllSynonymsXML(assumedClue));
        if (searchIntensity > 1)
            potentialAnswers.addAll(thesaurus.getRelatedWordsXML(assumedClue));
        if (searchIntensity > 2)
            potentialAnswers.addAll(getRelatedSynonyms(assumedClue)); //delete
        potentialAnswers = Helper.filterAll(potentialAnswers, hint, answerLength);

        ArrayList<String> solutionsList = null;
        if (phrase.contains(keyword))
            solutionsList = getSolutions(splitPhrase(phrase, keyword).get(0), splitPhrase(phrase, keyword).get(1), answerLength);
        if (solutionsList != null)
            solutionsList = Helper.filterAll(solutionsList, hint, answerLength);

        String answer = compareLists(potentialAnswers, solutionsList);
        if (answer != null)
            return answer;
        else if ((solutionsList == null) || (potentialAnswers == null)) {
            return null;
        }
        else if (solutionsList.size() > 1) {
            String possibleAnswers = "";
            for (String word : solutionsList) {
                possibleAnswers = possibleAnswers + word + ", ";
            }
            return possibleAnswers;
        }
        else {
            return null;
        }
    }

    // Compare the list of solutions to the list of synonyms.
    // Return a match, else return possible answers
    public String compareLists(ArrayList<String> synonyms, ArrayList<String> solutions)
    {
        if ((synonyms != null) && (solutions != null)) {
            // Loop through solutions and synonyms, looking for a matches
            for (String word : solutions) {
                for (String synonym : synonyms) {
                    if (word.equals(synonym)) {
                        return word;
                    }
                }
            }
        }
        // No match found, so return possible results
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

    // Return a list containing the left and right half of the phrase, split on the keyword
    public ArrayList<String> splitPhrase(String phrase, String keyword)
    {
        ArrayList<String> list = new ArrayList<>();
        list.add(phrase.substring(0, phrase.indexOf(keyword)).trim());
        list.add(phrase.substring(phrase.lastIndexOf(keyword) + keyword.length()).trim());
        return list;
    }

    // Return a list of potential solutions given the left and right half of the phrase
    public ArrayList<String> getSolutions(String leftHalf, String rightHalf, int... answerLength)
    {
        ArrayList<String> leftSynonyms = new ArrayList<>();
        ArrayList<String> rightSynonyms = new ArrayList<>();
        String[] leftSplit = leftHalf.split("\\s+");
        String[] rightSplit = rightHalf.split("\\s+");
        String leftWord = leftSplit[leftSplit.length - 1];
        String rightWord = rightSplit[0];

        // Add synonyms/related words of left/right most words to arrays (including the word itself), then
        // Add synonyms/related words of 2 left/right most words to arrays (including the word itself), then
        leftSynonyms.add(leftWord);
        rightSynonyms.add(rightWord);

        // Find synonyms/related words before mixing them
        if ((leftSplit.length >= 1) && (rightSplit.length >= 1)) {
            leftSynonyms.addAll(thesaurus.getAllSynonymsXML(leftWord));
            rightSynonyms.addAll(thesaurus.getAllSynonymsXML(rightWord));
            if (searchIntensity > 1) {
                leftSynonyms.addAll(thesaurus.getRelatedWordsXML(leftWord));
                rightSynonyms.addAll(thesaurus.getRelatedWordsXML(rightWord));
            }
            if (searchIntensity > 2) {
                leftSynonyms.addAll(getRelatedSynonyms(leftWord));
                rightSynonyms.addAll(getRelatedSynonyms(rightWord));
            }
        }

        if (leftSplit.length >= 2) {
            leftSynonyms.addAll(thesaurus.getAllSynonymsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
            if (searchIntensity > 1)
                leftSynonyms.addAll(thesaurus.getRelatedWordsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
            if (searchIntensity > 2)
                leftSynonyms.addAll(getRelatedSynonyms(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
        }

        if (rightSplit.length >= 2) {
            rightSynonyms.addAll(thesaurus.getAllSynonymsXML(rightSplit[0] + " " + rightSplit[1]));
            if (searchIntensity > 1)
                rightSynonyms.addAll(thesaurus.getRelatedWordsXML(rightSplit[0] + " " + rightSplit[1]));
            if (searchIntensity > 2)
                rightSynonyms.addAll(getRelatedSynonyms(rightSplit[0] + " " + rightSplit[1]));
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
        ArrayList<String> possibleWords = new ArrayList<>();

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

    //
    public ArrayList<String> getRelatedSynonyms(String word) {
        //
        ArrayList<String> completeList = new ArrayList<>();
        ArrayList<String> relatedWords = thesaurus.getRelatedWordsXML(word);

        for (String w : relatedWords) {
            completeList.addAll(thesaurus.getAllSynonymsXML(w));
        }

        return completeList;
    }
}