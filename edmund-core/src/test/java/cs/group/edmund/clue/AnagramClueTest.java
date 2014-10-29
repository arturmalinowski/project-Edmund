package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
        String solvedWord = clue.solve("Times when things appear obscure?", 6);

        assertThat(solvedWord, is("nights"));
    }

    @Ignore
    @Test
    public void secondAnagramClueCanBeSolved() {
        clue = new AnagramClue();
        String answer = clue.solve("School run - true/false", 7);

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
}
