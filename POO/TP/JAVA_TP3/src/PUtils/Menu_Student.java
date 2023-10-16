package PUtils;

import PClass.Class;
import PStudent.Student;

import static PUtils.Menu.drawTitleBox;
import static PUtils.Menu.launchMenu;

public class Menu_Student {

    // Crée le menu de suppression des notes
    public static void removeGradeMenu(Student student) {
        int userInput;
        String[] menuOptions = {"Supprimer toutes les notes", "Supprimer toutes les notes d'une matière", "Supprimer une note par matière et nom"};
        do {
            System.out.println("--- SUPPRESSION D'UNE NOTE ---");
            if (student.grades.size() == 0) {
                System.out.println("La liste des notes est vide !\n");
                Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                return;
            }
            userInput = launchMenu(false, menuOptions, "\n");

            switch (userInput) {
                case 1 -> {
                    System.out.println("--- SUPPRESSION DE TOUTES LES NOTES ---");
                    System.out.println("/!\\ Êtes vous sûr de vouloir supprimer toutes les notes de cet élève ?");
                    int temp = Utils.getUserInt("1 | Oui\n0 | Retour\n   → ", true, 0, 1);
                    if (temp == 0) {
                        continue;
                    }
                    student.removeAllGrades();
                    System.out.println("Toutes les notes ont été supprimées !\n");
                }
                case 2 -> {
                    System.out.println("--- SUPPRESSION DES NOTES D'UNE MATIERE ---");
                    String subject = Utils.getUserString("Nom de la matière → ", 2, 20);
                    student.removeGradesBySubject(subject);
                    System.out.println("Toutes les notes de la matière ont été supprimées !\n");
                }
                case 3 -> {
                    System.out.println("--- SUPPRESSION D'UNE NOTE PAR MATIERE ET NOM ---");
                    String subject = Utils.getUserString("Nom de la matière → ", 2, 20);
                    String gradeName = Utils.getUserString("Nom de la note → ", 2, 20);
                    student.removeGrade(subject, gradeName);
                    System.out.println("Toutes les notes avec ces matière et nom ont été supprimées !\n");
                }
            }
            int temp = Utils.getUserInt("1 | Supprimer une autre note\n0 | Retour\n   → ", true, 0, 1);
            if (temp == 0) {
                return;
            }
    } while (userInput != 0);    }

    // Crée le menu d'ajout des notes
    public static void addGradeMenu(Student student) {
        System.out.println("--- AJOUT D'UNE NOUVELLE NOTE ---");
        double studentPoints;
        String subject = Utils.getUserString("Nom de la matière → ", 2, 20);
        String gradeName = Utils.getUserString("Nom de la note → ", 2, 20);

        if (student.hasGrade(subject, gradeName) >= 0) {
            System.out.println("\n/!\\ L'étudiant possède déjà une note similaire.\n/!\\Continuer écrasera cette note.");
            int temp = Utils.getUserInt("1 | Continuer\n0 | Quitter\n   → ", true, 0, 1);
            if (temp == 0) {
                return;
            }
        }
        int weight = Utils.getUserInt("Coefficient de la note → ", true, 0, 15);
        double maxPoints = Utils.getUserInt("Nombre de points maximum (note /??) → ", true, 1, 100);
        System.out.println("\nNOTE: une note à -1 ne compte pas dans la moyenne de l'étudiant.\n");
        studentPoints = Utils.getUserDouble(String.format("Note de l'étudiant, /%s → ", maxPoints),
                true, -1, maxPoints);
        try {
            student.setGrade(subject, gradeName, weight, studentPoints, maxPoints);
            System.out.println("OK !");
        } catch (Exception e) {
            System.out.print("/!\\ Une erreur est survenue: ");
            System.out.println(e.getMessage());
        }
        System.out.println();
        Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
    }

    // Crée le menu d'affichage des détails de l'étudiant
    public static void displayDetailsMenu(Student student) {
        System.out.println("--- VUE DETAILLEE ---\n");
        student.display();
        int temp = Utils.getUserInt("1 | Afficher les notes\n0 | Quitter\n   → ", true, 0, 1);
        System.out.println();
        if (temp == 0) {
            return;
        }
        System.out.println("LISTE DES NOTES:");
        student.displayGrades();
        Utils.getUserInt("0 | Retour\n   → ", true, 0, 0);
    }

    // Crée le menu général pour un étudiant
    public static void studentMenu(Class classdata, Student student) {
        int userInput;
        String[] menuOptions = {"Vue détaillée", "Ajouter une note", "Supprimer une note",
                "Editer les informations", "Supprimer l'étudiant de la classe"};
        do {
            drawTitleBox(48, "E D I T I O N  D ' U N  E L E V E", "JAVA | TP n°3");
            System.out.println("Etudiant en cours d'édition:\n   " + student + "\n");
            userInput = launchMenu(false, menuOptions, "\n");

            switch (userInput) {
                case 1 -> displayDetailsMenu(student);
                case 2 -> addGradeMenu(student);
                case 3 -> removeGradeMenu(student);
                case 4 -> {
                    System.out.println("--- EDITER LES INFORMATIONS DE L'ETUDIANT ---");
                    System.out.printf("NOM DE L'ETUDIANT: [ %s, %s ]\n", student.lastname, student.firstname);
                    System.out.printf("ANNEE DE NAISSANCE DE L'ETUDIANT: [ %d ]\n\n", student.birthYear);
                    int temp = Utils.getUserInt("1 | Editer ces informations\n0 | Retour\n   → ", true, 0, 1);
                    System.out.println();
                    if (temp == 0) {
                        continue;
                    }
                    String lastname = Utils.getUserString("Nom de famille de l'étudiant → ", 2, 20);
                    String firstname = Utils.getUserString("Prénom de l'étudiant → ", 2, 20);
                    int alreadyExists = classdata.hasStudent(firstname, lastname);
                    if (alreadyExists > -1) {
                        System.out.println("/!\\ Impossible de renommer l'élève: un élève avec ce nom existe déjà dans la classe.\n");
                        Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                        continue;
                    }
                    int birthYear = Utils.getUserInt("Année de naissance de l'étudiant → ", true, 1900, 2020);
                    try {
                        student.setInfo(firstname, lastname, birthYear);
                        System.out.println("L'étudiant a été édité avec succès !\n");
                        Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                    } catch (Utils.InvalidInformationException e) {
                        System.out.println("/!\\ Impossible de renommer l'élève: une erreur est survenue.\n");
                        System.out.println(e.getMessage());
                        Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                    }
                    System.out.println();
                }
                case 5 -> {
                    System.out.println("--- SUPPRESSION D'UN ELEVE DE LA CLASSE ---");
                    System.out.println("/!\\ Êtes vous sûr de vouloir supprimer cet élève de la classe ?");
                    int temp = Utils.getUserInt("1 | Oui\n0 | Retour\n   → ", true, 0, 1);
                    if (temp == 0) {
                        continue;
                    }
                    classdata.removeStudent(student);
                    System.out.println("L'étudiant a été supprimé de la classe avec succès !\n");
                    Utils.getUserInt("0 | Continuer\n   → ", true, 0, 0);
                    return;
                }
            }
        }while (userInput != 0) ;

    }
}
