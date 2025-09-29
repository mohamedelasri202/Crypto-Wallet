package Metie;
import Utilitaire.ConnectionDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

public class MempoolService {
     private  final Connection connection;

    private final List<Transaction> pendingtransactions;

    public MempoolService() {
        pendingtransactions = new ArrayList<>();
        this.connection = ConnectionDatabase.getInstance().getConnection();
    }

    public void addTransaction(Transaction tx) {
        pendingtransactions.add(tx);
        sortByFees();
    }

    private void sortByFees() {
        pendingtransactions.sort((a, b) -> {
            int feeCompare = Double.compare(b.getFees(), a.getFees());
            if (feeCompare != 0) return feeCompare;
            return a.getCreation_date().compareTo(b.getCreation_date());
        });
    }

    public int getPositionInMempool(String transactionId) {
        for (int i = 0; i < pendingtransactions.size(); i++) {
            if (pendingtransactions.get(i).getTransaction_id().equals(transactionId)) {
                return i + 1;
            }
        }
        return -1;
    }


    public List<Transaction> getPendingTransactions() {
        return pendingtransactions;
    }


        public void processBlock(int blockSize ) {
             blockSize = 10;
            int count = Math.min(blockSize, pendingtransactions.size());
            for (int i = 0; i < count; i++) {
                Transaction tx = pendingtransactions.get(i);
                tx.setStatus("CONFIRMED");
                String updateSQL = "UPDATE transactions SET status = 'CONFIRMED' WHERE transaction_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
                    stmt.setString(1, tx.getTransaction_id());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            pendingtransactions.removeIf(tx -> tx.getStatus().equals("CONFIRMED"));
        }

        public int estimatedtime(String transactionID){
            int position = getPositionInMempool(transactionID);
            if(position == -1){
                return 0;
            }
            int timeEsstimated = position*10;
            return  timeEsstimated;

        }
    public void removeTransaction(Transaction tx) {
        pendingtransactions.remove(tx);
    }

    public List<Transaction> getPendingtransactions() {
        return pendingtransactions;
    }


    public List<Map<String, Object>> pendingTransactions() {
        List<Map<String, Object>> transactions = new ArrayList<>();

        String sql = "SELECT transaction_id, fees " +
                "FROM transactions " +
                "WHERE status = 'PENDING' " +
                "ORDER BY fees DESC, creation_date ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("transaction_id", rs.getString("transaction_id"));
                row.put("fees", rs.getDouble("fees"));

                transactions.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}

