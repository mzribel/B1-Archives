// Keeps the data of the current game.
class CurrentGame {
    constructor() {
        this.playerPoints = 0;
        this.isFinished = false;
        this.currentQuestionID = 0
    }
}

class Question {
    constructor(data, id) {
        this.id = id;
        this.title = data.title;
        this.isMultiple = data.isMultiple,
        this.answers = data.answers;
        this.points = data.points;
        this.details = data.details;
    }

    // Calculates the user's score on the question.
    // User must have all correct answers to have the full point.
    calculateScore(userAnswers) {
        if (userAnswers.length != this.answers.length) {
            return 0;
        } 
        return this.answers.every((element, index) => element.isTrue == userAnswers[index]) ?
            this.points : 0;
    }

    // Generates the HTML code for this question.   
    generateQuestion(parentDiv) {
        parentDiv.querySelector(".quiz-title").innerHTML = this.title;
        parentDiv.querySelector("form").id = `question-${this.id}`

        let answerContainer = parentDiv.querySelector(".answers-container")
        answerContainer.innerHTML = "";
        document.querySelector("#details").innerHTML = "";
        this.answers.forEach((answer, index) => {
            let newDiv = `
                <label for="answer-${index}" class="answer-item">
                    <span>${answer.title}</span>
                    <input type=${this.isMultiple ? "checkbox" : "radio"} name="question-${this.id}" id="answer-${index}">
                    <span class="cb-checkmark"></span>
                </label>`
            answerContainer.innerHTML += newDiv
        })
        // Event listener adding the "selected" class on checked items.
        // Used for styling purposes (CSS).
        itemCheckListener(parentDiv)
    }

    // Corrects the user's answers after submission by adding classes to
    // correct and incorrect answers, then calculates the resulting points.
    correctQuestion(parentDiv) {
        let answerdivs = parentDiv.querySelectorAll(".answer-item")
        let answers = [];
        answerdivs.forEach((div, index) => {
            let checkbox = div.querySelector("input[type='checkbox'], input[type='radio']");
            answers.push(checkbox.checked)
            checkbox.disabled = true;
            if (this.answers[index].isTrue) {
                div.classList.add("right")
            }
            if (checkbox.checked && !(this.answers[index].isTrue)) {
                div.classList.add("wrong")
            }
        })
        document.querySelector("#details").innerHTML = this.details;
        currentGame.playerPoints += this.calculateScore(answers)
    } 
}

// ---------------------------------- //

// Initializes the game data.
let currentGame = new CurrentGame()
// Initializes all 5 questions based on json data.
let allQuestions = data.map((element, index) => {
    return new Question(element, index)
})

// --------------------------------- //

document.querySelector(".start-quiz").addEventListener("click", function () {
    document.querySelector(".start-quiz").remove()
    let parentDiv = document.querySelector(".quiz-container")
    parentDiv.classList.remove("hidden");
    allQuestions[0].generateQuestion(parentDiv)
})

// When clicking the submit btn on a question, displays the corrected form,
// hides the submit button and shows the "next question" button.
document.querySelector("form").addEventListener("submit", function (e) {
    e.preventDefault();
    allQuestions[currentGame.currentQuestionID].correctQuestion(document.querySelector(".quiz-container"));
    hideSubmitBtn();
})

// When clicked, displays the next question and shows the "submit" button.
// If the question was the last one, displays the result screen.
document.querySelector(".next").addEventListener("click", function () {
    currentGame.currentQuestionID++
    showSubmitBtn();
    currentGame.currentQuestionID < 5 ? 
        allQuestions[currentGame.currentQuestionID].generateQuestion(document.querySelector(".quiz-container")) :
        displayResults()
})

// Removes the quiz container and makes the result screen visible with 
// the correct number of points gained by the user.
function displayResults() {
    document.querySelector(".quiz-container").remove()
    let endDiv = document.querySelector(".end-quiz")
    endDiv.classList.remove("hidden")
    endDiv.querySelector(".points").innerHTML = currentGame.playerPoints;
}

// Adds the "selected" CSS class to all selected radio/checkbox inputs when clicked.
function itemCheckListener(parentDiv) {
    let answerContainer = parentDiv.querySelector(".answers-container")
    let chbDivs = answerContainer.querySelectorAll(".answer-item")
    if (chbDivs) {
        chbDivs.forEach(element => {
            let input = element.querySelector("input");
            input.addEventListener("change", function() {
                if (input.type == "radio") {
                    removeClassFromGroup(chbDivs, "selected")
                    element.classList.add("selected")
                } else {
                    element.classList.toggle("selected")
                }
            })
        });
    }
}

// Toggle the next question / submit buttons.
function hideSubmitBtn() {
    document.querySelector(".submit").classList.add("hidden");
    if (currentGame.currentQuestionID == allQuestions.length - 1) {
        document.querySelector(".next").innerHTML = "RESULTATS";
    }
    document.querySelector(".next").classList.remove("hidden")
}
function showSubmitBtn() {
    document.querySelector(".next").classList.add("hidden");
    document.querySelector(".submit").classList.remove("hidden")
}

function removeClassFromGroup(divgroup, classname) {
    if (divgroup) {
        divgroup.forEach(div => {
            div.classList.remove(classname)
        })
    }
}