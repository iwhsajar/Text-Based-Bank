import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RhymeBankUI {
    private ArrayList<Customer> customers = new ArrayList<>();
    private DefaultTableModel tableModel;
    

    public RhymeBankUI() {
        JFrame frame = new JFrame("RhymeBank");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        // Table for Customers
        String[] columns = {"Name", "Balance"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable customerTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(customerTable);
        
    // Add border with title
    tableScroll.setBorder(BorderFactory.createTitledBorder("Customers"));

        // Transaction history area
        JTextArea transactionArea = new JTextArea();
        transactionArea.setEditable(false);
        JScrollPane transactionScroll = new JScrollPane(transactionArea);
        transactionScroll.setPreferredSize(new Dimension(250, 0));

        // Add border with title
        transactionScroll.setBorder(BorderFactory.createTitledBorder("Transaction History"));

        // Buttons
        JButton addButton = new JButton("Add Customer");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");

        addButton.addActionListener(e -> addCustomer(transactionArea));
        depositButton.addActionListener(e -> deposit(customerTable.getSelectedRow(), transactionArea));
        withdrawButton.addActionListener(e -> withdraw(customerTable.getSelectedRow(), transactionArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        // Add border with title
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(tableScroll, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(transactionScroll, BorderLayout.EAST);
        frame.add(tableScroll, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);
       frame.add(transactionScroll, BorderLayout.EAST);


        frame.setVisible(true);
    }

    private void addCustomer(JTextArea transactionArea) {
        String name = JOptionPane.showInputDialog("Enter customer name:");
        if(name != null && !name.isEmpty()) {
            Customer c = new Customer(name, 0);
            customers.add(c);
            tableModel.addRow(new Object[]{c.getName(), c.getBalance()});
            transactionArea.append("Customer added: " + c.getName() + "\n");
        }
    }

    private void deposit(int index, JTextArea transactionArea) {
        if(index < 0) {
            JOptionPane.showMessageDialog(null, "Select a customer first!");
            return;
        }
        String amountStr = JOptionPane.showInputDialog("Enter deposit amount:");
        try {
            double amount = Double.parseDouble(amountStr);
            Customer c = customers.get(index);
            c.setBalance(c.getBalance() + amount);
            tableModel.setValueAt(c.getBalance(), index, 1);
            transactionArea.append("Deposited $" + amount + " to " + c.getName() + "\n");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter a valid number!");
        }
    }

    private void withdraw(int index, JTextArea transactionArea) {
        if(index < 0) {
            JOptionPane.showMessageDialog(null, "Select a customer first!");
            return;
        }
        String amountStr = JOptionPane.showInputDialog("Enter withdrawal amount:");
        try {
            double amount = Double.parseDouble(amountStr);
            Customer c = customers.get(index);
            if(amount > c.getBalance()) {
                JOptionPane.showMessageDialog(null, "Insufficient balance!");
            } else {
                c.setBalance(c.getBalance() - amount);
                tableModel.setValueAt(c.getBalance(), index, 1);
                transactionArea.append("Withdrew $" + amount + " from " + c.getName() + "\n");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter a valid number!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RhymeBankUI::new);
    }
}
