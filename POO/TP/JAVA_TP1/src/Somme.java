public class Somme {

    // Récupère les valeurs à ajouter dans le tableau de taille n,
    // défini par l'utilisateur.
    public static float[] getValues(int n) {
        System.out.println();
        float input;
        float[] list = new float[n];
        for (int i =  1; i <= n; i++ ) {
            input = Inputs.getUserFloat("Entrez la valeur "+i+": ", true, -1000, 1000);
            list[i-1] = input;
        }
        return list;
    }

    // Calcule la somme de toutes les valeurs du tableau.
    public static float calculateSumOfArr(float[] list) {
        float result = 0;
        for (float v : list) {
            result += v;
        }
        return result;
    }

    public static void execute() {
        System.out.println();
        Utils.drawTitleBox(48, "S O M M E", "POO | TP n°4", 1);
        String introStr =  "Entrez la taille du tableau (0 pour quitter):\n→ ";

        int input = -1;
        while (input != 0) {
            // Demande la taille du tableau à l'utilisateur.
            input = Inputs.getUserInt(introStr, true, 0, 25);
            if (input != 0) {
                // Génère le tableau, calcule la somme des valeurs et en retourne le résultat.
                System.out.printf("\nLa somme du tableau est: %s.\n\n", Utils.roundNumber(Somme.calculateSumOfArr(Somme.getValues(input)), 4));
            }
        }
    }
    public static void main(String[] args) {
        Somme.execute();
    }
}
