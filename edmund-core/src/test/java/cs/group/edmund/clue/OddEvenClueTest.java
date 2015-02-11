package cs.group.edmund.clue;


import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class OddEvenClueTest {

    private OddEvenClue clue;

    @Test
    public void detectThatClueIsOddEvenTest() {
        clue = new OddEvenClue();
        assertThat(clue.isRelevant("Observe odd characters in scene"), is(true));
    }

    @Test
    public void detectThatClueIsNotOddEvenTest() {
        clue = new OddEvenClue();
        assertThat(clue.isRelevant("Times when things appear obscure?"), is(false));
    }

    @Test
    public void firstOddEvenClueCanBeSolved() {
        clue = new OddEvenClue();
        Optional<List<String>> answer = clue.solve("Have a meal with every other tenant", null, 3);

        assertThat(answer.get().get(0), is("eat"));
    }


    @Test
    public void secondOddEvenClueCanBeSolved() {
        clue = new OddEvenClue();
        Optional<List<String>> answer = clue.solve("Observe odd characters in scene", null, 3);

        assertThat(answer.get().get(0), is("see"));
    }


    @Test
    @Table({@Row({"Sprout evenly in plant container", "pot", "3", "null"}),
            @Row({"Odd oratress has rows", "oars", "4", "null"}),
            //@Row({"Fern-owls itch regularly in play", "frolic", "6", "null"}),
            //@Row({"Regulars in hard gang few saw", "adage", "5", "null"}),
            @Row({"Conduct uneven wrangle", "wage", "4", "null"})})
    public void bulkClueTest(String crosswordClue, String clueAnswer, String answerLength, String hint) {
        clue = new OddEvenClue();
        Optional<List<String>> answer = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));

        assertThat(answer.get().get(0), is(clueAnswer));
    }
}
