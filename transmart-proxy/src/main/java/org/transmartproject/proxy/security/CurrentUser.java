package org.transmartproject.proxy.security;

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class CurrentUser {

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    private static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication instanceof OAuth2Authentication) {
                if (((OAuth2Authentication) authentication).isClientOnly()) {
                    return false;
                }
            }
            return authentication.getAuthorities().stream()
                    .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ANONYMOUS"));
        }
        return false;
    }

    private static Authentication getAuthentication() {
        if (!isAuthenticated()) {
            return null;
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }

    public static String getLogin() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        } else {
            return authentication.getName();
        }
    }

    public static String getAccessToken() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object details = authentication.getDetails();
        assert details instanceof SimpleKeycloakAccount;
        return ((SimpleKeycloakAccount) details).getKeycloakSecurityContext().getTokenString();
    }

}
