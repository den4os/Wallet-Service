package io.ylab.walletservice.domain.repositories;

import io.ylab.walletservice.domain.entities.Admin;

public interface AdminRepository {
    Admin findByUsername(String username);
    void save(Admin admin);
}