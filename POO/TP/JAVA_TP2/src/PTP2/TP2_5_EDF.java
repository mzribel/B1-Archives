package PTP2;

import PUtils.Menu;
import PUtils.Utils;

public class TP2_5_EDF {
    // Exception personnalisée, liée à la classe TP2_5_EDF.
    public static class ExceptionEDF extends Exception {
        public ExceptionEDF(String message) { super(message); }
    }

    // Soustrait le nouvel index à l'ancien pour récupérer la consommation mensuelle.
    public static int KWHConsuption(int newIndex, int formerIndex){
       return newIndex - formerIndex;
    }

    // Calcule le coût TTC de la consommation mensuelle.
    public static double KWHCost(int KWHnb, double tva) throws ExceptionEDF {
        if (KWHnb < 0) {
            throw new ExceptionEDF("L'index du mois en cours ne peut pas être négatif.");
        }
        if (tva < 0) { // Puisque EDF n'est pas connu pour donner de l'argent,
            tva = 0;  // corrige la TVA à 0 si le taux est négatif.
        }

        double thresMin = KWHnb;
        double thresMid = 0; double thresMax = 0;

        // Divise la consommation en palliers.
        if (thresMin > 250) {
            thresMax = thresMin - 250;
            thresMin -= thresMax;
        }
        if (thresMin > 100) {
            thresMid = thresMin - 100;
            thresMin -= thresMid;
        }

        double costHT = 200 + 0.1*thresMin + 0.175*thresMid + 0.225*thresMax;
        return Math.round((costHT * (1 + tva/100))* 100.0) / 100.0;
    }

    // Menu associé au programme.
    public static void execute() {
        Menu.drawTitleBox(48, "C O N S O M M A T I O N E D F", "JAVA - TP 2.5");
        int userRetry;
        do {
            int formerIndex = Utils.getUserInt("Ancien index: ", true, 0, -1);
            int newIndex = Utils.getUserInt("Nouvel index: ", true, 0, -1);
            double TVA = Utils.getUserDouble("TVA en %: ", true, 1, 100);
            try {
                int temp = KWHConsuption(newIndex, formerIndex);
                System.out.print("\n>>> RESULTATS <<<\n");
                System.out.printf("Prix TTC pour une consommation mensuelle de %dkWh: %s€.\n", temp, Utils.roundNumber(KWHCost(temp, TVA), 2));
            } catch (TP2_5_EDF.ExceptionEDF e) {
                System.out.println("ERREUR: " + e.getMessage());
            }
            System.out.println();
            System.out.println("Recommencer ?");
            userRetry = Menu.launchMenu(true, null, "\n");
        } while (userRetry != 0);
    }

    // Tests unitaires des fonctions du programme.
    public static void testTP2_5() {
        System.out.println("--- Début des tests (TP2_5_EDF) ---");
        int successCount = 0;
        int prev = 0;
        int[] monthly = new int[]{0, 100, 250, 1000};
        double[] results = new double[]{216, 226.8, 236.25, 376.65};

        System.out.println("Réalisation des tests sur KWHConsuption()...");
        // Vérifie la justesse du calcul.
        if (KWHConsuption(1000, 250) == 750) {
            successCount++;
        }
        System.out.printf("Nombre de tests réussis: %d / 1.\n---\n", successCount);

        successCount = 0;
        int temp;
        System.out.println("Réalisation des tests sur KWHCost()...");
        // Vérifie si les quatre index de `monthly` correspondent aux résultats de `results`.
        for (int i = 0; i < monthly.length; i++) {
            temp = KWHConsuption(monthly[i], prev);
            try {
                if (KWHCost(temp, 8) == results[i]) {
                    successCount++;
                }
            } catch (ExceptionEDF ignored) {}
            prev = monthly[i];
        }
        // Vérifie qu'une erreur est bien lancée si l'index est négatif.
        try {
            KWHCost(-12, 8);
        } catch (ExceptionEDF e) {
            successCount++;
        }
        System.out.printf("Nombre de tests réussis: %d / 5.\n", successCount);
        System.out.println("--- Fin des tests ---");
    }

    public static void main (String[] args) {
        TP2_5_EDF.testTP2_5();
    }
}
