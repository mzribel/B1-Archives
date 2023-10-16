package PMenu;

import PBibliotheque.Bibliotheque;
import PLivre.Etat;
import PLivre.Exemplaire;
import PLivre.Oeuvre;
import POperations.Emprunt;
import POperations.Reservation;
import PUtilisateurs.Adherent;
import PUtils.Utils;
import java.util.Objects;

import static PMenu.Menu.drawTitleBox;
import static PMenu.Menu.launchMenu;

public class MenuPrets {
    public static void MenuGestionPrets(Bibliotheque data) {
        int userInput;
        String[] menuOptions = {"Afficher tous les prêts en cours", "Créer un prêt", "Renouveller un prêt", "Retourner un livre", "Afficher les prêts en retard", "Afficher toutes les réservations en cours", "Annuler une réservation"};
        do {
            drawTitleBox(48, "G E S T I O N  D E S  P R E T S", "Gestion de bibliothèque");
            System.out.println();
            userInput = launchMenu(false, menuOptions, "\n");

            switch (userInput) {
                case 1 -> {
                    System.out.println("--- AFFICHER LES PRÊTS ---\n");
                    data.displayPrets();
                    System.out.println();
                    Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                }
                case 2 -> {
                    System.out.println("--- CREER UN PRÊT ---\n");
                    Oeuvre oeuvre = getOeuvre(data);
                    if (oeuvre == null) { break; }
                    Adherent adherent = getAdherent(data);
                    if (adherent == null) { break; }

                    Exemplaire available = data.getAnAvailableExemplaire(oeuvre);
                    Exemplaire reserve = data.getReservationExemplaire(oeuvre, adherent);
                    Exemplaire reservable = data.getAReservableExemplaire(oeuvre);

                    if (reserve != null && data.getPositionInReservation(reserve, adherent) == 1) {
                        System.out.println("L'adhérent est le premier réservateur d'un exemplaire.\n\nEmprunter le livre ?");
                        if (Utils.getUserInt("1 | Oui \n0 | Retour\n   → ", true, 0, 1) == 1) {
                            try {
                                data.creerEmprunt(adherent, available);
                                data.getCurrentEmprunt(available.getID(), adherent.getID()).finirTransaction();
                            } catch (Bibliotheque.TooManyTransactionsException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else if (available != null && reserve == null) {
                        System.out.printf("""
                                Un exemplaire est disponible et empruntable pour
                                \t[ %s ] %s - de %s

                                Emprunter le livre ?
                                """, oeuvre.getISBN(), oeuvre.getTitre().toUpperCase(), oeuvre.getAuteur());
                        if (Utils.getUserInt("1 | Oui \n0 | Retour\n   → ", true, 0, 1) == 1) {
                            try {
                                data.creerEmprunt(adherent, available);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else if (reserve != null) {
                        System.out.println("Aucun exemplaire n'est disponible à l'emprunt, mais l'utilisateur possède déjà une " +
                                "réservation sur un exemplaire de cette oeuvre.");
                        Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                    } else if (reservable != null) {
                        System.out.println("""
                                Aucun exemplaire n'est disponible à l'emprunt, mais l'utilisateur peut
                                réserver un exemplaire de l'oeuvre.
                                """);
                        if (Utils.getUserInt("1 | Réserver l'exemplaire \n0 | Retour\n   → ", true, 0, 1) == 1) {
                            System.out.println();
                            try {
                                data.creerReservation(adherent, reservable);
                                System.out.println("L'exemplaire a été réservé avec succès !");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            Utils.getUserInt("\n0 | Retour\n   → ", true, 0, 0);
                        }
                    }else {
                        System.out.printf("""
                                Aucun exemplaire n'est disponible à l'emprunt ou à la réservation pour
                                \t[ %s ] %s - de %s

                                """, oeuvre.getISBN(), oeuvre.getTitre().toUpperCase(), oeuvre.getAuteur());
                    }
                    System.out.println();
                }
                case 3 -> {
                    int userInt;
                    System.out.println("--- RENOUVELLER UN PRÊT ---\n");
                    System.out.println("Choisir un prêt à renouveller ci-dessous:");
                    userInt = Menu.launchMenu(false, data.getAllCurrentEmprunts(), "");
                    if (userInt == 0) {
                        break;
                    }
                    Emprunt pret = data.transactions.emprunts.get(userInt-1);
                    System.out.println("Renouveller le prêt ?\n");
                    if (Utils.getUserInt("1 | Oui \n0 | Retour\n   → ", true, 0, 1) == 1) {
                        if (data.getAllCurrentReservations(pret.getIDExemplaire()).size() > 0) {
                            System.out.println("\n/!\\ Impossible de renouveller le prêt: l'exemplaire a été réservé.");
                        } else {
                            pret.renouveller();
                            System.out.println("\nPrêt renouvellé avec succès !");
                        }
                        Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                    }
                }
                case 4 -> {
                    int userInt;
                    System.out.println("--- RETOURNER UN LIVRE ---\n");
                    System.out.println("Choisir un prêt à retourner ci-dessous:");
                    userInt = Menu.launchMenu(false, data.getPretsString(), "");
                    if (userInt == 0) {
                        break;
                    }
                    Emprunt pret = data.transactions.emprunts.get(userInt-1);
                    System.out.println("\nRetourner le livre ?");
                    if (Utils.getUserInt("1 | Oui \n0 | Retour\n   → ", true, 0, 1) == 1) {
                        System.out.println();
                        while (true) {
                            try {
                                String tempEtat = Utils.getUserString("Entrez l'état du livre \n\t( [N]euf, [TB]ien, [B]ien, [A]cceptable, [P]erdu ) -> ", 1, 2);
                                Etat.EtatType etat = Etat.EtatType.valueOf(tempEtat);
                                Exemplaire exemplaire = data.getExemplaireByID(pret.getIDExemplaire());
                                boolean facturation = false;
                                if (Objects.equals(etat.toString(), "P") || Objects.equals(etat.toString(), "D")) {
                                    facturation = true;
                                    System.out.println("\n/!\\ L'adhérent a été facturé pour la perte ou la détérioration\nde l'exemplaire.");
                                }
                                pret.finirTransaction(etat, facturation);
                                exemplaire.setEtat(etat);
                                System.out.println("Livre retourné avec succès !");
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("/!\\ Mauvais format pour l'état du livre");
                            }
                        }
                        Utils.getUserInt("\n0 | Retour\n   → ", true, 0, 0);
                    }
                }
                case 5 -> {
                    System.out.println("--- AFFICHER LES PRÊTS EN RETARD ---\n");
                    data.displayPretsEnRetard();
                    System.out.println("\n/!\\ Il est possible d'envoyer un rappel 5j après la date\ndue ou le dernier rappel.\n");
                    if (Utils.getUserInt("1 | Envoyer les rappels\n0 | Retour\n   → ", true, 0, 1) == 1) {
                        data.envoyerRappels();
                        System.out.println("\nRappels envoyés avec succès !");
                        Utils.getUserInt("\n0 | Retour\n   → ", true, 0, 0);
                    }
                }
                case 6 -> {
                    System.out.println("--- AFFICHER LES RESERVATIONS ---\n");
                    data.displayAllReservations();
                    System.out.println();
                    Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                }
                case 7 -> {
                    int userInt;
                    System.out.println("--- ANNULER UNE RESERVATION ---\n");
                    System.out.println("Choisir une réservation à renouveller ci-dessous:");
                    userInt = Menu.launchMenu(false, data.getAllReservationStrings(), "");
                    if (userInt == 0) {
                        break;
                    }
                    Reservation pret = data.transactions.reservations.get(userInt-1);
                    System.out.println("Annuler la réservation ?\n");
                    if (Utils.getUserInt("1 | Oui \n0 | Retour\n   → ", true, 0, 1) == 1) {
                        pret.finirTransaction();
                        System.out.println("\nRéservation annulée avec succès !");
                        Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                    }
                }
            }
        }
        while (userInput != 0);
        System.out.println();
    }

    public static Oeuvre getOeuvre(Bibliotheque data) {
        while (true) {
            System.out.println("Choisir une oeuvre ci-dessous:");
            int userInt = Menu.launchMenu(false, data.catalogue, "");
            if (userInt == 0) {
                return null;
            }
            Oeuvre oeuvre = data.catalogue.get(userInt - 1);
            if (oeuvre.getExemplairesNonPerdus().size() == 0) {
                System.out.println("/!\\ Il n'existe aucun exemplaire empruntable de cette oeuvre\n");
                Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                continue;
            }
            System.out.println();
            return oeuvre;
        }
    }
    public static Adherent getAdherent(Bibliotheque data) {
        while (true) {
                System.out.println("Choisir un adhérent ci-dessous:");
                int userInt = Menu.launchMenu(false, data.getAllAdherents(), "");
                if (userInt == 0) { return null; }
                Adherent adherent = data.adherents.get(userInt - 1);
                if (adherent.getCaution() == 0) {
                    System.out.println("/!\\ Cet adhérent n'a pas fourni de caution\n");
                    Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                    continue;
                }
                System.out.println();
                return adherent;
            }
    }
}
