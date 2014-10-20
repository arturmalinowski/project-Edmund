package cs.group.edmund;

import cs.group.edmund.launch.EdmundRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static cs.group.edmund.fixtures.HttpClient.makeRequest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class EdmundIndexServletTest {

    private EdmundRunner edmund;

    @Before
    public void setUpTestEnvironment() {
        edmund = new EdmundRunner(15102);
    }

    @After
    public void tearDownTestEnvironment() {
        edmund.stopServer();
    }

    @Test
    public void edmundIndexServletReturnsValidHtml() {
        assertThat(makeRequest("http://localhost:15102/edmund"), containsString("Just ask Edmund!"));
    }

}
