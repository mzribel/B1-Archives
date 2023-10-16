package PUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Utils {

    // Récupère un int de l'utilisateur, y applique toutes les vérifications.
    // Si `useBoundaries` est true, un input hors de `min` et `max` sera considéré
    // comme faux.
    public static int getUserInt(String intro, boolean useBoundaries, int min, int max) {
        Scanner sc = new Scanner(System.in);
        int userInput;
        while (true) {
            System.out.print(intro);
            String userStr = sc.nextLine();
            try {
                userInput = Integer.parseInt(userStr);
            } catch (NumberFormatException e) {
                System.out.println("ERREUR: Vous devez entrer un nombre entier.\n");
                continue;
            }

            if (useBoundaries && !isInBounds(userInput, min, max)) {
                continue;
            }
            break;
        }
        return userInput;
    }

    // Vérifie que `number` se trouve dans les limites de `min` et `max`.
    // Si `min` > `max`, seul `min` est pris en compte.
    public static Boolean isInBounds(double number, double min, double max) {
        if (min > max && number < min) {
            System.out.printf("/!\\ Input inférieur au minimum autorisé. [min=%s]\n\n", Utils.roundNumber(min, 2));
            return false;
        } else if (max >= min && !(number >= min && number <= max)) {
            System.out.printf("/!\\ Input hors de l'intervale autorisé [%s:%s].\n\n", Utils.roundNumber(min, 2), Utils.roundNumber(max, 2));
            return false;
        }
        return true;
    }

    // Récupère un double de l'utilisateur, y applique toutes les vérifications.
    // Si `useBoundaries` est true, un input hors de `min` et `max` sera considéré
    // comme faux.
    public static double getUserDouble(String intro, boolean useBoundaries, double min, double max) {
        Scanner sc = new Scanner(System.in);
        double userInput;
        while (true) {
            System.out.print(intro);
            String userStr = sc.nextLine();
            try {
                userInput = Double.parseDouble(userStr);
            } catch (NumberFormatException e) {
                System.out.println("ERREUR: Vous devez entrer un nombre.\n");
                continue;
            }

            if (useBoundaries && !isInBounds(userInput, min, max)) {
                continue;
            }
            break;
        }
        return userInput;
    }

    // Ajoute des espaces de padding autour d'une string `s` pour que sa
    // longueur soit égale à `width` caractères minimum.
    public static String centerString(int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    // Arrondit un nombre à `decimals` décimales.
    // Si le nombre est entier, supprime les décimales.
    public static String roundNumber(double number, int decimals) {
        String pattern = decimals < 1 ? "0" : "0." + "#".repeat(decimals);
        DecimalFormat format = new DecimalFormat(pattern);
        format.setRoundingMode(RoundingMode.HALF_EVEN);
        return format.format(number);
    }

    // Récupère la position de la lettre dans l'alphabet.
    public static int getLetterPosition(char letter) {
        if (!Character.isLetter((letter))) {
            return -1;
        }
        return (int)Character.toUpperCase(letter) - 65;
    }

    // Renvoie `true` si le caractère est un char ASCII imprimable.
    public static boolean isAsciiPrintable(char base) {
        return (base > 31 && base < 127);
    }

    // Renvoie `true` si le caractère est une lettre du tableau ASCII.
    public static boolean isLetter(char base) {
        return (base > 64 && base < 91) || (base > 96 && base < 122);
    }
}
