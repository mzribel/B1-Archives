package PBibliotheque;

import PLivre.Exemplaire;
import PLivre.Oeuvre;
import POperations.Emprunt;
import POperations.Reservation;
import POperations.Transaction;
import PUtilisateurs.Adherent;
import PUtilisateurs.Gestionnaire;
import PUtilisateurs.Utilisateur;
import PUtils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class Bibliotheque {
    // ATTRIBUTS DE CLASSE
    public String name;
    public ArrayList<Gestionnaire> gestionnaires;
    public ArrayList<Adherent> adherents;
    public ArrayList<Oeuvre> catalogue;
    public AutoIncrementValues autoIncrementValues;
    public ListeTransactions transactions;

    public class TooManyTransactionsException extends Exception {
        public TooManyTransactionsException(String errorMessage) {
            super(errorMessage);
        }
    }

    // CLASSES INTERNES

    // Sert à garder une trace des autoIncrement values pour les attributs qui en ont.
    public class AutoIncrementValues {
        long reservations;
        long emprunts;
        long utilisateurs;
        long exemplaires;
        public AutoIncrementValues() {
            this.reservations = 1L;
            this.emprunts = 1L;
            this.exemplaires = 1L;
            this.utilisateurs = 1L;
        }
        public AutoIncrementValues(long reservations, long emprunts, long exemplaires, long utilisateurs) {
            this.reservations = reservations;
            this.emprunts = emprunts;
            this.exemplaires = exemplaires;
            this.utilisateurs = utilisateurs;
        }
        public Map<String, Long> getAutoIncrementValues() {
            Map<String, Long> result = new HashMap<>();
            result.put("reservations", this.reservations);
            result.put("emprunts", this.emprunts);
            result.put("exemplaires", this.exemplaires);
            result.put("utilisateurs", this.utilisateurs);
            return result;
        }
        public void updateFromClasses() {
            this.reservations = Reservation.getCurrentCount();
            this.emprunts = Emprunt.getCurrentCount();
            this.utilisateurs = Utilisateur.getCurrentCount();
            this.exemplaires = Exemplaire.getCurrentCount();
        }
        public void updateClasses() {
            Reservation.setCurrentCount(this.reservations);
            Emprunt.setCurrentCount(this.emprunts);
            Utilisateur.setCurrentCount(this.utilisateurs);
            Exemplaire.setCurrentCount(this.exemplaires);
        }
    }

    // Sert à regrouper les réservations et les emprunts dans un seul objet.
    public static class ListeTransactions {
        public ArrayList<Reservation> reservations;
        public ArrayList<Emprunt> emprunts;

        public ListeTransactions() {
            this.reservations = new ArrayList<>();
            this.emprunts = new ArrayList<>();
        }
    }


    // CONSTRUCTEUR
    public Bibliotheque(String name) {
        this.name = name;
        this.gestionnaires = new ArrayList<>();
        this.adherents = new ArrayList<>();
        this.catalogue = new ArrayList<>();
        this.transactions = new ListeTransactions();
        this.autoIncrementValues = new AutoIncrementValues();
    }



    // METHODES : Récupération

    // Récupère le nombre d'oeuvres dans la bibliothèque
    public int getNombreOeuvres() {
        return this.catalogue.size();
    }

    // Récupère le nombre d'exemplaires total, perdus ou non.
    public int getNombreExemplairesEnregistres() {
        int total = 0;
        for (Oeuvre oeuvre : this.catalogue) {
            total += oeuvre.getAllExemplaires().size();
        }
        return total;
    }

    // Récupère le nombre d'exemplaires non perdus.
    public int getNombreExemplairesDisponibles() {
        int total = 0;
        for (Oeuvre oeuvre : this.catalogue) {
            total += oeuvre.getExemplairesNonPerdus().size();
        }
        return total;
    }

    // Récupère le nombre d'exemplaires perdus.
    public int getNombreExemplairesPerdus() {
        int total = 0;
        for (Oeuvre oeuvre : this.catalogue) {
            ArrayList<Exemplaire> temp = oeuvre.getAllExemplaires();
            for (Exemplaire exemplaire : temp) {
                if (Objects.equals(exemplaire.getEtat(), "P")) {
                    total++;
                }
            }
        }
        return total;
    }

    // Récupère un exemplaire par son ID
    public Exemplaire getExemplaireByID(String ID) {
        for (Oeuvre oeuvre : this.catalogue) {
            Exemplaire temp = oeuvre.getAllExemplaires().stream()
                .filter(o -> o.getID().equals(ID))
                .findFirst().orElse(null);
            if (temp != null) {
                return temp;
            }
        }
        return null;
    }

    // Récupère des exemplaires par leur ISBN.
    public ArrayList<Exemplaire> getExemplairesByISBN(String ISBN) {
        for (Oeuvre oeuvre : this.catalogue) {
            if (Objects.equals(oeuvre.getISBN(), ISBN)) {
                return oeuvre.getAllExemplaires();
            }
        }
        return null;
    }


    // Récupère toutes les réservations.
    public ArrayList <Reservation> getAllReservations() {
        return this.transactions.reservations;
    }
    // Récupère toutes les réservations en cours.
    public ArrayList<Reservation> getAllCurrentReservations() {
        return (ArrayList<Reservation>) this.transactions.reservations.stream()
                .filter(Transaction::estEnCours)
                .collect(Collectors.toList());
    }
    // Récupère les réservations en cours pour un exemplaire donné.
    public ArrayList<Reservation> getAllCurrentReservations(String IDExemplaire) {
        return (ArrayList<Reservation>) this.transactions.reservations.stream()
                .filter(transaction -> transaction.estEnCours() && Objects.equals(transaction.getIDExemplaire(), IDExemplaire))
                .collect(Collectors.toList());
    }
    // Récupère les réservations en cours pour un exemplaire donné.
    public ArrayList<Reservation> getCurrentReservationsByExemplaireID(String ID) {
        ArrayList<Reservation> temp = (ArrayList<Reservation>) this.transactions.reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getIDExemplaire(), ID) && reservation.estEnCours())
                .collect(Collectors.toList());
        temp.sort(Comparator.comparing(Transaction::getDateDebut));
        return temp;
    }

    // Récypère toutes les réservations d'un adhérent.
    public ArrayList<Reservation> getAllReservationsByAdherent(String IDAdherent) {
        return (ArrayList<Reservation>) this.getAllReservations().stream()
                .filter(transaction -> Objects.equals(transaction.getIDAdherent(), IDAdherent))
                .collect(Collectors.toList());
    }
    // Récupère toutes les réservations actuelles d'un adhérent.
    public ArrayList<Reservation> getAllCurrentReservationsByAdherent(String IDAdherent) {
        return (ArrayList<Reservation>) this.getAllReservations().stream()
                .filter(transaction -> Objects.equals(transaction.getIDAdherent(), IDAdherent) &&
                        transaction.estEnCours())
                .collect(Collectors.toList());
    }

    // Récupère tous les emprunts.
    public ArrayList<Emprunt> getAllEmprunts() {
        return this.transactions.emprunts;
    }
    // Récupère tous les emprunts en cours.
    public ArrayList<Emprunt> getAllCurrentEmprunts() {
        return (ArrayList<Emprunt>) this.transactions.emprunts.stream()
                .filter(Transaction::estEnCours)
                .collect(Collectors.toList());
    }
    // Récupère l'emprunt en cours pour un exemplaire donné.
    public Emprunt getCurrentEmprunt(String IDExemplaire) {
        return this.transactions.emprunts.stream()
                .filter(emprunt -> Objects.equals(emprunt.getIDExemplaire(), IDExemplaire))
                .findFirst().orElse(null);
    }
    // Récupère un emprunt en cours pour un exemplaire et un adhérent donnés.
    public Emprunt getCurrentEmprunt(String IDExemplaire, String IDAdherent) {
        return this.transactions.emprunts.stream()
                .filter(emprunt -> Objects.equals(emprunt.getIDExemplaire(), IDExemplaire) && Objects.equals(emprunt.getIDAdherent(), IDAdherent))
                .findFirst().orElse(null);
    }

    // Récupère tous les emprunts d'un adhérent.
    public ArrayList<Emprunt> getAllEmpruntsByAdherent(String IDAdherent) {
        return (ArrayList<Emprunt>) this.getAllEmprunts().stream()
                .filter(transaction -> Objects.equals(transaction.getIDAdherent(), IDAdherent))
                .collect(Collectors.toList());
    }
    // Récupère tous les emprunts en cours d'un adhérent.
    public ArrayList<Emprunt> getAllCurrentEmpruntsByAdherent(String IDAdherent) {
        return (ArrayList<Emprunt>) this.getAllEmprunts().stream()
                .filter(transaction -> Objects.equals(transaction.getIDAdherent(), IDAdherent) &&
                        transaction.estEnCours())
                .collect(Collectors.toList());
    }

    // Récupère les exemplaires disponibles pour une oeuvre donnée.
    public ArrayList<Exemplaire> getExemplaireDisponiblesByISBN(String ISBN) {
        ArrayList<Exemplaire> temp = this.getExemplairesByISBN(ISBN);
        if (temp == null) {
            return null;
        }
        return (ArrayList<Exemplaire>) temp.stream()
                .filter(Exemplaire::estDisponible)
                .collect(Collectors.toList());
    }

    // Récupère tous les emprunts en cours et en retard.
    public ArrayList<Emprunt> getAllOverdueEmprunts() {
        return (ArrayList<Emprunt>) this.transactions.emprunts.stream()
                .filter(transaction -> transaction.estEnCours() && transaction.dateTheoriqueEstPassee())
                .collect(Collectors.toList());
    }

    // METHODES : Création
    public boolean creerOeuvre(Bibliotheque data, String ISBN) {
        String title = Utils.getUserString("Entrez le titre: -> ", 2, 300);
        String firstname = Utils.getUserString("Entrez le prénom de l'auteur (laisser vide si inconnu): -> ", 0, 100);
        String lastname = Utils.getUserString("Entrez le nom de famille de l'auteur (laisser vide si inconnu): -> ", 0, 100);
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
                data.catalogue.add(new Oeuvre(ISBN, title, lastname, firstname, tempPublication));
                System.out.println("Nouvelle oeuvre ajoutée avec succès !");
                return true;
            } catch (ParseException e) {
                System.out.println("/!\\ Une erreur est survenue.");
                return false;
            }
        } else {
            int position = Utils.getUserInt("Entrez le numéro de l'oeuvre dans la saga: -> ", true, 1, 100);
            try {
                data.catalogue.add(new Oeuvre(ISBN, title, lastname, firstname, tempPublication, saga,position));
                System.out.println("Nouvelle oeuvre ajoutée avec succès !");
            } catch (ParseException e) {
                System.out.println("/!\\ Une erreur est survenue.");
                return false;
            }
        }
        return true;
    }

    // METHODES : destruction

    public void deleteReservations(Exemplaire exemplaire) {
        for (int i = 0; i < this.transactions.reservations.size(); i++) {
            if (Objects.equals(this.transactions.reservations.get(i).getIDExemplaire(), exemplaire.getID())) {
                this.transactions.reservations.remove(this.transactions.reservations.get(i));
            }
        }
    }

    // METHODES : AFFICHAGE
    public void displayOeuvres() {
        for (Oeuvre oeuvre : this.catalogue) {
            System.out.println(oeuvre);
        }
    }
    public void displayOeuvreAndExemplaires() {
        for (Oeuvre oeuvre : this.catalogue) {
            if (oeuvre.getAllExemplaires().size() == 0) {
                continue;
            }
            System.out.println(oeuvre);
            for (Exemplaire exemplaire : oeuvre.getAllExemplaires()) {
                System.out.printf("\t%s\n", exemplaire);
        }
        }
    }


    public Exemplaire getAnAvailableExemplaire(Oeuvre oeuvre) {
        return oeuvre.getExemplairesNonPerdus().stream()
                .filter(element -> !Objects.equals(element.getEtat(), "P") && getCurrentEmprunt(element.getID()) == null)
                .findFirst().orElse(null);
    }
    public Exemplaire getAReservableExemplaire(Oeuvre oeuvre) {
        ArrayList<Exemplaire> temp = (ArrayList<Exemplaire>) oeuvre.getExemplairesNonPerdus().stream()
                .filter(element -> this.getAllCurrentReservations(element.getID()).size() < 5 && getCurrentEmprunt(element.getID()) != null)
                .collect(Collectors.toList());
        if (temp.size() == 0) {
            return null;
        }

        Exemplaire minExemplaire = null;
        for (Exemplaire exemplaire : temp) {
            if (minExemplaire == null) {
                minExemplaire = exemplaire;
                continue;
            }
            if (this.getAllCurrentReservations(exemplaire.getID()).size() < this.getAllCurrentReservations(minExemplaire.getID()).size()) {
                minExemplaire = exemplaire;
            }
        }
        return minExemplaire;
    }

    public Exemplaire getReservationExemplaire(Oeuvre oeuvre, Adherent adherent) {
        Reservation temp = this.getAllCurrentReservationsByAdherent(adherent.getID()).stream()
                .filter(emprunt -> Objects.equals(getExemplaireByID(emprunt.getIDExemplaire()).getISBN(), oeuvre.getISBN()))
                .findFirst().orElse(null);
        if (temp != null) {
            return getExemplaireByID(temp.getIDExemplaire());
        }
        return null;
    }
    public int getPositionInReservation(Exemplaire exemplaire, Adherent adherent) {
        ArrayList <Reservation> allReservations = this.getCurrentReservationsByExemplaireID(exemplaire.getID());
        for (int i = 0; i < allReservations.size(); i++) {
            if (Objects.equals(allReservations.get(i).getIDAdherent(), adherent.getID())) {
                return i+1;
            }
        }
        return 0;
    }

    public void displayAdherents() {
        for (Adherent adherent : this.adherents) {
            System.out.printf("[ %s ] %s, %s | Inscrit(e) le %s\n", adherent.getID(), adherent.getLastName().toUpperCase(), adherent.getFirstName(), Utils.dtf.format(adherent.getDateInscription()));
            System.out.printf("\t%d emprunt(s) en cours | %d réservation(s) en cours | Caution: %d€\n", getAllCurrentEmpruntsByAdherent(adherent.getID()).size(), getAllCurrentReservationsByAdherent(adherent.getID()).size(), adherent.getCaution());
        }
    }
    public void displayPrets() {
        ArrayList <Emprunt> emprunts = getAllCurrentEmprunts();
        for (Emprunt emprunt : emprunts) {
            Oeuvre oeuvre = getOeuvreByISBN(getExemplaireByID(emprunt.getIDExemplaire()).getISBN());
            Adherent adherent = getAdherentByID(emprunt.getIDAdherent());
            System.out.printf("[ %s ] %s (ID n°%s) | En retard: %s\n\tEmprunté par %s, %s (ID n°%s) du %s au %s\n", oeuvre.getISBN(), oeuvre.getTitre().toUpperCase(), emprunt.getIDExemplaire(), (emprunt.dateTheoriqueEstPassee() && emprunt.getDateEffectiveFin() == null),
                    adherent.getLastName().toUpperCase(), adherent.getFirstName(),adherent.getID(), Utils.dtf.format(emprunt.getDateDebut()), Utils.dtf.format(emprunt.getDateTheoriqueFin()));
        }
    }
    public void displayPretsEnRetard() {
        ArrayList <Emprunt> emprunts = this.getAllOverdueEmprunts();
        if (emprunts.size() == 0) {
            System.out.println("Aucun emprunt en retard à afficher.");
            return;
        }
        for (Emprunt emprunt : emprunts) {
            Oeuvre oeuvre = getOeuvreByISBN(getExemplaireByID(emprunt.getIDExemplaire()).getISBN());
            Adherent adherent = getAdherentByID(emprunt.getIDAdherent());
            System.out.printf("[ %s ] %s | \n\tEmprunté par %s, %s (ID n°%s) du %s au %s\n\tNombre de rappels: %s | Dernier rappel: %s\n", oeuvre.getISBN(), oeuvre.getTitre().toUpperCase(),
                    adherent.getLastName().toUpperCase(), adherent.getFirstName(), adherent.getID(), Utils.dtf.format(emprunt.getDateDebut()), Utils.dtf.format(emprunt.getDateTheoriqueFin()),
                    emprunt.getRappels().size(), (emprunt.getRappels().size() > 0 ? Utils.dtf.format(emprunt.getRappels().get(emprunt.getRappels().size()-1)) : "///"));
        }
    }

    public void envoyerRappels() {
        ArrayList <Emprunt> emprunts = this.getAllOverdueEmprunts();
        for (Emprunt emprunt : emprunts) {
            long days;
            if (emprunt.getRappels().size() == 0) {
                days = Duration.between(new Date().toInstant(), emprunt.getDateTheoriqueFin().toInstant()).toDays();
            } else {
                Date lastRappel = emprunt.getRappels().get(emprunt.getRappels().size() - 1);
                days = Duration.between(lastRappel.toInstant(), new Date().toInstant()).toDays();
            }
            if (days <= -5) {
                emprunt.addNewRappel();
            }
        }
    }
    public void displayCurrentReservations() {
        ArrayList<Reservation> temp = this.getAllCurrentReservations();
        temp.sort(Comparator.comparing(Transaction::dateTheoriqueEstPassee).thenComparing(Transaction::getDateTheoriqueFin));
        for (Reservation reservation : temp) {
            System.out.printf("[ ISBN %s ] Réservation n°%s de l'exemplaire n°%s par %s (%s)\n\tRéservé du %s au %s maximum; En cours: %s\n",
                    this.getExemplaireByID(reservation.getID()).getISBN(), reservation.getID(), reservation.getIDExemplaire(), getAdherentByID(reservation.getID()).getLastName(), getAdherentByID(reservation.getID()).getID(),
                    Utils.dtf.format(reservation.getDateDebut()), Utils.dtf.format(reservation.getDateTheoriqueFin()), (reservation.getDateEffectiveFin() == null ? "true" : "false"));
        }
    }
    public void displayCurrentEmprunts() {
        ArrayList<Emprunt> temp = this.getAllCurrentEmprunts();
        temp.sort(Comparator.comparing(Transaction::getDateTheoriqueFin));
        for (Emprunt reservation : temp) {
            System.out.printf("[ ISBN %s ] Emprunt n°%s de l'exemplaire n°%s par %s (%s)\n\tEmprunté du %s au %s maximum; En cours: %s",
                    this.getExemplaireByID(reservation.getID()).getISBN(), reservation.getID(), reservation.getIDExemplaire(), getAdherentByID(reservation.getID()).getLastName(), getAdherentByID(reservation.getID()).getID(),
                    Utils.dtf.format(reservation.getDateDebut()), Utils.dtf.format(reservation.getDateTheoriqueFin()), (reservation.getDateEffectiveFin() == null ? "true" : "false"));
        }
    }
    public void displayDetails() {
        System.out.printf("Nom de la bibliothèque: [ %s ]\n", this.name);
        System.out.printf("Nombre d'oeuvres enregistrées: [ %d ]\n", this.catalogue.size());
        System.out.printf("Nombre d'exemplaires enregistrés: [ %d ]\n\t(dont [ %d ] perdu(s))\n", this.getNombreExemplairesEnregistres(),
                this.getNombreExemplairesPerdus());
        System.out.printf("Nombre d'adhérents: [ %d ]\n---\n", this.adherents.size());
        System.out.printf("Nombre d'emprunts en cours/terminés: [ %d / %d ]\n",
                this.getAllCurrentEmprunts().size(), this.getAllEmprunts().size() - this.getAllCurrentEmprunts().size());
        System.out.printf("Nombre de réservation en cours/terminés: [ %d / %d ]\n", this.getAllCurrentReservations().size(),
        this.getAllReservations().size() - this.getAllCurrentReservations().size());
        System.out.printf("Nombre de retours en retard: %d\n", this.getAllOverdueEmprunts().size());

    }

    // METHODES : MODIFICATEURS

    public ArrayList<Oeuvre> trierOeuvres(ArrayList<Oeuvre> oldList) {
        ArrayList<Oeuvre> newList = new ArrayList<>(oldList);
        newList.sort((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.getAuteur(), b.getAuteur()));
        return newList;
    }
    public ArrayList<Adherent> trierAdherents(ArrayList<Adherent> oldList) {
        ArrayList<Adherent> newList = new ArrayList<>(oldList);
        newList.sort((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.getLastName(), b.getLastName()));
        return newList;
    }

    public ArrayList<Adherent> getAdherentsByName(String lastname) {
        return (ArrayList<Adherent>) this.adherents.stream()
                .filter(adherent -> StringUtils.stripAccents(adherent.getLastName())
                            .equalsIgnoreCase(StringUtils.stripAccents(lastname))).collect(Collectors.toList());
    }

    public Adherent getAdherentByEmail(String email) {
            return this.adherents.stream()
                .filter(adherent -> adherent.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);

    }
    public Adherent getAdherentByID(String ID) {
        return this.adherents.stream()
                .filter(adherent -> adherent.getID().equals(ID))
                .findFirst().orElse(null);
    }
    public String getReservationString(Reservation reservation) {
        Exemplaire tempExemplaire = getExemplaireByID(reservation.getIDExemplaire());
        Oeuvre tempOeuvre = getOeuvreByISBN(tempExemplaire.getISBN());
        Adherent tempAdherent = getAdherentByID(reservation.getIDAdherent());
        return String.format("Réservation n°%s par %s, %s (ID n°%s) // %s - %s\n" +
                "\t[ %s ] %s", reservation.getID(), tempAdherent.getLastName().toUpperCase(), tempAdherent.getFirstName(),
                tempAdherent.getID(), Utils.dtf.format(reservation.getDateDebut()), Utils.dtf.format(reservation.getDateTheoriqueFin()),
                tempOeuvre.getISBN(), tempOeuvre.getTitre());
    }

    public ArrayList<String> getPretsString() {
        ArrayList<String> resultat = new ArrayList<>();
        ArrayList <Emprunt> emprunts = getAllCurrentEmprunts();
        for (Emprunt emprunt : emprunts) {
            Oeuvre oeuvre = getOeuvreByISBN(getExemplaireByID(emprunt.getIDExemplaire()).getISBN());
            Adherent adherent = getAdherentByID(emprunt.getIDAdherent());
            resultat.add(String.format("[ %s ] %s (ID n°%s) | En retard: %s\n\tEmprunté par %s, %s (ID n°%s) du %s au %s", oeuvre.getISBN(), oeuvre.getTitre().toUpperCase(), emprunt.getIDExemplaire(), (emprunt.dateTheoriqueEstPassee() && emprunt.getDateEffectiveFin() == null),
                    adherent.getLastName().toUpperCase(), adherent.getFirstName(),adherent.getID(), Utils.dtf.format(emprunt.getDateDebut()), Utils.dtf.format(emprunt.getDateTheoriqueFin())));
        }
        return resultat;
    }

    public ArrayList<String> getAllReservationStrings() {
        ArrayList<Reservation> reservations = getAllCurrentReservations();
        ArrayList<String> resultat = new ArrayList<>();
        for (Reservation reservation : reservations) {
            resultat.add(getReservationString(reservation));
        }
        return resultat;
    }
    public void displayAllReservations() {
        for (String string : getAllReservationStrings()) {
            System.out.println(string);
        }
    }

    // OEUVRES:
    public Oeuvre getOeuvreByISBN(String ISBN) {
        return this.catalogue.stream()
                .filter(oeuvre -> oeuvre.getISBN().replaceAll("\\D", "").equals(ISBN.replaceAll("\\D", "")))
                .findFirst().orElse(null);
    }
    public ArrayList<Oeuvre> getOeuvresByName(String name) {
        return (ArrayList<Oeuvre>) this.catalogue.stream()
                .filter(oeuvre -> Objects.equals(StringUtils.stripAccents(oeuvre.getTitre()), StringUtils.stripAccents(name)))
                .collect(Collectors.toList());
    }
    public ArrayList<Oeuvre> getOeuvresByAuteur(String name) {
        return (ArrayList<Oeuvre>) this.catalogue.stream()
                .filter(oeuvre -> {
                    return Objects.equals(StringUtils.stripAccents(oeuvre.getAuteurLastName()), StringUtils.stripAccents(name));
                })
                .collect(Collectors.toList());
    }

    public void creerEmprunt(Adherent adherent, Exemplaire exemplaire) throws TooManyTransactionsException {
        if (adherent == null || exemplaire == null) {
            throw new InvalidParameterException("L'adhérent ou l'exemplaire est invalide ou n'existe plus");
        }
        ArrayList<Emprunt> temp = this.getAllCurrentEmpruntsByAdherent(adherent.getID());
        if (Objects.equals(exemplaire.getEtat(), "P")) {
            throw new InvalidParameterException("Cet exemplaire semble avoir été perdu");
        }else if (this.getCurrentEmprunt(exemplaire.getID()) != null) {
            throw new TooManyTransactionsException("Cet exemplaire a déjà été emprunté");
        } else if (temp.size() >= 5) {
            throw new TooManyTransactionsException("Un adhérent ne peut pas avoir plus de 5 réservations en cours");
        } else if (temp.stream()
                .filter(emprunt -> emprunt.estEnCours() && Objects.equals(getExemplaireByID(emprunt.getIDExemplaire()).getISBN(), exemplaire.getISBN()))
                .toList().size() > 0) {
            throw new TooManyTransactionsException("L'adhérent a déjà réservé un exemplaire de cette oeuvre.");
        }
        this.transactions.emprunts.add(new Emprunt(adherent.getID(),exemplaire.getID()));
    }
    public void creerReservation(Adherent adherent, Exemplaire exemplaire) throws TooManyTransactionsException {
        if (adherent == null || exemplaire == null) {
            throw new InvalidParameterException("L'adhérent ou l'exemplaire est invalide ou n'existe plus");
        }
        ArrayList<Reservation> temp = this.getAllCurrentReservationsByAdherent(adherent.getID());
        if (Objects.equals(exemplaire.getEtat(), "P")) {
            throw new InvalidParameterException("Cet exemplaire semble avoir été perdu");
        }else if (this.getAllCurrentReservations(exemplaire.getID()).size() >= 5) {
            throw new TooManyTransactionsException("Un exemplaire ne peut pas être réservé plus de 5 fois");
        } else if (temp.size() >= 5) {
            throw new TooManyTransactionsException("Un adhérent ne peut pas avoir plus de 5 réservations en cours");
        } else if (temp.stream()
                .filter(reservation -> reservation.estEnCours() && Objects.equals(getExemplaireByID(reservation.getIDExemplaire()).getISBN(), exemplaire.getISBN()))
                .toList().size() > 0) {
            throw new TooManyTransactionsException("L'adhérent a déjà réservé un exemplaire de cette oeuvre.");
        }
        this.transactions.reservations.add(new Reservation(adherent.getID(),exemplaire.getID()));
    }

    public void addNouvelAdherant(Adherent adherent) {
        this.adherents.add(adherent);
    }
    public void addNouvelAdherant(String firstName, String lastName, String adresseRue, String adresseVille, String adresseCP, String email, boolean caution) {
        this.adherents.add(new Adherent(firstName, lastName, adresseRue, adresseVille, adresseCP, email, caution));
    }

    public ArrayList<Adherent> getAllAdherents() {
        return this.adherents;
    }

    // METHODES : SAUVEGARDE
    public void saveInFile(String filename) throws IOException {
        this.autoIncrementValues.updateFromClasses();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fw = new FileWriter("src/data/" + filename);
        gson.toJson(this, fw);
        System.out.printf("Les données ont été sauvegardées avec succès (src/data/%s)!\n", filename);
        fw.close();
    }

    public static Bibliotheque readFromFile(String filename) throws Exception {
        Gson gson = new Gson();
        FileReader r = new FileReader("src/data/" + filename);
        Bibliotheque data = gson.fromJson(r, Bibliotheque.class);
        if (data == null) {
            throw new Exception("Le fichier est vide ou contient des données invalides.");
        }
        r.close();
        data.autoIncrementValues.updateClasses();
        return data;
    }
}

