import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                JOptionPane.showMessageDialog(null, "Database connection failed!");
                System.exit(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    // Add a patient to the database
    public void addPatient(String name, int age, String ailment) {
        String query = "INSERT INTO Patients (name, age, ailment) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, ailment);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding patient: " + e.getMessage());
        }
    }

    // Display all patients from the database
    public String displayPatients() {
        StringBuilder patientList = new StringBuilder();
        String query = "SELECT * FROM Patients";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (!rs.next()) {
                patientList.append("No patients in the hospital.");
                return patientList.toString();
            }
            do {
                patientList.append("ID: ").append(rs.getInt("id")).append(", Name: ").append(rs.getString("name"))
                        .append(", Age: ").append(rs.getInt("age")).append(", Ailment: ").append(rs.getString("ailment"))
                        .append("\n");
            } while (rs.next());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error displaying patients: " + e.getMessage());
        }
        return patientList.toString();
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
        }
    }
}

public class gui {
    private static Hospital hospital;
    private static JFrame frame;
    private static JTextField nameField, ageField, ailmentField;
    private static JTextArea displayArea;

    public static void main(String[] args) {
        hospital = new Hospital();

        // Create frame
        frame = new JFrame("Hospital Management System");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel for adding patient
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Patient Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Patient Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Ailment:"));
        ailmentField = new JTextField();
        panel.add(ailmentField);

        JButton addButton = new JButton("Add Patient");
        panel.add(addButton);

        frame.add(panel, BorderLayout.NORTH);

        // Text area for displaying patients
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Button for displaying all patients
        JButton displayButton = new JButton("Display Patients");
        frame.add(displayButton, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(new ActionListener() {
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

                if (name.isEmpty() || ailment.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.");
                } else {
                    hospital.addPatient(name, age, ailment);
                    JOptionPane.showMessageDialog(frame, "Patient added successfully.");
                    nameField.setText("");
                    ageField.setText("");
                    ailmentField.setText("");
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientList = hospital.displayPatients();
                displayArea.setText(patientList);
            }
        });

        // Show the window
        frame.setVisible(true);
    }
}
