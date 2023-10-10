package io.ylab.walletservice;

import io.ylab.walletservice.domain.repositories.*;
import io.ylab.walletservice.in.*;
import io.ylab.walletservice.infrastructure.inmemory.*;
import io.ylab.walletservice.infrastructure.services.*;

public class Application {
    public static void main(String[] args) {
        ServiceContainer serviceContainer = new ServiceContainer();
        ConsoleInterfaceManager consoleInterfaceManager = new ConsoleInterfaceManager();

        PlayerRepository playerRepository = new InMemoryPlayerRepository();
        PlayerService playerService = new PlayerServiceImpl(playerRepository);
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        TransactionService transactionService = new TransactionServiceImpl(playerRepository, transactionRepository);
        AdminRepository adminRepository = new InMemoryAdminRepository();
        AdminService adminService = new AdminServiceImpl(adminRepository);
        AuditLogRepository auditLogRepository = new InMemoryAuditLogRepository();
        AuditLogService auditLogService = new AuditLogServiceImpl(auditLogRepository, playerRepository);

        serviceContainer.setPlayerService(playerService);
        serviceContainer.setTransactionService(transactionService);
        serviceContainer.setAdminService(adminService);
        serviceContainer.setAuditLogService(auditLogService);

        ConsoleAuthorization consoleAuthorization = new ConsoleAuthorization(consoleInterfaceManager, serviceContainer);

        consoleInterfaceManager.pushInterface(consoleAuthorization);
        consoleInterfaceManager.start();
    }
}
