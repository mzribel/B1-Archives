package PMenu;

import PBibliotheque.Bibliotheque;
import POperations.Emprunt;
import POperations.Reservation;
import PUtilisateurs.Adherent;
import PUtils.Utils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static PMenu.Menu.drawTitleBox;
import static PMenu.Menu.launchMenu;

public class MenuAdherent {
    public static void MenuGestionAdherents(Bibliotheque data) {
        int userInput;
        String[] menuOptions = {"Afficher tous les adhérents", "Ajouter un adhérent", "Modifier ou supprimer un adhérent"};
        do {
            drawTitleBox(48, "G E S T I O N  D E S  A D H E R E N T S", "Gestion de bibliothèque");
            System.out.println();
            userInput = launchMenu(false, menuOptions, "\n");

            switch (userInput) {
                case 1 -> {
                    System.out.println("--- AFFICHER LES ADHERENTS ---\n");
                    data.displayAdherents();
                    System.out.println();
                    Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                }
                case 2 -> {
                    System.out.println("--- AJOUTER UN ADHERENT ---\n");
                    int userInt = -1;
                    do {
                        String email = Utils.getUserString("Entrez l'adresse mail: -> ", "email", 1, 300);
                        System.out.println();
                        Adherent adherent = data.getAdherentByEmail(email);
                        if (adherent != null) {
                            System.out.printf("""
                                            Un adhérent existe déjà avec cette adresse email:
                                            \t[ %s ] %s, %s | %s | Inscrit depuis le %s
                                            """, adherent.getID(), adherent.getLastName().toUpperCase(),
                                    adherent.getFirstName(), adherent.getAdresse(), Utils.dtf.format(adherent.getDateInscription()));
                            System.out.println();

                        } else {
                            String firstname = Utils.getUserString("Entrez le prénom: -> ", "name", 2, 20);
                            String lastname = Utils.getUserString("Entrez le nom de famille: -> ", "name", 2, 20);
                            String CP = Utils.getUserString("Entrez le code postal -> ", 5, 5);
                            String ville = Utils.getUserString("Entrez la ville -> ", 2, 20);
                            String rue = Utils.getUserString("Entrez la rue -> ", 2, 20);
                            String tempCaution;
                            while (true) {
                                tempCaution = Utils.getUserString("L'adhérent a-t-il déjà payé la caution ? [y/n] -> ", 1, 1);
                                if (!tempCaution.equalsIgnoreCase("y") && !tempCaution.equalsIgnoreCase("n")) {
                                    continue;
                                }
                                break;
                            }
                            boolean caution = tempCaution.equalsIgnoreCase("y");
                            Adherent ad = new Adherent(firstname, lastname, rue, ville, CP, email, caution);
                            data.addNouvelAdherant(ad);
                            System.out.println(ad);
                            System.out.printf("""
                                            Nouvel adhérent crée avec succès !
                                            \t[ %s ] %s, %s | Inscrit depuis le %s
                                                                                        
                                            """,
                                    ad.getID(), ad.getLastName().toUpperCase(),
                                    ad.getFirstName(), Utils.dtf.format(ad.getDateInscription()));
                        }
                        userInt = Utils.getUserInt("1 | Ajouter un autre adhérent \n0 | Retour\n   → ", true, 0, 1);
                    } while (userInt != 0);

                }
                case 3 -> {
                    int userInt;
                    do {
                        System.out.println("--- MODIFIER OU SUPPRIMER UN ADHERENT ---\n");
                        System.out.println("Choisir un adhérent ci-dessous:");
                        userInt = Menu.launchMenu(false, data.getAllAdherents(), "");
                        if (userInt == 0) {
                            break;
                        }
                        Adherent adherent = data.adherents.get(userInt - 1);
                        System.out.printf("\nQue faire avec\n\t%s\n\n", adherent);
                        String[] tempmenuOptions = {"Enregistrer une caution", "Modifier l'adhérent", "Supprimer l'adhérent"};
                        userInt = launchMenu(false, tempmenuOptions, "\n");
                        switch (userInt) {
                            case 2 -> {
                                System.out.println("--- MODIFIER UN ADHERENT ---\n");
                                System.out.println("Tapez 0 pour ne pas modifier un champ.");
                                String firstname, lastname;
                                while (true) {
                                    lastname = Utils.getUserString("Entrez le nom de famille de l'adhérent: -> ", "name", 0, 100);
                                    if (!Objects.equals(lastname, "0") && lastname.length() < 2) {
                                        System.out.println("/!\\ Le titre d'une oeuvre doit faire entre 1 et 300 caractères");
                                        continue;
                                    }
                                    break;
                                }
                                while (true) {
                                    firstname = Utils.getUserString("Entrez le nom de famille de l'adhérent: -> ", "name", 0, 100);
                                    if (!Objects.equals(firstname, "0") && firstname.length() < 2) {
                                        System.out.println("/!\\ Le titre d'une oeuvre doit faire entre 1 et 300 caractères");
                                        continue;
                                    }
                                    break;
                                }
                                while (true) {
                                    String email = Utils.getUserString("Entrez le nom de famille de l'adhérent: -> ", "email", 0, 100);
                                    if (!Objects.equals(email, "0") && data.getAdherentByEmail(email) != null) {
                                        System.out.println("/!\\ Cetta adresse mail est déjà utilisée par un autre adhérent");
                                        continue;
                                    }
                                    break;
                                }

                                String CP = Utils.getUserString("Entrez le code postal: -> ", 0, 5);
                                String ville = Utils.getUserString("Entrez la ville: -> ", "name", 0, 100);
                                String rue = Utils.getUserString("Entrez la rue: -> ", 0, 300);
                                adherent.setAttributes(firstname, lastname, CP, ville, rue);
                            }
                            case 3 -> {
                                System.out.println("--- SUPPRIMER L'ADHERENT ---\n");
                                ArrayList<Emprunt> emprunts = (ArrayList<Emprunt>) data.getAllCurrentEmpruntsByAdherent(adherent.getID()).stream()
                                        .filter(element -> Objects.equals(element.getIDAdherent(), adherent.getID()))
                                        .collect(Collectors.toList());
                                ArrayList<Reservation> reservations = (ArrayList<Reservation>) data.getAllCurrentReservationsByAdherent(adherent.getID()).stream()
                                        .filter(element -> Objects.equals(element.getIDAdherent(), adherent.getID()))
                                        .collect(Collectors.toList());

                                if (emprunts.size() != 0) {
                                    System.out.println("/!\\ Impossible de supprimer l'adhérent: des exemplaires sont toujours en cours de prêt");
                                    break;
                                }

                                System.out.printf("\nNombre de réservations d'exemplaires actuellement: [ %d ]\n\n", reservations.size());

                                System.out.println("Supprimer l'adhérent supprimera toutes les réservations liées à cet adhérent.\n");
                                if (Utils.getUserInt("1 | Supprimer\n0 | Retour\n   → ", true, 0, 1) == 1) {
                                    for (int i = 0; i < data.getAllAdherents().size(); i++) {
                                        data.adherents.remove(adherent);
                                    }
                                }
                            }
                            case 1 -> {
                                System.out.println("--- ENREGISTRER UNE CAUTION ---\n");
                                if (adherent.getCaution() != 0) {
                                    System.out.println("Cet adhérent a déjà une caution enregistrée !");
                                    userInt = Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                                } else {
                                    System.out.println("Voulez-vous enregistrer un chèque de caution de 50€ pour cet adhérent?");
                                    if (Utils.getUserInt("1 | Oui \n0 | Retour\n   → ", true, 0, 1) == 1) {
                                        adherent.setCaution();
                                    }
                                }
                            }
                        }
                    } while (userInt != 0);
                }
            }
        }
        while (userInput != 0);
    }
}
