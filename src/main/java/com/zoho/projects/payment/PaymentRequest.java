package com.zoho.projects.payment;

public class PaymentRequest {

    private String invoiceId;
    private double amount;
    private String currency;
    private User   user;

    public PaymentRequest(String invoiceId, double amount) {
        this.invoiceId = invoiceId;
        this.amount    = amount;
        // currency and user intentionally not set
        // to simulate incomplete request construction
    }

    public String getInvoiceId() { return invoiceId; }
    public double getAmount()    { return amount; }
    public String getCurrency()  { return currency; }  // can return null
    public User   getUser()      { return user; }       // can return null
}
