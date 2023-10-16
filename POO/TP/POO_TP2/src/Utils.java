import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

// Contient des fonctions communes aux trois TP mais pas forcément demandées par la notation.
// Ce sont des fonctions "utilitaires" type menus, formattage, etc.
public class Utils {

    // Demande à l'utilisateur un nombre potentiellement décimal et vérifie s'il se situe dans la range acceptée.
    public static float askForFloat(int min, int max, String intro) {
        Scanner sc = new Scanner(System.in);
        // Cherche un nombre positif ou négatif avec ou sans décimales, placé entre des potentiels espaces.
        String regex = "^ *[+-]?([0-9]*[.])?[0-9]+ *$";
        float number;

        System.out.print(intro);
        String input = sc.nextLine();

        while (true) {
            // Pattern non trouvé dans l'input de l'utilisateur:
            if (!Pattern.compile(regex).matcher(input).matches()) {
                System.out.print("/!\\Entrer une valeur valide. " + intro);
                input = sc.nextLine();
                continue;
            }

            // Pattern trouvé : l'input est au minimum du bon type et peut être parsé:
            number = Float.parseFloat(input);

            // Input hors range fournie:
            if (number < min || number > max) {
                System.out.printf("Valeurs acceptées: [%d:%d] | %s", min, max, intro);
                input = sc.nextLine();
                continue;
            }
            break;
        }
        return number;
    }
    // Demande à l'utilisateur un nombre entier et vérifie s'il se situe dans la range acceptée.
    // Si la fonction ressemble à celle du dessus, implémenter une version factorisée s'avérerait
    // relativement complexe.
    public static int askForInt(int min, int max, String intro, boolean quittable) {
        Scanner sc = new Scanner(System.in);
        // Cherche un nombre positif ou négatif, placé entre des potentiels espaces.
        String regex = "^ *[+-]?(\\d+) *$";
        // Cherche un nombre décimal.
        String invalidRegex = "^ *[+-]?(\\d+\\.\\d*) *$";
        int number;

        System.out.print(intro);
        String input = sc.nextLine();

        while (true) {
            // Pattern non trouvé dans l'input de l'utilisateur:
            if (Pattern.compile(invalidRegex).matcher(input).matches()) {
                System.out.print("/!\\ On ne veut que des chiffres entiers !!\n\n" + intro);
                input = sc.nextLine();
                continue;
            }
            else if (!Pattern.compile(regex).matcher(input).matches()) {
                System.out.print("/!\\ Il faudrait peut-être essayer avec un chiffre...\n\n" + intro);
                input = sc.nextLine();
                continue;
            }

            // Pattern trouvé : l'input est au minimum du bon type et peut être parsé:
            number = Integer.parseInt(input);

            // Input hors range fournie:
            if (number < min || number > max) {
                System.out.printf("Ce nombre est un poil petit, ou grand..? [%d:%d]\n\n%s", min, max, intro);
                input = sc.nextLine();
                continue;
            }
            break;
        }
        // Tada !
        return number;
    }

    // Arrondit un nombre à la valeur la plus proche.
    // Le paramètre `decimals` détermine le nombre de décimales de précision du résultat.
    public static String roundNumber(double number, int decimals) {
        String pattern = decimals < 1 ? "0" : "0." + "#".repeat(decimals);

        DecimalFormat format = new DecimalFormat(pattern);
        // Le roundingMode HALF_EVEN arrondit à la valeur la plus proche et à la valeur supérieure
        // s'il a le choix entre deux.
        format.setRoundingMode(RoundingMode.HALF_EVEN);

        return format.format(number);
    }

    // Dessine une boîte de `width` x 3 pour y mettre des titres.
    // Attention la fonction ne gère pas le dépassement des textes par rapport à width.
    public static void drawTitleBox(int width, String content, String title, int newLinesAfter) {

        // Ajoute un espace de padding horizontal au texte `title` s'il existe:
        if (title.length() != 0) {
            title = " " + title + " ";
        }

        // Positionne le texte `content au centre de la boîte:
        int missing = width - content.length();
        if (missing <= 0) {
            missing = 0;
        }
        content = " ".repeat(missing/2) + content + " ".repeat(missing/2+missing%2);

        // Print les trois lignes successives de la boîte et son contenu:
        System.out.println("┌" + title + "─".repeat(width-title.length()) + "┐");
        System.out.println("|" + content + "|");
        System.out.println("└" + "─".repeat(width) + "┘" + "\n".repeat(newLinesAfter));
    }

    // Fonction créée pour systématiquement éviter les erreurs de type InterruptedExecution
    // qui semblent se produire lorsque la fonction est appelée près de la fin d'un bloc
    // d'exécution.
    public static void sleepTime(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
