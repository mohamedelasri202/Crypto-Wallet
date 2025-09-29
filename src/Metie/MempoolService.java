package Metie;
import java.util.*;

public class MempoolService {

    final private List<Transaction>pendingtransactions;

    public MempoolService() {

        pendingtransactions = new ArrayList<>();
    }

    public  void addtransactions(Transaction tx){
        pendingtransactions.add(tx);
        sortByFees();

    }
    private void sortByFees(){

        pendingtransactions.sort((b,a)-> {
            int feecompare = Double.compare(b.getFees(), a.getFees());
            if (feecompare != 0) {
                return feecompare;
            }else {
                return b.getCreation_date().compareTo(a.getCreation_date());
            }
        });

    }
    public int getpostioninmempool(String transactinId){
        for(int i =0;i <pendingtransactions.size();i++){
           if(pendingtransactions.get(i).getTransaction_id().equals(transactinId));
           return i+1;

        }
        return -1;

    }

}
