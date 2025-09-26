package Metie;

import Utilitaire.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WalletService {
    Connection connection;

    public WalletService() {
        this.connection = ConnectionDatabase.getInstance().getConnection();
    }

    public Wallet createWallet(String type) {
        try {
            if (type.equals("Bitcoin")) {

                String address = AddressGenerator.bitcoinAdress();

                BitcoinWallet bw = new BitcoinWallet(address, 0.0);

                String sql = "INSERT INTO wallet (type, address, amount) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, bw.getType());
                stmt.setDouble(2, bw.getbalance());

                stmt.executeUpdate();


                return bw;
            }else{
                String address = AddressGenerator.ethereumAddress();
                EthereumWallet ew  = new EthereumWallet(address, 0.0);
                String sql = "INSERT INTO wallet (type, address, amount) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, ew.getType());
                stmt.setDouble(2, ew.getbalance());

                stmt.executeUpdate();


                return ew;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String doTransaction(String resAddress, String senAddress, double amount, String type) {
        // 1. Find wallets in memory (optional, for checks)
        Wallet sender = findByAddress(senAddress);
        Wallet receiver = findByAddress(resAddress);

        if (sender == null || receiver == null) {
            return "error: wallet not found";
        }

        if()

        // 2. Check balance
        EthereumFees ethFees = new EthereumFees();
        double fee = ethFees.calulatefees(amount, type); // fees
        double totalDebit = amount + fee;

        if (sender.getbalance() < totalDebit) {
            return "error: insufficient funds (amount + fee)";
        }

        try {

            connection.setAutoCommit(false);


            String insertTransactionSQL = "INSERT INTO transactions (transaction_id, sender_address, receiver_address, amount, fees, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertTransactionSQL)) {
                stmt.setString(1, UUID.randomUUID().toString());
                stmt.setString(2, senAddress);
                stmt.setString(3, resAddress);
                stmt.setDouble(4, amount);
                stmt.setDouble(5, fee);
                stmt.setString(6, "pending");
                stmt.executeUpdate();
            }


            String updateSenderSQL = "UPDATE wallet SET amount = amount - ? WHERE address = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateSenderSQL)) {
                stmt.setDouble(1, totalDebit);
                stmt.setString(2, senAddress);
                stmt.executeUpdate();
            }


            String updateReceiverSQL = "UPDATE wallet SET amount = amount + ? WHERE address = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateReceiverSQL)) {
                stmt.setDouble(1, amount);
                stmt.setString(2, resAddress);
                stmt.executeUpdate();
            }

            // 7. Commit DB transaction
            connection.commit();
            connection.setAutoCommit(true);

            return "transaction created";

        } catch (SQLException e) {
            // Rollback if anything goes wrong
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return "error: transaction failed";
        }
    }



//    methode for the cheecking using the address in the database

    public Wallet findByAddress(String address) {
        Wallet wallet = null;
        String sql = "SELECT address, type, amount FROM wallet WHERE address = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String walletAddress = rs.getString("address");
                    String walletType = rs.getString("type");
                    double balance = rs.getDouble("amount");

                    if ("Ethereum".equalsIgnoreCase(walletType)) {
                        wallet = new EthereumWallet(walletAddress, balance);
                    } else if ("Bitcoin".equalsIgnoreCase(walletType)) {
                        wallet = new BitcoinWallet(walletAddress, balance);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wallet;
    }





}
