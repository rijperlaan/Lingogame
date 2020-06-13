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

    let guess = guessField.value.substring(0, gameType);
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

guessButton.addEventListener('keyup', sendGuess);

guessField.addEventListener("keyup", function(event) {
    // 13 is the Enter key
    if (event.keyCode === 13) {
        event.preventDefault();
        sendGuess();
    }
});