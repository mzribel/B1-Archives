public class Menu {

    // Crée les menus contextuels.
    // Si `binaryChoice` est true, le programme ignorera le contenu de `options`.
    // Un menu binaire implique un choix entre "Oui" et "Quitter" tandis que l'inverse
    // implique un choix entre les éléments de `options` et "Quitter".
    public static void runMenu(boolean binaryChoice, String[] options) {
        if (!binaryChoice) {
            for (var i = 0; i < options.length; i++) {
                // Print chaque option, accessible par un chiffre (index + 1 puisque 0 = retour).
                System.out.printf("%d// %s\n", i+1, options[i]);
            }
            System.out.println();
        } else {
            System.out.println("1// Oui");
        }
        System.out.println("0// Quitter");
    }

    public static void main(String[] args) {
        // Contient les options du main menu.
        String[] arr = {"Exercice 1: Surface d'un polygone", "Exercice 2: Juste Prix", "Exercice 3: Sapin de Noël"};

        // `chosenOption` est initialisé pour que la boucle while se lance, mais la valeur
        // d'initialisation n'a pas d'utilité tant qu'elle ne peut pas parasiter le while qui la suit.
        int chosenOption = -1;

        // Le menu se répète tant que l'utilisateur n'a pas choisi d'en sortir.
        while (chosenOption != 0) {
            Utils.drawTitleBox(48, "M E N U  P R I N C I P A L", "POO | TP n°1", 1);
            runMenu(false, arr);

            chosenOption = Utils.askForInt(0, arr.length, "→ ", false);
            switch (chosenOption) {
                case 1 -> CalculateArea.main(args);
                case 2 -> JustePrix.main(args);
                case 3 -> ChristmasTree.main(args);
            }
        }

        // Se lance lorsque l'utilisateur a décidé de stopper le programme.
        System.out.println("\nBonne journée !! ♪");
        Utils.sleepTime(1000);
    }
}
