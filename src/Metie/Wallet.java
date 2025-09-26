package Metie;




public abstract class Wallet {
    protected String address;
    protected double balance;
    protected String type; // store wallet type

    public Wallet(String address, double balance, String type) {
        this.address = address;
        this.balance = balance;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public double getbalance() {
        return balance;
    }
}

