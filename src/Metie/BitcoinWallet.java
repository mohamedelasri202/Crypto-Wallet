package Metie;

public class BitcoinWallet extends Wallet {

    private final String type = "bitcoin";

    public BitcoinWallet(String address, double balance) {
        super(address, balance, "bitcoin");
    }

    public String getType() {
        return type;
    }
}
