package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.AuditLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryAuditLogRepositoryTest {

    private InMemoryAuditLogRepository auditLogRepository;

    @BeforeEach
    void setUp() {
        auditLogRepository = new InMemoryAuditLogRepository();
    }

    @Test
    void testSaveAuditLog() {
        LocalDateTime timestamp = LocalDateTime.now();
        AuditLog auditLog1 = new AuditLog("1", ActionType.TRANSACTION_HISTORY, ActionResult.SUCCESS, timestamp);
        AuditLog auditLog2 = new AuditLog("2", ActionType.PLAYER_LOGOUT, ActionResult.FAIL, timestamp);

        auditLogRepository.saveAuditLog(auditLog1);
        auditLogRepository.saveAuditLog(auditLog2);

        List<AuditLog> result = auditLogRepository.getAuditLogs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(auditLog1));
        assertTrue(result.contains(auditLog2));
    }

    @Test
    void testGetAuditLogs() {
        LocalDateTime timestamp = LocalDateTime.now();
        AuditLog auditLog1 = new AuditLog("1", ActionType.TRANSACTION_HISTORY, ActionResult.SUCCESS, timestamp);
        AuditLog auditLog2 = new AuditLog("2", ActionType.PLAYER_LOGOUT, ActionResult.FAIL, timestamp);

        auditLogRepository.saveAuditLog(auditLog1);
        auditLogRepository.saveAuditLog(auditLog2);

        List<AuditLog> result = auditLogRepository.getAuditLogs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(auditLog1));
        assertTrue(result.contains(auditLog2));
    }
}