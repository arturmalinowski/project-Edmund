package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import cs.group.edmund.utils.Helper;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class AnagramClueTest {

    private AnagramClue clue;
    private Thesaurus thesaurus;


    @Before
    public void setup() {
        thesaurus = new Thesaurus();

    }

    @Test
    public void detectsThatClueIsAnAnagram() {
        clue = new AnagramClue(thesaurus);
        assertThat(clue.isRelevant("Times when things appear obscure?"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAnAnagram() {
        clue = new AnagramClue(thesaurus);
        assertThat(clue.isRelevant("yearn for quite a while"), is(false));
    }

    @Test
    public void anagramClueTestCanBeSolved() {
        clue = new AnagramClue(thesaurus);
        Optional<List<String>> solvedWord = clue.solve("Times when things appear obscure?", "", 6);

        assertThat(solvedWord.get().get(0), is("nights"));
    }

    @Test
    public void secondAnagramClueCanBeSolved() {
        clue = new AnagramClue(thesaurus);
        Optional<List<String>> answer = clue.solve("School run - true/false", "", 7);

        assertThat(answer.get().get(0), is("nurture"));
    }

    @Ignore
    @Test
    public void firstAnagramClueTestCanBeCreated() {
        clue = new AnagramClue(thesaurus);
        String crypticCrossword = clue.create("nights");

        assertThat(crypticCrossword, containsString("thing"));
    }

    @Test
    public void anagramFindsAllWords() {
        clue = new AnagramClue(thesaurus);
        List listOfWords = clue.findAnagram("friend");

        assertThat(listOfWords.contains("finder"), is(true));
    }

    @Test
    public void isValidKeywordTest() {
        clue = new AnagramClue(thesaurus);
        ArrayList<String> list = new ArrayList<>(Arrays.asList("school", "run", "true", "false"));

        assertThat(clue.isValidKeyword(list, "run", 7), is(false));
    }

    @Test
    public void isValidKeywordSecondTest() {
        clue = new AnagramClue(thesaurus);
        ArrayList<String> list = new ArrayList<>(Arrays.asList("school", "run", "true", "false"));

        assertThat(clue.isValidKeyword(list, "false", 7), is(true));
    }

    @Test
    public void possibleAnagramsTest() {
        clue = new AnagramClue(thesaurus);
        ArrayList<String> list = new ArrayList<>(Arrays.asList("school", "run", "true", "false"));

        assertThat(Helper.combineWords(list, 7).contains("truerun"), is(true));
    }


    @Test
    @Table({@Row({"Get cast adrift in boat", "obtain", "6", ""}),
            @Row({"Lisa mistaken to travel to sea", "sail", "4", ""}),
            @Row({"Pleasant tumble in gale", "genial", "6", ""}),
            @Row({"Delays upset traders", "retards", "7", ".....d."}),
            @Row({"Pleased about a dire gift?", "gratified", "9", ""}),
            @Row({"Desert or ruins made good", "restored", "8", "...t...."}),
            @Row({"Dreadful nag punter found offensive", "repugnant", "9", ""}),
            //@Row({"A Greek mountain could be so lumpy", "olympus", "7", "null"}),
            @Row({"Go near fresh fruit", "orange", "6", ""}),
            //@Row({"Keeps dissolving in tears", "retains", "7", "null"}),
            @Row({"The importance of eating mud pie?", "magnitude", "9", ""}),
            @Row({"Reach up at tangled skydiving apparatus", "parachute", "9", ""}),
            @Row({"Form of rule as yet without extravagance", "austerely", "9", ""}),
            @Row({"Insane damn yeti is explosive", "dynamite", "8", ""}),
            @Row({"Badly pare the fruit", "pear", "4", ""}),
            @Row({"He cooked planet's animal", "elephant", "8", ""}),
            @Row({"Flustered, I forget rarer chilly compartment", "refrigerator", "12", ""}),
            //@Row({"Perilous sea dog? Run all over the place!", "dangerous", "9", "null"}),
            @Row({"Mixed a lad’s greens", "salad", "5", ""}),
            @Row({"Stinging insect damaged paws", "wasp", "4", ""}),
            @Row({"Reversed out and got booked!", "reserved", "8", "r......."}),
            @Row({"His patter confused analyst", "therapist", "9", ""}),
            @Row({"Teach about swindler", "cheat", "5", ""}),
            @Row({"Hens to become truthful?", "honest", "6", ""}),
            @Row({"Who's shaken up the display?", "show", "4", ""})})
    public void bulkClueTest(String crosswordClue, String clueAnswer, String answerLength, String hint) {
        clue = new AnagramClue(thesaurus);
        Optional<List<String>> answer = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));

        assertThat(answer.get().get(0), is(clueAnswer));
    }
}
