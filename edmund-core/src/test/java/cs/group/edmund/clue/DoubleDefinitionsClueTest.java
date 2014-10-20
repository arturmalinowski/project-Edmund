package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DoubleDefinitionsClueTest {

    @Ignore
    @Test
    public void anagramClueTestCanBeSolved() {
        Clue clue = new DoubleDefinitionsClue();
        String solvedWord = clue.solve("Yearn for quite a while");

        assertThat(solvedWord, is("long"));
    }

}
