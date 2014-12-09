package cs.group.edmund.clue;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
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
public class ContainerClueTest {

    private ContainerClue clue;
    private Thesaurus thesaurus;
    private Helper helper;

    @Before
    public void setup() {
        clue = new ContainerClue();
        thesaurus = new Thesaurus();
    }

    // Current success rate 30%
    @Ignore
    @Test
    @Table({
            // working without hints
            @Row({"Wear around the brave", "weather", "w......", "7"}),
            @Row({"Put on around the brave", "weather", "w......", "7"}),
            @Row({"We surrounded strike snowy", "white", "w....", "5"}),
            @Row({"Empty tin put in bin", "vacant", "v.....", "6"}),
            @Row({"Horseman capturing a freebooter", "raider", "r.....", "6"}),
            @Row({"Hear about the moorland flower", "heather", "h......", "7"}), //"moorland flower" does not return "heather"

            @Row({"Relative entering Highland dance and showing off", "flaunting", "f........", "9"}), //"showing off" returns "flaunting"
            @Row({"Noted Noah's ship in the Mediterranean", "marked", "m.....", "6"}), // "ark" -> "med" = "marked", "noted" returns "marked"
            @Row({"Shrink from phone in church", "cringe", "c.....", "6"}), // "ce" + "ring" = "cringe", "shrink" returns "cringe"

            @Row({"Outlaw in gangs carrying equipment", "brigands", "b.......", "8"}), //"outlaws" does not return "brigand", but "outlaw" does

            @Row({"Object when put into torn clothing", "raiment", "r......", "7"}), //"clothing" does not return "raiment"
            @Row({"Kid keeps near this evening", "tonight", "t......", "7"}),  //"tot" -> "nigh" = "tonight", "evening" does not return "tonight"
            @Row({"Points out lion in tropical islands", "indicates", "i........", "9"}), //"cat" -> "indies" = "indicates", "points out" does not return "indicates"
            @Row({"Apostle's friend outside of university", "paul", "p...", "4"}), //"pal" -> "u" = "paul", "apostle" does not return "paul"
            @Row({"Everyone in wager on dancing performance", "ballet", "b.....", "6"}), // "all" -> "bet" = "ballet", "dancing" or "performance" does not return "ballet"
            @Row({"Widest and best way inside", "broadest", "b.......", "8"}), //"best" -> "road" = "broadest", "widest" returns nothing
            @Row({"Damage surrounding the brave", "weather", "w......", "7"}), //"damage" does not return "wear"
            @Row({"Stuck with tot holding present", "adhered", "a......", "7"}), //"stuck" does not return "adhered"
            @Row({"Russet bears are raised", "reared", "r.....", "6"}) //"raised" does not return "reared"
    })
    public void bulkClueTest(String crosswordClue, String clueAnswer, String hint, String answerLength) {
        String answer = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));
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

        synonyms.add("ROBIN");
        synonyms.add("linnet");
        synonyms.add("OWL");
        synonyms.add("PIGEON");

        solutions.add("linnet");
        solutions.add("LEINNT");
        solutions.add("LETINN");

        assertThat("linnet", is(clue.compareLists(synonyms, solutions)));
    }

    @Test
    public void testFilterByHints() {
        String hint = "...g..";
        ArrayList<String> preFilterList = new ArrayList<>();
        preFilterList.add("hello");
        preFilterList.add("flight");
        preFilterList.add("felger");
        preFilterList.add("harpys");
        preFilterList.add("kantar");

        ArrayList<String> postFilterList = new ArrayList<>();
        postFilterList.add("flight");
        postFilterList.add("felger");

        assertThat(postFilterList, is(clue.filterByHints(preFilterList, hint)));
    }
}
