package com.zone.zissa.auth;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.UserRepository;

/**
 * The UserAccountDao Class.
 */
@Component
public class UserAccountDao implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountDao.class);

    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        try {
            User user = userRepo.findByUserName(username);

            if (user == null) {
                throw new UsernameNotFoundException("Username not found in DB");
            }
            return new org.springframework.security.core.userdetails.User(user.getUserName(), "", true, true, true,
                    true, getGrantedAuthorities(user.getRole()));

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            LOGGER.error("ERROR", e);
        }
        return null;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Role roleobj) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role;

        if (roleobj.getAdministration() == 1) {
            role = "ADMIN";
        } else {
            role = "USER";
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
