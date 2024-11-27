import java.util.Scanner;
import java.util.Vector;

class Patient {
    String name;
    int age;
    String ailment;
}

class Hospital {
    private Vector<Patient> patients;

    Hospital() {
        patients = new Vector<>(); 
    }

    void addPatient(String name, int age, String ailment) {
        Patient newPatient = new Patient();
        newPatient.name = name;
        newPatient.age = age;
        newPatient.ailment = ailment;
        patients.add(newPatient); 
        System.out.println("Patient added successfully.");
    }

    void displayPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients in the hospital.");
            return;
        }
        System.out.println("Patient List:");
        for (Patient patient : patients) {
            System.out.println("Name: " + patient.name + ", Age: " + patient.age + ", Ailment: " + patient.ailment);
        }
    }
}

public class HospitalManagement4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Display Patients");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            Hospital hospital = new Hospital();

            switch (choice) {
                case 1:
                    System.out.print("Enter patient name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter patient age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter ailment: ");
                    String ailment = scanner.nextLine();
                    hospital.addPatient(name, age, ailment);
                    break;
                case 2: 
                    hospital.displayPatients();
                    break;
                case 3: 
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
