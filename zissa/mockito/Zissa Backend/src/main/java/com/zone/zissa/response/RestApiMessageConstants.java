package com.zone.zissa.response;

/**
 * The Class RestAPIMessageConstants.
 */
public final class RestApiMessageConstants {

  /**
   * Instantiates a new rest API message constants.
   */
  private RestApiMessageConstants() {
    throw new IllegalAccessError("Utility class");
  }

  /** Allocation module. */
  public static final String NO_ALLOCATION_CONTENT =
      "No Allocation details available for given resource id";

  /** The Constant ALLOCATION_ACCESS_DENIED. */
  public static final String ALLOCATION_ACCESS_DENIED =
      "Permission not exist for getting allocation details by resource id";

  /** The Constant RESOURCE_NOT_FOUND. */
  public static final String RESOURCE_NOT_FOUND = "Resource not exists";

  /** The Constant GET_ALL_ALLOCATIONS. */
  public static final String GET_ALL_ALLOCATIONS =
      "Getting Allocation History by Resource is successful";

  /** The Constant GETTING_ALLOCATION_HISTORY_FAILURE. */
  public static final String GETTING_ALLOCATION_HISTORY_FAILURE =
      "Failed in getting Allocation History Details by Resource";

  /** The Constant GET_ALLOCATION_DETAILS_BY_RESOURCE_ERROR. */
  public static final String GET_ALLOCATION_DETAILS_BY_RESOURCE_ERROR =
      "Get Allocation Details By Resource throw an exception";

  /** The Constant GET_RESOURCE_WITH_SEARCHTERM. */
  public static final String GET_RESOURCE_WITH_SEARCHTERM =
      "Getting all the allocation resources details "
          + "with search term is successful";

  /** The Constant GET_RESOURCE_SEARCH_ERROR. */
  public static final String GET_RESOURCE_SEARCH_ERROR =
      "Getting allocation resources by searchTerm throw an exception";

  /** The Constant GET_RESOURCE_WITH_SEARCH_FAILURE. */
  public static final String GET_RESOURCE_WITH_SEARCH_FAILURE =
      "Failed in getting allocation Resources details with search term details";

  /** The Constant GET_PROJECTS. */
  public static final String GET_PROJECTS =
      "Getting All projects is successful";

  /** The Constant PROJECTS_GETTING_ERROR. */
  public static final String PROJECTS_GETTING_ERROR =
      "Getting all projects throw an exception";

  /** The Constant PROJECTS_GETTING_FAILURE. */
  public static final String PROJECTS_GETTING_FAILURE =
      "Failed to get all projects";

  /** User module. */
  public static final String ADD_USER = "User Created Successfully";

  /** The Constant USER_ADD_ERROR. */
  public static final String USER_ADD_ERROR = "Adding user throw an exception";

  /** The Constant USER_ADD_FAILURE. */
  public static final String USER_ADD_FAILURE = "Failed in User Creation";

  /** The Constant USER_EXISTS. */
  public static final String USER_EXISTS = "User Already Exists";

  /** The Constant UPDATE_USER. */
  public static final String UPDATE_USER = "User updated successfully";

  /** The Constant USER_UPDATE_ERROR. */
  public static final String USER_UPDATE_ERROR =
      "Updating user throw an exception";

  /** The Constant USER_UPDATE_FAILURE. */
  public static final String USER_UPDATE_FAILURE = "Failed in User Updation";

  /** The Constant GETTING_USER. */
  public static final String GETTING_USER =
      "Getting all the Users is successful";

  /** The Constant GETTING_USER_ERROR. */
  public static final String GETTING_USER_ERROR =
      "Get all users throw an exception";

  /** The Constant GETTING_USER_FAILURE. */
  public static final String GETTING_USER_FAILURE = "Failed in getting users";

  /** The Constant DELETE_USER. */
  public static final String DELETE_USER = "User deleted successfully";

  /** The Constant USER_DELETE_ERROR. */
  public static final String USER_DELETE_ERROR =
      "Deleting user throw an exception";

  /** The Constant USER_DELETION_FAILURE. */
  public static final String USER_DELETION_FAILURE = "Failed in User Deletion";

