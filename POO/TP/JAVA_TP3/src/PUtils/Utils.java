package PUtils;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Utils {
    public static DecimalFormat df = new DecimalFormat("0.##");

    public static class InvalidInformationException extends Exception {
        public InvalidInformationException(String errorMessage) {
            super(errorMessage);
        }
    }
    public static class ElementAlreadyExistsException extends Exception {
        public ElementAlreadyExistsException(String errorMessage) {
            super(errorMessage);
        }
    }

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

    public static String getUserString(String intro, int min, int max) {
        Scanner sc = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print(intro);
            userInput = sc.nextLine();
            if (userInput.length() > max || userInput.length() < min) {
                System.out.printf("/!\\ Nombre de caractères invalide. [%d:%d].\n\n", min, max);
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
            System.out.printf("/!\\ Input inférieur au minimum autorisé. [min=%s]\n\n", df.format(min));
            return false;
        } else if (max >= min && !(number >= min && number <= max)) {
            System.out.printf("/!\\ Input hors de l'intervale autorisé [%s:%s].\n\n", df.format(min), df.format(max));
            return false;
        }
        return true;
    }

    public static String centerString(int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
