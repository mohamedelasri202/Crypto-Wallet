package Utilitaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;

import java.util.TimerTask;
import java.util.logging.Logger;

import Metie.MempoolService;
import Metie.Transaction;


public class Autoconfirmation {
    private final MempoolService mempoolService;
/* loggs; */
    private final Logger logger = Logger.getLogger(Autoconfirmation.class.getName());

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


                String updateSQL = "UPDATE transactions SET status = 'CONFIRMED' WHERE transaction_id = ?";
                try (PreparedStatement stmt = mempoolService.getConnection().prepareStatement(updateSQL)) {
                    stmt.setString(1, tx.getTransaction_id());

                    int rows = stmt.executeUpdate();
                    logger.info("Updated transaction " + tx.getTransaction_id() + ". Rows affected: " + rows);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            logger.info("transaction "+ tx.getTransaction_id()+ " confirmed"+estimatedTimeMinutes +"minutes");

            }

        }, delayMillis);
    }
}


