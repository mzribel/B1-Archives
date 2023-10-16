package PLivre;

import java.util.Objects;

public class Auteur {
    private String lastName;
    private String firstName;

    public Auteur() {
        this.lastName = "Unknown";
    }
    public Auteur(String lastName) {
        this.lastName = lastName;
    }
    public Auteur(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String toString() {
        if (firstName != null && !Objects.equals(firstName, "")) {
            return this.lastName + ", " + this.firstName;
        } else {
            return this.lastName;
        }
    }
}