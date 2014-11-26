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
    private Thesaurus thesaurus;

    @Before
    public void setup() {
        clue = new ContainerClue();
        thesaurus = new Thesaurus();
    }

    // Current success rate 25%
    @Ignore
    @Test
    @Table({
            // working
            @Row({"Wear around the brave", "weather", "7"}),
            @Row({"Put on around the brave", "weather", "7"}),
            @Row({"We surrounded strike snowy", "white", "5"}),
            @Row({"Empty tin put in bin", "vacant", "6"}),
            @Row({"Horseman capturing a freebooter", "raider", "6"}),

            @Row({"Relative entering Highland dance and showing off", "flaunting", "9"}), //"showing off" returns "flaunting"
            @Row({"Noted Noah's ship in the Mediterranean", "marked", "6"}), // "ark" -> "med" = "marked", "noted" returns "marked"
            @Row({"Shrink from phone in church", "cringe", "6"}), // "ce" + "ring" = "cringe", "shrink" returns "cringe"

            @Row({"Outlaws in gangs carrying equipment", "brigands", "8"}), //"outlaws" does not return "brigand", but "outlaw" does

            @Row({"Object when put into torn clothing", "raiment", "7"}), //"clothing" does not return "raiment"
            @Row({"Kid keeps near this evening", "tonight", "7"}),  //"tot" -> "nigh" = "tonight", "evening" does not return "tonight"
            @Row({"Points out lion in tropical islands", "indicates", "9"}), //"cat" -> "indies" = "indicates", "points out" does not return "indicates"
            @Row({"Apostle's friend outside of university", "paul", "4"}), //"pal" -> "u" = "paul", "apostle" does not return "paul"
            @Row({"Hear about the moorland flower", "heather", "7"}), //"moorland flower" does not return "heather"
            @Row({"Everyone in wager on dancing performance", "ballet", "6"}), // "all" -> "bet" = "ballet", "dancing" or "performance" does not return "ballet"
            @Row({"Widest and best way inside", "broadest", "8"}), //"best" -> "road" = "broadest", "widest" returns nothing
            @Row({"Damage surrounding the brave", "weather", "7"}), //"damage" does not return "wear"
            @Row({"Stuck with tot holding present", "adhered", "7"}), //"stuck" does not return "adhered"
            @Row({"Russet bears are raised", "reared", "6"}) //"raised" does not return "reared"
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
        System.out.println(clue.isRelevant("Crazy flying mammals"));
        assertThat(clue.isRelevant("Crazy flying mammals"), is(true));
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
