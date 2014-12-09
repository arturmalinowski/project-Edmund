package cs.group.edmund.clue;

import cs.group.edmund.utils.Helper;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public class DeletionClue implements Clue {

    private final List<String> keyWordsHead, keyWordsTail, keyWordsBoth, keyWordsMiddle, keyWords;
    private Thesaurus thesaurus;
    private Helper helper;
    private int searchIntensity;

    public DeletionClue() {
        keyWordsHead = asList("WITHOUT FIRST", "DROPPING INTRODUCTION", "AFTER COMMENCEMENT", "BEGINNING TO GO", "BEHEADED", "BEHEADING", "DECAPITATED", "FIRST OFF", "HEADLESS", "HEAD OFF", "INITIALLY LACKING", "LEADERLESS", "LOSING OPENER", "MISSING THE FIRST", "NEEDING NO INTRODUCTION", "NOT BEGINNING", "NOT COMMENCING", "NOT STARTING", "START OFF", "START TO GO", "SCRATCH THE HEAD", "STRIKE THE HEAD", "UNINITIATED", "UNSTARTED", "WITHOUT ORIGIN");
        keyWordsTail = asList("ENDLESSLY", "ENDLESS", "BACKING AWAY", "ABRIDGED", "ALMOST", "BACK OFF", "CLIPPED", "CURTAILED", "CUT SHORT", "DETAILED", "EARLY CLOSING", "FALLING SHORT", "FINISH OFF", "FOR THE MOST PART", "INCOMPLETE", "INTERMINABLE", "LACKING FINISH", "MISSING THE LAST", "MOST", "MOSTLY", "NEARLY", "NOT COMPLETELY", "NOT FULLY", "NOT QUITE", "SHORT", "SHORTENING", "TAILLESS", "UNENDING", "UNFINISHED", "WITHOUT END");
        keyWordsBoth = asList("EDGES AWAY", "LACKING WINGS", "LIMITLESS", "LOSING MARGINS", "SHELLED", "SIDES SPLITTING", "TRIMMED", "UNLIMITED", "WINGLESS", "WITHOUT LIMITS");
        keyWordsMiddle = asList("HOLLOW", "CORED", "DISHEARTENED", "EMPTIED", "EMPTY", "EVACUATED", "FILLETED", "GUTTED", "HEARTLESS", "HOLLOW", "LOSING HEART", "UNCENTERED");
        keyWords = asList("AFTER COMMENCEMENT", "BEGINNING TO GO", "BEHEADED", "BEHEADING", "DECAPITATED", "FIRST OFF", "HEADLESS", "HEAD OFF", "INITIALLY LACKING", "LEADERLESS", "LOSING OPENER", "MISSING THE FIRST", "NEEDING NO INTRODUCTION", "NOT BEGINNING", "NOT COMMENCING", "NOT STARTING", "START OFF", "START TO GO", "SCRATCH THE HEAD", "STRIKE THE HEAD", "UNINITIATED", "UNSTARTED", "WITHOUT ORIGIN", "ABRIDGED", "ALMOST", "BACK OFF", "CLIPPED", "CURTAILED", "CUT SHORT", "DETAILED", "EARLY CLOSING", "ENDLESS", "FALLING SHORT", "FINISH OFF", "FOR THE MOST PART", "INCOMPLETE", "INTERMINABLE", "LACKING FINISH", "MISSING THE LAST", "MOST", "MOSTLY", "NEARLY", "NOT COMPLETELY", "NOT FULLY", "NOT QUITE", "SHORT", "SHORTENING", "TAILLESS", "UNENDING", "UNFINISHED", "WITHOUT END", "EDGES AWAY", "LACKING WINGS", "LIMITLESS", "LOSING MARGINS", "SHELLED", "SIDES SPLITTING", "TRIMMED", "UNLIMITED", "WINGLESS", "WITHOUT LIMITS", "CORED", "DISHEARTENED", "EMPTIED", "EMPTY", "EVACUATED", "FILLETED", "GUTTED", "HEARTLESS", "HOLLOW", "LOSING HEART", "UNCENTERED");
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
    public String solve(String phrase, String hint, int... answerLength) {

        // Check if any known keyword is in phrase
        phrase = phrase.toLowerCase();
        String key = getKeyword(phrase);

        if(isRelevant(phrase)) {
            String[] splitPhrase = phrase.split("\\s+");

            ArrayList<String> answers = new ArrayList<String>();
            if (phrase.substring(phrase.indexOf(" ")+1).contains(key))
                answers.add(solveFor(splitPhrase[0], phrase.substring(phrase.indexOf(" ")+1), key, hint, answerLength));
            if (phrase.substring(0, phrase.lastIndexOf(" ")).contains(key))
                answers.add(solveFor(splitPhrase[splitPhrase.length - 1], phrase.substring(0, phrase.lastIndexOf(" ")), key, hint, answerLength));

            // Return definitive answer
            for (String answer : answers) {
                if ((answer != null) && (!answer.contains(","))) {
                    return answer;
                }
            }
            // Last resort, try synonyms of related words
            if (searchIntensity != 3) {
                searchIntensity = 3;
                return solve(phrase, hint, answerLength);
            }
            // Return Possible answers
            for (String answer : answers) {
                if ((answer != null) && (answer.contains(","))) {
                    return answer;
                }
            }
        }
        return "Answer not found!";
    }

    //
    public String solveFor(String assumedClue, String phrase, String keyword, String hint, int... answerLength)
    {
        ArrayList<String> potentialAnswers = new ArrayList<String>();
        potentialAnswers.addAll(thesaurus.getAllSynonymsXML(assumedClue));
        if (searchIntensity > 1)
            potentialAnswers.addAll(thesaurus.getRelatedWordsXML(assumedClue));
        if (searchIntensity > 2)
            potentialAnswers.addAll(getRelatedWordSynonyms(thesaurus.getAllSynonymsXML(assumedClue)));
        if (potentialAnswers != null) {
            potentialAnswers = filterByAnswerLength(potentialAnswers, answerLength);
            potentialAnswers = filterByHints(potentialAnswers, hint);
            potentialAnswers = helper.removeDuplicates(potentialAnswers);
        }

        // Deduce which deletion clue type it is, and solve for that type
        ArrayList<String> solutionsList = null;
        String deletionType = getDeletionType(phrase);

        if (deletionType.equals("head")) {
            solutionsList = returnBeheadment(assumedClue, phrase, keyword, hint, answerLength);
        }
        if (deletionType.equals("tail")) {
            solutionsList = returnCurtailment(assumedClue, phrase, keyword, hint, answerLength);
        }
        if (deletionType.equals("both")) {

        }
        if (deletionType.equals("middle")) {

        }

        ArrayList<String> answerList = compareLists(potentialAnswers, solutionsList);

        // Definitive answer found
        if (answerList.size() == 1) {
            return answerList.get(0);
        }
        // More than one match found
        else if (answerList.size() > 1) {
            String likelyAnswers = "";
            for (String w : answerList) {
                likelyAnswers = likelyAnswers + w + ", ";
            }
            return likelyAnswers;
        }
        // No matches found
        else if((answerList.size() == 0) && (solutionsList.size() > 0)) {
            String possibleAnswers = "";
            for (String word : solutionsList) {
                possibleAnswers = possibleAnswers + word + ", ";
            }
            return possibleAnswers;
        }
        // No possible answers found
        else {
            return null;
        }
    }

    //
    public ArrayList<String> returnBeheadment(String assumedClue, String phrase, String keyword, String hint, int... answerLength)
    {
        ArrayList<String> possibleSolutions = new ArrayList<String>();

        String leftHalf = splitPhrase(phrase, keyword).get(0);
        String rightHalf = splitPhrase(phrase, keyword).get(1);
        String[] leftSplit = leftHalf.split("\\s+");
        String[] rightSplit = rightHalf.split("\\s+");
        String leftWord = leftSplit[leftSplit.length - 1];
        String rightWord = rightSplit[0];

        if (leftWord.length() > 1)
            possibleSolutions.add(leftWord.substring(1));
        if (rightWord.length() > 1)
            possibleSolutions.add(rightWord.substring(1));

        ArrayList<String> leftSynonyms = thesaurus.getAllSynonymsXML(leftWord);
        for (String word : leftSynonyms) {
            if (word.length() > 1)
                possibleSolutions.add(word.substring(1));
        }
        ArrayList<String> rightSynonyms = thesaurus.getAllSynonymsXML(rightWord);
        for (String word : rightSynonyms) {
            if (word.length() > 1)
                possibleSolutions.add(word.substring(1));
        }

        if (searchIntensity > 1) {
            for (String word : thesaurus.getRelatedWordsXML(leftWord)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(1));
            }
            for (String word : thesaurus.getRelatedWordsXML(rightWord)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(1));
            }
        }

        if (searchIntensity > 2) {
            for (String word : getRelatedWordSynonyms(leftSynonyms)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(1));
            }
            for (String word : getRelatedWordSynonyms(rightSynonyms)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(1));
            }
        }

        possibleSolutions = filterByAnswerLength(possibleSolutions, answerLength);
        possibleSolutions = filterByHints(possibleSolutions, hint);
        possibleSolutions = helper.removeDuplicates(possibleSolutions);

        return possibleSolutions;
    }

    //
    public ArrayList<String> returnCurtailment(String assumedClue, String phrase, String keyword, String hint, int... answerLength)
    {
        ArrayList<String> possibleSolutions = new ArrayList<String>();

        String leftHalf = splitPhrase(phrase, keyword).get(0);
        String rightHalf = splitPhrase(phrase, keyword).get(1);
        String[] leftSplit = leftHalf.split("\\s+");
        String[] rightSplit = rightHalf.split("\\s+");
        String leftWord = leftSplit[leftSplit.length - 1];
        String rightWord = rightSplit[0];

        if (leftWord.length() > 1)
            possibleSolutions.add(leftWord.substring(0, leftWord.length() - 1));
        if (rightWord.length() > 1)
            possibleSolutions.add(rightWord.substring(0, rightWord.length() - 1));

        ArrayList<String> leftSynonyms = thesaurus.getAllSynonymsXML(leftWord);
        for (String word : leftSynonyms) {
            if (word.length() > 1)
                possibleSolutions.add(word.substring(0, word.length() - 1));
        }
        ArrayList<String> rightSynonyms = thesaurus.getAllSynonymsXML(rightWord);
        for (String word : rightSynonyms) {
            if (word.length() > 1)
                possibleSolutions.add(word.substring(0, word.length() - 1));
        }

        if (searchIntensity > 1) {
            for (String word : thesaurus.getRelatedWordsXML(leftWord)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(0, word.length() - 1));
            }
            for (String word : thesaurus.getRelatedWordsXML(rightWord)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(0, word.length() - 1));
            }
        }

        if (searchIntensity > 2) {
            for (String word : getRelatedWordSynonyms(leftSynonyms)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(0, word.length() - 1));
            }
            for (String word : getRelatedWordSynonyms(rightSynonyms)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(0, word.length() - 1));
            }
        }

        possibleSolutions = filterByAnswerLength(possibleSolutions, answerLength);
        possibleSolutions = filterByHints(possibleSolutions, hint);
        possibleSolutions = helper.removeDuplicates(possibleSolutions);

        return possibleSolutions;
    }

    // Return a list containing the left and right half of the phrase, split on the keyword
    public ArrayList<String> splitPhrase(String phrase, String keyword)
    {
        ArrayList<String> list = new ArrayList<>();
        list.add(phrase.substring(0, phrase.indexOf(keyword)).trim());
        list.add(phrase.substring(phrase.lastIndexOf(keyword) + keyword.length()).trim());
        return list;
    }

    // Compare the list of solutions to the list of synonyms.
    // Return a match, else return possible answers
    public ArrayList<String> compareLists(ArrayList<String> synonyms, ArrayList<String> solutions)
    {
        ArrayList<String> possibleAnswers = new ArrayList<String>();

        if ((synonyms != null) && (solutions != null)) {
            // Loop through solutions and synonyms, looking for a matches
            for (String word : solutions) {
                for (String synonym : synonyms) {
                    if (word.equals(synonym)) {
                        possibleAnswers.add(word);
                    }
                }
            }
        }
        // No match found, so return possible results
        return possibleAnswers;
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

    // Return the deletion type
    public String getDeletionType(String phrase)
    {
        // Check each list
        for (String keyWord : keyWordsHead) {
            if (phrase.contains(keyWord.toLowerCase()))
                return "head";
        }
        for (String keyWord : keyWordsTail) {
            if (phrase.contains(keyWord.toLowerCase()))
                return "tail";
        }
        for (String keyWord : keyWordsBoth) {
            if (phrase.contains(keyWord.toLowerCase()))
                return "both";
        }
        for (String keyWord : keyWordsMiddle) {
            if (phrase.contains(keyWord.toLowerCase()))
                return "middle";
        }
        return null;
    }

    // Return synonyms of related words
    public ArrayList<String> getRelatedWordSynonyms(ArrayList<String> synonyms) {
        ArrayList<String> relatedList = new ArrayList<String>();
        for (String word : synonyms) {
            relatedList.addAll(thesaurus.getRelatedWordsXML(word));
        }
        return relatedList;
    }

    // Filter Methods

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
}