import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Bus {
    private String route;
    private int totalSeats;
    private int availableSeats;

    public Bus(String route, int totalSeats) {
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public String getRoute() {
        return route;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean bookSeats(int numSeats) {
        if (numSeats <= availableSeats) {
            availableSeats -= numSeats;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return route + " (" + availableSeats + "/" + totalSeats + " seats available)";
    }
}

class BusReservationSystemApp extends JFrame {
    private Map<String, Bus> buses;
    private JComboBox<String> busComboBox;
    private JTextField numSeatsField;
    private JTextArea bookingStatusArea;

    public BusReservationSystemApp() {
        buses = new HashMap<>();
        addBus(new Bus("BANGALORE", 30));
        addBus(new Bus("GOA", 40));
        addBus(new Bus("MYSORE", 20));

        busComboBox = new JComboBox<>(buses.keySet().toArray(new String[0]));
        numSeatsField = new JTextField(8);
        bookingStatusArea = new JTextArea(10, 50);
        bookingStatusArea.setEditable(false);

        JButton bookButton = new JButton("Book Seats");
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookSeats();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Bus Route:"));
        inputPanel.add(busComboBox);
        inputPanel.add(new JLabel("Number of Seats:"));
        inputPanel.add(numSeatsField);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(bookButton, BorderLayout.CENTER);

        JPanel bookingStatusPanel = new JPanel(new BorderLayout());
        bookingStatusPanel.add(new JLabel("Booking Status:"), BorderLayout.NORTH);
        bookingStatusPanel.add(new JScrollPane(bookingStatusArea), BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(bookingStatusPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        setTitle("Bus Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addBus(Bus bus) {
        buses.put(bus.getRoute(), bus);
    }

    private void bookSeats() {
        String selectedBusRoute = (String) busComboBox.getSelectedItem();
        Bus selectedBus = buses.get(selectedBusRoute);

        if (selectedBus != null) {
            try {
                int numSeats = Integer.parseInt(numSeatsField.getText());
                if (numSeats > 0) {
                    boolean booked = selectedBus.bookSeats(numSeats);
                    if (booked) {
                        updateBookingStatus();
                        JOptionPane.showMessageDialog(this, "Seats booked successfully!", "Booking Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Sorry, not enough seats available.", "Booking Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number of seats.", "Booking Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number of seats.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateBookingStatus() {
        StringBuilder status = new StringBuilder("Booking Status:\n");
        for (Bus bus : buses.values()) {
            status.append(bus.toString()).append("\n");
        }
        bookingStatusArea.setText(status.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BusReservationSystemApp();
            }
        });
    }
}

public class BusReservationApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BusReservationSystemApp();
            }
        });
    }
}
