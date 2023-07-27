package com.devstack.pos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRole {
    @Id
    @Column(name = "role_id", length = 45)
    private String roleId;
    @Column(name = "role_name", length = 45, unique = true)
    private String roleName;
    @Column(name = "description", length = 250)
    private String description;

    @OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
    private Set<UserRoleHasUser> userRoleHasUsers;
}
