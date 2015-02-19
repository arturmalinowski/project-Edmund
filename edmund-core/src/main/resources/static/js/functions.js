var clueArray = [];
var answerArray = [];
for (var i = 0; i < 15; i++) {
	answerArray.push([".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."]);
}

setupTable(clueArray, answerArray);

//
function runEdmund() {
	// Tests to delete
	//updateAnswerArray("0", "aaaaaa"); //
	//updateAnswerArray("1", "5abcde"); //
	//updateAnswerArray("2", "9abcdefg"); //
	//updateAnswerArray("3", "10abcd"); //
	//updateAnswerArray("12", "2abcd"); //
	updateCrosswordByEdmund();
	alert("Ran Edmund");
}


// Setup Table structure (COMPLETE)
function setupTable(clueArray, answerArray) {
	this.clueArray = clueArray;
	this.answerArray = answerArray;

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

	// Setup rest of table
	fillCrossword();
	importCrosswordClues();
	addClues();
}


// Import crossword clues into clueArray from JSON (INCOMPLETE)
function importCrosswordClues() {
	// Example clues to delete later
	var temp = [
		{"clueDirection":"across", "clueNumber":"1", "clueText":"Physician brings fish around", "clueLength":"3", "startPos":"(0,0)", "endPos":"(2,0)"},
		{"clueDirection":"across", "clueNumber":"2", "clueText":"Times when things appear obscure?", "clueLength":"6", "startPos":"(0,1)", "endPos":"(5,1)"},
		{"clueDirection":"across", "clueNumber":"3", "clueText":"Observe odd characters in scene", "clueLength":"3", "startPos":"(0,2)", "endPos":"(2,2)"},
		{"clueDirection":"across", "clueNumber":"4", "clueText":"We surrounded strike snowy", "clueLength":"5", "startPos":"(0,3)", "endPos":"(4,3)"},
		{"clueDirection":"across", "clueNumber":"5", "clueText":"Challenging sweetheart heartlessly", "clueLength":"6", "startPos":"(0,4)", "endPos":"(5,4)"},
		{"clueDirection":"across", "clueNumber":"6", "clueText":"Armor in the post", "clueLength":"4", "startPos":"(0,5)", "endPos":"(3,5)"},
		{"clueDirection":"across", "clueNumber":"7", "clueText":"His work is often framed", "clueLength":"6", "startPos":"(0,6)", "endPos":"(5,6)"},

		{"clueDirection":"down", "clueNumber":"8", "clueText":"Jeer? I beg to differ!", "clueLength":"4", "startPos":"(14,0)", "endPos":"(14,3)"},
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

// Add clue information to left side of page and crossword (COMPLETE)
function addClues() {
	// Add clues to clue list
	for (var i = 0; i < clueArray.length; i++) {
		var table = document.getElementById(clueArray[i][0]+"Clues");

		var row = table.insertRow();
		var cell = row.insertCell(0);
		//cell.innerHTML = "<img src='img/statusAdded.jpg' border=0/>";
		clueArray[i][9] = cell;

		var temp = "";
		for (var j = 0; j < parseInt(clueArray[i][3]); j++) {
			temp = temp + ".";
		}
		clueArray[i][10] = temp;

		var clueText = clueArray[i][1] + ". " + clueArray[i][2] + " (" + clueArray[i][3] + ")";
		cell = row.insertCell(0);
		cell.innerHTML = clueText;

		var xx = clueArray[i][4];
		var yy = clueArray[i][5];
		var num = clueArray[i][1];

		document.getElementById("crosswordTable").rows[yy].cells[xx].innerHTML = '.<div class="daySubscript">' + num + '</div>';

		// Log clue added
		log("Added " + clueArray[i][0] + " Clue: " + clueArray[i][1] + ". " + clueArray[i][2] + " (" + clueArray[i][3] + ").");
		log("");
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


// Generate hints by iterating through the answerArray (COMPLETE)
function generateHints() {

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
function updateCrosswordByEdmund() {
	for (var i = 0; i < clueArray.length; i++) {
		log("Sending " + clueArray[i][1] + " " + clueArray[i][0] + " to Edmund.");
		clueArray[i][9].innerHTML = "<img src='img/statusCalculating.png' border=0/>";

		// send clue to edmund, get response
		var newAnswer = sendToEdmund(clueArray[i][2], clueArray[i][3], clueArray[i][10]);

		if (!(newAnswer === "*")) {
			clueArray[i][10] = newAnswer;
			updateAnswerArray(clueArray, answerArray, i);
		}
		else {
			clueArray[i][9].innerHTML = "<img src='img/statusFailed.png' border=0/>";
			log("Edmund could not resolve " + clueArray[i][1] + " " + clueArray[i][0] + ".");
			log("");
		}
	}
}


// Send clue to Edmund with hint (INCOMPLETE)
function sendToEdmund(clueText, clueLength, hint) {
	var url = "http://localhost:9090/solve?clue=" + clueText + "&hint=" + hint + "&length=" + clueLength;
	alert(url);
	
	// ajax request
	$.getJSON(url, function(result){
		alert(result);
	});

	
	return "*"; // EDMUND FAILURE
}


// Update answerArray with new answers (COMPLETE)
function updateAnswerArray(clueIndexNumber, hint) {
	// fill answerArray with new answer, update status
	if (clueArray[clueIndexNumber][0] === "across") {
		var temp = 0;
		for (var j = parseInt(clueArray[clueIndexNumber][4]); j < parseInt(clueArray[clueIndexNumber][6])+1; j++) {
			updateCell(j, clueArray[clueIndexNumber][5], hint.substring(temp, temp+1));
			temp++;
		}
	}
	if (clueArray[clueIndexNumber][0] === "down") {
		var temp = 0;
		for (var j = parseInt(clueArray[clueIndexNumber][5]); j < parseInt(clueArray[clueIndexNumber][7])+1; j++) {
			updateCell(clueArray[clueIndexNumber][4], j, hint.substring(temp, temp+1));
			temp++;
		}
	}
	clueArray[clueIndexNumber][8] = "solved";
	clueArray[clueIndexNumber][9].innerHTML = "<img src='img/statusSolved.png' border=0/>";
}


// Fill the crossword based on the answerArray (COMPLETE)
function fillCrossword() {
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
function updateCell(x, y, content) {
	answerArray[x][y] = content;
	document.getElementById("crosswordTable").rows[y].cells[x].innerHTML = content.toString() + document.getElementById("crosswordTable").rows[y].cells[x].innerHTML.substring(1);
	generateHints();
}


// Blank the cell at the given coordinates (COMPLETE)
function setBlank(x, y) {
	var cell = document.getElementById("crosswordTable").rows[parseInt(y)].cells[parseInt(x)].className = "blankCell";
}


// Unblank the cell at the given coordinates (COMPLETE)
function setUnblank(x, y) {
	var cell = document.getElementById("crosswordTable").rows[parseInt(y)].cells[parseInt(x)].className = "crosswordCell";
}

// Log messages to the status pane (COMPLETE)
function log(message) {
	document.getElementById("statusPane").innerHTML = document.getElementById("statusPane").innerHTML + "&#10;" + message;
	document.getElementById("statusPane").scrollTop = document.getElementById("statusPane").scrollHeight 
}