public class Conversions {

    // Convertit un nombre en base 10 à son équivalent en base 2.
    // Limite: 2^31.
    public static String decToBin(int baseNumber) {
        if (baseNumber == 0) {
            return "0";
        }
        StringBuilder binResult = new StringBuilder();

        int remainder = Math.abs(baseNumber);
        while (remainder > 0) {
            binResult.insert(0, (remainder % 2));
            remainder /= 2;
        }

        if (baseNumber < 0) {
            binResult.insert(0, ("-"));
        }
        return binResult.toString();
    }

    // Convertit un nombre en base 10 à son équivalent en base 2.
    // Limite: 31 digits + signe "-"
    public static int binToDec(String baseNumber) {
        int decimalResult = 0;
        int currentBase = 1;
        boolean isNegative = false;
        for (int i = baseNumber.length() - 1; i >= 0; i--) {
            if (baseNumber.charAt(i) == '-') {
                isNegative = true;
                continue;
            }
            int currDigit = Integer.parseInt(String.valueOf(baseNumber.charAt(i)));
            decimalResult = decimalResult + (currDigit * currentBase);
            currentBase *= 2;

        }
        return isNegative ? -decimalResult : decimalResult;
    }

    // Convertit un nombre en base 10 à son équivalent en base 16.
    // Limite: 2^31. Non utilisé pour ne pas alourdir le menu.
    public static String decToHex(int baseNumber) {
        String[] valueArr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        StringBuilder hexResult = new StringBuilder();
        int remainder;
        long decimalNumber = Math.abs(baseNumber);
        while (decimalNumber > 0) {
            remainder = (int)(decimalNumber % 16);
            hexResult.insert(0, valueArr[remainder]);
            decimalNumber /= 16;
        }

        if (baseNumber < 0) {
            hexResult.insert(0, ("-"));
        }
        return hexResult.toString();
    }

    // Convertit un nombre un base 16 à son équivalent en base 10.
    // Limite exclusive: 80000000 (2^31). Non utilisé pour ne pas alourdir le menu.
    public static int hexToDec(String baseNumber) {
        String valueArr = "0123456789ABCDEF";

        int decimalResult = 0;
        double currentPower = 0;
        int decEquivalent;
        boolean isNegative = false;
        for (int i = baseNumber.length()-1; i >= 0; i--) {
            if (baseNumber.charAt(i) == '-') {
                isNegative = true;
                continue;
            }
            decEquivalent = valueArr.indexOf(baseNumber.charAt(i));
            decimalResult += (long)((double)decEquivalent * Math.pow(16, currentPower));
            currentPower++;
        }
        return isNegative ? -decimalResult : decimalResult;
    }

    public static void printConverted(int chosenConversion, String intro) {
        String baseNumber = ""; String javaResult = ""; String manualResult = "";
        String javaIntro = "";
        switch (chosenConversion) {
            case 1 -> {
                int numberInt = Inputs.getUserInt("Nombre à convertir (dec → bin): ", false, 0, 0);
                baseNumber = Integer.toString(numberInt);
                javaResult = Integer.toBinaryString(numberInt);
                javaIntro = "(méthode native java, binaire signé complément à 2)";
                manualResult = Conversions.decToBin(numberInt);
            }
            case 2 -> {
                baseNumber = Inputs.getUserNumber("bin", "Nombre à convertir (bin → dec): ");
                javaResult = Integer.toString(Integer.parseInt(baseNumber, 2));
                javaIntro = "(méthode native java)";
                manualResult = Integer.toString(binToDec(baseNumber));
            }
        }
        // Print le résultat dans le contexte d'un appel dans le menu.
        System.out.printf("""
                \n--- RESULTAT: %s ---
                %s → %s %s
                %s → %s (méthode manuelle)
                """, intro, baseNumber, javaResult, javaIntro, baseNumber, manualResult);
    }

    // Programme pour réaliser les conversions dans le terminal.
    public static void execute() {
        System.out.println();
        Utils.drawTitleBox(48, "C O N V E R S I O N S", "POO | TP n°4", 1);
        String[] conversionOptions = {"Décimal → Binaire", "Binaire → Décimal"};
        int retry = -1;

        while (retry != 0) {

            System.out.println("Choisir la conversion à réaliser: ");

            // Demande la conversion souhaitée à l'utilisateur.
            Menu.runMenu(false, conversionOptions);
            int chosenOption = Inputs.getUserInt("→ ", true, 0, conversionOptions.length);
            System.out.println();

            // Stoppe la fonction si l'utilisateur décide de ne pas faire de conversion.
            if (chosenOption == 0) {
                return;
            }
            // Lance la fonction de conversion selon celle choisie par l'utilisateur.
            printConverted(chosenOption, conversionOptions[chosenOption-1]);

            // Menu de relance du programme.
            System.out.println("\nConvertir un autre nombre ?");
            Menu.runMenu(true, null);
            retry = Inputs.getUserInt("→ ", true, 0, 1);
            System.out.println();
        }

    }
    public static void main(String[] args) {
        Conversions.execute();
    }
}