  /** The Constant USER_DELETION_EXCEPTION. */
  public static final String USER_DELETION_EXCEPTION =
      "Cannot delete a parent row: a foreign key constraint fails";

  /** Role module. */
  public static final String ROLE_EXISTS = "Role Already Exists";

  /** The Constant ROLE_NAME_EXISTS. */
  public static final String ROLE_NAME_EXISTS = "Role name Already Exists";

  /** The Constant ADD_ROLE. */
  public static final String ADD_ROLE = "Role Created Successfully";

  /** The Constant ROLE_ADDING_ERROR. */
  public static final String ROLE_ADDING_ERROR =
      "Adding role throw an exception";

  /** The Constant ROLE_ADDING_FAILURE. */
  public static final String ROLE_ADDING_FAILURE = "Failed in Role Creation";

  /** The Constant ROLE_NOT_EXISTS. */
  public static final String ROLE_NOT_EXISTS = "Role not Exists";

  /** The Constant UPDATE_ROLE. */
  public static final String UPDATE_ROLE = "Role Updated Successfully";

  /** The Constant ROLE_UPDATION_FAILURE. */
  public static final String ROLE_UPDATION_FAILURE = "Failed in Role Updation";

  /** The Constant GETTING_ALL_PERMISSIONS_BY_ROLE. */
  public static final String GETTING_ALL_PERMISSIONS_BY_ROLE =
      "Getting Role Permission Details is successful";

  /** The Constant GETTING_ALL_PERMISSIONS_BY_ROLE_FAILURE. */
  public static final String GETTING_ALL_PERMISSIONS_BY_ROLE_FAILURE =
      "Failed in Getting Role Permission Details";

  /** The Constant DELETE_ROLE. */
  public static final String DELETE_ROLE = "Role deleted successfully";

  /** The Constant DELETE_ROLE_ERROR. */
  public static final String DELETE_ROLE_ERROR =
      "Deleting role throw an exception";

  /** The Constant ROLE_DELETION_FAILURE. */
  public static final String ROLE_DELETION_FAILURE = "Failed in Role Deletion";

  /** The Constant ROLE_DELETION_EXCEPTION. */
  public static final String ROLE_DELETION_EXCEPTION =
      "Cannot delete a parent row: a foreign key constraint fails";

  /** The Constant UPDATE_ROLE_ERROR. */
  public static final String UPDATE_ROLE_ERROR =
      "Updating role throw an exception";

  /** The Constant GETTING_PERMISSIONS_BY_ROLE_ERROR. */
  public static final String GETTING_PERMISSIONS_BY_ROLE_ERROR =
      "Getting all permissions by role throw an exception";

  /** The Constant NO_PERMISSION_FOR_CATEGORY_ROLE. */
  public static final String NO_PERMISSION_FOR_CATEGORY_ROLE =
      "No Permissions available for the given category and Role";

  /** The Constant GETTING_ROLES. */
  public static final String GETTING_ROLES =
      "Getting all the Roles is successful";

  /** The Constant GETTING_ROLES_ERROR. */
  public static final String GETTING_ROLES_ERROR =
      "Getting all the Roles throw exception";

  /** The Constant GETTING_ROLES_FAILURE. */
  public static final String GETTING_ROLES_FAILURE = "Failed in Getting Roles";

  /** The Constant GETTING_PERMISSIONS_BY_ROLE_CATEGORY. */
  public static final String GETTING_PERMISSIONS_BY_ROLE_CATEGORY =
      "Getting Permission Details is successful";

  /** The Constant GETTING_PERMISSIONS_BY_ROLE_CATEGORY_FAILURE. */
  public static final String GETTING_PERMISSIONS_BY_ROLE_CATEGORY_FAILURE =
      "Failed in Getting Permission Details";

  /** The Constant GETTING_PERMISSIONS_BY_ROLE_CATEGORY_ERROR. */
  public static final String GETTING_PERMISSIONS_BY_ROLE_CATEGORY_ERROR =
      "Getting all permissions by role and category throw an exception";

  /** Ldap module. */
  public static final String GETTING_LDAP_USERS =
      "Getting all the Ldap Users is Successful";

  /** The Constant GETTING_LDAP_USERS_ERROR. */
  public static final String GETTING_LDAP_USERS_ERROR =
      "getting all ldap users throw an exception";

