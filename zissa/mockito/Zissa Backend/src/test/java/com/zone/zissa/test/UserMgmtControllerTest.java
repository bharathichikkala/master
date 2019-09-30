package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.UserMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import net.minidev.json.JSONObject;

/**
 * The Class UserMgmtControllerTest.
 */
public class UserMgmtControllerTest extends ZissaApplicationTest {

  /** The user mgmt controller. */
  @InjectMocks
  private UserMgmtController userMgmtController;

  /** The role. */
  @InjectMocks
  private Role role;


  /** The user. */
  @InjectMocks
  private User user;

  /** The user repo. */
  @Mock
  private UserRepository userRepo;

  /** The user service impl. */
  @Mock
  private UserService userService;

  /** The Constant username. */
  private static final String username = "BathiyaT";

  /** The Constant password. */
  private static final String password = "Zone@789";

  /**
   * Adds the user test.
   *
   * @throws JSONException the JSON exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addUserTest() throws JSONException {

    JSONObject addUser = new JSONObject();
    user = new User();
    user.setUserName("zoness");
    user.setRole(role);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));
    when(userService.addUser(addUser.toJSONString())).thenReturn(user);

    ServiceResponse<User> userResponse =
        userMgmtController.addUser(addUser.toJSONString());
    assertThat(201, is(userResponse.getStatus()));

  }

  /**
   * Update user.
   *
   * @throws JSONException the JSON exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateUser() throws JSONException {
    JSONObject userData = new JSONObject();
    user = new User();
    user.setUserName("zoness");
    user.setRole(role);
    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));
    when(userService.updateUser(userData.toJSONString())).thenReturn(user);
    ServiceResponse<User> userResponse =
        userMgmtController.updateUser(userData.toJSONString());
    assertThat(200, is(userResponse.getStatus()));
  }

  /**
   * Gets the all users test.
   *
   * @return the all users test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllUsersTest() {
    List<User> attributeList = new ArrayList<>();
    when(userService.getAllUsers()).thenReturn(attributeList);
    ServiceResponse<List<User>> userResponse = userMgmtController.getAllUsers();
    assertThat(RestApiMessageConstants.GETTING_USER,
        is(userResponse.getMessage()));
  }

  /**
   * Delete user.
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteUser() {
    user = new User();
    user.setUserID(1);
    userMgmtController.deleteUser(user.getUser_ID());
    verify(userService, times(1)).deleteUser(user.getUser_ID());
  }

}
