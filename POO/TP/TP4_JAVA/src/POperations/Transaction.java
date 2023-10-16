package POperations;

import PLivre.Exemplaire;
import PUtilisateurs.Adherent;
import PUtils.Utils;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Transaction {
    private String ID;
    private String IDAdherent;
    private String IDExemplaire;
    Date dateDebut;
    Date dateTheoriqueFin;
    Date dateEffectiveFin;
    private String typeTransaction;

    public Transaction(Date debut, int duree, String IDAdherent, String IDExemplaire) {
        this.IDAdherent = IDAdherent;
        this.IDExemplaire = IDExemplaire;
        this.dateDebut = debut;
        this.dateTheoriqueFin = Utils.getEndDate(debut, duree);
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateTheoriqueFin() {
        return dateTheoriqueFin;
    }

    public Date getDateEffectiveFin() {
        return dateEffectiveFin;
    }

    public boolean dateTheoriqueEstPassee() {
        return new Date().after(this.dateTheoriqueFin);
    }


    public void setID(String ID) {
        this.ID = ID;
    }
    public String getID() {
        return this.ID;
    }
    public boolean estEnCours() {
        return dateEffectiveFin == null;
    }

    public boolean finirTransaction() {
        this.dateEffectiveFin = new Date();
        return this.dateTheoriqueEstPassee();
    }

    public String getIDExemplaire() { return this.IDExemplaire; }
    public String getIDAdherent() {
        return this.IDAdherent;
    }
}
