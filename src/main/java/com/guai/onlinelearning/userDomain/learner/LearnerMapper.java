package com.guai.onlinelearning.userDomain.learner;

import com.guai.onlinelearning.exception.BusinessException;
import com.guai.onlinelearning.userDomain.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LearnerMapper {

    private final UserMapper userMapper;

    public Learner toLearner(LearnerRequest request) {
        request.isValid();
        return Learner.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .user(userMapper.toUser(request.getEmail(), request.getRole()))
                .build();
    }

    public Learner mergeForUpdateLearner(Integer id, Learner existingLearner, LearnerRequest request) {
        request.isValid();
        checkEmail(existingLearner.getEmail(), request.getEmail());
        return Learner.builder()
                .id(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(existingLearner.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .user(existingLearner.getUser())
                .build();
    }

    public LearnerResponse toLearnerResponse(Learner learner) {
        return LearnerResponse.builder()
                .id(learner.getId())
                .firstName(learner.getFirstName())
                .lastName(learner.getLastName())
                .email(learner.getEmail())
                .dateOfBirth(learner.getDateOfBirth())
                .phoneNumber(learner.getPhoneNumber())
                .gender(learner.getGender())
                .user(userMapper.toUserResponse(learner.getUser()))
                .build();
    }

    private void checkEmail(String oldEmail, String newEmail) {
        if (!oldEmail.equals(newEmail)) {
            throw new BusinessException("Changing or updating email is not supported.");
        }
    }
}
