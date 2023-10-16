import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

// Fonctions récupérant des inputs de l'utilisateur.
// Ces fonctions sont faites de sorte à ce que les erreurs renvoyées par un
// mauvais input soient le plus précises possibles.

public class Inputs {

    // Récupère un nombre (binaire, int, float ou double) de l'utilisateur sous forme de string.
    // Cette fonction réalise les checks de format de base et renvoie une string numérique valide.
    public static String getUserNumber(String inputType, String intro) {
        Scanner sc = new Scanner(System.in);
        String intRegex = "^ *[+-]?(\\d+) *$";
        String floatRegex = "^ *[+-]?(\\d+)(\\.\\d+)? *$";
        String binRegex = "^ *(-)? *([01] *){1,31} *$";
        String hexRegex = "^ *(-)? *([0-9a-fA-F] *){1,8} *$";
        String strInput;

        while (true) {
            System.out.print(intro);
            strInput = sc.nextLine();
            if (Objects.equals(inputType, "bin")) {
                if (!Pattern.compile(binRegex).matcher(strInput).matches()) {
                    System.out.print("/!\\ Nombre binaire invalide. [max = 31 digits + signe '-']\n\n");
                    continue;
                } else {
                    return strInput.replaceAll(" ", "");
                }
            }
            if (Objects.equals(inputType, "hex")) {
                if (!Pattern.compile(hexRegex).matcher(strInput).matches()) {
                    System.out.print("/!\\ Nombre hexadécimal invalide. [max = 8 digits + signe '-']\n\n");
                    continue;
                } else {
                    if (!hexIsValid(strInput)) {
                        System.out.print("/!\\ Nombre hexadécimal au-dessus des limites. [max = 80000000]\n\n");
                        continue;
                    } return strInput.replaceAll(" ", "");
                }
            }
            if (!Pattern.compile(floatRegex).matcher(strInput).matches()) {
                System.out.print("/!\\ Vous devez entrer un nombre.\n\n");
                continue;
            }
            if (Objects.equals(inputType, "int") && !Pattern.compile(intRegex).matcher(strInput).matches()) {
                System.out.print("/!\\ Vous devez entrer un nombre entier.\n\n");
                continue;
            }
            break;
        }
        return strInput;
    }

    // Récupère un int de la part de l'utilisateur, boucle tant que l'input est incorrect.
    // Si `useBoundaries` est `true`, considère que l'input est incorrect s'il est hors de [min:max].
    public static int getUserInt(String intro, Boolean useBoundaries, int min, int max) {
        int result;
        while (true) {
            String strInput = getUserNumber("int", intro);

            try {
                result = Integer.parseInt(strInput);
            } catch (java.lang.NumberFormatException ex) {
                System.out.print("/!\\ Input hors des limites d'un integer. [-2^31:2^31[\n\n");
                continue;
            }

            if (useBoundaries && !isInBounds(result, min, max)) {
                continue;
            }
            break;
        }
        return result;
    }

    // Récupère un double de la part de l'utilisateur, boucle tant que l'input est incorrect.
    // Si `useBoundaries` est `true`, considère que l'input est incorrect s'il est hors de [min:max].
    public static double getUserDouble(String intro, Boolean useBoundaries, double min, double max) {
        double result;
        while (true) {
            String strInput = getUserNumber("double", intro);

            try {
                result = Double.parseDouble(strInput);
            } catch (java.lang.NumberFormatException ex) {
                System.out.print("/!\\ Input hors des limites d'un double.\n\n");
                continue;
            }

            if (useBoundaries && !isInBounds(result, min, max)) {
                continue;
            }
            break;
        }
        return result;
    }

    // Récupère un float de la part de l'utilisateur, boucle tant que l'input est incorrect.
    // Si `useBoundaries` est `true`, considère que l'input est incorrect s'il est hors de [min:max].
    public static float getUserFloat(String intro, Boolean useBoundaries, float min, float max) {
        float result;
        while (true) {
            String strInput = getUserNumber("double", intro);

            try {
                result = Float.parseFloat(strInput);
            } catch (java.lang.NumberFormatException ex) {
                System.out.print("/!\\ Input hors des limites d'un float.\n\n");
                continue;
            }

            if (useBoundaries && !isInBounds(result, min, max)) {
                continue;
            }
            break;
        }
        return result;
    }

    // Renvoie `true` si `number` se trouve dans la range [`min`:`max`].
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

    public static Boolean hexIsValid(String hexValue) {
        hexValue = hexValue.replaceAll(" ", "");
        String invalidRegex = "^(-)?([8-9a-fA-F])([0-9a-fA-F] *){7}$";
        return !Pattern.compile(invalidRegex).matcher(hexValue).matches();
    }
}
