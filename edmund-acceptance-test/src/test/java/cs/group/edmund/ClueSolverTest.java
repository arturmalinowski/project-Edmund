package cs.group.edmund;

import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.*;
import cs.group.edmund.launch.EdmundRunner;
import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(SpecRunner.class)
public class ClueSolverTest extends TestState {

    private EdmundRunner edmundRunner;

    @Ignore
    @Test
    public void userIsReturnedCorrectAnswerForItsGivenClue() throws Exception {
        given(edmundIsRunning());

        when(theUserHasInputtedTheClue("Physician brings fish around"));

        then(edmundReturns(), theAnswer("doc"));
    }

    private Matcher<? super String> theAnswer(String answer) {
        return null;
    }

    private GivensBuilder edmundIsRunning() {
        return new GivensBuilder() {
            @Override
            public InterestingGivens build(InterestingGivens interestingGivens) throws Exception {
                edmundRunner = new EdmundRunner(10200);
                return interestingGivens;
            }
        };
    }

    private ActionUnderTest theUserHasInputtedTheClue(String clue) {
        return new ActionUnderTest() {
            @Override
            public CapturedInputAndOutputs execute(InterestingGivens interestingGivens, CapturedInputAndOutputs capturedInputAndOutputs) throws Exception {
                return null;
            }
        };
    }

    private StateExtractor<String> edmundReturns() {
        return new StateExtractor<String>() {
            @Override
            public String execute(CapturedInputAndOutputs capturedInputAndOutputs) throws Exception {
                return null;
            }
        };
    }

}
