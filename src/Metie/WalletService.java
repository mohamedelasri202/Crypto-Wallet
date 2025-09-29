package Metie;

import Utilitaire.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WalletService {
    Connection connection;
    MempoolService mempoolService;


    public WalletService() {
        this.connection = ConnectionDatabase.getInstance().getConnection();
        this.mempoolService = new MempoolService();
    }

    public Wallet createWallet(String type) {
        Wallet wallet = null;

        try {
            if (type.equals("bitcoin")) {

                String address = AddressGenerator.bitcoinAdress();

                BitcoinWallet bw = new BitcoinWallet(address, 0.0);

                String sql = "INSERT INTO wallet (type, address, amount) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, bw.getType());
                stmt.setString(2, bw.getaddress());
                stmt.setDouble(3, bw.getbalance());

                stmt.executeUpdate();


                wallet = bw;
            }else if (type.equals("ethereum")) {

                String address = AddressGenerator.ethereumAddress();
                EthereumWallet ew  = new EthereumWallet(address, 0.0);
                String sql = "INSERT INTO wallet (type, address, amount) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, ew.getType());
                stmt.setString(2, ew.getaddress());
                stmt.setDouble(3, ew.getbalance());
                stmt.executeUpdate();


                wallet= ew;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return wallet;

    }

    public String doTransaction(String resAddress, String senAddress, double amount, String type) {
        double totalDebit;
        double fee;

        Wallet sender = findByAddress(senAddress);
        Wallet receiver = findByAddress(resAddress);

        if (sender == null || receiver == null) {
            return "error: wallet not found";
        }

        String walletType = sender.getType();
        if (walletType.equals("ethereum")) {
            EthereumFees ethFees = new EthereumFees();
            fee = ethFees.calulatefees(amount, type);
        } else {
            BitcoinFees btcFees = new BitcoinFees();
            fee = btcFees.calulatefees(amount, type);
        }
        totalDebit = amount + fee;

        if (sender.getbalance() < totalDebit) {
            return "error: insufficient funds (amount + fee)";
        }

        String transactionId = UUID.randomUUID().toString();

        try {
            // Disable auto-commit for manual control
            connection.setAutoCommit(false);

            // 2. Insert transaction into DB
            String insertTransactionSQL = "INSERT INTO transactions (transaction_id, sender_address, receiver_address, amount, fees, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertTransactionSQL)) {
                stmt.setString(1, transactionId);
                stmt.setString(2, senAddress);
                stmt.setString(3, resAddress);
                stmt.setDouble(4, amount);
                stmt.setDouble(5, fee);
                stmt.setString(6, "PENDING");
                stmt.executeUpdate();
            }
            System.out.println(sender.getbalance());

            // 3. Update sender balance
            String updateSenderSQL = "UPDATE wallet SET amount = amount - ? WHERE address = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateSenderSQL)) {
                stmt.setDouble(1, totalDebit);
                stmt.setString(2, senAddress);
                stmt.executeUpdate();
            }

            // 4. Update receiver balance
            String updateReceiverSQL = "UPDATE wallet SET amount = amount + ? WHERE address = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateReceiverSQL)) {
                stmt.setDouble(1, amount);
                stmt.setString(2, resAddress);
                stmt.executeUpdate();

            }
            connection.commit();
            connection.setAutoCommit(true);


            Transaction tx = new Transaction(
                    transactionId,
                    senAddress,
                    resAddress,
                    amount,
                    fee,
                    "PENDING"
            );

            mempoolService.addtransactions(tx);


            return "transaction created with ID: " + transactionId;

        } catch (SQLException e) {
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
