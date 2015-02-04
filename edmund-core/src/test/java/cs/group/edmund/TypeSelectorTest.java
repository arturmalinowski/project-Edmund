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
    public void edmundReturnsTheCorrectAnswerForAReversalClue() {
        String answer = "";
        try {
            answer = selector.retrieveAnswer("Physician brings fish around", "", 3);
        } catch (Exception e) {
        }

        assertThat(answer, is("doc"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAnAnagramClue() {
        String answer = "";
        try {
            answer = selector.retrieveAnswer("Times when things appear obscure?", "", 6);
        } catch (Exception e) {
        }

        assertThat(answer, is("nights"));
    }

    @Ignore
    @Test
    public void edmundReturnsTheCorrectAnswerForAnOddEvenClue() {
        String answer = "";
        try {
            answer = selector.retrieveAnswer("Observe odd characters in scene", "", 3);
        } catch (Exception e) {
        }

        assertThat(answer, is("see"));
    }

    @Ignore
    @Test
    public void edmundReturnsTheCorrectAnswerForAContainerClue() {
        String answer = "";
        try {
            answer = selector.retrieveAnswer("We surrounded strike snowy", "w....", 5);
        } catch (Exception e) {
        }

        assertThat(answer, is("white"));
    }
}
