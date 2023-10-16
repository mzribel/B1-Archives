package PMenu;

import PBibliotheque.Bibliotheque;
import PLivre.Etat;
import PLivre.Oeuvre;
import POperations.Emprunt;
import POperations.Reservation;
import PUtils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import static PMenu.Menu.drawTitleBox;
import static PMenu.Menu.launchMenu;

public class MenuLivre {
    public static void MenuGestionLivres(Bibliotheque data) {
        int userInput;
        String[] menuOptions = {"Afficher la liste des oeuvres", "Ajouter une oeuvre", "Modifier ou supprimer une oeuvre", "Afficher tous les exemplaires", "Ajouter un exemplaire", "Modifier ou supprimer un exemplaire"};
        drawTitleBox(48, "G E S T I O N  D E S  L I V R E S", "Gestion de bibliothèque");
        System.out.println();
        userInput = launchMenu(false, menuOptions, "\n");

        switch (userInput) {
            case 1 -> {
                System.out.println("--- AFFICHER LES OEUVRES ---\n");
                data.displayOeuvres();
                Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
            }
            case 2 -> {
                System.out.println("--- AJOUTER UNE OEUVRE ---\n");
                boolean success = false;
                int userInt;
                do {
                    String ISBN = Utils.getUserString("Entrez l'ISBN: -> ", 13, 20);
                    Oeuvre temp = data.getOeuvreByISBN(ISBN);
                    if (temp != null) {
                        System.out.printf("""
                                Une oeuvre existe déjà avec cet ISBN:
                                \t%s
                                """, temp);
                        success = true;
                    } else {
                        String title = Utils.getUserString("Entrez le titre: -> ", 2, 300);
                        String firstname = Utils.getUserString("Entrez le prénom de l'auteur (laisser vide si inconnu): -> ", 0, 100);
                        String lastname = Utils.getUserString("Entrez le nom de famille de l'auteur (laisser vide si inconnu): -> ", 0, 100);
                        Oeuvre newOeuvre;
                        String tempPublication;
                        while (true) {
                            try {
                                tempPublication = Utils.getUserString("Entrez la date de publication (dd-mm-YYYY): -> ", 0, 10);
                                Utils.parseDate(tempPublication);
                                break;
                            } catch (ParseException e) {
                                System.out.println("/!\\ Nombre de caractères invalide. [13:20]");
                            }
                        }
                        String saga = Utils.getUserString("Entrez le nom de la saga de livres (laisser vide si aucune): -> ", 0, 100);
                        System.out.println();
                        if (saga.length() == 0) {
                            try {
                                newOeuvre = new Oeuvre(ISBN, title, lastname, firstname, tempPublication);
                                System.out.println("Nouvelle oeuvre ajoutée avec succès !\nAjouter un exemplaire pour l'oeuvre ?");
                                if (Utils.getUserInt("1 | Oui\n0 | Retour\n   → ", true, 0, 1) == 1) {
                                    System.out.println();
                                    ajouterExemplaire(newOeuvre);
                                }
                                data.catalogue.add(newOeuvre);
                            } catch (ParseException e) {
                                System.out.println("/!\\ Une erreur est survenue.");
                            }
                        } else {
                            int position = Utils.getUserInt("Entrez le numéro de l'oeuvre dans la saga: -> ", true, 1, 100);
                            try {
                                newOeuvre = new Oeuvre(ISBN, title, lastname, firstname, tempPublication, saga, position);
                                System.out.println("Nouvelle oeuvre ajoutée avec succès !\nAjouter un exemplaire pour l'oeuvre ?");
                                if (Utils.getUserInt("1 | Oui\n0 | Retour\n   → ", true, 0, 1) == 1) {
                                    System.out.println();
                                    ajouterExemplaire(newOeuvre);
                                }
                                data.catalogue.add(newOeuvre);
                            } catch (ParseException e) {
                                System.out.println("/!\\ Une erreur est survenue.");
                            }
                        }
                    }
                    System.out.println();
                    userInt = Utils.getUserInt("1 | Ajouter une autre oeuvre\n0 | Retour\n   → ", true, 0, 1);
                    System.out.println();
                } while (userInt != 0);
            }
            case 3 -> {
                int userInt;
                do {
                    System.out.println("--- MODIFIER OU SUPPRIMER UNE OEUVRE ---\n");
                    System.out.println("Choisir une oeuvre ci-dessous:");
                    userInt = Menu.launchMenu(false, data.catalogue, "");
                    if (userInt == 0) {
                        break;
                    }
                    Oeuvre oeuvre = data.catalogue.get(userInt - 1);
                    System.out.printf("\nQue faire avec\n\t%s\n\n", oeuvre);
                    String[] tempmenuOptions = {"Modifier l'oeuvre", "Supprimer les exemplaires", "Supprimer les exemplaires"};
                    userInt = launchMenu(false, tempmenuOptions, "\n");
                    switch (userInt) {
                        case 1 -> {
                            System.out.println("--- MODIFIER UNE OEUVRE ---\n");
                            System.out.println("Tapez 0 pour ne pas modifier un champ.");
                            String title;
                            while (true) {
                                title = Utils.getUserString("Entrez le titre: -> ", 0, 300);
                                if (!Objects.equals(title, "0") && title.length() < 2) {
                                    System.out.println("/!\\ Le titre d'une oeuvre doit faire entre 1 et 300 caractères");
                                    continue;
                                }
                                break;
                            }

                            String firstname = Utils.getUserString("Entrez le prénom de l'auteur (laisser vide si inconnu): -> ", 0, 100);
                            String lastname = Utils.getUserString("Entrez le nom de famille de l'auteur (laisser vide si inconnu): -> ", 0, 100);
                            String tempPublication;
                            while (true) {
                                try {
                                    tempPublication = Utils.getUserString("Entrez la date de publication (dd-mm-YYYY): -> ", 0, 10);
                                    if (!Objects.equals(tempPublication, "0")) {
                                        Utils.parseDate(tempPublication);
                                    }
                                    break;
                                } catch (ParseException e) {
                                    System.out.println("/!\\ Format de la date invalide");
                                }
                            }
                            String saga = Utils.getUserString("Entrez le nom de la saga de livres (laisser vide si aucune): -> ", 0, 100);

                            if (StringUtils.normalizeSpace(saga).length() != 0 || !Objects.equals(saga, "0") && oeuvre.series != null) {
                                int position = Utils.getUserInt("Entrez le numéro de l'oeuvre dans la saga: -> ", true, 1, 100);
                                try {
                                    oeuvre.setAttributes(title, lastname, firstname, tempPublication, saga, position);
                                    System.out.println("Nouvelle oeuvre ajoutée avec succès !");
                                } catch (ParseException ignore) {
                                }
                            } else {
                                try {
                                    oeuvre.setAttributes(title, lastname, firstname, tempPublication);
                                } catch (ParseException ignore) {
                                }
                            }
                        }
                        case 2 -> {
                            System.out.println("--- SUPPRIMER LES EXEMPLAIRES ---\n");
                            ArrayList<Emprunt> emprunts = (ArrayList<Emprunt>) data.getAllCurrentEmprunts().stream()
                                    .filter(element -> Objects.equals(data.getExemplaireByID(element.getIDExemplaire()).getISBN(), oeuvre.getISBN()))
                                    .collect(Collectors.toList());
                            ArrayList<Reservation> reservations = (ArrayList<Reservation>) data.getAllCurrentReservations().stream()
                                    .filter(element -> Objects.equals(data.getExemplaireByID(element.getIDExemplaire()).getISBN(), oeuvre.getISBN()))
                                    .collect(Collectors.toList());

                            if (emprunts.size() != 0) {
                                System.out.println("/!\\ Impossible de supprimer les exemplaires: certains exemplaires sont toujours en cours de prêt");
                                break;
                            }

                            System.out.printf("\nNombre de réservations d'exemplaires actuellement: [ %d ]\n\n", reservations.size());

                            System.out.println("Supprimer les exemplaires supprimera toutes les réservations des exemplaires de l'oeuvre.\n");
                            if (Utils.getUserInt("1 | Supprimer\n0 | Retour\n   → ", true, 0, 1) == 1) {
                                for (int i = 0; i < oeuvre.getAllExemplaires().size(); i++) {
                                    data.deleteReservations(oeuvre.getAllExemplaires().get(i));
                                    oeuvre.removeExemplaires();
                                }
                            }
                        }
                        case 3 -> {
                            System.out.println("--- SUPPRIMER L'OEUVRE ---\n");
                            ArrayList<Emprunt> emprunts = (ArrayList<Emprunt>) data.getAllCurrentEmprunts().stream()
                                    .filter(element -> Objects.equals(data.getExemplaireByID(element.getIDExemplaire()).getISBN(), oeuvre.getISBN()))
                                    .collect(Collectors.toList());
                            ArrayList<Reservation> reservations = (ArrayList<Reservation>) data.getAllCurrentReservations().stream()
                                    .filter(element -> Objects.equals(data.getExemplaireByID(element.getIDExemplaire()).getISBN(), oeuvre.getISBN()))
                                    .collect(Collectors.toList());

                            if (emprunts.size() != 0) {
                                System.out.println("/!\\ Impossible de supprimer l'oeuvre: des exemplaires sont toujours en cours de prêt");
                                break;
                            }

                            System.out.printf("\nNombre de réservations d'exemplaires actuellement: [ %d ]\n\n", reservations.size());

                            System.out.println("Supprimer l'oeuvre supprimera toutes les réservations des exemplaires de l'oeuvre.\n");
                            if (Utils.getUserInt("1 | Supprimer\n0 | Retour\n   → ", true, 0, 1) == 1) {
                                for (int i = 0; i < oeuvre.getAllExemplaires().size(); i++) {
                                    data.deleteReservations(oeuvre.getAllExemplaires().get(i));
                                    data.catalogue.remove(oeuvre);
                                }
                            }
                        }
                    }
                } while (userInt != 0);
            }
            case 4 -> {
                System.out.println("--- AFFICHER LES EXEMPLAIRES ---\n");
                data.displayOeuvreAndExemplaires();
                System.out.println();
                Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
            }
            case 5 -> {
                System.out.println("--- AJOUTER UN EXEMPLAIRE ---\n");
                int userInt = -1;
                do {
                    String ISBN = Utils.getUserString("Entrez l'ISBN: -> ", 13, 20);
                    System.out.println();
                    Oeuvre oeuvre = data.getOeuvreByISBN(ISBN);
                    if (oeuvre == null) {
                        System.out.println("Aucune oeuvre n'existe avec cet ISBN.\nEn créer une ?");
                        System.out.println();
                        if (Utils.getUserInt("1 | Oui\n0 | Retour\n   → ", true, 0, 1) == 0) {
                            continue;
                        }
                        if (!data.creerOeuvre(data, ISBN)) {
                            continue;
                        }
                        System.out.println();
                        oeuvre = data.getOeuvreByISBN(ISBN);
                    }
                    userInt = ajouterExemplaire(oeuvre);
                } while (userInt != 0);
            }
        }
    }
    public static int ajouterExemplaire(Oeuvre oeuvre) {
        int userInt = -1;
        do {
            System.out.printf("Ajout d'un exemplaire pour l'oeuvre\n\t[ %s ] %s de %s\n\n", oeuvre.getISBN(), oeuvre.getTitre().toUpperCase(), oeuvre.getAuteur());
            try {
                String tempEtat = Utils.getUserString("Entrez l'état du livre (laissez vide si neuf)\n\t( [N]euf, [TB]ien, [B]ien, [A]cceptable, [P]erdu ) -> ", 0, 2);
                Etat.EtatType etat = tempEtat.length() == 0 ? Etat.EtatType.N : Etat.EtatType.valueOf(tempEtat);
                String tempDate = Utils.getUserString("Entrez la date du dernier état connu (laissez vide si neuf ou aujourd'hui)\n\t(dd-mm-YYYY): -> ", 0, 10);
                System.out.println();
                Date date = tempDate.length() == 0 ? new Date() : Utils.parseDate(tempDate);
                oeuvre.addNewExemplaire(etat, date);
            } catch (IllegalArgumentException e) {
                System.out.println("/!\\ Mauvais format pour l'état du livre");
                continue;
            } catch (ParseException f) {
                System.out.println("/!\\ Mauvais format pour la date de l'état du livre");
                continue;
            }
            userInt = (Utils.getUserInt("1 | Ajouter un autre exemplaire\n0 | Retour\n   → ", true, 0, 1));
        } while (userInt != 0);
        return 0;
    }

}
