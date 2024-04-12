import java.util.List;

class Doctor extends Thread {
	private String name;
	private AvailableEquipment availableEquipment;
	private List<Patient> patients;

	public Doctor(String name, AvailableEquipment availableEquipment, List<Patient> patients) {
		this.name = name;
		this.availableEquipment = availableEquipment;
		this.patients = patients;
	}

	public boolean treat(Patient patient) {
		boolean treated = false;
		boolean hasAllEquipment = availableEquipment.reserveEquipment(patient.getEquipmentNeeded());
		if (!hasAllEquipment) {
			// If not all equipment is available
			System.out.println(name + " cannot treat the patient due to insufficient equipment. Waiting...");
			try {
				synchronized (availableEquipment) {
					availableEquipment.wait(); // Wait until equipment becomes available
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			treated = false;
		}
		if (hasAllEquipment) {
			System.out.println(name + " has reserved all equipment for the patient.");
			try {
				Thread.sleep(patient.getTreatmentTime()); //treatment time
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Release all the equipment used to treat that patient
			availableEquipment.releaseEquipment(patient.getEquipmentNeeded());
			System.out.println(name + " has treated the patient and released equipment.");
			treated = true;
		treated = true;
		}
		return treated;
		
	}
	@Override
	public void run() {
		while (!patients.isEmpty()) {
			Patient patient;
			synchronized (patients) {
				patient = patients.remove(0);
			}

			// Reserve all the equipment needed 
			boolean treated = treat(patient);
			while (!treated) {
				treated = treat(patient);
				
			}
		}
	}
}