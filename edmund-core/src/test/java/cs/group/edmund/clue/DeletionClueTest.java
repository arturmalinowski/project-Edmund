package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import com.sun.org.apache.xpath.internal.operations.Bool;
import cs.group.edmund.utils.Thesaurus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import cs.group.edmund.utils.Helper;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class DeletionClueTest {

    private DeletionClue clue;
    private Thesaurus thesaurus;
    private Helper helper;

    @Before
    public void setup() {
        clue = new DeletionClue();
        thesaurus = new Thesaurus();
        helper = new Helper();
    }

    // Currently 25% success ratio
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
            //@Row({"Challenging sweetheart heartlessly", "daring", "6", "....", "middle"}), // "sweetheart" -> "darling"
            //@Row({"Disheartened tinker making a row", "tier", "4", "....", "middle"}), // "tinker"
            //@Row({"Dull speeches hollow assurances", "proses", "5", ".......", "middle"}) // "assurances" -> "promises"
    })
    public void bulkClueTest(String crosswordClue, String clueAnswer, String answerLength, String hint, String deletionType) {
        String answer = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));
        assertThat(answer, is(clueAnswer));
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
            //@Row({"Challenging sweetheart heartlessly", "daring", "6", "....", "middle"}), // "sweetheart" -> "darling"
            //@Row({"Disheartened tinker making a row", "tier", "4", "....", "middle"}), // "tinker"
            //@Row({"Dull speeches hollow assurances", "proses", "5", ".......", "middle"}) // "assurances" -> "promises"
    })
    public void bulkClueCloseTest(String crosswordClue, String clueAnswer, String answerLength, String hint, String deletionType) {
        String answer = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));
        assertThat((answer.contains(clueAnswer)), is(true));
    }

    @Ignore
    @Test
    @Table({
            // Beheadments
            @Row({"Beheaded celebrity is sailor", "tar", "3", "....", "head"}), // "celebrity" -> "star"
            @Row({"First off mobilize supporter", "ally", "4", "....", "head"}), // "mobilize" -> "rally"
            @Row({"Work the land without first limb", "arm", "3", "....", "head"}), // "work the land" -> "farm"
            @Row({"Very happy to be associated with dropping introduction", "elated", "6", "....", "head"}), // "to be associated" -> "related"
            @Row({"Head off champion worker", "artisan", "7", "....", "head"}), // "champion" -> "partisan"
            @Row({"Suggest not starting in a flabby way", "imply", "5", "....", "head"}), // "in a flabby way" -> "limply"

            // Curtailments
            @Row({"Shout read endlessly", "boo", "3", "....", "tail"}), // "read" -> "book"
            @Row({"Vehicle backing away from wagon", "car", "3", "....", "tail"}), // "wagon" -> "cart"
            @Row({"Circuit almost falling", "lap", "4", "....", "tail"}), // "almost" -> "lapse"

            // Internal Deletion
            @Row({"Challenging sweetheart heartlessly", "daring", "6", "....", "middle"}), // "sweetheart" -> "darling"
            @Row({"Disheartened tinker making a row", "tier", "4", "....", "middle"}), // "tinker"
            @Row({"Dull speeches hollow assurances", "proses", "5", ".......", "middle"}) // "assurances" -> "promises"
    })
    public void testGetDeletionType(String phrase, String answer, String answerLength, String hint, String deletionType) {
        assertThat(deletionType, is(clue.getDeletionType(phrase.toLowerCase())));
    }

    public ArrayList<String> getRelatedWordSynonyms(ArrayList<String> synonyms) {
        ArrayList<String> relatedList = new ArrayList<String>();
        for (String word : synonyms) {
            relatedList.addAll(thesaurus.getRelatedWordsXML(word));
        }
        return relatedList;
    }

    @Ignore
    @Test
    @Table({
        // Beheadments
        @Row({"sailor", "tar"}),
        @Row({"celebrity", "star"}),
        @Row({"supporter", "ally"}),
        @Row({"mobilize", "rally"}),
        @Row({"limb", "arm"}),
        @Row({"work the land", "farm"}),
        @Row({"very happy", "elated"}),
        @Row({"to be associated with", "related"}),
        @Row({"worker", "artisan"}),
        @Row({"champion", "partisan"}),
        @Row({"suggest", "imply"}),
        @Row({"in a flabby way", "limply"}),

        // Curtailments
        @Row({"shout", "boo"}),
        @Row({"read", "book"}),
        @Row({"vehicle", "car"}),
        @Row({"wagon", "cart"}),
        @Row({"circuit", "lap"}),
        @Row({"almost", "lapse"}),

        // Internal Deletion
        @Row({"challenging", "daring"}),
        @Row({"sweetheart", "darling"}),
        @Row({"assurance", "promise"}),
        @Row({"dull speech", "prose"})
    })
    public void returnExpectedSynonym(String word, String expectedSynonym) {
        //Beheaded celebrity is sailor -> tar
        String expectedWord = expectedSynonym;
        Boolean wordFound = false;

        ArrayList<String> synonymsList = thesaurus.getAllSynonymsXML(word);
        ArrayList<String> relatedWords = thesaurus.getRelatedWordsXML(word);

        ArrayList<String> fullList = new ArrayList<String>();
        fullList.addAll(synonymsList);
        fullList.addAll(relatedWords);
        fullList = helper.removeDuplicates(fullList);

        if (fullList.contains(expectedWord)) {
            wordFound = true;
        }
        else {
            fullList.addAll(getRelatedWordSynonyms(synonymsList));
            fullList = helper.removeDuplicates(fullList);

            if (fullList.contains(expectedWord)) {
                wordFound = true;
            }
        }
        assertThat(true, is(wordFound));
    }
}