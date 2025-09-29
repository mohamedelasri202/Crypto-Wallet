package Utilitaire;

import java.util.Timer;

import java.util.TimerTask;
import Metie.MempoolService;

public class Autoconfirmation {
    private final MempoolService mempoolService;

    public Autoconfirmation(MempoolService mempoolService) {
        this.mempoolService = mempoolService;
    }

    public void autoconfirmation() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int blocksize = 10;
                if (!mempoolService.getPendingTransactions().isEmpty()) {
                    mempoolService.processBlock(blocksize);
                    System.out.println("Automatic block mined: top " + blocksize + " transactions confirmed.");
                }
            }
        }, 0, 60000); // every 60 seconds
    }
}