  /** The Constant GETTING_LDAP_USERS_FAILURE. */
  public static final String GETTING_LDAP_USERS_FAILURE =
      "Failed in Getting all the Ldap Users";

  /** The Constant LDAP_USER_NOT_EXISTS. */
  public static final String LDAP_USER_NOT_EXISTS =
      "No users exists with the search string entered";

  /** The Constant LDAP_USER_BY_SEARCH_STRING. */
  public static final String LDAP_USER_BY_SEARCH_STRING =
      "Getting all the Ldap Users by Search string is Successful";

  /** The Constant LDAP_USER_BY_SEARCH_STRING_FAILURE. */
  public static final String LDAP_USER_BY_SEARCH_STRING_FAILURE =
      "Failed in Getting all the Ldap Users by Search string";

  /** The Constant LDAP_SEARCH_ERROR. */
  public static final String LDAP_SEARCH_ERROR =
      "Getting all Ldap Users by Search string throw an exception";

  /** Login module. */
  public static final String LOGIN_SUCCESS = "Login Successful";

  /** The Constant LOGIN_ERROR. */
  public static final String LOGIN_ERROR = "Login throw an exception";

  /** The Constant LOGIN_FAILURE. */
  public static final String LOGIN_FAILURE = "Bad Credentials";

  /** Attribute module. */
  public static final String ATTRIBUTE_EXISTS = "Attribute already exists";

  /** The Constant ATTRIBUTE_NAME_EXISTS. */
  public static final String ATTRIBUTE_NAME_EXISTS =
      "Attribute name already exists";

  /** The Constant ADD_ATTRIBUTE. */
  public static final String ADD_ATTRIBUTE = "Attribute Created Successfully";

  /** The Constant ADD_ATTRIBUTE_FAILURE. */
  public static final String ADD_ATTRIBUTE_FAILURE =
      "Failed in Attribute Creation";

  /** The Constant ADDING_ATTRIBUTE_ERROR. */
  public static final String ADDING_ATTRIBUTE_ERROR =
      "Adding attribute throw an exception";

  /** The Constant UPDATE_ATTRIBUTE. */
  public static final String UPDATE_ATTRIBUTE =
      "Updated Attribute successfully";

  /** The Constant ATTRIBUTE_UPDATE_CONFLICT. */
  public static final String ATTRIBUTE_UPDATE_CONFLICT =
      "Attribute values is already in use";

  /** The Constant ATTRIBUTE_UPDATION_ERROR. */
  public static final String ATTRIBUTE_UPDATION_ERROR =
      "Updating attribute throw an exception";

  /** The Constant ATTRIBUTE_UPDATION_FAILURE. */
  public static final String ATTRIBUTE_UPDATION_FAILURE =
      "Failed to update attribute";

  /** The Constant DELETE_ATTRIBUTE. */
  public static final String DELETE_ATTRIBUTE =
      "Attribute is deleted successfully";

  /** The Constant ATTRIBUTE_DELETION_ERROR. */
  public static final String ATTRIBUTE_DELETION_ERROR =
      "Deleting attribute throw an exception";

  /** The Constant ATTRIBUTE_DELETION_FAILURE. */
  public static final String ATTRIBUTE_DELETION_FAILURE =
      "Failed in Attribute Deletion";

  /** The Constant ATTRIBUTE_DELETION_EXCEPTION. */
  public static final String ATTRIBUTE_DELETION_EXCEPTION =
      "Cannot delete a parent row: a foreign key constraint fails";

  /** The Constant GETTING_ATTRIBUTES. */
  public static final String GETTING_ATTRIBUTES =
      "Getting all the Attributes is successful";

  /** The Constant GETTING_ATTRIBUTES_EXCEPTION. */
  public static final String GETTING_ATTRIBUTES_EXCEPTION =
      "Getting all attribute throw an exception";

  /** The Constant GETTING_ATTRIBUTES_FAILURE. */
  public static final String GETTING_ATTRIBUTES_FAILURE =
      "Failed in getting Attributes";

  /** The Constant GETTING_ATTRIBUTE_DATATYPES. */
  public static final String GETTING_ATTRIBUTE_DATATYPES =
      "Getting all the Attribute DataTypes is successful";

