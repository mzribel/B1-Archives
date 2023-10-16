package PUtils;

import PTP2.*;

public class Menu {
    public static int launchMenu(boolean binaryChoice, String[] options, String endSeparator) {
        int userInput;
        int choiceNumber = 1;

        if (!binaryChoice) {
            choiceNumber = options.length;
            for (var i = 0; i < options.length; i++) {
                // Print chaque option, accessible par un chiffre (index + 1 puisque 0 = retour).
                System.out.printf("%d | %s\n", i+1, options[i]);
            }
            System.out.println();
        } else {
            System.out.println("1 | Oui");
        }
        System.out.println("0 | Quitter");

        while(true) {
            userInput = Utils.getUserInt("→ ", false, 0, 0);
            if (userInput < 0 || userInput > choiceNumber) {
                System.out.printf("ERREUR: La valeur doit être comprise entre %d et %d.\n\n", 0, choiceNumber);
                continue;
            }
            break;
        }
        System.out.print(endSeparator);
        return userInput;
    }

    // Dessine une textbox de largeur `width` autour d'un texte `content`.
    // Si `title` n'est pas nul, est inscrit en haut à gauche de la textbox.
    public static void drawTitleBox(int width, String content, String title) {
        // Ajoute un espace de padding horizontal au texte `title` s'il existe:
        if (title.length() != 0) {
            title = " " + title + " ";
        }

        // Print les trois lignes successives de la boîte et son contenu:
        System.out.println("┌" + title + "─".repeat(width-title.length()) + "┐");
        System.out.println("|" + Utils.centerString(width, content) + "|");
        System.out.println("└" + "─".repeat(width) + "┘" + "\n");
    }

    // Menu principal du TP.
    public static void execute() {
        int userInput;
        String[] optionArr = {
                "Chiffrement", "Suite - Arctangente", "Permutations", "Seuil", "EDF"
        };
        do {
            drawTitleBox(48, "M E N U  P R I N C I P A L", "JAVA | TP n°2");
            userInput = Menu.launchMenu(false, optionArr, "\n");
            switch(userInput) {
                case 1 -> TP2_1_Chiffre.execute();
                case 2 -> TP2_2_SuiteArc.execute();
                case 3 -> TP2_3_Permutations.execute();
                case 4 -> TP2_4_Seuil.execute();
                case 5 -> TP2_5_EDF.execute();
            }
        } while (userInput != 0);
        System.out.println("Bonne journée !! ♪");
    }

    public static void main(String[] args) {
        Menu.execute();
    }
}
