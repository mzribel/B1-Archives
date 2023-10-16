package PUtils;

import PClass.Class;
import PStudent.Student;
import PTeacher.Teacher;
import java.io.IOException;
import java.util.ArrayList;

import static PUtils.Menu.drawTitleBox;
import static PUtils.Menu.launchMenu;
import static PUtils.Menu_Student.studentMenu;

public class Menu_Class {

    // Crée le menu qui affiche les détails de la classe.
    public static void displayDetailsMenu(Class classdata) {
        System.out.println("--- VUE DETAILLEE ---\n");
        classdata.display();
        int temp = Utils.getUserInt("1 | Afficher la liste des élèves\n0 | Quitter\n   → ", true, 0, 1);
        System.out.println();
        if (temp == 0) {
            return;
        }
        System.out.println("LISTE DES ETUDIANTS:");
        classdata.displayStudentList();
        Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
    }

    // Crée le menu de sélection d'un étudiant (qui mène au menu étudiant)
    public static void selectStudentMenu(Class classdata) {
        String[] menuOptions = {"Dans la liste", "Par nom de famille", "Par nom et prénom"};
        int userInput;
        do {
            System.out.println("--- SELECTION D'UN ETUDIANT ---");
            userInput = launchMenu(false, menuOptions, "\n");
            switch (userInput) {
                case 1 -> {
                    System.out.println("--- SELECTION PAR INDEX ---");
                    classdata.displayStudentList();
                    int studentID = Utils.getUserInt("Index de l'étudiant (0 pour quitter) → ", true, 0, classdata.students.size());
                    System.out.println();
                    if (studentID == 0) {
                        continue;
                    }
                    studentMenu(classdata, classdata.getStudent(studentID-1));
                    return;
                }
                case 2 -> {
                    System.out.println("--- SELECTION PAR NOM ---");
                    String studentName = Utils.getUserString("Nom de famille de l'étudiant → ", 2, 20);
                    ArrayList<Student> temp = classdata.getStudentsbyLastname(studentName);
                    classdata.displayStudentList(temp);
                    int studentID = Utils.getUserInt("Index de l'étudiant (0 pour quitter) → ", true, 0, classdata.students.size());
                    System.out.println();
                    if (studentID == 0) {
                        continue;
                    }
                    studentMenu(classdata, classdata.getStudent(studentID-1));
                    return;
                }
                case 3 -> {
                    System.out.println("--- SELECTION PAR NOM/PRENOM ---");
                    String studentLastname = Utils.getUserString("Nom de famille de l'étudiant → ", 2, 20);
                    String studentFirstname = Utils.getUserString("Nom de famille de l'étudiant → ", 2, 20);
                    Student result = classdata.getStudent(studentFirstname, studentLastname);
                    if (result == null) {
                        System.out.println("Aucun élève n'a été trouvé.");
                        Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
                        continue;
                    }
                    studentMenu(classdata, result);
                    return;
                }
            }
        } while (userInput != 0);
        System.out.println();
    }

    // Crée le menu d'ajout d'un nouvel étudiant.
    public static void addNewStudentMenu(Class classdata) throws Utils.InvalidInformationException {
        String studentFirstname, studentLastname;
        int birthYear;
        Student newStudent = null;
        int hasStudentID = 0;
        int userInput;
        do {
            try {
                System.out.println("--- AJOUT D'UN NOUVEL ETUDIANT ---");
                studentLastname = Utils.getUserString("Nom de famille de l'étudiant → ", 2, 20);
                studentFirstname = Utils.getUserString("Prénom de l'étudiant → ", 2, 20);
                birthYear = Utils.getUserInt("Année de naissance de l'étudiant → ", true, 1900, 2020);
                System.out.println();

                newStudent = new Student(studentFirstname, studentLastname, birthYear);
                hasStudentID = classdata.hasStudent(studentFirstname, studentLastname);
                classdata.setStudent(newStudent);
                System.out.println("Etudiant ajouté avec succès!");
            } catch (Utils.ElementAlreadyExistsException e) {
                System.out.println("Un étudiant existe déjà avec ces nom et prénom. Remplacer ?");
                int tempUserInput = launchMenu(true, null, "");
                System.out.println();
                if (tempUserInput == 1) {
                    classdata.updateStudent(hasStudentID, newStudent);
                    System.out.println("Etudiant mis à jour avec succès!");
                }
            }
            userInput = Utils.getUserInt("1 | Ajouter un autre étudiant\n0 | Retour\n   → ", true, 0, 1);
        } while (userInput != 0);

    }

