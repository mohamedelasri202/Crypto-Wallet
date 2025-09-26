package Metie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EthereumWallet extends Wallet {
    private final String type = "Ethereum";
    Connection connection;

    public EthereumWallet(String address, double balance) {
        super(address, balance, "Ethereum");
    }

    public String getType(){
        return type;
    }






}
