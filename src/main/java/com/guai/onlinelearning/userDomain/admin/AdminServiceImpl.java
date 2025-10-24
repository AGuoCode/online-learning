package com.guai.onlinelearning.userDomain.admin;

import com.guai.onlinelearning.common.PaginationResponse;
import com.guai.onlinelearning.exception.BusinessException;
import com.guai.onlinelearning.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Override
    public Integer create(AdminRequest adminRequest) {
        Admin admin = adminMapper.toAdmin(adminRequest);
        return adminRepository.save(admin).getId();
    }

    @Override
    public Integer update(Integer id, AdminRequest adminRequest) {
        adminRequest.isValid();
        Admin existingAdmin = adminRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Admin.class, "id", id)
        );
        Admin admin = adminMapper.mergeForUpdateAdmin(id, existingAdmin, adminRequest);
        return adminRepository.save(admin).getId();
    }

    @Override
    public AdminResponse findById(Integer id) {
        return adminRepository.findById(id).map(adminMapper::toAdminResponse).orElseThrow(
                () -> new EntityNotFoundException(Admin.class, "id", id)
        );
    }

    @Override
    public void delete(Integer id) {
        throw new BusinessException("Not supported operation.");
    }

    @Override
    public boolean existsById(Integer id) {
        return adminRepository.existsById(id);
    }

    @Override
    public PaginationResponse<AdminResponse> findAll(Pageable pageable) {
        throw new BusinessException("Not supported operation.");
    }
}
