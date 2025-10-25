package com.guai.onlinelearning.userDomain.staff;

import com.guai.onlinelearning.exception.BusinessException;
import com.guai.onlinelearning.userDomain.position.Position;
import com.guai.onlinelearning.userDomain.position.PositionMapper;
import com.guai.onlinelearning.userDomain.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffMapper {

    private final UserMapper userMapper;
    private final PositionMapper positionMapper;

    public Staff mergeForSaveStaff(StaffRequest request, Position position) {
        request.isValid();
        return Staff.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .nrc(request.getNrc())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .user(userMapper.toUser(request.getEmail(), request.getRole()))
                .position(position)
                .build();
    }

    public Staff mergeForUpdateStaff(Integer id, Staff existingStaff, StaffRequest request, Position position) {
        request.isValid();
        checkEmail(existingStaff.getEmail(), request.getEmail());
        return Staff.builder()
                .id(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(existingStaff.getEmail())
                .nrc(request.getNrc())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .user(existingStaff.getUser())
                .position(position)
                .build();
    }

    public StaffResponse toStaffResponse(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .fullName(staff.getFirstName() + " " + staff.getLastName())
                .email(staff.getEmail())
                .nrc(staff.getNrc())
                .address(staff.getAddress())
                .phoneNumber(staff.getPhoneNumber())
                .dateOfBirth(staff.getDateOfBirth())
                .gender(staff.getGender())
                .position(positionMapper.toPositionResponse(staff.getPosition()))
                .user(userMapper.toUserResponse(staff.getUser()))
                .build();
    }


    private void checkEmail(String oldEmail, String newEmail) {
        if (!oldEmail.equals(newEmail)) {
            throw new BusinessException("Changing or updating email is not supported.");
        }
    }

}
