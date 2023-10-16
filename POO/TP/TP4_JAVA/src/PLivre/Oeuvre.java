package PLivre;


import PUtils.Utils;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Oeuvre {

    public class SeriesElement {
        public String name;
        private int numeroInSeries;
        public SeriesElement(String name, int numeroInSeries) {
            this.name = name;
            this.numeroInSeries = numeroInSeries;
        }
        @Override public String toString() {
            return String.format("\"%s\", Tome %d", this.name, this.numeroInSeries);
        }
    }

    private String titre;
    private Auteur auteur;
    private String ISBN;
    private Date datePublication;
    protected ArrayList<Exemplaire> exemplaires;
    private String saga;
    public SeriesElement series;


    public Oeuvre(String ISBN, String titre, String nomAuteur, String prenomAuteur, String datePublication, String saga, int numeroInSaga) throws ParseException {
        if (titre.length() < 1 || titre.length() > 300) {
            throw new InvalidParameterException("Le titre d'une oeuvre doit faire entre 1 et 300 caractères");
        }
        this.titre = titre;

        ISBN = Utils.getISBNFromString(ISBN);
        if (ISBN.length() != 13) {
            throw new InvalidParameterException("Un ISBN doit contenir 13 chiffres");
        }

        // Vérification sur le nom de l'auteur
        if (Objects.equals(nomAuteur, "") && Objects.equals(prenomAuteur, "")) {
            this.auteur = new Auteur();
        } else if (Objects.equals(prenomAuteur, "")) {
            this.auteur = new Auteur(nomAuteur);
        } else {
            this.auteur = new Auteur(nomAuteur, prenomAuteur);
        }

        this.ISBN = ISBN;
        this.datePublication = Utils.parseDate(datePublication);
        this.exemplaires = new ArrayList<>();
        this.series = new SeriesElement(saga, numeroInSaga);
    }
    // CONSTRUCTEURS
    public Oeuvre(String ISBN, String titre, String nomAuteur, String prenomAuteur, String datePublication) throws ParseException {
        if (titre.length() < 1 || titre.length() > 300) {
            throw new InvalidParameterException("Le titre d'une oeuvre doit faire entre 1 et 300 caractères");
        }
        this.titre = titre;

        ISBN = Utils.getISBNFromString(ISBN);
        if (ISBN.length() != 13) {
            throw new InvalidParameterException("Un ISBN doit contenir 13 chiffres");
        }

        if (Objects.equals(nomAuteur, "") && Objects.equals(prenomAuteur, "")) {
            this.auteur = new Auteur();
        } else if (Objects.equals(prenomAuteur, "")) {
            this.auteur = new Auteur(nomAuteur);
        } else {
            this.auteur = new Auteur(nomAuteur, prenomAuteur);
        }
        this.ISBN = ISBN;
        this.datePublication = Utils.parseDate(datePublication);
        this.exemplaires = new ArrayList<>();
    }

    // RECUPERATEURS ET MODIFICATEURS D'ATTRIBUTS SIMPLES:
    // Permet de ne pas donner la modification directe à un élément extérieur du programme.
    public String getTitre() { return this.titre;}
    public void setTitre(String newTitre) { this.titre = newTitre;}
    public String getISBN() { return this.ISBN;}
    public void setISBN(String newISBN) { this.ISBN = newISBN;}
    public Date getDatePublication() { return this.datePublication;}
    public void setDatePublication(String newDate) throws ParseException { this.datePublication = Utils.parseDate(newDate);}
    public ArrayList<Exemplaire> getAllExemplaires() { return this.exemplaires;}

    public void setAttributes(String titre, String prenomAuteur, String nomAuteur, String date) throws ParseException {
        if (!Objects.equals(titre, "0")) {
            if (titre.length() < 1 || titre.length() > 300) {
                throw new InvalidParameterException("Le titre d'une oeuvre doit faire entre 1 et 300 caractères");
            }
            this.titre = titre;
        }

        String tempPrenom = (Objects.equals(prenomAuteur, "0")) ? this.auteur.getFirstName() : prenomAuteur;
        String tempNom = (Objects.equals(nomAuteur, "0")) ? this.auteur.getLastName() : nomAuteur;

        if (Objects.equals(tempNom, "") && Objects.equals(tempPrenom, "")) {
            this.auteur = new Auteur();
        } else if (Objects.equals(tempPrenom, "")) {
            this.auteur = new Auteur(tempNom);
        } else {
            this.auteur = new Auteur(tempNom, tempPrenom);
        }
        if (!Objects.equals(date, "0")) {
            this.datePublication = Utils.parseDate(date);
        }
    }
    public void setAttributes(String titre, String prenomAuteur, String nomAuteur, String date, String saga, int position) throws ParseException {
        setAttributes(titre, prenomAuteur, nomAuteur, date);
        if (!Objects.equals(saga, "0")) {
            this.series = new SeriesElement(saga, position);
        }
    }
    public String getAuteurLastName() {
        return this.auteur.getLastName();
    }
    public String getAuteur() {
        return this.auteur.toString();
    }
    public void removeExemplaires() {
        this.exemplaires = new ArrayList<>();
    }
    public void addNewExemplaire() {
        this.exemplaires.add(new Exemplaire(this.getISBN()));
    }
    public void addNewExemplaires(int number) {
        for (int i = 0; i < number; i++) {
            this.exemplaires.add(new Exemplaire(this.getISBN()));
        }
    }
    public void addNewExemplaire(Etat.EtatType etat, Date dateDepuisEtat) {
        this.exemplaires.add(new Exemplaire(this.getISBN(), etat, dateDepuisEtat));
    }
    public void addNewExemplaires(Etat.EtatType etat, Date dateDepuisEtat, int number) {
        for (int i = 0; i < number; i++) {
            this.exemplaires.add(new Exemplaire(this.getISBN(), etat, dateDepuisEtat));
        }
    }

    public int getNombreExemplairesEnregistres() {
        return this.exemplaires.size();
    }
    public int getNombreExemplairesDisponibles() {
        int total = 0;
        for (Exemplaire exemplaire : this.exemplaires) {
            if (!Objects.equals(exemplaire.getEtat(), "P")) {
                total++;
            }
        }
        return total;
    }

    public void removeExemplaire(Exemplaire exemplaire) {
        this.exemplaires.remove(exemplaire);
    }
    public void displayExemplaires() {
        StringBuilder result = new StringBuilder();
        if (exemplaires.size() > 0) {
            for (Exemplaire exemplaire : this.exemplaires) {
                result.append(String.format("\t%s,\n", exemplaire.toString()));
            }
        }
        System.out.println(result);
    }
    public void displayOeuvre() {
        StringBuilder stringbuilder = new StringBuilder(this.toString());
        if (this.series != null && !Objects.equals(this.series.name, "")) {
            stringbuilder.append(String.format("Série: %s | Publication: %s", this.series, Utils.dtf.format(this.datePublication)));
        } else {
            stringbuilder.append(String.format("Série: [N/A] | Publication: %s", Utils.dtf.format(this.datePublication)));
        }
        System.out.println(stringbuilder);
        this.displayExemplaires();

    }
    public ArrayList<Exemplaire> getExemplairesNonPerdus() {
        return (ArrayList<Exemplaire>) this.exemplaires.stream()
                .filter(exemplaire -> !Objects.equals(exemplaire.getEtat(), "P"))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Qté: %d",
                this.ISBN, this.titre.toUpperCase(), this.auteur, this.getExemplairesNonPerdus().size());
    }

}
