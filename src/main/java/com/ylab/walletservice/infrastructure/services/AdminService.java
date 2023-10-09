package com.ylab.walletservice.infrastructure.services;

import com.ylab.walletservice.domain.entities.Admin;

public interface AdminService {
    Admin registerAdmin(String username, String password);
    Admin authorizeAdmin(String username, String password);
}