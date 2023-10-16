public class Archimede {
    public static double Cn(double X, double Y) {
        if (X < 0 || Y < 0) {
            return -1;
        }
        return 2 * X * Y / (X + Y);
    }

    // Formule de la suite calculant le périmètre du polygone
    // régulier inscrit à un cercle de diamètre 1.
    // X=périmètre conscrit ; Y=pémimètre inscrit de l'étape précédente de la suite.
    public static double In(double X, double Y) {
        if (X < 0 || Y < 0) {
            return -1;
        }
        return java.lang.Math.sqrt(X * Y);
    }

    // Pour un cercle de diamètre 1, renvoie les valeurs des périmètres des polygones
    // conscrit (X) et inscrit (Y) de ce cercle, à 2^(2+N) côtés.
    public static double[] An(int N) {
        double[] result = new double[2];
        // Initialise les périmètres des polygones conscrit (X) et inscrit (Y)
        // en partant de polygones à quatre côtés (carrés).
        double X = 4;
        double Y = 2 * java.lang.Math.sqrt(2);

        // Double le nombre de côtés des polygones à chaque itération, donc
        // calcule les périmètres des polygones pour 2^(2+N) côtés.
        for (int i = 1; i <= N; i++) {
            X = Archimede.Cn(X, Y);
            Y = Archimede.In(X, Y);
        }
        result[0] = X; result[1] = Y;
        return result;
    }

    // Menu dédié au test des méthodes de la classe Archimède.
    public static void executeDebug(int archimedeValue) {
        String[] aTitles = {"C N", "I N", "A N"};
        Utils.drawTitleBox(48, "A R C H I M E D E  " + aTitles[archimedeValue], "POO | TP n°4 - Archimede", 1);

        if (archimedeValue == 2) {
            int N = Inputs.getUserInt("Entrez la valeur de N: ", false, 0, 0);

            double[] result = Archimede.An(N);
            System.out.printf("Résultat de Archimede.An avec N=%d:\n    X=%s ; Y=%s.\n\n", N, result[0], result[1]);
        } else {
            double X = Inputs.getUserDouble("Entrez la valeur de X: ", true, 0, 1000);
            double Y = Inputs.getUserDouble("Entrez la valeur de Y: ", true, 0, 1000);
            String eq = archimedeValue == 0 ? "Cn" : "In";
            Double result = archimedeValue == 0 ? Archimede.Cn(X, Y) : Archimede.In(X, Y);

            System.out.printf("Résultat de Archimede.%s avec X=%s et Y=%s: %s.\n\n", eq, Utils.roundNumber(X, 3), Utils.roundNumber(Y, 3), result);
        }
        System.out.println("0 // Continuer");
        Inputs.getUserInt("→ ", true, 0, 0);
        System.out.println();

    }

    // Menu exécutable de la classe Archimède.
    public static void execute() {
        System.out.println();
        int retry = -1;
        String[] menuOptions = {"Tester Archimede.Cn", "Tester Archimede.In", "Tester Archimede.An"};

        while (retry != 0) {
            Utils.drawTitleBox(48, "A R C H I M E D E", "POO | TP n°4", 1);
            Menu.runMenu(false, menuOptions);
            retry = Inputs.getUserInt("→ ", true, 0, menuOptions.length);
            System.out.println();
            if (retry != 0) {
                executeDebug(retry-1);
            }
        }
    }
    public static void main(String[] args) {
        Archimede.execute();
    }
}
