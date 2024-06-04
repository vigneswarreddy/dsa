package dsagui;

public class Order {
    int patientId;
    String organType;
    String deliveryLocation;
    int urgencyLevel;

    public Order(int patientId, String organType, String deliveryLocation, int urgencyLevel) {
        this.patientId = patientId;
        this.organType = organType;
        this.deliveryLocation = deliveryLocation;
        this.urgencyLevel = urgencyLevel;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    @Override
    public String toString() {
        return "Order{" +
                "patientId=" + patientId +
                ", organType='" + organType + '\'' +
                ", deliveryLocation='" + deliveryLocation + '\'' +
                ", urgencyLevel=" + urgencyLevel +
                '}';
    }
}
