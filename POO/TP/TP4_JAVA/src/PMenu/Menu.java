package PMenu;

import PBibliotheque.Bibliotheque;
import PUtils.Utils;

import java.util.ArrayList;

import static PBibliotheque.Bibliotheque.readFromFile;
import static PMenu.MenuBibliotheque.mainMenu;

public class Menu {

    public static void drawTitleBox(int width, String content, String title) {
        // Ajoute un espace de padding horizontal au texte `title` s'il existe:
        if (title.length() != 0) {
            title = " " + title + " ";
        }

        // Print les trois lignes successives de la boîte et son contenu:
        System.out.println("┌" + title + "─".repeat(width-title.length()) + "┐");
        System.out.println("|" + Utils.centerString(width, content) + "|");
        System.out.println("└" + "─".repeat(width) + "┘");
    }

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
    public static <T> int launchMenu(boolean binaryChoice, ArrayList<T> options, String endSeparator) {
        int userInput;
        int choiceNumber = 1;

        if (!binaryChoice) {
            choiceNumber = options.size();
            for (var i = 0; i < options.size(); i++) {
                // Print chaque option, accessible par un chiffre (index + 1 puisque 0 = retour).
                System.out.printf("%d | %s\n", i+1, options.get(i));
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


    public static void execute() {
        int userInput;
        String[] menuOptions = {"Importer une bibliothèque", "Créer une bibliothèque"};
        do {
            drawTitleBox(48, "M E N U  P R I N C I P A L", "JAVA | TP n°4");
            System.out.println();
            userInput = Menu.launchMenu(false, menuOptions, "\n");
            try {
                if (userInput == 1) {
                    System.out.println("NOTE: le fichier `exemple.json` est rempli avec des valeurs valides.\nL'autre contiendra les données sauvées par l'utilisateur.\n");
                    int fileID = Utils.getUserInt("Quel fichier importer ?\n1 | exemple.json\n2 | data.json\n   → ", true, 1, 2);
                    Bibliotheque data = fileID == 1 ? readFromFile("exemple.json") : readFromFile("data.json");
                    System.out.println();
                    mainMenu(data);
                } else if (userInput == 2) {
                    String userStr = Utils.getUserString("Quel nom donner à cette bibliothèque ?\n   → ", 1, 30);
                    mainMenu(new Bibliotheque(userStr));
                }
            } catch (Exception e) {
                System.out.println("/!\\ " +e.getMessage()+"\n");
                Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
            }
            System.out.println();
        } while (userInput != 0);
    }
}
