package Metie;
import java.util.*;

public class MempoolService {

    private final List<Transaction> pendingtransactions;

    public MempoolService() {
        pendingtransactions = new ArrayList<>();
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


        public void processBlock(int blockSize) {
            blockSize = 10;
            int count = Math.min(blockSize, pendingtransactions.size());
            for (int i = 0; i < count; i++) {
                Transaction tx = pendingtransactions.get(i);
                tx.setStatus("CONFIRMED");
                // Optionally update DB
            }
            pendingtransactions.removeIf(tx -> tx.getStatus().equals("CONFIRMED"));
        }

        public int estimatedtime(String transactionID){

        }

    }

