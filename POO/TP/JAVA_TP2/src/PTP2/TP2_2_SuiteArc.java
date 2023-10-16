package PTP2;

import PUtils.Menu;
import PUtils.Utils;

import java.text.DecimalFormat;
import java.util.Objects;

public class TP2_2_SuiteArc {

    // Exception personnalisée, liée à la classe TP2_2_SuiteArc.
    public static class ExceptionSuiteArc extends Exception {
        public ExceptionSuiteArc(String message) {
            super(message);
        }
    }

    // "Copie" de la fonction Math.pow(). Prend une base et un exposant en paramètres.
    // La fonction gère les valeurs négatives.
    public static double myPow(double base, int exp) {
        double result = 1;
        if (exp == 0) {
            return result;
        }

        boolean expIsNegative = false;
        if (exp < 0) {
            expIsNegative = true;
            exp = Math.abs(exp);
        }
        for (int i = 0; i < exp; i++) {
            result *= base;
        }
        return expIsNegative ? 1 / result : result;
    }

    // Implémentation de la suite supposée converger vers atan(x) pour x autour de 0.
    // N représente le nombre d'occurences de la suite, doit être supérieur à 50.
    public static double suite(double x, int N) throws ExceptionSuiteArc {
        if (N <= 50) { // Envoie une Exception si N est invalide.
            throw new TP2_2_SuiteArc.ExceptionSuiteArc("N doit être supérieur à 50.");
        }
        double curr = x;
        for (int i = 1; i <= N; i++) {
            int tmp = 2 * i + 1;
            curr += myPow(-1, i) * myPow(x, tmp) / (tmp);
        }
        return curr;
    }

    // Menu associé au programme.
    public static void execute() {
        Menu.drawTitleBox(48, "S U I T E - A R C T A N", "JAVA - TP 2.2");
        System.out.println("Cette fonction compare le résultat de\nUn = Un-1 + (-1)^n * x^(2*n-1) / ( 2*n-1) pour u0 = x\navec le résultat de arctan(x).\n");
        int userRetry;
        double result;
        do {
            // Récupère x et N de l'utilisateur.
            double x = Utils.getUserDouble("Valeur de x (doit être autour de 0): ", false, 0, 0);
            int N = Utils.getUserInt("Valeur de N (doit être > 50): ", true, 50, -1);
            // Réalise le calcul et récupère l'erreur s'il y a.
            try {
                result = suite(x, N);
                System.out.print("\n>>> RESULTATS <<<\n");
                System.out.printf("Suite: %f\nArctan(x): %f\n", result, Math.atan(x));
            } catch (TP2_2_SuiteArc.ExceptionSuiteArc e) {
                System.out.println("ERREUR: " + e.getMessage());
            }
            System.out.println();
            System.out.println("Recommencer ?");
            userRetry = Menu.launchMenu(true, null, "\n");
        } while (userRetry != 0);
    }

    // Tests unitaires des fonctions du programme.
    public static void testTP2_2() {
        DecimalFormat decimalformat = new DecimalFormat("#.################");
        System.out.println("--- Début des tests (TP2_2_SuiteArc) ---");

        System.out.println("Réalisation des tests sur myPow()...");
        // Compare le résultat de myPow avec base et exp. positif/0/négatif avec le
        // résultat du built-in Math.pow.
        int[] myPowTestValues = {5, 0, -5};
        int successCount = 0;
        for (int myPowTestValue : myPowTestValues) {
            for (int powTestValue : myPowTestValues) {
                successCount += myPow(myPowTestValue, powTestValue) == Math.pow(myPowTestValue, powTestValue) ? 1 : 0;
            }
        }
        System.out.printf("Nombre de tests réussis: %d / 9.\n---\n", successCount);

        System.out.println("Réalisation des tests sur suite()...");
        successCount = 0;
        // Vérifie que l'exception se lance si N est inférieur à 50.
        try {
            suite(.02, 1);
        } catch (ExceptionSuiteArc e) {
            successCount++;
        }
        // Vérifie que le résultat de suite(x, N) avec des valeurs fixes et cohérentes
        // correspondent à ceux de Math.atan(x).
        try {
            if (Objects.equals(decimalformat.format(suite(0.2, 100)), decimalformat.format(Math.atan(0.2)))) {
                successCount++;
            }
        } catch (ExceptionSuiteArc ignored) {}
        System.out.printf("Nombre de tests réussis: %d / 2.\n", successCount);
        System.out.println("--- Fin des tests ---");
    }

    public static void main(String[] args){
        TP2_2_SuiteArc.testTP2_2();
    }
}