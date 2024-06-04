package dsagui;

public class Organ {
    String type;
    String location;
    int deliveryTime;
    String bloodType;
    int quantity;

    Organ(String type, String location, int deliveryTime, String bloodType, int quantity) {
        this.type = type;
        this.location = location;
        this.deliveryTime = deliveryTime;
        this.bloodType = bloodType;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Organ{" +
                "type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", bloodType='" + bloodType + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
