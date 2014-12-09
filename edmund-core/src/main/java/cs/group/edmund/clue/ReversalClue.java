package cs.group.edmund.clue;

import cs.group.edmund.utils.Helper;
import cs.group.edmund.utils.Thesaurus;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ReversalClue implements Clue {


    private final List<String> keyWords;
    private List<String> answersList = new ArrayList<>();
    private boolean answerFound = false;
    private String candidateWord;
    private ArrayList<String> otherSynonymAndRelatedWords = new ArrayList<>();

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

        quickSearch(clueWords, answerLength[0]);

        // get a candidate word
        for (int i = 0; i < clueWords.length; i++) {
            for (String keyword : keyWords) {
                if (clueWords[i].equals(keyword.toLowerCase())) {
                    candidateWord = clueWords[i - 1];
                    clueWords[i] = "";
                    removeCandidateFromList(clueWords);
                }
            }
        }

        // search synonyms and related words for candidate
        Thesaurus thesaurus = new Thesaurus();
        List<String> candidateSynonymAndRelatedWords = thesaurus.getAllSynonymsXML(candidateWord);
        candidateSynonymAndRelatedWords.addAll(thesaurus.getRelatedWordsXML(candidateWord));

        for (String clueWord : clueWords) {
            otherSynonymAndRelatedWords.addAll(thesaurus.getAllSynonymsXML(clueWord));
            otherSynonymAndRelatedWords.addAll(thesaurus.getRelatedWordsXML(clueWord));
        }


        for (int i = 0; i < candidateSynonymAndRelatedWords.size(); i++) {
            if (candidateSynonymAndRelatedWords.get(i).length() == answerLength[0]) {
                for (String word : otherSynonymAndRelatedWords) {
                    String reversedWord = new StringBuilder(candidateSynonymAndRelatedWords.get(i)).reverse().toString();
                    if (word.equals(reversedWord)) {
                        answersList.add(reversedWord);
                        answerFound = true;
                    }
                }


            }
        }

        ArrayList<String> answer = Helper.removeDuplicates(new ArrayList<>((answersList)));

        if (answerFound) {
            return answer.get(0);
        } else {
            return "Answer not found";
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

    private void quickSearch(String[] words, int i) {
        for (String word : words) {
            if (word.length() == i) {
                answersList.add(new StringBuilder(word).reverse().toString());
                answerFound = true;
            }

        }
    }


}
