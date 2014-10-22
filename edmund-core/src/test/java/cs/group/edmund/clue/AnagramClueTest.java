package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.anyOf;

public class AnagramClueTest {

    @Test
    public void detectsThatClueIsAnAnagram() {
        Clue clue = new AnagramClue();
        assertThat(clue.isRelevant("Times when things appear obscure?"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAnAnagram() {
        Clue clue = new AnagramClue();
        assertThat(clue.isRelevant("yearn for quite a while"), is(false));
    }

    @Ignore
    @Test
    public void anagramClueTestCanBeSolved() {
        Clue clue = new AnagramClue();
        String solvedWord = clue.solve("Times when things appear obscure?", 6);

        assertThat(solvedWord, is("nights"));
    }

    @Ignore
    @Test
    public void firstAnagramClueTestCanBeCreated() {
        Clue clue = new AnagramClue();
        String crypticCrossword = clue.create("nights");

        assertThat(crypticCrossword, containsString("things"));
    }

    @Ignore
    @Test
    public void secondAnagramClueTestCanBeCreated() {
        Clue clue = new AnagramClue();
        String crypticCrossword = clue.create("friend");

        assertThat(crypticCrossword, anyOf(containsString("finder"), containsString("redfin")));
    }
}
