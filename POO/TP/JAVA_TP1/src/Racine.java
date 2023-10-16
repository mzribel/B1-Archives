public class Racine {
    public static void execute(){
        System.out.println();
        Utils.drawTitleBox(48, "R A C I N E", "POO | TP n°4", 1);

        int input = -1;
        while (input != 0) {
            // Récupère la valeur souhaitée par l'utilisateur.
            input = Inputs.getUserInt("Entrez une valeur positive (0 pour quitter):\n→ ", true, 0, -1);
            if (input != 0) {
                // Effectue le calcul et renvoie le résultat.
                System.out.printf("Racine carrée de %d: %s\n\n", input, Utils.roundNumber(java.lang.Math.sqrt(input), 3));
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Racine.execute();
    }
}
