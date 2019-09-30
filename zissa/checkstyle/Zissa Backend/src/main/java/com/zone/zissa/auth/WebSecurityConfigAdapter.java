package com.zone.zissa.auth;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/** The WebSecurityConfigAD Class. */
@Configuration
@EnableWebSecurity
@Profile("ldap")
public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

  /** The ldap domain. */
  @Value("${ldap-domain}")
  private String ldapDomain;

  /** The ldap url. */
  @Value("${ldap-baseurl}")
  private String ldapUrl;

  /** The realm. */
  private static String realm = "MY_TEST_REALM";

  /** The user detail service. */
  @Autowired
  private UserDetailService userDetailService;

  /**
   * The propertyConfigInDev method.
   *
   * @return PropertySourcesPlaceholderConfigurer
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  /**
   * configure method.
   *
   * @param authManagerBuilder the auth manager builder
   * @throws Exception the exception
   */
  @Override
  protected void configure(
      final AuthenticationManagerBuilder authManagerBuilder) throws Exception {
    authManagerBuilder.authenticationProvider(
        activeDirectoryLdapAuthenticationProvider(userDetailService));
  }

  /**
   * The authenticationManager method.
   *
   * @return AuthenticationManager
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(Arrays
        .asList(activeDirectoryLdapAuthenticationProvider(userDetailService)));
  }

  /**
   * The activeDirectoryLdapAuthenticationProvider method.
   *
   * @param userDetailServices the user detail services
   * @return AuthenticationProvider
   */
  @Bean
  public AuthenticationProvider activeDirectoryLdapAuthenticationProvider(
      final UserDetailService userDetailServices) {
    ActiveDirectoryLdapAuthenticationProvider provider =
        new ActiveDirectoryLdapAuthenticationProvider(ldapDomain, ldapUrl);
    provider.setConvertSubErrorCodesToExceptions(true);
    provider.setUseAuthenticationRequestCredentials(true);
    provider.setUserDetailsContextMapper(this.userDetailService);
    return provider;
  }

  /**
   * The configure method.
   *
   * @param http the http
   * @throws Exception the exception
   */
  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.cors().and().authorizeRequests()
        .antMatchers("/v1/attributes/**", "/v1/categories/**", "/v1/roles/**",
            "/v1/users/**")
        .hasAuthority("ROLE_ADMIN").anyRequest().authenticated().and()
        .httpBasic().realmName(realm)
        .authenticationEntryPoint(getBasicAuthEntryPoint()).and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(new ZissaCorsFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new SecurityFilter(), BasicAuthenticationFilter.class)
        .csrf().disable();
  }

  /**
   * The configure method.
   *
   * @param web the web
   * @throws Exception the exception
   */
  @Override
  public void configure(final WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
        "/swagger-resources", "/configuration/security", "/webjars/**",
        "/swagger-resources/configuration/ui", "/swagger-ui.html",
        "/swagger-resources/configuration/security",
        "/v1/categories/{categoryID}/attributes", "/v1/allocations/projects");
  }

  /**
   * Gets the basic auth entry point.
   *
   * @return the basic auth entry point
   */
  @Bean
  public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
    return new CustomBasicAuthenticationEntryPoint();
  }
}


/** The WebSecurityConfigADLocal Class. */
@Configuration
@EnableWebSecurity
@Profile("localldap")
class WebSecurityConfigAdapterLocal extends WebSecurityConfigurerAdapter {
  /** The ldap domain. */
  @Value("${ldap-domain}")
  private String ldapDomain;
  /** The ldap url. */
  @Value("${ldap-baseurl}")
  private String ldapUrl;
  /** The realm. */
  private static String realm = "MY_TEST_REALM";
  /** The user detail service. */
  @Autowired
  private UserDetailService userDetailService;

  /**
   * contextSource method.
   * 
   * @return DefaultSpringSecurityContextSource
   */
  @Bean
  public DefaultSpringSecurityContextSource contextSource() {
    return new DefaultSpringSecurityContextSource(
        Collections.singletonList(ldapUrl), ldapDomain);
  }

  /**
   * configure method.
   * 
   * @param authManagerBuilder the authManagerBuilder
   * @throws Exception the Exception
   */
  @Override
  protected void configure(
      final AuthenticationManagerBuilder authManagerBuilder) throws Exception {
    authManagerBuilder.ldapAuthentication().userDnPatterns("uid={0},ou=users")
        .userSearchBase("ou=users").userSearchFilter("uid={0}")
        .contextSource(contextSource()).passwordCompare()
        .passwordEncoder(new LdapShaPasswordEncoder())
        .passwordAttribute("userPassword").and()
        .userDetailsContextMapper(userDetailService);
  }

  /**
   * The configure method.
   *
   * @param http the http
   * @throws Exception the Exception
   */
  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.cors().and().authorizeRequests()
        .antMatchers("/v1/attributes/**", "/v1/categories/**", "/v1/roles/**",
            "/v1/users/**")
        .hasAuthority("ROLE_ADMIN").anyRequest().authenticated().and()
        .httpBasic().realmName(realm)
        .authenticationEntryPoint(getBasicAuthEntryPoint()).and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(new ZissaCorsFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new SecurityFilter(), BasicAuthenticationFilter.class)
        .csrf().disable();
  }

  /**
   * The configure method.
   *
   * @param web the web
   * @throws Exception the Exception
   */
  @Override
  public void configure(final WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
        "/swagger-resources", "/configuration/security", "/webjars/**",
        "/swagger-resources/configuration/ui", "/swagger-ui.html",
        "/swagger-resources/configuration/security",
        "/v1/categories/{categoryID}/attributes", "/v1/allocations/projects",
        "/actuator/", "/actuator/health", "/actuator/metrics/**",
        "/actuator/auditevents");
  }

  /**
   * Gets the basic auth entry point.
   *
   * @return the basic auth entry point
   */
  @Bean
  public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
    return new CustomBasicAuthenticationEntryPoint();
  }
}
