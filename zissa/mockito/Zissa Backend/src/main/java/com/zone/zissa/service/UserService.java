package com.zone.zissa.service;

import com.zone.zissa.model.User;
import com.zone.zissa.response.ServiceResponse;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/** The Interface UserService. */
public interface UserService {

  /**
   * Adds the User.
   *
   * @param userData the user data
   * @return User
   * @throws JSONException the JSON exception
   */
  User addUser(@Valid @RequestBody String userData) throws JSONException;

  /**
   * Update User.
   *
   * @param userData the user data
   * @return User
   * @throws JSONException the JSON exception
   */
  User updateUser(@Valid @RequestBody String userData) throws JSONException;

  /**
   * Delete User.
   *
   * @param userId the user id
   * @return ServiceResponseBean
   */
  ServiceResponse deleteUser(@NotNull @PathVariable Integer userId);

  /**
   * Gets all users.
   *
   * @return the list
   */
  List<User> getAllUsers();
}
