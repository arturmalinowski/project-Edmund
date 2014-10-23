package cs.group.edmund.clue;

import cs.group.edmund.fixtures.HttpClient;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.util.Arrays.asList;

public class AnagramClue implements Clue {

    private final List<String> keyWords;

    public AnagramClue() {
        keyWords = asList("ABNORMAL", "ABOUT", "ABSURD", "ADAPT", "ADAPTED", "ADDLED", "ADJUST", "ADJUSTED", "ADJUSTMENT", "ADRIFT", "AFRESH", "AFTER A FASHION", "AGITATED", "ALL OVER", "ALTER", "ALTERATION", "ALTERED", "ALTERING", "AMISS", "ANEW", "ANOTHER WAY", "ANYHOW", "AROUND", "ARRANGE", "ARRANGED", "ARRANGEMENT", "ARRANGING", "ASKEW", "ASSEMBLE", "ASSEMBLED", "ASSEMBLING", "ASSEMBLY", "ASSORTED", "ASSORTMENT", "ASTRAY", "AT ODDS", "AT SEA", "AT SIXES AND SEVENS", "AWFUL", "AWFULLY", "AWKWARD", "AWKWARDLY", "AWRY", "BAD", "BADLY", "BANANAS", "BASTARD", "BATS", "BATTERED", "BATTY", "BEATEN", "BEATEN UP", "BECOME", "BECOMES", "BECOMING", "BEND", "BENT", "BIZARRE", "BLEND", "BLENDED", "BOIL", "BOILED", "BOILING", "BREAK", "BREAKING", "BREW", "BREWED", "BREWING", "BROADCAST", "BROKEN", "BUILD", "BUILDING", "BUILT", "BY ARRANGEMENT", "BY MISTAKE", "CAN BE", "CARELESS", "CARELESSLY", "CAST", "CAVORT", "CAVORTING", "CHANGE", "CHANGED", "CHANGES", "CHANGING", "CHAOS", "CHAOTIC", "CHARACTERS", "CHOP", "CHOPPED", "CIRCULATING", "COCKTAIL", "COMPLICATE", "COMPLICATED", "COMPONENTS", "COMPOSE", "COMPOSED", "COMPOSING", "COMPOUND", "COMPRISE", "CONCEALED", "CONCEALING", "CONCEALS", "CONFUSE", "CONFUSED", "CONFUSING", "CONSTITUENTS", "CONSTRUCT", "CONVERSION", "CONVERT", "CONVERTED", "CONVERTIBLE", "COOK", "COOKED", "CORRECT", "CORRECTED", "CORRUPT", "CORRUPTED", "COULD BE", "CRACK", "CRACKED", "CRACKERS", "CRACKING", "CRAZED", "CRAZY", "CRIMINAL", "CUCKOO", "CUNNINGLY", "CURIOUS", "CURIOUSLY", "DAMAGE", "DAMAGED", "DANCE", "DANCING", "DEALT", "DEFORM", "DEFORMED", "DEMOLISHED", "DEPLOY", "DEPLOYED", "DERIVED FROM", "DESIGN", "DESIGNED", "DEVELOP", "DEVELOPED", "DEVIATION", "DEVIOUS", "DICKY", "DIFFERENT", "DIFFERENTLY", "DISARRAY", "DISFIGURE", "DISFIGURED", "DISGUISE", "DISGUISED", "DISHEVELLED", "DISLOCATED", "DISMANTLED", "DISORDER", "DISORDERED", "DISORDERLY", "DISPERSED", "DISPOSED", "DISRUPTION", "DISSEMINATED", "DISTRESSED", "DISTURBED", "DISTURBING", "DIVERSIFIED", "DIZZY", "DOCTOR", "DOCTORED", "DODGY", "DOTTY", "DREADFUL", "DREADFULLY", "DRESSED", "DRUNK", "DUD", "DUFF", "ECCENTRIC", "EDIT", "EDITED", "ENGENDERING", "ENGINEERED", "ENSEMBLE", "ENTANGLED", "ERRATIC", "ERUPTING", "ESSENTIALS", "EXCITED", "EXOTIC", "EXPLODE", "EXPLODED", "EXPLODING", "EXTRAORDINARY", "FABRICATED", "FALSE", "FANCIFUL", "FANCY", "FANTASTIC", "FASHION", "FASHIONED", "FASHIONING", "FAULTY", "FERMENTED", "FIDDLE", "FIDDLED", "FIND", "FLUID", "FLURRIED", "FOOLISH", "FOOLISHLY", "FOR A CHANGE", "FORCED", "FORGE", "FORGED", "FORM", "FRACTURED", "FRAGMENTED", "FRAGMENTS", "FREE", "FREELY", "FRESH", "FRESHLY", "FROLICKING", "FUDGE", "FUNNY", "GARBLED", "GENERATING", "GETS", "GROTESQUE", "GROUND", "HAPHAZARD", "HASH", "HAVOC", "HAYWIRE", "HIDE", "HIDING", "HIGGLEDY-PIGGLEDY", "ILL", "ILL-DISPOSED", "ILL-FORMED", "ILL-TREATED", "IMPROPER", "IMPROPERLY", "IN A FERMENT", "IN A JUMBLE", "IN A MESS", "IN A WHIRL", "INCORRECT", "INCORRECTLY", "IN DISARRAY", "IN DISGUISE", "IN ERROR", "INGREDIENTS", "IN RUINS", "INTERFERED WITH", "INVOLVE", "INVOLVED", "IRREGULAR", "JITTERY", "JUGGLED", "JUMBLED", "JUMPING", "KIND OF", "KINKY", "KNOTTED", "LAWLESS", "LETTERS", "LIQUID", "LOOSE", "LOOSELY", "LUNATIC", "MAD", "MADE", "MADE UP", "MADLY", "MAKE-UP", "MALFORMED", "MALFUNCTION", "MALTREATED", "MANAGED", "MANGLED", "MANOEUVRE", "MASH", "MASSAGED", "MAYBE", "MAY BE", "MEANDERING", "MEDLEY", "MESS", "MESSY", "MINCED", "MISHANDLE", "MISPLACED", "MISSHAPEN", "MISTAKE", "MISTAKEN", "MISUSE", "MIX", "MIXED", "MIXTURE", "MOBILE", "MODELLED", "MODIFIED", "MOVING", "MUDDLE", "MUDDLED", "MUTILATED", "MUTINOUS", "NAUGHTY", "NEW", "NEWLY", "NOT IN ORDER", "NOT PROPERLY", "NOT RIGHT", "NOT STRAIGHT", "NOVEL", "NUTS", "NUTTY", "OBSCURE", "ODD", "OFF", "ORDER", "ORDERED", "ORDERLY", "ORGANISE", "ORGANISED", "ORIGINAL", "ORIGINALLY", "OTHERWISE", "OUT", "OUT OF JOINT", "OUT OF SORTS", "PANTS", "PECULIAR", "PERHAPS", "PERVERSE", "PERVERSELY", "PERVERT", "PERVERTED>", "PHONEY", "PIE", "PITCHING", "PLASTIC", "PLAYING", "POOR", "POORLY", "POSSIBLE", "POSSIBLY", "POTENTIAL", "POTENTIALLY", "PREPARE", "PREPARED", "PROBLEM", "PROCESSING", "PRODUCE", "PRODUCTION", "PSEUDO", "PUT OUT", "PUT RIGHT", "PUT STRAIGHT", "QUEER", "QUEERLY", "QUESTIONABLE", "QUIRKY", "RAGGED", "RAMBLING", "RANDOM", "RANDOMLY", "RECOLLECTED", "RECTIFIED", "REELING", "REELS", "REFORM", "REFORMED", "REGULATED", "REPAIR", "REPAIRED", "REPLACED", "RESORT", "REVIEW", "REVIEWED", "REVOLTING", "REVOLUTIONARY", "RIGGED", "RIOT", "RIOTING", "RIOTOUS", "ROCK", "ROCKING", "ROCKY", "ROTTEN", "ROUGH", "ROUGHLY", "ROUND", "RUIN", "RUINED", "RUINOUS", "RUM", "RUN", "RUNNING", "RUNS", "RUPTURED", "SAD", "SADLY", "SCATTER", "SCATTERED", "SET OFF", "SET OUT", "SHAKE", "SHAKEN", "SHAKING", "SHAKY", "SHAMBLES", "SHIFT", "SHIFTED", "SHIFTING", "SHOT", "SILLY", "SLOPPY", "SMASH", "SMASHED", "SMASHING", "SNARLED", "SOMEHOW", "SPILL", "SPILLED", "SPIN", "SPINNING", "SPOIL", "SPOILT", "SPREAD", "SPUN", "STAGGERED", "STAGGERING", "STEW", "STRANGE", "STRANGELY", "STRAY", "SUBSTITUTE", "SUBSTITUTED", "SURPRISING", "SURPRISINGLY", "SUSPECT", "SWIMMING", "SWIRL", "SWIRLING", "SWITCH", "SWITCHED", "TANGLED", "TATTERED", "TIDIED", "TIPSY", "TORTUOUS", "TRAIN", "TRAINED", "TRAINING", "TRANSFORM", "TRANSFORMED", "TRANSLATE", "TRANSLATED", "TREAT", "TREATED", "TRICKY", "TRIM", "TROUBLE", "TROUBLED", "TUMBLING", "TURN", "TURNED", "TURNING", "TURNS", "TWIST", "TWISTED", "UNDONE", "UNNATURAL", "UNRESTRAINED", "UNRULY", "UNSETTLED", "UNSTEADY", "UNTIDY", "UNUSUAL", "UNUSUALLY", "UPHEAVAL", "UPSET", "USED", "VAGUE", "VAGUELY", "VARIATION", "VARIED", "VARIETY", "VERSION", "VOLATILE", "WANDER", "WANDERING", "WARPED", "WAYWARD", "WEAVE", "WEAVING", "WEIRD", "WILD", "WILDER", "WILDLY", "WORKED", "WORKING", "WORRIED", "WOVEN", "WRECKED", "WRITHING", "WRONG", "WRONGLY");
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
    public String solve(String phrase, int ... answerLength) {
        return null;
    }

    public List findAnagram(String word) {
        List anagrams = new ArrayList<String>();

        String httpResponse = HttpClient.makeRequest("http://www.solverscrabble.com/words-with-the-letters-" + word);

        if (httpResponse.contains("Anagrams of " + word)) {

            int resultStartPosition = httpResponse.indexOf("<p class=\"letterInfo\">Anagrams");
            int resultEndingPosition = httpResponse.indexOf("<p class=\"letterInfo\">Words with");
            String result = httpResponse.substring(resultStartPosition, resultEndingPosition);

            Document document = Jsoup.parse(result);
            Elements elements = document.getElementsByClass("jumble");

            for (Element element : elements) {
                anagrams.add(element.text());
            }
        }
        else {
            return null;
        }
        return anagrams;
    }

}
