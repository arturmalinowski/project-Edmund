package cs.group.edmund.clue.controllers;

import cs.group.edmund.typeSelector.Selector;
import cs.group.edmund.utils.Dictionary;
import cs.group.edmund.utils.Thesaurus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Configuration
@SuppressWarnings("unused")
@RestController
public class SolverController {

    @Autowired
    private Selector selector;

    @Autowired
    private Thesaurus thesaurus;

    @Autowired
    private Dictionary dictionary;

    @RequestMapping(value = "solve", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> solve(@RequestParam("clue") String clue,
                                       @RequestParam("hint") String hint,
                                       @RequestParam("length") int length) {
        try {
            List<String> answer = selector.retrieveAnswer(clue, hint, length);
            return new ResponseEntity<>(answer, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
