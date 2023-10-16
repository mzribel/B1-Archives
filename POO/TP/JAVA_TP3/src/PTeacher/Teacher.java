package PTeacher;
import PPerson.Person;
import PUtils.Utils.*;

public class Teacher extends Person{
    public String speciality;
    public String role;

    public Teacher(String firstname, String lastname, int birthYear, String speciality, boolean isHead) throws InvalidInformationException {
        super(firstname, lastname, birthYear);
        this.speciality = speciality;
        this.role = isHead ? "Head Teacher" : "Teacher";
    }

    @Override
    public String toString() {
        return String.format("[ %s, %s ] né en [ %d ]\n   Spécialité: [ %s ]\n",
        this.lastname, this.firstname, this.birthYear, this.speciality);
    }
}
