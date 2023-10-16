package PTP2;
import PUtils.Menu;
import PUtils.Utils;
import java.util.Scanner;

// NOTE: L'exercice a été compris et réalisé comme un Vigénère simple à l'origine
// (seules les lettres étaient altérées, pas la casse ni les chiffres/symboles).
// Il a ensuite été réalisé selon la consigne, avec la table ASCII complète.
// Pour ne pas "jeter" l'ancienne fonction, elle est toujours disponible dans le menu.
public class TP2_1_Chiffre {

    // Permet de chiffrer une chaîne de caractères selon le principe du chiffre de Vigénère.
    // Si `isAlphaOnly` est true, seuls les caractères alphabétiques de la clé et de la cible
    // seront lus et altérés. A l'inverse, la table ASCII entière est utilisée.
    public static String romanCipher(String base, String key, boolean isAlphaOnly){
        if (base.length() == 0 || key.length() == 0) {
            return base;
        }

        StringBuilder cipheredString = new StringBuilder();
        for (int i = 0; i < base.length(); i++) {
            char cipheredChar = cipherLetter(base.charAt(i), key.charAt(i % key.length()), isAlphaOnly);
            cipheredString.append(cipheredChar);
        }
        return cipheredString.toString();
    }

    // Chiffre un caractère `base` en utilisant le caractère `clé`.
    // Renvoie le caractère non-altéré si la base ou la clé ne correspondent pas
    // à `isAlphaOnly`.
    public static char cipherLetter(char base, char key, boolean isAlphaOnly) {
        if (!isAlphaOnly) {
            return (!Utils.isAsciiPrintable(base) || !Utils.isAsciiPrintable(key)) ?
                    base : (char) ((base + key - 64) % 96 + 32);
        }
        if (!Utils.isLetter(base) || !Utils.isLetter(key)) {
            return base;
        }
        char cypheredLetter = (char) ((Utils.getLetterPosition(base) + Utils.getLetterPosition(key)) % 26 + 65);
        return Character.isUpperCase(base) ?
                cypheredLetter : Character.toLowerCase(cypheredLetter);
    }

    // Déchiffre un caractère `base` en utilisant le caractère `clé`.
    // Renvoie le caractère non-altéré si la base ou la clé ne correspondent pas
    // à `isAlphaOnly`.
    public static char decipherLetter(char base, char key, boolean isAlphaOnly) {
        if (!isAlphaOnly) {
            return (!Utils.isAsciiPrintable(base) || !Utils.isAsciiPrintable(key)) ?
                    base : (char)((96 + (base - 32) - (key - 32)) % 96 + 32);
        }
        if (!Utils.isLetter(base) || !Utils.isLetter(key)) {
            return base;
        }
        char cypheredLetter = (char) ((26 + (Utils.getLetterPosition(base) - Utils.getLetterPosition(key))) % 26 + 65);
        return Character.isUpperCase(base) ?
                cypheredLetter : Character.toLowerCase(cypheredLetter);
    }

    // Permet de déchiffrer une chaîne de caractères selon le principe du chiffre de Vigénère.
    // Si `isAlphaOnly` est true, seuls les caractères alphabétiques de la clé et de la cible
    // seront lus et altérés. A l'inverse, la table ASCII entière est utilisée.
    public static String romanDecipher(String base, String key, boolean isAlphaOnly) {
        if (base.length() == 0 || key.length() == 0) {
            return base;
        }
        int currKeyIndex = 0;
        StringBuilder decipheredString = new StringBuilder();
        for (int i = 0; i < base.length(); i++) {
                decipheredString.append(decipherLetter(base.charAt(i), key.charAt(currKeyIndex),isAlphaOnly));

                currKeyIndex++;
                if (currKeyIndex >= key.length()) {
                    currKeyIndex = 0;
                }
            }
        return decipheredString.toString();
    }

    // Menu associé au programme.
    public static void execute() {
        String[] menuOptions = {"Chiffrer (Alphabétique)", "Déchiffrer (Alphabétique)", "Chiffrer (ASCII)", "Déchiffrer (ASCII)"};
        int chosenOption, userRetry;
        Menu.drawTitleBox(48, "C H I F F R E  R O M A I N", "JAVA - TP 2.1");
        do {
            chosenOption = Menu.launchMenu(false, menuOptions, "\n");
            if (chosenOption == 0) {
                break;
            }
            System.out.printf(">>> %s <<<\n", menuOptions[chosenOption-1].toUpperCase());
            Scanner sc = new Scanner(System.in);
            System.out.print("Texte brut:\n  → ");
            String baseStr = sc.nextLine(); // Récupère le texte brut de l'utilisateur.
            System.out.print("Clé de chiffrement:\n  → ");
            String keyStr = sc.nextLine();  // Récupère la clé de l'utilisateur.
            String result = null;
            switch (chosenOption) {
                // Réalise la bonne opération en fonction de l'option choisie.
                case 1 -> result = romanCipher(baseStr, keyStr, true);
                case 2 -> result = romanDecipher(baseStr, keyStr, true);
                case 3 -> result = romanCipher(baseStr, keyStr, false);
                case 4 -> result = romanDecipher(baseStr, keyStr, false);
            }
            if (result.equals("")) {
                result = "[vide]";
            }
            // Renvoie le résultat à l'utilisateur.
            System.out.printf("Résultat:\n  → %s\n\n", result);

            System.out.println("Réaliser une autre opération ?");
            userRetry = Menu.launchMenu(true, null, "\n");
        } while (userRetry != 0);
    }

