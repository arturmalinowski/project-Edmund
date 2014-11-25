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
        for (String keyWord : keyWords)
        {
            if (phrase.contains(keyWord.toLowerCase()))
                return true;
        }
        return false;
    }

    @Override
    public String solve(String phrase, String hint, int... answerLength)
    {
        // Set phrase to lowercase, and get container clue keyword
        phrase = phrase.toLowerCase();
        String key = getKeyword(phrase);

        // Get side words on the left/right of the phrase by using split(), then
        // Get synonyms list for first and last words in phrase by using getAllSynonyms(), then
        // Get synonyms list for first 2 and last 2 words in phrase by using getAllSynonyms(), then
        // Remove the words not of the appropriate length using an Iterator
        String[] splitPhrase = phrase.split("\\s+");
        ArrayList<String> firstWordSynonyms = ((ArrayList<String>) thesaurus.getAllSynonymsXML(splitPhrase[0]));
        ArrayList<String> lastWordSynonyms = (ArrayList<String>) thesaurus.getAllSynonymsXML(splitPhrase[splitPhrase.length - 1]);
        //firstWordSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(splitPhrase[0]));
        //lastWordSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(splitPhrase[splitPhrase.length - 1]));
        //ArrayList<String> firstTwoWordSynonyms = null;
        //ArrayList<String> lastTwoWordSynonyms = null;
        //if(splitPhrase.length >= 2)
            //firstTwoWordSynonyms = ((ArrayList<String>) thesaurus.getAllSynonymsXML(splitPhrase[0] + " " + splitPhrase[1]));
        //if(splitPhrase.length >= 4)
            //lastTwoWordSynonyms = ((ArrayList<String>) thesaurus.getAllSynonymsXML(splitPhrase[splitPhrase.length - 2] + " " + splitPhrase[splitPhrase.length - 1]));
        firstWordSynonyms.addAll(lastWordSynonyms);
        //if (firstTwoWordSynonyms != null)
            //firstWordSynonyms.addAll(firstTwoWordSynonyms);
        //if (lastTwoWordSynonyms != null)
            //firstWordSynonyms.addAll(lastTwoWordSynonyms);
        for (Iterator<String> iter = firstWordSynonyms.listIterator(); iter.hasNext(); ) {
            String a = iter.next();
            if (((answerLength.length == 1) && ((a.length() != answerLength[0]) || (a.contains(" ")))) || ((answerLength.length > 1) && (!a.contains(" "))))
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

    // Return a list containing the left and right half of the phrase, split on the keyword
    public ArrayList<String> splitPhrase(String phrase, String keyword)
    {
        //
        if (verbose)
        {
            System.out.println("splitPhrase - phrase: " + phrase); //delete
            System.out.println("splitPhrase - keyword: " + keyword); //delete
        }
        ArrayList<String> list = new ArrayList<String>();
        list.add(phrase.substring(0, phrase.indexOf(keyword)).trim());
        list.add(phrase.substring(phrase.lastIndexOf(keyword) + keyword.length()).trim());
        return list;
    }

    // Return a list of potential solutions given the left and right half of the phrase
    public ArrayList<String> getSolutions(String phrase, String leftHalf, String rightHalf, int... answerLength)
    {

        // Instatiate arraylist to contain solutions, then
        //
        ArrayList<String> solutions = new ArrayList<String>();
        String[] leftSplit = leftHalf.split("\\s+");
        String[] rightSplit = rightHalf.split("\\s+");

        //
        String leftWord = leftSplit[leftSplit.length - 1];
        String rightWord = rightSplit[0];
        if(verbose)
        {
            System.out.println("Left Word: " + leftWord); //delete
            System.out.println("Right Word: " + rightWord); //delete
        }

        // Add list of synonyms for left/right words to appropriate arrays
        ArrayList<String> leftSynonyms = new ArrayList<String>();
        ArrayList<String> rightSynonyms = new ArrayList<String>();
        leftSynonyms.add(leftWord);
        rightSynonyms.add(rightWord);

        if (leftWord != null)
        {
            leftSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(leftWord));
            //leftSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(leftWord));
        }
        if (rightWord != null)
        {
            rightSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(rightWord));
            //rightSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(rightWord));
        }
        if(verbose)
        {
            System.out.println("Left Word is: " + leftWord + ", and Left Synonyms are: " + leftSynonyms); //delete
            System.out.println("Right Word is: " + rightWord + ", and Right Synonyms are: " + rightSynonyms); //delete
        }

        // Add list of synonyms for 2 left/right words to appropriate arrays
        if(leftSplit.length >= 2)
        {
            leftSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
            //leftSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1]));
            if (verbose)
                System.out.println("Left Words are: " + leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1] + ", and Left Synonyms are: " + (ArrayList<String>) thesaurus.getAllSynonymsXML(leftSplit[leftSplit.length - 2] + " " + leftSplit[leftSplit.length - 1])); //delete
        }
        if(rightSplit.length >= 2)
        {
            rightSynonyms.addAll((ArrayList<String>) thesaurus.getAllSynonymsXML(rightSplit[0] + " " + rightSplit[1]));
            //rightSynonyms.addAll((ArrayList<String>) thesaurus.getRelatedWordsXML(rightSplit[0] + " " + rightSplit[1]));
            if(verbose)
                System.out.println("Right Words are: " + rightSplit[0] + " " + rightSplit[1] + ", and Right Synonyms are: " + (ArrayList<String>) thesaurus.getAllSynonymsXML(rightSplit[0] + " " + rightSplit[1])); //delete
        }

        // Calculate list of mixed words, removing the answers that don't adhere to the answerLength, then
        // Add that list to solutions, then
        // return solutions
        ArrayList<String> containedWords = returnContainedWords(leftSynonyms, rightSynonyms);
        if (answerLength.length == 1) {
            for (Iterator<String> iter = containedWords.listIterator(); iter.hasNext(); ) {
                String a = iter.next();
                if (a.length() != answerLength[0])
                    iter.remove();
            }
        }
        if(verbose)
            System.out.println("Contained Words: " + containedWords); //delete
        return containedWords;
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