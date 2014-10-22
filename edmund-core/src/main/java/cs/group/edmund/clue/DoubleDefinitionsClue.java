package cs.group.edmund.clue;

import cs.group.edmund.fixtures.HttpClient;

public class DoubleDefinitionsClue implements Clue {


    private String[] unformattedSynonymsResults;

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        getSynonyms(phrase);
        return false;
    }

    @Override
    public String solve(String phrase) {
        return null;
    }


    public String[] getSynonyms(String phrase) {
        String[] splitPhrase = phrase.split(" ");

        for (int i = 0; i < 2; i++) {
            getUnformattedSynonymResults(splitPhrase[i], i);
        }

        // strip the synonyms from the strings

        // put the synonyms for each phrase in two separate arrays


        return splitPhrase;
    }

    private void getUnformattedSynonymResults(String word, int i) {
        unformattedSynonymsResults = new String[2];
        unformattedSynonymsResults[i] = sendGet(word);
        System.out.println(i + ": " + unformattedSynonymsResults[i]);
    }

    public String sendGet(String word) {
        String url = "http://words.bighugelabs.com/api/2/ecdcfa6e1dd349d1d1f4c0755f8b4d1d/" + word + "/";
        return HttpClient.makeRequest(url);
    }


}
