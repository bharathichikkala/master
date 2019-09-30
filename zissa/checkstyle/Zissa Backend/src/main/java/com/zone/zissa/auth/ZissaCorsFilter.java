package com.zone.zissa.auth;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** The CORSFilter Class. */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ZissaCorsFilter implements Filter {

  /** Default constructor. */
  public ZissaCorsFilter() {
    // zero argument constructor
  }

  /** doFilter method. */
  @Override
  public void doFilter(final ServletRequest req, final ServletResponse res,
      final FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods",
        "POST, GET, OPTIONS, DELETE, PUT");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers",
        "x-requested-with, Authorization,Content-Type");
    response.setHeader("Access-Control-Expose-Headers", "Administration");
    HttpServletRequest request = (HttpServletRequest) req;
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(req, res);
    }
  }

  /** Destroy method. */
  @Override
  public void destroy() {
    // No implementation
  }

  /**
   * init method.
   * 
   * @param arg0 the arg0
   */
  @Override
  public void init(final FilterConfig arg0) throws ServletException {
    // No implementation
  }
}
