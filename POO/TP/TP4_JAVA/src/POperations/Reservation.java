package POperations;

import PUtils.Utils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation extends Transaction{
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
    static final int DUREE_DEFAUT_RESERVATION = 10;

    // CONSTRUCTEURS
    public Reservation(String IDAdherent, String IDExemplaire) {
        super(new Date(), DUREE_DEFAUT_RESERVATION,  IDAdherent, IDExemplaire);
        this.setID(createID());
    }

    public Reservation(int duree, String IDAdherent, String IDExemplaire) {
        super(new Date(), duree, IDAdherent, IDExemplaire);
        this.setID(createID());
    }
    @Override
    public String toString() {
        return String.format("Réservation n°%s de %s par %s | De %s à %s | Fin le: %s", this.getID(), this.getIDExemplaire(), this.getIDAdherent(), Utils.dtf.format(this.dateDebut),
                Utils.dtf.format(this.dateTheoriqueFin), (this.getDateEffectiveFin() == null ? "//" : Utils.dtf.format(this.getDateEffectiveFin())));
    }
}
