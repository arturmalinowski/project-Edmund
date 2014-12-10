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

    // Currently 30% success ratio
    //@Ignore
    @Test
    @Table({
            // Beheadments
            @Row({"Beheaded celebrity is sailor", "tar", "3", "....", "head"}), // "celebrity" -> "star" (works)
            //@Row({"First off mobilize supporter", "ally", "4", "....", "head"}), // "mobilize" -> "rally"
            //@Row({"Work the land without first limb", "arm", "3", "....", "head"}), // "work the land" -> "farm"
            //@Row({"Very happy to be associated with dropping introduction", "elated", "6", "....", "head"}), // "to be associated" -> "related"
            @Row({"Head off champion worker", "artisan", "7", "....", "head"}), // "champion" -> "partisan" (works)
            //@Row({"Suggest not starting in a flabby way", "imply", "5", "....", "head"}), // "in a flabby way" -> "limply"

            // Curtailments
            @Row({"Shout read endlessly", "boo", "3", "....", "tail"}), // "read" -> "book" (works)
            //@Row({"Vehicle backing away from wagon", "car", "3", "....", "tail"}), // "wagon" -> "cart"
            //@Row({"Circuit almost falling", "lap", "4", "....", "tail"}), // "almost" -> "lapse"

            // Internal Deletion
            @Row({"Challenging sweetheart heartlessly", "daring", "6", "....", "middle"}), // "sweetheart" -> "darling"
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
            @Row({"Challenging sweetheart heartlessly", "daring", "6", "....", "middle"}), // "sweetheart" -> "darling"
            //@Row({"Disheartened tinker making a row", "tier", "4", "....", "middle"}), // "tinker"
            //@Row({"Dull speeches hollow assurances", "proses", "5", ".......", "middle"}) // "assurances" -> "promises"
    })
    public void bulkClueCloseTest(String crosswordClue, String clueAnswer, String answerLength, String hint, String deletionType) {
        String answer = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));
        assertThat((answer.contains(clueAnswer)), is(true));
    }

    //@Ignore
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
        @Row({"sailor", "tar", "celebrity", "star"}),
        @Row({"supporter", "ally", "mobilize", "rally"}),
        @Row({"limb", "arm", "work the land", "farm"}),
        @Row({"very happy", "elated", "to be associated with", "related"}),
        @Row({"worker", "artisan", "champion", "partisan"}),
        @Row({"suggest", "imply", "in a flabby way", "limply"}),

        // Curtailments
        @Row({"shout", "boo", "read", "book"}),
        @Row({"vehicle", "car", "wagon", "cart"}),
        @Row({"circuit", "lap", "almost", "lapse"}),

        // Internal Deletion
        @Row({"challenging", "daring", "sweetheart", "darling"}),
        @Row({"assurance", "promise", "dull speech", "prose"})
    })
    public void returnExpectedSynonym(String word1, String expectedSynonym1, String word2, String expectedSynonym2) {
        //Beheaded celebrity is sailor -> tar
        Boolean wordFound1 = false;
        Boolean wordFound2 = false;

        ArrayList<String> synonymsList1 = thesaurus.getAllSynonymsXML(word1);
        ArrayList<String> relatedWords1 = thesaurus.getRelatedWordsXML(word1);

        ArrayList<String> fullList1 = new ArrayList<String>();
        fullList1.addAll(synonymsList1);
        fullList1.addAll(relatedWords1);
        fullList1 = helper.removeDuplicates(fullList1);

        if (fullList1.contains(expectedSynonym1)) {
            wordFound1 = true;
        }
        else {
            fullList1.addAll(getRelatedWordSynonyms(synonymsList1));
            fullList1 = helper.removeDuplicates(fullList1);

            if (fullList1.contains(expectedSynonym1)) {
                wordFound1 = true;
            }
        }

        ArrayList<String> synonymsList2 = thesaurus.getAllSynonymsXML(word2);
        ArrayList<String> relatedWords2 = thesaurus.getRelatedWordsXML(word2);

        ArrayList<String> fullList2 = new ArrayList<String>();
        fullList2.addAll(synonymsList2);
        fullList2.addAll(relatedWords2);
        fullList2 = helper.removeDuplicates(fullList2);

        if (fullList2.contains(expectedSynonym2)) {
            wordFound2 = true;
        }
        else {
            fullList2.addAll(getRelatedWordSynonyms(synonymsList2));
            fullList2 = helper.removeDuplicates(fullList2);

            if (fullList2.contains(expectedSynonym2)) {
                wordFound2 = true;
            }
        }

        assertThat(true, is(wordFound1 && wordFound2));
    }
}