  /** The Constant GETTING_ATTRIBUTE_DATATYPES_ERROR. */
  public static final String GETTING_ATTRIBUTE_DATATYPES_ERROR =
      "Getting Attribute DataTypes throw an exception";

  /** The Constant GETTING_ATTRIBUTE_DATATYPES_FAILURE. */
  public static final String GETTING_ATTRIBUTE_DATATYPES_FAILURE =
      "Failed to Get all Attribute DataTypes";

  /** The Constant GET_ATTRIBUTE_BY_ID. */
  public static final String GET_ATTRIBUTE_BY_ID =
      "Getting Attribute Details is successful";

  /** The Constant GET_ATTRIBUTE_BY_ID_ERROR. */
  public static final String GET_ATTRIBUTE_BY_ID_ERROR =
      "Getting attribute details by id throw an exception";

  /** The Constant GETTING_ATTRIBUTE_BY_ID_FAILURE. */
  public static final String GETTING_ATTRIBUTE_BY_ID_FAILURE =
      "Failed in getting Attribute Details by id";

  /** The Constant ATTRIBUTE_NOT_EXISTS. */
  public static final String ATTRIBUTE_NOT_EXISTS = "Attribute not Exists";

  /** Category module. */
  public static final String ADD_CATEGORY = "Category Created Successfully";

  /** The Constant ADDING_CATEGORY_ERROR. */
  public static final String ADDING_CATEGORY_ERROR =
      "Adding category throw an exception";

  /** The Constant ADDING_CATEGORY_FAILURE. */
  public static final String ADDING_CATEGORY_FAILURE =
      "Failed in Category Creation";

  /** The Constant CODE_PATTERN_EXISTS. */
  public static final String CODE_PATTERN_EXISTS =
      "Code Pattern Already Exists";

  /** The Constant CATEGORY_EXISTS. */
  public static final String CATEGORY_EXISTS = "Category Already Exists";

  /** The Constant CATEGORY_NOT_EXISTS. */
  public static final String CATEGORY_NOT_EXISTS = "Category not Exists";

  /** The Constant NO_ATTRIBUTE_FOR_CATEGORY. */
  public static final String NO_ATTRIBUTE_FOR_CATEGORY =
      "No attributes available for the given category";

  /** The Constant UPDATE_CATEGORY. */
  public static final String UPDATE_CATEGORY = "Category updated successfully";

  /** The Constant CATEGORY_UPDATION_ERROR. */
  public static final String CATEGORY_UPDATION_ERROR =
      "Updating category throw an exception";

  /** The Constant CATEGORY_UPDATION_FAILURE. */
  public static final String CATEGORY_UPDATION_FAILURE =
      "Failed in Category updation";

  /** The Constant CATEGORY_ATTRIBUTES_UPDATION_FAILURE. */
  public static final String CATEGORY_ATTRIBUTES_UPDATION_FAILURE =
      "Can't be updated as resources are allocated to this category";

  /** The Constant CATEGORY_NAME_EXISTS. */
  public static final String CATEGORY_NAME_EXISTS =
      "Category name Already Exists";

  /** The Constant DELETE_CATEGORY. */
  public static final String DELETE_CATEGORY = "Category deleted successfully";

  /** The Constant DELETE_CATEGORY_ERROR. */
  public static final String DELETE_CATEGORY_ERROR =
      "Deleting category throw an exception";

  /** The Constant CATEGORY_DELETION_FAILURE. */
  public static final String CATEGORY_DELETION_FAILURE =
      "Failed in Category Deletion";

  /** The Constant CATEGORY_DELETION_EXCEPTION. */
  public static final String CATEGORY_DELETION_EXCEPTION =
      "Cannot delete a parent row: a foreign key constraint fails";

  /** The Constant GET_CATEGORY. */
  public static final String GET_CATEGORY =
      "Getting all the Categories is successful";

  /** The Constant GET_CATEGORY_ERROR. */
  public static final String GET_CATEGORY_ERROR =
      "Getting all Categories throw an exception";

  /** The Constant GET_CATEGORY_FAILURE. */
  public static final String GET_CATEGORY_FAILURE =
      "Failed in Getting Categories";

  /** The Constant GET_CATEGORY_ATTRIBUTE. */
  public static final String GET_CATEGORY_ATTRIBUTE =
      "Getting the Category related Attributes is successful";

