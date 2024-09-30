import java.util.Scanner;

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

    void addPatient(String name, int age, String ailment) {
        if (patientCount < patients.length) {
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

public class HospitalManagement3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter hospital capacity: ");
        int capacity = scanner.nextInt();
        Hospital hospital = new Hospital();
        hospital.initialize(capacity); 

        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Display Patients");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

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
