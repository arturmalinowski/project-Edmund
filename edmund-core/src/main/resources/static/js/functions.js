//
function setupTable() {
	// Add rows and cells to table
	var table = document.getElementById("crosswordTable");
	for (var i = 0; i < 15; i++) {
		var row = table.insertRow();
		row.id = "row_" + i.toString();

		for (var j = 0; j < 15; j++) {
			var cell = row.insertCell(0);
			cell.className = cell.className + " crosswordCell";
		}
	}

	// Fill all blank
	for (var i = 0; i < 15; i++) {
		for (var j = 0; j < 15; j++) {
			setBlank(i, j);
		}
	}
}

// Import crossword clues into clueArray from JSON (INCOMPLETE)
function importCrosswordClues(clueArray) {
	// Example clues to delete later
	var temp = [
		{"clueDirection":"across", "clueNumber":"1", "clueText":"His work is often framed", "clueLength":"6", "startPos":"(0,0)", "endPos":"(6,0)"},
		{"clueDirection":"across", "clueNumber":"5", "clueText":"Its mother's first to lactate", "clueLength":"6", "startPos":"(9,0)", "endPos":"(14,0)"},
		{"clueDirection":"across", "clueNumber":"9", "clueText":"Tall thin person training runners", "clueLength":"8", "startPos":"(0,2)", "endPos":"(7,2)"},
		{"clueDirection":"across", "clueNumber":"10", "clueText":"Astronaut found in the local, drinking", "clueLength":"6", "startPos":"(9,2)", "endPos":"(14,2)"},
		{"clueDirection":"across", "clueNumber":"17", "clueText":"Subdued sound of cattle", "clueLength":"3", "startPos":"(12,7)", "endPos":"(14,7)"},
		{"clueDirection":"across", "clueNumber":"19", "clueText":"Dismissed when on strike", "clueLength":"3", "startPos":"(0,9)", "endPos":"(2,9)"},
		{"clueDirection":"across", "clueNumber":"20", "clueText":"Contributory funds one put on daily account", "clueLength":"10", "startPos":"(5,9)", "endPos":"(14,9)"},
		{"clueDirection":"across", "clueNumber":"26", "clueText":"Listener following close win", "clueLength":"6", "startPos":"(0,12)", "endPos":"(5,12)"},
		{"clueDirection":"across", "clueNumber":"27", "clueText":"Furtive sort of hat style", "clueLength":"8", "startPos":"(7,12)", "endPos":"(14,12)"},
		{"clueDirection":"across", "clueNumber":"28", "clueText":"Bearing a flower in March or April?", "clueLength":"6", "startPos":"(0,14)", "endPos":"(5,14)"},
		{"clueDirection":"across", "clueNumber":"29", "clueText":"Girl with two articles to stow away safely", "clueLength":"7", "startPos":"(8,14)", "endPos":"(14,14)"},
		{"clueDirection":"down", "clueNumber":"1", "clueText":"Jeer? I beg to differ!", "clueLength":"4", "startPos":"(0,0)", "endPos":"(0,3)"},
		{"clueDirection":"down", "clueNumber":"2", "clueText":"Open with a shock", "clueLength":"4", "startPos":"(2,0)", "endPos":"(2,3)"},
		{"clueDirection":"down", "clueNumber":"3", "clueText":"Understood devil comes to a lawful end", "clueLength":"8", "startPos":"(4,0)", "endPos":"(4,7)"},
		{"clueDirection":"down", "clueNumber":"4", "clueText":"Australian football is supreme, OK?", "clueLength":"5", "startPos":"(6,0)", "endPos":"(6,4)"}
	]; // DELETE LATER

	// Importing temp array into clueArray
	for (var i = 0; i < temp.length; i++) {
		clueArray.push([
			temp[i].clueDirection,
			temp[i].clueNumber,
			temp[i].clueText,
			temp[i].clueLength,
			temp[i].startPos.substring(1, parseInt(temp[i].startPos.indexOf(","))),
			temp[i].startPos.substring(parseInt(temp[i].startPos.indexOf(","))+1, parseInt(temp[i].startPos.indexOf(")"))),
			temp[i].endPos.substring(1, parseInt(temp[i].endPos.indexOf(","))),
			temp[i].endPos.substring(parseInt(temp[i].endPos.indexOf(","))+1, parseInt(temp[i].endPos.indexOf(")"))),
			"UNCALCULATED",
			"BLANK",
			"BLANK"
		]);
	}	
}

