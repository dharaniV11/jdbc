import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();

    public static void main(String[] args) {
        configureLogger();
        while (true) {
            logger.info("\n\u001B[97m===== Student Database Menu =====");
            logger.info("1. Insert Student");
            logger.info("2. View All Students");
            logger.info("3. Remove Student");
            logger.info("4. Update Student");
            logger.info("5. Exit");
            logger.info("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    insertStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    removeStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    logger.info("Exiting...");

                default:
                    logger.warning("Invalid option. Try again.");
            }
        }
    }

    private static void insertStudent() {
        Student student = studentService.createStudent();
        boolean isInserted = DatabaseManager.insertStudent(student);
        if (isInserted) {
            logger.info("Student data inserted successfully!");
        } else {
            logger.warning("Failed to insert student data.");
        }
    }

    private static void viewAllStudents() {
        if (DatabaseManager.isEmpty()) {
            logger.info("No student found in the database.");
        } else {
            logger.info("\n--- All Students ---");
            DatabaseManager.displayAllStudents();
        }
    }

    private static void removeStudent() {
        if (DatabaseManager.isEmpty()) {
            logger.info("No student to remove.");
        } else {
            logger.info("Enter Student ID to remove: ");
            int id = getIntInput();
            boolean isRemoved = DatabaseManager.removeStudent(id);
            if (isRemoved) {
                logger.info("Student with ID " + id + " has been removed.");
            } else {
                logger.info("No student found with ID " + id + ".");
            }
        }
    }

    private static void updateStudent() {
        try {
            if (DatabaseManager.isEmpty()) {
                logger.info("No student found to update.");
                return;
            }

            logger.info("Enter the ID of the student to update: ");
            int id = getIntInput();

            logger.info("Enter new details for the student:");
            Student updatedStudent = studentService.createStudent();

            boolean isUpdated = DatabaseManager.updateStudent(id, updatedStudent);
            if (isUpdated) {
                logger.info("Student with ID " + id + " updated successfully.");
            } else {
                logger.warning("Update failed. No student found with ID " + id + ".");
            }
        } catch (DatabaseAccessException e) {
            logger.severe("Database error : " + e.getMessage());
        }
    }


    private static int getIntInput() {
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


    private static void configureLogger() {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();

        for (Handler handler : handlers) {
            handler.setFormatter(new SimpleFormatter() {
                private static final String FORMAT = "%s%n";
                @Override
                public synchronized String format(LogRecord record) {
                    return String.format(FORMAT, record.getMessage());
                }
            });
        }
    }
}
