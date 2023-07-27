package com.devstack.pos.service.impl;

import com.devstack.pos.entity.ApplicationUser;
import com.devstack.pos.entity.UserRoleHasUser;
import com.devstack.pos.repo.UserRepo;
import com.devstack.pos.repo.UserRoleHasUserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.devstack.pos.security.ApplicationUserRoles.*;

@Service
public class ApplicationUserServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    private final UserRoleHasUserRepo userRoleHasUserRepo;

    public ApplicationUserServiceImpl(UserRepo userRepo, UserRoleHasUserRepo userRoleHasUserRepo) {
        this.userRepo = userRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var selectedUser = userRepo.findUserByEmail(username);
        if(selectedUser.isEmpty()){
            throw new UsernameNotFoundException(
                    String.format("Username %s not found",username)
            );
        }
        List<UserRoleHasUser> userRoles =
                userRoleHasUserRepo.findByUserId(selectedUser.get().getUserId());

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRoleHasUser uRole : userRoles) {
            if(uRole.getUserRole().equals("USER")){
                grantedAuthorities.addAll(USER.getSimpleGrantedAuthorities());
            }
            if(uRole.getUserRole().equals("ADMIN")){
                grantedAuthorities.addAll(ADMIN.getSimpleGrantedAuthorities());
            }
            if(uRole.getUserRole().equals("MANAGER")){
                grantedAuthorities.addAll(MANAGER.getSimpleGrantedAuthorities());
            }
        }
        ApplicationUser user = new ApplicationUser(
                grantedAuthorities,
                selectedUser.get().getPassword(),
                selectedUser.get().getEmail(),
                selectedUser.get().isAccountNonExpired(),
                selectedUser.get().isAccountNonLocked(),
                selectedUser.get().isCredentialsNonExpired(),
                selectedUser.get().isEnabled()
        );
        return user;
    }
}
