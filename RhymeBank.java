import java.util.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class RhymeBank {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Customer> customers = new ArrayList<>();
          try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(RhymeBankUI::new);

        while (true) {
            System.out.println("\nBank Admin Menu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Change Customer Name");
            System.out.println("3. Check Balance");
            System.out.println("4. Modify Balance");
            System.out.println("5. Summary of All Accounts");
            System.out.println("-1. Quit");
            System.out.print("Please enter a menu option: ");

            int choice;
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine(); // clear invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    addCustomer(input, customers);
                    break;
                case 2:
                    changeCustomerName(input, customers);
                    break;
                case 3:
                    checkBalance(input, customers);
                    break;
                case 4:
                    modifyBalance(input, customers);
                    break;
                case 5:
                    summary(customers);
                    break;
                case -1:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addCustomer(Scanner input, List<Customer> customers) {
        System.out.print("Enter account balance: ");
        double balance = input.nextDouble();
        input.nextLine(); // consume newline

        System.out.print("Enter customer name: ");
        String name = input.nextLine();

        customers.add(new Customer(name, balance));
        System.out.println("Customer ID is: " + (customers.size() - 1));
    }

    private static void changeCustomerName(Scanner input, List<Customer> customers) {
        System.out.print("Enter customer ID: ");
        int id = input.nextInt();
        input.nextLine();

        if(id < 0 || id >= customers.size()) {
            System.out.println("Invalid Customer ID!");
            return;
        }

        System.out.print("Enter new name: ");
        String name = input.nextLine();
        customers.get(id).setName(name);
        System.out.println("Name updated successfully.");
    }

    private static void checkBalance(Scanner input, List<Customer> customers) {
        System.out.print("Enter customer ID: ");
        int id = input.nextInt();

        if(id < 0 || id >= customers.size()) {
            System.out.println("Invalid Customer ID!");
            return;
        }

        Customer c = customers.get(id);
        System.out.printf("%s has $%.2f in their account%n", c.getName(), c.getBalance());
    }

    private static void modifyBalance(Scanner input, List<Customer> customers) {
        System.out.print("Enter customer ID: ");
        int id = input.nextInt();

        if(id < 0 || id >= customers.size()) {
            System.out.println("Invalid Customer ID!");
            return;
        }

        System.out.print("Enter new balance: ");
        double balance = input.nextDouble();
        customers.get(id).setBalance(balance);
        System.out.println("Balance updated successfully.");
    }

    private static void summary(List<Customer> customers) {
        double total = 0;
        System.out.println("\n--- All Customer Accounts ---");
        for(int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.printf("ID %d: %s has $%.2f%n", i, c.getName(), c.getBalance());
            total += c.getBalance();
        }
        System.out.printf("Total balance in bank: $%.2f%n", total);
        
    }
    
    
}
