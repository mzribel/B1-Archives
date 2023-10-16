public class ChristmasTree {

    // Dessine l'arbre.
    // `height` définit la hauteur de l'arbre entre la pointe et le tronc.
    public static void drawTree(int height, int titleBoxWidth, boolean padding) {
        // `midWidth` et `maxWidth` sont les valeurs de largeur de l'arbre.
        int midWidth = height + 2;
        int maxWidth = midWidth * 2 + 1;

        // `extraPadding` ajoute des espaces de padding horizontal
        // si l'on veut centrer l'arbre par rapport à une largeur spécifique.
        // Ici, la largeur spécifique est celle de la titleBox qui sera au-dessus.
        int extraPadding = maxWidth > titleBoxWidth+2 || !padding ? 0 : (titleBoxWidth+2-maxWidth) / 2;

        // Elargit la titleBox si l'arbre est plus large qu'elle (largeur default: 50).
        titleBoxWidth = Math.max(maxWidth, titleBoxWidth);

        // Dessine la titleBox liée à l'arbre.
        Utils.drawTitleBox(titleBoxWidth, "T A D A A A A  ♪", "", 1);


        // Dessine l'arbre lui-même en fonction de la hauteur donnée.
        // Hauteur = de la pointe au tronc exclus.
        for (int row = 0; row < height; row++) {
            System.out.print(" ".repeat(extraPadding + midWidth - row));
            System.out.print("*".repeat(row * 2 + 1));
            System.out.println(" ".repeat(midWidth - row));
        }
        // Dessine le tronc de l'arbre (fixe, c'est un arbre très stable).
        System.out.println(" ".repeat(extraPadding + midWidth) + "*" + " ".repeat(midWidth));
        System.out.println(" ".repeat(extraPadding + midWidth - 1) + "***" + " ".repeat(midWidth-1) + "\n");
    }
    public static void main(String[] args) {
        System.out.println();
        Utils.drawTitleBox(48, "L E  S A P I N  D E  N O Ë L", "POO | TP n°1", 1);
        int retry = -1;
        while (retry != 0) {

            // Demande la hauteur de l'arbre à l'utilisateur.
            int height = Utils.askForInt(4, 25, "Hauteur de l'arbre ? → ", false);
            System.out.println();
            // Dessine l'arbre avec la hauteur donnée.
            drawTree(height, 48, true);

            // Retry: 1 relance la fonction depuis le début (boucle while), 0 l'arrête
            // et revient au menu précédent.
            System.out.println("Voulez-vous dessiner un autre arbre ?\n");
            Menu.runMenu(true, null);
            r
            System.out.println();
        }
    }
}
