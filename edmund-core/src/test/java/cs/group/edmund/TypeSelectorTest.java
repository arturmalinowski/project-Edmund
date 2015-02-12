package cs.group.edmund;

import cs.group.edmund.typeSelector.Selector;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TypeSelectorTest {

    private Selector selector;
    private Thesaurus thesaurus;

    @Before
    public void setup() {
        thesaurus = new Thesaurus();
        selector = new Selector();
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAReversalClue() throws Exception {
        String answer = selector.retrieveAnswer("Physician brings fish around", "", 3, thesaurus);

        assertThat(answer, is("doc"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAnAnagramClue() throws Exception {
        String answer = selector.retrieveAnswer("Times when things appear obscure?", "", 6, thesaurus);

        assertThat(answer, is("nights"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAnOddEvenClue() throws Exception {
        String answer = selector.retrieveAnswer("Observe odd characters in scene", "", 3, thesaurus);

        assertThat(answer, is("see"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAContainerClue() throws Exception {
        String answer = selector.retrieveAnswer("We surrounded strike snowy", "w....", 5, thesaurus);

        assertThat(answer, is("white"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForADeletionClue() throws Exception {
        String answer = selector.retrieveAnswer("Challenging sweetheart heartlessly", "d.....", 6, thesaurus);

        assertThat(answer, is("daring"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForADoubleDefinitionClue() throws Exception {
        String answer = selector.retrieveAnswer("Armor in the post", "m...", 4, thesaurus);

        assertThat(answer, is("mail"));
    }
}
