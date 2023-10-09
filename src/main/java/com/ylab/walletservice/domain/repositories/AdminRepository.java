package com.ylab.walletservice.domain.repositories;

import com.ylab.walletservice.domain.entities.Admin;

public interface AdminRepository {
    Admin findByUsername(String username);
    void save(Admin admin);
}