let gameType = -1;

let fiveButton = document.getElementById("fiveButton");
let sixButton = document.getElementById("sixButton");
let sevenButton = document.getElementById("sevenButton");

document.getElementById("guess").addEventListener('keyup', toUpper);
document.getElementById("guessButton").addEventListener('click', sendGuess);
document.getElementById("guess").addEventListener("keyup", function (event) {
    // 13 is the Enter key
    if (event.keyCode === 13) {
        event.preventDefault();
        sendGuess();
    }
});

fiveButton.addEventListener("click", function (event) {
    resetGame();
    gameType = 5;
    fiveButton.classList.add("selected");
    sixButton.classList.remove("selected");
    sevenButton.classList.remove("selected");
    startGame();
});
sixButton.addEventListener("click", function (event) {
    resetGame();
    gameType = 6;
    sixButton.classList.add("selected");
    fiveButton.classList.remove("selected");
    sevenButton.classList.remove("selected");
    startGame();
});
sevenButton.addEventListener("click", function (event) {
    resetGame();
    gameType = 7;
    sevenButton.classList.add("selected");
    sixButton.classList.remove("selected");
    fiveButton.classList.remove("selected");
    startGame();
});

function startGame() {
    document.getElementById("gameType").innerHTML = gameType + " letter word";
    let guessContainer = document.getElementById("guessContainer");
    guessContainer.innerHTML = "";
    getWord();
    document.getElementById("guess").disabled = false;
}

function toUpper() {
    let guessField = document.getElementById("guess");
    guessField.value = guessField.value.toUpperCase();
}

function resetGame() {
    preparedLine = [null, null, null, null, null, null];
    given = ["", "", "", "", "", "", ""];
    lineIndex = 0;
    sessionStorage.removeItem("word");
    sessionStorage.removeItem("sessionToken");
    document.getElementById("guess").disabled = true;
    gameType = -1;
    fiveButton.classList.remove("selected");
    sixButton.classList.remove("selected");
    sevenButton.classList.remove("selected");
    document.getElementById("gameType").innerHTML = "Select word length to start";
}

function sendGuess() {
    let word = sessionStorage.getItem("word");
    let sessionToken = sessionStorage.getItem("sessionToken");

    if (word == null || sessionToken == null) {
        resetGame();
        getWord();
        return;
    }

    let guess = document.getElementById("guess").value.substring(0, gameType).toLowerCase();

    if (guess.length < gameType) {
        alert("You are missing a few letters for a " + gameType + " letter word!");
        return;
    }

    let data = {
        word: word,
        sessionToken: sessionToken,
        guess: guess
    }

    let fetchoptions = {
        method: 'POST',
        body: JSON.stringify(data)
    };
    fetch("/api/words/guess", fetchoptions)
        .then(function (response) {
            if (response.ok) return response.json();
            else {
                throw "Something went wrong";
            }
        })
        .then(myJson => {
            let complete = true;
            let i = 0;
            for (let element of myJson.guessResult) {
                let tile = preparedLine[lineIndex].children[i];
                tile.innerHTML = element[0].toUpperCase();
                tile.classList.add(element[1]);
                if (element[1] !== "correct") {
                    complete = false;
                } else {
                    given[i] = element[0].toUpperCase();
                }
                i++;
            }

            lineIndex++;

            document.getElementById("guess").value = "";
            if (myJson.gameOver) {
                for (let i = 0; i < given.length; i++) {
                    given[i] = myJson.answer.substring(i, i + 1).toUpperCase();
                }
                showAnswer();
                resetGame();
            } else {
                if (complete) {
                    resetGame();
                } else {
                    fillPreparedLine();
                }
            }
        })
        .catch(error => console.log(error));
}

function getWord() {
    let fetchoptions = {
        method: 'GET'
    };
    fetch("/api/words/" + gameType, fetchoptions)
        .then(function (response) {
            if (response.ok) return response.json();
            else {
                throw "Something went wrong";
            }
        })
        .then(myJson => {
            window.sessionStorage.setItem("word", myJson.word);
            window.sessionStorage.setItem("sessionToken", myJson.sessionToken);
            given[0] = myJson.firstLetter.toUpperCase();
            prepareLines();
            fillPreparedLine();
        })
        .catch(error => console.log(error));
}

let preparedLine = [null, null, null, null, null, null];
let lineIndex = 0;
let given = ["", "", "", "", "", "", ""];

function prepareLines() {
    let guessContainer = document.getElementById("guessContainer");
    for (let o = 0; o < 5; o++) {
        preparedLine[o] = document.createElement("div");
        preparedLine[o].classList.add("row");

        for (let i = 0; i < gameType; i++) {
            let tile = document.createElement("div");
            tile.classList.add("tile");
            preparedLine[o].appendChild(tile);
        }
        guessContainer.appendChild(preparedLine[o]);
    }
}

function fillPreparedLine() {
    for (let i = 0; i < gameType; i++) {
        let tile = preparedLine[lineIndex].children[i];
        if (given[i] !== "") {
            tile.innerHTML = given[i].toUpperCase();
        }
    }
}

function showAnswer() {
    let guessContainer = document.getElementById("guessContainer");
    let newLine = document.createElement("div");
    newLine.classList.add("row");

    for (let i = 0; i < gameType; i++) {
        let tile = document.createElement("div");
        tile.classList.add("tile");
        tile.classList.add("gameOver");
        if (given[i] !== "") {
            tile.innerHTML = given[i].toUpperCase();
        }
        newLine.appendChild(tile);

    }
    guessContainer.appendChild(newLine);

}
