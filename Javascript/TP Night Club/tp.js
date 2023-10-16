function CheckAge(age) {
    return age >= 18 
}

// Vérifie l'âge et le genre d'un personne à l'entrée d'une boîte 
//  de nuit.
function CheckIdentity(age, gender) {
    if (!CheckAge(age)) {
        console.log("Sortez !")
        return
    }
    gender == "F" ? console.log("Tu peux entrer, c'est 10€") : console.log("Tu peux entrer, c'est 15€!")
}

// Affiche les clients d'une table
function ResTable(clients) {
    if (clients.length == 0) {
        console.log("Personne n'a réservé de table")
        return
    }

    console.log(`La table a été réservée pour ${clients.length} personne(s):`)
    clients.forEach((client, index) => {
        console.log(`${index+1} // ${client}`)
    })
}

ResTable("Marianne", "Kevin", "Evz")