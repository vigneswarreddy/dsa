package dsagui;

public class Patient {
    int id;
    String name;
    int age;
    String deliveryPreference;
    String bloodType;
    int urgencyLevel;

    public Patient(int id, String name, int age, String deliveryPreference, String bloodType, int urgencyLevel) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.deliveryPreference = deliveryPreference;
        this.bloodType = bloodType;
        this.urgencyLevel = urgencyLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDeliveryPreference() {
        return deliveryPreference;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", deliveryPreference='" + deliveryPreference + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", urgencyLevel=" + urgencyLevel +
                '}';
    }
}
