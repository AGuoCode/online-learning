package com.guai.onlinelearning.userDomain.staff;

import com.guai.onlinelearning.exception.EntityNotFoundException;
import com.guai.onlinelearning.userDomain.Gender;
import com.guai.onlinelearning.userDomain.UserRole;
import com.guai.onlinelearning.userDomain.position.Position;
import com.guai.onlinelearning.userDomain.position.PositionRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Staff Service Test")
class StaffServiceImplTest {

    @Mock
    private StaffRepository staffRepository;
    @Mock
    private PositionRepository positionRepository;
    @Mock
    private StaffMapper staffMapper;

    @InjectMocks
    private StaffServiceImpl staffService;

    private Position position;

    private Position updatedPosition;

    private StaffRequest staffRequest;
    private Staff staff;

    private StaffRequest updatedStaffRequest;
    private Staff updatedStaff;

    private User user;

    @BeforeEach
    public void init() {

        this.user = User.builder()
                .id(2)
                .username("johndoe@gmail.com")
                .password("JohnDoe123")
                .role(UserRole.STAFF)
                .enabled(true)
                .locked(false)
                .build();

        this.position = Position.builder()
                .id(3)
                .name("English Teacher")
                .build();

        this.updatedPosition = Position.builder()
                .id(4)
                .name("French Teacher")
                .build();

        this.staffRequest = StaffRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .phoneNumber("123456789")
                .address("")
                .nrc("123947293")
                .dateOfBirth("")
                .gender(Gender.MALE)
                .role(UserRole.STAFF)
                .positionId(3)
                .build();

        this.staff = Staff.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .phoneNumber("123456789")
                .address("")
                .nrc("123947293")
                .dateOfBirth("")
                .gender(Gender.MALE)
                .user(this.user)
                .position(this.position)
                .build();

        this.updatedStaffRequest = StaffRequest.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johndoe@gmail.com")
                .phoneNumber("123456789")
                .address("")
                .nrc("123947293")
                .dateOfBirth("")
                .gender(Gender.MALE)
                .role(UserRole.STAFF)
                .positionId(4)
                .build();

        this.updatedStaff = Staff.builder()
                .id(1)
                .firstName("John")
                .lastName("Smith")
                .email("johndoe@gmail.com")
                .phoneNumber("123456789")
                .address("")
                .nrc("123947293")
                .dateOfBirth("")
                .gender(Gender.MALE)
                .user(this.user)
                .position(this.updatedPosition)
                .build();

    }

    @Nested
    @DisplayName("Create Staff Service Testing")
    class CreateStaffTest {
        @Test
        @DisplayName("create staff successfully when valid data provided")
        void createStaff_Success() {
            //GIVEN
            // Check Position Exist and return Position
            when(positionRepository.findById(staffRequest.getPositionId())).thenReturn(Optional.of(position));
            // Map Data from StaffRequest to Staff
            when(staffMapper.mergeForSaveStaff(staffRequest, position)).thenReturn(staff);
            // save Staff and return Staff with Id
            when(staffRepository.save(staff)).thenReturn(staff);

            //WHEN
            final Integer staffId = staffService.create(staffRequest);

            //THEN
            assertNotNull(staffId);
            assertEquals(1, staffId);

            //VERIFY
            verify(positionRepository, times(1)).findById(staffRequest.getPositionId());
            verify(staffMapper, times(1)).mergeForSaveStaff(staffRequest, position);
            verify(staffRepository, times(1)).save(
                    argThat(staff ->
                            staff.getUser() != null &&
                                    staff.getUser().equals(user) &&
                                    staff.getPosition() != null &&
                                    staff.getPosition().equals(position)
                    )
            );
        }

        @Test
        @DisplayName("throw exception when Position not found")
        void createStaff_PositionNotFound_ThrowException() {
            //GIVEN
            final Integer positionId = 3;
            // Check Position Exist but return null
            when(positionRepository.findById(positionId)).thenReturn(Optional.empty());

            //WHEN & THEN
            assertThrows(EntityNotFoundException.class,
                    () -> staffService.create(staffRequest)
            );

            //VERIFY
            verify(positionRepository, times(1)).findById(positionId);
            verifyNoInteractions(staffMapper, staffRepository);
        }
    }

    @Nested
    @DisplayName("Update Staff Service Testing")
    class UpdateStaffTest {
        @Test
        @DisplayName("update staff successfully when valid data provided")
        void updateStaff_Success() {
            //GIVEN
            final Integer staffId = 1;
            //Check Staff Exist and return Existing Staff
            when(staffRepository.findById(staffId)).thenReturn(Optional.of(staff));
            // Check Position Exist and return Position
            when(positionRepository.findById(updatedStaffRequest.getPositionId())).thenReturn(Optional.of(updatedPosition));
            // Map Data from staffId, existingStaff, updatedStaffRequest, and updatedPosition to updatedStaff
            when(staffMapper.mergeForUpdateStaff(staffId, staff, updatedStaffRequest, updatedPosition)).thenReturn(updatedStaff);
            // save updatedStaff and return updatedStaff with Id
            when(staffRepository.save(updatedStaff)).thenReturn(updatedStaff);

            //WHEN
            final Integer staffIdAfterUpdate = staffService.update(staffId, updatedStaffRequest);

            //THEN
            assertNotNull(staffIdAfterUpdate);
            assertEquals(1, staffIdAfterUpdate);

            //VERIFY
            verify(staffRepository, times(1)).findById(staffId);
            verify(positionRepository, times(1)).findById(updatedStaffRequest.getPositionId());
            verify(staffMapper, times(1)).mergeForUpdateStaff(staffId, staff, updatedStaffRequest, updatedPosition);
            verify(staffRepository, times(1)).save(
                    argThat(updatedStaff ->
                            updatedStaff.getUser() != null &&
                                    updatedStaff.getUser().equals(user) &&
                                    updatedStaff.getPosition() != null &&
                                    updatedStaff.getPosition().equals(updatedPosition)
                    )
            );
        }

        @Test
        @DisplayName("throw exception when staff not found during update")
        void updateStaff_StaffNotFound_ThrowsException() {
            //GIVEN
            final Integer staffId = 5;
            // Check Staff Exist but return null
            when(staffRepository.findById(staffId)).thenReturn(Optional.empty());

            //WHEN & THEN
            assertThrows(EntityNotFoundException.class, () -> staffService.update(staffId, updatedStaffRequest));

            //VERIFY
            verify(staffRepository, times(1)).findById(staffId);
            verifyNoMoreInteractions(staffRepository);
            verifyNoInteractions(positionRepository, staffMapper);
        }

        @Test
        @DisplayName("throw exception when position not found during update")
        void updateStaff_PositionNotFound_ThrowsException() {
            //GIVEN
            final Integer staffId = 5;
            //Check Staff Exist and return Existing Staff
            when(staffRepository.findById(staffId)).thenReturn(Optional.of(staff));
            // Check Position Exist but return null
            when(positionRepository.findById(updatedStaffRequest.getPositionId())).thenReturn(Optional.empty());

            //WHEN & THEN
            assertThrows(EntityNotFoundException.class, () -> staffService.update(staffId, updatedStaffRequest));

            //VERIFY
            verify(staffRepository, times(1)).findById(staffId);
            verify(positionRepository, times(1)).findById(updatedStaffRequest.getPositionId());
            verifyNoMoreInteractions(staffRepository);
            verifyNoInteractions(staffMapper);
        }
    }

}