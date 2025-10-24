package com.guai.onlinelearning.userDomain.admin;

import com.guai.onlinelearning.userDomain.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminMapper {

    private final UserMapper userMapper;

    public Admin toAdmin(AdminRequest request) {
        return Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .article(request.getArticle())
                .professional(request.getProfessional())
                .bio(request.getBio())
                .certificates(request.getCertificates())
                .user(
                        userMapper.toUser(request.getEmail(), request.getRole())
                )
                .build();
    }

    public Admin mergeForUpdateAdmin(Integer id, Admin existingAdmin, AdminRequest request) {
        return Admin.builder()
                .id(id)
                .name(request.getName())
                .email(existingAdmin.getEmail())
                .article(request.getArticle())
                .professional(request.getProfessional())
                .bio(request.getBio())
                .certificates(request.getCertificates())
                .user(existingAdmin.getUser())
                .build();
    }

    public AdminResponse toAdminResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .article(admin.getArticle())
                .professional(admin.getProfessional())
                .bio(admin.getBio())
                .certificates(admin.getCertificates())
                .user(userMapper.toUserResponse(admin.getUser()))
                .build();
    }
}
