package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import static java.util.Arrays.asList;

public class ContainerClue implements Clue {

    private final List<String> keyWords;
    private Thesaurus thesaurus;
    private boolean verbose;

    public ContainerClue() {
        keyWords = asList("CLOTHING", "ABOUT", "ADMIT", "ADMITS", "ADMITTING", "AROUND", "BESIEGE", "BESIEGES", "BESIEGING", "BOX", "BOXES", "BOXING", "BRIDGE", "BRIDGES", "BRIDGING", "CAPTURE", "CAPTURED", "CAPTURES", "CAPTURING", "CATCH", "CATCHES", "CATCHING", "CIRCLE", "CIRCLES", "CIRCLING", "CLUTCH", "CLUTCHES", "CLUTCHING", "CONTAIN", "CONTAINING", "CONTAINS", "COVER", "COVERING", "COVERS", "EMBRACE", "EMBRACES", "EMBRACING", "ENCIRCLE", "ENCIRCLES", "ENCIRCLING", "ENFOLD", "ENFOLDING", "ENFOLDS", "ENVELOP", "ENVELOPING", "ENVELOPS", "EXTERNAL", "FLANK", "FLANKING", "FLANKS", "FRAME", "FRAMED", "FRAMING", "FRAMES", "GRASP", "GRASPING", "GRASPS", "HARBOUR", "HARBOURS", "HARBOURING", "HOLD", "HOLDING", "HOLDS", "HOUSE", "HOUSES", "HOUSING", "OUTSIDE", "RING", "RINGING", "RINGS", "ROUND", "SHELTER", "SHELTERING", "SHELTERS", "SURROUND", "SURROUNDING", "SURROUNDS", "SWALLOW", "SWALLOWING", "SWALLOWS", "TAKE IN", "TAKES IN", "TAKING IN", "WITHOUT", "WRAP", "WRAPPING", "WRAPS");
        thesaurus = new Thesaurus();
        verbose = true;
    }

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase)
    {
        for (String keyWord : keyWords)
        {
            if (phrase.contains(keyWord.toLowerCase()))
                return true;
        }
        return false;
    }

    @Override
    public String solve(String phrase, int... answerLength)
    {
        // Set phrase to lowercase, and get container clue keyword
        phrase = phrase.toLowerCase();
        String key = getKeyword(phrase);

        // Get side words on the left/right of the phrase by using getSideWords(), then
        // Get synonyms list for first and last words in phrase by using getAllSynonyms(), then
        // Remove the words not of the appropriate length using an Iterator
        ArrayList<String> firstWordSynonyms = ((ArrayList<String>) thesaurus.getAllSynonyms(getSideWords(phrase).get(0)));
        ArrayList<String> lastWordSynonyms = (ArrayList<String>) thesaurus.getAllSynonyms(getSideWords(phrase).get(1));
        firstWordSynonyms.addAll(lastWordSynonyms);
        for (Iterator<String> iter = firstWordSynonyms.listIterator(); iter.hasNext(); )
        {
            String a = iter.next();
            if ((a.length() != answerLength[0]) || (a.contains(" ")))
                iter.remove();
        }
        if (verbose)
            System.out.println("Possible answers at start/end of word: " + firstWordSynonyms); //delete

        // Get left and right half of phrase by using splitPhrase() (removing the keyword), then
        // Get list of potential cryptic crossword solutions by using getSolutions() (assuming the first word is the answer, remove the first word), then
        // Compare against list of synonyms, and return the result by using compareLists() (null if no match has been found)
        return compareLists(firstWordSynonyms, getSolutions(phrase, splitPhrase(phrase, key).get(0), splitPhrase(phrase, key).get(1), answerLength));
    }

    // Compare the list of solutions to the list of synonyms. Return a match, else return null.
    public String compareLists(ArrayList<String> synonyms, ArrayList<String> solutions)
    {
        // Loop through solutions and synonyms, looking for a matches
        for (String word : solutions)
        {
            for (String synonym : synonyms)
            {
                if (word.equals(synonym)) {
                    if(verbose)
                        System.out.println("Found a match: " + synonym.toLowerCase()); //delete
                    return synonym.toLowerCase();
                }
            }
        }
        if(verbose)
            System.out.println("Found no match"); //delete
        return null;
    }

    // Return the keyword used in the phrase
    public String getKeyword(String phrase)
    {
        for (String keyWord : keyWords) {
            if (phrase.contains(keyWord.toLowerCase()))
                return keyWord.toLowerCase();
        }
        return null;
    }

    // Return a list with the first and last words of the phrase
    public ArrayList<String> getSideWords(String phrase)
    {
        //
        ArrayList<String> words = new ArrayList<String>();

        if (phrase.contains(" "))
        {
            words.add(phrase.substring(0, phrase.indexOf(" ")));
            words.add(phrase.substring(phrase.lastIndexOf(" ") + 1));
        }
        return words;
    }

    // Return a list containing the left and right half of the phrase, split on the keyword
    public ArrayList<String> splitPhrase(String phrase, String keyword)
    {
        //
        ArrayList<String> list = new ArrayList<String>();
        list.add(phrase.substring(0, phrase.indexOf(keyword)).trim());
        list.add(phrase.substring(phrase.lastIndexOf(keyword) + keyword.length()).trim());
        return list;
    }

    // Return a list of potential solutions given the left and right half of the phrase
    public ArrayList<String> getSolutions(String phrase, String leftHalf, String rightHalf, int... answerLength)
    {

        // List of solutions
        ArrayList<String> solutions = new ArrayList<String>();
        String leftWord, rightWord = null;

        // Get solutions
        ArrayList<String> l = getLeftRightWords(leftHalf, rightHalf);
        leftWord = l.get(0);
        rightWord = l.get(1);
        if(verbose)
        {
            System.out.println("Left Word: " + leftWord); //delete
            System.out.println("Right Word: " + rightWord); //delete
        }

        // Get lists of synonyms for left and right words
        ArrayList<String> leftSynonyms = new ArrayList<String>();
        ArrayList<String> rightSynonyms = new ArrayList<String>();
        leftSynonyms.add(leftWord);
        rightSynonyms.add(rightWord);
        ArrayList<String> l1 = null;

        if (leftWord != null)
            leftSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(leftWord));
        if (rightWord != null)
            rightSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(rightWord));
        if(verbose)
        {
            System.out.println("Left Synonyms: " + leftSynonyms); //delete
            System.out.println("Right Synonyms: " + rightSynonyms); //delete
        }

        // Return list of mixed words, removing the answers that don't adhere to the answerLength
        ArrayList<String> containedWords = returnContainedWords(leftSynonyms, rightSynonyms);
        for (Iterator<String> iter = containedWords.listIterator(); iter.hasNext(); )
        {
            String a = iter.next();
            if (a.length() != answerLength[0])
                iter.remove();
            else if (a.contains(" "))
                iter.remove();
        }
        if(verbose)
            System.out.println("Contained Words: " + containedWords); //delete
        return containedWords;
    }

    // Return a list containing the words to the left and right of the keyword
    public ArrayList<String> getLeftRightWords(String leftHalf, String rightHalf)
    {

        //
        ArrayList<String> list = new ArrayList<String>();
        String leftWord, rightWord = null;

        leftWord = leftHalf.substring(leftHalf.lastIndexOf(" ")+1).trim();
        try
        {
            rightWord = rightHalf.substring(0, rightHalf.indexOf(" ")).trim();
        }
        catch (Exception e)
        {
            rightWord = rightHalf.substring(0).trim();
        }
        list.add(leftWord);
        list.add(rightWord);
        return list;

    }

    // Return a list of all possible combinations of words given in leftSynonyms/rightSynonyms
    public ArrayList<String> returnContainedWords(ArrayList<String> leftSynonyms, ArrayList<String> rightSynonyms)
    {
        // Create empty list to place possible words into
        ArrayList<String> possibleWords = new ArrayList<String>();

        // Loop through all words, adding every possible mix of
        for (String leftWord : leftSynonyms)
        {
            for (String rightWord : rightSynonyms)
            {
                // Add outermost combination of left and right words to list
                possibleWords.add((leftWord + rightWord).toLowerCase());
                possibleWords.add((rightWord + leftWord).toLowerCase());

                // Place leftWord in every possible position in rightWord, add to list
                for (int i = 1; i < rightWord.length(); i++)
                {
                    String possibleWord = rightWord.substring(0, i).trim() + leftWord.trim() + rightWord.substring(i);

                    if (isWord(possibleWord))
                        possibleWords.add(possibleWord.toLowerCase());
                }

                // Place rightWord in every possible position in leftWord, add to list
                for (int i = 1; i < leftWord.length(); i++)
                {
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