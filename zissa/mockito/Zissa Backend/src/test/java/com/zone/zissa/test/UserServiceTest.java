package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import net.minidev.json.JSONObject;

/**
 * The Class UserTest.
 */
public class UserServiceTest extends ZissaApplicationTest {

  /** The user. */
  @InjectMocks
  private User user;

  /** The role. */
  @InjectMocks
  private Role role;

  /** The user service impl. */
  @InjectMocks
  private UserServiceImpl userServiceImpl;

  /** The user repo. */
  @Mock
  private UserRepository userRepo;

  /** The role repo. */
  @Mock
  private RoleRepository roleRepo;


  /**
   * Inits the.
   */
  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }


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

    addUser.put("sAMAccountName", "zones");
    addUser.put("email", "zones@zone24x7.com");
    addUser.put("firstName", "Bathiya");
    addUser.put("lastName", "Tennakoon");
    addUser.put("role_ID", "1");

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserName("zoness");
    user.setRole(role);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(Optional.of(role));
    when(userRepo.save(any(User.class))).thenReturn(user);
    User userResponse = userServiceImpl.addUser(addUser.toJSONString());
    assertThat(user.getUserName(), is(userResponse.getUserName()));

  }

  /**
   * Adds the user failure test.
   *
   * @throws JSONException the JSON exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addUserFailureTest() throws JSONException {

    JSONObject addUser = new JSONObject();

    addUser.put("sAMAccountName", "BathiyaT");
    addUser.put("email", "zones@zone24x7.com");
    addUser.put("firstName", "Bathiya");
    addUser.put("lastName", "Tennakoon");
    addUser.put("role_ID", "1");

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserName("BathiyaT");
    user.setRole(role);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(Optional.of(role));

    when(userServiceImpl.addUser(addUser.toJSONString()));
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
    List<User> userList = new ArrayList<User>();
    user = new User();
    user.setUserName("BathiyaT");
    user.setRole(role);
    userList.add(user);
    when(userRepo.findAll()).thenReturn(userList);
    List<User> list = userServiceImpl.getAllUsers();
    assertThat(userList.get(0).getUserName(), is(list.get(0).getUserName()));
    assertThat(1, is(list.size()));
  }

  /**
   * Update user test.
   *
   * @throws JSONException the JSON exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateUserTest() throws JSONException {

    JSONObject userData = new JSONObject();
    userData.put("user_ID", "1");
    userData.put("sAMAccountName", "Zissa");
    userData.put("email", "bathiyat@zone24x7.com");
    userData.put("firstName", "Bathiya");
    userData.put("lastName", "Tennakoon");
    userData.put("role_ID", 1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setRole(role);

    when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(Optional.of(role));

    when(userRepo.save(any(User.class))).thenReturn(user);

    User userResponse = userServiceImpl.updateUser(userData.toJSONString());

    assertThat(userData.get("email"), is(userResponse.getEmail()));

  }

  /**
   * Update user failure test.
   *
   * @throws JSONException the JSON exception
   */
  @Test(expected = DataNotFoundException.class)
  @WithMockUser(username = username, password = password)
  public void updateUserFailureTest() throws JSONException {

    JSONObject userData = new JSONObject();
    userData.put("user_ID", "1");
    userData.put("sAMAccountName", "Zissa");
    userData.put("email", "bathiyat@zone24x7.com");
    userData.put("firstName", "Bathiya");
    userData.put("lastName", "Tennakoon");
    userData.put("role_ID", 1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(100);
    user.setEmail("bathiyat@zone24x7.com");
    user.setRole(role);

    when(userRepo.findByEmail("bathiyatk@zone24x7.com"))
        .thenReturn(Optional.of(user));
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(Optional.of(role));

    when(userServiceImpl.updateUser(userData.toJSONString()));
  }

  /**
   * Adds the user failure test by invalid data.
   *
   * @throws Exception the exception
   */
  @Test(expected = JSONException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addUserFailureTestByInvalidData() throws Exception {

    JSONObject addUser = new JSONObject();

    addUser.put("sAMAccountName", "BathiyaT");
    addUser.put("Email", "zones@zone24x7.com");
    addUser.put("firstName", "Bathiya");
    addUser.put("lastName", "Tennakoon");
    addUser.put("role_ID", "1");
    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserName("zoness");
    user.setRole(role);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(Optional.of(role));
    when(userServiceImpl.addUser(addUser.toJSONString()))
        .thenReturn(Optional.of(user).get());


  }


  /**
   * Delete user test.
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteUserTest() {
    user = new User();
    user.setUserID(1);
    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));
    userServiceImpl.deleteUser(1);
    verify(userRepo, times(1)).deleteById(1);

  }

  /**
   * Delete user failure test.
   */
  @Test(expected = DataNotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteUserFailureTest() {
    user = new User();
    user.setUserID(1);
    when(userServiceImpl.deleteUser(0));
  }

  /**
   * Adds the user failure by json test.
   *
   * @throws Exception the exception
   */
  @Test(expected = JSONException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addUserFailureByJsonTest() throws Exception {

    JSONObject addUser = new JSONObject();

    addUser.put("sAMAccountName", "BathiyaT");
    addUser.put("Email", "zones@zone24x7.com");
    addUser.put("firstName", "Bathiya");
    addUser.put("lastName", "Tennakoon");
    addUser.put("role_ID", "1");
    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserName("BathiyaT");
    user.setRole(role);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(Optional.of(role));

    when(userServiceImpl.addUser(addUser.toJSONString()))
        .thenThrow(new JSONException("invalid json format"));

  }

}
