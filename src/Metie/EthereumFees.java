package Metie;
import Utilitaire.Priority;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EthereumFees implements Fees{
   private final static Logger loger =Logger.getLogger(EthereumFees.class.getName());

 @Override
 public double calulatefees(double amount,String type) {
     double gasused ;
     double Gasprice;
     gasused = 21000 +(amount * 10);

     if (type.equals(Priority.ECONOMICAL.displayName())) {
         Gasprice  = 10;
     } else if (type.equals(Priority.STANDARD.displayName())) {
         Gasprice = 30;
     } else if (type.equals(Priority.FAST.displayName())) {

         Gasprice = 100;
     } else {
         throw new IllegalArgumentException("Invalid priority type: " + type);

     }

     double feeETH = gasused * Gasprice * Math.pow(10, -9);

     return  feeETH;

 }

}