package com.zone.zissa.response;

public class RestAPIMessageConstants {

    private RestAPIMessageConstants() {
        throw new IllegalAccessError("Utility class");
    }

    public static final String ACCESS_DENIED = "Access is denied";

    public static final String CAT_CREATION_FAILURE = "Failed in Category Creation";

    public static final String ATTR_DELETION_FAILURE = "Failed in Attribute Deletion";

    public static final String CAT_DELETION_FAILURE = "Failed in Category Deletion";

    public static final String RESC_DELETION_FAILURE = "Failed in Resource Deletion";

    public static final String USER_DELETION_FAILURE = "Failed in User Deletion";

    public static final String ROLE_DELETION_FAILURE = "Failed in Role Deletion";

    public static final String ATTR_UPDATION_FAILURE = "Failed in Attribute Updation";

    public static final String CAT_UPDATION_FAILURE = "Failed in Category Updation";

    public static final String RESC_UPDATION_FAILURE = "Failed in Resource Updation";

    public static final String USER_UPDATION_FAILURE = "Failed in User Updation";

    public static final String ROLE_UPDATION_FAILURE = "Failed in Role Updation";

    public static final String CATATTR_RETREIVE_FAILURE = "Failed in getting Category related Attributes";

    public static final String CATRESC_RETREIVE_FAILURE = "Failed in getting Category related Resources";

    public static final String CATDISPRESC_RETREIVE_FAILURE = "Failed in getting Category related disposed Resources";

    public static final String RESC_EXISTS_FAILURE = "Resource not Exists";

    public static final String ATTR_EXISTS_FAILURE = "Attribute not Exists";

    public static final String CAT_EXISTS_FAILURE = "Category not Exists";

    public static final String ROLE_EXISTS_FAILURE = "Role not Exists";

    public static final String USER_EXISTS_FAILURE = "User not Exists";

    public static final String RESOURCE_ALLOCATION = "Resource Allocation or DeAllocation is Successfull";

    public static final String RESOURCE_ALLOCATION_FAILURE = "Failed in resource Allocation or Deallocation";

    public static final String GETTING_ALLOCATION_HISTORY_FAILURE = "Failed in getting Allocation History Details by Resource";

    public static final String ATTR_UPDATION = "Attribute Updated Successfully";

    public static final String GETTING_ATTR_FAILURE = "Failed in getting Attribute Details";

    /**
     * Error message constants
     * 
     */
    public static final String RESOURCE_ALLOCATION_ERROR = "Allocating or deallocating resources throw an exception";

    public static final String GET_ALLOCATION_DETAILS_BY_RESOURCE_ERROR = "Get Allocation Details By Resource throw an exception";

    public static final String ADDING_ATTRIBUTE_ERROR = "Adding attribute throw an exception";

    public static final String ATTRIBUTE_DELETION_ERROR = "Deleting attribute throw an exception";

    public static final String ATTRIBUTE_UPDATION_ERROR = "Updating attribute throw an exception";

    public static final String ADDING_CATEGORY_ERROR = "Adding category throw an exception";

    public static final String CATEGORY_UPDATION_ERROR = "Updating category throw an exception";

    public static final String GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_ERROR = "Getting attribute details by category throw an exception";

    public static final String GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_LIST_ERROR = "Getting attribute details by category list throw an exception";

    public static final String DELETE_CATEGORY_ERROR = "Deleting category throw an exception";

    public static final String LDAP_SEARCH_ERROR = "Getting all Ldap Users by Search string throw an exception";

    public static final String ADDING_RESOURCE_ERROR = "Adding resource throw an exception";

    public static final String GET_DISPOSED_RESOURCE_BY_CATEGORY_ERROR = "getDisposedResourcesByCategoryId throw an exception";

    public static final String GET_RESOURCE_ERROR = "Getting resource throw an exception";

    public static final String GET_DISPOSED_RESOURCE_ERROR = "Getting Disposed Resource throw an exception";

    public static final String GET_ALL_RESOURCE_BY_CATEGORY_ERROR = "Getting Category related Resources throw an exception";

    public static final String DELETE_RESOURCE_ERROR = "Delete Resource throw an exception";

    public static final String UPDATE_RESOURCE_ERROR = "Updating resource throw an exception";

    public static final String DISPOSE_RESOURCE_ERROR = "Dispose resource throw an exception";

    public static final String RESTORE_RESOURCE_ERROR = "Restoring resource throw an exception";

    public static final String GETTING_PERMISSIONS_BY_ROLE_CATEGORY_ERROR = "Getting all permissions by role and category throw an exception";

    public static final String ADDING_ROLE_ERROR = "Adding role throw an exception";

    public static final String UPDATE_ROLE_ERROR = "Updating role throw an exception";

    public static final String DELETE_ROLE_ERROR = "Deleting role throw an exception";

    public static final String GETTING_PERMISSIONS_BY_ROLE_ERROR = "Getting all permissions by role throw an exception";

    public static final String ADDING_USER_ERROR = "Adding user throw an exception";

    public static final String UPDATE_USER_ERROR = "Updating user throw an exception";

    public static final String DELETE_USER_ERROR = "Deleting user throw an exception";

    public static final String ACCESS_DENIED_ERROR = "Error in accessing data";

}