    // Tests unitaires des fonctions du programme.
    public static void testTP2_1() {
        String base = "Voix Ambigue D'Un Coeur Qui, Au Zephyr, Prefere Les Jattes De Kiwis ♪";
        System.out.println("--- Début des tests (TP2_2_SuiteArc) ---");

        System.out.println("Réalisation des tests sur romanCipher()...");
        int successCount = 0;

        // Clé nulle: renvoie la base.
        if (romanCipher("nonNullBase", "", true).equals("nonNullBase")) {
         successCount++;
        }
        // Base nulle: renvoie une string vide.
        if (romanCipher("", "nonNullKey", true).equals("")) {
            successCount++;
        }

        // Chiffrement (caractères alphabétiques seulement), clé valide.
        String temp = "Xovx Dobvglh D'Ue Eorui Suv, Dw Megkar, Gugfrrv Nef Advtrs Gg Xinlu ♪";
        if (romanCipher(base, "canard", true).equals(temp)) {
            successCount++;
        }
        // Chiffrement (caractères alphabétiques seulement), clé invalide.
        if (romanCipher(base, "è_-/;'éè_-'", true).equals(base)) {
            successCount++;
        }

        // Chiffrement (table ascii, car. imprimables), clé valide et alphabétique.
        temp = "9PWYr%PCWHgIc%u6`d&PSVdd4VWmr%XaHFbL\\SzaBVHGSSWd/Faa<EWUSTr(Ha9JiMVa♪";
        if (romanCipher(base, "canard", false).equals(temp)) {
            successCount++;
        }
        // Chiffrement (table ascii, car. imprimables), clé valide en ascii.
        temp = "9NWWr%PAWFgIc#u4`d&NSTdd4TWkr%X_HDbL\\Qz_BVHESQWd/Da_<EWSSRr(H_9HiMV_♪";
        if (romanCipher(base, "c_n_rd", false).equals(temp)) {
            successCount++;
        }
        // Chiffrement (table ascii, car. imprimables), clé invalide.
        if (romanCipher(base, "♪♫", false).equals(base)) {
            successCount++;
        }
        System.out.printf("Nombre de tests réussis: %d / 7.\n---\n", successCount);

        System.out.println("Réalisation des tests sur romanDecipher()...");
        successCount = 0;
        // Clé nulle: renvoie la base.
        if (romanDecipher("nonNullBase", "", true).equals("nonNullBase")) {
            successCount++;
        }
        // Base nulle: renvoie une string vide.
        if (romanDecipher("", "nonNullKey", true).equals("")) {
            successCount++;
        }

        // Chiffrement (caractères alphabétiques seulement), clé valide.
        temp = "Xovx Dobvglh D'Ue Eorui Suv, Dw Megkar, Gugfrrv Nef Advtrs Gg Xinlu ♪";
        if (romanDecipher(temp, "canard", true).equals(base)) {
            successCount++;
        }
        // Chiffrement (caractères alphabétiques seulement), clé invalide.
        if (romanDecipher(base, "è_-/;'éè_-'", true).equals(base)) {
            successCount++;
        }

        // Chiffrement (table ascii, car. imprimables), clé valide et alphabétique.
        temp = "9PWYr%PCWHgIc%u6`d&PSVdd4VWmr%XaHFbL\\SzaBVHGSSWd/Faa<EWUSTr(Ha9JiMVa♪";
        if (romanDecipher(temp, "canard", false).equals(base)) {
            successCount++;
        }
        // Chiffrement (table ascii, car. imprimables), clé valide en ascii.
        temp = "9NWWr%PAWFgIc#u4`d&NSTdd4TWkr%X_HDbL\\Qz_BVHESQWd/Da_<EWSSRr(H_9HiMV_♪";
        if (romanDecipher(temp, "c_n_rd", false).equals(base)) {
            successCount++;
        }
        // Chiffrement (table ascii, car. imprimables), clé invalide.
        if (romanDecipher(base, "♪♫", false).equals(base)) {
            successCount++;
        }
        System.out.printf("Nombre de tests réussis: %d / 7.\n", successCount);
        System.out.println("--- Fin des tests ---");
    }

    public static void main(String[] args) {
        testTP2_1();
    }
}
