package com.medfusion.factory.pojos.provisioning;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lubson on 17.01.16.
 */
public class UserRoles {

    public String userId;

    public String userName;

    private String roles;

    public Set<String> getRoles(){
        Set<String> rolesArray;
        if (roles != null) {
            String roles = this.roles.toUpperCase().replaceAll("\\s", "");
            rolesArray = new HashSet<String>(Arrays.asList(roles.split(",")));
        } else {
            rolesArray = new HashSet<String>();
        }
        return rolesArray;
    }

    public void setRoles(String roles){
      this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoles userRoles = (UserRoles) o;

        if (!userId.equals(userRoles.userId)) return false;
        if (!userName.equals(userRoles.userName)) return false;
        return getRoles().equals(userRoles.getRoles());
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + getRoles().hashCode();
        return result;
    }
}
