package cs.group.edmund;

import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.*;
import cs.group.edmund.launch.EdmundRunner;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@RunWith(SpecRunner.class)
public class ClueSolverTest extends TestState {

    private EdmundRunner edmundRunner;
    private ResponseEntity<String> response;

    @After
    public void tearDown() {
        edmundRunner.stopEdmund();
    }

    @Test
    public void userIsReturnedCorrectAnswerForItsGivenClue() throws Exception {
        given(edmundIsRunning());

        when(theUserHasInputtedTheClue("Physician brings fish around"));

        then(edmundReturns(), theAnswer("doc"));
    }

    private GivensBuilder edmundIsRunning() {
        return new GivensBuilder() {
            @Override
            public InterestingGivens build(InterestingGivens interestingGivens) throws Exception {
                edmundRunner = new EdmundRunner();
                edmundRunner.startEdmund();
                return interestingGivens;
            }
        };
    }

    private ActionUnderTest theUserHasInputtedTheClue(String clue) {
        return new ActionUnderTest() {
            @Override
            public CapturedInputAndOutputs execute(InterestingGivens interestingGivens, CapturedInputAndOutputs capturedInputAndOutputs) throws Exception {
                String url = String.format("http://localhost:9090/solve?clue=%s&hint=&length=3", clue);
                response = new RestTemplate().getForEntity(url, String.class);
                capturedInputAndOutputs.add("URL: ", url);
                return capturedInputAndOutputs;
            }
        };
    }

    private StateExtractor<String> edmundReturns() {
        return new StateExtractor<String>() {
            @Override
            public String execute(CapturedInputAndOutputs capturedInputAndOutputs) throws Exception {
                capturedInputAndOutputs.add("Response from Edmund: ", response.getBody());
                capturedInputAndOutputs.add("Response code: ", response.getStatusCode());
                return response.getBody();
            }
        };
    }

    private Matcher<? super String> theAnswer(String answer) {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String s) {
                return s.contains(answer);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(answer);
            }
        };
    }

}
