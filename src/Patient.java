import java.util.List;

class Patient {
    private int treatmentTime;
    private List<String> equipmentNeeded;

    public Patient(int treatmentTime, List<String> equipmentNeeded) {
        this.treatmentTime = treatmentTime;
        this.equipmentNeeded = equipmentNeeded;
    }

    public int getTreatmentTime() {
        return treatmentTime;
    }

    public List<String> getEquipmentNeeded() {
        return equipmentNeeded;
    }

}
