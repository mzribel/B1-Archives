package PPerson;
import PUtils.Utils.*;

public abstract class Person {

    // ATTRIBUTS:
    public String firstname;
    public String lastname;
    public int birthYear;

    //----------------
    // CONSTRUCTEURS:
    public Person(String firstname, String lastname, int birthYear) throws InvalidInformationException {
        if (firstname.length() < 2 || lastname.length() < 2) {
            throw new InvalidInformationException("First and last name cannot have less than 2 characters.");
        }
        if (birthYear < 1900 || birthYear > 2020) {
            throw new InvalidInformationException("Birth year invalid (must be between 1900 and 2020.");
        }
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthYear = birthYear;
    }

    //----------------
    // METHODES - MODIFICATEURS:
    public void setInfo(String firstname, String lastname, int birthYear) throws InvalidInformationException {
        if (firstname.length() < 2 || lastname.length() < 2) {
            throw new InvalidInformationException("First and last name cannot have less than 2 characters.");
        }
        if (birthYear < 1900 || birthYear > 2020) {
            throw new InvalidInformationException("Birth year invalid (must be between 1900 and 2020.");
        }
        this.firstname = firstname; this.lastname = lastname; this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s] - %d", this.lastname, this.firstname, this.birthYear);
    }
}
