package dsagui;

import javax.swing.JOptionPane;
import java.util.*;

public class TransplantExpress {
    private final Map<Integer, Patient> patients = new HashMap<>();
    private final Map<String, Map<String, Organ>> organs = new HashMap<>();
    private final List<Order> orders = new ArrayList<>();
    private final Map<Integer, Delivery> deliveries = new HashMap<>();
    public final DijkstrasShortestPath dsp;
    private final Notification notification = new Notification();
    private int patientIdCounter = 1;
    private boolean suppressNotifications = false;

    private static final Set<String> VALID_BLOOD_TYPES = Set.of("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");
    private static final Set<String> VALID_ORGAN_TYPES = Set.of("brain", "spinal cord", "heart", "blood vessels", "lungs", "trachea", "bronchi",
            "mouth", "esophagus", "stomach", "small intestine", "large intestine", "liver", "pancreas", "gallbladder", "kidneys", "ureters", "bladder", "urethra",
            "pituitary gland", "thyroid gland", "adrenal glands", "lymph nodes", "spleen", "thymus", "testes", "prostate gland", "penis", "ovaries", "fallopian tubes",
            "uterus", "vagina", "skin", "hair", "nails", "bones", "joints", "cartilage", "ligaments", "skeletal muscles", "cardiac muscle", "smooth muscles", "eyes", "ears", "nose", "tongue");
    private static final List<String> GRAPH_LOCATIONS = List.of("Main", "Manipal", "St.Johnson", "Kims", "Rainbow", "Apollo");
    private static final Set<String> DELIVERY_PREFERENCES = Set.of("standard", "urgent", "critical");

    public TransplantExpress(int V) {
        dsp = new DijkstrasShortestPath(V);
    }

