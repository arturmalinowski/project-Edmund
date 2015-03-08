package cs.group.edmund.clue.controllers;

import cs.group.edmund.typeSelector.Selector;
import cs.group.edmund.utils.Thesaurus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Configuration
@RestController
public class SolverController {

    @Autowired
    private final Selector selector;

    @Autowired
    private final Thesaurus thesaurus;

    public SolverController(Selector selector, Thesaurus thesaurus) {
        this.selector = selector;
        this.thesaurus = thesaurus;
    }

    @RequestMapping(value = "solve", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<List<String>> solve(@RequestParam("clue") String clue,
                                       @RequestParam("hint") String hint,
                                       @RequestParam("length") int length) {
        try {
            List<String> answer = selector.retrieveAnswer(clue, hint, length, thesaurus);
            return new ResponseEntity<>(answer, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}