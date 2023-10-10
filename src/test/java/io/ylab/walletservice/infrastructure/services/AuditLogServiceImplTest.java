package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.AuditLog;
import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.repositories.AuditLogRepository;
import io.ylab.walletservice.domain.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuditLogServiceImplTest {

    private AuditLogServiceImpl auditLogService;
    private AuditLogRepository auditLogRepository;
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        auditLogRepository = mock(AuditLogRepository.class);
        playerRepository = mock(PlayerRepository.class);
        auditLogService = new AuditLogServiceImpl(auditLogRepository, playerRepository);
    }

    @Test
    void testAddAuditLog() {
        String playerId = "samplePlayerId";
        ActionType actionType = ActionType.PLAYER_BALANCE;
        ActionResult actionResult = ActionResult.SUCCESS;
        doNothing().when(auditLogRepository).saveAuditLog(any());
        auditLogService.addAuditLog(playerId, actionType, actionResult);
        verify(auditLogRepository, times(1)).saveAuditLog(argThat(auditLog ->
                auditLog.getPlayerId().equals(playerId) &&
                        auditLog.getActionType() == actionType &&
                        auditLog.getResult() == actionResult
        ));
    }

    @Test
    void testGetAllAuditLogs() {
        List<AuditLog> auditLogs = new ArrayList<>();
        auditLogs.add(new AuditLog("player1", ActionType.PLAYER_BALANCE, ActionResult.SUCCESS, LocalDateTime.now()));
        auditLogs.add(new AuditLog("player2", ActionType.PLAYER_REGISTRATION, ActionResult.SUCCESS, LocalDateTime.now()));
        when(auditLogRepository.getAuditLogs()).thenReturn(auditLogs);
        List<AuditLog> result = auditLogService.getAllAuditLogs();
        assertEquals(auditLogs, result);
    }

    @Test
    void testGetPlayerAuditLogs() {
        String playerId = "samplePlayerId";
        List<AuditLog> auditLogs = new ArrayList<>();
        auditLogs.add(new AuditLog(playerId, ActionType.PLAYER_BALANCE, ActionResult.SUCCESS, LocalDateTime.now()));
        auditLogs.add(new AuditLog("otherPlayer", ActionType.PLAYER_REGISTRATION, ActionResult.SUCCESS, LocalDateTime.now()));
        when(playerRepository.findById(playerId)).thenReturn(new Player(playerId, "SamplePlayer", "password", 100.0));
        when(auditLogRepository.getAuditLogs()).thenReturn(auditLogs);
        List<AuditLog> result = auditLogService.getPlayerAuditLogs(playerId);
        assertEquals(1, result.size());
        assertEquals(playerId, result.get(0).getPlayerId());
    }
}