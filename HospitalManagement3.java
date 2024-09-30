import java.util.Scanner;

// Class representing a patient
class Patient {
    String name;
    int age;
    String ailment;
}

// Class managing hospital operations
class Hospital {
    private Patient[] patients; // Array to store patients
    private int patientCount;   // Current number of patients

    // Method to initialize the hospital capacity
    void initialize(int capacity) {
        patients = new Patient[capacity];
        patientCount = 0;
    }

    // Method to add a new patient
    void addPatient(String name, int age, String ailment) {
        if (patientCount < patients.length) {
            // Create a new Patient object
            patients[patientCount] = new Patient();
            patients[patientCount].name = name;
            patients[patientCount].age = age;
            patients[patientCount].ailment = ailment;
            patientCount++;
            System.out.println("Patient added successfully.");
        } else {
            System.out.println("Cannot add more patients; the hospital is at full capacity.");
        }
    }

    // Method to display all patients
    void displayPatients() {
        if (patientCount == 0) {
            System.out.println("No patients in the hospital.");
            return;
        }
        System.out.println("Patient List:");
        for (int i = 0; i < patientCount; i++) {
            System.out.println("Name: " + patients[i].name + ", Age: " + patients[i].age + ", Ailment: " + patients[i].ailment);
        }
    }
}

// Main class for running the hospital management program
public class HospitalManagement3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input hospital capacity
        System.out.print("Enter hospital capacity: ");
        int capacity = scanner.nextInt();
        Hospital hospital = new Hospital();
        hospital.initialize(capacity); // Initialize hospital capacity

        // Main loop for the menu
        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Display Patients");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Add patient
                    System.out.print("Enter patient name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter patient age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter ailment: ");
                    String ailment = scanner.nextLine();
                    hospital.addPatient(name, age, ailment);
                    break;
                case 2: // Display patients
                    hospital.displayPatients();
                    break;
                case 3: // Exit
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default: // Invalid choice
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
