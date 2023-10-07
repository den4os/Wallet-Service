package com.ylab.walletservice.domain.entities;

/**
 * Enum representing the types of financial transactions (debit or credit).
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-07
 */
public enum TransactionType {
    DEBIT,  // Debit transaction (withdrawal)
    CREDIT  // Credit transaction (replenishment of funds)
}