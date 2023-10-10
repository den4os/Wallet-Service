package io.ylab.walletservice.infrastructure.services;


import io.ylab.walletservice.domain.entities.Admin;
import io.ylab.walletservice.domain.repositories.AdminRepository;

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private int playerIdCount = 1;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin registerAdmin(String username, String password) {
        Admin existingAdmin = adminRepository.findByUsername(username);
        if (existingAdmin != null) {
            return null;
        }

        String adminId = generateUniqueAdminId();
        Admin newAdmin = new Admin(adminId, username, password);
        adminRepository.save(newAdmin);

        return newAdmin;
    }

    private String generateUniqueAdminId() {
        playerIdCount += playerIdCount;
        return Integer.toString(playerIdCount);
    }

    @Override
    public Admin authorizeAdmin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }

        return null;
    }
}