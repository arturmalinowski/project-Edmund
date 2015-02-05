package cs.group.edmund;

import cs.group.edmund.typeSelector.Selector;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TypeSelectorTest {

    private Selector selector;

    @Before
    public void setup() {
        selector = new Selector();
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAReversalClue() throws Exception {
        String answer = selector.retrieveAnswer("Physician brings fish around", "", 3);

        assertThat(answer, is("doc"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAnAnagramClue() throws Exception {
        String answer = selector.retrieveAnswer("Times when things appear obscure?", "", 6);

        assertThat(answer, is("nights"));
    }

    //@Ignore
    @Test
    public void edmundReturnsTheCorrectAnswerForAnOddEvenClue() throws Exception {
        String answer = selector.retrieveAnswer("Observe odd characters in scene", "", 3);

        assertThat(answer, is("see"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAContainerClue() throws Exception {
        String answer = selector.retrieveAnswer("We surrounded strike snowy", "w....", 5);

        assertThat(answer, is("white"));
    }
}
