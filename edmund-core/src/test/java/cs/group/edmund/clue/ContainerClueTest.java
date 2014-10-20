package cs.group.edmund.clue;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContainerClueTest {

    @Test
    public void detectsThatClueIsAnContainer() {
        Clue clue = new ContainerClue();
        assertThat(clue.isRelevant("Everything can be a container"), is(true));
        assertThat(clue.isRelevant("No matter what the clue is"), is(true));
        assertThat(clue.isRelevant("Containers have no magical keywords"), is(true));
        assertThat(clue.isRelevant("Even this will return true"), is(true));
        assertThat(clue.isRelevant(null), is(true));
    }

    @Ignore
    @Test
    public void containerClueTestCanBeSolved() {
        Clue clue = new ContainerClue();
        String solvedWord = clue.solve("Stop getting letters from friends");

        assertThat(solvedWord, is("end"));
    }

}
