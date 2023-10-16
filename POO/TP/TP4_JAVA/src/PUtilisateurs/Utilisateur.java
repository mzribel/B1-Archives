package PUtilisateurs;

import PLivre.Auteur;
import PUtils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Utilisateur {
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
    private String lastName;
    private String firstName;
    private final Adresse adresse;
    private String email;
    private Date dateInscription;

    public Utilisateur(String firstName, String lastName, String adresseRue, String adresseVille, String adresseCP, String email) {
        super();
        if (!Utils.checkNameFormat(firstName)|| !Utils.checkNameFormat(lastName)) {
            throw new InvalidParameterException("Format du nom ou du prénom invalide");
        }
        if(!Utils.checkEmailFormat(email)) {
            throw new InvalidParameterException("Format de l'email invalide");
        }
        this.firstName= firstName;
        this.lastName = lastName;
        this.email = StringUtils.normalizeSpace(email);
        this.adresse = new Adresse(adresseRue, adresseCP, adresseVille);
        this.ID = createID();
        this.dateInscription = new Date();
    }

    public String getID() {
        return this.ID;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getEmail() {
        return this.email;
    }
    public String getAdresse() {
        return this.adresse.toString();
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    // METHODES : MODIFICATEURS

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public void setAttributes(String firstname, String lastname, String CP, String ville, String rue) {
        this.firstName = (Objects.equals(firstname, "0")) ? this.firstName : firstname;
        this.lastName = (Objects.equals(lastname, "0")) ? this.lastName : lastname;
        this.adresse.codePostal= (Objects.equals(CP, "0")) ? this.adresse.codePostal : CP;
        this.adresse.ville = (Objects.equals(ville, "0")) ? this.adresse.ville : ville;
        this.adresse.rue= (Objects.equals(rue, "0")) ? this.adresse.rue : rue;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public class Adresse {
        private String rue;
        private String details;
        private String ville;
        private String codePostal;

        public Adresse(String rue, String codePostal, String ville) {
//            if (!Utils.checkNameFormat(ville)) {
//                throw new InvalidParameterException("Format de la ville invalide");
//            }
//            if (!Utils.checkcodePostalFormat(codePostal)) {
//                throw new InvalidParameterException("Format du code postal invalide");
//            }
            this.rue = rue;
            this.ville = ville;
            this.codePostal = codePostal;
        }

        public void setRue(String rue) {
            this.rue = rue;
        }


        public void setVilleAndCP(String codePostal, String ville) {
            this.codePostal = codePostal;
            this.ville = ville;
        }
        public String getCP() {
            return this.codePostal;
        }


        @Override
        public String toString() {
            return String.format("%s,\n%s %s", this.rue, this.codePostal, this.ville);
        }
    }
}
