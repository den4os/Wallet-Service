package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.domain.entities.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTransactionRepositoryTest {

    private InMemoryTransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = new InMemoryTransactionRepository();
    }

    @Test
    void testSaveTransaction() {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction1 = new Transaction("1", "player1", TransactionType.DEBIT, 50.0, timestamp);
        Transaction transaction2 = new Transaction("2", "player2", TransactionType.CREDIT, 100.0, timestamp);

        transactionRepository.saveTransaction(transaction1);
        transactionRepository.saveTransaction(transaction2);

        Map<String, Transaction> allTransactions = transactionRepository.getAllTransactions();

        assertNotNull(allTransactions);
        assertEquals(2, allTransactions.size());
        assertTrue(allTransactions.containsKey("1"));
        assertTrue(allTransactions.containsKey("2"));
    }

    @Test
    void testGetTransactionsByPlayerId() {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction1 = new Transaction("1", "player1", TransactionType.DEBIT, 50.0, timestamp);
        Transaction transaction2 = new Transaction("2", "player1", TransactionType.CREDIT, 100.0, timestamp);
        Transaction transaction3 = new Transaction("3", "player2", TransactionType.DEBIT, 25.0, timestamp);

        transactionRepository.saveTransaction(transaction1);
        transactionRepository.saveTransaction(transaction2);
        transactionRepository.saveTransaction(transaction3);

        List<Transaction> player1Transactions = transactionRepository.getTransactionsByPlayerId("player1");
        List<Transaction> player2Transactions = transactionRepository.getTransactionsByPlayerId("player2");

        assertNotNull(player1Transactions);
        assertEquals(2, player1Transactions.size());
        assertTrue(player1Transactions.contains(transaction1));
        assertTrue(player1Transactions.contains(transaction2));

        assertNotNull(player2Transactions);
        assertEquals(1, player2Transactions.size());
        assertTrue(player2Transactions.contains(transaction3));
    }
}