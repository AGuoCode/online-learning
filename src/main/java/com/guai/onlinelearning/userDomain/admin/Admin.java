package com.guai.onlinelearning.userDomain.admin;

import com.guai.onlinelearning.base.BaseEntity;
import com.guai.onlinelearning.userDomain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "admins")
@EntityListeners(AuditingEntityListener.class)
public class Admin extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false, unique = true, updatable = false)
    private String email;
    @Column(nullable = false)
    private String article;
    @Column(nullable = false)
    private String professional;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String bio;
    private List<String> certificates;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
