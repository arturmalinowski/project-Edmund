// Arrays
// [clueDirection, clueNumber, clueText, clueLength, startX, startY, endX, endY, status, statusLink, hint]
var clueArray = [];
var answerArray = [];
for (var i = 0; i < 15; i++) {
	answerArray.push([".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."]);
}

setupTable();

fillCrossword(answerArray);

importCrosswordClues(clueArray);
addClues(clueArray);
//updateCrosswordByEdmund(clueArray, answerArray);

// Tests to delete
updateAnswerArray(clueArray, answerArray, "0", "1abcde"); //
updateAnswerArray(clueArray, answerArray, "1", "5abcde"); //
updateAnswerArray(clueArray, answerArray, "2", "9abcdefg"); //
updateAnswerArray(clueArray, answerArray, "3", "10abcd"); //
updateAnswerArray(clueArray, answerArray, "12", "2abcd"); //

//generateHints(clueArray, answerArray);
//alert(clueArray[0]);
//alert("done");