package Metie;

import Utilitaire.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WalletService {
    Connection connection;
    private final MempoolService mempoolService;
    private final static Logger log = Logger.getLogger(WalletService.class.getName());


    public WalletService(MempoolService mempoolService) {
        this.connection = ConnectionDatabase.getInstance().getConnection();
        this.mempoolService = mempoolService;
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


        Priority priority;
        try {
            priority = Priority.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "error: invalid priority type";
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

            connection.setAutoCommit(false);


            String insertTransactionSQL = "INSERT INTO transactions (transaction_id, sender_address, receiver_address, amount, fees, status, priority) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertTransactionSQL)) {
                stmt.setString(1, transactionId);
                stmt.setString(2, senAddress);
                stmt.setString(3, resAddress);
                stmt.setDouble(4, amount);
                stmt.setDouble(5, fee);
                stmt.setString(6, "PENDING");
                stmt.setString(7, priority.name());
                stmt.executeUpdate();
            }

            // 2. Update sender balance
            String updateSenderSQL = "UPDATE wallet SET amount = amount - ? WHERE address = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateSenderSQL)) {
                stmt.setDouble(1, totalDebit);
                stmt.setString(2, senAddress);
                stmt.executeUpdate();
            }

            // 3. Update receiver balance
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
                    "PENDING",
                    priority
            );


            mempoolService.addTransaction(tx);



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


    public List<Map<String, Object>> getFeeComparisonData() {
        List<Map<String, Object>> tableData = new ArrayList<>();


        for (Transaction tx : mempoolService.getPendingTransactions()) {
            Map<String, Object> row = new HashMap<>();

            row.put("priority", tx.getPriority().name());
            row.put("fee", tx.getFees());
            row.put("position", mempoolService.getPositionInMempool(tx.getTransaction_id())); // Position
            row.put("estimatedTime", mempoolService.estimatedtime(tx.getTransaction_id()));   // Est. Time

            tableData.add(row);
        }

        return tableData;
    }




}
