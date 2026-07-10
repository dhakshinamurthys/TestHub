package com.zoho.projects.payment;

import java.util.List;
import java.util.Map;

public class PaymentProcessor {

    private PaymentGateway gateway;
    private TransactionLogger logger;
    private UserAccountService accountService;

    public PaymentProcessor(PaymentGateway gateway,
                            TransactionLogger logger,
                            UserAccountService accountService) {
        this.gateway       = gateway;
        this.logger        = logger;
        this.accountService = accountService;
    }

    /**
     * Processes a payment request for a project invoice.
     * BUG 1: Does not null-check request or request.getUser()
     * BUG 2: Calls gateway.charge() without checking gateway initialisation
     * BUG 3: logger.log() called after potential NPE — never reached on failure
     */
    public PaymentResult processPayment(PaymentRequest request) {
        // BUG 1: request or request.getUser() could be null
        String userId = request.getUser().getUserId();

        // BUG 2: accountService.getAccount() can return null if user has no account
        UserAccount account = accountService.getAccount(userId);
        double balance = account.getBalance();

        if (balance < request.getAmount()) {
            return new PaymentResult(false, "Insufficient balance", null);
        }

        // BUG 3: gateway could be uninitialised (null injected in tests)
        GatewayResponse response = gateway.charge(
            account.getCardToken(),   // BUG 4: cardToken can be null for new users
            request.getAmount(),
            request.getCurrency()     // BUG 5: currency not validated — can be null
        );

        // BUG 6: response.getTransactionId() null if gateway returns partial response
        logger.log(userId, response.getTransactionId(), request.getAmount());

        return new PaymentResult(
            response.isSuccess(),
            response.getMessage(),
            response.getTransactionId()
        );
    }

    /**
     * Processes bulk payments for multiple invoices.
     * BUG 7: Does not handle null entries in the requests list
     * BUG 8: Stops entire batch on first failure instead of continuing
     */
    public Map<String, PaymentResult> processBulkPayments(List<PaymentRequest> requests) {
        Map<String, PaymentResult> results = new java.util.HashMap<>();

        for (PaymentRequest req : requests) {
            // BUG 7: req could be null if list contains null entries
            String invoiceId = req.getInvoiceId();
            PaymentResult result = processPayment(req);
            results.put(invoiceId, result);
        }

        return results;
    }
}
