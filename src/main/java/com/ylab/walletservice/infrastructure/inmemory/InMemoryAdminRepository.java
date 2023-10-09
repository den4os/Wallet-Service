package com.ylab.walletservice.infrastructure.inmemory;

import com.ylab.walletservice.domain.entities.Admin;
import com.ylab.walletservice.domain.repositories.AdminRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAdminRepository implements AdminRepository {
    private final Map<String, Admin> admins = new HashMap<>();

    @Override
    public Admin findByUsername(String username) {
        return admins.values().stream()
                .filter(admin -> admin.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Admin admin) {
        admins.put(admin.getAdminId(), admin);
    }
}