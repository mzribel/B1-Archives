package PTP2;
import PUtils.Menu;
import PUtils.Utils;
import java.text.DecimalFormat;
import java.util.Arrays;

public class TP2_3_Permutations {
    // Inverse deux valeurs d'un tableau par leur index. Gère les exceptions.
    // S'il y a pas assez de valeurs, le tableau n'est pas altéré.
    public static void singleInversion(double[] table, int a, int b) throws ArrayIndexOutOfBoundsException {
        if (table.length < 2) {
            return;
        }
        double temp = table[a];
        table[a] = table[b];
        table[b] = temp;
    }

    // Inverse la totalité des valeurs d'un tableau.
    // S'il y a pas assez de valeurs, le tableau n'est pas altéré.
    public static void tableInversion(double[] table) {
        if (table.length < 2) {
            return;
        }
        for (int i = 0; i < table.length/2; i++) {
            singleInversion(table, i, table.length-1-i);
        }
    }

    // Fonction servant à l'affichage des tableaux.
    public static void displayTable(String intro, double[] table) {
        DecimalFormat decimalformat = new DecimalFormat("###,###.###");
        if (table.length == 0) {
            System.out.println("Rien à afficher: le tableau est vide !");
            return;
        }
        System.out.println(intro);
        for (int i = 0; i < table.length; i++) {
            System.out.printf("Index[%d] = %s\n", i, decimalformat.format(table[i]));
        }
    }

    // Fonction venant du TP1 JAVA !
    // Utilisée pour récupérer un array de `n` double fournis par l'utilisateur.
    public static double[] getValues(int n) {
        double[] list = new double[n];
        if (n == 0) {
          return list;
        }
        System.out.println();
        double input;
        for (int i =  1; i <= n; i++ ) {
            input = Utils.getUserDouble("Entrez la valeur ["+i+"]: ", true, -1000, 1000);
            list[i-1] = input;
        }
        return list;
    }

    // Menu associé au programme.
    public static void execute() {
        double[] currTable = new double[]{249.05, 291.41, 231.28, 299.57, 0.78};

        int chosenOption, userRetry;
        Menu.drawTitleBox(48, "P E R M U T A T I O N S", "JAVA - TP 2.3");
        do {
            System.out.printf("[ Taille du tableau actuel: %s]\n", currTable.length);
            String[] menuOptions = {"Créer un nouveau tableau", "Afficher le tableau", "Permuter deux valeurs", "Permuter tout le tableau"};
            chosenOption = Menu.launchMenu(false, menuOptions, "\n");
            try {
                switch (chosenOption) {
                    // Permet à l'utilisateur de créer un nouveau tableau pour remplacer le default.
                    case 1 -> {
                        int tableSize = Utils.getUserInt("Taille du tableau [0:5]: ", true, 0, 5);
                        currTable = getValues(tableSize);
                    }
                    // Affiche le tableau actuel.
                    case 2 -> displayTable(">>> AFFICHAGE <<<", currTable);
                    // Permute deux valeurs du tableau.
                    case 3 -> {
                        if (currTable.length < 2) {
                            System.out.println("Le tableau comporte moins de deux éléments!");
                            break;
                        }
                        int firstIndex = Utils.getUserInt("Index du premier élément à permuter: ", false, 0, currTable.length - 1);
                        int secondIndex = Utils.getUserInt("Index du deuxième élément à permuter: ", false, 0, currTable.length - 1);
                        displayTable("\n>>> TABLEAU AVANT INVERSION <<<", currTable);
                        singleInversion(currTable, firstIndex, secondIndex);
                        System.out.println();
                        displayTable(">>> TABLEAU APRES INVERSION <<<", currTable);
                    }
                    // Permute le tableau entier.
                    case 4 -> {
                        if (currTable.length < 2) {
                            System.out.println("Le tableau comporte moins de deux éléments!");
                            break;
                        }
                        displayTable("\n>>> TABLEAU AVANT INVERSION <<<", currTable);
                        tableInversion(currTable);
                        System.out.println();
                        displayTable(">>> TABLEAU APRES INVERSION <<<", currTable);
                    }
                    default -> {
                        return;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ERREUR: Un des index est invalide; permutation impossible.");
            }
            System.out.println("\nContinuer ?");
            userRetry = Menu.launchMenu(true, null, "\n");
        } while (userRetry != 0);
    }

    // Tests unitaires des fonctions du programme.
    public static void test_TP2_3() {
        System.out.println("--- Début des tests (TP2_3_Permuter) ---");
        int successCount = 0;
        double[] test, testCopy;
        System.out.println("Réalisation des tests sur singleInversion()...");

        // Longueur de l'array inférieure à 2.
        test = new double[]{0.1}; testCopy = test.clone();
        singleInversion(test, 1, 3);
        if (Arrays.equals(test, testCopy)) {
            successCount++;
        }
        // Array et index valides.
        test = new double[]{0.1, 0.2, 0.3}; testCopy = test.clone();
        singleInversion(test, 0, 2);
        if (test[0] == testCopy[2] && test[2] == testCopy[0]) {
            successCount++;
        }
        // Index non existant dans l'array.
        try {
            singleInversion(test, 3, 5);
        } catch (ArrayIndexOutOfBoundsException e) {
            successCount++;
        }
        System.out.printf("Nombre de tests réussis: %d / 3.\n---\n", successCount);

        System.out.println("Réalisation des tests sur tableInversion()...");
        successCount = 0;

        // Longueur de l'array inférieure à 2.
        test = new double[]{0.1}; testCopy = test.clone();
        tableInversion(test);
        if (Arrays.equals(test, testCopy)) {
            successCount++;
        }
        // Longueur de l'array est un chiffre pair.
        test = new double[]{0.1, 0.2, 0.3, 0.4}; testCopy =  new double[]{0.4, 0.3, 0.2, 0.1};
        tableInversion(test);
        if (Arrays.equals(test, testCopy)) {
            successCount++;
        }
        // Longueur de l'array est un chiffre impair.
        test = new double[]{0.1, 0.2, 0.3, 0.4, 0.5}; testCopy =  new double[]{0.5, 0.4, 0.3, 0.2, 0.1};
        tableInversion(test);
        if (Arrays.equals(test, testCopy)) {
            successCount++;
        }
        System.out.printf("Nombre de tests réussis: %d / 3.\n", successCount);
        System.out.println("--- Fin des tests ---");
    }

    public static void main(String[] args) {
        TP2_3_Permutations.test_TP2_3();
    }


}
