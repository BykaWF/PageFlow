package com.project.pageflow.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.project.pageflow.util.Constant.*;

/**
 * The authoritiesListProvider class provides a utility for retrieving authorities based on user type.
 * It maps each user type to a string representation of their authorities.
 */
public class authoritiesListProvider {

    private static HashMap<String, String> authoritiesMap;

    /**
     * Retrieves the authorities associated with the specified user type.
     * If the authoritiesMap is null, it initializes it with predefined authorities for student and admin users.
     * @param userType The type of user for which authorities are to be retrieved.
     * @return A string representation of the authorities associated with the specified user type.
     */
    public static String getAuthorities(String userType) {
        if(authoritiesMap == null) {

            authoritiesMap = new HashMap<>();

            List<String> studentAuthorities = Arrays.asList(
                    Constant.STUDENT_SELF_INFO_AUTHORITY
            );
            List<String> adminAuthorities = Arrays.asList(
                    Constant.CREATE_BOOK_AUTHORITY,
                    Constant.CREATE_ADMIN_AUTHORITY,
                    Constant.INITIATE_TRANSACTION_AUTHORITY
            );

            String studentAuthoritiesAsString = String.join(Constant.DELIMITER, studentAuthorities);
            String adminAuthoritiesAsString = String.join(Constant.DELIMITER, adminAuthorities);

            authoritiesMap.put(Constant.STUDENT_USER, studentAuthoritiesAsString);
            authoritiesMap.put(Constant.ADMIN_USER, adminAuthoritiesAsString);
        }

        return authoritiesMap.containsKey(userType) ? authoritiesMap.get(userType) : "";
    }
}
