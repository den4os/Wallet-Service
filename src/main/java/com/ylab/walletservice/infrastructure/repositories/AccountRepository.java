package com.ylab.walletservice.infrastructure.repositories;

import com.ylab.walletservice.domain.entities.Account;

/**
 * Repository interface for accessing player accounts.
 * This interface provides methods to interact with player accounts in the repository.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-07
 */
public interface AccountRepository {
    /**
     * Retrieves an account by its unique identifier.
     *
     * @param accountId The unique identifier of the account to retrieve.
     * @return The account with the specified identifier, or null if not found.
     */
    Account findById(String accountId);

    /**
     * Saves an account in the repository.
     *
     * @param account The account to save.
     */
    void save(Account account);

    /**
     * Updates an existing account in the repository.
     *
     * @param account The account to update.
     */
    void update(Account account);
}