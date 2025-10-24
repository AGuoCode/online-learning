package com.guai.onlinelearning.config;

import com.guai.onlinelearning.userDomain.UserRole;
import com.guai.onlinelearning.userDomain.admin.AdminRequest;
import com.guai.onlinelearning.userDomain.admin.IAdminService;
import com.guai.onlinelearning.userDomain.user.User;
import com.guai.onlinelearning.userDomain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitConfiguration {

    private final IAdminService adminService;
    private final UserRepository userRepository;
    @Value("${app.setup.admin.email}")
    private String adminEmail;

    @Bean
    public CommandLineRunner initCommandLineRunner() {
        return args -> {
            System.out.println("=== Initializing Application Data ===");

            // Initialize admin
            initializeAdmin();

            System.out.println("=== Initialization Application Data  Complete ===");
        };
    }

    private AdminRequest initializeAdminRequest() {
        return AdminRequest.builder()
                .name("Admin")
                .email(adminEmail)
                .article("Teaching Article")
                .professional("Your Professional")
                .bio("""
                        Your Bio""")
                .certificates(null)
                .role(UserRole.ADMIN)
                .build();
    }

    private void initializeAdmin() {
        // Check setup email already exists
        User user = userRepository.findByUsername(adminEmail).orElse(null);
        if (user == null) {
            //Initialize adminRequest
            AdminRequest request = initializeAdminRequest();
            adminService.create(request);
        }
    }


}
