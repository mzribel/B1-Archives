import java.math.RoundingMode;
import java.text.DecimalFormat;

// Contient des fonctions communes à tous les TP mais pas forcément demandées par la notation.
// Ce sont des fonctions "utilitaires" type menus, formattage, etc.
public class Utils {

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

    // Fonction créée pour systématiquement éviter les erreurs de type InterruptedExecution.
    public static void sleepTime(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // ------------------------------------------------- //
    // -------------------- INPUTS --------------------- //
    // --------------------------------------------------//



}