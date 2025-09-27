package Metie;
import Utilitaire.Priority;
public class BitcoinFees implements  Fees{

    @Override

    public double calulatefees(double amount,String type) {
        double transactionSize ;
        double fee_rate;
        if(amount <0.01){
            transactionSize = 250;

        } else if (amount<1) {
            transactionSize = 300;

        }else {
            transactionSize = 400;
        }
        if(type.equals(Priority.ECONOMIQUE.displayName())){
            fee_rate = 5;
        } else if (type.equals(Priority.STANDARD.displayName())) {
            fee_rate = 20;

        }else {
            fee_rate =50;
        }
        double fee_satoshis = transactionSize*fee_rate;
        double bitocin_fee = fee_satoshis/100_000_000;

        return  bitocin_fee;

    }
}

