import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.*;

public class StudentService {
    Logger logger = Logger.getLogger("StudentService");
    private Scanner scanner = new Scanner(System.in);

    public Student createStudent() {
        String name;
        String[] subjects = {"Tamil", "English", "Maths", "Science", "Social"};
        int[] marks = new int[subjects.length];

        while (true) {
            logger.info("Enter Student Name: ");
            name = scanner.nextLine();
            if (name.matches("[A-Za-z\\s]+")) break;
            logger.warning("Name cannot contain numbers or symbols. Please try again.");
        }

        for (int i = 0; i < subjects.length; i++) {
            marks[i] = getMarks(subjects[i]);
        }

        // Calculate average and grade
        double average = 0;
        for (int mark : marks) {
            average += mark;
        }
        average /= marks.length;

        char grade = calculateGrade(average);

        return new Student(name, marks[0], marks[1], marks[2], marks[3], marks[4], average, grade);
    }

    private int getMarks(String subject) {
        int marks;
        while (true) {
            logger.info("Enter " + subject + " Marks : ");
            marks = getIntInput();
            if (marks >= 0 && marks <= 100) {
                break; // Valid mark entered, exit loop
            } else {
                logger.warning("Marks must be between 0 and 100. Please try again.");
            }
        }
        return marks;
    }

    private int getIntInput() {
        while (true) {
            try {
                int num = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return num;
            } catch (InputMismatchException e) {
                logger.warning("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // flush
            }
        }
    }

    private char calculateGrade(double average) {
        if (average >= 90) return 'A';
        if (average >= 75) return 'B';
        if (average >= 60) return 'C';
        if (average >= 40) return 'D';
        return 'F';
    }
}
