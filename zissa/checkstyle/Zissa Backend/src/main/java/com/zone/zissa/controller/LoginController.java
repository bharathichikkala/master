package com.zone.zissa.controller;

import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** The LoginController Class. */
@RestController
public class LoginController {

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /**
   * Login controller.
   *
   * @return ServiceResponse
   */
  @GetMapping("/")
  public ServiceResponse<com.zone.zissa.model.User> index() {
    ServiceResponse<com.zone.zissa.model.User> response =
        new ServiceResponse<>();
    User user = (User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    Optional<com.zone.zissa.model.User> userObject =
        userRepo.findByUserName(user.getUsername());

    if (userObject.isPresent()) {
      response.setData(userObject.get());
    }
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.LOGIN_SUCCESS);
    return response;
  }
}
