import java.sql.*;
import java.util.Scanner;

class Patient {
    String name;
    int age;
    String ailment;

    public Patient(String name, int age, String ailment) {
        this.name = name;
        this.age = age;
        this.ailment = ailment;
    }
}

class Hospital {
    private Connection conn;

    // Constructor establishes the database connection
    public Hospital() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitaldb", "root", "");
            if (conn == null) {
                System.out.println("Database connection failed!");
                System.exit(1); // Exit if connection fails
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            System.exit(1); // Exit if connection fails
        }
    }

    // Add a patient to the database
    void addPatient(String name, int age, String ailment) {
        String query = "INSERT INTO Patients (name, age, ailment) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, ailment);
            stmt.executeUpdate();
            System.out.println("Patient added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }

    // Display all patients from the database
    void displayPatients() {
        String query = "SELECT * FROM Patients";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (!rs.next()) {
                System.out.println("No patients in the hospital.");
                return;
            }
            System.out.println("Patient List:");
            do {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age") + ", Ailment: " + rs.getString("ailment"));
            } while (rs.next());
        } catch (SQLException e) {
            System.out.println("Error displaying patients: " + e.getMessage());
        }
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}

public class Jdbc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital(); // Create Hospital object that manages database operations

        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Display Patients");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading an integer

            switch (choice) {
                case 1:
                    System.out.print("Enter patient name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter patient age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character after reading an integer
                    System.out.print("Enter ailment: ");
                    String ailment = scanner.nextLine();
                    hospital.addPatient(name, age, ailment);
                    break;
                case 2:
                    hospital.displayPatients();
                    break;
                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    hospital.closeConnection(); // Close the database connection before exiting
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
