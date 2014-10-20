package cs.group.edmund.clue;

import cs.group.edmund.clue.Clue;

public class ClueCreator {
    // Fields
    String clueType;

    // Constructor
    ClueCreator(String clueType) {
        // Assign variables
        this.clueType = clueType;
    }

    // Methods
    public Clue createClue() {
        // Case statment for each clue type
        switch (clueType.toLowerCase()) {
            case "anagram":
                return createAnagram();
            default:
                break;
        }
        return null;
    }

    public Clue createAnagram() {
        return null;
    }
}

