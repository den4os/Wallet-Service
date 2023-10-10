package io.ylab.walletservice.in;

import io.ylab.walletservice.infrastructure.services.AdminService;
import io.ylab.walletservice.infrastructure.services.AuditLogService;
import io.ylab.walletservice.infrastructure.services.PlayerService;
import io.ylab.walletservice.infrastructure.services.TransactionService;

/**
 * This class represents a container for various services used in the wallet service system.
 * It provides getters and setters for the following services: PlayerService, TransactionService,
 * AdminService, and AuditLogService.
 * These services are used to manage players, transactions, administrators, and audit logs within the system.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
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
