package com.guai.onlinelearning.userDomain.learner;

import com.guai.onlinelearning.base.BaseEntity;
import com.guai.onlinelearning.userDomain.Gender;
import com.guai.onlinelearning.userDomain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "learners")
@EntityListeners(AuditingEntityListener.class)
public class Learner extends BaseEntity {

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true, updatable = false)
    private String email;
    private String dateOfBirth;
    private String phoneNumber;
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
