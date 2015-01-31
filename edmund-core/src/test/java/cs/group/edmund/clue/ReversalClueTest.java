package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(TableRunner.class)
public class ReversalClueTest {

    private Thesaurus thesaurus;

    @Before
    public void setup() {
        thesaurus = new Thesaurus();
    }

    @Test
    public void detectsThatClueIsAReversal() {
        Clue clue = new ReversalClue(thesaurus);
        assertThat(clue.isRelevant("Physician brings fish round"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAReversal() {
        Clue clue = new ReversalClue(thesaurus);
        assertThat(clue.isRelevant("Crazy flying mammals"), is(false));
    }

    @Test
    public void reversalClueCanBeSolved() {
        ArrayList<String> firstReturnedValues = new ArrayList<>(Arrays.asList("doc", "doctor", "invalid-twoWords", "medical"));
        ArrayList<String> secondReturnedValues = new ArrayList<>(Arrays.asList("serve", "servant", "invalid-twoWords", "pulls"));
        ArrayList<String> thirdReturnedValues = new ArrayList<>(Arrays.asList("carp", "cod", "invalid-twoWords", "sea"));
        ArrayList<String> forthReturnedValues = new ArrayList<>(Arrays.asList("circle", "outside", "invalid-twoWords", "loop"));

        when(mock(Thesaurus.class).getAllSynonymsXML("physician")).thenReturn(firstReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("brings")).thenReturn(secondReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("fish")).thenReturn(thirdReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("around")).thenReturn(forthReturnedValues);

        Clue clue = new ReversalClue(thesaurus);
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
            @Row({"Animal going round a shopping precinct", "", "5", "llama"}),
            @Row({"Serve up a drink, fit for a king", "", "5", "regal"}),
            @Row({"Stop the flow in crazy get-up", "", "3", "dam"}),
            @Row({"An elevating kipling poem", "", "2", "na"}),
            @Row({"Rising star, nonsense", "", "2", "rats"}),
            @Row({"Bambi for example, overturned a plant", "", "4", "deer"})
    })
    public void bulkClueTest(String clue, String hint, String length, String answer) {
        Clue reversalClue = new ReversalClue(thesaurus);
        String solvedWord = reversalClue.solve(clue, hint, Integer.parseInt(length));

        assertThat(solvedWord, is(answer));
    }
}
