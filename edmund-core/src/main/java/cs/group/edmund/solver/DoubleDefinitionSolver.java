package cs.group.edmund.solver;

public class DoubleDefinitionSolver {

    private final ThesaurusHttpUrlConnection httpUrlConnection;

    private String[] unformattedSynonymsResults;

    public DoubleDefinitionSolver() {
        httpUrlConnection = new ThesaurusHttpUrlConnection();
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
        try {
            unformattedSynonymsResults[i] = httpUrlConnection.sendGet(word);
        } catch (Exception e) {
            System.out.println("Error occurred during GET request");
        }
        System.out.println(i + ": " + unformattedSynonymsResults[i]);
    }

}
