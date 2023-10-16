package PClass;
import PStudent.Student;
import PTeacher.Teacher;
import PStudent.Grade;
import PUtils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import PUtils.Utils.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class Class {
    public static final DecimalFormat df = new DecimalFormat( "#.00" );

    //----------------
    // ATTRIBUTS:
    public String name;
    public final ArrayList<Student> students;
    public Teacher headTeacher;

    //----------------
    // CONSTRUCTEURS:
    public Class(String name) throws InvalidInformationException {
        if (name.length() < 1) {
            throw new InvalidInformationException("Le nom de la classe doit contenir au moins 1 caractère.");
        }
        this.name = name;
        this.students = new ArrayList<>();
        this.headTeacher = null;
    }

    //----------------
    // METHODES - MODIFICATEURS

    // Renomme la classe:
    public void setClassName(String name) throws InvalidInformationException {
        if (name.length() < 2) {
            throw new InvalidInformationException("Class name must be at least 2 characters long.");
        }
        this.name = name;
    }

    // Remplace l'étudiant à l'ID `formerID` par `newStudent`.
    public void updateStudent(int formerID, Student newStudent) {
        this.students.set(formerID, newStudent);
    }

    // Ajoute un étudiant `student` à la classe.
    public void setStudent(Student student) throws Utils.ElementAlreadyExistsException {
        if (this.students.contains(student)) {
            return;
        }

        int existingStudentID = this.hasStudent(student.firstname, student.lastname);
        if (existingStudentID >= 0) {throw new Utils.ElementAlreadyExistsException("A student with this name already exists.");}
        this.students.add(student);
        this.sortStudents("lastname");
    }

    // Crée un étudiant avec les paramètres données puis l'ajoute à la classe.
    public void setStudent(String firstName, String lastName, int birthYear) throws ElementAlreadyExistsException, InvalidInformationException {
        setStudent(new Student(firstName, lastName, birthYear));
    }

    // Retire l'étudiant `student` de la classe.
    public void removeStudent(Student student) {
        try {
            this.students.remove(student);
        } catch (Exception ignored){}
    }

    // Ordonne la liste des étudiants par nom de famille ou moyenne, ordre croissant.
    public void sortStudents(String order) {
        switch (order) {
            case "lastname" ->
                    this.students.sort((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.lastname, b.lastname));
            case "average" -> this.students.sort(Comparator.comparingDouble(Student::getAverage));
        }
    }

    //----------------
    // METHODES - RECUPERATEUR

    // Retourne l'index de l'étudiant s'il existe.
    public int hasStudent(String firstname, String lastname) {
        for (int i = 0; i < this.students.size(); i++) {
            Student current = this.students.get(i);
            if (current.firstname.equalsIgnoreCase(firstname) && current.lastname.equalsIgnoreCase(lastname)) {
                return i;
            }
        }
        return -1;
    }

    // Retourne true si au moins un étudiant à une note qui correspond aux paramètres.
    public boolean studentsHaveGrade(String subject, String name) {
        for (Student student : this.students) {
            if (student.hasGrade(subject, name) >= 0) {
                return true;
            }
        }
        return false;
    }

    // Retourne l'étudiant s'il existe.
    public Student getStudent(String firstname, String lastname) {
        return this.students.stream()
                .filter(student -> student.firstname.equalsIgnoreCase(firstname) && student.lastname.equalsIgnoreCase(lastname))
                .findFirst().orElse(null);
    }

    // Retourne un étudiant par son index dans la liste.
    public Student getStudent(int index) {
        try {
            return this.students.get(index);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    // Retourne tous les étudiants ayant le nom de famille en paramètre.
    public ArrayList<Student> getStudentsbyLastname(String lastname) {
        return (ArrayList<Student>) this.students.stream().filter(student -> student.lastname.equalsIgnoreCase(lastname)).collect(Collectors.toList());
    }

    // Retourne la moyenne générale de l'étudiant.
    // Attention: la moyenne est calculée avec le total des notes et non par matière.
    public double getAverage() {
        int totalWeight = 0;
        double gradeTotal = 0;
        for (Student student : this.students) {
            double studentAvg = student.getAverage();
            if (student.getAverage() < 0) {
                continue;
            }
            gradeTotal += studentAvg;
            totalWeight++;
        }
        return totalWeight > 0 ? gradeTotal / totalWeight : -1;
    }

    // Retourne la moyenne de l'étudiant dans la matière passée en paramètre.
    public double getAverage(String subject) {
        int totalWeight = 0;
        double gradeTotal = 0;
        for (Student student : this.students) {
            double studentAvg = student.getAverage(subject);
            if (student.getAverage() < 0) {
                continue;
            }
            gradeTotal += studentAvg;
            totalWeight++;
        }
        return totalWeight > 0 ? gradeTotal / totalWeight : -1;
    }

    // Retourne tous les noms de sujets présents dans la liste de l'étudiant.
    public ArrayList<String> getSubjectList() {
        ArrayList<String> list = new ArrayList<>();
        for (Student student : this.students ) {
            for (Grade grade : student.grades) {
                if (list.stream().noneMatch(i -> i.equalsIgnoreCase(grade.name))) {
                    list.add(grade.name);
                }
            }
        }
        return list;
    }

    //----------------
    // METHODES: SAUVEGARDE
    public void saveInFile(String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fw = new FileWriter("src/data/" + filename);
        gson.toJson(this, fw);
        System.out.printf("La classe a été sauvegardée avec succès (src/data/%s)!\n", filename);
        fw.close();
    }

    public static Class readFromFile(String filename) throws Exception {
        Gson gson = new Gson();
        FileReader r = new FileReader("src/data/" + filename);
        Class classData = gson.fromJson(r, Class.class);
        if (classData == null) {
            throw new InvalidInformationException("Le fichier est vide ou contient des données invalides.");
        }
        r.close();
        classData.sortStudents("lastname");
        return classData;
    }

    //----------------
    // METHODES: AFFICHAGE

    public void display() {
        double avg = this.getAverage();
        System.out.printf("NOM DE LA CLASSE: [ %s ]\n", this.name);
        System.out.printf("PROFESSEUR PRINCIPAL: [ %s ]\n",
                this.headTeacher == null ? "N/A" : this.headTeacher.lastname + ", "+this.headTeacher.firstname);
        System.out.printf("NOMBRE D'ETUDIANTS: [ %d ]\n", this.students.size());
        System.out.printf("MOYENNE GENERALE: [ %s ]\n\n", avg < 0 ? "N/A" : avg + "/20");
    }

    public void displayHeadTeacher() {
        if (this.headTeacher == null) {
            System.out.println("Aucun professeur principal assigné.");
            return;
        }
        System.out.println(this.headTeacher);
    }

    // Affiche la liste de tous les étudiants présents dans la classe.
    public void displayStudentList() {
        displayStudentList(this.students);
    }

    // Affiche la liste des étudiants passés en paramètre.
    public void displayStudentList(ArrayList<Student> studentList) {
        if (studentList.size() == 0 ) {
            System.out.println("Aucun élève n'a été trouvé.");
        } else {
            for (int i = 0; i < studentList.size(); i++) {
                Student current = studentList.get(i);
                double avg = current.getAverage();
                System.out.printf("%d | %s, %s -- Moy. Générale: %s [%d note(s)]\n",
                        i + 1, current.lastname, current.firstname,
                        avg >= 0 ? df.format(avg) + "/20" : "N/A",
                        current.grades.size());
            }
        }
        System.out.println();
    }

    // Remplace la méthode toString de l'objet.
    @Override
    public String toString() {
        double avg = getAverage();
        return String.format("[ %s ]\n   %d étudiant(s) - Moy. Générale: %s",
                this.name,
                this.students.size(),
                avg == -1 ? "N/A" : df.format(avg)+"/20");
    }

    public static void runTests() {
        System.out.println("Début des tests sur PClass.Class...");
        int successful = 0;
        Class c1 = null;

        // Test 1: mauvaise initialisation
        try {
            c1 = new Class("");
        } catch (InvalidInformationException e) {
            successful++;
        }

        // Test 2: initialisation valide
        try {
            c1 = new Class("B1 Info");
            successful += Objects.equals(c1.name, "B1 Info") ? 1 : 0;
        } catch (InvalidInformationException ignored){}

        // Test 3: lecture du fichier erroné
        try {
            c1 = readFromFile("doesntexist.json");
        } catch (Exception e) {
            successful++;
        }

        // Test 4: lecture du fichier valide
        try {
            c1 = readFromFile("unitTests.json");
            successful++;
        } catch (Exception ignored) {}

        // Test 5: Récupération d'un étudiant par ID
        assert c1 != null;
        try {
            if (Objects.equals(c1.getStudent(0).lastname, "Austin")) {
                successful++;
            }
        } catch (Exception ignored) {}

        // Test 6: Récupération d'un étudiant par nom
        try {
            if (Objects.equals(c1.getStudent("Adrien", "Austin").lastname, "Austin")) {
                successful++;
            }
        } catch (Exception ignored) {}

        // Test 7: Récupération de la moyenne générale de la classe
        if (Objects.equals(df.format(c1.getAverage()), "14,17")) {
            successful++;
        }

        // Test 8: Récupération de la moyenne de la classe sur une matière
        if (Objects.equals(df.format(c1.getAverage("maths")), "14,60")) {
            successful++;
        }

        // Test 9: Ajout d'un nouvel étudiant
        try {
            Student s1 = new Student("Amory", "Zaoui", 1999);
            c1.setStudent(s1);
            if (c1.students.size() == 3) {
                successful++;
            }
        } catch (Exception ignored) {}
        System.out.printf("Fin des tests: %d/9 tests réussis.\n", successful);
    }

    public static void main(String[] args) {
        Class.runTests();
    }
}
