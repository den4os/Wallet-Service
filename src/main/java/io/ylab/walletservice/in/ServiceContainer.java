package io.ylab.walletservice.in;

import io.ylab.walletservice.infrastructure.services.AdminService;
import io.ylab.walletservice.infrastructure.services.AuditLogService;
import io.ylab.walletservice.infrastructure.services.PlayerService;
import io.ylab.walletservice.infrastructure.services.TransactionService;

public class ServiceContainer {
    private PlayerService playerService;
    private TransactionService transactionService;
    private AdminService adminService;
    private AuditLogService auditLogService;

    public PlayerService getPlayerService() {
        return playerService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public AuditLogService getAuditLogService() {
        return auditLogService;
    }

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public void setAuditLogService(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }
}
