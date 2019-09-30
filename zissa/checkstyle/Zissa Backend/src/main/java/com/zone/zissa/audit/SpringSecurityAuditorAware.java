package com.zone.zissa.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

/** The Class SpringSecurityAuditorAware. */
public class SpringSecurityAuditorAware implements AuditorAware<String> {
  /**
   * getCurrentAuditor method.
   * 
   * @return String
   */
  @Override
  public Optional<String> getCurrentAuditor() {
    org.springframework.security.core.userdetails.User user =
        (org.springframework.security.core.userdetails.User) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();
    return Optional.of(user.getUsername());
  }
}
