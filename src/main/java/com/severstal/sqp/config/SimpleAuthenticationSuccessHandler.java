package com.severstal.sqp.config;

/**
 * Spring security configuration.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.config
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setStatus(HttpServletResponse.SC_OK);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        authorities.forEach(authority -> {
            if (authority.getAuthority().equals("ROLE_USER")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/adminPanel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (authority.getAuthority().equals("ROLE_MANAGER")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/manager");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (authority.getAuthority().equals("ROLE_DIRECTOR") || authority.getAuthority().equals("ROLE_TOP_MANAGEMENT")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/report");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (authority.getAuthority().equals("ROLE_HEADMAN")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/onlineReports");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalStateException();
            }
        });

    }
}
