// Stocke les cinq élements "équipes"
const btns = document.querySelectorAll(".team-item")

btns.forEach(btn => {
    let input = btn.querySelector("input")
    // "Surveille" les cinq radios basé sur leur changement (checked ou non).
    input.addEventListener("change", function () {
        // Retire la classe "active" de tous les boutons, 
        // puis la rajoute au bouton qui vient d'être cliqué.
        removeClassFromList("active", btns)
        btn.classList.add("active")
        // Change les valeurs des sources de la vidéo et de l'image d'équipe.
        changeMediaSrc(input.value)
    })
})

function removeClassFromList(classname, divList) {
    divList.forEach(element => {
        element.classList.remove(classname)
    })
}

function changeMediaSrc(value) {
    document.querySelector("video").src = `assets/video/highlight-${value}.mp4`
    document.querySelector("#team-img").src = `assets/img/teams/${value}.png`
}