package dsagui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

public class GUIApplication {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static TransplantExpress transplantExpress;
    private static Map<String, String> users;

    public static void main(String[] args) {
        users = new HashMap<>();
        users.put("admin", "password"); // You can add more users as needed

        transplantExpress = new TransplantExpress(20);
        initializeData();

        frame = new JFrame("Organ Delivery System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel mainMenuPanel = createMainMenuPanel();

        mainPanel.add(loginPanel, "login");
        mainPanel.add(mainMenuPanel, "mainMenu");

        frame.add(mainPanel);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "login");
    }

    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(loginLabel, gbc);

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if (authenticate(username, password)) {
                    cardLayout.show(mainPanel, "mainMenu");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private static boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    private static JPanel createMainMenuPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("ASSETS/logo.png"); // Replace with your logo path
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(scaledImg, 0, 0, this);

                // Applying blur effect
                float[] blurKernel = {
                        1 / 9f, 1 / 9f, 1 / 9f,
                        1 / 9f, 1 / 9f, 1 / 9f,
                        1 / 9f, 1 / 9f, 1 / 9f
                };
                Kernel kernel = new Kernel(3, 3, blurKernel);
                ConvolveOp op = new ConvolveOp(kernel);
                BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D biGraphics = bufferedImage.createGraphics();
                biGraphics.drawImage(scaledImg, 0, 0, null);
                g2d.drawImage(op.filter(bufferedImage, null), 0, 0, null);
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome to Organ Delivery System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        JButton registerPatientButton = new JButton("Register Patient");
        registerPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerPatient();
            }
        });

        JButton addOrganButton = new JButton("Add Organ");
        addOrganButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrgan();
            }
        });

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });

        JButton updateDeliveryStatusButton = new JButton("Update Delivery Status");
        updateDeliveryStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDeliveryStatus();
            }
        });

        JButton displayPatientsButton = new JButton("Display Patients");
        displayPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPatients();
            }
        });

        JButton displayOrgansButton = new JButton("Display Organs");
        displayOrgansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOrgans();
            }
        });

        JButton displayOrdersButton = new JButton("Display Orders");
        displayOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOrders();
            }
        });

        JButton displayDeliveriesButton = new JButton("Display Deliveries");
        displayDeliveriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDeliveries();
            }
        });

        JButton displayGraphButton = new JButton("Display Graph");
        displayGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayGraph();
            }
        });

        // Ensure all buttons have the same size
        Dimension buttonSize = new Dimension(200, 25);
        registerPatientButton.setPreferredSize(buttonSize);
        addOrganButton.setPreferredSize(buttonSize);
        placeOrderButton.setPreferredSize(buttonSize);
        updateDeliveryStatusButton.setPreferredSize(buttonSize);
        displayPatientsButton.setPreferredSize(buttonSize);
        displayOrgansButton.setPreferredSize(buttonSize);
        displayOrdersButton.setPreferredSize(buttonSize);
        displayDeliveriesButton.setPreferredSize(buttonSize);
        displayGraphButton.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(registerPatientButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(addOrganButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(placeOrderButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(updateDeliveryStatusButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(displayPatientsButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(displayOrgansButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(displayOrdersButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(displayDeliveriesButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(displayGraphButton, gbc);

        return panel;
    }

    private static void registerPatient() {
        JTextField nameField = new JTextField(5);
        JTextField ageField = new JTextField(5);
        JTextField deliveryPreferenceField = new JTextField(5);
        JTextField bloodTypeField = new JTextField(5);
        JTextField urgencyLevelField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Name:"));
        myPanel.add(nameField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Age:"));
        myPanel.add(ageField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Delivery Preference:"));
        myPanel.add(deliveryPreferenceField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Blood Type:"));
        myPanel.add(bloodTypeField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Urgency Level:"));
        myPanel.add(urgencyLevelField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Register Patient", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String deliveryPreference = deliveryPreferenceField.getText();
                String bloodType = bloodTypeField.getText();
                int urgencyLevel = Integer.parseInt(urgencyLevelField.getText());
                int patientId = transplantExpress.registerPatient(name, age, deliveryPreference, bloodType, urgencyLevel);
                JOptionPane.showMessageDialog(null, "Patient registered successfully! Patient ID: " + patientId);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void addOrgan() {
        JTextField typeField = new JTextField(5);
        JTextField locationField = new JTextField(5);
        JTextField bloodTypeField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Type:"));
        myPanel.add(typeField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Location:"));
        myPanel.add(locationField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Blood Type:"));
        myPanel.add(bloodTypeField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Add Organ", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String type = typeField.getText();
                String location = locationField.getText();
                String bloodType = bloodTypeField.getText();
                transplantExpress.addOrgan(type, location, bloodType);
                JOptionPane.showMessageDialog(null, "Organ added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void placeOrder() {
        JTextField patientIdField = new JTextField(5);
        JTextField organTypeField = new JTextField(5);
        JTextField deliveryLocationField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Patient ID:"));
        myPanel.add(patientIdField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Organ Type:"));
        myPanel.add(organTypeField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Delivery Location:"));
        myPanel.add(deliveryLocationField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Place Order", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int patientId = Integer.parseInt(patientIdField.getText());
                String organType = organTypeField.getText();
                String deliveryLocation = deliveryLocationField.getText();
                transplantExpress.placeOrder(patientId, organType, deliveryLocation);
                JOptionPane.showMessageDialog(null, "Order placed successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void updateDeliveryStatus() {
        JTextField orderIdField = new JTextField(5);
        JTextField currentLocationField = new JTextField(5);
        JTextField estimatedTimeField = new JTextField(5);
        JTextField statusField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Order ID:"));
        myPanel.add(orderIdField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Current Location:"));
        myPanel.add(currentLocationField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Estimated Time:"));
        myPanel.add(estimatedTimeField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Status:"));
        myPanel.add(statusField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Update Delivery Status", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());
                String currentLocation = currentLocationField.getText();
                int estimatedTime = Integer.parseInt(estimatedTimeField.getText());
                String status = statusField.getText();
                transplantExpress.updateDeliveryStatus(orderId, currentLocation, estimatedTime, status);
                JOptionPane.showMessageDialog(null, "Delivery status updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void displayPatients() {
        String[] columnNames = {"ID", "Name", "Age", "Delivery Preference", "Blood Type", "Urgency Level"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Patient patient : transplantExpress.getPatients()) {
            model.addRow(new Object[]{
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getDeliveryPreference(),
                    patient.getBloodType(),
                    patient.getUrgencyLevel()
            });
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Patients", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void displayOrgans() {
        String[] columnNames = {"Type", "Location", "Delivery Time", "Blood Type", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Organ organ : transplantExpress.getOrgans()) {
            model.addRow(new Object[]{
                    organ.getType(),
                    organ.getLocation(),
                    organ.getDeliveryTime(),
                    organ.getBloodType(),
                    organ.getQuantity()
            });
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Organs", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void displayOrders() {
        StringBuilder sb = new StringBuilder();
        for (Order order : transplantExpress.getOrders()) {
            sb.append(order.toString()).append("\n");
        }
        if (sb.length() == 0) {
            JOptionPane.showMessageDialog(null, "No orders found.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, sb.toString(), "Orders", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void displayDeliveries() {
        StringBuilder sb = new StringBuilder();
        for (Delivery delivery : transplantExpress.getDeliveries()) {
            sb.append(delivery.toString()).append("\n");
        }
        if (sb.length() == 0) {
            JOptionPane.showMessageDialog(null, "No deliveries found.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, sb.toString(), "Deliveries", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void displayGraph() {
        String graphPath = "ASSETS/graph.jpg"; // Replace with the actual path to the graph image

        // Create a new JFrame to display the image
        JFrame imageFrame = new JFrame("Graph Display");
        imageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load the image
        ImageIcon imageIcon = new ImageIcon(graphPath);
        Image image = imageIcon.getImage(); // transform it

        // Ensure the frame is visible to get the correct width and height
        imageFrame.setVisible(true);

        // Wait for the frame to be fully visible before getting dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        // Scale the image
        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back

        // Add the image to a JLabel
        JLabel imageLabel = new JLabel(imageIcon);
        imageFrame.add(imageLabel);

        // Make the frame visible
        imageFrame.setVisible(true);
    }

    private static void initializeData() {
        // Initialize graph edges based on the given image
       
        transplantExpress.dsp.addEdge(0, 1, 2); // Main to Manipal
        transplantExpress.dsp.addEdge(0, 2, 3); // Main to St.Johnson
        transplantExpress.dsp.addEdge(0, 3, 5); // Main to Kims
        transplantExpress.dsp.addEdge(0, 5, 4); // Main to Rainbow
        transplantExpress.dsp.addEdge(1, 0, 2); // Manipal to Main
        transplantExpress.dsp.addEdge(1, 5, 1); // Manipal to Rainbow
        transplantExpress.dsp.addEdge(2, 0, 3); // St.Johnson to Main
        transplantExpress.dsp.addEdge(2, 3, 1); // St.Johnson to Kims
        transplantExpress.dsp.addEdge(3, 0, 5); // Kims to Main
        transplantExpress.dsp.addEdge(3, 2, 1); // Kims to St.Johnson
        transplantExpress.dsp.addEdge(3, 4, 2); // Kims to Apollo
        transplantExpress.dsp.addEdge(3, 5, 2); // Kims to Rainbow
        transplantExpress.dsp.addEdge(4, 3, 2); // Apollo to Kims
        transplantExpress.dsp.addEdge(4, 5, 2); // Apollo to Rainbow
        transplantExpress.dsp.addEdge(5, 0, 4); // Rainbow to Main
        transplantExpress.dsp.addEdge(5, 1, 1); // Rainbow to Manipal
        transplantExpress.dsp.addEdge(5, 3, 2); // Rainbow to Kims
        transplantExpress.dsp.addEdge(5, 4, 2); // Rainbow to Apollo

        // Register patients and add organs as before
        transplantExpress.registerPatient("Alice", 30, "standard", "A+", 5);
        transplantExpress.registerPatient("Bob", 45, "urgent", "B-", 8);
        transplantExpress.registerPatient("Charlie", 25, "critical", "O+", 10);
        transplantExpress.registerPatient("Diana", 50, "standard", "AB-", 4);
        transplantExpress.registerPatient("Edward", 35, "urgent", "A-", 7);
        transplantExpress.registerPatient("Fiona", 28, "critical", "B+", 9);
        transplantExpress.registerPatient("George", 40, "standard", "O-", 6);
        transplantExpress.registerPatient("Hannah", 22, "urgent", "AB+", 7);
        transplantExpress.registerPatient("Irene", 55, "critical", "A+", 10);
        transplantExpress.registerPatient("Jack", 60, "standard", "B-", 3);

        transplantExpress.addOrgan("heart", "Main", "A+");
        transplantExpress.addOrgan("liver", "Manipal", "B-");
        transplantExpress.addOrgan("kidneys", "St.Johnson", "O+");
        transplantExpress.addOrgan("lungs", "Kims", "AB-");
        transplantExpress.addOrgan("pancreas", "Rainbow", "A-");
        transplantExpress.addOrgan("brain", "Apollo", "B+");
        transplantExpress.addOrgan("stomach", "Manipal", "O-");
        transplantExpress.addOrgan("eyes", "Rainbow", "AB+");
        transplantExpress.addOrgan("spleen", "Kims", "A+");
        transplantExpress.addOrgan("skin", "St.Johnson", "B-");
    }

}
