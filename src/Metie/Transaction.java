package Metie;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Transaction {
    private  String   transaction_id;
    private  String  sender_address;
    private String  reseiver_address;
    private double amount;
    private double fees;
    private String status ;
    private LocalDateTime creation_date;

    public Transaction(String transaction_id, String sender_address, String reseiver_address, double amount, double fees, String status) {
        this.transaction_id = transaction_id;
        this.sender_address = sender_address;
        this.reseiver_address = reseiver_address;
        this.amount = amount;
        this.fees = fees;
        this.status = status;
        this.creation_date = LocalDateTime.now();

    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getSender_address() {
        return sender_address;
    }

    public String getReseiver_address() {
        return reseiver_address;
    }

    public double getAmount() {
        return amount;
    }

    public double getFees() {
        return fees;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setSender_address(String sender_address) {
        this.sender_address = sender_address;
    }

    public void setReseiver_address(String reseiver_address) {
        this.reseiver_address = reseiver_address;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }
    public void confirmTransactions() {
        Timer timer = new Timer(true); // daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int blockSize = 10;
                if (!MempoolService.getPendingTransactions().isEmpty()) {
                    mempoolService.processBlock(blockSize);
                    System.out.println("Automatic block mined: top " + blockSize + " transactions confirmed.");
                }
            }
        }, 0, 60000); // run immediately, then every 60 seconds
    }

}

