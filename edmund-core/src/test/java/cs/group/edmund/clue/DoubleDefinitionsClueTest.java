package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

@RunWith(TableRunner.class)
public class DoubleDefinitionsClueTest {

    private DoubleDefinitionsClue clue;

    @Before
    public void setup() {
        clue = new DoubleDefinitionsClue();
    }

    @Test
    public void detectsTheDoubleDefinitionClue() {
        assertEquals(clue.isRelevant("Expensive honey"), true);
    }

    @Test
    public void detectsClueIsNotADoubleDefinitionClue() {
        assertEquals(clue.isRelevant("Bird allowed outside tavern"), false);
    }

    // Current success rate 42.8%, more examples needed
    @Ignore
    @Test
    @Table({
            @Row({"nothing for romance", ".o..", "4", "love"}),
            @Row({"instant credit", "...k", "4", "tick"}),
            @Row({"armor in the post", "m...", "4", "mail"}),
            @Row({"sound warning for a temptress", "..r..", "5", "siren"}),
            @Row({"fish detected by its odour", ".m...", "5", "smelt"}),
            @Row({"run away from an infertile area", ".....t", "6", "desert"}),
            @Row({"a fast train say", "e......", "7", "express"})
    })
    public void bulkClueTest(String cryticClue, String hint, String answerLength, String answer) {
        String solvedWord = clue.solve(cryticClue, hint, Integer.parseInt(answerLength));

        assertThat(solvedWord, is(answer));
    }

    @Test
    public void correctWordIsFoundInListViaAHint() {
        String solvedWord = clue.solve("Armor in the post", "m...", 4);

        assertThat(solvedWord, is("mail"));
    }

    @Test
    public void limitedDoubleDefinitionClueCanBeSolved() {
        String solvedWord = clue.solve("Expensive sweet golden honey", ".e..", 4);

        assertThat(solvedWord, is("dear"));
    }

    @Test
    public void doubleDefinitionClueWithClueWordNotAtTheLeftEndOfTheClueCanBeSolved() {
        String solvedWord = clue.solve("Sweet expensive golden honey", ".e..", 4);

        assertThat(solvedWord, is("dear"));
    }

    @Test
    public void doubleDefinitionClueWithClueWordNotAtTheRightEndOfTheClueCanBeSolved() {
        String solvedWord = clue.solve("Expensive sweet honey golden", ".e..", 4);

        assertThat(solvedWord, is("dear"));
    }

    @Test
    public void moreComplexDoubleDefinitionClueCanBeSolved() {
        String solvedWord = clue.solve("A fast train, say", "e......", 7);

        assertThat(solvedWord, is("express"));
    }

    @Test
    public void handlesWhenNoMatchingWordIsFound() {
        String solvedWord = clue.solve("Bird allowed outside tavern", null, 4);

        assertThat(solvedWord, is("Answer not found :("));
    }

    @Test
    public void correctLengthWordIsReturned() {
        String solvedWord = clue.solve("Expensive expensive", ".e..", 4);
        assertThat(solvedWord, is("dear"));
    }

}
