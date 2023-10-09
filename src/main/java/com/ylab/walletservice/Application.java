package com.ylab.walletservice;

import com.ylab.walletservice.domain.repositories.AdminRepository;
import com.ylab.walletservice.domain.repositories.AuditLogRepository;
import com.ylab.walletservice.domain.repositories.PlayerRepository;
import com.ylab.walletservice.domain.repositories.TransactionRepository;
import com.ylab.walletservice.in.ConsoleAuthorizationInterface;
import com.ylab.walletservice.infrastructure.inmemory.InMemoryAdminRepository;
import com.ylab.walletservice.infrastructure.inmemory.InMemoryAuditLogRepository;
import com.ylab.walletservice.infrastructure.inmemory.InMemoryPlayerRepository;
import com.ylab.walletservice.infrastructure.inmemory.InMemoryTransactionRepository;
import com.ylab.walletservice.infrastructure.services.*;

public class Application {
    public static void main(String[] args) {
        PlayerRepository playerRepository = new InMemoryPlayerRepository();
        PlayerService playerService = new PlayerServiceImpl(playerRepository);
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        TransactionService transactionService = new TransactionServiceImpl(playerRepository, transactionRepository);
        AdminRepository adminRepository = new InMemoryAdminRepository();
        AdminService adminService = new AdminServiceImpl(adminRepository);
        AuditLogRepository auditLogRepository = new InMemoryAuditLogRepository();
        AuditLogService auditLogService = new AuditLogServiceImpl(auditLogRepository, playerRepository);
        ConsoleAuthorizationInterface consoleAuthorizationInterface = new ConsoleAuthorizationInterface(playerService, transactionService, adminService, auditLogService);

        consoleAuthorizationInterface.start();
    }
}
