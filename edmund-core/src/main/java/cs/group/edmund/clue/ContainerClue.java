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
    private Helper helper;
    private int searchIntensity;

    public ContainerClue() {
        keyWords = asList("CAPTURING", "SURROUNDED", "PUT IN", "KEEPS", "INSIDE", " IN ", "CLOTHING", "ABOUT", "ADMIT", "ADMITS", "ADMITTING", "AROUND", "BESIEGE", "BESIEGES", "BESIEGING", "BOX", "BOXES", "BOXING", "BRIDGE", "BRIDGES", "BRIDGING", "CAPTURE", "CAPTURED", "CAPTURES", "CAPTURING", "CATCH", "CATCHES", "CATCHING", "CIRCLE", "CIRCLES", "CIRCLING", "CLUTCH", "CLUTCHES", "CLUTCHING", "CONTAIN", "CONTAINING", "CONTAINS", "COVER", "COVERING", "COVERS", "EMBRACE", "EMBRACES", "EMBRACING", "ENCIRCLE", "ENCIRCLES", "ENCIRCLING", "ENFOLD", "ENFOLDING", "ENFOLDS", "ENVELOP", "ENVELOPING", "ENVELOPS", "EXTERNAL", "FLANK", "FLANKING", "FLANKS", "FRAME", "FRAMED", "FRAMING", "FRAMES", "GRASP", "GRASPING", "GRASPS", "HARBOUR", "HARBOURS", "HARBOURING", "HOLD", "HOLDING", "HOLDS", "HOUSE", "HOUSES", "HOUSING", "OUTSIDE", "ENTERING", "RINGS", "ROUND", "SHELTER", "SHELTERING", "SHELTERS", "SURROUND", "SURROUNDING", "SURROUNDS", "SWALLOW", "SWALLOWING", "SWALLOWS", "TAKE IN", "TAKES IN", "TAKING IN", "WITHOUT", "WRAP", "WRAPPING", "WRAPS");
        thesaurus = new Thesaurus();
        helper = new Helper();
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

            ArrayList<String> answers = new ArrayList<String>();
            answers.add(solveFor(splitPhrase[0], phrase.substring(phrase.indexOf(" ")+1), key, hint, answerLength));
            answers.add(solveFor(splitPhrase[splitPhrase.length - 1], phrase.substring(0, phrase.lastIndexOf(" ")), key, hint, answerLength));

            for (String answer : answers) {
                if ((answer != null) && (!answer.contains(","))) {
                    return answer;
                }
            }
            for (String answer : answers) {
                if ((answer != null) && (answer.contains(","))) {
                    return answer;
                }
            }
            // no answer found, last resort
            searchIntensity = 3;
            return solve(phrase, hint, answerLength);
        }
        return "Answer not found!";
    }

    public String solveFor(String assumedClue, String phrase, String keyword, String hint, int... answerLength)
    {
        ArrayList<String> potentialAnswers = new ArrayList<String>();
        potentialAnswers.addAll(thesaurus.getAllSynonymsXML(assumedClue));
        if (searchIntensity > 1)
            potentialAnswers.addAll(thesaurus.getRelatedWordsXML(assumedClue));
        if (searchIntensity > 2)
            potentialAnswers.addAll(getRelatedSynonyms(assumedClue)); //delete
        if (potentialAnswers != null) {
            potentialAnswers = filterByAnswerLength(potentialAnswers, answerLength);
            potentialAnswers = filterByHints(potentialAnswers, hint);
            potentialAnswers = helper.removeDuplicates(potentialAnswers);
        }

        ArrayList<String> solutionsList = null;
        if (phrase.contains(keyword))
            solutionsList = getSolutions(splitPhrase(phrase, keyword).get(0), splitPhrase(phrase, keyword).get(1), answerLength);
        if (solutionsList != null) {
            solutionsList = filterByAnswerLength(solutionsList, answerLength);
            solutionsList = filterByHints(solutionsList, hint);
            solutionsList = helper.removeDuplicates(solutionsList);
        }

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

    // Return a list containing words corresponding with the given hint
    public ArrayList<String> filterByHints(ArrayList<String> originalList, String hint)
    {
            String[] hintLetters = hint.split("");

            // Loop through possible answers, removing
            for (Iterator<String> iter = originalList.listIterator(); iter.hasNext(); ) {
                // Loop through letters in hint, removing the answers which do not conform to the hint
                String a = iter.next();
                String[] answerLetters = a.split("");

                for (int i = 1; i < hintLetters.length; i++) {
                    if ((!hintLetters[i].equals(".")) && (!hintLetters[i].equals(answerLetters[i]))) {
                        iter.remove();
                    }
                }
            }

        return originalList;
    }

    // Return a list containing words corresponding with the given answerLength
    public ArrayList<String> filterByAnswerLength(ArrayList<String> originalList, int... answerLength) {
        if (originalList.size() >= 1) {
            if (answerLength.length == 1) {
                // AnswerLength is only 1
                for (Iterator<String> iter = originalList.listIterator(); iter.hasNext(); ) {
                    String a = iter.next();
                    if ((a.length() != answerLength[0]) || (a.contains(" ")))
                        // Incorrect word length, remove from array
                        iter.remove();
                }
            } else if (answerLength.length > 1) {
                // answerLength is greater than 1
                for (Iterator<String> iter = originalList.listIterator(); iter.hasNext(); ) {
                    String a = iter.next();
                    String[] splitString = a.split("\\s+");

                    if (splitString.length != answerLength.length) {
                        // Incorrect amount of words, remove from array
                        iter.remove();
                    } else {
                        // Check each word for length
                        for (int i = 0; i < splitString.length; i++) {
                            if (splitString[i].length() != answerLength[i]) {
                                // Incorrect word length, remove from array
                                iter.remove();
                            }
                        }
                    }
                }
            }
        }
        return originalList;
    }

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