package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContainerClueTest {

    @Ignore
    @Test
    public void anagramClueTestCanBeSolved() {
        Clue clue = new ContainerClue();
        String solvedWord = clue.solve("Stop getting letters from friends");

        assertThat(solvedWord, is("end"));
    }

}
