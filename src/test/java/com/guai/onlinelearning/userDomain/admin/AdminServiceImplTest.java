package com.guai.onlinelearning.userDomain.admin;

import com.guai.onlinelearning.exception.EntityNotFoundException;
import com.guai.onlinelearning.userDomain.UserRole;
import com.guai.onlinelearning.userDomain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Admin Service Test")
public class AdminServiceImplTest {
    
    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminMapper adminMapper;

    @InjectMocks
    private AdminServiceImpl adminService;

    private AdminRequest adminRequest;
    private Admin admin;

    private AdminRequest updatedAdminRequest;
    private Admin updatedAdmin;

    private User user;

    @BeforeEach
    public void init() {

        this.user = User.builder()
                .id(2)
                .username("admin@gmail.com")
                .password("admin123")
                .role(UserRole.ADMIN)
                .enabled(true)
                .locked(false)
                .build();

        this.adminRequest = AdminRequest.builder()
                .name("admin")
                .email("admin@gmail.com")
                .article("Learn Japanese and start careers")
                .professional("Japanese Teacher")
                .bio("""
                         Hello Learners, I am a Japanese Teacher.
                        
                         I have got the JLPT N3 level certificate. I have 1 year experience for teaching Japanese.
                        """)
                .certificates(List.of("JLPT N3 Certificate"))
                .role(UserRole.ADMIN)
                .build();

        this.admin = Admin.builder()
                .id(1)
                .name("admin")
                .email("admin@gmail.com")
                .article("Learn Japanese and start careers")
                .professional("Japanese Teacher")
                .bio("""
                         Hello Learners, I am a Japanese Teacher.
                        
                         I have got the JLPT N3 level certificate. I have 1 year experience for teaching Japanese.
                        """)
                .certificates(List.of("JLPT N3 Certificate"))
                .user(this.user)
                .build();

        this.updatedAdminRequest = AdminRequest.builder()
                .name("Sho Japanese Learning")
                .email("admin@gmail.com")
                .article("Learn Japanese and start careers")
                .professional("Japanese Teacher")
                .bio("""
                         Hello Learners, I am a Japanese Teacher.
                        
                         I have got the JLPT N2 level certificate. I have 1 year experience for teaching Japanese.
                        """)
                .certificates(List.of("JLPT N2 Certificate"))
                .role(UserRole.ADMIN)
                .build();

        this.updatedAdmin = Admin.builder()
                .id(1)
                .name("Sho Japanese Learning")
                .email("admin@gmail.com")
                .article("Learn Japanese and start careers")
                .professional("Japanese Teacher")
                .bio("""
                         Hello Learners, I am a Japanese Teacher.
                        
                         I have got the JLPT N2 level certificate. I have 1 year experience for teaching Japanese.
                        """)
                .certificates(List.of("JLPT N2 Certificate"))
                .user(this.user)
                .build();
    }

    @Nested
    @DisplayName("Create Admin Service Testing")
    class CreateAdminTest {
        @Test
        @DisplayName("create Admin successfully when valid data provided")
        void createAdmin_Success() {
            //GIVEN
            // Map Data from AdminRequest to Admin
            when(adminMapper.toAdmin(adminRequest)).thenReturn(admin);
            // save Admin and return Admin with Id
            when(adminRepository.save(admin)).thenReturn(admin);

            //WHEN
            final Integer adminId = adminService.create(adminRequest);

            //THEN
            assertNotNull(adminId);
            assertEquals(1, adminId);

            //VERIFY
            verify(adminMapper, times(1)).toAdmin(adminRequest);
            verify(adminRepository, times(1)).save(
                    argThat(admin ->
                            admin.getUser() != null &&
                                    admin.getUser().equals(user)
                    )
            );
        }

    }

    @Nested
    @DisplayName("Update Admin Service Testing")
    class UpdateAdminTest {
        @Test
        @DisplayName("update admin successfully when valid data provided")
        void updateAdmin_Success() {
            //GIVEN
            final Integer adminId = 1;

            //Check Admin Exist and return Existing Admin
            when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
            // Map Data from adminId, existingAdmin and updatedAdminRequest to updatedAdmin
            when(adminMapper.mergeForUpdateAdmin(adminId, admin, updatedAdminRequest)).thenReturn(updatedAdmin);
            // save updatedAdmin and return updatedAdmin with Id
            when(adminRepository.save(updatedAdmin)).thenReturn(updatedAdmin);

            //WHEN
            final Integer adminIdAfterUpdate = adminService.update(adminId, updatedAdminRequest);

            //THEN
            assertNotNull(adminIdAfterUpdate);
            assertEquals(1, adminIdAfterUpdate);

            //VERIFY
            verify(adminRepository, times(1)).findById(adminId);
            verify(adminMapper, times(1)).mergeForUpdateAdmin(adminId, admin, updatedAdminRequest);
            verify(adminRepository, times(1)).save(
                    argThat(updatedAdmin ->
                            updatedAdmin.getUser() != null &&
                                    updatedAdmin.getUser().equals(user)
                    )
            );
        }

        @Test
        @DisplayName("throw exception when admin not found during update")
        void updateAdmin_AdminNotFound_ThrowsException() {
            //GIVEN
            final Integer adminId = 2;
            // Check Admin Exist but return null
            when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

            //WHEN & THEN
            assertThrows(EntityNotFoundException.class, () -> adminService.update(adminId, updatedAdminRequest));

            //VERIFY
            verify(adminRepository, times(1)).findById(adminId);
            verifyNoMoreInteractions(adminRepository);
            verifyNoInteractions(adminMapper);
        }
    }

}
