package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.LoginController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;

public class LoginControllerTest extends ZissaApplicationTest {
  /** The login controller. */
  @InjectMocks
  private LoginController loginController;

  /** The user repo. */
  @Mock
  private UserRepository userRepo;

  /** The user. */
  @InjectMocks
  private User user;

  /** The role. */
  @InjectMocks
  private Role role;

  /** The Constant username. */
  private static final String username = "BathiyaT";


  /** The Constant password. */
  private static final String password = "Zone@789";

  /**
   * Login test.
   *
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void LoginTest() throws Exception {
    user.setUserID(1);
    user.setUserName(username);
    user.setEmail("bathiyat@zone24x7.com");
    when(userRepo.findByUserName(username)).thenReturn(Optional.of(user));
    ServiceResponse<User> response = loginController.index();
    assertThat(200, is(response.getStatus()));
    assertThat(RestApiMessageConstants.LOGIN_SUCCESS,
        is(response.getMessage()));
  }

}
