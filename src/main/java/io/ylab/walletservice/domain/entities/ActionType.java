package io.ylab.walletservice.domain.entities;

/**
 * This enumeration represents various types of actions that can be recorded in the audit log.
 * Each action type corresponds to a specific user interaction or system operation.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public enum ActionType {
    PLAYER_REGISTRATION,
    PLAYER_AUTHORIZATION,
    PLAYER_BALANCE,
    DEBIT_TRANSACTION,
    CREDIT_TRANSACTION,
    TRANSACTION,
    TRANSACTION_HISTORY,
    PLAYER_LOGOUT
}