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
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(TableRunner.class)
public class ContainerClueTest {

    private ContainerClue clue;

    @Before
    public void setup() {
        clue = new ContainerClue(new Thesaurus());
    }

    // Current success rate 33%
    @Ignore
    @Test
    @Table({
            //@Row({"Going round stronghold, take a look", "teak", "....", "4"}),
            // working
            @Row({"Wear around the brave", "weather", "w......", "7"}), // (passes)
            @Row({"Put on around the brave", "weather", "w......", "7"}), // (passes)
            @Row({"We surrounded strike snowy", "white", "w....", "5"}), // (passes)
            @Row({"Empty tin put in bin", "vacant", "v.....", "6"}), // (passes)
            @Row({"Horseman capturing a freebooter", "raider", "r.....", "6"}), // (passes)
            @Row({"Hear about the moorland flower", "heather", "h......", "7"}), // (passes)
            @Row({"Everyone in wager on dancing performance", "ballet", "b.....", "6"}), // (passes)
            @Row({"Attire clothing the brave", "weather", "w......", "7"}), // (passes)
            @Row({"Stash or put in stage", "storage", "s......", "7"}), // (passes)
            @Row({"Make a change and put me in last", "emend", "e....", "5"}), // (passes)

            @Row({"Superman retains interest in Painter", "titian", "t.....", "6"}),
            @Row({"Everything in the broadcast is superficial", "shallow", "s......", "7"}),
            @Row({"Hospital residents make knots in trousers", "patients", "p.......", "8"}),
            @Row({"Cooked meat in the oven - uncooked outside it?", "roasted", "r......", "7"}),

            @Row({"Mixed a boy’s greens", "salad", "s....", "5"}),
            @Row({"South in motionless flyer", "insert", "i.....", "6"}),
            @Row({"Sink, as in rubbish container", "basin", "b....", "5"}),
            @Row({"Contented and very quiet, cutting hay", "happy", "h....", "5"}),
            @Row({"Quick! Sieve, including tungsten!", "swift", "s....", "5"}),
            @Row({"Find a doctor getting to grips with garbled voices", "discover", "d.......", "8"}),
            @Row({"Shave back in south eastern Mexican shawl", "serape", "s.....", "6"}),
            @Row({"Relative entering Highland dance and showing off", "flaunting", "f........", "9"}), //"showing off" returns "flaunting"
            @Row({"Noted Noah's ship in the Mediterranean", "marked", "m.....", "6"}), // "ark" -> "med" = "marked", "noted" returns "marked"
            @Row({"Shrink from phone in church", "cringe", "c.....", "6"}), // "ce" + "ring" = "cringe", "shrink" returns "cringe"
            @Row({"Outlaw in gangs carrying equipment", "brigands", "b.......", "8"}), //"outlaws" does not return "brigand", but "outlaw" does
            @Row({"Object when put into torn clothing", "raiment", "r......", "7"}), //"clothing" does not return "raiment"
            @Row({"Points out lion in tropical islands", "indicates", "i........", "9"}), //"cat" -> "indies" = "indicates", "points out" does not return "indicates"
            @Row({"Apostle's friend outside of university", "paul", "p...", "4"}), //"pal" -> "u" = "paul", "apostle" does not return "paul"
            @Row({"Widest and best way inside", "broadest", "b.......", "8"}), //"best" -> "road" = "broadest", "widest" returns nothing
            @Row({"Damage surrounding the brave", "weather", "w......", "7"}), //"damage" does not return "wear"
            @Row({"Stuck with tot holding present", "adhered", "a......", "7"}), //"stuck" does not return "adhered"
            @Row({"Russet bears are raised", "reared", "r.....", "6"}) //"raised" does not return "reared"
    })
    public void bulkClueTest(String crosswordClue, String clueAnswer, String hint, String answerLength) {
        Optional<List<String>> answers = clue.solve(crosswordClue, hint, Integer.parseInt(answerLength));
        assertThat(answers.get().contains(clueAnswer), is(true));
    }

    @Test
    public void detectsThatClueIsAnContainer() {
        assertThat(clue.isRelevant("Bird allowed outside tavern"), is(true));
    }

    @Test
    public void testGetKeyword() {
        assertThat(clue.getKeyword("bird allowed outside tavern"), is("outside"));
    }

    @Test
    public void testSplitPhrase() { assertThat(clue.splitPhrase("bird allowed outside tavern", "outside"), is(new ArrayList<>(asList("bird allowed", "tavern")))); }

    @Test
    public void testCompareLists() { assertThat(new ArrayList<>(asList("linnet")), is(clue.compareLists(new ArrayList<>(asList("robin", "linnet", "owl", "pigeon")), new ArrayList<>(asList("linnet", "leinnt", "letinn"))))); }

    @Test
    public void testFilterLargerWords() {
        ArrayList<String> list = new ArrayList<>(asList("hell", "helios", "abraham"));
        ArrayList<String> answerList = new ArrayList<>(asList("hell"));

        assertThat(answerList, is(clue.filterLargerWords(list,4)));
    }
}
