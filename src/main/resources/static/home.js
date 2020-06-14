let guessField = document.getElementById("guess");
let guessButton = document.getElementById("guessButton");
let gameTypeElement = document.getElementById("gameType");
let gameType = 5;
gameTypeElement.innerHTML = gameType + " letter words";

let fiveButton = document.getElementById("fiveButton");
let sixButton = document.getElementById("sixButton");
let sevenButton = document.getElementById("sevenButton");

guessField.addEventListener('keyup', toUpper);
guessButton.addEventListener('click', sendGuess);
guessField.addEventListener("keyup", function (event) {
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
    gameTypeElement.innerHTML = gameType + " letter words";
    sessionStorage.removeItem("word");
    resetGuesses();
});

sixButton.addEventListener("click", function (event) {
    gameType = 6;
    sixButton.classList.add("selected");
    fiveButton.classList.remove("selected");
    sevenButton.classList.remove("selected");
    gameTypeElement.innerHTML = gameType + " letter words";
    sessionStorage.removeItem("word");
    resetGuesses();
});

sevenButton.addEventListener("click", function (event) {
    gameType = 7;
    sevenButton.classList.add("selected");
    sixButton.classList.remove("selected");
    fiveButton.classList.remove("selected");
    gameTypeElement.innerHTML = gameType + " letter words";
    sessionStorage.removeItem("word");
    resetGuesses();
});



function toUpper() {
    guessField.value = guessField.value.toUpperCase();
}

function resetGuesses() {
    let guessContainer = document.getElementById("guessContainer");
    guessContainer.innerHTML = "";
}

function sendGuess() {
    let word = sessionStorage.getItem("word");
    console.log(word);
    if (word == null) {
        resetGuesses();
        getWord();
        return;
    }

    let guess = guessField.value.substring(0, gameType).toLowerCase();

    if (guess.length < gameType) {
        alert("You are missing a few letters for a " + gameType + " letter word!");
        return;
    }

    let data = {
        word: word,
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
            console.log(myJson)
            let guessContainer = document.getElementById("guessContainer");
            let div = document.createElement("div");
            div.classList.add("row");

            let complete = true;

            for (let element of myJson) {
                let tile = document.createElement("div");
                tile.classList.add("tile");
                tile.innerHTML = element[0].toUpperCase();
                if (element[1] === "correct") {
                    tile.classList.add("correct");
                } else {
                    complete = false;
                    if (element[1] === "exists") {
                        tile.classList.add("exists");
                    }
                }
                div.appendChild(tile);
            }
            if (complete) {
                sessionStorage.removeItem("word");
            }
            if (guessContainer.children.length > 4) {
                guessContainer.removeChild(guessContainer.firstChild);
            }
            guessContainer.appendChild(div);
            guessField.value = "";
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
            sendGuess();
        })
        .catch(error => console.log(error));
}
