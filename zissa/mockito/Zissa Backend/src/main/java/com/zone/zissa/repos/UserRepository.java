package com.zone.zissa.repos;

import com.zone.zissa.model.User;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

/** The UserRepository Interface for the User database table. */
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Find by user name method.
   *
   * @param userName the user name
   * @return User
   */
  Optional<User> findByUserName(String userName);

  /**
   * Find by email method.
   *
   * @param email the email
   * @return User
   */
  Optional<User> findByEmail(String email);

  /**
   * Find by userId method.
   *
   * @param userId the user id
   * @return User
   */
  Optional<User> findByUserID(int userId);
}
