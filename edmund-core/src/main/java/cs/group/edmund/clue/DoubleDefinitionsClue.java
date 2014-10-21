package cs.group.edmund.clue;

import cs.group.edmund.solver.DoubleDefinitionSolver;

public class DoubleDefinitionsClue implements Clue {

    @Override
    public String create(String word) {
        return null;
    }

    @Override
    public boolean isRelevant(String phrase) {
        DoubleDefinitionSolver doubleDefinitionSolver = new DoubleDefinitionSolver();
        doubleDefinitionSolver.getSynonyms(phrase);
        return false;
    }

    @Override
    public String solve(String phrase) {
        return null;
    }

}
