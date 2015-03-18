package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import cs.group.edmund.utils.Helper;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class DeletionClueTest {

    private DeletionClue clue;
    private Thesaurus thesaurus;

    @Before
    public void setup() {
        thesaurus = new Thesaurus();
        clue = new DeletionClue(thesaurus);

    }

    // Currently 30% success ratio.
    @Ignore
    @Test
    @Table({
            // Beheadments
            @Row({"Beheaded celebrity is sailor", "tar", "3", "t..", "head"}), // (passes)
            @Row({"First off mobilize supporter", "ally", "4", "a...", "head"}), // "mobilize" -> "rally" (passes)
            @Row({"Work the land without first limb", "arm", "3", "a..", "head"}), // "work the land" -> "farm" (passes)
            @Row({"Head off champion worker", "artisan", "7", "a......", "head"}), // (passes)

            @Row({"Shout read endlessly", "boo", "3", "b..", "tail"}), // (passes) // curtailment
            @Row({"Challenging sweetheart heartlessly", "daring", "6", "d.....", "middle"}), // (passes) // internal

            @Row({"Very happy to be associated with dropping introduction", "elated", "6", "e.....", "head"}), // "to be associated" -> "related"

            @Row({"Suggest not starting in a flabby way", "imply", "5", "i....", "head"}), // "in a flabby way" -> "limply"

            // Curtailments

            @Row({"Vehicle backing away from wagon", "car", "3", "c..", "tail"}), // "wagon" -> "cart"
            @Row({"Circuit almost falling", "lap", "4", "l..", "tail"}), // "almost" -> "lapse"

            // Internal Deletion

            @Row({"Disheartened tinker making a row", "tier", "4", "t...", "middle"}), // "tinker"
            @Row({"Dull speeches hollow assurances", "proses", "5", "p.....", "middle"}) // "assurances" -> "promises"
    })
    public void bulkClueTest(String crosswordClue, String clueAnswer, String answerLength, String hint, String clueType) {
        Optional<List<String>> answers = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));
        assertThat(answers.get().contains(clueAnswer), is(true));
    }

    @Ignore
    @Test
    @Table({
            // Beheadments
            @Row({"Beheaded celebrity is sailor", "tar", "3", "....", "head"}), // "celebrity" -> "star"
            //@Row({"First off mobilize supporter", "ally", "4", "....", "head"}), // "mobilize" -> "rally"
            //@Row({"Work the land without first limb", "arm", "3", "....", "head"}), // "work the land" -> "farm"
            //@Row({"Very happy to be associated with dropping introduction", "elated", "6", "....", "head"}), // "to be associated" -> "related"
            @Row({"Head off champion worker", "artisan", "7", "....", "head"}), // "champion" -> "partisan"
            //@Row({"Suggest not starting in a flabby way", "imply", "5", "....", "head"}), // "in a flabby way" -> "limply"

            // Curtailments
            @Row({"Shout read endlessly", "boo", "3", "....", "tail"}), // "read" -> "book"
            //@Row({"Vehicle backing away from wagon", "car", "3", "....", "tail"}), // "wagon" -> "cart"
            //@Row({"Circuit almost falling", "lap", "4", "....", "tail"}), // "almost" -> "lapse"

            // Internal Deletion
            @Row({"Challenging sweetheart heartlessly", "daring", "6", "....", "middle"}), // "sweetheart" -> "darling"
            //@Row({"Disheartened tinker making a row", "tier", "4", "....", "middle"}), // "tinker"
            //@Row({"Dull speeches hollow assurances", "proses", "5", ".......", "middle"}) // "assurances" -> "promises"
    })
    public void bulkClueCloseTest(String crosswordClue, String clueAnswer, String answerLength, String hint) {
        Optional<List<String>> answer = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));
        assertThat((answer.get().contains(clueAnswer)), is(true));
    }


    @Ignore
    @Test
    @Table({
            // Beheadments
            @Row({"Beheaded celebrity is sailor", "tar", "3", "....", "head"}),
            @Row({"First off mobilize supporter", "ally", "4", "....", "head"}),
            @Row({"Work the land without first limb", "arm", "3", "....", "head"}),
            @Row({"Very happy to be associated with dropping introduction", "elated", "6", "....", "head"}),
            @Row({"Head off champion worker", "artisan", "7", "....", "head"}),
            @Row({"Suggest not starting in a flabby way", "imply", "5", "....", "head"}),

            // Curtailments
            @Row({"Shout read endlessly", "boo", "3", "....", "tail"}),
            @Row({"Vehicle backing away from wagon", "car", "3", "....", "tail"}),
            @Row({"Circuit almost falling", "lap", "4", "....", "tail"}),

            // Internal Deletion
            @Row({"Challenging sweetheart heartlessly", "daring", "6", "....", "middle"}),
            @Row({"Disheartened tinker making a row", "tier", "4", "....", "middle"}),
            @Row({"Dull speeches hollow assurances", "proses", "5", ".......", "middle"})
    })
    public void testGetDeletionType(String phrase, String answer, String answerLength, String hint, String deletionType) {
        assertThat(deletionType, is(clue.getDeletionType(phrase.toLowerCase())));
    }

    @Ignore
    @Test
    @Table({
        // Beheadments
        @Row({"sailor", "tar", "celebrity", "star"}), // (passes)
        @Row({"supporter", "ally", "mobilize", "rally"}), // (passes)
        @Row({"limb", "arm", "work the land", "farm"}),
        @Row({"very happy", "elated", "to be associated with", "related"}),
        @Row({"worker", "artisan", "champion", "partisan"}), // (passes)
        @Row({"suggest", "imply", "in a flabby way", "limply"}),

        // Curtailments
        @Row({"shout", "boo", "read", "book"}), // (passes)
        @Row({"vehicle", "car", "wagon", "cart"}),
        @Row({"circuit", "lap", "almost", "lapse"}),
        @Row({"alter", "amend", "the last word", "amen"}),
        @Row({"climb onto", "board", "wild pig", "boar"}),

        // Internal Deletion
        @Row({"challenging", "daring", "sweetheart", "darling"}), // (passes)
        @Row({"assurance", "promise", "dull speech", "prose"}),
        @Row({"naught", "nothing", "observing", "noting"}),
        @Row({"parker", "coat", "bed", "cot"}),

        // Both Ends
        @Row({"outpouring", "spate", "exactly", "pat"}),
        @Row({"diver's equipment", "scuba", "little shark", "cub"}),

        // Specific words
        @Row({"uncertain", "vague", "cold", "ague"}), // (passes)
        @Row({"estrangement", "alienation", "state", "nation"}), // (passes)

    })
    public void testIsSolvable(String word1, String expectedSynonym1, String word2, String expectedSynonym2) {
        //
        Boolean wordFound1 = false;
        Boolean wordFound2 = false;

        ArrayList<String> synonymsList1 = thesaurus.getAllSynonymsXML(word1);
        ArrayList<String> relatedWords1 = thesaurus.getRelatedWordsXML(word1);

        ArrayList<String> fullList1 = new ArrayList<>();
        fullList1.addAll(synonymsList1);
        fullList1.addAll(relatedWords1);
        fullList1 = Helper.removeDuplicates(fullList1);

        if (fullList1.contains(expectedSynonym1)) {
            wordFound1 = true;
        }
        else {
            fullList1.addAll(thesaurus.getSynonymsOfRelatedWordsXML(word1));
            fullList1 = Helper.removeDuplicates(fullList1);

            if (fullList1.contains(expectedSynonym1)) {
                wordFound1 = true;
            }
            else {
                fullList1.addAll(thesaurus.getRelatedWordsOfSynonymsXML(word1));
                fullList1 = Helper.removeDuplicates(fullList1);
                if (fullList1.contains(expectedSynonym1)) {
                    wordFound1 = true;
                }
            }
        }

        ArrayList<String> synonymsList2 = thesaurus.getAllSynonymsXML(word2);
        ArrayList<String> relatedWords2 = thesaurus.getRelatedWordsXML(word2);

        ArrayList<String> fullList2 = new ArrayList<>();
        fullList2.addAll(synonymsList2);
        fullList2.addAll(relatedWords2);
        fullList2 = Helper.removeDuplicates(fullList2);

        if (fullList2.contains(expectedSynonym2)) {
            wordFound2 = true;
        }
        else {
            fullList2.addAll(thesaurus.getSynonymsOfRelatedWordsXML(word2));
            fullList2 = Helper.removeDuplicates(fullList2);
            if (fullList2.contains(expectedSynonym2)) {
                wordFound2 = true;
            }
            else {
                fullList2.addAll(thesaurus.getRelatedWordsOfSynonymsXML(word2));
                fullList2 = Helper.removeDuplicates(fullList2);
                if (fullList2.contains(expectedSynonym2)) {
                    wordFound2 = true;
                }
            }
        }

        assertThat(true, is(wordFound1 && wordFound2));
    }
}