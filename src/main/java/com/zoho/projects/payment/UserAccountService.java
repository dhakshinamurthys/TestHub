package com.zoho.projects.payment;

import java.util.HashMap;
import java.util.Map;

public class UserAccountService {

    private Map<String, UserAccount> accountStore = new HashMap<>();

    /**
     * Returns the UserAccount for a given userId.
     * Returns null if user has no account — callers must null-check.
     * BUG: Most callers don't null-check the return value.
     */
    public UserAccount getAccount(String userId) {
        return accountStore.get(userId); // returns null if not found
    }

    /**
     * Returns the card token for a user.
     * BUG: Does not check if account exists before calling getCardToken()
     */
    public String getCardToken(String userId) {
        UserAccount account = getAccount(userId);
        return account.getCardToken(); // NPE if account is null
    }

    /**
     * Validates if a user has sufficient balance.
     * BUG: No null check on account — throws NPE for unknown users
     */
    public boolean hasSufficientBalance(String userId, double amount) {
        UserAccount account = getAccount(userId);
        return account.getBalance() >= amount; // NPE if account null
    }
}
