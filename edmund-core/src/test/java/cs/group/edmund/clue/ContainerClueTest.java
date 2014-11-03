package cs.group.edmund.clue;

import cs.group.edmund.utils.Thesaurus;
import org.junit.Ignore;
import org.junit.Test;
import java.util.List;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContainerClueTest {

    @Test
    public void detectsThatClueIsAnContainer() {
        Clue clue = new ContainerClue();
        assertThat(clue.isRelevant("Bird allowed outside tavern"), is(true));
    }

    @Test
    public void detectsThatClueIsNotAContainer() {
        Clue clue = new ContainerClue();
        assertThat(clue.isRelevant("Crazy flying mammals"), is(false));
    }

    @Ignore
    @Test
    public void containerClueTestCanBeSolved() {
        Clue clue = new ContainerClue();
        String solvedWord = clue.solve("Bird allowed outside tavern", 6);

        assertThat(solvedWord, is("linnet"));
    }

    @Test
    public void testGetKeyword() {
        ContainerClue clue = new ContainerClue();
        assertThat(clue.getKeyword("BIRD ALLOWED OUTSIDE TAVERN"), is("OUTSIDE"));
    }

    @Test
    public void testGetSideWords() {
        ContainerClue clue = new ContainerClue();

        String phrase = "BIRD ALLOWED OUTSIDE TAVERN";
        ArrayList<String> list = new ArrayList<String>();
        list.add("BIRD");
        list.add("TAVERN");

        assertThat(clue.getSideWords(phrase), is(list));
    }

    @Test
    public void testSplitPhrase() {
        ContainerClue clue = new ContainerClue();

        String phrase = "BIRD ALLOWED OUTSIDE TAVERN";
        String keyword = "OUTSIDE";
        ArrayList<String> list = new ArrayList<String>();
        list.add("BIRD ALLOWED");
        list.add("TAVERN");

        assertThat(clue.splitPhrase(phrase,keyword), is(list));
    }

    @Test
    public void testGetLeftRightWords() {
        ContainerClue clue = new ContainerClue();

        String leftHalf = "ALLOWED";
        String rightHalf = "TAVERN";

        ArrayList<String> list = new ArrayList<String>();
        list.add("ALLOWED");
        list.add("TAVERN");


        assertThat(clue.getLeftRightWords(leftHalf, rightHalf), is(list));
    }

    @Test
    public void testCompareLists() {
        ContainerClue clue = new ContainerClue();

        ArrayList<String> synonyms = new ArrayList<String>();
        ArrayList<String> solutions = new ArrayList<String>();
        synonyms.add("ROBIN");
        synonyms.add("LINNET");
        synonyms.add("OWL");
        synonyms.add("PIGEON");
        solutions.add("LINNET");
        solutions.add("LEINNT");
        solutions.add("LETINN");

        assertThat("LINNET", is(clue.compareLists(synonyms, solutions)));
    }

    @Test
    @Ignore
    public void testReturnContainedWords() {
        ContainerClue clue = new ContainerClue();
        Thesaurus thesaurus = new Thesaurus();

        ArrayList<String> leftSynonyms = (ArrayList<String>) thesaurus.getAllSynonyms("SON");
        ArrayList<String> rightSynonyms = (ArrayList<String>) thesaurus.getAllSynonyms("MARY");
        leftSynonyms.add("SON");
        rightSynonyms.add("MARY");

        System.out.println(clue.returnContainedWords(leftSynonyms, rightSynonyms));
        System.out.println(leftSynonyms);
        System.out.println(rightSynonyms);
        //String rightWord = "mary";
        //String leftWord = "boy";
        //ArrayList<String> list = new ArrayList<String>();
        //list.add(leftWord + rightWord);
        //for (int i = 1; i < rightWord.length(); i++) {
            //list.add(rightWord.substring(0, i).trim() + leftWord.trim() + rightWord.substring(i));
        //}
        //list.add(rightWord.substring(0, 1).trim() + leftWord.trim() + rightWord.substring(1).trim());
        //list.add(rightWord.substring(0, 2).trim() + leftWord.trim() + rightWord.substring(2).trim());
        //list.add(rightWord.substring(0, 3).trim() + leftWord.trim() + rightWord.substring(3).trim());
        //list.add(rightWord + leftWord);
        //System.out.println(list);
    }

    @Ignore
    @Test
    public void testGetSolutions() {
        ContainerClue clue = new ContainerClue();
        Thesaurus thesaurus = new Thesaurus();

        String leftHalf = "BIRD ALLOWED";
        String rightHalf = "TAVERN";

        // TO COMPLETE
    }
}
