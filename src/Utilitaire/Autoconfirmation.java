package Utilitaire;

import java.util.Timer;

import java.util.TimerTask;
import Metie.MempoolService;
import Metie.Transaction;


public class Autoconfirmation {
    private final MempoolService mempoolService;

    public Autoconfirmation(MempoolService mempoolService) {
        this.mempoolService = mempoolService;
    }

    public void autoConfirmTransaction(Transaction tx) {
        int estimatedTimeMinutes = mempoolService.estimatedtime(tx.getTransaction_id());
        long delayMillis = estimatedTimeMinutes * 60 * 1000L;

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tx.setStatus("CONFIRMED");
                mempoolService.removeTransaction(tx);
                System.out.println("Transaction " + tx.getTransaction_id() +
                        " confirmed automatically after " + estimatedTimeMinutes + " minutes.");
            }
        }, delayMillis);
    }
}