// Add clue information to left side of page (COMPLETE)
function addClues(clueArray) {
	// Add clues to clue list
	for (var i = 0; i < clueArray.length; i++) {
		var table = document.getElementById(clueArray[i][0]+"Clues");

		var row = table.insertRow();
		var cell = row.insertCell(0);
		//cell.innerHTML = "<img src='img/statusAdded.jpg' border=0/>";
		cell.innerHTML = "<p> </p>";
		clueArray[i][9] = cell;

		var temp = "";
		for (var j = 0; j < parseInt(clueArray[i][3]); j++) {
			temp = temp + ".";
		}
		clueArray[i][10] = temp;

		var clueText = clueArray[i][1] + ". " + clueArray[i][2] + " (" + clueArray[i][3] + ")";
		cell = row.insertCell(0);
		cell.innerHTML = clueText;
	}

	// Unhighlight crossword table
	for (var i = 0; i < clueArray.length; i++) {
		if (clueArray[i][0] === "across") {
			for (var j = parseInt(clueArray[i][4]); j < parseInt(clueArray[i][6])+1; j++) {
				setUnblank(j, clueArray[i][7]);
			}
		}
		if (clueArray[i][0] === "down") {
			for (var j = parseInt(clueArray[i][5]); j < parseInt(clueArray[i][7])+1; j++) {
				setUnblank(clueArray[i][4], j);
			}
		}
	}
}


//
function generateHints(clueArray, answerArray) {

	for (var i = 0; i < clueArray.length; i++) {
		var temp = "";
		if (clueArray[i][0] === "across") {
			for (var j = parseInt(clueArray[i][4]); j < parseInt(clueArray[i][6])+1; j++) {
				var x = j;
				var y = parseInt(clueArray[i][5]);

				temp = temp + answerArray[x][y];
			}
			clueArray[i][10] = temp;
		}
		if (clueArray[i][0] === "down") {
			for (var j = parseInt(clueArray[i][5]); j < parseInt(clueArray[i][7])+1; j++) {
				var x = parseInt(clueArray[i][4]);
				var y = j;

				temp = temp + answerArray[x][y];
			}
			clueArray[i][10] = temp;
		}
	}
}


// Use Edmund to find answers to clues (INCOMPLETE)
function updateCrosswordByEdmund(clueArray, answerArray) {
	for (var i = 0; i < clueArray.length; i++) {
		clueArray[i][9].innerHTML = "<img src='img/statusCalculating.png' border=0/>";
		// send clue to edmund, get response
		var newAnswer = sentToEdmund(clueArray[i][2], clueArray[i][3], clueArray[i][10]);

		if (!(newAnswer === "")) {
			clueArray[i][10] = newAnswer;
			updateAnswerArray(clueArray, answerArray, i);
		}
		else {
			clueArray[clueIndexNumber][9].innerHTML = "<img src='img/statusFailed.png' border=0/>";
		}
	}
}


// Send clue to Edmund with hint (INCOMPLETE)
function sendToEdmund(clueText, clueLength, hint) {
	var url = "http://localhost:18800/solve?clue=" + clueText + "&hint=" + hint + "&length=" + clueLength;
	return "";
}


// Update answerArray with new answers (COMPLETE)
function updateAnswerArray(clueArray, answerArray, clueIndexNumber, hint) {
	// fill answerArray with new answer, update status
	if (clueArray[clueIndexNumber][0] === "across") {
		var temp = 0;
		for (var j = parseInt(clueArray[clueIndexNumber][4]); j < parseInt(clueArray[clueIndexNumber][6])+1; j++) {
			updateCell(answerArray, j, clueArray[clueIndexNumber][5], hint.substring(temp, temp+1), clueArray);
			temp++;
		}
	}
	if (clueArray[clueIndexNumber][0] === "down") {
		var temp = 0;
		for (var j = parseInt(clueArray[clueIndexNumber][5]); j < parseInt(clueArray[clueIndexNumber][7])+1; j++) {
			updateCell(answerArray, clueArray[clueIndexNumber][4], j, hint.substring(temp, temp+1), clueArray);
			temp++;
		}
	}
	clueArray[clueIndexNumber][8] = "solved";
	clueArray[clueIndexNumber][9].innerHTML = "<img src='img/statusSolved.png' border=0/>";
}


// Fill the crossword based on the answerArray (COMPLETE)
function fillCrossword(answerArray) {
	for (var i = 0; i < 15; i++) {
		for (var j = 0; j < 15; j++) {
			document.getElementById("crosswordTable").rows[i].cells[j].innerHTML = answerArray[i][j];
		}
	}
}


// Get content of given cell (COMPLETE)
function getCell(x, y) {
	var cell = document.getElementById("crosswordTable").rows[y].cells[x];
	return cell.innerHTML;
}


// Update the cell contents at the given coordinates (COMPLETE)
function updateCell(answerArray, x, y, content, clueArray) {
	answerArray[x][y] = content;
	document.getElementById("crosswordTable").rows[y].cells[x].innerHTML = content.toString();
	generateHints(clueArray, answerArray);
}


// Blank the cell at the given coordinates (COMPLETE)
function setBlank(x, y) {
	var cell = document.getElementById("crosswordTable").rows[parseInt(y)].cells[parseInt(x)].className = "blankCell";
}


// Unblank the cell at the given coordinates (COMPLETE)
function setUnblank(x, y) {
	var cell = document.getElementById("crosswordTable").rows[parseInt(y)].cells[parseInt(x)].className = "crosswordCell";
}