    // Crée le menu de suppression d'une note sur une classe entière.
    public static void removeGradeToAll(Class classdata) {
        String[] menuOptions = {"Supprimer toutes les notes", "Supprimer toutes les notes d'une matière", "Supprimer une note par matière et nom"};
        int userInput;

        do {
            System.out.println("--- SUPPRESSION DES NOTES ---");
            if (classdata.students.size() == 0) {
                System.out.println("Aucun étudiant dans la liste: impossible de supprimer une note.\n");
                Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                return;
            }
            userInput = launchMenu(false, menuOptions, "\n");

            switch (userInput) {
                case 1 -> {
                    System.out.println("--- SUPPRESSION DE TOUTES LES NOTES ---");
                    System.out.println("/!\\ Êtes vous sûr de vouloir supprimer toutes les notes de tous les élèves ?");
                    int temp = Utils.getUserInt("1 | Oui\n0 | Retour\n   → ", true, 0, 1);
                    if (temp == 0) {
                        continue;
                    }
                    for (Student student : classdata.students) {
                        student.removeAllGrades();
                    }
                    System.out.println("Toutes les notes ont été supprimées !\n");
                }
                case 2 -> {
                    System.out.println("--- SUPPRESSION DES NOTES D'UNE MATIERE ---");
                    String subject = Utils.getUserString("Nom de la matière → ", 2, 20);
                    for (Student student : classdata.students) {
                        student.removeGradesBySubject(subject);
                    }
                    System.out.println("Toutes les notes de la matière ont été supprimées !\n");
                }
                case 3 -> {
                    System.out.println("--- SUPPRESSION D'UNE NOTE PAR MATIERE ET NOM ---");
                    String subject = Utils.getUserString("Nom de la matière → ", 2, 20);
                    String gradeName = Utils.getUserString("Nom de la note → ", 2, 20);
                    for (Student student : classdata.students) {
                        student.removeGrade(subject, gradeName);
                    }
                    System.out.println("Toutes les notes avec ces matière et nom ont été supprimées !\n");
                }
            }
            int temp = Utils.getUserInt("1 | Supprimer une autre note\n0 | Retour\n   → ", true, 0, 1);
            if (temp == 0) {
                return;
            }
        } while (userInput != 0);
    }

    public static void editClassMenu(Class classdata) throws Utils.InvalidInformationException {
        String[] menuOptions = {"Editer le nom de la classe", "Editer le professeur principal"};
        int userInput;
        do {
            System.out.println("--- EDITER LES INFORMATIONS DE LA CLASSE ---");
            System.out.printf("NOM DE LA CLASSE: [ %s ]\n", classdata.name);
            System.out.printf("PROFESSEUR PRINCIPAL: [ %s ]\n\n",
                    classdata.headTeacher == null ? "N/A" : classdata.headTeacher.lastname + ", " + classdata.headTeacher.firstname);
            userInput = launchMenu(false, menuOptions, "\n");
            switch (userInput) {
                case 1 -> {
                    System.out.println("--- EDITER LE NOM DE LA CLASSE ---");
                    String name = Utils.getUserString("Nouveau nom de la classe: ", 2, 20);
                    classdata.setClassName(name);
                    System.out.println("Nom de la classe édité avec succès!\n");
                    Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                }
                case 2 -> {
                    System.out.println("--- EDITER LE PROFESSEUR PRINCIPAL ---");
                    System.out.print("Informations actuelles: \n   "); classdata.displayHeadTeacher();
                    System.out.println();
                    int temp = Utils.getUserInt("1 | Editer le professeur\n2 | Supprimer le professeur\n0 | Retour\n    → ", true, 0, 2);
                    System.out.println();
                    switch (temp) {
                        case 1 -> {
                            String lastname = Utils.getUserString("Nom de famille du professeur → ", 2, 20);
                            String firstname = Utils.getUserString("Prénom du professeur → ", 2, 20);
                            int birthYear = Utils.getUserInt("Année de naissance du professeur → ", true, 1900, 2020);
                            String speciality = Utils.getUserString("Spécialité du professeur → ", 2, 20);
                            System.out.println();

                            classdata.headTeacher = new Teacher(firstname, lastname, birthYear, speciality, true);
                            System.out.println("Professeur principal édité avec succès!\n");
                            Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                            System.out.println();
                        }
                        case 2 -> {
                            System.out.println("/!\\ Êtes vous sûr de vouloir supprimer le professeur principal de cette classe ?");
                            int verif = Utils.getUserInt("1 | Oui\n0 | Retour\n   → ", true, 0, 1);
                            System.out.println();
                            if (verif != 0) {
                                classdata.headTeacher = null;System.out.println("Professeur principal supprimé avec succès!\n");
                                Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                                System.out.println();
                            }
                        }
                    }

                }
            }

        } while (userInput != 0);
    }

