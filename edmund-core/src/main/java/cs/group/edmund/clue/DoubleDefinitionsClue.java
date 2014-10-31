package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;

public class DoubleDefinitionsClue implements Clue {

    private ArrayList<String> firstElementList = new ArrayList<>();
    private ArrayList<String> secondElementList = new ArrayList<>();
    private boolean matchingWordFound;
    private String answer;

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        return findMatchingWords(phrase);
    }

    // enable answer length
    @Override
    public String solve(String phrase, int... answerLength) {
        return getAnswer(phrase);
    }

    public String getAnswer(String phrase) {
        findMatchingWords(phrase);
        if (answer == null) {
            answer = "Answer not found :(";
        }
        return answer;
    }

    public Boolean findMatchingWords(String phrase) {
        String[] splitPhrase = phrase.split(" ");
        if (splitPhrase.length > 2) {
            splitPhrase = new String[]{splitPhrase[0], splitPhrase[splitPhrase.length - 1]};
        }

        putRelatedWordsInTwoLists(splitPhrase);

        compareTheListsOfWords();

        if (matchingWordFound) {
            System.out.println("Answer found: " + answer);
        }

        return matchingWordFound;
    }

    private void putRelatedWordsInTwoLists(String[] splitPhrase) {
        Thesaurus thesaurus = new Thesaurus();
        for (int i = 0; i < 2; i++) {
            try {
                Document document = thesaurus.getSynonymsAsDocument(splitPhrase[i]);
                getSynonyms(document, i);
            } catch (Exception e) {
                System.out.println("Error parsing xml");
            }
        }
    }

    private void getSynonyms(Document document, int list) {

        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator("w"); i.hasNext(); ) {
            Element word = (Element) i.next();
            if (list == 0) {
                firstElementList.add(word.getText());
            } else {
                secondElementList.add(word.getText());
            }
        }
    }

    private void compareTheListsOfWords() {
        matchingWordFound = false;
        for (String element : firstElementList) {
            if (secondElementList.contains(element)) {
                matchingWordFound = true;
                answer = element;
            }
        }
    }
}