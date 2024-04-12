import java.util.ArrayList;
import java.util.List;

class AvailableEquipment {
    private List<Equipment> equipmentList;

    public AvailableEquipment(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public synchronized boolean reserveEquipment(List<String> equipmentNeeded) {
        List<String> reservedEquipment = new ArrayList<>(); // List to keep track of reserved equipment
        boolean hasAllEquipment = true; 
        for (String equipmentName : equipmentNeeded) {
            boolean found = false;
            for (Equipment equipment : equipmentList) {
                if (equipment.getName().equals(equipmentName) && equipment.getQuantity() > 0) {
                    equipment.setQuantity(equipment.getQuantity() - 1);
                    found = true;
                    reservedEquipment.add(equipmentName);
                    break;
                }
            }
            if (!found) {
                hasAllEquipment = false;
                break;
            }
        }
        if (!hasAllEquipment) {
            // Release any already reserved equipment if we can't get them all
            for (String reserved : reservedEquipment) {
                for (Equipment equipment : equipmentList) {
                    if (equipment.getName().equals(reserved)) {
                        equipment.setQuantity(equipment.getQuantity() + 1);
                        break;
                    }
                }
            }
            notify(); 
        }
        return hasAllEquipment;
    }


    public synchronized void releaseEquipment(List<String> equipmentNeeded) {
        for (String equipmentName : equipmentNeeded) {
            for (Equipment equipment : equipmentList) {
                if (equipment.getName().equals(equipmentName)) {
                    equipment.setQuantity(equipment.getQuantity() + 1);
                    break;
                }
            }
        }
        notify(); // Notify waiting threads that equipment is available
    }
}
