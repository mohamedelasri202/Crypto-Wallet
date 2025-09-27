package Metie;
import java.time.LocalDateTime;

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







}

