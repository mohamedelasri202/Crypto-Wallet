package Metie;
import Utilitaire.Priority;

public class EthereumFees implements Fees{


 @Override
 public double calulatefees(double amount,String type) {
     double gasused ;
     double Gasprice;
     gasused = 21000 +(amount * 10);

     if(type.equals(Priority.ECONOMIQUE.displayName())){
         Gasprice  = 10;
     }else if(type.equals(Priority.STANDARD.displayName()) ) {
         Gasprice = 30;
     }else {
         Gasprice =100;
     }
     double feeETH = gasused * Gasprice * Math.pow(10, -9);

     return  feeETH;

 }

}