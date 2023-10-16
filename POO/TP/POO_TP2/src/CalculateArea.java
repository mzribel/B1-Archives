public class CalculateArea {
    public static double calculateArea(int sideCount, double sideLength) {
        return sideCount * (Math.pow(sideLength, 2)) / (4.0 * Math.tan(Math.toRadians(180) / sideCount));
    }

    public static void main(String[] args) {
        System.out.println();
        Utils.drawTitleBox(48, "L ' A I R E  D U  P O L Y G O N E", "POO | TP n°1", 1);

        // Définit la valeur par défaut pour le menu Retry.
        // Les valeurs ne peuvent être que 0 et 1, -1 initialise la variable pour que la boucle while
        // se lance, avec une valeur qui ne peut pas bloquer son exécution.
        int retry = -1;
        while (retry != 0) {
            // Demande à l'utilisateur les mesures de son polygone.
            float sideLength = Utils.askForFloat(1, 150, "Valeur de la mesure en mètres d'un côté: ");
            int sideCount = Utils.askForInt(3, 20, "Nombre de côtés du polygone: ", false);

            // Calcule et affiche le résultat en l'arrondissant à la troisième décimale.
            String result = Utils.roundNumber(calculateArea(sideCount, sideLength), 3);
            System.out.printf(">> Aire du polygone: %sm².\n", result);

            // Retry: 1 relance la fonction depuis le début (boucle while), 0 l'arrête
            // et revient au menu précédent.
            System.out.println("\nCalculer l'aire d'un autre polygone ?\n");
            Menu.runMenu(true, null);
            retry = Utils.askForInt(0, 1, "→ ", false);
            System.out.println();

        }
   }

}
