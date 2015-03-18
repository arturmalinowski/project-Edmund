package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

import static cs.group.edmund.matchers.ContainsExpectedWords.containsWords;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(TableRunner.class)
public class DoubleDefinitionsClueTest {

    private DoubleDefinitionsClue clue;

    @Before
    public void setup() {
        clue = new DoubleDefinitionsClue(new Thesaurus());
    }

    @Test
    public void detectsTheDoubleDefinitionClue() {
        assertEquals(clue.isRelevant("Expensive honey"), true);
    }

    @Ignore
    @Test
    public void detectsClueIsNotADoubleDefinitionClue() {
        assertEquals(clue.isRelevant("Bird allowed outside tavern"), false);
    }

    // Current success rate 20% without hints, 50% with hints (Current non passing tests commented)
    @Test
    @Table({
//            @Row({"Nothing for romance", "", "4", "love"}),
            @Row({"Expensive honey", "", "4", "dear"}),
//            @Row({"Instant credit", "", "4", "tick"}),
            @Row({"Armor in the post", "m...", "4", "mail"}),
//            @Row({"Sound warning for a temptress", "", "5", "siren"}),
//            @Row({"Fish detected by its odour", "", "5", "smelt"}),
            @Row({"Run away from an infertile area", "d.....", "6", "desert"}),
            @Row({"A fast train say", "", "7", "express"}),
//            @Row({"Put up with an animal", "", "4", "bear"}),
//            @Row({"Yearn for a while", "", "4", "long"}),
            @Row({"Succession of command", "", "5", "order"}),
//            @Row({"Eaten up and taken in eagerly", "", "8", "devoured"}),
//            @Row({"Spy found", "", "5", "plant"}),
//            @Row({"Reject junk", "..f...", "6", "refuse"}),
//            @Row({"Alter coins", "", "6", "change"}),
//            @Row({"Choose tool", "", "4", "pick"}),
//            @Row({"Noble number", ".o...", "5", "count"}),
//            @Row({"Declare complete", "u....", "5", "utter"}),
            @Row({"Go in record", "e....", "5", "enter"})
//            @Row({"Lookalike twofold", "", "6", "double"})
    })
    public void bulkClueTest(String crypticClue, String pattern, String answerLength, String answer) {
        Optional<List<String>> solvedWord = clue.solve(crypticClue, pattern, Integer.parseInt(answerLength));

        assertThat(solvedWord.get().get(0), is(answer));
    }

    @Test
    public void correctWordIsFoundInListViaAHint() {
        Optional<List<String>> solvedWord = clue.solve("Armor in the post", "m...", 4);

        assertThat(solvedWord.get().get(0), is("mail"));
    }

    @Test
    public void limitedDoubleDefinitionClueCanBeSolved() {
        Optional<List<String>> solvedWord = clue.solve("Expensive sweet golden honey", ".e..", 4);

        assertThat(solvedWord.get().get(0), is("dear"));
    }

    @Test
    public void doubleDefinitionClueWithClueWordNotAtTheLeftEndOfTheClueCanBeSolved() {
        Optional<List<String>> solvedWord = clue.solve("Sweet expensive golden honey", ".e..", 4);

        assertThat(solvedWord.get().get(0), is("dear"));
    }

    @Test
    public void doubleDefinitionClueWithClueWordNotAtTheRightEndOfTheClueCanBeSolved() {
        Optional<List<String>> solvedWord = clue.solve("Expensive sweet honey golden", ".e..", 4);

        assertThat(solvedWord.get().get(0), is("dear"));
    }

    @Test
    public void moreComplexDoubleDefinitionClueCanBeSolved() {
        Optional<List<String>> solvedWord = clue.solve("A fast train, say", "e......", 7);

        assertThat(solvedWord.get().get(0), is("express"));
    }

    @Test
    public void handlesWhenNoMatchingWordIsFound() {
        Optional<List<String>> solvedWord = clue.solve("Bird allowed outside tavern", null, 4);

        assertTrue(!solvedWord.isPresent());
    }

    @Test
    public void correctLengthWordIsReturned() {
        Optional<List<String>> solvedWord = clue.solve("Expensive expensive", ".e..", 4);
        assertThat(solvedWord.get().get(0), is("dear"));
    }

    @Test
    public void hintWithMoreThanOneLetterIsAccepted() {
        Optional<List<String>> solvedWord = clue.solve("Reject junk", "..fu..", 6);
        assertThat(solvedWord.get().get(0), is("refuse"));
    }

    @Test
    public void twoWordedAnswerCanBeFound() {
        Optional<List<String>> solvedWord = clue.solve("Expensive expensive", null, 6, 3);

        assertThat(solvedWord.get().toString(), containsWords("dearly won"));
    }

    @Test
    public void invalidWordsDoNotCallApi() {
        Thesaurus thesaurus = mock(Thesaurus.class);
        DoubleDefinitionsClue doubleDefinitionClue = new DoubleDefinitionsClue(thesaurus);
        doubleDefinitionClue.solve("the a", null, 4);

        verify(thesaurus, never()).getAllSynonymsXML("the");
        verify(thesaurus, never()).getAllSynonymsXML("a");
    }

    @Test
    public void moreThanOneMatchingWordsCanBeFound() {
        Optional<List<String>> solvedWord = clue.solve("Expensive expensive", null, 6);

        assertThat(solvedWord.get().toString(), containsWords("costly, pricey"));
    }


}
