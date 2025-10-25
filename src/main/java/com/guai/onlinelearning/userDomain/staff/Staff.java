package com.guai.onlinelearning.userDomain.staff;

import com.guai.onlinelearning.base.BaseEntity;
import com.guai.onlinelearning.userDomain.Gender;
import com.guai.onlinelearning.userDomain.position.Position;
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
@Table(name = "staffs")
@EntityListeners(AuditingEntityListener.class)
public class Staff extends BaseEntity {

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true, updatable = false)
    private String email;
    @Column(nullable = false, unique = true)
    private String nrc;
    private String dateOfBirth;
    private String phoneNumber;
    private String address;
    private Gender gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
