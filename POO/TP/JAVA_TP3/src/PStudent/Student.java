package PStudent;
import PPerson.Person;
import PTeacher.Teacher;
import PUtils.Utils.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Student extends Person {
    public static final DecimalFormat df = new DecimalFormat( "#.00" );

    //----------------
    // ATTRIBUTS:
    public final ArrayList<Grade> grades;

    //----------------
    // CONSTRUCTEURS:
    public Student(String firstname, String lastname, int birthYear) throws InvalidInformationException {
        super(firstname, lastname, birthYear);
        String role = "student";
        this.grades = new ArrayList<>();
    }

    //----------------
    // METHODES - MODIFICATEURS:
    public void setGrade(String subject, String name, double studentPoints, double maxPoints) throws Exception{
        int existingGrade = this.hasGrade(subject, name);
        if (existingGrade >= 0) {
            this.grades.set(existingGrade, new Grade(subject, name, studentPoints, maxPoints));
        }
        this.grades.add(new Grade(subject, name, studentPoints, maxPoints));
    }
    public void setGrade(String subject, String name, int weight, double studentPoints, double maxPoints) throws Exception {
        int existingGrade = this.hasGrade(subject, name);
        if (existingGrade >= 0) {
            this.grades.set(existingGrade, new Grade(subject, name, weight, studentPoints, maxPoints));
            return;
        }
        this.grades.add(new Grade(subject, name, weight,studentPoints, maxPoints));
    }

    public void sortGrades() {
        this.grades.sort((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.subject, b.subject));
    }

    //----------------
    // METHODES - RECUPERATEURS:

    public int hasGrade(String subject, String name) {
        for (int i = 0; i < this.grades.size(); i++) {
            Grade current = this.grades.get(i);
            if (current.subject.equalsIgnoreCase(subject) && current.name.equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Grade> getGradesBySubject(String subject) {
        return (ArrayList<Grade>) this.grades.stream().filter(grade -> grade.subject.equalsIgnoreCase(subject)).collect(Collectors.toList());
    }

    public double getAverage() {
        if (this.grades.isEmpty()) {
            return -1;
        }
        int totalWeight = 0;
        double gradeTotal = 0;
        for (Grade grade : this.grades) {
            if (grade.studentPoints < 0) {
                continue;
            }
            gradeTotal += grade.studentPoints / grade.maxPoints * 20 * grade.weight;
            totalWeight += grade.weight;
        }
        return gradeTotal / totalWeight;
    }

    public double getAverage(String subject) {
        if (this.grades.isEmpty()) {
            return -1;
        }
        int totalWeight = 0;
        double gradeTotal = 0;
        for (Grade grade : this.grades) {
            if (grade.studentPoints < 0 || !grade.subject.equalsIgnoreCase(subject)) {
                continue;
            }
            gradeTotal += grade.studentPoints / grade.maxPoints * 20 * grade.weight;
            totalWeight += grade.weight;
        }
        return gradeTotal == 0 ? -1 : gradeTotal / totalWeight;
    }

    //----------------
    // METHODES - SUPPRESSEURS :

    public void removeAllGrades() {
        this.grades.clear();
    }

    public void removeGrade(String subject, String name) {
        if (this.grades.isEmpty()) {
            return;
        }
        this.grades.removeIf(current -> current.name.equalsIgnoreCase(name) && current.subject.equalsIgnoreCase(subject));
    }

    public void removeGradesBySubject(String subject) {
        if (this.grades.isEmpty()) {
            return;
        }
            this.grades.removeIf(current -> current.subject.equalsIgnoreCase(subject));
    }

    //----------------
    // METHODES - AFFICHAGE:

    public void display() {
        double avg = this.getAverage();
        System.out.printf("NOM DE L'ELEVE: [ %s, %s ]\n", this.lastname, this.firstname);
        System.out.printf("NOMBRE DE NOTES: [ %d ]\n", this.grades.size());
        System.out.printf("MOYENNE GENERALE: [ %s ]\n\n", avg < 0 ? "N/A" : avg + "/20");
    }

    public void displayGrades(ArrayList<Grade> grades) {
        if (grades.isEmpty()) {
            System.out.println("Aucune note n'a été trouvée.");
            return;
        }
        this.sortGrades();
        String currentSubject = "";
        for (Grade grade : grades) {
            if (!grade.subject.toUpperCase().equals(currentSubject)) {
                currentSubject = grade.subject.toUpperCase();
                double avg = this.getAverage(grade.subject);
                System.out.printf("[ %s ] - Moy. : %s\n", currentSubject.toUpperCase(),
                        avg > -1 ? df.format(avg) + "/20" : "N/A");
            }
            System.out.printf("   %s - %s (coeff %d)\n", grade.name,
                    grade.studentPoints >= 0 ? df.format(grade.studentPoints) + "/20" : "N/A", grade.weight);
        }
        System.out.println();
    }

    public void displayGrades(String subject) {
        displayGrades(this.getGradesBySubject(subject));
    }

    public void displayGrades() {
        displayGrades(this.grades);
    }

    @Override
    public String toString() {
        double avg = this.getAverage();
        return String.format("[ %s, %s ]\n   %d note(s) - Moy. Générale: %s",
                this.lastname, this.firstname,
                this.grades.size(),
                avg == -1 ? "N/A" : df.format(avg)+"/20");
    }

    //----------------
    // EXECUTABLE:
    public static void runTests() throws Exception {
        System.out.println("Début des tests sur PStudent.Student...");
        Student s1 = null;
        int successful = 0;
        // Test 1: Création d'un étudiant invalide
        try {
            s1 = new Student("", "", 1900);
        } catch (InvalidInformationException e) {
            successful++;
        }

        // Test 2: Création d'un étudiant valide
        try {
            s1 = new Student("Antoine", "Delarue", 1999);
            if (Objects.equals(s1.firstname, "Antoine")) {successful++;}
        } catch (InvalidInformationException ignored) {}

        // Test 3: Création d'une note à coefficient 1
        assert s1 != null;
        s1.setGrade("Maths", "Test", 13, 20);
        if (s1.grades.size() == 1) {
            successful++;
        }

        s1.setGrade("Maths", "Test 2", 2, 8, 10);
        if (s1.grades.get(1).weight == 2) {
            successful++;
        }

        s1.setGrade("Anglais", "Test 1", 2, 20, 20);
        if (s1.getAverage("Maths") == 15) {
            successful++;
        }
        if (s1.getAverage() == 17) {
            successful++;
        }
        System.out.printf("Fin des tests: %d/6 tests réussis.\n", successful);
    }

    public static void main(String[] args) throws Exception {
        Student.runTests();
    }
}
