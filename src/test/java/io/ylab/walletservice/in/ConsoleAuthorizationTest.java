package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.infrastructure.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleAuthorizationTest {

    @Mock
    private ConsoleInterfaceManager interfaceManager;

    @Mock
    private ConsoleUserInput consoleUserInput;

    @Mock
    private ServiceContainer serviceContainer;

    @Mock
    private PlayerService playerService;

    @Mock
    private AuditLogService auditLogService;


    @Test
    void handlePlayerRegistration() {
        consoleUserInput = mock(ConsoleUserInput.class);
        when(consoleUserInput.getNextLine())
                .thenReturn("testuser")
                .thenReturn("testpassword");

        Player fakePlayer = new Player("1", "testuser", "testpassword", new BigDecimal("0.0"));
        when(playerService.registerPlayer("testuser", "testpassword")).thenReturn(fakePlayer);

        when(serviceContainer.getPlayerService()).thenReturn(playerService);
        when(serviceContainer.getAuditLogService()).thenReturn(auditLogService);

        ConsoleAuthorization consoleAuthorization = new ConsoleAuthorization(interfaceManager, serviceContainer, consoleUserInput);

        consoleAuthorization.handlePlayerRegistration();
        verify(playerService, Mockito.times(1)).registerPlayer("testuser", "testpassword");
    }
}