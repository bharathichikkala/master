package com.zone.zissa.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** The Class Config. */
@Configuration
@EnableJpaAuditing
public class Config {

  /**
   * Auditor provider.
   *
   * @return the auditor aware
   */
  @Bean
  public AuditorAware<String> auditorProvider() {
    return new SpringSecurityAuditorAware();
  }
}
