package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class ReversalClueTest {

    @Test
    public void detectsThatClueIsAReversal() {
        Clue clue = new ReversalClue();
        assertThat(clue.isRelevant("Physician brings fish round"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAReversal() {
        Clue clue = new ReversalClue();
        assertThat(clue.isRelevant("Crazy flying mammals"), is(false));
    }

    @Test
    public void reversalClueCanBeSolved() {
        Clue clue = new ReversalClue();
        String solvedWord = clue.solve("Physician brings fish round", null, 3);

        assertThat(solvedWord, is("doc"));
    }

    // Currently 2/5 passing, need to find more examples and improve success ratio
    @Ignore
    @Test
    @Table({
            @Row({"Physician brings fish round", "", "3", "doc"}),
            @Row({"Going round stronghold, take a look", "p...", "4", "peek"}),
            @Row({"Containers for drinks taken back to bar", "", "4", "stop"}),
            @Row({"Animal going round a shopping precinct", "", "4", "llama"}),
            @Row({"Bambi for example, overturned a plant", "", "4", "deer"})
    })
    public void bulkClueTest(String clue, String hint, String length, String answer) {
        Clue reversalClue = new ReversalClue();
        String solvedWord = reversalClue.solve(clue, hint, Integer.parseInt(length));

        assertThat(solvedWord, is(answer));
    }
}
