package com.zone.zissa.auth;

import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/** The UserAccountDao Class. */
@Component
public class UserAccountDao implements UserDetailsService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(UserAccountDao.class);

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /**
   * The loadUserByUsername method.
   *
   * @param username the username
   * @return UserDetails
   */
  @Override
  public UserDetails loadUserByUsername(final String username) {

    try {
      Optional<User> user = userRepo.findByUserName(username);

      if (!user.isPresent()) {
        throw new UsernameNotFoundException("Username not found in DB");
      }
      return new org.springframework.security.core.userdetails.User(
          user.get().getUserName(), "", true, true, true, true,
          getGrantedAuthorities(user.get().getRole()));

    } catch (Exception e) {
      LOGGER.info(e.getMessage());
      LOGGER.error("ERROR", e);
    }
    return null;
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
}