    public static void classMenu(Class classdata) throws Utils.InvalidInformationException {
        int userInput;
        String[] menuOptions = {"Liste des livres", "Sélectionner un étudiant", "Ajouter un étudiant",
                "Ajouter une note à tous", "Supprimer une note à tous", "Editer les informations de la classe", "Sauvegarder la classe"};
        do {
            drawTitleBox(48, "E D I T I O N  D ' U N E  C L A S S E", "JAVA | TP n°3");
            System.out.println("Classe en cours d'édition: "+classdata+"\n");
            userInput = launchMenu(false, menuOptions, "\n");

            switch (userInput) {
                case 1 -> displayDetailsMenu(classdata);
                case 2 -> selectStudentMenu(classdata);
                case 3 -> addNewStudentMenu(classdata);
                case 4 ->{
                    System.out.println("--- AJOUT D'UNE NOUVELLE NOTE A TOUS ---");
                    if (classdata.students.size() == 0) {
                        System.out.println("Aucun étudiant dans la liste: impossible d'ajouter une nouvelle note.\n");
                        Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                        continue;
                    }
                    double studentPoints;
                    String subject = Utils.getUserString("Nom de la matière → ", 2, 20);
                    String gradeName = Utils.getUserString("Nom de la note → ", 2, 20);

                    if (classdata.studentsHaveGrade(subject, gradeName)) {
                        System.out.println("\n/!\\ Au moins un étudiant possède déjà une note similaire.\n/!\\Continuer écrasera cette note.");
                        int temp = Utils.getUserInt("1 | Continuer\n0 | Quitter\n   → ", true, 0, 1);
                        if (temp == 0) {
                            continue;
                        }
                    }

                    int weight = Utils.getUserInt("Coefficient de la note → ", true, 0, 15);
                    double maxPoints = Utils.getUserInt("Nombre de points maximum (note /??) → ", true, 1, 100);
                    System.out.println("\nNOTE: une note à -1 ne compte pas dans la moyenne de l'étudiant.\n");
                    for (Student student : classdata.students) {
                        studentPoints = Utils.getUserDouble(String.format("Note de [%s, %s], /%s → ", student.lastname, student.firstname, maxPoints),
                                true, -1, maxPoints);
                        try {
                            student.setGrade(subject, gradeName, weight, studentPoints, maxPoints);
                            System.out.println("OK !");
                        } catch (Exception e) {
                            System.out.print("/!\\ Une erreur est survenue: ");
                            System.out.println(e.getMessage());
                        }
                    }
                    System.out.println();
                    Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                }
                case 5 -> removeGradeToAll(classdata);
                case 6 -> editClassMenu(classdata);
                case 7 -> {
                    System.out.println("La classe sera sauvegardée dans le fichier `src/data/data.json`");
                    System.out.println("Voulez-vous continuer ?");
                    int tempUserInput = launchMenu(true, null, "");
                    System.out.println();
                    if (tempUserInput == 0) {
                        continue;
                    }
                    try {
                        classdata.saveInFile("data.json");
                    } catch (IOException e) {
                        System.out.println("/!\\ Une erreur est survenue: " + e.getMessage());
                        continue;
                    }
                    Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                }
            }
            System.out.println();
        } while (userInput != 0);
    }

}
