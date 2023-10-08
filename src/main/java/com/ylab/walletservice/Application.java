package com.ylab.walletservice;

import com.ylab.walletservice.domain.repositories.PlayerRepository;
import com.ylab.walletservice.domain.repositories.TransactionRepository;
import com.ylab.walletservice.in.ConsoleUserInterface;
import com.ylab.walletservice.infrastructure.inmemory.InMemoryPlayerRepository;
import com.ylab.walletservice.infrastructure.inmemory.InMemoryTransactionRepository;
import com.ylab.walletservice.infrastructure.services.PlayerService;
import com.ylab.walletservice.infrastructure.services.PlayerServiceImpl;
import com.ylab.walletservice.infrastructure.services.TransactionService;
import com.ylab.walletservice.infrastructure.services.TransactionServiceImpl;

public class Application {
    public static void main(String[] args) {
        PlayerRepository playerRepository = new InMemoryPlayerRepository();
        PlayerService playerService = new PlayerServiceImpl(playerRepository);
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        TransactionService transactionService = new TransactionServiceImpl(playerRepository, transactionRepository);
        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface(playerService, transactionService);

        consoleUserInterface.start();
    }
}
