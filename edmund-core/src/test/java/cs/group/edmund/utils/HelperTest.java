package cs.group.edmund.utils;


import org.junit.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelperTest {

    @Test
    public void removeDuplicatesFromArrayListTest() {
        ArrayList<String> list = new ArrayList<>(asList("one", "two", "one"));
        assertThat(list.size(), is(3));

        list = Helper.removeDuplicates(list);
        assertThat(list.size(), is(2));
    }

    @Test
    public void filterByAnswerLengthTest() {
        ArrayList<String> list = new ArrayList<>(asList("hello", "world", "welcome", "wereld"));
        ArrayList<String> filteredList = new ArrayList<>(asList("hello", "world"));

        assertThat(filteredList, is(Helper.filterByAnswerLength(list, new int[]{5})));
    }

    @Test
    public void filterByHintTest() {
        ArrayList<String> list = new ArrayList<>(asList("hello", "world", "hallo", "wereld"));
        ArrayList<String> filteredList = new ArrayList<>(asList("hello", "hallo"));

        assertThat(filteredList, is(Helper.filterByHint(list, "h...o")));
    }

    @Test
    public void filterAllTest() {
        ArrayList<String> list = new ArrayList<>(asList("hello", "world", "welcome", "wereld"));
        ArrayList<String> filteredList = new ArrayList<>(asList("hello"));

        assertThat(filteredList, is(Helper.filterAll(list, "h...o", new int[]{5})));
    }

    @Test
    public void removeSpecialCharAndSplitTest() {
        String word = "  Me remove you^?";
        ArrayList<String> list;
        list = Helper.removeSpecialChar(word);

        assertThat(list.contains("remove"), is(true));
        assertThat(list.contains("me"), is(true));
        assertThat(list.contains("you"), is(true));
    }
}
