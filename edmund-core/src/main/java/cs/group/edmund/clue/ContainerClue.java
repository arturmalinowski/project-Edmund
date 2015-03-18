package cs.group.edmund.clue;

import cs.group.edmund.utils.Helper;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class ContainerClue implements Clue {

    private final List<String> keyWords;
    private Thesaurus thesaurus;
    private String hint;
    private int searchIntensity;

    public ContainerClue(Thesaurus thesaurus) {
        keyWords = asList("CAPTURING", "SURROUNDED", "PUT IN", "KEEPS", "INSIDE", " IN ", "CLOTHING", "ABOUT", "ADMIT", "ADMITS", "ADMITTING", "AROUND", "BESIEGE", "BESIEGES", "BESIEGING", "BOX", "BOXES", "BOXING", "BRIDGE", "BRIDGES", "BRIDGING", "CAPTURE", "CAPTURED", "CAPTURES", "CAPTURING", "CATCH", "CATCHES", "CATCHING", "CIRCLE", "CIRCLES", "CIRCLING", "CLUTCH", "CLUTCHES", "CLUTCHING", "CONTAIN", "CONTAINING", "CONTAINS", "COVER", "COVERING", "COVERS", "EMBRACE", "EMBRACES", "EMBRACING", "ENCIRCLE", "ENCIRCLES", "ENCIRCLING", "ENFOLDING", "ENFOLDS", "ENFOLD", "ENVELOPING", "ENVELOPS", "ENVELOP", "EXTERNAL", "FLANKING", "FLANK", "FLANKS", "FRAMED", "FRAMES", "FRAMING", "FRAME", "GRASP", "GRASPING", "GRASPS", "HARBOUR", "HARBOURS", "HARBOURING", "HOLD", "HOLDING", "HOLDS", "HOUSE", "HOUSES", "HOUSING", "OUTSIDE", "ENTERING", "RINGS", "ROUND", "SHELTER", "SHELTERING", "SHELTERS", "SURROUND", "SURROUNDING", "SURROUNDS", "SWALLOW", "SWALLOWING", "SWALLOWS", "TAKE IN", "TAKES IN", "TAKING IN", "WITHOUT", "WRAP", "WRAPPING", "WRAPS");
        this.thesaurus = thesaurus;
        searchIntensity = 0;
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
    public Optional<List<String>> solve(String phrase, String hint, int... answerLength)
    {
        // Check if any known keyword is in phrase
        phrase = phrase.toLowerCase();
        String key = getKeyword(phrase);
        this.hint = hint;

        if (isRelevant(phrase)) {
            ArrayList<String> splitPhrase = Helper.removeSpecialChar(phrase);

            ArrayList<String> answers = new ArrayList<>();
            answers.addAll(solveFor(splitPhrase.get(0), phrase.substring(phrase.indexOf(" ")+1), key, hint, answerLength)); // assuming hint is first word
            answers.addAll(solveFor(splitPhrase.get(splitPhrase.size() - 1), phrase.substring(0, phrase.lastIndexOf(" ")), key, hint, answerLength)); // assuming hint is last word

            if (answers.size() > 0)
                return Optional.of(answers);
            else if (searchIntensity < 1) {
                searchIntensity++;
                return solve(phrase, hint, answerLength);
            }
        }
        return Optional.empty();
    }

    //
    public List<String> solveFor(String assumedClue, String phrase, String keyword, String hint, int... answerLength)
    {
        // Get synonyms and related words for the assumed answer clue
        ArrayList<String> potentialAnswers = new ArrayList<>();
        potentialAnswers.addAll(thesaurus.getAllSynonymsXML(assumedClue));
        potentialAnswers.addAll(thesaurus.getRelatedWordsXML(assumedClue));
        if (searchIntensity > 0) // NEW
            potentialAnswers.addAll(thesaurus.getRelatedWordsOfSynonymsXML(assumedClue)); // NEW
        potentialAnswers = Helper.filterAll(potentialAnswers, hint, answerLength);

        // Process the rest of the clue to find a match with the assumed answer clue results
        ArrayList<String> solutionsList = null;
        if (phrase.contains(keyword))
            solutionsList = getSolutions(splitPhrase(phrase, keyword).get(0), splitPhrase(phrase, keyword).get(1), answerLength);

        return compareLists(potentialAnswers, solutionsList);
    }

    // Compare the list of solutions to the list of synonyms.
    // Return a match, else return possible answers.
    public List<String> compareLists(ArrayList<String> synonyms, ArrayList<String> solutions)
    {
        ArrayList words = new ArrayList<>();

        if ((synonyms != null) && (solutions != null)) {
            for (String word : solutions) {
                word = word.toLowerCase();
                for (String synonym : synonyms) {
                    synonym = synonym.toLowerCase();
                    if (word.equals(synonym)) {
                        words.add(word);
                    }
                }
            }
        }
        return words;
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
        if ((!leftWord.equals("")) && (leftWord.length() > 1)) {
            leftSynonyms.add(leftWord);

            if  (leftSplit.length >= 1) {
                leftSynonyms.addAll(thesaurus.getAllSynonymsXML(leftWord));
                leftSynonyms.addAll(thesaurus.getRelatedWordsXML(leftWord));
            }

            if (leftSplit.length >= 2) {
                leftSynonyms.addAll(thesaurus.getAllSynonymsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
                leftSynonyms.addAll(thesaurus.getRelatedWordsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
            }
        }
        if ((!rightWord.equals("")) && (rightWord.length() > 1)) {
            rightSynonyms.add(rightWord);

            if  (rightSplit.length >= 1) {
                rightSynonyms.addAll(thesaurus.getAllSynonymsXML(rightWord));
                rightSynonyms.addAll(thesaurus.getRelatedWordsXML(rightWord));
            }

            if (rightSplit.length >= 2) {
                rightSynonyms.addAll(thesaurus.getAllSynonymsXML(rightSplit[0] + " " + rightSplit[1]));
                rightSynonyms.addAll(thesaurus.getRelatedWordsXML(rightSplit[0] + " " + rightSplit[1]));
            }
        }

        // Filter any synonyms larger than the answerLength
        leftSynonyms = filterLargerWords(leftSynonyms, answerLength);
        rightSynonyms = filterLargerWords(rightSynonyms, answerLength);

        // Calculate list of mixed words by using returnContainedWords(), then
        // Remove the answers that don't adhere to the answerLength
        return Helper.filterAll(returnContainedWords(leftSynonyms, rightSynonyms), hint, answerLength);
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
                    possibleWords.add(possibleWord.toLowerCase());
                }

                // Place rightWord in every possible position in leftWord, add to list
                for (int i = 1; i < leftWord.length(); i++) {
                    String possibleWord = leftWord.substring(0, i).trim() + rightWord.trim() + leftWord.substring(i);
                    possibleWords.add(possibleWord.toLowerCase());
                }
            }
        }
        return possibleWords;
    }

    //
    public ArrayList<String> filterLargerWords(ArrayList<String> list, int... answerLength)
    {
        if (list.size() > 0) {
            if (answerLength.length == 1) {
                for (Iterator<String> iter = list.listIterator(); iter.hasNext(); ) {
                    String word = iter.next();
                    if ((word.length() > answerLength[0]) || (word.contains(" ")))
                        iter.remove();
                }
            }
        }
        return list;
    }
}