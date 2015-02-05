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
import java.util.Optional;

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

    @Test
    public void detectsClueIsNotADoubleDefinitionClue() {
        assertEquals(clue.isRelevant("Bird allowed outside tavern"), false);
    }

    // Current success rate 20% without hints, 50% with hints
    @Ignore
    @Test
    @Table({
            @Row({"Nothing for romance", "", "4", "love"}),
            @Row({"Expensive honey", "", "4", "dear"}),
            @Row({"Instant credit", "", "4", "tick"}),
            @Row({"Armor in the post", "m...", "4", "mail"}),
            @Row({"Sound warning for a temptress", "", "5", "siren"}),
            @Row({"Fish detected by its odour", "", "5", "smelt"}),
            @Row({"Run away from an infertile area", "d.....", "6", "desert"}),
            @Row({"A fast train say", "", "7", "express"}),
            @Row({"Put up with an animal", "", "4", "bear"}),
            @Row({"Yearn for a while", "", "4", "long"}),
            @Row({"Succession of command", "", "5", "order"}),
            @Row({"Eaten up and taken in eagerly", "", "8", "devoured"}),
            @Row({"Spy found", "", "5", "plant"}),
            @Row({"Reject junk", "..f...", "6", "refuse"}),
            @Row({"Alter coins", "", "6", "change"}),
            @Row({"Choose tool", "", "4", "pick"}),
            @Row({"Noble number", ".o...", "5", "count"}),
            @Row({"Declare complete", "u....", "5", "utter"}),
            @Row({"Go in record", "e....", "5", "enter"}),
            @Row({"Lookalike twofold", "", "6", "double"})
    })
    public void bulkClueTest(String crypticClue, String pattern, String answerLength, String answer) {
        Optional<String> solvedWord = clue.solve(crypticClue, pattern, Integer.parseInt(answerLength));

        assertThat(solvedWord.get(), is(answer));
    }

    @Test
    public void correctWordIsFoundInListViaAHint() {
        ArrayList<String> firstReturnedValues = new ArrayList<>(Arrays.asList("dearly-won", "dear", "invalid-twoWords", "mail"));
        ArrayList<String> secondReturnedValues = new ArrayList<>(Arrays.asList("expensive", "royal", "invalid-twoWords", "mail"));

        when(mock(Thesaurus.class).getAllSynonymsXML("armour")).thenReturn(firstReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("post")).thenReturn(secondReturnedValues);

        Optional<String> solvedWord = clue.solve("Armor in the post", "m...", 4);

        assertThat(solvedWord.get(), is("mail"));
    }

    @Test
    public void limitedDoubleDefinitionClueCanBeSolved() {
        ArrayList<String> firstReturnedValues = new ArrayList<>(Arrays.asList("dearly-won", "dear", "invalid-twoWords", "mail"));
        ArrayList<String> secondReturnedValues = new ArrayList<>(Arrays.asList("taste", "sour", "invalid-twoWords", "charming"));
        ArrayList<String> thirdReturnedValues = new ArrayList<>(Arrays.asList("yellow", "rich", "invalid-twoWords", "jewelry"));
        ArrayList<String> forthReturnedValues = new ArrayList<>(Arrays.asList("expensive", "dear", "invalid-twoWords", "mail"));

        when(mock(Thesaurus.class).getAllSynonymsXML("expensive")).thenReturn(firstReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("sweet")).thenReturn(secondReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("golden")).thenReturn(thirdReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("honey")).thenReturn(forthReturnedValues);

        Optional<String> solvedWord = clue.solve("Expensive sweet golden honey", ".e..", 4);

        assertThat(solvedWord.get(), is("dear"));
    }

    @Test
    public void doubleDefinitionClueWithClueWordNotAtTheLeftEndOfTheClueCanBeSolved() {
        ArrayList<String> firstReturnedValues = new ArrayList<>(Arrays.asList("taste", "sour", "invalid-twoWords", "charming"));
        ArrayList<String> secondReturnedValues = new ArrayList<>(Arrays.asList("dearly-won", "dear", "invalid-twoWords", "mail"));
        ArrayList<String> thirdReturnedValues = new ArrayList<>(Arrays.asList("yellow", "rich", "invalid-twoWords", "jewelry"));
        ArrayList<String> forthReturnedValues = new ArrayList<>(Arrays.asList("expensive", "dear", "invalid-twoWords", "mail"));

        when(mock(Thesaurus.class).getAllSynonymsXML("sweet")).thenReturn(firstReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("expensive")).thenReturn(secondReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("golden")).thenReturn(thirdReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("honey")).thenReturn(forthReturnedValues);

        Optional<String> solvedWord = clue.solve("Sweet expensive golden honey", ".e..", 4);

        assertThat(solvedWord.get(), is("dear"));
    }

    @Test
    public void doubleDefinitionClueWithClueWordNotAtTheRightEndOfTheClueCanBeSolved() {
        ArrayList<String> firstReturnedValues = new ArrayList<>(Arrays.asList("dearly-won", "dear", "invalid-twoWords", "mail"));
        ArrayList<String> secondReturnedValues = new ArrayList<>(Arrays.asList("taste", "sour", "invalid-twoWords", "charming"));
        ArrayList<String> thirdReturnedValues = new ArrayList<>(Arrays.asList("expensive", "dear", "invalid-twoWords", "mail"));
        ArrayList<String> forthReturnedValues = new ArrayList<>(Arrays.asList("yellow", "rich", "invalid-twoWords", "jewelry"));

        when(mock(Thesaurus.class).getAllSynonymsXML("expensive")).thenReturn(firstReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("sweet")).thenReturn(secondReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("honey")).thenReturn(thirdReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("golden")).thenReturn(forthReturnedValues);

        Optional<String> solvedWord = clue.solve("Expensive sweet honey golden", ".e..", 4);

        assertThat(solvedWord.get(), is("dear"));
    }

    @Test
    public void moreComplexDoubleDefinitionClueCanBeSolved() {
        ArrayList<String> firstReturnedValues = new ArrayList<>(Arrays.asList("quick", "hunger", "invalid-twoWords", "slow"));
        ArrayList<String> secondReturnedValues = new ArrayList<>(Arrays.asList("express", "tracks", "invalid-twoWords", "late"));
        ArrayList<String> thirdReturnedValues = new ArrayList<>(Arrays.asList("speak", "express", "invalid-twoWords", "talk"));

        when(mock(Thesaurus.class).getAllSynonymsXML("fast")).thenReturn(firstReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("train")).thenReturn(secondReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("say")).thenReturn(thirdReturnedValues);

        Optional<String> solvedWord = clue.solve("A fast train, say", "e......", 7);

        assertThat(solvedWord.get(), is("express"));
    }

    @Test
    public void handlesWhenNoMatchingWordIsFound() {
        ArrayList<String> firstReturnedValues = new ArrayList<>(Arrays.asList("fly", "feathers", "invalid-twoWords", "chicken"));
        ArrayList<String> secondReturnedValues = new ArrayList<>(Arrays.asList("entry", "access", "invalid-twoWords", "permitted"));
        ArrayList<String> thirdReturnedValues = new ArrayList<>(Arrays.asList("nature", "seasons", "invalid-twoWords", "air"));
        ArrayList<String> forthReturnedValues = new ArrayList<>(Arrays.asList("pub", "drink", "invalid-twoWords", "inn"));

        when(mock(Thesaurus.class).getAllSynonymsXML("bird")).thenReturn(firstReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("allowed")).thenReturn(secondReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("outside")).thenReturn(thirdReturnedValues);
        when(mock(Thesaurus.class).getAllSynonymsXML("tavern")).thenReturn(forthReturnedValues);

        Optional<String> solvedWord = clue.solve("Bird allowed outside tavern", null, 4);

        assertTrue(!solvedWord.isPresent());
    }

    @Test
    public void correctLengthWordIsReturned() {
        ArrayList<String> returnedValues = new ArrayList<>(Arrays.asList("dearly-won", "dear", "invalid-twoWords"));

        when(mock(Thesaurus.class).getAllSynonymsXML("expensive")).thenReturn(returnedValues);

        Optional<String> solvedWord = clue.solve("Expensive expensive", ".e..", 4);
        assertThat(solvedWord.get(), is("dear"));
    }

    @Test
    public void hintWithMoreThanOneLetterIsAccepted() {
        ArrayList<String> returnedValues = new ArrayList<>(Arrays.asList("dearly-won", "dear", "invalid-twoWords", "refuse"));

        when(mock(Thesaurus.class).getAllSynonymsXML(any(String.class))).thenReturn(returnedValues);

        Optional<String> solvedWord = clue.solve("Reject junk", "..fu..", 6);
        assertThat(solvedWord.get(), is("refuse"));
    }

    @Test
    public void twoWordedAnswerCanBeFound() {
        ArrayList<String> returnedValues = new ArrayList<>(Arrays.asList("dearly-won", "dear", "invalid-twoWords"));

        when(mock(Thesaurus.class).getAllSynonymsXML("expensive")).thenReturn(returnedValues);

        Optional<String> solvedWord = clue.solve("Expensive expensive", null, 6, 3);

        assertThat(solvedWord.get(), is("dearly won"));
    }

    @Test
    public void invalidWordsDoNotCallApi() {
        Thesaurus thesaurus = mock(Thesaurus.class);
        DoubleDefinitionsClue doubleDefinitionClue = new DoubleDefinitionsClue(thesaurus);
        doubleDefinitionClue.solve("the a", null, 4);

        verify(thesaurus, never()).getAllSynonymsXML("the");
        verify(thesaurus, never()).getAllSynonymsXML("a");
    }

}