  /** The Constant GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_ERROR. */
  public static final String GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_ERROR =
      "Getting attribute details by category throw an exception";

  /** The Constant GET_CATEGORY_ATTRIBUTE_FAILURE. */
  public static final String GET_CATEGORY_ATTRIBUTE_FAILURE =
      "Failed in getting Category related Attributes by id";

  /** The Constant GET_CATEGORY_LIST_ATTRIBUTE. */
  public static final String GET_CATEGORY_LIST_ATTRIBUTE =
      "Getting all the Category list related Attributes is successful";

  /** The Constant GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_LIST_ERROR. */
  public static final String GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_LIST_ERROR =
      "Getting attribute details by category list throw an exception";

  /** The Constant GET_CATEGORY_LIST_ATTRIBUTE_FAILURE. */
  public static final String GET_CATEGORY_LIST_ATTRIBUTE_FAILURE =
      "Failed in Getting Category List related Attributes";

  /** The Constant GET_ALL_OPERATIONS. */
  public static final String GET_ALL_OPERATIONS =
      "Getting all the Operations is successful";

  /** The Constant GET_ALL_OPERATIONS_ERROR. */
  public static final String GET_ALL_OPERATIONS_ERROR =
      "Getting operations list throw an exception";

  /** The Constant GET_ALL_OPERATIONS_FAILURE. */
  public static final String GET_ALL_OPERATIONS_FAILURE =
      "Failed in Getting operations";

  /** The Constant GET_CATEGORY_VIEW. */
  public static final String GET_CATEGORY_VIEW =
      "Getting all Categories with View Permission is successful";

  /** The Constant GET_CATEGORY_VIEW_ERROR. */
  public static final String GET_CATEGORY_VIEW_ERROR =
      "Getting Categories with View Permission throw an exception";

  /** The Constant GET_CATEGORY_VIEW_FAILURE. */
  public static final String GET_CATEGORY_VIEW_FAILURE =
      "Failed in Getting Categories with View Permission";

  /** The Constant GET_CATEGORY_ADD. */
  public static final String GET_CATEGORY_ADD =
      "Getting all Categories with add Permission is successful";

  /** The Constant GET_CATEGORY_ADD_ERROR. */
  public static final String GET_CATEGORY_ADD_ERROR =
      "Getting Categories with add Permission throw an exception";

  /** The Constant GET_CATEGORY_ADD_FAILURE. */
  public static final String GET_CATEGORY_ADD_FAILURE =
      "Failed in Getting Categories with add Permission";

  /** The Constant GET_CATEGORY_DISPOSE. */
  public static final String GET_CATEGORY_DISPOSE =
      "Getting all Categories with dispose Permission is successful";

  /** The Constant GET_CATEGORY_DISPOSE_ERROR. */
  public static final String GET_CATEGORY_DISPOSE_ERROR =
      "Getting Categories with dispose Permission throw an exception";

  /** The Constant GET_CATEGORY_DISPOSE_FAILURE. */
  public static final String GET_CATEGORY_DISPOSE_FAILURE =
      "Failed in Getting Categories with dispose Permission";

  /** The Constant GET_CATEGORY_ALLOCATION. */
  public static final String GET_CATEGORY_ALLOCATION =
      "Getting all Categories with Allocate Permission is successful";

  /** The Constant GET_CATEGORY_ALLOCATION_ERROR. */
  public static final String GET_CATEGORY_ALLOCATION_ERROR =
      "Getting Categories with Allocate Permission throw an exception";

  /** The Constant GET_CATEGORY_ALLOCATION_FAILURE. */
  public static final String GET_CATEGORY_ALLOCATION_FAILURE =
      "Failed in Getting Categories with Allocate Permission";

  /** Resource module. */
  public static final String RESOURCE_ADD_PERMISSION =
      "Access is denied to add resource";

  /** The Constant RESOURCE_UPDATE_PERMISSION. */
  public static final String RESOURCE_UPDATE_PERMISSION =
      "Access is denied to update resource";

  /** The Constant RESOURCE_VIEW_PERMISSION. */
  public static final String RESOURCE_VIEW_PERMISSION =
      "Access is denied to get resource";

