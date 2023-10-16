public class JustePrix {

    // Génère un nombre aléatoire situé entre `min` et `max` tous deux inclus.
    public static int generateRandomInt(int min, int max) {
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    // Gère une partie de Juste Prix.
    // `maxAttempts` définit le nombre d'essais maximum pour le joueur.
    // `minBound` et `maxBound` définissent les limites du chiffre à deviner.
    // Plus la différence entre ces deux valeurs est grande et le nombre d'essai petit,
    // plus le jeu sera difficile.
    // `debug` est une variable permettant de print le nombre dès le départ de la partie.
    public static void playGame(int maxAttempts, int minBound, int maxBound, boolean debug) {
        Utils.drawTitleBox(48, "L E  J U S T E  P R I X", "POO | TP n°1", 0);
        // Génère le nombre à deviner.
        int number = generateRandomInt(-100, 100);
        if (debug) {
            System.out.printf("DEBUG MODE ON || Nombre généré: %s\n\n", number);
        }
        // userAttempts est la valeur "contraire" à maxAttempts.
        // Deux variables sont utilisées pour traquer le nombre d'essais à la fin de la partie.
        int userAttempts = 1;
        int userGuess;

        // Cette boucle s'exécute tant que le joueur peut encore jouer.
        // Si le joueur gagne, la fonction s'arrête immédiatement.
        for (; userAttempts <= maxAttempts; userAttempts++) {
            System.out.printf("\nATTEMPTS LEFT: %d\n", maxAttempts +1 - userAttempts);
            userGuess = Utils.askForInt(minBound, maxBound, "Quel est le nombre mystère ? → ", false);

            // Victoire: le joueur a trouvé le nombre mystère.
            if (userGuess == number) {
                System.out.printf("\nFélicitations !! C'est une victoire en %d essai(s) !\n", userAttempts);
                Utils.sleepTime(1200);
                return;
            // Round perdu: chiffre deviné trop grand ou trop petit.
            } else if (userGuess > number) {
                System.out.println("C'est plus petit !");
            } else {
                System.out.println("C'est plus grand !");
            }
        }

        // Défaite: le joueur n'a pas trouvé le nombre dans son nombre d'essais impartis.
        System.out.printf("\nRaté... Le nombre mystère était %d.\n", number);
        Utils.sleepTime(1200);
    }

    public static void main(String[] args) {
        // Pour une UX plus fluide, le titleBox se trouve en haut de la fonction `playGame` et non ici.
        System.out.println();

        // Définit la valeur par défaut pour le menu Retry.
        // Les valeurs ne peuvent être que 0 et 1, -1 initialise la variable pour que la boucle while
        // se lance, avec une valeur qui ne peut pas bloquer son exécution.
        int retry = -1;
        while (retry != 0) {
            // Lance le jeu.
            // `debug` permet de print le nombre généré en haut du jeu.
            playGame(10, -100, 100, false);

            // Retry: 1 relance la fonction depuis le début (boucle while), 0 l'arrête
            // et revient au menu précédent.
            System.out.println("Lancer une nouvelle partie ?\n");
            Menu.runMenu(true, null);
            retry = Utils.askForInt(0, 1, "→ ", false);
            System.out.println();
        }
    }
}
