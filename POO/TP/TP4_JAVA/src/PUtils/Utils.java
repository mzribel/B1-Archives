package PUtils;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utils {
    public static DecimalFormat df = new DecimalFormat("0.##");
    public static final SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");

    // Récupère un int de l'utilisateur, y applique toutes les vérifications.
    // Si `useBoundaries` est true, un input hors de `min` et `max` sera considéré
    // comme faux.
    public static int getUserInt(String intro, boolean useBoundaries, int min, int max) {
        Scanner sc = new Scanner(System.in);
        int userInput;
        while (true) {
            System.out.print(intro);
            String userStr = sc.nextLine();
            try {
                userInput = Integer.parseInt(userStr);
            } catch (NumberFormatException e) {
                System.out.println("ERREUR: Vous devez entrer un nombre entier.\n");
                continue;
            }

            if (useBoundaries && !isInBounds(userInput, min, max)) {
                continue;
            }
            break;
        }
        return userInput;
    }

    public static double getUserDouble(String intro, boolean useBoundaries, double min, double max) {
        Scanner sc = new Scanner(System.in);
        double userInput;
        while (true) {
            System.out.print(intro);
            String userStr = sc.nextLine();
            try {
                userInput = Double.parseDouble(userStr);
            } catch (NumberFormatException e) {
                System.out.println("ERREUR: Vous devez entrer un nombre.\n");
                continue;
            }

            if (useBoundaries && !isInBounds(userInput, min, max)) {
                continue;
            }
            break;
        }
        return userInput;
    }

    public static String getEmail() {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Scanner sc = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print("Entrer l'adresse email: ");
            userInput = sc.nextLine();
            userInput = StringUtils.normalizeSpace(userInput).toLowerCase();
            if (userInput.length() > 320 || !Pattern.compile(regex).matcher(userInput).matches()) {
                System.out.println("/!\\ Format d'email invalide.\n");
                continue;
            }
            break;
        }
        return userInput;
    }
    public static String getName() {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Scanner sc = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print("Entrer l'adresse email: ");
            userInput = sc.nextLine();
            userInput = StringUtils.normalizeSpace(userInput).toLowerCase();
            if (userInput.length() > 320 || !Pattern.compile(regex).matcher(userInput).matches()) {
                System.out.println("/!\\ Format d'email invalide.\n");
                continue;
            }
            break;
        }
        return userInput;
    }

    public static String getUserString(String intro, String type, int min, int max) {
        Scanner sc = new Scanner(System.in);
        String userInput;

        while (true) {
            System.out.print(intro);
            userInput = sc.nextLine();
            userInput = StringUtils.normalizeSpace(userInput);
            if (userInput.length() > max || userInput.length() < min) {
                System.out.printf("/!\\ Nombre de caractères invalide. [%d:%d].\n\n", min, max);
                continue;
            }

            switch (type) {
                case "email" -> {
                    if (!checkEmailFormat(userInput)) {
                        System.out.println("/!\\ Format d'email invalide.");
                        continue;
                    }
                }
                case "name" -> {
                    if (!checkNameFormat(userInput)) {
                        System.out.println("/!\\ Format de nom invalide.");
                        continue;
                    }
                }
                case "ISBN" -> {
                    userInput = getISBNFromString(userInput);
                    if (userInput.length() != 13) {
                        System.out.println("/!\\ Un ISBN doit continir 13 chiffres.");
                        continue;
                    }
                }
            }
            break;
        }
        return userInput;
    }


    public static String getUserString(String intro, int min, int max) {
        Scanner sc = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.print(intro);
            userInput = sc.nextLine();
            userInput = StringUtils.normalizeSpace(userInput);
            if (userInput.length() > max || userInput.length() < min) {
                System.out.printf("/!\\ Nombre de caractères invalide. [%d:%d].\n\n", min, max);
                continue;
            }
            break;
        }
        return userInput;
    }

    // Vérifie que `number` se trouve dans les limites de `min` et `max`.
    // Si `min` > `max`, seul `min` est pris en compte.
    public static Boolean isInBounds(double number, double min, double max) {
        if (min > max && number < min) {
            System.out.printf("/!\\ Input inférieur au minimum autorisé. [min=%s]\n\n", df.format(min));
            return false;
        } else if (max >= min && !(number >= min && number <= max)) {
            System.out.printf("/!\\ Input hors de l'intervale autorisé [%s:%s].\n\n", df.format(min), df.format(max));
            return false;
        }
        return true;
    }

    public static String centerString(int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public static Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    public static boolean checkEmailFormat(String email) {
        return Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matcher(email).matches();
    }
    public static boolean checkNameFormat(String name) {
        return Pattern.compile("^([A-Za-zÀ-ÖØ-öø-ÿ]+'?)([ \\-]*[A-Za-zÀ-ÖØ-öø-ÿ]+'?)*$").matcher(name).matches();
    }

    public static Date getEndDate(Date origineDate, int numberOfDays) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(origineDate);
        tempCalendar.add(Calendar.DATE, numberOfDays);
        return tempCalendar.getTime();
    }
    public static String getISBNFromString(String original) {
        return original.replaceAll("\\D", "");
    }
}
