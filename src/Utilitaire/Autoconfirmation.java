package Utilitaire;


import Metie.MempoolService;
import javafx.animation.PauseTransition;

import java.util.Timer;
import java.util.TimerTask;

public class Autoconfirmation {

 MempoolService mempoolService;
 public Autoconfirmation(MempoolService mempoolService) {
     this.mempoolService = mempoolService;

 }

    public void autoconfirmation() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int blocksize = 10;
                // Process the block directly
                mempoolService.processBlock(blocksize);
                System.out.println("Automatic block mined: top " + blocksize + " transactions confirmed.");
            }
        }, 0, 60000); // repeat every 60 seconds
    }

}
