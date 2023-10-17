package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.domain.entities.TransactionType;
import io.ylab.walletservice.domain.repositories.PlayerRepository;
import io.ylab.walletservice.domain.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    private TransactionServiceImpl transactionService;
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        playerRepository = mock(PlayerRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionServiceImpl(playerRepository, transactionRepository);
    }

    @Test
    void testPerformDebitTransaction() {
        Player player = new Player("samplePlayerId", "SamplePlayer", "password", new BigDecimal("100.0"));
        when(playerRepository.findById("samplePlayerId")).thenReturn(player);
        when(transactionRepository.getAllTransactions()).thenReturn(new HashMap<>());
        boolean result = transactionService.performDebitTransaction("samplePlayerId", "debitTransactionId", new BigDecimal("50.0"));
        assertTrue(result);
        assertEquals(new BigDecimal("50.0"), player.getBalance());
        verify(transactionRepository).saveTransaction(any());
    }

    @Test
    void testPerformCreditTransaction() {
        Player player = new Player("samplePlayerId", "SamplePlayer", "password", new BigDecimal("100.0"));
        when(playerRepository.findById("samplePlayerId")).thenReturn(player);
        when(transactionRepository.getAllTransactions()).thenReturn(new HashMap<>());
        boolean result = transactionService.performCreditTransaction("samplePlayerId", "creditTransactionId", new BigDecimal("50.0"));
        assertTrue(result);
        assertEquals(new BigDecimal("150.0"), player.getBalance());
        verify(transactionRepository).saveTransaction(any());
    }

    @Test
    void testGetPlayerTransactionHistory() {
        String playerId = "samplePlayerId";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("txn1", playerId, TransactionType.DEBIT, new BigDecimal("50.0"), LocalDateTime.now()));
        transactions.add(new Transaction("txn2", playerId, TransactionType.CREDIT, new BigDecimal("30.0"), LocalDateTime.now()));
        when(transactionRepository.getTransactionsByPlayerId(playerId)).thenReturn(transactions);
        List<Transaction> result = transactionService.getPlayerTransactionHistory(playerId);
        assertEquals(transactions, result);
    }
}