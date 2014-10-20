package cs.group.edmund.utils;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DictionaryTest {

    private static Dictionary dictionary;

    @BeforeClass
    public static void setup() {
        dictionary = new Dictionary();
    }

    @Test
    public void dictionaryCanReadFromFile(){
        assertThat(dictionary.getWords().get(5), is("aardvark"));
    }

    @Test
    public void verifiesThatAWordIsValid() {
        assertThat(dictionary.validate("aardvark"), is(true));
    }

    @Test
    public void verifiesThatAWordIsInvalid() {
        assertThat(dictionary.validate("benqueath"), is(false));
    }

}
