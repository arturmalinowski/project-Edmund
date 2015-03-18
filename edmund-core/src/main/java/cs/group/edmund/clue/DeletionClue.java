package cs.group.edmund.clue;

import cs.group.edmund.utils.Helper;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class DeletionClue implements Clue {

    private final List<String> keyWordsHead, keyWordsTail, keyWordsBoth, keyWordsMiddle, keyWordsSpecific, keyWords;
    private Thesaurus thesaurus;
    private int searchIntensity;

    public DeletionClue(Thesaurus thesaurus) {
        keyWordsHead = asList("WITHOUT FIRST", "DROPPING INTRODUCTION", "AFTER COMMENCEMENT", "BEGINNING TO GO", "BEHEADED", "BEHEADING", "DECAPITATED", "FIRST OFF", "HEADLESS", "HEAD OFF", "INITIALLY LACKING", "LEADERLESS", "LOSING OPENER", "MISSING THE FIRST", "NEEDING NO INTRODUCTION", "NOT BEGINNING", "NOT COMMENCING", "NOT STARTING", "START OFF", "START TO GO", "SCRATCH THE HEAD", "STRIKE THE HEAD", "UNINITIATED", "UNSTARTED", "WITHOUT ORIGIN");
        keyWordsTail = asList("ENDLESSLY", "ENDLESS", "BACKING AWAY", "ABRIDGED", "ALMOST", "BACK OFF", "CLIPPED", "CURTAILED", "CUT SHORT", "DETAILED", "EARLY CLOSING", "FALLING SHORT", "FINISH OFF", "FOR THE MOST PART", "INCOMPLETE", "INTERMINABLE", "LACKING FINISH", "MISSING THE LAST", "MOST", "MOSTLY", "NEARLY", "NOT COMPLETELY", "NOT FULLY", "NOT QUITE", "SHORT", "SHORTENING", "TAILLESS", "UNENDING", "UNFINISHED", "WITHOUT END");
        keyWordsBoth = asList("EDGES AWAY", "LACKING WINGS", "LIMITLESS", "LOSING MARGINS", "SHELLED", "SIDES SPLITTING", "TRIMMED", "UNLIMITED", "WINGLESS", "WITHOUT LIMITS");
        keyWordsMiddle = asList("HEARTLESSLY", "HEARTLESS", "DISHEARTENED", "LOSING HEART", "HOLLOW", "CORED", "EMPTIED", "EMPTY", "EVACUATED", "FILLETED", "GUTTED", "HOLLOW", "UNCENTERED");
        keyWordsSpecific = asList("CUTTING", "ERASED", "GOES OUT OF", "LEAVES", "MISSING", "NOT", "NO", "REMOVED", "SHORT OF", "STRUCK", "WITHOUT");
        keyWords = new ArrayList<>();
        keyWords.addAll(keyWordsHead);
        keyWords.addAll(keyWordsTail);
        keyWords.addAll(keyWordsBoth);
        keyWords.addAll(keyWordsMiddle);
        keyWords.addAll(keyWordsSpecific);
        this.thesaurus = thesaurus;
        searchIntensity = 0;
    }

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        for (String keyWord : keyWords) {
            if (phrase.contains(keyWord.toLowerCase()))
                return true;
        }
        return false;
    }

    @Override
    public Optional<List<String>> solve(String phrase, String hint, int... answerLength) {

        // Check if any known keyword is in phrase
        phrase = phrase.toLowerCase();
        String key = getKeyword(phrase);

        if (isRelevant(phrase)) {
            ArrayList<String> splitPhrase = Helper.removeSpecialChar(phrase);

            ArrayList<String> answers = new ArrayList<>();
            answers.addAll(solveFor(splitPhrase.get(0), phrase.substring(phrase.indexOf(" ")+1), key, hint, answerLength)); // assuming hint is first word
            if (answers.size() > 0)
                return Optional.of(answers);

            answers.addAll(solveFor(splitPhrase.get(splitPhrase.size() - 1), phrase.substring(0, phrase.lastIndexOf(" ")), key, hint, answerLength)); // assuming hint is last word
            if (answers.size() > 0)
                return Optional.of(answers);
            else if (searchIntensity < 2) {
                searchIntensity++;
                return solve(phrase, hint, answerLength);
            }
        }
        return Optional.empty();
    }

    //
    public List<String> solveFor(String assumedClue, String phrase, String keyword, String hint, int... answerLength) {

        ArrayList<String> potentialAnswers = new ArrayList<>();
        potentialAnswers.addAll(thesaurus.getAllSynonymsXML(assumedClue));
        potentialAnswers.addAll(thesaurus.getRelatedWordsXML(assumedClue));
        if (searchIntensity > 0)
            potentialAnswers.addAll(thesaurus.getRelatedWordsOfSynonymsXML(assumedClue));
        potentialAnswers = Helper.filterAll(potentialAnswers, hint, answerLength);

        // Deduce which deletion clue type it is, and solve for that type
        ArrayList<String> solutionsList = new ArrayList<>();
        String deletionType = getDeletionType(phrase);

        if (deletionType.equals("head")) {
            solutionsList = returnBeheadmentDeletion(phrase, keyword, hint, answerLength);
        }
        if (deletionType.equals("tail")) {
            solutionsList = returnCurtailmentDeletion(phrase, keyword, hint, answerLength);
        }
        if (deletionType.equals("middle")) {
            solutionsList = returnInternalDeletion(phrase, keyword, hint, answerLength);
        }

        return compareLists(potentialAnswers, solutionsList);
    }

    //
    public ArrayList<String> returnBeheadmentDeletion(String phrase, String keyword, String hint, int... answerLength) {
        ArrayList<String> possibleSolutions = new ArrayList<>();

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

        for (String word : thesaurus.getRelatedWordsXML(leftWord)) {
            if (word.length() > 1)
                possibleSolutions.add(word.substring(1));
        }
        for (String word : thesaurus.getRelatedWordsXML(rightWord)) {
            if (word.length() > 1)
                possibleSolutions.add(word.substring(1));
        }

        if (searchIntensity > 1) {
            for (String word : thesaurus.getRelatedWordsOfSynonymsXML(leftWord)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(1));
            }
            for (String word : thesaurus.getRelatedWordsOfSynonymsXML(rightWord)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(1));
            }
        }

        return Helper.filterAll(possibleSolutions, hint, answerLength);
    }

    //
    public ArrayList<String> returnCurtailmentDeletion(String phrase, String keyword, String hint, int... answerLength) {
        ArrayList<String> possibleSolutions = new ArrayList<>();

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

        for (String word : thesaurus.getRelatedWordsXML(leftWord)) {
            if (word.length() > 1)
                possibleSolutions.add(word.substring(0, word.length() - 1));
        }
        for (String word : thesaurus.getRelatedWordsXML(rightWord)) {
            if (word.length() > 1)
                possibleSolutions.add(word.substring(0, word.length() - 1));
        }

        if (searchIntensity > 1) {
            for (String word : thesaurus.getRelatedWordsOfSynonymsXML(leftWord)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(0, word.length() - 1));
            }
            for (String word : thesaurus.getRelatedWordsOfSynonymsXML(rightWord)) {
                if (word.length() > 1)
                    possibleSolutions.add(word.substring(0, word.length() - 1));
            }
        }

        return Helper.filterAll(possibleSolutions, hint, answerLength);
    }

    //
    public ArrayList<String> returnInternalDeletion(String phrase, String keyword, String hint, int... answerLength) {
        ArrayList<String> possibleSolutions = new ArrayList<>();

        String leftHalf = splitPhrase(phrase, keyword).get(0);
        String rightHalf = splitPhrase(phrase, keyword).get(1);
        String[] leftSplit = leftHalf.split("\\s+");
        String[] rightSplit = rightHalf.split("\\s+");
        String leftWord = leftSplit[leftSplit.length - 1];
        String rightWord = rightSplit[0];

        if (leftWord.length() > 2)
            possibleSolutions.addAll(gutMiddle(leftWord));
        if (rightWord.length() > 2)
            possibleSolutions.addAll(gutMiddle(rightWord));

        ArrayList<String> leftSynonyms = thesaurus.getAllSynonymsXML(leftWord);
        for (String word : leftSynonyms) {
            if (word.length() > 3)
                possibleSolutions.addAll(gutMiddle(word));
        }
        ArrayList<String> rightSynonyms = thesaurus.getAllSynonymsXML(rightWord);
        for (String word : rightSynonyms) {
            if (word.length() > 3)
                possibleSolutions.addAll(gutMiddle(word));
        }

        for (String word : thesaurus.getRelatedWordsXML(leftWord)) {
            if (word.length() > 1)
                possibleSolutions.addAll(gutMiddle(word));
        }
        for (String word : thesaurus.getRelatedWordsXML(rightWord)) {
            if (word.length() > 1)
                possibleSolutions.addAll(gutMiddle(word));
        }

        if (searchIntensity > 1) {
            for (String word : thesaurus.getRelatedWordsOfSynonymsXML(leftWord)) {
                if (word.length() > 1)
                    possibleSolutions.addAll(gutMiddle(word));
            }
            for (String word : thesaurus.getRelatedWordsOfSynonymsXML(rightWord)) {
                if (word.length() > 1)
                    possibleSolutions.addAll(gutMiddle(word));
            }
        }

        return Helper.filterAll(possibleSolutions, hint, answerLength);
    }

    // Return the given word, gutting every possible inner letter
    public List<String> gutMiddle(String word) {
        ArrayList<String> guttedWords = new ArrayList<>();

        for (int i = 1; i < word.length() - 1; i++) {
            guttedWords.add(word.substring(0, i) + word.substring(i + 1));
        }
        return guttedWords;
    }

    // Return a list containing the left and right half of the phrase, split on the keyword
    public List<String> splitPhrase(String phrase, String keyword) {
        ArrayList<String> list = new ArrayList<>();
        list.add(phrase.substring(0, phrase.indexOf(keyword)).trim());
        list.add(phrase.substring(phrase.lastIndexOf(keyword) + keyword.length()).trim());
        return list;
    }

    // Compare the list of solutions to the list of synonyms.
    // Return a match, else return possible answers
    public List<String> compareLists(ArrayList<String> synonyms, ArrayList<String> solutions)
    {
        if ((synonyms != null) && (solutions != null)) {
            Set<String> synonymsSet = new HashSet<String>(synonyms);
            Set<String> solutionsSet = new HashSet<String>(solutions);
            synonymsSet.retainAll(solutionsSet);
            return new ArrayList<>(synonymsSet);
        }
        return new ArrayList<>();
    }

    // Return the keyword used in the phrase, else return null.
    public String getKeyword(String phrase) {
        for (String keyWord : keyWords) {
            if (phrase.contains(keyWord.toLowerCase()))
                return keyWord.toLowerCase();
        }
        return null;
    }

    // Return the deletion type
    public String getDeletionType(String phrase) {
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
        return "none";
    }
}