package com.guai.onlinelearning.userDomain.staff;

import com.guai.onlinelearning.common.PaginationResponse;
import com.guai.onlinelearning.exception.EntityNotFoundException;
import com.guai.onlinelearning.userDomain.position.Position;
import com.guai.onlinelearning.userDomain.position.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements IStaffService {

    private final StaffRepository staffRepository;
    private final PositionRepository positionRepository;
    private final StaffMapper staffMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer create(StaffRequest staffRequest) {
        Position position = positionRepository.findById(staffRequest.getPositionId())
                .orElseThrow(
                        () -> new EntityNotFoundException(Position.class, "ID", staffRequest.getPositionId())
                );

        Staff staff = staffMapper.mergeForSaveStaff(staffRequest, position);
        return staffRepository.save(staff).getId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer update(Integer id, StaffRequest staffRequest) {
        Staff existingStaff = staffRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Staff.class, "ID", id));

        Position position = positionRepository.findById(staffRequest.getPositionId())
                .orElseThrow(
                        () -> new EntityNotFoundException(Position.class, "ID", staffRequest.getPositionId())
                );

        Staff staff = staffMapper.mergeForUpdateStaff(id, existingStaff, staffRequest, position);
        return staffRepository.save(staff).getId();
    }

    @Override
    public StaffResponse findById(Integer id) {
        return staffRepository.findById(id).map(staffMapper::toStaffResponse).orElseThrow(
                () -> new EntityNotFoundException(Staff.class, "ID", id)
        );
    }

    @Override
    public void delete(Integer id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException(Staff.class, "ID", id);
        }
        staffRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return staffRepository.existsById(id);
    }

    @Override
    public PaginationResponse<StaffResponse> findAll(Pageable pageable) {
        Page<Staff> staffPage = staffRepository.findAll(pageable);
        List<StaffResponse> resList = staffPage.stream().map(staffMapper::toStaffResponse).toList();
        return new PaginationResponse<>(
                staffPage.getNumber(),
                staffPage.getSize(),
                staffPage.getTotalPages(),
                staffPage.getTotalElements(),
                resList,
                staffPage.isFirst(),
                staffPage.isLast(),
                staffPage.isEmpty()
        );
    }
}
