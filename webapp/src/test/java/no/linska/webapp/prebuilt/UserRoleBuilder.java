package no.linska.webapp.prebuilt;

import no.linska.webapp.entity.UserRole;

public class UserRoleBuilder {


    public static UserRole createCustomerUserRole() {
        UserRole userRole =  new UserRole();
        userRole.setId(1);
        userRole.setType("customer");
        return userRole;
    }

    public static UserRole createSellerUserRole() {
        UserRole userRole =  new UserRole();
        userRole.setId(2);
        userRole.setType("seller");
        return userRole;
    }
}
