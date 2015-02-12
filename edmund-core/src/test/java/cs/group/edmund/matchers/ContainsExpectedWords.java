package cs.group.edmund.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ContainsExpectedWords extends TypeSafeMatcher<String> {

    private String expectedListValue;

    public ContainsExpectedWords(String expected) {
        expectedListValue = expected;
    }

    @Override
    protected boolean matchesSafely(String s) {
        return s.contains(expectedListValue);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("list containing words " + expectedListValue);
    }

    @Factory
    public static Matcher<String> containsWords(String expected) {
        return new ContainsExpectedWords(expected);
    }
}
