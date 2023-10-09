package com.ylab.walletservice.domain.entities;

public enum ActionType {
    PLAYER_REGISTRATION,      // Player registration
    PLAYER_AUTHORIZATION,     // Player authorization
    PLAYER_BALANCE,    // Player balance update
    DEBIT_TRANSACTION,        // Debit transaction (withdrawal)
    CREDIT_TRANSACTION,
    TRANSACTION,
    TRANSACTION_HISTORY,
    PLAYER_LOGOUT// Credit transaction (replenishment of funds)
}