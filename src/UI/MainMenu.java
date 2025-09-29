package UI;

import Metie.*;
import Utilitaire.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {
    private static MempoolService mempoolService = new MempoolService();
    private static WalletService service = new WalletService(mempoolService);

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choix = scanner.nextLine();
            try {
                switch (choix) {
                    case "1": createEthereumWallet(); break;
                    case "2": createBitcoinWallet(); break;
                    case "3": makeTransaction(); break;
                    case "4": positionInMempoo(); break;
                    case "5": comparaisonFees(); break;
                    case "6": consulteEtatMempool(); break;
                    case "7": running = false; break;
                    default: System.out.println("Invalid choice"); break;
                }
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    public static void printMenu() {
        System.out.println("=========Welcome to Bitcoin Wallet===============");
        System.out.println("Please choose a number from the Menu:");
        System.out.println("1 - Create an Ethereum Wallet");
        System.out.println("2 - Create a Bitcoin Wallet");
        System.out.println("3 - Créer une nouvelle transaction");
        System.out.println("4 - Voir ma position dans le mempool");
        System.out.println("5 - Comparer les 3 niveaux de frais (fee levels)");
        System.out.println("6 - Consulter l'état actuel du mempool");
        System.out.println("7 - Au revoir");
    }

    public static void createEthereumWallet() {
        String type = "ethereum";

        Wallet bw = service.createWallet(type);

        if (bw != null) {
            System.out.println("Ethereum Wallet Created!");

            System.out.println("Balance: " + bw.getbalance());
        } else {
            System.out.println("Error creating wallet.");
        }
    }

    public static void createBitcoinWallet() {
        String type = "bitcoin";
        Wallet bw = service.createWallet(type);

        if (bw != null) {
            System.out.println("Bitcoin Wallet Created!");

            System.out.println("Balance: " + bw.getbalance());
        } else {
            System.out.println("Error creating wallet.");
        }
    }

    public static void makeTransaction() {
        System.out.println("Please enter your address:");
        String senaddress = scanner.nextLine();

        if (!InputValidator.isValidAddress(senaddress)) {
            System.out.println("Invalid address");
            return;
        }

        System.out.println("Please enter the address of the receiver:");
        String resaddress = scanner.nextLine();
        if (!InputValidator.isValidAddress(resaddress)) {
            System.out.println("Invalid address");
            return;
        }
        System.out.println("Please enter the amount you want to send:");
        double amount = readDouble();

        System.out.println("Please choose the type of priority you prefer:");
        System.out.println("1 - ECONOMICAL (slow)");
        System.out.println("2 - STANDARD (medium)");
        System.out.println("3 - FAST (quick)");

        String type = "STANDARD";
        String choix = scanner.nextLine();

        switch (choix) {
            case "1": type = "ECONOMICAL"; break;
            case "2": type = "STANDARD"; break;
            case "3": type = "FAST"; break;
            default: System.out.println("Invalid choice");
        }

        System.out.println(service.doTransaction(resaddress, senaddress, amount, type));
        System.out.println("Transaction sent successfully!");
    }

    public static void positionInMempoo() {
        System.out.println("Please enter your transaction ID:");
        String transactionID = scanner.nextLine();

        int position = mempoolService.getPositionInMempool(transactionID);
        if (position == -1) {
            System.out.println("Transaction not found in mempool.");
        } else {
            System.out.println("Your position in the mempool is: " + position);
        }
        System.out.println("It's estimated to get confirmed in "
                + mempoolService.estimatedtime(transactionID)
                + " minutes.");


    }

    public static void comparaisonFees() {}

    public static void consulteEtatMempool() {
        List<Map<String, Object>> pending = mempoolService.pendingTransactions();

        // Print table header
        System.out.printf("%-40s | %-15s%n", "Address", "Fees");
        System.out.println("----------------------------------------------------");

        for (Map<String, Object> row : pending) {
            String address = (String) row.get("transaction_id");
            double fees = (Double) row.get("fees");

            System.out.printf("%-40s | %-15.8f%n", address, fees);
        }
    }













    private static double readDouble() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide. Entrez un nombre: ");
            }
        }
    }
}
