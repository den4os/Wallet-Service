package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.AuditLog;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcAuditLogRepositoryTest extends AbstractContainerBaseTest {

    private JdbcAuditLogRepository jdbcAuditLogRepository;

    @BeforeEach
    void setUp() throws Exception {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        Liquibase liquibase = new Liquibase(
                "db/changelog/changelog.xml",
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(connection)
        );
        liquibase.update((String) null);

        jdbcAuditLogRepository = new JdbcAuditLogRepository(connection);
    }

    @Test
    void saveAuditLog() {
        AuditLog expectedAuditLog = new AuditLog("1", ActionType.PLAYER_AUTHORIZATION, ActionResult.SUCCESS, LocalDateTime.now());
        jdbcAuditLogRepository.saveAuditLog(expectedAuditLog);
        List<AuditLog> auditLogs = jdbcAuditLogRepository.getAuditLogs();
        AuditLog actualAuditLog = auditLogs.stream()
                .filter(auditLog -> auditLog.getPlayerId().equals("1"))
                .findFirst()
                .orElse(null);
        assertNotNull(actualAuditLog);
        assertEquals(expectedAuditLog.getPlayerId(), actualAuditLog.getPlayerId());
        assertEquals(expectedAuditLog.getActionType(), actualAuditLog.getActionType());
        assertEquals(expectedAuditLog.getResult(), actualAuditLog.getResult());
    }

    @Test
    void getAuditLogs() {
        AuditLog auditLog1 = new AuditLog("1", ActionType.TRANSACTION, ActionResult.SUCCESS, LocalDateTime.now());
        AuditLog auditLog2 = new AuditLog("2", ActionType.PLAYER_BALANCE, ActionResult.FAIL, LocalDateTime.now());
        jdbcAuditLogRepository.saveAuditLog(auditLog1);
        jdbcAuditLogRepository.saveAuditLog(auditLog2);
        List<AuditLog> actualAuditLogs = jdbcAuditLogRepository.getAuditLogs();
        assertNotNull(actualAuditLogs);

        assertEquals(auditLog1.getPlayerId(), actualAuditLogs.get(0).getPlayerId());
        assertEquals(auditLog1.getActionType(), actualAuditLogs.get(0).getActionType());
        assertEquals(auditLog1.getResult(), actualAuditLogs.get(0).getResult());

        assertEquals(auditLog2.getPlayerId(), actualAuditLogs.get(1).getPlayerId());
        assertEquals(auditLog2.getActionType(), actualAuditLogs.get(1).getActionType());
        assertEquals(auditLog2.getResult(), actualAuditLogs.get(1).getResult());
    }
}