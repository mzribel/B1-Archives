package PLivre;

import PUtils.Utils;

import java.util.Date;
import java.util.EnumMap;

public class Etat {
    public enum EtatType {
        N, TB, B, A, U, D, P
    }
    protected EtatType CodeEtat;
    protected Date DateDepuisEtat;

    public Etat() {
        this.CodeEtat = EtatType.N;
        this.DateDepuisEtat = new Date();
    }
    public Etat(EtatType codeEtat, Date dateDepuisEtat) {
        this.CodeEtat = codeEtat;
        this.DateDepuisEtat = dateDepuisEtat;
    }

    public String getNomEtat() {
        EnumMap<EtatType, String> listeEtats = new EnumMap<EtatType, String>(EtatType.class);
        listeEtats.put(EtatType.N, "Neuf");
        listeEtats.put(EtatType.TB, "Très bon");
        listeEtats.put(EtatType.B, "Bon");
        listeEtats.put(EtatType.A, "Acceptable");
        listeEtats.put(EtatType.U, "Usé");
        listeEtats.put(EtatType.D, "Détérioré");
        listeEtats.put(EtatType.P, "Perdu");
        return listeEtats.get(this.CodeEtat);
    }
    public String getCodeEtat() {
        return this.CodeEtat.toString();
    }
    public Date getDateDepuisEtat() {
        return this.DateDepuisEtat;
    }
    public void setEtat(String newEtat) {
        this.CodeEtat = EtatType.valueOf(newEtat);
        this.DateDepuisEtat = new Date();
    }
    public void setEtat(EtatType newEtat) {
        this.CodeEtat = newEtat;
        this.DateDepuisEtat = new Date();
    }
    @Override
    public String toString() {
        return String.format("\"%s\" (%s)",
                this.getNomEtat(), Utils.dtf.format(this.DateDepuisEtat));
    }
}
