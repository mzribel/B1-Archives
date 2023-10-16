package POperations;

import PLivre.Etat;
import PLivre.Exemplaire;
import PUtilisateurs.Adherent;
import PUtils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Emprunt extends Transaction{
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
    static final int DUREE_DEFAUT_EMPRUNT = 15;

    private Etat.EtatType etatRetour;

    private ArrayList<Date> rappels;
    private int nombreRenouvellements;
    private boolean aEteFacture;
    // CONSTRUCTEURS
    public Emprunt(String IDAdherent, String IDExemplaire) {
        super(new Date(), DUREE_DEFAUT_EMPRUNT, IDAdherent, IDExemplaire);
        this.setID(createID());
        this.rappels = new ArrayList<>();
    }
    public Emprunt(int duree, String IDAdherent, String IDExemplaire) {
        super(new Date(), duree, IDAdherent, IDExemplaire);
        this.setID(createID());
        this.rappels = new ArrayList<>();
    }

    // METHODES DE LA CLASSE EMPRUNT
    public void envoyerRappel(Adherent adherent) {
        this.rappels.add(new Date());
    }
    public void renouveller(int duree) {
        this.dateDebut = new Date();
        Calendar tempDate = Calendar.getInstance();
        tempDate.setTime(this.dateDebut);
        tempDate.add(Calendar.DATE, duree);
        this.dateTheoriqueFin = tempDate.getTime();
        this.nombreRenouvellements++;
    }
    public void renouveller() {
        Date dateDebut = new Date();
        Calendar tempDate = Calendar.getInstance();
        tempDate.setTime(dateDebut);
        tempDate.add(Calendar.DATE, DUREE_DEFAUT_EMPRUNT);
        this.dateTheoriqueFin = tempDate.getTime();
        this.nombreRenouvellements++;
    }
    public void setEtatRetour(Etat.EtatType etat) {
        this.etatRetour = etat;
    }

    public boolean finirTransaction(Etat.EtatType etatRetour, boolean aEteFacture) {
        this.dateEffectiveFin = new Date();
        this.etatRetour = etatRetour;
        this.aEteFacture = aEteFacture;
        return this.dateTheoriqueEstPassee();
    }
    public ArrayList<Date> getRappels() {
        return this.rappels;
    }
    public void addNewRappel() {
        this.rappels.add(new Date());
    }

    @Override
    public String toString() {
        return String.format("Emprunt n°%s par %s | De %s à %s | Fin le: %s", this.getID(), this.getIDAdherent(), Utils.dtf.format(this.dateDebut),
                Utils.dtf.format(this.dateTheoriqueFin), (this.getDateEffectiveFin() == null ? "//" : Utils.dtf.format(this.getDateEffectiveFin())));
    }
}
