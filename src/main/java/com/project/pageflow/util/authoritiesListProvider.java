package com.project.pageflow.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.project.pageflow.util.Constant.*;

public class authoritiesListProvider {
    private static HashMap<String, String> authoritiesMap;

    public static String getAuthorities(String userType) {
        if(authoritiesMap == null) {
            authoritiesMap = new HashMap<>();
            List<String> studentAuthorities = Arrays.asList(
                    STUDENT_SELF_INFO_AUTHORITY
            );
            List<String> adminAuthorities = Arrays.asList(
                    CREATE_BOOK_AUTHORITY,
                    CREATE_ADMIN_AUTHORITY,
                    INITIATE_TRANSACTION_AUTHORITY
            );

            String studentAuthorititesAsString = String.join(DELIMITER, studentAuthorities);
            String adminAuthoritiesAsString = String.join(DELIMITER, adminAuthorities);
            authoritiesMap.put(STUDENT_USER, studentAuthorititesAsString);
            authoritiesMap.put(ADMIN_USER, adminAuthoritiesAsString);
        }

        return authoritiesMap.containsKey(userType) ? authoritiesMap.get(userType) : "";
    }
}