  /** The Constant RESOURCE_DELETE_PERMISSION. */
  public static final String RESOURCE_DELETE_PERMISSION =
      "Access is denied to delete resource";

  /** The Constant RESOURCE_DISPOSE_PERMISSION. */
  public static final String RESOURCE_DISPOSE_PERMISSION =
      "Access is denied to dispose resource";

  /** The Constant RESOURCE_RESTORE_PERMISSION. */
  public static final String RESOURCE_RESTORE_PERMISSION =
      "Access is denied to restore resource";

  /** The Constant RESOURCE_ALLOCATION_PERMISSION. */
  public static final String RESOURCE_ALLOCATION_PERMISSION =
      "Access is denied to allocate resource";

  /** The Constant RESOURCE_NOT_EXISTS. */
  public static final String RESOURCE_NOT_EXISTS =
      "Category exists but no resources available";

  /** The Constant DELETE_RESOURCE. */
  public static final String DELETE_RESOURCE = "Resource deleted successfully";

  /** The Constant DELETE_ALLOCATED_RESOURCE. */
  public static final String DELETE_ALLOCATED_RESOURCE =
      "Allocated Resource cannot be deleted";

  /** The Constant DISPOSE_RESOURCE_REASON. */
  public static final String DISPOSE_RESOURCE_REASON =
      "Data too long for reason to dispose resource";

  /** The Constant DISPOSE_ALLOCATED_RESOURCE. */
  public static final String DISPOSE_ALLOCATED_RESOURCE =
      "Allocated Resources cannot be Disposed";

  /** The Constant RESOURCE_EXISTS_FAILURE. */
  public static final String RESOURCE_EXISTS_FAILURE = "Resource not exists";

  /** The Constant DISPOSE_RESOURCE_FAILURE. */
  public static final String DISPOSE_RESOURCE_FAILURE =
      "No Dipsosed Resources available for the given category";

  /** The Constant ALLOCATION_RESOURCE_FAILURE. */
  public static final String ALLOCATION_RESOURCE_FAILURE =
      "Failed in Allocating some Resources with "
          + "inventory code as they are already allocated";

  /** The Constant ADD_RESOURCE. */
  public static final String ADD_RESOURCE = "Resources Created Successfully";

  /** The Constant ADDING_RESOURCE_ERROR. */
  public static final String ADDING_RESOURCE_ERROR =
      "Adding resource throw an exception";

  /** The Constant ADDING_RESOURCE_FAILURE. */
  public static final String ADDING_RESOURCE_FAILURE =
      "Failed in Resources Creation";

  /** The Constant GET_LAST_RESOURCE_BY_CATEGORY. */
  public static final String GET_LAST_RESOURCE_BY_CATEGORY =
      "Getting Last Resource Details by Category is successful";

  /** The Constant GET_LAST_RESOURCE_BY_CATEGORY_ERROR. */
  public static final String GET_LAST_RESOURCE_BY_CATEGORY_ERROR =
      "Get last resource by category throw an exception";

  /** The Constant GET_LAST_RESOURCE_BY_CATEGORY_FAILURE. */
  public static final String GET_LAST_RESOURCE_BY_CATEGORY_FAILURE =
      "Failed in Getting Last Resource Details";

  /** The Constant GET_DISPOSE_RESOURCE_BY_CATEGORY. */
  public static final String GET_DISPOSE_RESOURCE_BY_CATEGORY =
      "Getting disposed resources by Category is successful";

  /** The Constant GET_DISPOSED_RESOURCE_BY_CATEGORY_ERROR. */
  public static final String GET_DISPOSED_RESOURCE_BY_CATEGORY_ERROR =
      "Get disposed resources by categoryId throw an exception";

  /** The Constant GET_DISPOSED_RESOURCE_BY_CATEGORY_FAILURE. */
  public static final String GET_DISPOSED_RESOURCE_BY_CATEGORY_FAILURE =
      "Failed in getting Category related disposed Resources";

  /** The Constant GET_RESOURCE. */
  public static final String GET_RESOURCE =
      "Getting Resource Details is successful";

  /** The Constant GET_RESOURCE_ERROR. */
  public static final String GET_RESOURCE_ERROR =
      "Getting resource throw an exception";

