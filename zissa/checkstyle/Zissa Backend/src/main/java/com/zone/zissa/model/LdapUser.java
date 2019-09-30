package com.zone.zissa.model;

/**
 * The LdapUser class.
 */
public class LdapUser {

  /** The s AM account name. */
  private String sAMAccountName;

  /** The name. */
  private String name;

  /** The email. */
  private String email;

  /** The first name. */
  private String firstName;

  /** The last name. */
  private String lastName;

  /**
   * The getsAMAccountName method.
   *
   * @return sAMAccountName
   */
  public String getsAMAccountName() {
    return sAMAccountName;
  }

  /**
   * The setsAMAccountName method.
   *
   * @param accountName the new s AM account name
   */
  public void setsAMAccountName(final String accountName) {
    this.sAMAccountName = accountName;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param ldapUserName the new name
   */
  public void setName(final String ldapUserName) {
    this.name = ldapUserName;
  }

  /**
   * Gets the first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name.
   *
   * @param ldapUserFirstName the new first name
   */
  public void setFirstName(final String ldapUserFirstName) {
    this.firstName = ldapUserFirstName;
  }

  /**
   * Gets the last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name.
   *
   * @param ldapUserLastName the new last name
   */
  public void setLastName(final String ldapUserLastName) {
    this.lastName = ldapUserLastName;
  }

  /**
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email.
   *
   * @param emailId the new email
   */
  public void setEmail(final String emailId) {
    this.email = emailId;
  }
}
