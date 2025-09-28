package Metie;
import java.util.*;

public class MempoolService {

    private List<Transaction>pendingtransactions;

    public MempoolService() {
        pendingtransactions = new ArrayList<>();
    }

    public  void addtransactions(Transaction tx){
        pendingtransactions.add(tx);
        sortByFees();

    }
    private void sortByFees(){
        pendingtransactions.sort((a,b)->Double.compare(a.getFees(),b.getFees()));
    }

}
