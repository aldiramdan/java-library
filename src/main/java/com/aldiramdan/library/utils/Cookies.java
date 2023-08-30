package com.aldiramdan.library.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;

public class Cookies {
    public static void setEmailCookie(HttpServletResponse response, String code) {
        Cookie resetPasswordCookie = new Cookie("resetPasswordEmail", code);

        // Set the cookie path (optional, depending on your requirements)
        resetPasswordCookie.setPath("/");  // The cookie is available

        // Set the cookie expiration time (in seconds)
        resetPasswordCookie.setMaxAge(900);  // Cookie expires in 15 min

        // Add the cookie to the response
        response.addCookie(resetPasswordCookie);
    }

    public static void setCodeCookie(HttpServletResponse response, String code) {
        Cookie resetPasswordCookie = new Cookie("resetPasswordCode", code);

        // Set the cookie path (optional, depending on your requirements)
        resetPasswordCookie.setPath("/");  // The cookie is available

        // Set the cookie expiration time (in seconds)
        resetPasswordCookie.setMaxAge(900);  // Cookie expires in 15 min

        // Add the cookie to the response
        response.addCookie(resetPasswordCookie);
    }
}
