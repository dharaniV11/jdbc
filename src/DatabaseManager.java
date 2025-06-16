import java.sql.*;
import java.util.logging.*;

public class DatabaseManager {
    static Logger logger = Logger.getLogger(DatabaseManager.class.getName() );
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Dharani@v11";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static boolean insertStudent(Student student) {
        String sql = "INSERT INTO students (name, tamil, english, maths, science, social, average, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql, student.getName(), student.getTamil(), student.getEnglish(), student.getMaths(),
                student.getScience(), student.getSocial(), student.getAverage(), String.valueOf(student.getGrade()));
    }

    private static boolean executeUpdate(String sql, Object... params) {
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void displayAllStudents() {
        String sql = "SELECT * FROM students";
        try (Connection con = getConnection(); Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                logger.info(String.format("ID: %d | Name: %s | Tamil: %d | English: %d | Maths: %d | Science: %d | Social: %d | Avg: %.2f | Grade: %s",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("tamil"), rs.getInt("english"), rs.getInt("maths"),
                        rs.getInt("science"), rs.getInt("social"), rs.getDouble("average"), rs.getString("grade")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean removeStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        return executeUpdate(sql, id);
    }

    public static boolean updateStudent(int id, Student student) {
        String sql = "UPDATE students SET name=?, tamil=?, english=?, maths=?, science=?, social=?, average=?, grade=? WHERE id=?";
        return executeUpdate(sql, student.getName(), student.getTamil(), student.getEnglish(), student.getMaths(),
                student.getScience(), student.getSocial(), student.getAverage(), String.valueOf(student.getGrade()), id);
    }

    public static boolean isEmpty() {
        String sql = "SELECT COUNT(*) AS total FROM students";
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() && rs.getInt("total") == 0;
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to access the database");
        }
    }
}
