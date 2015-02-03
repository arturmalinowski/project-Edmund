package cs.group.edmund;

import cs.group.edmund.typeSelector.Selector;
import org.junit.Before;
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
    public void edmundReturnsTheCorrectAnswerForNonSpecifiedClue() {
        String answer = "";
        try {
            answer = selector.retrieveAnswer("Physician brings fish around", "", 3);
        } catch (Exception e) {
        }

        assertThat(answer, is("doc"));
    }
}