    public int registerPatient(String name, int age, String deliveryPreference, String bloodType, int urgencyLevel) throws IllegalArgumentException {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Invalid age. Age should be between 0 and 150.");
        }
        if (!VALID_BLOOD_TYPES.contains(bloodType.toUpperCase())) {
            throw new IllegalArgumentException("Invalid blood type. Valid blood types are: " + VALID_BLOOD_TYPES);
        }
        if (!DELIVERY_PREFERENCES.contains(deliveryPreference.toLowerCase())) {
            throw new IllegalArgumentException("Invalid delivery preference. Valid preferences are: " + DELIVERY_PREFERENCES);
        }
        int id = patientIdCounter++;
        patients.put(id, new Patient(id, name, age, deliveryPreference.toLowerCase(), bloodType.toUpperCase(), urgencyLevel));
        if (!suppressNotifications) {
            notification.sendNotification("New patient registered: " + name);
        }
        return id;
    }

    public void addOrgan(String type, String location, String bloodType) throws IllegalArgumentException {
        if (!VALID_ORGAN_TYPES.contains(type.toLowerCase())) {
            throw new IllegalArgumentException("Invalid organ type. Valid organ types are: " + VALID_ORGAN_TYPES);
        }
        if (!isValidLocation(location)) {
            throw new IllegalArgumentException("Invalid location. Valid locations are: " + GRAPH_LOCATIONS);
        }
        if (!VALID_BLOOD_TYPES.contains(bloodType.toUpperCase())) {
            throw new IllegalArgumentException("Invalid blood type. Valid blood types are: " + VALID_BLOOD_TYPES);
        }

        String organKey = type.toLowerCase() + ":" + bloodType.toUpperCase();
        Map<String, Organ> locationMap = organs.getOrDefault(organKey, new HashMap<>());

        String normalizedLocation = location.toLowerCase();
        if (locationMap.containsKey(normalizedLocation)) {
            // Update existing organ entry
            Organ existingOrgan = locationMap.get(normalizedLocation);
            existingOrgan.setQuantity(existingOrgan.getQuantity() + 1);
        } else {
            // Create new organ entry
            int deliveryTime = calculateDeliveryTime(normalizedLocation);
            locationMap.put(normalizedLocation, new Organ(type.toLowerCase(), normalizedLocation, deliveryTime, bloodType.toUpperCase(), 1));
        }

        organs.put(organKey, locationMap);

        if (!suppressNotifications) {
            notification.sendNotification("New organ added: " + type.toLowerCase());
        }
    }

    private boolean isValidLocation(String location) {
        String normalizedLocation = location.toLowerCase();
        for (String loc : GRAPH_LOCATIONS) {
            if (loc.equalsIgnoreCase(normalizedLocation)) {
                return true;
            }
        }
        return false;
    }

    private int calculateDeliveryTime(String location) {
        int[] distances = dsp.dijkstra(0); // Assuming 'Main' is the source
        int index = getLocationIndex(location.toLowerCase());
        return distances[index];
    }

    private int getLocationIndex(String location) {
        String normalizedLocation = location.toLowerCase();
        for (int i = 0; i < GRAPH_LOCATIONS.size(); i++) {
            if (GRAPH_LOCATIONS.get(i).equalsIgnoreCase(normalizedLocation)) {
                return i;
            }
        }
        return -1; // This should never happen if isValidLocation is used correctly
    }

    public void placeOrder(int patientId, String organType, String deliveryLocation) throws IllegalArgumentException {
        if (!patients.containsKey(patientId)) {
            throw new IllegalArgumentException("Patient ID not found. Please register the patient first.");
        }
        String organKey = organType.toLowerCase() + ":" + patients.get(patientId).getBloodType();
        if (!organs.containsKey(organKey)) {
            throw new IllegalArgumentException("Organ type not found. Please find it in the next center.");
        }
        Map<String, Organ> locationMap = organs.get(organKey);
        Organ selectedOrgan = null;
        String selectedLocation = null;
        String normalizedDeliveryLocation = deliveryLocation.toLowerCase();
        for (Map.Entry<String, Organ> entry : locationMap.entrySet()) {
            if (entry.getValue().getQuantity() > 0) {
                selectedOrgan = entry.getValue();
                selectedLocation = entry.getKey();
                break;
            }
        }
        if (selectedOrgan == null) {
            throw new IllegalArgumentException("Organ not available in sufficient quantity.");
        }

        Patient patient = patients.get(patientId);
        orders.add(new Order(patientId, organType.toLowerCase(), normalizedDeliveryLocation, patient.getUrgencyLevel()));
        orders.sort(Comparator.comparingInt(Order::getUrgencyLevel).reversed()); // Sort orders by urgency level, highest first
        int orderId = orders.size() - 1;
        int sourceIndex = getLocationIndex(selectedLocation);
        int destinationIndex = getLocationIndex(normalizedDeliveryLocation);
        int distance = dsp.getDistance(sourceIndex, destinationIndex);
        int estimatedTime = distance; // 1 minute per kilometer
        deliveries.put(orderId, new Delivery(orderId, selectedLocation, estimatedTime, "Pending", normalizedDeliveryLocation));
        notification.sendNotification("Order placed for patient " + patientId + " for organ " + organType.toLowerCase());

        int cost = distance * 10; // 10 rupees per kilometer
        displayMessage("Distance: " + distance + " km\nEstimated delivery time: " + estimatedTime + " minutes\nDelivery cost: " + cost + " rupees.\nOrder placed successfully!");

        selectedOrgan.setQuantity(selectedOrgan.getQuantity() - 1);
        if (selectedOrgan.getQuantity() == 0) {
            locationMap.remove(selectedLocation);
        }

        if (locationMap.isEmpty()) {
            organs.remove(organKey);
        }
    }

    public void updateDeliveryStatus(int orderId, String currentLocation, int estimatedTime, String status) throws IllegalArgumentException {
        if (deliveries.containsKey(orderId)) {
            deliveries.get(orderId).updateStatus(currentLocation.toLowerCase(), estimatedTime, status);
            notification.sendNotification("Delivery update for order " + orderId + ": " + status);
            displayMessage("Now the order is updated and order is at " + currentLocation + ". Delivery status updated successfully!");
        } else {
            throw new IllegalArgumentException("Order ID not found.");
        }
    }

    public List<Patient> getPatients() {
        return new ArrayList<>(patients.values());
    }

    public List<Organ> getOrgans() {
        List<Organ> allOrgans = new ArrayList<>();
        for (Map<String, Organ> organMap : organs.values()) {
            allOrgans.addAll(organMap.values());
        }
        return allOrgans;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public List<Delivery> getDeliveries() {
        return new ArrayList<>(deliveries.values());
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
