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
}
