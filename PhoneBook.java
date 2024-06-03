
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import javax.swing.*;

class Contact {
    String name;
    String phoneNumber;
    List<String> callLog;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.callLog = new LinkedList<>();
    }

    public void addToCallLog(String callDetails) {
        callLog.add(0, callDetails);
    }

    public List<String> getCallLog() {
        return callLog;
    }

    @Override
    public String toString() {
        return "<html><b>Name:</b> " + name + "<br><b>Phone:</b> " + phoneNumber + "</html>";
    }
}

public class PhoneBook extends JFrame {
    private TreeSet<Contact> contacts;

    public PhoneBook() {
        contacts = new TreeSet<>((c1, c2) -> c1.name.compareToIgnoreCase(c2.name));
    }

    private Contact selectContact(String action) {
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No contacts available.", action, JOptionPane.PLAIN_MESSAGE);
            return null;
        }

        JComboBox<Contact> contactComboBox = new JComboBox<>(contacts.toArray(new Contact[0]));

        Object[] message = {
                "Select a contact:", contactComboBox
        };

        int option = JOptionPane.showConfirmDialog(null, message, action, JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            return (Contact) contactComboBox.getSelectedItem();
        }

        return null;
    }

    public void displayContacts() {
        StringBuilder contactInfo = new StringBuilder("<html><h2>Contacts</h2>");
        
        char currentLetter = 0;

        for (Contact contact : contacts) {
            char firstLetter = contact.name.toUpperCase().charAt(0);
            
            if (firstLetter != currentLetter) {
                if (currentLetter != 0) {
                    contactInfo.append("<br>"); 
                }
                contactInfo.append("<h3>").append(firstLetter).append("</h3>");
                currentLetter = firstLetter;
            }

            contactInfo.append(contact).append("<br>");
        }
        contactInfo.append("</html>");
        showMessageDialog("Contacts", contactInfo.toString());
    }

    public void addContact(String name, String phoneNumber) {
        Contact newContact = new Contact(name, phoneNumber);
        contacts.add(newContact);
        showMessageDialog("Contact added", "Contact added successfully!");
    }

    public void deleteContact() {
        Contact selectedContact = selectContact("Delete Contact");

        if (selectedContact != null) {
            contacts.remove(selectedContact);
            showMessageDialog("Contact deleted", "Contact deleted successfully!");
        }
    }

    public void makeCall() {
        Contact selectedContact = selectContact("Make a Call");

        if (selectedContact != null) {
            String callDuration = JOptionPane.showInputDialog("Call duration (e.g., 5 minutes):");
            String callDetails = "Call to " + selectedContact.name + ", Duration: " + callDuration;
            selectedContact.addToCallLog(callDetails);
            showMessageDialog("Call recorded", "Call recorded in the log.");
        }
    }

    public void displayCallLog() {
        Contact selectedContact = selectContact("Display Call Log");

        if (selectedContact != null) {
            List<String> callLog = selectedContact.getCallLog();
            if (callLog.isEmpty()) {
                showMessageDialog("Call log", "Call log is empty for " + selectedContact.name);
            } else {
                StringBuilder callLogInfo = new StringBuilder("<html><h2>Call log for " + selectedContact.name + "</h2>");
                for (String callDetails : callLog) {
                    callLogInfo.append(callDetails).append("<br>");
                }
                callLogInfo.append("</html>");
                showMessageDialog("Call log", callLogInfo.toString());
            }
        }
    }

    public void displayAllCallLogs() {
        StringBuilder allCalls = new StringBuilder("<html><h2>All call logs in recent order</h2>");
        Stack<String> recentCalls = new Stack<>();

        for (Contact contact : contacts) {
            List<String> callLog = contact.getCallLog();
            for (String callDetails : callLog) {
                recentCalls.push(callDetails);
            }
        }

        while (!recentCalls.isEmpty()) {
            allCalls.append(recentCalls.pop()).append("<br>");
        }

        allCalls.append("</html>");
        showMessageDialog("All Call Logs", allCalls.toString());
    }

    public void searchContacts() {
        String searchQuery = JOptionPane.showInputDialog("Enter name or phone number to search:");
        StringBuilder searchResult = new StringBuilder("<html><h2>Search Results</h2>");

        for (Contact contact : contacts) {
            if (contact.name.toLowerCase().contains(searchQuery.toLowerCase()) ||
                    contact.phoneNumber.contains(searchQuery)) {
                searchResult.append(contact).append("<br>");
            }
        }

        if (searchResult.length() == 0) {
            showMessageDialog("Search Results", "No contacts found.");
        } else {
            searchResult.append("</html>");
            showMessageDialog("Search Results", searchResult.toString());
        }
    }

    public void updateContact() {
        Contact selectedContact = selectContact("Update Contact");

        if (selectedContact != null) {
            String newName = JOptionPane.showInputDialog("Enter new name (leave blank to keep the existing name):");
            String newPhoneNumber = JOptionPane.showInputDialog("Enter new phone number (leave blank to keep the existing number):");

            if (newName != null && !newName.trim().isEmpty()) {
                selectedContact.name = newName;
            }

            if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty()) {
                selectedContact.phoneNumber = newPhoneNumber;
            }

            showMessageDialog("Contact updated", "Contact updated successfully!");
        }
    }

    private void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhoneBook phoneBook = new PhoneBook();
            phoneBook.setTitle("PhoneBook");
            phoneBook.setSize(600, 500);
            phoneBook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton displayContactsButton = new JButton("Display Contacts");
            JButton addContactButton = new JButton("Add New Contact");
            JButton deleteContactButton = new JButton("Delete Contact");
            JButton makeCallButton = new JButton("Make a Call");
            JButton displayCallLogButton = new JButton("Display Call History");
            JButton displayAllCallLogsButton = new JButton("Display All Call Logs");
            JButton searchContactsButton = new JButton("Search Contacts");
            JButton updateContactButton = new JButton("Update Contact");
            JButton exitButton = new JButton("Exit");

            displayContactsButton.addActionListener(e -> phoneBook.displayContacts());
            addContactButton.addActionListener(e -> {
                String name = JOptionPane.showInputDialog("Enter name:");
                String phoneNumber = JOptionPane.showInputDialog("Enter phone number:");
                phoneBook.addContact(name, phoneNumber);
            });
            deleteContactButton.addActionListener(e -> phoneBook.deleteContact());
            makeCallButton.addActionListener(e -> phoneBook.makeCall());
            displayCallLogButton.addActionListener(e -> phoneBook.displayCallLog());
            displayAllCallLogsButton.addActionListener(e -> phoneBook.displayAllCallLogs());
            searchContactsButton.addActionListener(e -> phoneBook.searchContacts());
            updateContactButton.addActionListener(e -> phoneBook.updateContact());
            exitButton.addActionListener(e -> System.exit(0));

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(9, 1, 10, 10)); 
            panel.add(displayContactsButton);
            panel.add(addContactButton);
            panel.add(deleteContactButton);
            panel.add(makeCallButton);
            panel.add(displayCallLogButton);
            panel.add(displayAllCallLogsButton);
            panel.add(searchContactsButton);
            panel.add(updateContactButton);
            panel.add(exitButton);
            phoneBook.add(panel);
            phoneBook.setVisible(true);
        });
    }
}
