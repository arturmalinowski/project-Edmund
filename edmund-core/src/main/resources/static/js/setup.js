// Arrays
// [clueDirection, clueNumber, clueText, clueLength, startX, startY, endX, endY, status, statusLink, hint]
var clueArray = [];
var answerArray = [];
for (var i = 0; i < 15; i++) {
	answerArray.push([".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."]);
}

setupTable(clueArray, answerArray);