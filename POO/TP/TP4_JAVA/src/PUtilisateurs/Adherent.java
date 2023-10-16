package PUtilisateurs;

import POperations.Emprunt;
import POperations.Reservation;

import java.util.ArrayList;

public class Adherent extends Utilisateur {
    class AdherentException extends Exception {
        public AdherentException(String message) {
            super(message);
        }
    }
    private int cautionActuelle;
    static final int EMPRUNTS_AUTORISES = 5;
    private ArrayList<Reservation> currReservations;
    private ArrayList<Emprunt> currEmprunts;
    private int nombreRappels;
    public Adherent(String firstName, String lastName, String adresseRue, String adresseVille,
                    String adresseCP, String email) {
        super(firstName, lastName, adresseRue, adresseVille, adresseCP, email);
        this.cautionActuelle = 0;
        this.currReservations = new ArrayList<>();
        this.currEmprunts = new ArrayList<>();
    }
    public Adherent(String firstName, String lastName, String adresseRue, String adresseVille,
                    String adresseCP, String email, boolean aFourniCaution) {
        super(firstName, lastName, adresseRue, adresseVille, adresseCP, email);
        if (aFourniCaution) {
            this.cautionActuelle = 50;
            this.currReservations = new ArrayList<>();
            this.currEmprunts = new ArrayList<>();
        }
    }
    public int getCaution() {
        return this.cautionActuelle;
    }
    public void setCaution() {
        this.cautionActuelle = 50;
    }

    @Override
    public String toString() {
        return String.format("%s, %s - Caution fournie: %d", this.getLastName().toUpperCase(), this.getFirstName(), this.getCaution());
    }
    public void incrementNombreRappels() {
        this.nombreRappels++;
    }


}
