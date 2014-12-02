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
public class DeletionClueTest {

    private DeletionClue clue;
    private Thesaurus thesaurus;

    @Before
    public void setup() {
        clue = new DeletionClue();
        thesaurus = new Thesaurus();
    }

    @Ignore
    @Test
    @Table({
            // Beheadments
            @Row({"Beheaded celebrity is sailor", "tar", "3"}), // "celebrity" -> "star"
            @Row({"First off mobilize supporter", "ally", "4"}), // "mobilize" -> "rally"
            @Row({"Work the land, without first limb", "arm", "3"}), // "work the land" -> "farm"
            @Row({"Very happy to be associated with dropping introduction", "elated", "6"}), // "to be associated" -> "related"

            // Curtailments
            @Row({"Shout read endlessly", "boo", "3"}), // "read" -> "book"
            @Row({"Vehicle backing away from wagon", "car", "3"}), // "wagon" -> "cart"

            // Internal Deletion
            @Row({"Challenging sweetheart heartlessly", "daring", "6"}), // "sweetheart" -> "darling"
            @Row({"Disheartened tinker making a row", "tier", "4"}) // "tinker"
    })
    public void bulkClueTest(String crosswordClue, String clueAnswer, String answerLength) {
        String answer = clue.solve(crosswordClue, null, Integer.parseInt(answerLength));
        assertThat(answer, is(clueAnswer));
    }

    @Test
    public void testGetKeyword() {
        String phrase = "";
    }
}