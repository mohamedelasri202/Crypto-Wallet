package Metie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EthereumWallet extends Wallet {
    private final String type = "ethereum";
    Connection connection;

    public EthereumWallet(String address, double balance) {
        super(address, balance, "ethereum");
    }

    public String getType(){
        return type;
    }






}
