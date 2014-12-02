package cs.group.edmund.clue;


import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OddEvenClueTest {

    private OddEvenClue clue;

    @Test
    public void detectThatClueIsOddEvenTest() {
        clue = new OddEvenClue();
        assertThat(clue.isRelevant("Observe odd characters in scene"), is(true));
    }

    @Test
    public void detectThatClueIsNotOddEvenTest() {
        clue = new OddEvenClue();
        assertThat(clue.isRelevant("Times when things appear obscure?"), is(false));
    }

    @Ignore
    @Test
    public void firstOddEvenClueCanBeSolved() {
        clue = new OddEvenClue();
        String answer = clue.solve("Have a meal with every other tenant", null, 3);

        assertThat(answer, is("eat"));
    }
}
