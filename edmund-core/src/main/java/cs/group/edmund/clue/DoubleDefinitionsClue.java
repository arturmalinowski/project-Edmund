package cs.group.edmund.clue;

import cs.group.edmund.fixtures.HttpClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoubleDefinitionsClue implements Clue {


    private String[] unformattedSynonymsResults;
    // Todo: Need a variable sized array
    private String[][] formattedWordList = new String[2][100];
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

    public String sendGet(String word) {
        String url = "http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/xml";
        return HttpClient.makeRequest(url);
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

        putRelatedWordIn2dArray(splitPhrase);

        compareTheListsOfWords();

        if (matchingWordFound) {
            System.out.println("Answer found: " + answer);
        }

        return matchingWordFound;
    }

    private void putRelatedWordIn2dArray(String[] splitPhrase) {
        for (int i = 0; i < 2; i++) {
            getUnformattedSynonymResults(splitPhrase[i], i);

            Pattern pattern = Pattern.compile("(?<=>).*?(?=</w>)");
            Matcher matcher = pattern.matcher(unformattedSynonymsResults[i]);

            boolean found = false;

            int index = 0;
            while (matcher.find()) {
                String formattedWord = formatWords(matcher.group());
                formattedWordList[i][index] = formattedWord;
                index++;
                found = true;
            }
            if (!found) {
                System.out.println("I didn't find the text");
            }
        }
    }

    // Todo: Try and simplify
    private void compareTheListsOfWords() {
        matchingWordFound = false;
        for (int i = 0; i < formattedWordList[0].length; i++) {
            String listOneWord = formattedWordList[0][i];
            listOneWord = (listOneWord == null) ? "a" : listOneWord;
            for (int j = 0; j < formattedWordList[1].length; j++) {
                String listTwoWord = formattedWordList[1][j];
                listTwoWord = (listTwoWord == null) ? "b" : listTwoWord;
                if (listOneWord.equals(listTwoWord)) {
                    matchingWordFound = true;
                    answer = listOneWord;
                }
            }
        }
    }

    private void getUnformattedSynonymResults(String word, int i) {
        unformattedSynonymsResults = new String[2];
        unformattedSynonymsResults[i] = sendGet(word);
    }

    public String formatWords(String formattedWords) {
        // Todo: find a better way
        formattedWords = formattedWords.replace("<words>", "");
        formattedWords = formattedWords.replace("<w p=\"noun\" r=\"syn\">", "");
        formattedWords = formattedWords.replace("<w p=\"noun\" r=\"ant\">", "");
        formattedWords = formattedWords.replace("<w p=\"verb\" r=\"syn\">", "");
        formattedWords = formattedWords.replace("<w p=\"verb\" r=\"ant\">", "");
        formattedWords = formattedWords.replace("<w p=\"adjective\" r=\"sim\">", "");
        formattedWords = formattedWords.replace("<w p=\"adjective\" r=\"rel\">", "");
        formattedWords = formattedWords.replace("<w p=\"adjective\" r=\"ant\">", "");
        formattedWords = formattedWords.replace("<w p=\"adjective\" r=\"syn\">", "");
        formattedWords = formattedWords.replace("<w p=\"adverb\" r=\"syn\">", "");
        formattedWords = formattedWords.replace("<w p=\"adverb\" r=\"ant\">", "");
        return formattedWords;
    }
}
