package PMenu;

import PBibliotheque.Bibliotheque;
import PUtils.Utils;

import java.io.IOException;

import static PMenu.Menu.drawTitleBox;
import static PMenu.Menu.launchMenu;
import static PMenu.MenuAdherent.MenuGestionAdherents;
import static PMenu.MenuLivre.MenuGestionLivres;
import static PMenu.MenuPrets.MenuGestionPrets;

public class MenuBibliotheque {
    public static void mainMenu(Bibliotheque data) {
        int userInput;
        String[] menuOptions = {"Afficher les données générales", "Gestion des livres", "Gestion des adhérents", "Gestion des prêts et réservations", "Sauvegarder la bibliothèque"};
        do {
            drawTitleBox(48, "G E S T I O N  D E  B I B L I O T H E Q U E", "JAVA | TP n°4");
            System.out.println("Bibliothèque en cours d'édition: " + data.name + "\n");
            userInput = launchMenu(false, menuOptions, "\n");

            switch (userInput) {
                case 1 -> {
                    drawTitleBox(48, "V U E  D E T A I L L E E", "Gestion de bibliothèque");
                    System.out.println();
                    data.displayDetails();
                    System.out.println();
                    Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                }
                case 2 -> MenuGestionLivres(data);
                case 3 -> MenuGestionAdherents(data);
                case 4 -> MenuGestionPrets(data);
                case 5 -> {
                    System.out.println("La bibliothèque sera sauvegardée dans le fichier `src/data/data.json`");
                    System.out.println("Voulez-vous continuer ?");
                    int tempUserInput = launchMenu(true, (String[]) null, "");
                    System.out.println();
                    if (tempUserInput == 0) {
                        continue;
                    }
                    try {
                        data.saveInFile("data.json");
                    } catch (IOException e) {
                        System.out.println("/!\\ Une erreur est survenue: " + e.getMessage());
                        continue;
                    }
                    Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                }
            }
            System.out.println();
        } while (userInput != 0);
    }
}

