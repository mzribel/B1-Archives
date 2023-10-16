package PLivre;

import POperations.Emprunt;
import POperations.Reservation;
import PUtils.Utils;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Exemplaire {
    public static AtomicLong IDCounter = new AtomicLong(1);
    public String createID()
    {
        return String.valueOf(IDCounter.getAndIncrement());
    }
    public static long getCurrentCount() {
        return IDCounter.get();
    }
    public static void setCurrentCount(long newStart) {
        IDCounter = new AtomicLong(newStart);
    }
    private String ID;
    private int nombreEmprunts;
    private String ISBN;
    private Etat etat;
    private Reservation currReservation;
    private Emprunt currEmprunt;
    public String getID() { return this.ID; }

    public Exemplaire(String ISBN) {
        this.ID = createID();
        String tempISBN = Utils.getISBNFromString(ISBN);
        if (tempISBN.length() != 13) {
            throw new InvalidParameterException("Un ISBN doit contenir 13 chiffres");
        }
        this.nombreEmprunts = 0;
        this.ISBN = ISBN;
        this.etat = new Etat();
        this.currReservation = null;
        this.currEmprunt = null;
    }
    public Exemplaire(String ISBN, Etat.EtatType etat, Date dateDepuisEtat) {
        this.ID = createID();
        ISBN = Utils.getISBNFromString(ISBN);
        if (ISBN.length() != 13) {
            throw new InvalidParameterException("Un ISBN doit contenir 13 chiffres");
        }
        this.nombreEmprunts = 0;
        this.ISBN = ISBN;
        this.etat = new Etat(etat, dateDepuisEtat);
        this.currReservation = null;
        this.currEmprunt = null;
    }

    // RECUPERATEURS ET MODIFICATEURS D'ATTRIBUTS SIMPLES:
    // Permet de ne pas donner la modification directe à un élément extérieur du programme.
//    public String getID() {
//        return this.ID;
//    }

    public String getISBN() {return this.ISBN;}
    public void setISBN(String newISBN) { this.ISBN = newISBN;}
    public boolean estDisponible() {
        return (this.currEmprunt == null && this.currReservation == null & !Objects.equals(this.etat.getCodeEtat(), "P"));
    }
    public String getEtat() {
        return this.etat.getCodeEtat();
    }
    public String getStatut() {
        enum StatutType {
            D, E, P
        }
        if (Objects.equals(this.etat.getCodeEtat(), "P")) {
            return "PERDU";
        }
        else if (this.currEmprunt != null) {
            return "EMPRUNTE";
        }
        return "DISPONIBLE";
    }

    public void setEtat(Etat.EtatType etat) {
        this.etat.setEtat(etat);
    }

    @Override
    public String toString() {
        return String.format("ID n°%s | Etat %s | %s", this.ID, this.etat.getNomEtat(), this.getStatut());
    }

}
