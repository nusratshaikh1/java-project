import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class gui {
    private JFrame frame;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField ailmentField;
    private JTextArea displayArea;
    private Hospital hospital;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gui().createAndShowGUI());
    }

    public gui() {
        hospital = new Hospital(); // Create the Hospital object to manage database operations
    }

    public void createAndShowGUI() {
        // Create the main window (JFrame)
        frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Panel for input fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2)); // 4 rows, 2 columns

        // Input fields for name, age, and ailment
        inputPanel.add(new JLabel("Patient Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Patient Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Patient Ailment:"));
        ailmentField = new JTextField();
        inputPanel.add(ailmentField);

        // Add "Add Patient" button
        JButton addButton = new JButton("Add Patient");
        addButton.addActionListener(new AddButtonListener());
        inputPanel.add(addButton);

        // Display area for showing patients
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add the components to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button to display all patients
        JButton displayButton = new JButton("Display Patients");
        displayButton.addActionListener(new DisplayButtonListener());
        frame.add(displayButton, BorderLayout.SOUTH);

        // Display the frame
        frame.setVisible(true);
    }

    // ActionListener for the "Add Patient" button
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            int age;
            try {
                age = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age.");
                return;
            }
            String ailment = ailmentField.getText();

            // Add the patient to the database
            hospital.addPatient(name, age, ailment);

            // Clear input fields after adding
            nameField.setText("");
            ageField.setText("");
            ailmentField.setText("");
        }
    }

    // ActionListener for the "Display Patients" button
    private class DisplayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Display all patients in the text area
            hospital.displayPatients(displayArea);
        }
    }

    // Inner class for managing the database connection and operations
    class Hospital {
        private Connection conn;

        // Constructor establishes the database connection
        public Hospital() {
            try {
                // Update the connection URL, username, and password to use the provided database server details
                String dbURL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12753373";
                String dbUsername = "sql12753373";
                String dbPassword = "6RzRIjlnwA";

                // Establish the connection to the database
                conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

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
        void displayPatients(JTextArea textArea) {
            String query = "SELECT * FROM Patients";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                if (!rs.next()) {
                    textArea.setText("No patients in the hospital.");
                    return;
                }
                StringBuilder patientList = new StringBuilder();
                do {
                    patientList.append("ID: ").append(rs.getInt("id")).append(", Name: ").append(rs.getString("name"))
                            .append(", Age: ").append(rs.getInt("age")).append(", Ailment: ").append(rs.getString("ailment")).append("\n");
                } while (rs.next());
                textArea.setText(patientList.toString());
            } catch (SQLException e) {
                textArea.setText("Error displaying patients: " + e.getMessage());
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
}