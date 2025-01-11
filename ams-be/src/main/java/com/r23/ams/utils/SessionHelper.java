package com.r23.ams.utils;

import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.SessionUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionHelper {
    public static AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return ((SessionUser)authentication.getPrincipal()).getUser();
        }
        return null;
    }

}
