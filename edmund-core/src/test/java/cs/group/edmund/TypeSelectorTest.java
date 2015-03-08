package cs.group.edmund;

import cs.group.edmund.typeSelector.Selector;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static cs.group.edmund.matchers.ContainsExpectedWords.containsWords;
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
        List<String> answer = selector.retrieveAnswer("Physician brings fish around", "", 3, thesaurus);

        assertThat(answer.get(0), is("doc"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAnAnagramClue() throws Exception {
        List<String> answer = selector.retrieveAnswer("Times when things appear obscure?", "", 6, thesaurus);

        assertThat(answer.get(0), is("nights"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAnOddEvenClue() throws Exception {
        List<String> answer = selector.retrieveAnswer("Observe odd characters in scene", "", 3, thesaurus);

        assertThat(answer.get(0), is("see"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForAContainerClue() throws Exception {
        List<String> answer = selector.retrieveAnswer("Hide in Arthur's kingdom", "", 4, thesaurus);

        assertThat(answer.get(0), is("king"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForADeletionClue() throws Exception {
        List<String> answer = selector.retrieveAnswer("Challenging sweetheart heartlessly", "d.....", 6, thesaurus);

        assertThat(answer.get(0), is("daring"));
    }

    @Test
    public void edmundReturnsTheCorrectAnswerForADoubleDefinitionClue() throws Exception {
        List<String> answer = selector.retrieveAnswer("Armor in the post", "m...", 4, thesaurus);

        assertThat(answer.get(0), is("mail"));
    }

    @Test
    public void edmundCanReturnMultiplePossibleAnswers() throws Exception {
        List<String> answer = selector.retrieveAnswer("Reject junk", "......", 6, thesaurus);

        assertThat(answer.toString(), containsWords("jetsam, refuse"));
    }
}
