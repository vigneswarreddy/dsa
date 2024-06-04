package dsagui;

public class Delivery {
    int orderId;
    String currentLocation;
    int estimatedTime;
    String status;
    String destination;

    Delivery(int orderId, String currentLocation, int estimatedTime, String status, String destination) {
        this.orderId = orderId;
        this.currentLocation = currentLocation;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.destination = destination;
    }

    void updateStatus(String currentLocation, int estimatedTime, String status) {
        this.currentLocation = currentLocation;
        this.estimatedTime = estimatedTime;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "orderId=" + orderId +
                ", currentLocation='" + currentLocation + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", status='" + status + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
