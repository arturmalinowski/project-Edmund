package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class HiddenClueTest {

    private HiddenClue clue;
    private Thesaurus thesaurus;
    private Dictionary dictionary;

    @Before
    public void setup() {
        thesaurus = new Thesaurus();
        dictionary = new Dictionary();

    }

    @Test
    public void detectsThatClueIsAHiddenClue() {
        clue = new HiddenClue(thesaurus, dictionary);
        assertThat(clue.isRelevant("Time to take part in flower arranging"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAHiddenClue() {
        clue = new HiddenClue(thesaurus, dictionary);
        assertThat(clue.isRelevant("Yearn for quite a while"), is(false));
    }

    @Test
    public void hiddenClueTestCanBeSolved() {
        clue = new HiddenClue(thesaurus, dictionary);
        Optional<List<String>> solvedWord = clue.solve("Formerly of some concern", "", 4);

        assertThat(solvedWord.get().contains("once"), is(true));
    }

    @Test
    public void secondHiddenClueTestCanBeSolved() {
        clue = new HiddenClue(thesaurus, dictionary);
        Optional<List<String>> solvedWord = clue.solve("Jane was hiding again", "", 4);

        assertThat(solvedWord.get().get(0), is("anew"));
    }

    @Test
    @Table({@Row({"Time to take part in flower arranging", "era", "3", ".r."}),
            @Row({"Mislaid in Buffalo State", "lost", "4", ""}),
            @Row({"How some answers may be found in clues, some of which I'd denoted", "hidden", "6", ""}),
            @Row({"Composition from Bliss on a tape", "sonata", "6", ""}),
            @Row({"What's in Latin sign, if I can translate, is of no importance", "insignificant", "13", ""}),
            @Row({"Stop getting letters from friends", "end", "3", ""}),
            @Row({"Some teachers get hurt", "ache", "4", ""}),
            @Row({"Metal concealed by environmentalist", "iron", "4", ""}),
            @Row({"Hide in Arthur's kingdom", "skin", "4", ""}),
            @Row({"Who means to reveal where the heart is?", "home", "4", ""}),
            @Row({"Pole coming from Pakistan or Thailand", "north", "5", ""}),
            @Row({"Delia’s pickle contains jelly", "aspic", "5", ""}),
            @Row({"Brew some magic up pal", "cuppa", "5", ""}),
            @Row({"One lewdly desiring some bicycle chains?", "lech", "4", ""})})
    public void bulkClueTest(String crosswordClue, String clueAnswer, String answerLength, String hint) {
        clue = new HiddenClue(thesaurus, dictionary);
        Optional<List<String>> answer = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));

        assertThat(answer.get().contains(clueAnswer), is(true));
    }
}
