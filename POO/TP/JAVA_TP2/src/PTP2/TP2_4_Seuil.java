package PTP2;

import PUtils.Menu;
import PUtils.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TP2_4_Seuil {
    // Exception personnalisée, liée à la classe TP2_4_Seuil.
    public static class ExceptionSeuil extends Exception {
        public ExceptionSeuil(String message) { super(message); }
    }

    // Renvoie toutes les valeurs intermédiaires de SeuilArray dans un tableau
    // de doubles.
    public static double[] SeuilInt(double P, double T) throws ExceptionSeuil {
        if (T <= 1) {
            throw new ExceptionSeuil("Le taux ne doit pas être inférieur ou égal à 1.");
        }
        if (P >= 1000) {
            return new double[0];
        }
        List<Double> list = new ArrayList<>();
        do {
            P *= T;
            list.add(P);
        } while (P < 1000);
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    // Renvoie le nombre d'itérations nécessaires pour que `P` atteigne 1000
    // en étant multiplié par T à chaque fois.
    public static int SeuilArray(double P, double T) throws ExceptionSeuil {
        int result = 0;
        if (T <= 1) { // Renvoie une exception pour éviter une boucle infinie.
            throw new ExceptionSeuil("Le taux ne doit pas être inférieur ou égal à 1.");
        }
        if (P >= 1000) { // P déjà supérieur à 1000, 0 itération nécessaire.
            return result;
        }
        do {
            P *= T;
            result++;
        } while (P < 1000);
        return result;
    }


    // Dessine les lignes horizontales séparatrices d'un tableau de valeurs.
    // Toutes les cellules (* `cellNb`) font `cellWidth` de large.
    // Les caractères de séparation sont personnalisables.
    public static void drawTableLine(int cellWidth, int cellNb, String startChar, String midChar, String midLineChar, String endChar) {
        System.out.print(startChar);
        for (int i = 0; i < cellNb; i++) {
            if (i == cellNb - 1) {
                System.out.printf("%s%s%n", midChar.repeat(cellWidth), endChar);
                continue;
            }
            System.out.printf("%s%s", midChar.repeat(cellWidth), midLineChar);
        }
    }

    // Dessine le tableau lié aux valeurs renvoyées par SeuilInt().
    public static void drawPriceTable(double basePrice, double[] table) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        if (basePrice <= 0) {
            return;
        }
        drawTableLine(10, 2, "┌", "─", "┬", "┐");
        System.out.printf("|%s|%s|\n",Utils.centerString(10, "PRIX"), Utils.centerString(10, "MOIS"));
        drawTableLine(10, 2, "├", "─", "┼", "┤");
        System.out.printf("|%9s |%9s |\n", decimalFormat.format(basePrice), "0");
        if (table.length < 50) {
            for (int i = 0; i < table.length; i++) {
                System.out.printf("|%9s |%9s |\n", decimalFormat.format(table[i]), i+1);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                System.out.printf("|%9s |%9s |\n", decimalFormat.format(table[i]), i+1);
            }
            System.out.printf("|%9s |%9s |\n", "... ", "... ");
            for (int i = 10; i >= 1; i--) {
                System.out.printf("|%9s |%9s |\n", decimalFormat.format(table[table.length-i]), table.length-i+1);
            }
        }
        drawTableLine(10, 2, "└", "─", "┴", "┘");
        System.out.println("Nmax = " + table.length);
    }

    // Menu associé au programme.
    public static void execute() {
            int chosenOption;
            int userRetry;
            double[] result;
            String[] menuOptions = {"Calculer le nombre de mois seulement", "Calculer et afficher le tableau"};
            Menu.drawTitleBox(48, "S E U I L", "JAVA - TP 2.4");
            do {

                chosenOption = Menu.launchMenu(false, menuOptions, "\n");
                if (chosenOption == 0) {
                    break;
                }
                double P = Utils.getUserDouble("Prix de base: ", true, 0.01, 999.99);
                double T = Utils.getUserDouble("Taux d'inflation (format 1.##): ", true, 0.01, 10);

                try {
                    switch (chosenOption) {
                        case 1 -> {
                            System.out.print("\n>>> RESULTATS <<<\n");
                            System.out.printf("Pour P=%s et T=%s, Nmax=%d.\n", Utils.roundNumber(P, 2), Utils.roundNumber(T, 2), SeuilArray(P, T));
                        }
                        case 2 -> {
                            result = SeuilInt(P, T);
                            drawPriceTable(P, result);
                        }
                    }
                } catch (TP2_4_Seuil.ExceptionSeuil e) {
                    System.out.println("ERREUR: " + e.getMessage());
                }
                System.out.println("\nRecommencer ?");
                userRetry = Menu.launchMenu(true, null, "\n");
            } while (userRetry != 0);

    }

    // Tests unitaires des fonctions du programme.
    public static void test_TP2_4() {
        System.out.println("--- Début des tests (TP2_4_Seuil) ---");
        int successCount = 0;
        double[] test, testCopy;
        System.out.println("Réalisation des tests sur SeuilArray()...");

        // Taux inférieur à 1.
        try {
            SeuilArray(300, 1);
        } catch (ExceptionSeuil e) {
            successCount++;
        }
        // Prix déjà égal ou supérieur à 1000.
        try {
            if (SeuilArray(1000, 1.08) == 0) {
                successCount++;
            }
        } catch (ExceptionSeuil ignored){}
        // Valeurs valides.
        try {
            if (SeuilArray(300, 1.08) == 16) {
                successCount++;
            }
        } catch (ExceptionSeuil ignored){}
        System.out.printf("Nombre de tests réussis: %d / 3.\n", successCount);
        System.out.println("--- Fin des tests ---");
    }

    public static void main(String[] args) throws ExceptionSeuil {
        test_TP2_4();
    }
}
