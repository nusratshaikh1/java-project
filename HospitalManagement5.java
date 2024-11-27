import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Patient {
    String name;
    int age;
    String ailment;
}

class Hospital {
    private Patient[] patients;  
    private int patientCount;   

    void initialize(int capacity) {
        patients = new Patient[capacity];
        patientCount = 0;
    }

    String addPatient(String name, int age, String ailment) {
        if (patientCount < patients.length) {
            patients[patientCount] = new Patient();
            patients[patientCount].name = name;
            patients[patientCount].age = age;
            patients[patientCount].ailment = ailment;
            patientCount++;
            return "Patient added successfully.";
        } else {
            return "Cannot add more patients; the hospital is at full capacity.";
        }
    }

    String displayPatients() {
        if (patientCount == 0) {
            return "No patients in the hospital.";
        }
        StringBuilder patientList = new StringBuilder("Patient List:\n");
        for (int i = 0; i < patientCount; i++) {
            patientList.append("Name: ").append(patients[i].name)
                    .append(", Age: ").append(patients[i].age)
                    .append(", Ailment: ").append(patients[i].ailment).append("\n");
        }
        return patientList.toString();
    }
}

public class HospitalManagement5 {
    private static Hospital hospital;
    private static JTextArea outputArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Patient Name:");
        JTextField nameField = new JTextField(20);
        JLabel ageLabel = new JLabel("Patient Age:");
        JTextField ageField = new JTextField(20);
        JLabel ailmentLabel = new JLabel("Ailment:");
        JTextField ailmentField = new JTextField(20);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(ailmentLabel);
        inputPanel.add(ailmentField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Patient");
        JButton displayButton = new JButton("Display Patients");
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        String capacityInput = JOptionPane.showInputDialog(frame, "Enter hospital capacity:");
        int capacity = Integer.parseInt(capacityInput);
        hospital = new Hospital();
        hospital.initialize(capacity);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String ailment = ailmentField.getText();
                String message = hospital.addPatient(name, age, ailment);
                outputArea.setText(message);
                nameField.setText("");
                ageField.setText("");
                ailmentField.setText("");
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientList = hospital.displayPatients();
                outputArea.setText(patientList);
            }
        });

        frame.setVisible(true);
    }
}
