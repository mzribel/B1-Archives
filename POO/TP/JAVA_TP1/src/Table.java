import java.text.DecimalFormat;
import java.math.RoundingMode;

public class Table {

    // Dessine les lignes contenant les valeurs d'un tableau de flottants.
    public static void drawTableRow(int cellWidth, float[] rowValues, String cellSep, Boolean avgRow) {
        if (rowValues.length == 0) {
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        System.out.print(cellSep);
        for (float v: rowValues) {
            String formattedValue = avgRow ? String.format("[%s]", decimalFormat.format(v)) : String.format("%s ", decimalFormat.format(v));
            System.out.printf("%" + cellWidth + "s%s",formattedValue, cellSep);
        }
    }

    // Dessine les lignes séparatrices du tableau. Les séparateurs peuvent être passés en paramètres.
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

    // Dessine un tableau entier basé sur un tableau à deux dimensions de flottants.
    // `cellWidth` permet de contrôler la taille des cellules.
    public static void drawFullTable(int cellWidth, float[][] table) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        if (table.length == 0 || table[0].length == 0) {
            return;
        }

        int rowCount = table.length;
        int colCount = table[0].length;
        float[][] tableAverageValues = calculateTableAvg(table);
        drawTableLine(cellWidth, colCount, "┌", "─", "┬", "┐");
        for (int i = 0; i < rowCount; i++) {
            drawTableRow(cellWidth, table[i], "|", false);
            System.out.printf(" [%s]\n", decimalFormat.format(tableAverageValues[0][i]));
            if (i != rowCount -1) {
                drawTableLine(cellWidth, colCount, "├", "─", "┼", "┤");
            }
        }
        drawTableLine(cellWidth, colCount, "└", "─", "┴", "┘");
        drawTableRow(cellWidth, tableAverageValues[1], " ", true);

        if (rowCount == colCount) {
            float averageSum = (Somme.calculateSumOfArr(tableAverageValues[0]) + Somme.calculateSumOfArr(tableAverageValues[1])) / 2;
            System.out.printf(" [%s]", decimalFormat.format(averageSum));
        }
        System.out.println();
    }

    // Génère un tableau de taille `width`*`height`, rempli de flottants
    // donnés par l'utilisateur.
    public static float[][] getValues(int width, int height) {
        float input;
        float[][] list = new float[height][width];

        for (int row = 1; row <= height; row++) {
            for (int col = 1; col <= width; col++) {
                String strIntro = String.format("Entrez la valeur [%d:%d] : ", row, col);
                input = Inputs.getUserFloat(strIntro, true, -100, 100);
                list[row-1][col-1] = input;
            }
            System.out.println("----");
        }
        return list;
    }

    // Calcule les moyennes de toutes les lignes, toutes les colonnes et
    // toutes les valeurs d'un tableau de flottants passé en paramètre.
    public static float[][] calculateTableAvg(float[][] table) {
        if (table.length == 0 || table[0].length == 0) {
            return new float[0][0];
        }
        float[] averagesY = new float[table[0].length];
        float[] averagesX = new float[table.length];
        float tempY; float tempX;

        for (int col = 0; col < table[0].length; col++) {
            tempY = 0;

            for (int row = 0; row < table.length; row++) {
                tempY += table[row][col];
                tempX= 0;
                for (int secCol = 0; secCol < table[row].length; secCol++) {
                    tempX += table[row][secCol];

                }
                averagesX[row] = tempX / table[0].length;
            }
            averagesY[col] = tempY / table.length;
        }

        return new float[][]{averagesX, averagesY};
    }

    public static void execute() {
        System.out.println();
        String strIntro = "Entrez la largeur du tableau (0 pour quitter): ";
        Utils.drawTitleBox(48, "T A B L E A U", "POO | TP n°4", 1);

        int tableWidth = -1;
        // Boucle sur la génération de tableau tant que l'utilisateur ne rentre pas de "0".
        while (tableWidth != 0) {
            // Récupère la largeur du tableau.
            tableWidth = Inputs.getUserInt(strIntro, true, 0, 10);
            if (tableWidth == 0) {
                continue;
            }

            // Récupère la hauteur du tableau.
            int tableHeight = Inputs.getUserInt("Entrez la hauteur du tableau: ", true, 0, 10);
            System.out.println();
            if (tableHeight == 0) {
                continue;
            }

            // Génère le tableau et le dessine avec les valeurs données par l'utilisateur.
            float[][] table = getValues(tableWidth, tableHeight);
            drawFullTable(10, table);
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Table.execute();
    }
}