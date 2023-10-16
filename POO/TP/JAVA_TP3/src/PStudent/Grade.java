package PStudent;

import PTeacher.Teacher;
import PUtils.Utils.*;
import java.text.DecimalFormat;

public class Grade {
    public static final DecimalFormat decimalFormat = new DecimalFormat( "#.00" );

    public String subject;
    public String name;
    public int weight;
    public double studentPoints;
    public double maxPoints;

    public Grade(String subject, String name, double studentPoints, double maxPoints) throws InvalidInformationException {
        if (name.length() < 2 || subject.length() < 2) {
            throw new InvalidInformationException("Subject and grade name must have more than 1 character.");
        }
        if (maxPoints <= 0 || studentPoints < -1) {
            throw new InvalidInformationException("Not enough points ?");
        }
        if (maxPoints < studentPoints) {
            throw new InvalidInformationException("Student points cannot be greater than max points.");
        }
        this.subject = subject;
        this.name = name;
        this.weight = 1;
        this.studentPoints = studentPoints;
        this.maxPoints = maxPoints;
    }

    public Grade(String subject, String name, int weight, double studentPoints, double maxPoints) throws InvalidInformationException {
        if (name.length() < 2 || subject.length() < 2) {
            throw new InvalidInformationException("Subject and grade name must have more than 1 character.");
        }
        if (maxPoints <= 0 || studentPoints < -1) {
            throw new InvalidInformationException("Not enough points ?");
        }
        if (maxPoints < studentPoints) {
            throw new InvalidInformationException("Student points cannot be greater than max points.");
        }
        this.subject = subject;
        this.name = name;
        this.weight = weight >= 0 ? weight : 1;
        this.studentPoints = studentPoints;
        this.maxPoints = maxPoints;
    }

    @Override
    public String toString() {
        return String.format("[%s - %s]\n   %s / %s (coeff %d)",
                this.subject, this.name,
                decimalFormat.format(this.studentPoints), decimalFormat.format(this.maxPoints),
                this.weight);
    }
}
