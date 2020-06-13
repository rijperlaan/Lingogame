let guessField = document.getElementById("guess");
let guessButton = document.getElementById("guessButton");
let gameTypeElement = document.getElementById("gameType");
gameTypeElement.innerHTML = "5 letter words";
let gameType = 5;


function toUpper() {
    guessField.value = guessField.value.toUpperCase();
}

function sendGuess() {
    let word = sessionStorage.getItem("word");
    if (word == null) {
        initPage();
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
        .then(function(response) {
            if (response.ok) return response.json();
            else {
                throw "Something went wrong";
            }
        })
        .then(myJson =>  {
            console.log(myJson)
            let contentContainer = document.getElementById("contentContainer");
            let div = document.createElement("div");
            div.classList.add("row");

            for (let element of myJson) {
                console.log(element);
                let tile = document.createElement("div");
                tile.classList.add("tile");
                tile.innerHTML = element[0].toUpperCase();
                if (element[1] === "correct") {
                    tile.classList.add("correct");
                } else if (element[1] === "exists") {
                    tile.classList.add("exists");
                }
                div.appendChild(tile);
            }

            contentContainer.appendChild(div);
            guessField.value = "";
        })
        .catch(error => console.log(error));
}

function initPage() {
    let word = sessionStorage.getItem("word");
    if (word == null) {
        let fetchoptions = {
            method: 'GET'
        };
        fetch("/api/words/" + gameType, fetchoptions)
            .then(function(response) {
                if (response.ok) return response.json();
                else {
                    throw "Something went wrong";
                }
            })
            .then(myJson =>  {
                console.log(myJson)
                window.sessionStorage.setItem("word", myJson.word)
            })
            .catch(error => console.log(error));
    }
}

initPage();

guessField.addEventListener('keyup', toUpper);

guessButton.addEventListener('click', sendGuess);

guessField.addEventListener("keyup", function(event) {
    // 13 is the Enter key
    if (event.keyCode === 13) {
        event.preventDefault();
        sendGuess();
    }
});