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
    gameType = 5;
    fiveButton.classList.add("selected");
    sixButton.classList.remove("selected");
    sevenButton.classList.remove("selected");
    startGame();
});
sixButton.addEventListener("click", function (event) {
    gameType = 6;
    sixButton.classList.add("selected");
    fiveButton.classList.remove("selected");
    sevenButton.classList.remove("selected");
    startGame();
});
sevenButton.addEventListener("click", function (event) {
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
    preparedLine = null;
    given = ["", "", "", "", "", "", ""];
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
                let tile = preparedLine.children[i];
                tile.innerHTML = element[0].toUpperCase();
                tile.classList.add(element[1]);
                if (element[1] !== "correct") {
                    complete = false;
                } else {
                    given[i] = element[0].toUpperCase();
                }
                i++;
            }

            document.getElementById("guess").value = "";
            if (myJson.gameOver) {
                for (let i = 0; i < given.length; i++) {
                    given[i] = myJson.answer.substring(i, i + 1).toUpperCase();
                }
                prepareLine(true);
                resetGame();
            } else {
                if (complete) {
                    resetGame();
                } else {
                    let guessContainer = document.getElementById("guessContainer");
                    if (guessContainer.children.length > 4) {
                        guessContainer.removeChild(guessContainer.firstChild);
                    }
                    prepareLine(false);
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
            prepareLine(false);
        })
        .catch(error => console.log(error));
}

let preparedLine = null;
let given = ["", "", "", "", "", "", ""];

function prepareLine(gameOver) {
    let guessContainer = document.getElementById("guessContainer");

    preparedLine = document.createElement("div");
    preparedLine.classList.add("row");

    for (let i = 0; i < gameType; i++) {
        let tile = document.createElement("div");
        tile.classList.add("tile");
        if (gameOver) {
            tile.classList.add("gameOver");
        }
        preparedLine.appendChild(tile);
        if (given[i] !== "") {
            tile.innerHTML = given[i].toUpperCase();
        }
    }

    guessContainer.appendChild(preparedLine);
}

