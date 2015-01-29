package cs.group.edmund;

import cs.group.edmund.clue.AnagramClue;
import cs.group.edmund.clue.Clue;
import cs.group.edmund.type_selector.Selector;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class TypeSelectorTest {

    @Test
    public void edmundCanDecipherTheCorrectClueType() {
        Selector selector = new Selector();

        Clue clueType = selector.findRelevantClueType("Times when things appear obscure?");

        assertThat(clueType, instanceOf(AnagramClue.class));
    }
}