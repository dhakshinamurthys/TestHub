package com.zoho.projects.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogger {

    private List<String> logs = new ArrayList<>();

    /**
     * Logs a completed transaction.
     * BUG 1: transactionId can be null — String.format will print "null" silently
     *        but downstream log parsers crash on "null" transaction IDs
     * BUG 2: No timestamp validation — LocalDateTime.now() timezone not normalised
     * BUG 3: userId null check missing — log entry becomes unparseable
     */
    public void log(String userId, String transactionId, double amount) {
        String entry = String.format(
            "[%s] User: %s | TxID: %s | Amount: %.2f",
            LocalDateTime.now(),
            userId,          // null if caller passed null userId
            transactionId,   // null if gateway returned partial response
            amount
        );
        logs.add(entry);
        System.out.println(entry);
    }

    /**
     * Returns all logs for audit.
     * BUG: Returns internal list reference — callers can mutate it externally
     */
    public List<String> getLogs() {
        return logs; // should return Collections.unmodifiableList(logs)
    }

    /**
     * Searches logs for a specific transaction.
     * BUG: Throws NullPointerException if transactionId is null
     */
    public String findLog(String transactionId) {
        for (String log : logs) {
            if (log.contains(transactionId)) { // NPE if transactionId is null
                return log;
            }
        }
        return null;
    }
}
