public class Suite {

    // Calcule le résultat de la suite suivante pour n itérations:
    // Un = Un-1 + 1 / n avec n > 0 et U1 = 1.
    public static double calculateSuite(int n) {
        if (n < 1) {
            return -1;
        } else if (n == 1) {
            return 1;
        }
        double prev = 0;
        double curr = 1;
        for (int i = 2; i <= n; i++) {
            prev = curr;
            curr = prev + (1.0 / i);
        }
        return curr;
    }

    public static void execute() {
        System.out.println();
        Utils.drawTitleBox(48, "S U I T E", "POO | TP n°4", 1);

        String introStr =  "Entrez le nombre de termes de la suite à calculer avec n > 0 (0 pour quitter):\n→ ";
        int input = -1;
        while (input != 0) {
            // Demande à l'utilisateur le nombre d'itérations souhaitées de la suite.
            input = Inputs.getUserInt(introStr, true, 0, -1);
            if (input != 0) {
                // Calcule la suite avec le nombre d'itérations donné.
                System.out.printf("Résultat de la suite avec n=%d: %s.\n\n", input, Utils.roundNumber(calculateSuite(input), 4));
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Suite.execute();
    }
}
