package com.zone.zissa.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/** The Class SecurityFilter. */
public class SecurityFilter extends OncePerRequestFilter {

  /**
   * The doFilterInternal method.
   *
   * @param httpRequest the http request
   * @param httpResponse the http response
   * @param filterChain the filter chain
   * @throws ServletException the servlet exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  protected void doFilterInternal(final HttpServletRequest httpRequest,
      final HttpServletResponse httpResponse, final FilterChain filterChain)
      throws ServletException, IOException {
    org.springframework.security.core.userdetails.User user =
        (org.springframework.security.core.userdetails.User) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();
    Collection<GrantedAuthority> authoritiesList = user.getAuthorities();
    ArrayList<GrantedAuthority> newList = new ArrayList<>(authoritiesList);
    if (newList.get(0).toString().equalsIgnoreCase("ROLE_USER")) {
      httpResponse.setHeader("Administration", "0");
    } else {
      httpResponse.setHeader("Administration", "1");
    }
    filterChain.doFilter(httpRequest, httpResponse);
  }
}