  /** The Constant GET_RESOURCE_FAILURE. */
  public static final String GET_RESOURCE_FAILURE =
      "Failed in Getting Resource Details";

  /** The Constant GET_DISPOSE_RESOURCE. */
  public static final String GET_DISPOSE_RESOURCE =
      "Getting Disposed Resource Details is successful";

  /** The Constant GET_DISPOSE_RESOURCE_ERROR. */
  public static final String GET_DISPOSE_RESOURCE_ERROR =
      "Getting Dispose resource throw an exception";

  /** The Constant GET_DISPOSE_RESOURCE_FAILURE. */
  public static final String GET_DISPOSE_RESOURCE_FAILURE =
      "Failed in Getting Disposed Resource Details";

  /** The Constant GET_RESOURCE_DETAILS. */
  public static final String GET_RESOURCE_DETAILS =
      "Getting Resources Details is successful";

  /** The Constant GET_RESOURCE_DETAILS_ERROR. */
  public static final String GET_RESOURCE_DETAILS_ERROR =
      "Getting Resources Details throw an exception";

  /** The Constant GET_RESOURCE_DETAILS_FAILURE. */
  public static final String GET_RESOURCE_DETAILS_FAILURE =
      "Failed in Getting Resources Details";

  /** The Constant GET_ALL_RESOURCE_BY_CATEGORY. */
  public static final String GET_ALL_RESOURCE_BY_CATEGORY =
      "Getting all the resources is successful";

  /** The Constant GET_ALL_RESOURCE_BY_CATEGORY_ERROR. */
  public static final String GET_ALL_RESOURCE_BY_CATEGORY_ERROR =
      "Getting Category related Resources throw an exception";

  /** The Constant GET_ALL_RESOURCE_BY_CATEGORY_FAILURE. */
  public static final String GET_ALL_RESOURCE_BY_CATEGORY_FAILURE =
      "Failed in getting Category related Resources";

  /** The Constant DELETE_RESOURCE_ERROR. */
  public static final String DELETE_RESOURCE_ERROR =
      "Delete Resource throw an exception";

  /** The Constant RESOURCE_DELETION_FAILURE. */
  public static final String RESOURCE_DELETION_FAILURE =
      "Failed in Resource Deletion";

  /** The Constant UPDATE_RESOURCE. */
  public static final String UPDATE_RESOURCE = "Resources Updated Successfully";

  /** The Constant RESOURCE_UPDATION_FAILURE. */
  public static final String RESOURCE_UPDATION_FAILURE =
      "Failed in Resources Updation";

  /** The Constant UPDATE_RESOURCE_ERROR. */
  public static final String UPDATE_RESOURCE_ERROR =
      "Updating resource throw an exception";

  /** The Constant DISPOSE_RESOURCE. */
  public static final String DISPOSE_RESOURCE =
      "Resource is disposed successfully";

  /** The Constant DISPOSE_RESOURCE_ERROR. */
  public static final String DISPOSE_RESOURCE_ERROR =
      "Dispose resource throw an exception";

  /** The Constant RESOURCE_DISPOSE_FAILURE. */
  public static final String RESOURCE_DISPOSE_FAILURE =
      "Failed in Resource Disposition";

  /** The Constant RESTORE_RESOURCE. */
  public static final String RESTORE_RESOURCE =
      "Resources is restored successfully";

  /** The Constant RESTORE_RESOURCE_FAILURE. */
  public static final String RESTORE_RESOURCE_FAILURE =
      "Failed in Resource Restoration";

  /** The Constant RESTORE_RESOURCE_ERROR. */
  public static final String RESTORE_RESOURCE_ERROR =
      "Restoring resource throw an exception";

  /** The Constant GET_RESOURCE_BY_SEARCHTERM. */
  public static final String GET_RESOURCE_BY_SEARCHTERM =
      "Getting all the resources by Searchterm is successful";

  /** The Constant GET_RESOURCE_BY_SEARCHTERM_ERROR. */
  public static final String GET_RESOURCE_BY_SEARCHTERM_ERROR =
      "Getting all the resources by Searchterm throw an exception";

  /** The Constant GET_RESOURCE_BY_SEARCHTERM_FAILURE. */
  public static final String GET_RESOURCE_BY_SEARCHTERM_FAILURE =
      "Failed in getting Resources by search term";

