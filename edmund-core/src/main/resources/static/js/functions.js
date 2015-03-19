preloadImages();
// [clueDirection, clueNumber, clueNumberLink, clueText, clueLength, startX, startY, endX, endY, clueStatus, clueStatusLink, clueHint, clueAnswers]
var clueArray = [];
var answerArray = [];
var jsonUploaded = false;
var solving = false;

for (var i = 0; i < 15; i++) {
	answerArray.push([".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "."]);
}
setupTable();
addClickListeners();
addKeyListeners();

// Functions
//
//
// Setup Table structure (WORKING)
function setupTable() {

	// Add rows and cells to table
	var table = document.getElementById("crosswordTable");
	for (var i = 0; i < 15; i++) {
		var row = table.insertRow();
		row.id = "row_" + i.toString();

		for (var j = 0; j < 15; j++) {
			var cell = row.insertCell(-1);
			cell.innerHTML = '<div id="' + j.toString() + '_' + i.toString() + '_content" contentEditable></div>' + '<div id="' + j.toString() + '_' + i.toString() + '_number' + '" class="daySubscript"></div>';
			cell.className = cell.className + " crosswordCell";
		}
	}

	// Set crossword cells as blank
	for (var i = 0; i < 15; i++) {
		for (var j = 0; j < 15; j++) {
			setBlank(i, j);
		}
	}

	fillCrossword();
}


// Clear current crossword configuration (WORKING)
function clearConfiguration() {

	// Clear crossword table
	for (var i = 0; i < 15; i++) {
		for (var j = 0; j < 15; j++) {
			setBlank(i, j);
			updateCell(i, j, ".");
		}
	}

	// Delete clue information
	for (var i in clueArray) {
		var table = document.getElementById(clueArray[i][0]+"Clues");
		table.deleteRow(0);
	}
}


// Upload selected .txt file with JSON, parse it to clueArray (WORKING)
function uploadJSON() {

	var x = document.getElementById("fileInput");
	var file = x.files[0];
	if (file.name.substring(file.name.indexOf(".") + 1) === "txt") {
		x.disabled = true;
		jsonUploaded = true;

		var reader = new FileReader();
		reader.onload = function(){
			var json = reader.result,
      		obj = JSON.parse(json);

			// Import temp array into clueArray
			// [clueDirection, clueNumber, clueNumberLink, clueText, clueLength, startX, startY, endX, endY, clueStatus, clueStatusLink, clueHint, clueAnswers]
			for (var i = 0; i < obj.size; i++) {
				clueArray.push([
					obj.clues[i].clueDirection,
					obj.clues[i].clueNumber,
					document.getElementById(obj.clues[i].startPos.substring(1, parseInt(obj.clues[i].startPos.indexOf(","))) + "_" + obj.clues[i].startPos.substring(parseInt(obj.clues[i].startPos.indexOf(","))+1, parseInt(obj.clues[i].startPos.indexOf(")"))) + "_number"),
					obj.clues[i].clueText,
					obj.clues[i].clueLength,
					obj.clues[i].startPos.substring(1, parseInt(obj.clues[i].startPos.indexOf(","))),
					obj.clues[i].startPos.substring(parseInt(obj.clues[i].startPos.indexOf(","))+1, parseInt(obj.clues[i].startPos.indexOf(")"))),
					obj.clues[i].endPos.substring(1, parseInt(obj.clues[i].endPos.indexOf(","))),
					obj.clues[i].endPos.substring(parseInt(obj.clues[i].endPos.indexOf(","))+1, parseInt(obj.clues[i].endPos.indexOf(")"))),
					"UNCALCULATED",
					"BLANK",
					"BLANK",
					"BLANK"
				]);
			}
			addClues();
		};
		reader.readAsText(file);
	}
}


// Fill the crossword with the contents of the answerArray (WORKING)
function fillCrossword() {
	for (var i = 0; i < 15; i++) {
		for (var j = 0; j < 15; j++) {
			updateCell(j, i, answerArray[j][i]);
		}
	}
}


// Add clue information to left side of page and crossword table (WORKING)
function addClues() {

	// Add clues to clue list
	for (var i in clueArray) {
		var table = document.getElementById(clueArray[i][0]+"Clues");

		// Fill clueStatusLink
		var row = table.insertRow();
		var cell = row.insertCell(0);
		clueArray[i][10] = cell;

		// Fill hint
		var temp = "";
		for (var j = 0; j < parseInt(clueArray[i][4]); j++) {
			temp = temp + ".";
		}
		clueArray[i][11] = temp;

		// Display clueText
		var clueText = clueArray[i][1] + ". " + clueArray[i][3] + " (" + clueArray[i][4] + ")";
		cell = row.insertCell(0);
		cell.innerHTML = clueText;

		// Add clueIndex on crossword table
		document.getElementById(clueArray[i][5] + "_" + clueArray[i][6] + "_number").innerHTML = clueArray[i][1];
	}

	// Unhighlight crossword table
	for (var i in clueArray) {
		if (clueArray[i][0] === "across") {
			for (var j = parseInt(clueArray[i][5]); j < parseInt(clueArray[i][7])+1; j++) {
				setUnblank(j, clueArray[i][6]);
			}
		}
		if (clueArray[i][0] === "down") {
			for (var j = parseInt(clueArray[i][6]); j < parseInt(clueArray[i][8])+1; j++) {
				setUnblank(clueArray[i][7], j);
			}
		}
	}
}


// Send all unsolved clues to Edmund (WORKING)
function runEdmund() {

	// Check if any clues still solving
    	for (var i in clueArray) {
    		if (clueArray[i][9] === "SOLVING") {
    			solving = true;
    		}
    	}


    	if ((jsonUploaded) && (!solving)) {
    		updateAnswerArrayFromUser();
    		generateHints();

    		sendToEdmund(0);
    		document.getElementById("edmundButton").disabled = true;
    	}
}

//
function runEdmundSingle() {
    document.getElementById("answer").innerHTML = "Edmund is thinking...";
    var clue = document.getElementById("singleClue").value.toString();
    var length = document.getElementById("answerLength").value.toString();
    var hint = document.getElementById("hint").value.toString();
    var url = "http://localhost:9090/solve?clue=" + clue + "&hint=" + hint + "&length=" + length;

    if ((hint === "hint?") || (hint === "")) {
        hint = "";
        for (var i = 0; i < parseInt(length); i++) {
        	hint = hint + ".";
        }
        document.getElementById("hint").innerHTML = hint;
    }

    // ajax request
    $.getJSON(url, function(data) {
        if (data.length == 1) {
            document.getElementById("answer").innerHTML = "Your answer is: " + data[0];
        }
        else {
            var temp = "";
            for (var i in data) {
            	temp = temp + data[i] + ", ";
            }
            temp = temp.substring(0, temp.length - 2);
            document.getElementById("answer").innerHTML = "Your answer could be: " + temp;
        }
    })
    .error( function(data) {
        document.getElementById("answer").innerHTML = "We're sorry, Edmund could not find the answer!";
    });
}


// Send clue to Edmund by index (WORKING)
function sendToEdmund(clueIndex) {

	if (((clueArray[clueIndex][9] === "UNCALCULATED") || (clueArray[clueIndex][9] === "MULTIPLE")) || (clueArray[clueIndex][9] === "UNSOLVED")) {
		//
		clueArray[clueIndex][9] = "SOLVING";
		clueArray[clueIndex][10].innerHTML = "<img src='img/statusCalculating.png' border=0/>";

		// Generate URL for http request
		var url = "http://localhost:9090/solve?clue=" + clueArray[clueIndex][3] + "&hint=" + clueArray[clueIndex][11] + "&length=" + clueArray[clueIndex][4];

		// ajax request
		$.getJSON(url, function(data) {
			receiveFromEdmund(clueIndex, data, "success");
		})
		.error( function(data) {
			receiveFromEdmund(clueIndex, data, "failure");
		});
	}
	else {
	    if ((clueIndex + 1) < clueArray.length) { sendToEdmund(clueIndex + 1); }
        if (clueIndex == (clueArray.length - 1)) {
            solving = false;
            document.getElementById("edmundButton").disabled = false;
        }
	}
}


// Receive responses from Edmund (WORKING)
function receiveFromEdmund(clueIndex, newAnswer, returnStatus) {

	// Answer found
	if (returnStatus === "success") {

		if (newAnswer.length == 1) {
		    clueArray[clueIndex][9] = "SOLVED";
		    clueArray[clueIndex][10].innerHTML = "<img src='img/statusSolved.png' border=0/>";
		    updateAnswerArrayFromHints(clueIndex, newAnswer[0]);
		}
		else {
		    clueArray[clueIndex][9] = "MULTIPLE";
		    clueArray[clueIndex][10].innerHTML = "<img src='img/statusMultiple.png' border=0/>";
		    clueArray[clueIndex][12] = newAnswer;
		}

		// Log answers and add as tooltips
		var temp = "";
		for (var i in newAnswer) {
			temp = temp + newAnswer[i] + ", ";
		}
		clueArray[clueIndex][10].title = temp.substring(0, temp.length - 2);
	}
	// Answer not found
	else {
		clueArray[clueIndex][9] = "UNSOLVED";
		clueArray[clueIndex][10].innerHTML = "<img src='img/statusFailed.png' border=0/>";
		clueArray[clueIndex][10].title = "";
	}

	if (clueIndex == (clueArray.length - 1)) {
        solving = false;
        document.getElementById("edmundButton").disabled = false;
    }
    if ((clueIndex + 1) < clueArray.length) { sendToEdmund(clueIndex + 1); }
}


// Update the cell contents at the given coordinates (WORKING)
function updateCell(x, y, content) {
	answerArray[x][y] = content;
	document.getElementById(x + "_" + y + "_content").innerHTML = content;
	generateHints();
}


// Update answerArray with new answers (WORKING)
function updateAnswerArrayFromHints(clueIndexNumber, answer) {

	// fill answerArray with new answer, update clue status
	var temp = 0;
	if (clueArray[clueIndexNumber][0] === "across") {
		for (var j = parseInt(clueArray[clueIndexNumber][5]); j < parseInt(clueArray[clueIndexNumber][7])+1; j++) {
			updateCell(j, clueArray[clueIndexNumber][6], answer.substring(temp, temp+1));
			temp++;
		}
	}
	if (clueArray[clueIndexNumber][0] === "down") {
		for (var j = parseInt(clueArray[clueIndexNumber][6]); j < parseInt(clueArray[clueIndexNumber][8])+1; j++) {
			updateCell(clueArray[clueIndexNumber][7], j, answer.substring(temp, temp+1));
			temp++;
		}
	}
	clueArray[clueIndexNumber][9] = "solved";
	clueArray[clueIndexNumber][10].innerHTML = "<img src='img/statusSolved.png' border=0/>";
}


// Fill answer array from crossword table content (WORKING)
function updateAnswerArrayFromUser() {

	for (var i = 0; i < 15; i++) {
		for (var j = 0; j < 15; j++) {
			answerArray[i][j] = document.getElementById(i + "_" + j + "_content").innerHTML;
		}
	}
}


// Generate hints by iterating through the answerArray (WORKING)
function generateHints() {

	for (var i in clueArray) {
		var temp = "";
		if (clueArray[i][0] === "across") {
			for (var j = parseInt(clueArray[i][5]); j < parseInt(clueArray[i][7])+1; j++) {
				var x = j;
				var y = parseInt(clueArray[i][6]);

				temp = temp + answerArray[x][y];
			}
			clueArray[i][11] = temp;
		}
		if (clueArray[i][0] === "down") {
			for (var j = parseInt(clueArray[i][6]); j < parseInt(clueArray[i][8])+1; j++) {
				var x = parseInt(clueArray[i][7]);
				var y = j;

				temp = temp + answerArray[x][y];
			}
			clueArray[i][11] = temp;
		}
	}
}


// Blank the cell at the given coordinates (WORKING)
function setBlank(x, y) {
	var cell = document.getElementById("crosswordTable").rows[parseInt(y)].cells[parseInt(x)].className = "blankCell";
	updateCell(y, x, "");
}


// Unblank the cell at the given coordinates (WORKING)
function setUnblank(x, y) {
	var cell = document.getElementById("crosswordTable").rows[parseInt(y)].cells[parseInt(x)].className = "crosswordCell";
	updateCell(x, y, ".");
}

function preloadImages() {
    // preload images
    ImageCalculating = new Image(30,30);
    ImageCalculating.src = "img/statusCalculating.png";
    ImageSolved = new Image(30,30);
    ImageSolved.src = "img/statusSolved.png";
    ImageMultiple = new Image(30,30);
    ImageMultiple.src = "img/statusMultiple.png";
    ImageFailed = new Image(30,30);
    ImageFailed.src = "img/statusFailed.png";
}

//
function addClickListener(x, y) {
    document.getElementById(x.toString() + "_" + y.toString() + "_content").addEventListener("click", function(){
        if (!solving) {
            updateCell(x, y, "");
        }
    });
}

//
function addClickListeners() {
    for (var i = 0; i < 15; i++) {
    	for (var j = 0; j < 15; j++) {
            addClickListener(j, i);
    	}
    }

    document.getElementById("singleClue").addEventListener("click", function(){
        document.getElementById("singleClue").innerHTML = "";
        document.getElementById("singleClue").value = "";
    });
    document.getElementById("answerLength").addEventListener("click", function(){
            document.getElementById("answerLength").innerHTML = "";
             document.getElementById("answerLength").value = "";
    });
    document.getElementById("hint").addEventListener("click", function(){
            document.getElementById("hint").innerHTML = "";
            document.getElementById("hint").value = "";
    });
}