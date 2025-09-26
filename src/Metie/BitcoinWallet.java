package Metie;

public class BitcoinWallet extends Wallet {

    private final String type = "Bitcoin";

    public BitcoinWallet(String address, double balance) {
        super(address, balance, "Bitcoin");
    }

    public String getType() {
        return type;
    }
}