  /** The Constant GET_DISPOSE_RESOURCE_BY_SEARCHTERM. */
  public static final String GET_DISPOSE_RESOURCE_BY_SEARCHTERM =
      "Getting all the disposed resources by searchterm is successful";

  /** The Constant GET_DISPOSE_RESOURCE_BY_SEARCHTERM_ERROR. */
  public static final String GET_DISPOSE_RESOURCE_BY_SEARCHTERM_ERROR =
      "Getting all the disposed resources by Searchterm throw an exception";

  /** The Constant GET_DISPOSE_RESOURCE_BY_SEARCHTERM_FAILURE. */
  public static final String GET_DISPOSE_RESOURCE_BY_SEARCHTERM_FAILURE =
      "Failed in getting dispose Resources by search term";

  /** The Constant RESOURCE_ALLOCATION. */
  public static final String RESOURCE_ALLOCATION =
      "Resource Allocation or DeAllocation is Successful";

  /** The Constant RESOURCE_ALLOCATION_FAILURE. */
  public static final String RESOURCE_ALLOCATION_FAILURE =
      "Failed in resource Allocation or Deallocation";

  /** The Constant RESOURCE_ALLOCATION_ERROR. */
  public static final String RESOURCE_ALLOCATION_ERROR =
      "Allocating or deallocating resources throw an exception";

  /** The Constant FORBIDDEN. */
  public static final int FORBIDDEN = 403;

  /** The Constant INTERNAL_SERVER_ERROR. */
  public static final int INTERNAL_SERVER_ERROR = 500;

  /** The Constant NOT_FOUND. */
  public static final int NOT_FOUND = 404;

  /** The Constant OK. */
  public static final int OK = 200;

  /** The Constant CONFLICT. */
  public static final int CONFLICT = 409;

  /** The Constant BAD_REQUEST. */
  public static final int BAD_REQUEST = 400;

  /** The Constant NO_CONTENT. */
  public static final int NO_CONTENT = 204;

  /** The Constant CREATED. */
  public static final int CREATED = 201;

  /** The Constant VALUE_LENGTH. */
  public static final int VALUE_LENGTH = 100;

  /** The Constant LENGTH. */
  public static final int LENGTH = 20;

  /** The Constant PROJECT_NAME_LENGTH. */
  public static final int PROJECT_NAME_LENGTH = 5;

  /** The Constant EMAIL_LENGTH. */
  public static final int EMAIL_LENGTH = 32;

  /** The Constant OPERATION_VIEW. */
  public static final int OPERATION_VIEW = 1;

  /** The Constant OPERATION_ADD. */
  public static final int OPERATION_ADD = 2;

  /** The Constant OPERATION_EDIT. */
  public static final int OPERATION_EDIT = 3;

  /** The Constant OPERATION_ALLOCATE. */
  public static final int OPERATION_ALLOCATE = 4;

  /** The Constant OPERATION_DISPOSE. */
  public static final int OPERATION_DISPOSE = 5;

  /** The Constant OPERATION_DELETE. */
  public static final int OPERATION_DELETE = 6;

  /** The Constant DATA_TYPE_ID_INTEGER. */
  public static final int DATA_TYPE_ID_INTEGER = 2;

  /** The Constant DATA_TYPE_ID_FLOAT. */
  public static final int DATA_TYPE_ID_FLOAT = 3;

  /** The Constant DATA_TYPE_ID_CURRENCY. */
  public static final int DATA_TYPE_ID_CURRENCY = 4;

  /** The Constant DATA_TYPE_ID_DATE. */
  public static final int DATA_TYPE_ID_DATE = 5;

  /** The Constant VALUE_ONE. */
  public static final int VALUE_ONE = 1;

  /** The Constant VALUE_TWO. */
  public static final int VALUE_TWO = 2;

  /** The Constant VALUE_THREE. */
  public static final int VALUE_THREE = 3;

  /** The Constant VALUE_FOUR. */
  public static final int VALUE_FOUR = 4;

  /** The Constant VALUE_FIVE. */
  public static final int VALUE_FIVE = 5;

  /** The Constant DISPOSE_REASON_LENGTH. */
  public static final int DISPOSE_REASON_LENGTH = 255;
}
