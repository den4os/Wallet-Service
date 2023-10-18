package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.domain.entities.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTransactionRepositoryIT extends AbstractContainerBaseIT {

    private JdbcTransactionRepository jdbcTransactionRepository;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        jdbcTransactionRepository = new JdbcTransactionRepository(connection);
    }

    @Test
    void saveTransaction() {
        Transaction expectedTransaction = new Transaction("1", "1", TransactionType.CREDIT, new BigDecimal("100.00"), LocalDateTime.now());
        jdbcTransactionRepository.saveTransaction(expectedTransaction);

        List<Transaction> transactions = jdbcTransactionRepository.getTransactionsByPlayerId("1");
        Transaction transaction = transactions.get(0);
        assertNotNull(transaction);
        assertEquals(transaction.getTransactionId(), expectedTransaction.getTransactionId());
        assertEquals(transaction.getPlayerId(), expectedTransaction.getPlayerId());
        assertEquals(transaction.getType(), expectedTransaction.getType());
        assertEquals(transaction.getAmount(), expectedTransaction.getAmount());
    }

    @Test
    void getTransactionsByPlayerId() {
        Transaction expectedTransaction1 = new Transaction("1", "1", TransactionType.CREDIT, new BigDecimal("100.00"), LocalDateTime.now());
        Transaction expectedTransaction2 = new Transaction("2", "1", TransactionType.DEBIT, new BigDecimal("20.00"), LocalDateTime.now());
        Transaction expectedTransaction3 = new Transaction("3", "2", TransactionType.DEBIT, new BigDecimal("20.00"), LocalDateTime.now());
        jdbcTransactionRepository.saveTransaction(expectedTransaction1);
        jdbcTransactionRepository.saveTransaction(expectedTransaction2);
        jdbcTransactionRepository.saveTransaction(expectedTransaction3);
        List<Transaction> transactions = jdbcTransactionRepository.getTransactionsByPlayerId("1");
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
    }

    @Test
    void getAllTransactions() {
        Transaction expectedTransaction1 = new Transaction("1", "1", TransactionType.CREDIT, new BigDecimal("100.00"), LocalDateTime.now());
        Transaction expectedTransaction2 = new Transaction("2", "1", TransactionType.DEBIT, new BigDecimal("20.00"), LocalDateTime.now());
        Transaction expectedTransaction3 = new Transaction("3", "2", TransactionType.DEBIT, new BigDecimal("20.00"), LocalDateTime.now());
        Transaction expectedTransaction4 = new Transaction("4", "3", TransactionType.DEBIT, new BigDecimal("30.00"), LocalDateTime.now());
        jdbcTransactionRepository.saveTransaction(expectedTransaction1);
        jdbcTransactionRepository.saveTransaction(expectedTransaction2);
        jdbcTransactionRepository.saveTransaction(expectedTransaction3);
        jdbcTransactionRepository.saveTransaction(expectedTransaction4);
        Map<String, Transaction> transactions = jdbcTransactionRepository.getAllTransactions();
        assertNotNull(transactions);
        assertEquals(4, transactions.size());
    }
}