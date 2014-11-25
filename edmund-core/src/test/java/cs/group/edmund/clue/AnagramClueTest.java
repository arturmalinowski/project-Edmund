package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class AnagramClueTest {

    private AnagramClue clue;

    @Test
    public void detectsThatClueIsAnAnagram() {
        clue = new AnagramClue();
        assertThat(clue.isRelevant("Times when things appear obscure?"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAnAnagram() {
        clue = new AnagramClue();
        assertThat(clue.isRelevant("yearn for quite a while"), is(false));
    }

    @Test
    public void anagramClueTestCanBeSolved() {
        clue = new AnagramClue();
        String solvedWord = clue.solve("Times when things appear obscure?", null, 6);

        assertThat(solvedWord, is("nights"));
    }

    @Test
    public void secondAnagramClueCanBeSolved() {
        clue = new AnagramClue();
        String answer = clue.solve("School run - true/false", null, 7);

        assertThat(answer, is("nurture"));
    }

    @Ignore
    @Test
    public void firstAnagramClueTestCanBeCreated() {
        clue = new AnagramClue();
        String crypticCrossword = clue.create("nights");

        assertThat(crypticCrossword, containsString("things"));
    }

    @Ignore
    @Test
    public void secondAnagramClueTestCanBeCreated() {
        clue = new AnagramClue();
        String crypticCrossword = clue.create("friend");

        assertThat(crypticCrossword, anyOf(containsString("finder"), containsString("redfin")));
    }

    @Test
    public void anagramFindsAllWords() {
        clue = new AnagramClue();
        List listOfWords = clue.findAnagram("friend");
        System.out.println("cze");
        assertThat(listOfWords.contains("finder"), is(true));
        assertThat(listOfWords.contains("redfin"), is(true));
        assertThat(listOfWords.contains("refind"), is(true));
    }

    @Test
    public void isValidKeywordTest() {
        clue = new AnagramClue();
        ArrayList list = new ArrayList<>(Arrays.asList("school", "run", "true", "false"));

        assertThat(clue.isValidKeyword(list, "run", 7), is(false));
    }

    @Test
    public void isValidKeywordSecondTest() {
        clue = new AnagramClue();
        ArrayList list = new ArrayList<>(Arrays.asList("school", "run", "true", "false"));

        assertThat(clue.isValidKeyword(list, "false", 7), is(true));
    }

    @Test
    public void possibleAnagramsTest() {
        clue = new AnagramClue();
        ArrayList list = new ArrayList<>(Arrays.asList("school", "run", "true", "false"));

        assertThat(clue.possibleAnagrams(list, 7).contains("truerun"), is(true));
    }

    @Ignore
    @Test
    @Table({@Row({"Get cast adrift in boat", "obtain", "6"}),
    @Row({"Lisa mistaken to travel to sea", "sail", "4"}),
    @Row({"Pleasant tumble in gale", "genial", "6"}),
    @Row({"Delays upset traders", "retards", "7"}),
    @Row({"Pleased about a dire gift?", "gratified", "9"}),
    @Row({"Desert or ruins made good", "restored", "8"}),
    @Row({"Dreadful nag punter found offensive", "repugnant", "9"}),
    @Row({"A Greek mountain could be so lumpy", "olympus", "7"}),
    @Row({"Go near fresh fruit", "orange", "6"}),
    @Row({"Keeps dissolving in tears", "retains", "7"}),
    @Row({"The importance of eating mud pie?", "magnitude", "9"}),
    @Row({"Reach up at tangled skydiving apparatus", "parachute", "9"}),
    @Row({"Form of rule as yet without extravagance", "austerely", "9"}),
    @Row({"Insane damn yeti is explosive", "dynamite", "8"}),
    @Row({"Badly pare the fruit", "pear", "4"}),
    @Row({"He cooked planet's animal", "elephant", "8"}),
    @Row({"Flustered, I forget rarer chilly compartment", "refrigerator", "12"}),
    @Row({"Perilous sea dog? Run all over the place!", "dangerous", "9"}),
    @Row({"Mixed a lad’s greens", "salad", "5"}),
    @Row({"Stinging insect damaged paws", "wasp", "4"}),
    @Row({"Reversed out and got booked!", "reserved", "8"}),
    @Row({"His patter confused analyst", "therapist", "9"}),
    @Row({"Teach about swindler", "cheat", "5"}),
    @Row({"Hens to become truthful?", "honest", "5"}),
    @Row({"Who's shaken up the display?", "show", "4"})})
    public void bulkClueTest(String crosswordClue, String clueAnswer, String answerLength) {
        clue = new AnagramClue();
        String answer = clue.solve(crosswordClue, null, Integer.parseInt(answerLength));

        assertThat(answer, is(clueAnswer));
    }

    @Test
    public void secondAnagramClueCanBeSolved1() throws Exception {
        clue = new AnagramClue();
        String answer = clue.solve("Delays upset traders", null, 7);

        assertThat(answer, is("retards"));
    }
}
