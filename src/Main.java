import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Main {public static void main(String[] args) {
    try {
      
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            Scanner scanner = new Scanner(inputFile);

            // Read number of equipment
            int numEquipment = scanner.nextInt();
            scanner.nextLine(); // consume newline

            // Read equipment details
            List<Equipment> equipmentList = new ArrayList<>();

            for (int i = 0; i < numEquipment; i++) {
                String[] equipmentData = scanner.nextLine().split(" ");
                equipmentList.add(new Equipment(equipmentData[0], Integer.parseInt(equipmentData[1])));
            }

            // Read number of patients
            int numPatients = scanner.nextInt();
            scanner.nextLine(); // consume newline

            // Read patient details
            List<Patient> patientList = new ArrayList<>();
            for (int i = 0; i < numPatients; i++) {
                int treatmentTime = scanner.nextInt();
                scanner.nextLine(); // consume newline
                String[] equipmentNeeded = scanner.nextLine().split(" ");
                List<String> equipmentNeededList = Arrays.asList(equipmentNeeded);
                patientList.add(new Patient(treatmentTime, equipmentNeededList));
            }
            scanner.close();

            // Initialize doctors
            AvailableEquipment eq =  new AvailableEquipment(equipmentList);
            int numDoctors = 4; 
            Doctor[] doctors = new Doctor[numDoctors];
            for (int i = 0; i < numDoctors; i++) {
                doctors[i] = new Doctor("Doctor " + (i + 1), eq, patientList);
                doctors[i].start();
            }

            // Wait for all doctors to finish
            long startTime = System.currentTimeMillis();
            for (Doctor doctor : doctors) {
                try {
                    doctor.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("All patients treated, took " + totalTime+ " miliseconds to treat");
        }
    } catch (Exception e) {
        System.out.println("File not found.");
    }
}


}
