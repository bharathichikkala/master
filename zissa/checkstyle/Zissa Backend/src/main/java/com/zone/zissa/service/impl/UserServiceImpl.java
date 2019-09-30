package com.zone.zissa.service.impl;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.UserService;
import java.util.List;
import java.util.Optional;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The UserServiceImpl class. */
@Service
public class UserServiceImpl implements UserService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(UserServiceImpl.class);

  /** The role repo. */
  @Autowired
  private RoleRepository roleRepo;

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /**
   * Add user service implementation.
   *
   * @param userData the user data
   * @return User
   * @throws JSONException the JSON exception
   */
  @Override
  public User addUser(final String userData) throws JSONException {

    LOGGER.info("Add new User Service implementation");
    JSONObject object = new JSONObject(userData);
    User user = new User();
    String userName = object.getString("sAMAccountName");
    user.setUserName(userName);
    String email = object.getString("email");
    user.setEmail(email);
    String firstName = object.getString("firstName");
    user.setFirstName(firstName);
    String lastName = object.getString("lastName");
    user.setLastName(lastName);
    int roleId = object.getInt("role_ID");
    Optional<Role> roleObject = roleRepo.findByroleID(roleId);
    if (roleObject.isPresent()) {
      user.setRole(roleObject.get());
    }
    user.setActiveStatus(1);
    User userObject = null;
    Optional<User> userExists = userRepo.findByUserName(userName);
    if (!userExists.isPresent()) {
      userObject = userRepo.save(user);
    } else {

      throw new ConflictException(RestApiMessageConstants.USER_EXISTS);
    }
    return userObject;
  }

  /**
   * Update user service implementation.
   *
   * @param userData the user data
   * @return User
   * @throws JSONException the JSON exception
   */
  @Override
  public User updateUser(final String userData) throws JSONException {

    LOGGER.info("Update User Service implementation");
    User user = new User();
    User userObject = null;
    JSONObject object = new JSONObject(userData);
    String userName = object.getString("sAMAccountName");
    String email = object.getString("email");
    String firstName = object.getString("firstName");
    String lastName = object.getString("lastName");
    int userId = object.getInt("user_ID");
    int roleId = object.getInt("role_ID");
    Optional<Role> roleObj = roleRepo.findByroleID(roleId);
    Optional<User> userExists = userRepo.findByEmail(email);
    if (!userExists.isPresent()) {
      throw new DataNotFoundException(RestApiMessageConstants.UPDATE_USER);
    } else {
      user.setUserName(userName);
      user.setEmail(email);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      if (roleObj.isPresent()) {
        user.setRole(roleObj.get());
      }
      user.setActiveStatus(1);
      user.setUserID(userId);
      user.setCreatedDate(userExists.get().getCreatedDate());
      user.setCreatedBy(userExists.get().getCreatedBy());

      userObject = userRepo.save(user);
    }
    return userObject;
  }

  /**
   * Gets all users service implementation.
   *
   * @return the list
   */
  @Override
  public List<User> getAllUsers() {

    LOGGER.info("Get all Users Service implementation");
    List<User> userList = userRepo.findAll();
    userList.stream().map(User::getRole).forEachOrdered(role -> {
      role.setPermissions(null);
      role.setDefaultCategory(null);
    });

    return userList;
  }

  /**
   * Delete user service implementation.
   *
   * @param userID the user ID
   * @return ServiceResponseBean
   */
  @Override
  public ServiceResponse deleteUser(final Integer userID) {

    LOGGER.info("Delete User Service implementation");
    ServiceResponse response = new ServiceResponse();
    Optional<User> userExists = userRepo.findByUserID(userID);
    if (userExists.isPresent()) {
      userRepo.deleteById(userID);
    } else {

      throw new DataNotFoundException(RestApiMessageConstants.DELETE_USER);
    }
    return response;
  }
}
