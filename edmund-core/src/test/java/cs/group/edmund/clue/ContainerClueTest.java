package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContainerClueTest {

    @Test
    public void detectsThatClueIsAnContainer() {
        Clue clue = new ContainerClue();
        assertThat(clue.isRelevant("Bird allowed outside tavern"), is(true));
        assertThat(clue.isRelevant("Relative entering Highland dance and showing off"), is(true));
        assertThat(clue.isRelevant("Stuck with tot holding present"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAContainer() {
        Clue clue = new ContainerClue();
        assertThat(clue.isRelevant("Crazy flying mammals"), is(false));
    }

    @Ignore
    @Test
    public void containerClueTestCanBeSolved() {
        Clue clue = new ContainerClue();
        String solvedWord = clue.solve("Stop getting letters from friends", 3);

        assertThat(solvedWord, is("end"));
    }

}
