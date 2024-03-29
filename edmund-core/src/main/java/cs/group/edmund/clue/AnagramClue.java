package cs.group.edmund.clue;

import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Helper;
import cs.group.edmund.utils.Thesaurus;

import java.util.*;

import static java.util.Arrays.asList;

public class AnagramClue implements Clue {

    private final List<String> keyWords;
    private Thesaurus thesaurus;
    private Dictionary dictionary;

    public AnagramClue(Thesaurus thesaurus, Dictionary dictionary) {
        this.thesaurus = thesaurus;
        this.dictionary = dictionary;
        keyWords = asList("ABNORMAL", "ABOUT", "ABSURD", "ADAPT", "ADAPTED", "ADDLED", "ADJUST", "ADJUSTED", "ADJUSTMENT", "ADRIFT", "AFRESH", "AFTER A FASHION", "AGITATED", "ALL OVER", "ALTER", "ALTERATION", "ALTERED", "ALTERING", "AMISS", "ANEW", "ANOTHER WAY", "ANYHOW", "AROUND", "ARRANGE", "ARRANGED", "ARRANGEMENT", "ARRANGING", "ASKEW", "ASSEMBLE", "ASSEMBLED", "ASSEMBLING", "ASSEMBLY", "ASSORTED", "ASSORTMENT", "ASTRAY", "AT ODDS", "AT SEA", "AT SIXES AND SEVENS", "AWFUL", "AWFULLY", "AWKWARD", "AWKWARDLY", "AWRY", "BAD", "BADLY", "BANANAS", "BASTARD", "BATS", "BATTERED", "BATTY", "BEATEN", "BEATEN UP", "BECOME", "BECOMES", "BECOMING", "BEND", "BENT", "BIZARRE", "BLEND", "BLENDED", "BOIL", "BOILED", "BOILING", "BREAK", "BREAKING", "BREW", "BREWED", "BREWING", "BROADCAST", "BROKEN", "BUILD", "BUILDING", "BUILT", "BY ARRANGEMENT", "BY MISTAKE", "CAN BE", "CARELESS", "CARELESSLY", "CAST", "CAVORT", "CAVORTING", "CHANGE", "CHANGED", "CHANGES", "CHANGING", "CHAOS", "CHAOTIC", "CHARACTERS", "CHOP", "CHOPPED", "CIRCULATING", "COCKTAIL", "COMPLICATE", "COMPLICATED", "COMPONENTS", "COMPOSE", "COMPOSED", "COMPOSING", "COMPOUND", "COMPRISE", "CONCEALED", "CONCEALING", "CONCEALS", "CONFUSE", "CONFUSED", "CONFUSING", "CONSTITUENTS", "CONSTRUCT", "CONVERSION", "CONVERT", "CONVERTED", "CONVERTIBLE", "COOK", "COOKED", "CORRECT", "CORRECTED", "CORRUPT", "CORRUPTED", "COULD BE", "CRACK", "CRACKED", "CRACKERS", "CRACKING", "CRAZED", "CRAZY", "CRIMINAL", "CUCKOO", "CUNNINGLY", "CURIOUS", "CURIOUSLY", "DAMAGE", "DAMAGED", "DANCE", "DANCING", "DEALT", "DEFORM", "DEFORMED", "DEMOLISHED", "DEPLOY", "DEPLOYED", "DERIVED FROM", "DESIGN", "DESIGNED", "DEVELOP", "DEVELOPED", "DEVIATION", "DEVIOUS", "DICKY", "DIFFERENT", "DIFFERENTLY", "DISARRAY", "DISFIGURE", "DISFIGURED", "DISGUISE", "DISGUISED", "DISHEVELLED", "DISLOCATED", "DISMANTLED", "DISORDER", "DISORDERED", "DISORDERLY", "DISPERSED", "DISPOSED", "DISRUPTION", "DISSEMINATED", "DISSOLVE", "DISSOLVING", "DISTRESSED", "DISTURBED", "DISTURBING", "DIVERSIFIED", "DIZZY", "DOCTOR", "DOCTORED", "DODGY", "DOTTY", "DREADFUL", "DREADFULLY", "DRESSED", "DRUNK", "DUD", "DUFF", "ECCENTRIC", "EDIT", "EDITED", "ENGENDERING", "ENGINEERED", "ENSEMBLE", "ENTANGLED", "ERRATIC", "ERUPTING", "ESSENTIALS", "EXCITED", "EXOTIC", "EXPLODE", "EXPLODED", "EXPLODING", "EXTRAORDINARY", "FABRICATED", "FALSE", "FANCIFUL", "FANCY", "FANTASTIC", "FASHION", "FASHIONED", "FASHIONING", "FAULTY", "FERMENTED", "FIDDLE", "FIDDLED", "FIND", "FLUID", "FLURRIED", "FLUSTERED", "FOOLISH", "FOOLISHLY", "FOR A CHANGE", "FORCED", "FORGE", "FORGED", "FORM", "FRACTURED", "FRAGMENTED", "FRAGMENTS", "FREE", "FREELY", "FRESH", "FRESHLY", "FROLICKING", "FUDGE", "FUNNY", "GARBLED", "GENERATING", "GETS", "GROTESQUE", "GROUND", "HAPHAZARD", "HASH", "HAVOC", "HAYWIRE", "HIDE", "HIDING", "HIGGLEDY-PIGGLEDY", "ILL", "ILL-DISPOSED", "ILL-FORMED", "ILL-TREATED", "IMPROPER", "IMPROPERLY", "IN A FERMENT", "IN A JUMBLE", "IN A MESS", "IN A WHIRL", "INCORRECT", "INCORRECTLY", "IN DISARRAY", "IN DISGUISE", "IN ERROR", "INGREDIENTS", "IN RUINS", "INTERFERED WITH", "INSANE", "INVOLVE", "INVOLVED", "IRREGULAR", "JITTERY", "JUGGLED", "JUMBLED", "JUMPING", "KIND OF", "KINKY", "KNOTTED", "LAWLESS", "LETTERS", "LIQUID", "LOOSE", "LOOSELY", "LUNATIC", "MAD", "MADE", "MADE UP", "MADLY", "MAKE-UP", "MALFORMED", "MALFUNCTION", "MALTREATED", "MANAGED", "MANGLED", "MANOEUVRE", "MASH", "MASSAGED", "MAYBE", "MAY BE", "MEANDERING", "MEDLEY", "MESS", "MESSY", "MINCED", "MISHANDLE", "MISPLACED", "MISSHAPEN", "MISTAKE", "MISTAKEN", "MISUSE", "MIX", "MIXED", "MIXTURE", "MOBILE", "MODELLED", "MODIFIED", "MOVING", "MUDDLE", "MUDDLED", "MUTILATED", "MUTINOUS", "NAUGHTY", "NEW", "NEWLY", "NOT IN ORDER", "NOT PROPERLY", "NOT RIGHT", "NOT STRAIGHT", "NOVEL", "NUTS", "NUTTY", "OBSCURE", "ODD", "OFF", "ORDER", "ORDERED", "ORDERLY", "ORGANISE", "ORGANISED", "ORIGINAL", "ORIGINALLY", "OTHERWISE", "OUT", "OUT OF JOINT", "OUT OF SORTS", "PANTS", "PECULIAR", "PERHAPS", "PERVERSE", "PERVERSELY", "PERVERT", "PERVERTED>", "PHONEY", "PIE", "PITCHING", "PLASTIC", "PLAYING", "POOR", "POORLY", "POSSIBLE", "POSSIBLY", "POTENTIAL", "POTENTIALLY", "PREPARE", "PREPARED", "PROBLEM", "PROCESSING", "PRODUCE", "PRODUCTION", "PSEUDO", "PUT OUT", "PUT RIGHT", "PUT STRAIGHT", "QUEER", "QUEERLY", "QUESTIONABLE", "QUIRKY", "RAGGED", "RAMBLING", "RANDOM", "RANDOMLY", "RECOLLECTED", "RECTIFIED", "REELING", "REELS", "REFORM", "REFORMED", "REGULATED", "REPAIR", "REPAIRED", "REPLACED", "RESORT", "REVIEW", "REVIEWED", "REVOLTING", "REVOLUTIONARY", "RIGGED", "RIOT", "RIOTING", "RIOTOUS", "ROCK", "ROCKING", "ROCKY", "ROTTEN", "ROUGH", "ROUGHLY", "ROUND", "RUIN", "RUINED", "RUINOUS", "RUM", "RUN", "RUNNING", "RUNS", "RUPTURED", "SAD", "SADLY", "SCATTER", "SCATTERED", "SET OFF", "SET OUT", "SHAKE", "SHAKEN", "SHAKING", "SHAKY", "SHAMBLES", "SHIFT", "SHIFTED", "SHIFTING", "SHOT", "SILLY", "SLOPPY", "SMASH", "SMASHED", "SMASHING", "SNARLED", "SOMEHOW", "SPILL", "SPILLED", "SPIN", "SPINNING", "SPOIL", "SPOILT", "SPREAD", "SPUN", "STAGGERED", "STAGGERING", "STEW", "STRANGE", "STRANGELY", "STRAY", "SUBSTITUTE", "SUBSTITUTED", "SURPRISING", "SURPRISINGLY", "SUSPECT", "SWIMMING", "SWIRL", "SWIRLING", "SWITCH", "SWITCHED", "TANGLED", "TATTERED", "TIDIED", "TIPSY", "TORTUOUS", "TRAIN", "TRAINED", "TRAINING", "TRANSFORM", "TRANSFORMED", "TRANSLATE", "TRANSLATED", "TREAT", "TREATED", "TRICKY", "TRIM", "TROUBLE", "TROUBLED", "TUMBLE", "TUMBLING", "TURN", "TURNED", "TURNING", "TURNS", "TWIST", "TWISTED", "UNDONE", "UNNATURAL", "UNRESTRAINED", "UNRULY", "UNSETTLED", "UNSTEADY", "UNTIDY", "UNUSUAL", "UNUSUALLY", "UPHEAVAL", "UPSET", "USED", "VAGUE", "VAGUELY", "VARIATION", "VARIED", "VARIETY", "VERSION", "VOLATILE", "WANDER", "WANDERING", "WARPED", "WAYWARD", "WEAVE", "WEAVING", "WEIRD", "WILD", "WILDER", "WILDLY", "WORKED", "WORKING", "WORRIED", "WOVEN", "WRECKED", "WRITHING", "WRONG", "WRONGLY");
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
    public Optional<List<String>> solve(String phrase, String hint, int... answerLength) {
        String keyWord = null, possibleAnswer;
        String[] words = phrase.replaceAll("['’/]", " ").replaceAll("[-+^:,.?!'’]", "").toUpperCase().split(" ");
        ArrayList<String> clueWords = new ArrayList<>(Arrays.asList(words)), possibleAnswers = new ArrayList<>(), finalAnswers = new ArrayList<>();
        clueWords = Helper.removeDuplicates(clueWords);

        for (int i : answerLength) {
            ArrayList<String> tempList = new ArrayList<>();
            tempList.addAll(clueWords);

            for (String word : clueWords) {
                if (keyWords.contains(word)) {
                    if (isValidKeyword(tempList, word, i)) {
                        keyWord = word;
                        break;
                    }
                }
            }

            clueWords.remove(keyWord);
            ArrayList<String> matchingWords = Helper.combineWords(clueWords, i);

            for (String word : matchingWords) {
                List<String> answers = findAnagram(word);

                if ((answers != null) && (answers.size() < 2)) {
                    possibleAnswers.addAll(answers);
                }
                if (answers != null) {
                    possibleAnswer = synonymsCheck(answers, clueWords);

                    if (!possibleAnswer.equals("")) {
                        possibleAnswers.add(possibleAnswer);
                        break;
                    }
                    possibleAnswers.addAll(answers);
                }
            }
        }

        Helper.removeDuplicates(possibleAnswers);

        if (!hint.equals("")) {
            for (String word : possibleAnswers) {
                if (word.matches(hint)) {
                    finalAnswers.add(word);
                }
            }
        }

        if (hint.equals("") && finalAnswers.size() < 1 && possibleAnswers.size() > 0) return Optional.of(possibleAnswers);
        if (finalAnswers.size() > 0) return Optional.of(finalAnswers);

        return Optional.empty();
    }

    public List<String> findAnagram(String word) {
        List<String> anagrams = new ArrayList<>();

        for (String dictWord : dictionary.getWordsSize(word.length())) {
            if (dictWord.length() == word.length() && !dictWord.toLowerCase().equals(word.toLowerCase())) {
                if (isAnagram(dictWord.toLowerCase(), word.toLowerCase())) {
                    anagrams.add(dictWord);
                }
            }
        }

        if ((anagrams.size() == 0) || (anagrams.size() < 2) && (anagrams.get(0).equals(word.toLowerCase()))) {
            return null;
        } else {
            return anagrams;
        }
    }

    private boolean isAnagram(String firstWord, String secondWord) {
        Map<Character, Integer> occurrencesMap = new HashMap<>();

        for(int i = 0; i < firstWord.length(); i++){
            char charFromLeft = firstWord.charAt(i);
            int nrOfCharsInLeft = occurrencesMap.containsKey(charFromLeft) ? occurrencesMap.get(charFromLeft) : 0;
            occurrencesMap.put(charFromLeft, ++nrOfCharsInLeft);
            char charFromRight = secondWord.charAt(i);
            int nrOfCharsInRight = occurrencesMap.containsKey(charFromRight) ? occurrencesMap.get(charFromRight) : 0;
            occurrencesMap.put(charFromRight, --nrOfCharsInRight);
        }

        for(int occurrencesNr : occurrencesMap.values()){
            if(occurrencesNr != 0){
                return false;
            }
        }

        return true;
    }

    public Boolean isValidKeyword(ArrayList<String> list, String keyWord, int answerLength) {
        Boolean check = false;
        list.remove(keyWord);
        ArrayList<String> newList = new ArrayList<>(list);

        loop:
        for (String word : newList) {
            if (word.length() > answerLength) {
                list.remove(word);
                continue;
            }
            for (String secondWord : newList) {
                if (secondWord.length() + word.length() == answerLength) {
                    check = true;
                    break loop;
                }
                for (String thirdWord : newList) {
                    if (thirdWord.length() + secondWord.length() + word.length() == answerLength) {
                        check = true;
                        break loop;
                    }
                }
            }
            if (word.length() == answerLength) {
                check = true;
                break loop;
            }
        }
        list.add(keyWord);
        return check;
    }

    private String synonymsCheck(List<String> answers, ArrayList<String> possibleSynonyms) {
        String newAnswer = "";

        if (answers == null) {
            return newAnswer;
        }

        twoLoops:
        for (String answer : answers) {
            List<String> list = thesaurus.getAllSynonymsXML(answer);
            for (String checkedWord : possibleSynonyms) {
                if (list.contains(checkedWord.toLowerCase())) {
                    newAnswer = answer.toLowerCase();
                    break twoLoops;
                }
            }
        }

        if (!newAnswer.equals("")) {
            return newAnswer;
        }

        twoLoops:
        for (String answer : answers) {
            List<String> list = thesaurus.getRelatedWordsXML(answer);
            for (String checkedWord : possibleSynonyms) {
                if (list.contains(checkedWord.toLowerCase())) {
                    newAnswer = answer.toLowerCase();
                    break twoLoops;
                }
            }
        }

        if (!newAnswer.equals("")) {
            return newAnswer;
        }

        twoLoops:
        for (String answer : answers) {
            for (String checkedWord : possibleSynonyms) {
                List<String> list = thesaurus.getRelatedWordsXML(checkedWord.toLowerCase());
                if (list.contains(answer)) {
                    newAnswer = answer.toLowerCase();
                    break twoLoops;
                }
            }
        }

        return newAnswer;
    }
}
