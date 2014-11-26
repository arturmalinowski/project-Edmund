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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class ContainerClueTest {

    private ContainerClue clue;

    @Before
    public void setup() {
        clue = new ContainerClue();
    }

    // Current success rate 22.2%
    @Ignore
    @Test
    @Table({
            @Row({"Put on around the brave", "weather", "7"}),
            @Row({"wear around the brave", "weather", "7"}),
            @Row({"Damage surrounding the brave", "weather", "7"}),
            @Row({"Stash or put in stage", "storage", "7"}),
            @Row({"Stuck with tot holding present", "adhered", "7"}),
            @Row({"Relative entering Highland dance and showing off", "flaunting", "9"}),
            @Row({"Outlaws in gangs carrying equipment", "brigands", "8"}),
            @Row({"Object when put into torn clothing", "raiment", "7"}),
            @Row({"Bird allowed outside tavern", "linnet", "7"})
    })
    public void bulkClueTest(String crosswordClue, String clueAnswer, String answerLength) {
        String answer = clue.solve(crosswordClue, null, Integer.parseInt(answerLength));
        assertThat(answer, is(clueAnswer));
    }

    @Test
    public void detectsThatClueIsAnContainer() {
        assertThat(clue.isRelevant("Bird allowed outside tavern"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAContainer() {
        assertThat(clue.isRelevant("Crazy flying mammals"), is(false));
    }

    @Test
    public void testGetKeyword() {
        assertThat(clue.getKeyword("bird allowed outside tavern"), is("outside"));
    }

    @Test
    public void testSplitPhrase() {
        String phrase = "bird allowed outside tavern";
        String keyword = "outside";
        ArrayList<String> list = new ArrayList<String>();
        list.add("bird allowed");
        list.add("tavern");

        assertThat(clue.splitPhrase(phrase,keyword), is(list));
    }

    @Test
    public void testCompareLists() {
        ArrayList<String> synonyms = new ArrayList<String>();
        ArrayList<String> solutions = new ArrayList<String>();
        ArrayList<String> answer = new ArrayList<String>();
        answer.add("linnet");
        synonyms.add("ROBIN");
        synonyms.add("LINNET");
        synonyms.add("OWL");
        synonyms.add("PIGEON");
        solutions.add("LINNET");
        solutions.add("LEINNT");
        solutions.add("LETINN");

        assertThat(answer, is(clue.compareLists(synonyms, solutions)));
    }
}
