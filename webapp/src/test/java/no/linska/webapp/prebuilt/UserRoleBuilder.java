package no.linska.webapp.prebuilt;

import no.linska.webapp.entity.UserRole;

public class UserRoleBuilder {
    public static UserRole sellerUserRole() {
        UserRole userRole =  new UserRole();
        userRole.setId(2);
        userRole.setType("seller");
        return userRole;
    }
}
