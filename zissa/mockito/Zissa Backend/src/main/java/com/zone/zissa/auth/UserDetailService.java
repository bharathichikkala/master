package com.zone.zissa.auth;

import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

/** The UserDetailService Class. */
@Component
public class UserDetailService implements UserDetailsContextMapper {

  /** The user repository. */
  @Autowired
  private UserRepository userRepository;

  /**
   * mapUserFromContext method.
   *
   * @param ctx the ctx
   * @param userName the user name
   * @param authorities the authorities
   * @return UserDetails
   */
  @Override
  public UserDetails mapUserFromContext(final DirContextOperations ctx,
      final String userName,
      final Collection<? extends GrantedAuthority> authorities) {
    Optional<User> user = userRepository.findByUserName(userName);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException("Username not found in DB");
    }
    Role role = user.get().getRole();
    return new org.springframework.security.core.userdetails.User(
        user.get().getUserName(), user.get().getEmail(), true, true, true, true,
        getGrantedAuthorities(role));
  }

  /**
   * Gets the granted authorities.
   *
   * @param roleObj the role obj
   * @return the granted authorities
   */
  private List<GrantedAuthority> getGrantedAuthorities(final Role roleObj) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    String role;

    if (roleObj.getAdministration() == 1) {
      role = "ADMIN";
    } else {
      role = "USER";
    }
    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
    return authorities;
  }

  /**
   * mapUserToContext method.
   *
   * @param user the user
   * @param ctx the ctx
   */
  @Override
  public void mapUserToContext(final UserDetails user,
      final DirContextAdapter ctx) {
    throw new UnsupportedOperationException();
  }
}
