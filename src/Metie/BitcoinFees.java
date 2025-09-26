package Metie;

public class BitcoinFees implements  Fees{

    @Override

    public double calulatefees(double amount,String type) {
        double gasused ;
        double Gasprice;
         gasused = 21000 +(amount * 10);
        if(type =="ECONOMIQUE"){
            Gasprice  = 10;
        } else if (type == "STANDARD") {
            Gasprice = 30;

        }else {
            Gasprice =100;
        }
        double feeETH = gasused * Gasprice * Math.pow(10, -9);

        return  feeETH;

    }
}

