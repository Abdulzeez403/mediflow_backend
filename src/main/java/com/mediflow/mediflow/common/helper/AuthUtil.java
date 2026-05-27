package com.mediflow.mediflow.common.helper;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mediflow.mediflow.user.entity.UserPrincipal;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class AuthUtil {

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // ✅ EMAIL
    public String getCurrentUserEmail() {
        UserPrincipal principal = (UserPrincipal) getAuth().getPrincipal();
        return principal.getEmail();
    }

    // ✅ USER ID
    public String getCurrentUserId() {
        UserPrincipal principal = (UserPrincipal) getAuth().getPrincipal();
        return principal.getUserId();
    }

    // ✅ ROLES
    public List<String> getCurrentUserRoles() {
        return getAuth()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", "")) // optional cleanup
                .toList();
    }
}