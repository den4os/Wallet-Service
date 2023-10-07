package com.ylab.walletservice.domain.entities;

/**
 * This class represents a financial transaction associated with a player's account.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-07
 */
public class Transaction {
    private String transactionId;
    private String accountId;
    private double amount;
    private TransactionType type;
    private String timestamp;

    /**
     * Creates a new transaction with the given ID, account ID, amount, transaction type, and timestamp.
     *
     * @param transactionId Unique transaction identifier.
     * @param accountId     Account associated with the transaction.
     * @param amount        Transaction amount.
     * @param type          Type of transaction (debit or credit).
     * @param timestamp     Timestamp of the transaction.
     */
    public Transaction(String transactionId, String accountId, double amount, TransactionType type, String timestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}