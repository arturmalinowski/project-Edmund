package cs.group.edmund.clue;

import cs.group.edmund.utils.Helper;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ReversalClue implements Clue {


    private final List<String> keyWords;

    private boolean answerFound = false;
    private int firstCandidatePosition;
    private String candidateWord;
    private ArrayList<String> otherSynonymAndRelatedWords = new ArrayList<>();
    private List<String> answersList = new ArrayList<>();
    private String hint;

    public ReversalClue() {
        keyWords = asList("ABOUT", "AROUND", "ASCENDING", "BACK", "BACKED", "BACKING", "BACK-TO-FRONT", "BACKWARD", "BROUGHTABOUT", "BROUGHTUP", "CASTUP", "CLIMBING", "COMINGBACK", "COMINGUP", "COUNTER", "FLIPPED", "FLIPPING", "FROMTHEBOTTOM", "FROMTHEEAST", "FROMTHERIGHT", "FROMTHESOUTH", "GOINGBACK", "GOINGNORTH", "GOINGROUND", "GOINGUP", "GOINGWEST", "LIFTED", "LOOKINGBACK", "LOOKINGUP", "NORTHBOUND", "OVER", "OVERTURNED", "RAISED", "RAISING", "RETREAT", "RETREATING", "RETROGRADE", "RETROSPECTIVE", "REVERSED", "REVERSING", "REVOLUTIONARY", "RISING", "ROUND", "SENTBACK", "SENTUP", "SHOWNUP", "TAKENUP", "TURN", "TURNED", "TURNING", "TURNS", "UP", "UPENDED", "UPSET", "UPWARDLYMOBILE", "WESTBOUND", "WRITTENUP");
    }


    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        for (String keyWord : keyWords) {
            if (phrase.toUpperCase().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String solve(String phrase, String hint, int... answerLength) {
        String[] clueWords = phrase.split("\\s+");
        for (int i = 0; i < clueWords.length; i++) {
            clueWords[i] = clueWords[i].replaceAll("[,?!]", "");
        }

        this.hint = hint;
        if (this.hint == null || this.hint.equals("")) {
            this.hint = ".*";
        }

        for (int i = 0; i < clueWords.length; i++) {
            for (String keyword : keyWords) {
                if (clueWords[i].equals(keyword.toLowerCase())) {
                    candidateWord = clueWords[i - 1];
                    clueWords[i] = "";
                    removeCandidateFromList(clueWords);
                    firstCandidatePosition = i;
                }
            }
        }
        searchMatchesForCandidate(clueWords, answerLength[0], candidateWord);

        searchOtherSideKeyWord(clueWords, answerLength);

        ArrayList<String> answer = Helper.removeDuplicates(new ArrayList<>((answersList)));

        if (answerFound) {
            return answer.get(0);
        } else {
            return "Answer not found";
        }
    }

    private void searchOtherSideKeyWord(String[] clueWords, int[] answerLength) {
        if (!answerFound) {
            candidateWord = clueWords[firstCandidatePosition + 1];
            clueWords[firstCandidatePosition + 1] = "";
            searchMatchesForCandidate(clueWords, answerLength[0], candidateWord);
        }
    }

    private void searchMatchesForCandidate(String[] clueWords, int i, String possibleWord) {
        Thesaurus thesaurus = new Thesaurus();
        List<String> candidateSynonymAndRelatedWords = thesaurus.getAllSynonymsXML(possibleWord);
        candidateSynonymAndRelatedWords.addAll(thesaurus.getRelatedWordsXML(possibleWord));

        for (String clueWord : clueWords) {
            if (clueWord != null && !clueWord.equals("")) {
                otherSynonymAndRelatedWords.addAll(thesaurus.getAllSynonymsXML(clueWord));
                otherSynonymAndRelatedWords.addAll(thesaurus.getRelatedWordsXML(clueWord));
            }
        }


        for (String candidateSynonymAndRelatedWord : candidateSynonymAndRelatedWords) {
            if (candidateSynonymAndRelatedWord.length() == i) {
                for (String word : otherSynonymAndRelatedWords) {
                    String reversedWord = new StringBuilder(candidateSynonymAndRelatedWord).reverse().toString();
                    if (word.equals(reversedWord) && reversedWord.matches(hint)) {
                        answersList.add(reversedWord);
                        answerFound = true;
                    }
                }


            }
        }
    }


    private void removeCandidateFromList(String[] clueWords) {
        for (int i = 0; i < clueWords.length; i++) {
            if (clueWords[i].equals(candidateWord)) {
                clueWords[i] = null;
                break;
            }
        }
    }


}
