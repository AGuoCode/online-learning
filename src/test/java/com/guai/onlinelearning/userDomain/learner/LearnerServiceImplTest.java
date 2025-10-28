package com.guai.onlinelearning.userDomain.learner;

import com.guai.onlinelearning.exception.EntityNotFoundException;
import com.guai.onlinelearning.userDomain.Gender;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Learner Service Test")
class LearnerServiceImplTest {

    @Mock
    private LearnerRepository learnerRepository;

    @Mock
    private LearnerMapper learnerMapper;

    @InjectMocks
    private LearnerServiceImpl learnerService;

    private LearnerRequest learnerRequest;
    private Learner learner;

    private LearnerRequest updatedLearnerRequest;
    private Learner updatedLearner;

    private User user;

    @BeforeEach
    public void init() {

        this.user = User.builder()
                .id(2)
                .username("johndoe@gmail.com")
                .password("joghdoe123")
                .role(UserRole.LEARNER)
                .enabled(true)
                .locked(false)
                .build();

        this.learnerRequest = LearnerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .dateOfBirth("20251024")
                .phoneNumber("099258234369")
                .gender(Gender.MALE)
                .role(UserRole.LEARNER)
                .build();

        this.learner = Learner.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .dateOfBirth("20251024")
                .phoneNumber("099258234369")
                .gender(Gender.MALE)
                .user(this.user)
                .build();

        this.updatedLearnerRequest = LearnerRequest.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johndoe@gmail.com")
                .dateOfBirth("20251022")
                .phoneNumber("094829387683")
                .gender(Gender.FEMALE)
                .role(UserRole.LEARNER)
                .build();

        this.updatedLearner = Learner.builder()
                .id(1)
                .firstName("John")
                .lastName("Smith")
                .email("johndoe@gmail.com")
                .dateOfBirth("20251022")
                .phoneNumber("094829387683")
                .gender(Gender.FEMALE)
                .user(this.user)
                .build();
    }

    @Nested
    @DisplayName("Create Learner Service Testing")
    class CreateLearnerTest {
        @Test
        @DisplayName("create Learner successfully when valid data provided")
        void createLearner_Success() {
            //GIVEN
            // Map Data from LearnerRequest to Learner
            when(learnerMapper.toLearner(learnerRequest)).thenReturn(learner);
            // save Learner and return Learner with Id
            when(learnerRepository.save(learner)).thenReturn(learner);

            //WHEN
            final Integer learnerId = learnerService.create(learnerRequest);

            //THEN
            assertNotNull(learnerId);
            assertEquals(1, learnerId);

            //VERIFY
            verify(learnerMapper, times(1)).toLearner(learnerRequest);
            verify(learnerRepository, times(1)).save(
                    argThat(learner ->
                            learner.getUser() != null &&
                                    learner.getUser().equals(user)
                    )
            );
        }

    }

    @Nested
    @DisplayName("Update Learner Service Testing")
    class UpdateLearnerTest {
        @Test
        @DisplayName("update learner successfully when valid data provided")
        void updateLearner_Success() {
            //GIVEN
            final Integer learnerId = 1;

            //Check Learner Exist and return Existing Learner
            when(learnerRepository.findById(learnerId)).thenReturn(Optional.of(learner));
            // Map Data from learnerId, existingLearner and updatedLearnerRequest to updatedLearner
            when(learnerMapper.mergeForUpdateLearner(learnerId, learner, updatedLearnerRequest)).thenReturn(updatedLearner);
            // save updatedLearner and return updatedLearner with Id
            when(learnerRepository.save(updatedLearner)).thenReturn(updatedLearner);

            //WHEN
            final Integer learnerIdAfterUpdate = learnerService.update(learnerId, updatedLearnerRequest);

            //THEN
            assertNotNull(learnerIdAfterUpdate);
            assertEquals(1, learnerIdAfterUpdate);

            //VERIFY
            verify(learnerRepository, times(1)).findById(learnerId);
            verify(learnerMapper, times(1)).mergeForUpdateLearner(learnerId, learner, updatedLearnerRequest);
            verify(learnerRepository, times(1)).save(
                    argThat(updatedLearner ->
                            updatedLearner.getUser() != null &&
                                    updatedLearner.getUser().equals(user)
                    )
            );
        }

        @Test
        @DisplayName("throw exception when learner not found during update")
        void updateAdmin_AdminNotFound_ThrowsException() {
            //GIVEN
            final Integer learnerId = 2;
            // Check Admin Exist but return null
            when(learnerRepository.findById(learnerId)).thenReturn(Optional.empty());

            //WHEN & THEN
            assertThrows(EntityNotFoundException.class, () -> learnerService.update(learnerId, updatedLearnerRequest));

            //VERIFY
            verify(learnerRepository, times(1)).findById(learnerId);
            verifyNoMoreInteractions(learnerRepository);
            verifyNoInteractions(learnerMapper);
        }
    }

}